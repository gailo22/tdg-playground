package domain

type Expr struct {
}

func New() *Expr {
	return &Expr{}
}

func (e Expr) Addition(a int32, b int32) (int32, error) {
	return a + b, nil
}
