package main

import (
	"context"
	"flight_booking/flight"
	"flight_booking/service"
	"fmt"
	"log"
)

func main() {
	host, port := "localhost", "3000"
	ctx, err := service.Start(
		context.Background(),
		"Flight Booking Service",
		host,
		port,
		flight.RegisterHandlers,
	)
	if err != nil {
		log.Fatal(err)
	}
	<-ctx.Done()
	fmt.Println("Shutting down flight booking service")
}
