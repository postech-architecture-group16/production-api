resource "kubernetes_namespace" "production_namespace" {
  metadata {
    name = "production"
  }
}

resource "kubernetes_secret" "production_secret" {
  metadata {
    name      = "production-secret"
    namespace = kubernetes_namespace.production_namespace.metadata[0].name
  }

  data = {
    aws_access_key_id     = var.AWS_ACCESS_KEY_ID
    aws_secret_access_key = var.AWS_SECRET_ACCESS_KEY
    aws_session_token     = var.AWS_SESSION_TOKEN
    db_url                = data.aws_ssm_parameter.db_url.value
    db_username           = local.db_credentials.username
    db_password           = local.db_credentials.password
  }

  type = "Opaque"

  depends_on = [kubernetes_namespace.production_namespace]
}

resource "kubernetes_deployment" "production_deployment" {
  metadata {
    name      = "production-api"
    namespace = kubernetes_namespace.production_namespace.metadata[0].name
    labels = {
      app = "production-api"
    }
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "production-api"
      }
    }

    template {
      metadata {
        labels = {
          app = "production-api"
        }
      }

      spec {
        container {
          image             = data.aws_ecr_image.latest_image.image_uri
          name              = "production-api"
          image_pull_policy = "Always"

          resources {
            limits = {
              cpu    = "500m"
              memory = "1Gi"
            }
            requests = {
              cpu    = "250m"
              memory = "512Mi"
            }
          }

          liveness_probe {
            http_get {
              path = "/production-api/actuator/health"
              port = var.server_port
            }
            initial_delay_seconds = 60
            period_seconds        = 30
            timeout_seconds       = 5
            failure_threshold     = 3
          }

          readiness_probe {
            http_get {
              path = "/production-api/actuator/health"
              port = var.server_port
            }
            initial_delay_seconds = 60
            period_seconds        = 10
            timeout_seconds       = 3
            failure_threshold     = 1
          }

          env {
            name  = "SPRING_PROFILES_ACTIVE"
            value = "default"
          }

          env {
            name = "POSTGRES_HOST"
            value_from {
              secret_key_ref {
                name = "production-secret"
                key  = "db_url"
              }
            }
          }

          env {
            name = "POSTGRES_USER"
            value_from {
              secret_key_ref {
                name = "production-secret"
                key  = "db_username"
              }
            }
          }

          env {
            name = "POSTGRES_PASSWORD"
            value_from {
              secret_key_ref {
                name = "production-secret"
                key  = "db_password"
              }
            }
          }

          env {
            name = "AWS_ACCESS_KEY_ID"
            value_from {
              secret_key_ref {
                name = "production-secret"
                key  = "aws_access_key_id"
              }
            }
          }

          env {
            name = "AWS_SECRET_ACCESS_KEY"
            value_from {
              secret_key_ref {
                name = "production-secret"
                key  = "aws_secret_access_key"
              }
            }
          }

          env {
            name = "AWS_SESSION_TOKEN"
            value_from {
              secret_key_ref {
                name = "production-secret"
                key  = "aws_session_token"
              }
            }
          }

          env {
            name  = "QUEUE_NAME_CONSUMER"
            value = var.queue_name_consumer
          }

          env {
            name  = "AWS_REGION"
            value = "us-east-1"
          }
        }
      }
    }
  }

  timeouts {
    create = "4m"
    update = "4m"
    delete = "4m"
  }

  depends_on = [kubernetes_secret.production_secret]
}

resource "kubernetes_service" "production_service" {
  metadata {
    name      = "production-api-service"
    namespace = kubernetes_namespace.production_namespace.metadata[0].name
  }

  spec {
    selector = {
      app = "production-api"
    }

    port {
      port        = var.server_port
      target_port = var.server_port
    }

    cluster_ip = "None"
  }
}

resource "kubernetes_ingress_v1" "production_ingress" {
  metadata {
    name      = "production-api-ingress"
    namespace = kubernetes_namespace.production_namespace.metadata[0].name

    annotations = {
      "nginx.ingress.kubernetes.io/x-forwarded-port" = "true"
      "nginx.ingress.kubernetes.io/x-forwarded-host" = "true"
    }
  }

  spec {
    ingress_class_name = "nginx"

    rule {
      http {
        path {
          path      = "/production-api"
          path_type = "Prefix"

          backend {
            service {
              name = "production-api-service"
              port {
                number = var.server_port
              }
            }
          }
        }
      }
    }
  }

  depends_on = [kubernetes_service.production_service]

}

resource "kubernetes_horizontal_pod_autoscaler_v2" "production_hpa" {
  metadata {
    name      = "production-api-hpa"
    namespace = kubernetes_namespace.production_namespace.metadata[0].name
  }

  spec {
    scale_target_ref {
      api_version = "apps/v1"
      kind        = "Deployment"
      name        = "production-api"
    }

    min_replicas = 1
    max_replicas = 5

    metric {
      type = "Resource"

      resource {
        name = "cpu"
        target {
          type                = "Utilization"
          average_utilization = 75
        }
      }
    }
  }

  depends_on = [kubernetes_service.production_service]

}