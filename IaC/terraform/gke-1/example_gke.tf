provider "google" {
  credentials = file("../enterprise-architecture-d019c61310b6.json")

  project = "enterprise-architecture-252602"
  region  = "asia-southeast1"
  zone    = "asia-southeast1-b"
}

resource "google_compute_network" "vpc_network" {
  name = "terraform-network-1"
}

resource "google_container_cluster" "primary" {
  name     = "my-gke-cluster-02"
  location = "asia-southeast1"

  # We can't create a cluster with no node pool defined, but we want to only use
  # separately managed node pools. So we create the smallest possible default
  # node pool and immediately delete it.
  remove_default_node_pool = true
  initial_node_count = 1

  master_auth {
    username = ""
    password = ""

    client_certificate_config {
      issue_client_certificate = false
    }
  }
}

resource "google_container_node_pool" "primary_preemptible_nodes" {
  name       = "my-node-pool"
  location   = "asia-southeast1"
  cluster    = "${google_container_cluster.primary.name}"
  node_count = 1

  node_config {
    preemptible  = true
    machine_type = "n1-standard-1"

    metadata = {
      disable-legacy-endpoints = "true"
    }

    oauth_scopes = [
      "https://www.googleapis.com/auth/logging.write",
      "https://www.googleapis.com/auth/monitoring",
    ]
  }
}