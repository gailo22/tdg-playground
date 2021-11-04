package customer

import "fmt"

type Customer struct {
	ID   int
	Name string
}

func (c Customer) String() string {
	return fmt.Sprintf(
		"ID:\t%v\n"+
			"Name:\t%q\n", c.ID, c.Name)
}

var Customers = []Customer{
	Customer{
		ID:   1,
		Name: "John",
	},
	Customer{
		ID:   2,
		Name: "Jane",
	},
	Customer{
		ID:   3,
		Name: "Mark",
	},
	Customer{
		ID:   4,
		Name: "Richard",
	},
	Customer{
		ID:   5,
		Name: "Anne",
	},
	Customer{
		ID:   6,
		Name: "Jin",
	},
	Customer{
		ID:   7,
		Name: "Anand",
	},
	Customer{
		ID:   8,
		Name: "Suzy",
	},
	Customer{
		ID:   9,
		Name: "Lydia",
	},
	Customer{
		ID:   10,
		Name: "Scarlet",
	},
}
