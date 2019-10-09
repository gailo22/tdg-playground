provider "google" {
  credentials = file("../enterprise-architecture-d019c61310b6.json")

  project = "enterprise-architecture-252602"
  region  = "asia-southeast1"
  zone    = "asia-southeast1-b"
}

resource "google_compute_network" "vpc_network" {
  name = "terraform-network-1"
}

resource "google_compute_instance" "vm_instance" {
  name         = "terraform-instance-1"
  machine_type = "f1-micro"
  tags         = ["web", "dev"]

  boot_disk {
    initialize_params {
      image = "cos-cloud/cos-stable"
    }
  }

  network_interface {
    network = google_compute_network.vpc_network.name
    access_config {
    }
  }
}