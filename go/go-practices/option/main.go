package main

import (
	"time"
)

type options struct {
	timeout time.Duration
	caching bool
}

type Option interface {
	apply(*options)
}

type optionFunc func(*options)

func (f optionFunc) apply(o *options) {
	f(o)
}

func WithTimeout(t time.Duration) Option {
	return optionFunc(func(o *options) {
		o.timeout = t
	})
}

func WithCaching(cache bool) Option {
	return optionFunc(func(o *options) {
		o.caching = cache
	})
}

var defaultTimeout = 30 * time.Second
var defaultCaching = false

// Connect creates a connection.
func Connect(
	addr string,
	opts ...Option,
) (*Connection, error) {
	options := options{
		timeout: defaultTimeout,
		caching: defaultCaching,
	}

	for _, o := range opts {
		o.apply(&options)
	}

	// ...
	return nil, nil
}

type Connection struct{}

func main() {
	// db.Connect(addr)
	// db.Connect(addr, db.WithTimeout(newTimeout))
	// db.Connect(addr, db.WithCaching(false))
	// db.Connect(
	// 	addr,
	// 	db.WithCaching(false),
	// 	db.WithTimeout(newTimeout),
	// )
}
