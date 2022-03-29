package repositories

import (
	"go-hex/internal/core/ports"
	"time"

	"github.com/jmoiron/sqlx"
)

type exprRepo struct {
	db *sqlx.DB
}

type exprHistory struct {
	Date      time.Time `db:"date"`
	Answer    int       `db:"answer"`
	Operation string    `db:"operation"`
}

func NewExprRepo(db *sqlx.DB) ports.ExprRepository {
	return &exprRepo{db}
}

func (r exprRepo) CloseDbConnection() {
	r.db.Close()
}

func (r exprRepo) AddToHistory(answer int32, operation string) error {
	history := exprHistory{
		Date:      time.Now().UTC(),
		Answer:    int(answer),
		Operation: operation,
	}
	tx := r.db.MustBegin()
	_, err := r.db.NamedExec(`INSERT INTO expr_history (date, answer, operation) VALUES (:date, :answer, :operation)`, history)
	tx.Commit()

	if err != nil {
		return err
	}

	return nil
}
