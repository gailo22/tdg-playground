package main

import (
	"context"
	"crypto/sha256"
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
	"text/template"
	"time"

	"github.com/google/uuid"
	"go.oauth2.client/model"
)

var config = struct {
	appID               string
	authURL             string
	logoutURL           string
	afterLogoutRedirect string
	authCodeCallback    string
	tokenEndpoint       string
	appPassword         string
	servicesEndpoint    string
}{
	appID:               "billingApp",
	appPassword:         "b65e913c-a1eb-4905-9612-d0a08e5f480b",
	authURL:             "http://localhost:9999/auth/realms/myrealm/protocol/openid-connect/auth",
	logoutURL:           "http://localhost:9999/auth/realms/myrealm/protocol/openid-connect/logout",
	afterLogoutRedirect: "http://localhost:8080/home",
	authCodeCallback:    "http://localhost:8080/authCodeRedirect",
	tokenEndpoint:       "http://localhost:9999/auth/realms/myrealm/protocol/openid-connect/token",
	servicesEndpoint:    "http://localhost:8081/billing/v1/services",
	// servicesEndpoint:    "http://localhost:8082/billing/v1/services", // evil service
}

var t = template.Must(template.ParseFiles("template/index.html"))
var tServices = template.Must(template.ParseFiles("template/index.html", "template/services.html"))

type AppVar struct {
	AuthCode     string
	SessionState string
	AccessToken  string
	RefreshToken string
	Scope        string
	Services     []string
	State        map[string]struct{}
}

func newAppVar() AppVar {
	return AppVar{State: make(map[string]struct{})}
}

var appVar = newAppVar()

var codeVerifier = "elU6u5zyqQT2f92GRQUq6PautAeNDf4DQPayyR0ek_c"

func main() {
	const port string = ":8080"
	http.HandleFunc("/home", enableLog(home))
	http.HandleFunc("/login", enableLog(login))
	http.HandleFunc("/logout", enableLog(logout))
	// http.HandleFunc("/exchangeToken", enableLog(exchangeToken))
	http.HandleFunc("/services", enableLog(services))
	http.HandleFunc("/refreshToken", enableLog(refreshToken))
	http.HandleFunc("/authCodeRedirect", enableLog(authCodeRedirect))
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

func home(w http.ResponseWriter, r *http.Request) {
	t.Execute(w, appVar)
}

func login(w http.ResponseWriter, r *http.Request) {
	req, err := http.NewRequest("GET", config.authURL, nil)
	if err != nil {
		log.Print(err)
		return
	}
	qs := url.Values{}
	state := uuid.New().String()
	appVar.State[state] = struct{}{}
	qs.Add("state", state)
	qs.Add("client_id", config.appID)
	qs.Add("response_type", "code")
	qs.Add("redirect_uri", config.authCodeCallback)
	// qs.Add("scope", "evil-service")
	qs.Add("scope", "billingService")

	codeChallenge := makeCodeChallenge(codeVerifier)
	qs.Add("code_challenge", codeChallenge)
	qs.Add("code_challenge_method", "S256")

	req.URL.RawQuery = qs.Encode()
	http.Redirect(w, r, req.URL.String(), http.StatusFound)
}

func authCodeRedirect(w http.ResponseWriter, r *http.Request) {
	appVar.AuthCode = r.URL.Query().Get("code")
	callbackState := r.URL.Query().Get("state")
	if _, ok := appVar.State[callbackState]; !ok {
		fmt.Fprintf(w, "Error")
		return
	}
	delete(appVar.State, callbackState)

	appVar.SessionState = r.URL.Query().Get("session_state")
	r.URL.RawQuery = ""
	fmt.Printf("Request query string: %+v\n", appVar)

	// http.Redirect(w, r, "http://localhost:8080", http.StatusFound)
	exchangeToken()
	t.Execute(w, appVar)
}

func logout(w http.ResponseWriter, r *http.Request) {
	qs := url.Values{}
	qs.Add("redirect_uri", config.afterLogoutRedirect)

	logoutURL, err := url.Parse(config.logoutURL)
	if err != nil {
		log.Println(err)
	}
	logoutURL.RawQuery = qs.Encode()
	appVar = newAppVar()
	http.Redirect(w, r, logoutURL.String(), http.StatusFound)
}

func exchangeToken() {
	form := url.Values{}
	form.Add("grant_type", "authorization_code")
	form.Add("code", appVar.AuthCode)
	form.Add("redirect_uri", config.authCodeCallback)
	form.Add("client_id", config.appID)
	form.Add("code_verifier", codeVerifier)
	req, err := http.NewRequest("POST", config.tokenEndpoint, strings.NewReader(form.Encode()))
	req.Header.Add("Content-Type", "application/x-www-form-urlencoded")
	if err != nil {
		log.Println(err)
		return
	}

	req.SetBasicAuth(config.appID, config.appPassword)

	c := http.Client{}
	res, err := c.Do(req)
	if err != nil {
		log.Println("couldn't get access token", err)
		return
	}

	byteBody, err := ioutil.ReadAll(res.Body)
	defer res.Body.Close()
	if err != nil {
		log.Println(err)
		return
	}

	accessTokenResponse := &model.AccessTokenResponse{}
	json.Unmarshal(byteBody, accessTokenResponse)

	appVar.AccessToken = accessTokenResponse.AccessToken
	appVar.RefreshToken = accessTokenResponse.RefreshToken
	appVar.SessionState = accessTokenResponse.SessionState
	appVar.Scope = accessTokenResponse.Scope
	log.Println(accessTokenResponse)
}

func services(w http.ResponseWriter, r *http.Request) {
	req, err := http.NewRequest("GET", config.servicesEndpoint, nil)
	req.Header.Add("Content-Type", "application/x-www-form-urlencoded")
	if err != nil {
		log.Println(err)
		tServices.Execute(w, appVar)
		return
	}

	req.Header.Add("Authorization", "Bearer "+appVar.AccessToken)

	ctx, cancelFunc := context.WithTimeout(context.Background(), 500*time.Millisecond)
	defer cancelFunc()

	c := http.Client{}
	res, err := c.Do(req.WithContext(ctx))
	if err != nil {
		log.Println(err)
		tServices.Execute(w, appVar)
		return
	}

	byteBody, err := ioutil.ReadAll(res.Body)
	defer res.Body.Close()
	if err != nil {
		log.Println(err)
		tServices.Execute(w, appVar)
		return
	}

	if res.StatusCode != 200 {
		log.Println(string(byteBody))
		appVar.Services = []string{string(byteBody)}
		tServices.Execute(w, appVar)
		return
	}

	billingResponse := &model.Billing{}
	err = json.Unmarshal(byteBody, billingResponse)
	if err != nil {
		log.Println(err)
		tServices.Execute(w, appVar)
		return
	}

	appVar.Services = billingResponse.Services

	tServices.Execute(w, appVar)
}

func refreshToken(w http.ResponseWriter, r *http.Request) {
	form := url.Values{}
	form.Add("grant_type", "refresh_token")
	form.Add("refresh_token", appVar.RefreshToken)
	// form.Add("scope", "billingService")
	req, err := http.NewRequest("POST", config.tokenEndpoint, strings.NewReader(form.Encode()))
	req.Header.Add("Content-Type", "application/x-www-form-urlencoded")
	if err != nil {
		log.Println(err)
		return
	}

	req.SetBasicAuth(config.appID, config.appPassword)

	c := http.Client{}
	res, err := c.Do(req)
	if err != nil {
		log.Println("couldn't get access token by refresh token", err)
		return
	}

	byteBody, err := ioutil.ReadAll(res.Body)
	defer res.Body.Close()
	if err != nil {
		log.Println(err)
		return
	}

	accessTokenResponse := &model.AccessTokenResponse{}
	json.Unmarshal(byteBody, accessTokenResponse)

	appVar.AccessToken = accessTokenResponse.AccessToken
	appVar.RefreshToken = accessTokenResponse.RefreshToken
	appVar.SessionState = accessTokenResponse.SessionState
	appVar.Scope = accessTokenResponse.Scope
	log.Println(accessTokenResponse)
	t.Execute(w, appVar)
}

func makeCodeChallenge(plain string) string {
	h := sha256.Sum256([]byte(plain))
	hs := base64.RawURLEncoding.EncodeToString(h[:])
	return hs
}
