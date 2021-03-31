package main

import (
    "github.com/gofiber/fiber"
)

func helloworld(c *fiber.Ctx)  {
    c.Send("Hello, world")
}

func main() {
    app := fiber.New()

    app.Get("/", helloworld)

    app.Listen(3000)
}