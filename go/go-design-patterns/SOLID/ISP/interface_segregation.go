package main

type Document struct {
}

type Machine interface {
	Print(d Document)
	Fax(d Document)
	Scan(d Document)
}

type MultiFunctionPrinter struct {
}

func (m MultiFunctionPrinter) Print(d Document) {

}

func (m MultiFunctionPrinter) Fax(d Document) {

}

func (m MultiFunctionPrinter) Scan(d Document) {

}

type OldFashionedPrinter struct {
}

func (o OldFashionedPrinter) Print(d Document) {
	// ok
}

func (o OldFashionedPrinter) Fax(d Document) {
	panic("Operation not supported")
}

func (o OldFashionedPrinter) Scan(d Document) {
	panic("Operation not supported")
}

//---------
type Printer interface {
	Print(d Document)
}

type Scanner interface {
	Scan(d Document)
}

type MyPrinter struct{}

func (m MyPrinter) Print(d Document) {

}

type Photocopier struct{}

func (p Photocopier) Print(d Document) {

}

func (p Photocopier) Scan(d Document) {

}

type MultiFunctionDevice interface {
	Printer
	Scanner
	// Fax
}

// decorator
type MultiFunctionMachine struct {
	printer Printer
	scanner Scanner
}

func (m MultiFunctionMachine) Print(d Document) {
	m.printer.Print(d)
}

func main() {
	d := Document{}
	ofp := OldFashionedPrinter{}
	ofp.Fax(d)

	mp := MyPrinter{}
	mp.Print(d)

}
