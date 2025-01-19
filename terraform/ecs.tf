#resource "aws_ecr_repository" "app_repo" {
#  name = "my-app"
#}
#
#resource "aws_ecs_cluster" "app_cluster" {
#  name = "app-cluster"
#}
#
#resource "aws_ecs_task_definition" "app_task" {
#  family                   = "app-task"
#  network_mode             = "awsvpc"
#  requires_compatibilities = ["FARGATE"]
#  cpu                      = "256" # 0.25 vCPU
#  memory                   = "512" # 512MB RAM
#
#  container_definitions = jsonencode([{
#    name      = "my-app-container"
#    image     = "${aws_ecr_repository.app_repo.repository_url}:latest"
#    cpu       = 256
#    memory    = 512
#    essential = true
#    portMappings = [{
#      containerPort = 80
#      hostPort      = 80
#    }]
#  }])
#}
#
#resource "aws_ecs_service" "app_service" {
#  name            = "app-service"
#  cluster         = aws_ecs_cluster.app_cluster.id
#  task_definition = aws_ecs_task_definition.app_task.arn
#  desired_count   = 1
#
#  launch_type = "FARGATE"
#
#  network_configuration {
#    subnets         = [aws_subnet.ci_cd_subnet.id]
#    security_groups = [aws_security_group.jenkins_sg.id]
#  }
#}
#
#output "ecr_repository_url" {
#  value = aws_ecr_repository.app_repo.repository_url
#}
#
#output "ecs_service_name" {
#  value = aws_ecs_service.app_service.name
#}