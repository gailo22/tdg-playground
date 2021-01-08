package main

import (
	"fmt"
	"strings"
)

type Expression1 interface {
}

type DoubleExpression1 struct {
	value float64
}

type AdditionExpression1 struct {
	left, right Expression1
}

func Print(e Expression1, sb *strings.Builder) {
	if de, ok := e.(*DoubleExpression1); ok {
		sb.WriteString(fmt.Sprintf("%g", de.value))
	} else if ae, ok := e.(*AdditionExpression1); ok {
		sb.WriteRune('(')
		Print(ae.left, sb)
		sb.WriteRune('+')
		Print(ae.right, sb)
		sb.WriteRune(')')
	} // problem: additional code
}

// func main() {
// 	// 1 + (2+3)
// 	e := &AdditionExpression1{
// 		left: &DoubleExpression1{1},
// 		right: &AdditionExpression1{
// 			left:  &DoubleExpression1{2},
// 			right: &DoubleExpression1{3},
// 		},
// 	}

// 	sb := strings.Builder{}
// 	Print(e, &sb)
// 	fmt.Println(sb.String())
// }
