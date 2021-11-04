package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"

	customer "go-practices/v1/goroutine1/customer"
)

var cache = map[int]customer.Customer{}
var rnd = rand.New(rand.NewSource(time.Now().UnixNano()))

func main() {
	wg := &sync.WaitGroup{}
	m := &sync.RWMutex{}
	cacheCh := make(chan customer.Customer)
	dbCh := make(chan customer.Customer)

	for i := 0; i < 10; i++ {
		id := rnd.Intn(10) + 1
		wg.Add(2)

		go func(id int, wg *sync.WaitGroup, m *sync.RWMutex, ch chan<- customer.Customer) {
			if c, ok := queryFromCache(id, m); ok {
				ch <- c
			}
			wg.Done()
		}(id, wg, m, cacheCh)

		go func(id int, wg *sync.WaitGroup, m *sync.RWMutex, ch chan<- customer.Customer) {
			if c, ok := queryFromDb(id); ok {
				m.Lock()
				cache[id] = c
				m.Unlock()
				ch <- c
			}
			wg.Done()
		}(id, wg, m, dbCh)

		go func(cacheCh, dbCh <-chan customer.Customer) {
			select {
			case c := <-cacheCh:
				fmt.Println("From cache:")
				fmt.Println(c)
				<-dbCh
			case c := <-dbCh:
				fmt.Println("From db:")
				fmt.Println(c)
			}
		}(cacheCh, dbCh)
		time.Sleep(150 * time.Millisecond)
	}
	wg.Wait()
}

func queryFromCache(id int, m *sync.RWMutex) (customer.Customer, bool) {
	m.RLock()
	c, ok := cache[id]
	m.RUnlock()
	return c, ok
}

func queryFromDb(id int) (customer.Customer, bool) {
	time.Sleep(100 * time.Millisecond)
	for _, c := range customer.Customers {
		if c.ID == id {
			return c, true
		}
	}
	return customer.Customer{}, false
}
