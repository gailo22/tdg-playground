package main

import (
	"go-rest-api/handlers"

	"github.com/gin-gonic/gin"
)

func main() {
	r := gin.Default()
	r.GET("/ping", handlers.Ping)
	r.Run() // listen and serve on 0.0.0.0:8080
}
