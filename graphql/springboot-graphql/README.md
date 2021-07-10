# GraphQL Spring Boot

## Install graphql playground
```
$ brew install --cask graphql-playground
```

## open `http://localhost:8080/graphql` in playground

## Query
```json
{
  bookById(id: "book-1") {
    id
    name
    pageCount
    author {
      firstName
      lastName
    }
  }
}
```