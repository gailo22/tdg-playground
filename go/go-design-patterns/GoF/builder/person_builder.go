package main

type Person1 struct {
	StreetAddress, Postcode, City string

	CompanyName, Position string
	AnnualIncome          int
}

type PersonBuilder1 struct {
	person *Person1
}

func NewPersonBuilder() *PersonBuilder1 {
	return &PersonBuilder1{&Person1{}}
}
