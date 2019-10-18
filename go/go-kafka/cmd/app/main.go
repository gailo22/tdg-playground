package main

import (
	"fmt"
	"log"
	"os"
	"os/signal"
	"sync"

	"github.com/gailo22/tdg-playground/go/go-kafka/pkg/util"

	"github.com/Shopify/sarama"
)

func main() {

	fmt.Println(util.Hello())

	config := sarama.NewConfig()
	config.Producer.Return.Successes = true
	producer, err := sarama.NewAsyncProducer([]string{"localhost:9092"}, config)
	if err != nil {
		panic(err)
	}

	consumer, err := sarama.NewConsumer([]string{"localhost:9092"}, config)
	if err != nil {
		panic(err)
	}

	fmt.Println(consumer)

	// Trap SIGINT to trigger a graceful shutdown.
	signals := make(chan os.Signal, 1)
	signal.Notify(signals, os.Interrupt)

	var (
		wg                   sync.WaitGroup
		_, successes, errors int
	)

	wg.Add(1)
	go func() {
		defer wg.Done()
		for range producer.Successes() {
			successes++
		}
	}()

	wg.Add(1)
	go func() {
		defer wg.Done()
		for err := range producer.Errors() {
			log.Println(err)
			errors++
		}
	}()

}
