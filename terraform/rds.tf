# ----------------------------------------------------
# RDS
# ----------------------------------------------------

resource "aws_db_instance" "postgres" {

  allocated_storage    = 20
  engine               = "postgres"
  instance_class       = "db.t3.micro"
  db_name              = var.aws_rds_db
  username             = var.db_user
  password             = var.db_password
  skip_final_snapshot  = true
  publicly_accessible  = true

  vpc_security_group_ids = [aws_security_group.rds_sg.id]
  db_subnet_group_name = aws_db_subnet_group.rds_subnet_group.id

  tags = {
    Name = "RDS DevOps"
  }

}

resource "aws_db_subnet_group" "rds_subnet_group" {
  name       = "main"
  subnet_ids = [aws_subnet.ci_cd_subnet_1.id, aws_subnet.ci_cd_subnet_1.id]

  tags = {
    Name = "DB subnet group"
  }
}

# ----------------------------------------------------
# outputs
# ----------------------------------------------------

output "db_endpoint" {
  description = "RDS Endpoint"
  value       = aws_db_instance.postgres.endpoint
}

output "db_name" {
  description = "Database name"
  value       = aws_db_instance.postgres.db_name
}