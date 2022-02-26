package tracking

import (
	"gokit_api/cargo"
	"time"

	"github.com/go-kit/log"
)

type Service interface {
	Track(id string) (Cargo, error)
}

type service struct {
	cargos cargo.Repository
}

type Cargo struct {
	TrackingID string `json:"tracking_id"`
}

func (s *service) Track(id string) (Cargo, error) {
	//...
	return Cargo{}, nil
}

func NewService(cargos cargo.Repository) Service {
	return &service{
		cargos: cargos,
	}
}

type loggingService struct {
	logger log.Logger
	Service
}

func NewLoggingService(logger log.Logger, s Service) Service {
	return &loggingService{logger, s}
}

func (s *loggingService) Track(id string) (c Cargo, err error) {
	defer func(begin time.Time) {
		s.logger.Log("method", "track", "tracking_id", id, "took", time.Since(begin), "err", err)
	}(time.Now())
	return s.Service.Track(id)
}

// var service Service
// service = NewService(...)
// service = NewLoggingService(logger, service)
// service = NewInstrumentingService(..., service)
