
# ------------------------------------------------------------------------------
# ECS role policy
# ------------------------------------------------------------------------------

data "aws_iam_role" "execution_role" {
  name = "LabRole"
}

output "ecs_role" {
  value = data.aws_iam_role.execution_role.arn
}


# ----------------------------------------------------
# ECS
# ----------------------------------------------------

resource "aws_ecs_cluster" "app_cluster" {
  name = "devops-cluster"
}

resource "aws_ecs_service" "app_service" {
    name            = "app-service"
    cluster         = aws_ecs_cluster.app_cluster.id
    task_definition = aws_ecs_task_definition.app_task.arn
    desired_count   = 2

    launch_type = "FARGATE"

    network_configuration {
        subnets             = [aws_subnet.ci_cd_subnet_1.id, aws_subnet.ci_cd_subnet_2.id]
        assign_public_ip    = true
        security_groups     = [aws_security_group.backend_sg.id]
    }


    load_balancer {
        target_group_arn                    = aws_lb_target_group.be_target_group.arn 
        container_name                      = "devops-container"
        container_port                      = var.be_ip_port
    }
    depends_on                            = [aws_lb_listener.be_listener]
    
    availability_zone_rebalancing = "ENABLED"

}

resource "aws_ecs_task_definition" "app_task" {
    family                   = "app-task"
    network_mode             = "awsvpc"
    requires_compatibilities = ["FARGATE"]
    cpu                      = 1024
    memory                   = 3072
    execution_role_arn       = data.aws_iam_role.execution_role.arn
    task_role_arn            = data.aws_iam_role.execution_role.arn

    container_definitions    = jsonencode([
        {
          name                      = "devops-container"
          image                     = "${var.aws_ecr_client_id}.dkr.ecr.us-east-1.amazonaws.com/${var.aws_ecr_be_repo_name}:latest"
          essential                 = true
          networkMode               = "awsvpc"
          portMappings =                 [
                                              {
                                                  containerPort = tonumber(var.be_ip_port)
                                                  hostPort      = tonumber(var.be_ip_port)
                                              }
                                            ]


          environment = [
                {
                    name = "DB_ENDPOINT"
                    value =  aws_db_instance.postgres.endpoint
                },
                {
                    name = "DB_NAME"
                    value =  var.aws_rds_db
                },
                {
                    name = "DB_USER"
                    value =  var.db_user
                },
                {
                    name = "DB_PASSWORD"
                    value =  var.db_password
                },
                {
                    name = "PROFILE"
                    value =  "prod"
                }
                ]

        }
      ])
}

