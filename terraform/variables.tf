variable "aws_region" {
  type        = string
  default     = "us-east-1"
  description = "AWS Account region"
}

variable "eks_cluster_name" {
  type        = string
  default     = "fiap-tech-challenge-eks-cluster"
  description = "EKS Cluster name"
}

variable "ecr_repository_name" {
  type        = string
  default     = "production-api"
  description = "AWS ECR repository name"
}

variable "server_port" {
  type        = number
  default     = 8092
  description = "Production App server port"
}

variable "rds_instance_name" {
  type        = string
  default     = "production_db"
  description = "Rds instance name"
}

variable "AWS_ACCESS_KEY_ID" {
  type        = string
  description = "aws_access_key_id"
}

variable "AWS_SECRET_ACCESS_KEY" {
  type        = string
  description = "aws_secret_access_key"
}

variable "AWS_SESSION_TOKEN" {
  type        = string
  description = "aws_session_token"
}

variable "secret_name" {
  type        = string
  default     = "tech-challenge-production-database-credentials"
  description = "secret_name"
}

variable "database_port" {
  type        = string
  default     = "5432"
  description = "value of the database port"
}

variable "queue_name_consumer" {
  type        = string
  default     = "order-production"
  description = "Order to production consumer MQ Queue"
}
