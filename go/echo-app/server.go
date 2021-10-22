package main

import (
    "net/http"

    "github.com/labstack/echo/v4"
    middleware "github.com/labstack/echo/v4/middleware"
)

// e.GET("/users/:id", getUser)
func getUser(c echo.Context) error {
    // User ID from path `users/:id`
    id := c.Param("id")
  return c.String(http.StatusOK, id)
}

//e.GET("/show", show)
func show(c echo.Context) error {
	// Get team and member from the query string
	team := c.QueryParam("team")
	member := c.QueryParam("member")
	return c.String(http.StatusOK, "team:" + team + ", member:" + member)
}

// e.POST("/save", save)
func save(c echo.Context) error {
	// Get name and email
	name := c.FormValue("name")
	email := c.FormValue("email")
	return c.String(http.StatusOK, "name:" + name + ", email:" + email)
}

type User struct {
	Name  string `json:"name" xml:"name" form:"name" query:"name"`
	Email string `json:"email" xml:"email" form:"email" query:"email"`
}

// e.POST("/users", saveUser)
func saveUser(c echo.Context) error {
	u := new(User)
	if err := c.Bind(u); err != nil {
		return err
	}
	return c.JSON(http.StatusCreated, u)
	// or
	// return c.XML(http.StatusCreated, u)
}

func main()  {
    e := echo.New()
    e.GET("/", func(c echo.Context) error {
        return c.String(http.StatusOK, "Hello, World")
    })

    e.POST("/users", saveUser)
    e.GET("/users/:id", getUser)
    // e.PUT("/users/:id", updateUser)
    // e.DELETE("/users/:id", deleteUser)
    e.GET("/show", show)
    e.POST("/save", save)

    e.Static("/static", "static")

    // Root level middleware
    e.Use(middleware.Logger())
    e.Use(middleware.Recover())

    // Group level middleware
    g := e.Group("/admin")
    g.Use(middleware.BasicAuth(func(username, password string, c echo.Context) (bool, error) {
    if username == "joe" && password == "secret" {
        return true, nil
    }
    return false, nil
    }))

    // Route level middleware
    track := func(next echo.HandlerFunc) echo.HandlerFunc {
        return func(c echo.Context) error {
            println("request to /users")
            return next(c)
        }
    }
    e.GET("/users", func(c echo.Context) error {
        return c.String(http.StatusOK, "/users")
    }, track)

    e.Logger.Fatal(e.Start(":1323"))
}