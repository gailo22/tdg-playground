# Getting Started

### Endpoints

* /hello

### Call Api
```
export TOKEN=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MTMwNzA0NjYsInVzZXJfbmFtZSI6ImpvaG4iLCJhdXRob3JpdGllcyI6WyJyZWFkIl0sImp0aSI6ImE1ZjllYTY4LTFkMmItNGM4My1iMjQ1LTBkMjI3NWZkMDhmZiIsImNsaWVudF9pZCI6ImNsaWVudDEiLCJzY29wZSI6WyJyZWFkIl19.MLeAmL987rDhBgRRx9Gm9Gaa7yN3UqsUYIFd1UcK_xY
curl -H "Authorization: Bearer $TOKEN" localhost:2222/hello -v
```

```
Hello, World
```

