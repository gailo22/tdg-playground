package exprsrv

import (
	"go-hex/internal/core/domain"
	"go-hex/internal/core/ports"
)

type service struct {
	expr domain.Expr
	repo ports.ExprRepository
}

func NewExprService(expr domain.Expr, repo ports.ExprRepository) ports.ExprService {
	return &service{
		expr: expr,
		repo: repo,
	}
}

func (s service) GetAddition(a, b int32) (int32, error) {
	answer, err := s.expr.Addition(a, b)
	if err != nil {
		return 0, err
	}

	err = s.repo.AddToHistory(answer, "addition")
	if err != nil {
		return 0, err
	}

	return answer, nil
}
