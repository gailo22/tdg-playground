# Getting Started

### Get authentication code

```
https://dev-456882.okta.com/oauth2/default/v1/authorize?client_id=xxx&response_type=code&scope=openid+message:read&redirect_uri=http://localhost:8081/login/oauth2/code/okta&nonce=UBGW&state=1234
```

### Request access token


```
curl --request POST \
--url https://dev-456882.okta.com/oauth2/default/v1/token \
--header 'accept: application/json' \
--header 'content-type: application/x-www-form-urlencoded' \
--data 'client_id=xxx&client_secret=xxx&state=123&grant_type=authorization_code&redirect_uri=http://localhost:8081/login/oauth2/code/okta&code=4BRL4GGoYcY8qjU3K44imOuHqomOoniOdC5H2Uq1dxk'
```


### Call resource server api

```
export TOKEN=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzdWJqZWN0IiwiZXhwIjoyMTY0MjQ1NjQ4LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiY2I1ZGMwNDYtMDkyMi00ZGJmLWE5MzAtOGI2M2FhZTYzZjk2IiwiY2xpZW50X2lkIjoicmVhZGVyIiwic2NvcGUiOlsibWVzc2FnZTpyZWFkIl19.Pre2ksnMiOGYWQtuIgHB0i3uTnNzD0SMFM34iyQJHK5RLlSjge08s9qHdx6uv5cZ4gZm_cB1D6f4-fLx76bCblK6mVcabbR74w_eCdSBXNXuqG-HNrOYYmmx5iJtdwx5fXPmF8TyVzsq_LvRm_LN4lWNYquT4y36Tox6ZD3feYxXvHQ3XyZn9mVKnlzv-GCwkBohCR3yPow5uVmr04qh_al52VIwKMrvJBr44igr4fTZmzwRAZmQw5rZeyep0b4nsCjadNcndHtMtYKNVuG5zbDLsB7GGvilcI9TDDnUXtwthB_3iq32DAd9x8wJmJ5K8gmX6GjZFtYzKk_zEboXoQ

curl -H "Authorization: Bearer $TOKEN" localhost:2222/message

curl -H "Authorization: Bearer $TOKEN" localhost:2222 -v
```