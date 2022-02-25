package payment

import (
	"net/http"
)

func RegisterHandlers() {
	handler := new(paymentHandler)
	http.Handle("/payments", handler)
	http.Handle("/payments/{id}", handler)
}

type paymentHandler struct{}

func (p paymentHandler) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	w.Write([]byte("payment service api"))
}
