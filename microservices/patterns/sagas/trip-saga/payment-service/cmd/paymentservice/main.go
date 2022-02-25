package main

import (
	"context"
	"fmt"
	"log"
	"payment/payment"
	"payment/service"
)

func main() {
	host, port := "localhost", "3000"
	ctx, err := service.Start(
		context.Background(),
		"Payment Service",
		host,
		port,
		payment.RegisterHandlers,
	)
	if err != nil {
		log.Fatal(err)
	}
	<-ctx.Done()
	fmt.Println("Shutting down payment service")
}
