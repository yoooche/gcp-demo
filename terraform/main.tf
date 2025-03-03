provider "google" {
  project = var.project_id
  region  = var.region
}

# VPC 網路設置
resource "google_compute_network" "vpc_network" {
  name                    = "springboot-vpc"
  auto_create_subnetworks = false
}

resource "google_compute_subnetwork" "subnet" {
  name          = "springboot-subnet"
  ip_cidr_range = "10.0.0.0/24"
  region        = var.region
  network       = google_compute_network.vpc_network.id
}

# 防火牆規則
resource "google_compute_firewall" "allow_http" {
  name    = "allow-http"
  network = google_compute_network.vpc_network.id

  allow {
    protocol = "tcp"
    ports    = ["8080"]
  }

  source_ranges = ["0.0.0.0/0"]
}

# VPC 連接器
resource "google_vpc_access_connector" "connector" {
  name          = "vpc-connector"
  region        = var.region
  ip_cidr_range = "10.8.0.0/28"
  network       = google_compute_network.vpc_network.id
}

# Cloud Run 服務
resource "google_cloud_run_service" "springboot_app" {
  name     = "springboot-app"
  location = var.region

  template {
    spec {
      containers {
        image = var.image_url
        ports {
          container_port = 8080
        }
      }
    }

    metadata {
      annotations = {
        "autoscaling.knative.dev/maxScale" = "10"
        "run.googleapis.com/vpc-access-connector" = google_vpc_access_connector.connector.name
      }
    }
  }

  traffic {
    percent         = 100
    latest_revision = true
  }

  depends_on = [google_vpc_access_connector.connector]
}

# IAM 設置公開訪問
resource "google_cloud_run_service_iam_member" "public_access" {
  service  = google_cloud_run_service.springboot_app.name
  location = google_cloud_run_service.springboot_app.location
  role     = "roles/run.invoker"
  member   = "allUsers"
}

# 輸出服務 URL
output "service_url" {
  value = google_cloud_run_service.springboot_app.status[0].url
}