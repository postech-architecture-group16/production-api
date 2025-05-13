data "aws_eks_cluster" "eks_cluster" {
  name = var.eks_cluster_name
}

data "aws_eks_cluster_auth" "eks_cluster" {
  name = data.aws_eks_cluster.eks_cluster.name
}

data "aws_ecr_repository" "ecr_repo" {
  name = var.ecr_repository_name
}

data "aws_ecr_image" "latest_image" {
  repository_name = data.aws_ecr_repository.ecr_repo.name
  image_tag       = "latest"
}

data "aws_ssm_parameter" "db_url" {
  name = "/fiap-tech-challenge/production-rds-endpoint"
}

data "aws_secretsmanager_secret_version" "db_credentials" {
  secret_id = var.secret_name
}