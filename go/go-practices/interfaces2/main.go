package main

import "fmt"

type Shape interface {
	GetPerimeter() uint
	GetArea() uint
}

type Square struct {
	Cache
	side uint
}

func (s *Square) GetPerimeter() uint {
	value := s.GetValue("perimeter")
	if value == -1 {
		value = int(s.side * 4)
		s.SetValue("perimeter", uint(value))
	}

	return uint(value)
}

func (s *Square) GetArea() uint {
	value := s.GetValue("area")
	if value == -1 {
		value = int(s.side * s.side)
		s.SetValue("area", uint(value))
	}

	return uint(value)
}

type Rectangle struct {
	Cache
	width  uint
	height uint
}

func (r *Rectangle) GetPerimeter() uint {
	value := r.GetValue("perimeter")
	if value == -1 {
		value = int(r.width+r.height) * 2
		r.SetValue("perimeter", uint(value))
	}

	return uint(value)
}

func (r *Rectangle) GetArea() uint {
	value := r.GetValue("area")
	if value == -1 {
		value = int(r.width * r.height)
		r.SetValue("area", uint(value))
	}

	return uint(value)
}

type Cache struct {
	cache map[string]uint
}

func (c *Cache) GetValue(name string) int {
	value, ok := c.cache[name]
	if ok {
		fmt.Println("cache hit")
		return int(value)
	} else {
		fmt.Println("cache miss")
		return -1
	}
}

func (c *Cache) SetValue(name string, value uint) {
	c.cache[name] = value
}

func main() {
	shapes := []Shape{
		&Square{Cache{cache: make(map[string]uint)}, 2},
		&Rectangle{Cache{cache: make(map[string]uint)}, 3, 5},
	}
	var totalArea uint
	for _, shape := range shapes {
		totalArea += shape.GetArea()
	}

	fmt.Println("Total area: ", totalArea)

	totalArea = 0
	for _, shape := range shapes {
		totalArea += shape.GetArea()
	}

	fmt.Println("Total area: ", totalArea)
}
