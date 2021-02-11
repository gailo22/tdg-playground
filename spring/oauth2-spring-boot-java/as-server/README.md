# Getting Started

### Endpoints

* /oauth/token
* /oauth/token_key
* /oauth/check_token

### Get Token

```bash
curl --location --request POST 'http://localhost:8080/oauth/token' \
--header 'Authorization: Basic Y2xpZW50MTpzZWNyZXQ=' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'username=john' \
--data-urlencode 'password=12345'
```

```json
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MTMwNzA0NjYsInVzZXJfbmFtZSI6ImpvaG4iLCJhdXRob3JpdGllcyI6WyJyZWFkIl0sImp0aSI6ImE1ZjllYTY4LTFkMmItNGM4My1iMjQ1LTBkMjI3NWZkMDhmZiIsImNsaWVudF9pZCI6ImNsaWVudDEiLCJzY29wZSI6WyJyZWFkIl19.MLeAmL987rDhBgRRx9Gm9Gaa7yN3UqsUYIFd1UcK_xY",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJqb2huIiwic2NvcGUiOlsicmVhZCJdLCJhdGkiOiJhNWY5ZWE2OC0xZDJiLTRjODMtYjI0NS0wZDIyNzVmZDA4ZmYiLCJleHAiOjE2MTU2MTkyNjYsImF1dGhvcml0aWVzIjpbInJlYWQiXSwianRpIjoiYjY3M2JjNzMtZTQzYi00NmIzLTg2MDMtMTI2YmY4MjU0NTY1IiwiY2xpZW50X2lkIjoiY2xpZW50MSJ9.qx_szsHRg5pMw7uPt6afRkI0m3QsERjqsShx0lB3tmQ",
    "expires_in": 43199,
    "scope": "read",
    "jti": "a5f9ea68-1d2b-4c83-b245-0d2275fd08ff"
}
```

### Verify Token

```bash
curl --location --request GET 'http://localhost:8080/oauth/check_token?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MTMwNzA0NjYsInVzZXJfbmFtZSI6ImpvaG4iLCJhdXRob3JpdGllcyI6WyJyZWFkIl0sImp0aSI6ImE1ZjllYTY4LTFkMmItNGM4My1iMjQ1LTBkMjI3NWZkMDhmZiIsImNsaWVudF9pZCI6ImNsaWVudDEiLCJzY29wZSI6WyJyZWFkIl19.MLeAmL987rDhBgRRx9Gm9Gaa7yN3UqsUYIFd1UcK_xY'
```

```json
{
    "user_name": "john",
    "scope": [
        "read"
    ],
    "active": true,
    "exp": 1613070466,
    "authorities": [
        "read"
    ],
    "jti": "a5f9ea68-1d2b-4c83-b245-0d2275fd08ff",
    "client_id": "client1"
}
```

