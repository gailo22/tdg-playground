package 

import "fmt"

type  struct {
	ID   int
	Name string
}

func (c ) String() string {
	return fmt.Sprintf(
		"ID:\t%v\n"+
			"Name:\t%q\n", c.ID, c.Name)
}

var Customers = []Customer{
	{
		ID:   1,
		Name: "John",
	},
	{
		ID:   2,
		Name: "Jane",
	},
	{
		ID:   3,
		Name: "Mark",
	},
	{
		ID:   4,
		Name: "Richard",
	},
	{
		ID:   5,
		Name: "Anne",
	},
	{
		ID:   6,
		Name: "Jin",
	},
	{
		ID:   7,
		Name: "Anand",
	},
	{
		ID:   8,
		Name: "Suzy",
	},
	{
		ID:   9,
		Name: "Lydia",
	},
	{
		ID:   10,
		Name: "Scarlet",
	},
}
