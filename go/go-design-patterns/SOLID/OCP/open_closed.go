package main

// OCP
// open for extension, closed for modification
// Specification

type Color int

const (
	red Color = iota
	green
	blue
)

type Size int

const (
	small Size = iota
	medium
	large
)

type Filter struct {
	//
}

func (f *Filter) FilterByColor(products []Product, color Color) []*Product {
	result := make([]*Product, 0)
	for i, v := range products {
		if v.color == color {
			result = append(result, &products[i])
		}
	}
	return result
}

func (f *Filter) FilterBySize(products []Product, size Size) []*Product {
	result := make([]*Product, 0)
	for i, v := range products {
		if v.size == size {
			result = append(result, &products[i])
		}
	}
	return result
}

func (f *Filter) FilterBySizeAndColor(products []Product, size Size, color Color) []*Product {
	result := make([]*Product, 0)
	for i, v := range products {
		if v.size == size && v.color == color {
			result = append(result, &products[i])
		}
	}
	return result
}

type Product struct {
	name  string
	color Color
	size  Size
}

// -----
type Specification interface {
	IsSatified(p *Product) bool
}

type ColorSpecification struct {
	color Color
}

func (c ColorSpecification) IsSatified(p *Product) bool {
	return p.color == c.color
}

type SizeSpecification struct {
	size Size
}

func (s SizeSpecification) IsSatified(p *Product) bool {
	return p.size == s.size
}

type AndSpecification struct {
	first, second Specification
}

func (a AndSpecification) IsSatified(p *Product) bool {
	return a.first.IsSatified(p) && a.second.IsSatified(p)
}

type BetterFilter struct{}

func (f *BetterFilter) Filter(products []Product, spec Specification) []*Product {
	result := make([]*Product, 0)
	for i, v := range products {
		if spec.IsSatified(&v) {
			result = append(result, &products[i])
		}
	}
	return result
}

func main() {

}
