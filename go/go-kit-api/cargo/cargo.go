package cargo

type TrackingID string

type Cargo struct {
	TrackingID TrackingID
}

type Repository interface {
	Store(cargo *Cargo) error
	Find(id TrackingID) (*Cargo, error)
	FindAll() []*Cargo
}
