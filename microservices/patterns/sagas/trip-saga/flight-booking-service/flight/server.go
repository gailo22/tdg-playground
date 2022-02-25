package flight

import (
	"net/http"
)

func RegisterHandlers() {
	handler := new(flightBookingHandler)
	http.Handle("/flights", handler)
	http.Handle("/flights/{id}", handler)
}

type flightBookingHandler struct{}

func (f flightBookingHandler) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	w.Write([]byte("flight booking service api"))
}
