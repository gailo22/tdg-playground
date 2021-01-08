package main

import (
	"fmt"
	"strings"
)

// double dispatch

type ExpressionVisitor interface {
	VisitDoubleExpression(e *DoubleExpression2)
	VisitAdditionExpression(e *AdditionExpression2)
}

type Expression2 interface {
	Accept(ev ExpressionVisitor)
}

type DoubleExpression2 struct {
	value float64
}

func (d *DoubleExpression2) Accept(ev ExpressionVisitor) {
	ev.VisitDoubleExpression(d)
}

type AdditionExpression2 struct {
	left, right Expression2
}

func (d *AdditionExpression2) Accept(ev ExpressionVisitor) {
	ev.VisitAdditionExpression(d)
}

type ExpressionPrinter struct {
	sb strings.Builder
}

func NewExpressionPrinter() *ExpressionPrinter {
	return &ExpressionPrinter{strings.Builder{}}
}

func (ep *ExpressionPrinter) VisitDoubleExpression(e *DoubleExpression2) {
	ep.sb.WriteString(fmt.Sprintf("%g", e.value))
}

func (ep *ExpressionPrinter) VisitAdditionExpression(e *AdditionExpression2) {
	ep.sb.WriteRune('(')
	e.left.Accept(ep)
	ep.sb.WriteRune('+')
	e.right.Accept(ep)
	ep.sb.WriteRune(')')
}

func (ep *ExpressionPrinter) String() string {
	return ep.sb.String()
}

type ExpressionEvaluator struct {
	result float64
}

func (ee *ExpressionEvaluator) VisitDoubleExpression(e *DoubleExpression2) {
	ee.result = e.value
}

func (ee *ExpressionEvaluator) VisitAdditionExpression(e *AdditionExpression2) {
	e.left.Accept(ee)
	x := ee.result
	e.right.Accept(ee)
	x += ee.result
	ee.result = x
}

func main() {
	// 1 + (2+3) = 6
	e := &AdditionExpression2{
		left: &DoubleExpression2{1},
		right: &AdditionExpression2{
			left:  &DoubleExpression2{2},
			right: &DoubleExpression2{3},
		},
	}
	ep := NewExpressionPrinter()
	e.Accept(ep)
	fmt.Println(ep.String())

	ee := &ExpressionEvaluator{}
	e.Accept(ee)
	fmt.Printf("%s = %g", ep, ee.result)
}
