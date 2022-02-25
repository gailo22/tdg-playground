package main

import (
	"context"
	"fmt"
	"hotel_booking/hotel"
	"hotel_booking/service"
	"log"
)

func main() {
	host, port := "localhost", "3000"
	ctx, err := service.Start(
		context.Background(),
		"Hotel Booking Service",
		host,
		port,
		hotel.RegisterHandlers,
	)
	if err != nil {
		log.Fatal(err)
	}
	<-ctx.Done()
	fmt.Println("Shutting down hotel booking service")
}
