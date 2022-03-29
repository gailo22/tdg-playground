package ports

type ExprService interface {
	GetAddition(a, b int32) (int32, error)
}
