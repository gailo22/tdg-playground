package hotel

import (
	"net/http"
)

func RegisterHandlers() {
	handler := new(flightBookingHandler)
	http.Handle("/hotels", handler)
	http.Handle("/hotels/{id}", handler)
}

type flightBookingHandler struct{}

func (f flightBookingHandler) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	w.Write([]byte("hotel booking service api"))
}
