# ----------------------------------------------------
# ALB
# ----------------------------------------------------

resource "aws_lb" "alb" {
    name = "devops-ecs-lb"
    load_balancer_type = "application"
    security_groups = [aws_security_group.alb_sg.id]
    subnets = [aws_subnet.ci_cd_subnet_1.id, aws_subnet.ci_cd_subnet_2.id]

    tags = {
        Name = "DevOps ALB"
    }
}


# ----------------------------------------------------
# TARGET GROUPS
# ----------------------------------------------------

#Defining the target group and a health check on the application
resource "aws_lb_target_group" "be_target_group" {
  name                      = "be-tg"
  port                      = var.be_ip_port
  protocol                  = "HTTP"
  target_type               = "ip"
  vpc_id                    = aws_vpc.ci_cd_vpc.id
  health_check {
      path                  = "/actuator/health"
      protocol              = "HTTP"
      matcher               = "200"
      port                  = "traffic-port"
      healthy_threshold     = 2
      unhealthy_threshold   = 2
      timeout               = 10
      interval              = 30
  }
}


# ----------------------------------------------------
# LISTENERS
# ----------------------------------------------------

#Defines an HTTP Listener for the ALB
resource "aws_lb_listener" "be_listener" {
  load_balancer_arn         = aws_lb.alb.arn
  port                      = var.be_ip_port
  protocol                  = "HTTP"

  default_action {
    type                    = "forward"
    target_group_arn        = aws_lb_target_group.be_target_group.arn
  }
}


# ----------------------------------------------------
# outputs
# ----------------------------------------------------

output "alb_dns" {
    value = aws_lb.alb.dns_name
}