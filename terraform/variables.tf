variable "project_id" {
  description = "GCP 專案 ID"
  type        = string
}

variable "region" {
  description = "GCP 區域"
  type        = string
  default     = "asia-east1"
}

variable "image_url" {
  description = "Docker 映像的完整 URL"
  type        = string
}