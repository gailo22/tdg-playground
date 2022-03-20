package main

import (
	"log"
	"net/http"
)

func main() {
	hub := H
	go hub.Run()
	http.HandleFunc("/ws", func(w http.ResponseWriter, r *http.Request) {
		ServeWs(w, r)
	})

	log.Fatal(http.ListenAndServe(":8080", nil))
}
