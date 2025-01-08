# RDS PostgreSQL instance
resource "aws_db_instance" "postgres" {
  allocated_storage    = 20                     # Storage size in GB
  engine               = "postgres"             # Database engine
#  engine_version       = "13.4"                 # PostgreSQL version (example)
  instance_class       = "db.t3.micro"          # Change instance type as needed
  db_name              = "mydatabase"           # Database name
  username             = "postgres"                # Database admin username
  password             = "password"             # Database admin password (set securely)
#  parameter_group_name = "default.postgres13"   # Parameter group for PostgreSQL
  skip_final_snapshot  = true                   # Set to false in production
  publicly_accessible  = true                   # Set to false for private access

  vpc_security_group_ids = [aws_security_group.rds-sg.id]

  tags = {
    Name = "my-postgresql-db"
  }
}

output "db_endpoint" {
  description = "RDS Endpoint"
  value       = aws_db_instance.postgres.endpoint
}

output "db_name" {
  description = "Database name"
  value       = aws_db_instance.postgres.db_name
}