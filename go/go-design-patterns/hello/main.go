package main

import (
	"fmt"
	"os"
)

func main() {

	key := os.Getenv("KEY")
	value := os.Getenv("VALUE")
	shell := os.Getenv("SHELL")

	fmt.Println(key, " ", value, shell)
}
