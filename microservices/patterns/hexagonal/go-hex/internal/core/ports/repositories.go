package ports

type ExprRepository interface {
	CloseDbConnection()
	AddToHistory(answer int32, operation string) error
}
