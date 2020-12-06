package model

type TokenClaim struct {
	Exp      int    `json:"exp"`
	Iat      int    `json:"iat"`
	AuthTime int    `json:"auth_time"`
	Jti      string `json:"jti"`
	Iss      string `json:"iss"`
	// Aud            string   `json:"aud"`
	// "aud": "evil-service",

	// "aud": [
	//   "billingServiceV2",
	//   "billingService"
	// ],
	Aud            interface{} `json:"aud"`
	Sub            string      `json:"sub"`
	Typ            string      `json:"typ"`
	Azp            string      `json:"azp"`
	SessionState   string      `json:"session_state"`
	Acr            string      `json:"acr"`
	AllowedOrigins []string    `json:"allowed-origins"`
	RealmAccess    struct {
		Roles []string `json:"roles"`
	} `json:"realm_access"`
	ResourceAccess struct {
		Account struct {
			Roles []string `json:"roles"`
		} `json:"account"`
	} `json:"resource_access"`
	Scope             string `json:"scope"`
	EmailVerified     bool   `json:"email_verified"`
	Name              string `json:"name"`
	PreferredUsername string `json:"preferred_username"`
	GivenName         string `json:"given_name"`
	FamilyName        string `json:"family_name"`
	Email             string `json:"email"`
}

func (t *TokenClaim) AudAsSlice() []string {

	switch t.Aud.(type) {
	case string:
		return []string{t.Aud.(string)}
	case []interface{}:
		auds, ok := t.Aud.([]interface{})
		if !ok {
			return []string{}
		}
		result := []string{}
		for _, aud := range auds {
			if sAud, ok := aud.(string); ok {
				result = append(result, sAud)
			}
		}
		return result
	default:
		return []string{}
	}
}
