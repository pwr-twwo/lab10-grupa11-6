# ----------------------------------------------------
# VPC + subnets
# ----------------------------------------------------


resource "aws_vpc" "ci_cd_vpc" {
  cidr_block            = "10.0.0.0/16"
  enable_dns_support    = true
  enable_dns_hostnames  = true

  tags = {
    Name = "CI/CD VPS"
  }
}

# potrzebne gdy tworzymy własną vpc 
resource "aws_subnet" "ci_cd_subnet_1" {
  vpc_id                  = aws_vpc.ci_cd_vpc.id
  cidr_block              = "10.0.1.0/24"
  map_public_ip_on_launch = true
}

resource "aws_subnet" "ci_cd_subnet_2" {
  vpc_id                  = aws_vpc.ci_cd_vpc.id
  cidr_block              = "10.0.2.0/24"
  map_public_ip_on_launch = true
}


# ----------------------------------------------------
# SGs
# ----------------------------------------------------


resource "aws_security_group" "jenkins_sg" {
  name        = "jenkins-sg"
  description = "Security group for Jenkins and build node"
  vpc_id      = aws_vpc.ci_cd_vpc.id

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"] # Jenkins UI
  }

  # POTEM SPRAWDZIĆ CZY POTRZEBNE (docker komunikacja?)
  ingress {
    from_port   = 2376
    to_port     = 2376
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"] 
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "JENKINS-SG"
  }
}

resource "aws_security_group" "rds-sg" {
  name        = "rds-sg"
  description = "Allow RDS postgres access"
  vpc_id      = aws_vpc.ci_cd_vpc.id

  ingress {
    from_port   = 5432
    to_port     = 5432
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    protocol    = "-1"
    from_port   = 0
    to_port     = 0
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "RDS-SG"
  }
}