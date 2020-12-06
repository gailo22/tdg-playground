package main

import (
	"encoding/base64"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
	"net/url"
	"reflect"
	"runtime"
	"strings"

	"go.oauth2.billing/model"
)

type Billing struct {
	Services []string `json:"services"`
}

type TokenIntrospect struct {
	Exp    int         `json:"exp"`
	Nbf    int         `json:"nbf"`
	Iat    int         `json:"iat"`
	Jti    string      `json:"jti"`
	Aud    interface{} `json:"aud"`
	Typ    string      `json:"typ"`
	Acr    string      `json:"acr"`
	Active bool        `json:"active"`
}

type BillingError struct {
	Error string `json:"error"`
}

var config = struct {
	appID              string
	appPassword        string
	tokenIntrospection string
}{
	appID:              "tokenIntrospection",
	appPassword:        "ea80da0f-1761-467f-b142-ea13d028a18a",
	tokenIntrospection: "http://localhost:9999/auth/realms/myrealm/protocol/openid-connect/token/introspect",
}

func main() {
	const port string = ":8081"
	http.HandleFunc("/billing/v1/services", enableLog(services))
	log.Println("Server listening on port ", port)
	log.Fatalln(http.ListenAndServe(port, nil))
}

func enableLog(handler func(http.ResponseWriter, *http.Request)) func(http.ResponseWriter, *http.Request) {
	return func(w http.ResponseWriter, r *http.Request) {
		handleName := runtime.FuncForPC(reflect.ValueOf(handler).Pointer()).Name()
		log.Println("---> ", handleName)
		log.Printf("request: %+v\n", r.RequestURI)
		// log.Printf("response: %+v\n", w)
		handler(w, r)
		log.Println("<--- ", handleName)
		log.Println()
	}
}

func services(w http.ResponseWriter, r *http.Request) {
	token, err := getToken(r)
	if err != nil {
		log.Println(err)
		w.Header().Add("Content-Type", "application/json")
		w.Header().Add("Access-Control-Allow-Origin", "*")
		s := &BillingError{Error: err.Error()}
		encoder := json.NewEncoder(w)
		w.WriteHeader(http.StatusBadRequest)
		encoder.Encode(s)
		return
	}

	log.Println("Token:", token)

	if !validateToken(token) {
		w.Header().Add("Content-Type", "application/json")
		w.Header().Add("Access-Control-Allow-Origin", "*")
		s := &BillingError{Error: "Invalid token"}
		encoder := json.NewEncoder(w)
		w.WriteHeader(http.StatusBadRequest)
		encoder.Encode(s)
		return
	}

	tokenParts := strings.Split(token, ".")
	claims, err := base64.RawURLEncoding.DecodeString(tokenParts[1])
	if err != nil {
		fmt.Println(err)
		return
	}

	tokenClaim := &model.TokenClaim{}
	err = json.Unmarshal(claims, tokenClaim)
	if err != nil {
		log.Println(err)
		return
	}

	log.Println("Claims:", tokenClaim)

	scopes := strings.Split(tokenClaim.Scope, " ")
	for _, v := range scopes {
		fmt.Println(v)
	}

	validAudience := false
	for _, v := range tokenClaim.AudAsSlice() {
		if v == "billingService" || v == "billingServiceV2" {
			validAudience = true
			break
		}
	}

	if !validAudience {
		fmt.Println("Invalid audience, requires audience [billingService, billingServiceV2]")
		return
	}

	if !strings.Contains(tokenClaim.Scope, "getBillingService") {
		fmt.Println("Invalid scope, requires scope [getBillingService]")
		return
	}

	s := Billing{
		Services: []string{
			"electric",
			"phone",
			"internet",
			"water",
		},
	}
	w.Header().Add("Content-Type", "application/json")
	w.Header().Add("Access-Control-Allow-Origin", "*")
	encoder := json.NewEncoder(w)
	encoder.Encode(s)
}

func getToken(r *http.Request) (string, error) {
	token := r.Header.Get("Authorization")
	if token != "" {
		auths := strings.Split(token, " ")
		if len(auths) != 2 {
			return "", fmt.Errorf("Invalid Authorization header format")
		}
		return auths[1], nil
	}

	token = r.FormValue("access_token")
	if token != "" {
		return token, nil
	}

	token = r.URL.Query().Get("access_token")
	if token != "" {
		return token, nil
	}
	return token, fmt.Errorf("Missing access token")

}

func validateToken(token string) bool {
	form := url.Values{}
	form.Add("token", token)
	form.Add("token_type_hint", "requesting_party_token")
	req, err := http.NewRequest("POST", config.tokenIntrospection, strings.NewReader(form.Encode()))
	req.Header.Add("Content-Type", "application/x-www-form-urlencoded")
	if err != nil {
		log.Println(err)
		return false
	}

	req.SetBasicAuth(config.appID, config.appPassword)

	c := http.Client{}
	res, err := c.Do(req)
	if err != nil {
		log.Println("couldn't get access token", err)
		return false
	}

	if res.StatusCode != 200 {
		log.Println("response status is: ", res.StatusCode)
		return false
	}

	byteBody, err := ioutil.ReadAll(res.Body)
	defer res.Body.Close()
	if err != nil {
		log.Println(err)
		return false
	}

	tokenIntrospect := &TokenIntrospect{}
	err = json.Unmarshal(byteBody, tokenIntrospect)
	if err != nil {
		log.Println(err)
		return false
	}

	log.Println(tokenIntrospect)

	return tokenIntrospect.Active
}
