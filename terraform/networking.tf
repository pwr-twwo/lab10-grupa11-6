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
  availability_zone = "us-east-1a"
  vpc_id                  = aws_vpc.ci_cd_vpc.id
  cidr_block              = "10.0.1.0/24"
  map_public_ip_on_launch = true
}

resource "aws_subnet" "ci_cd_subnet_2" {
  availability_zone = "us-east-1b"
  vpc_id                  = aws_vpc.ci_cd_vpc.id
  cidr_block              = "10.0.2.0/24"
  map_public_ip_on_launch = true
}




# ----------------------------------------------------
# ROUTING
# ----------------------------------------------------


resource "aws_internet_gateway" "igw" {
    vpc_id = aws_vpc.ci_cd_vpc.id

    tags = {
        Name = "DevOps-IGW"
    }

}

resource "aws_route_table" "route_table" {
  vpc_id = aws_vpc.ci_cd_vpc.id

  tags = {
    Name = "DevOps-RT"
  }

}

resource "aws_route" "default_internet_route" {

  route_table_id         = aws_route_table.route_table.id
  gateway_id             = aws_internet_gateway.igw.id
  destination_cidr_block = "0.0.0.0/0"

}

resource "aws_route_table_association" "public_subnet_association_1" {

  subnet_id      = aws_subnet.ci_cd_subnet_1.id
  route_table_id = aws_route_table.route_table.id
  
}

resource "aws_route_table_association" "public_subnet_association_2" {

  subnet_id      = aws_subnet.ci_cd_subnet_2.id
  route_table_id = aws_route_table.route_table.id
  
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

resource "aws_security_group" "rds_sg" {
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


resource "aws_security_group" "alb_sg" {

    name        = "alb-sg"
    vpc_id      = aws_vpc.ci_cd_vpc.id
    description = "SG for ALB"
    
    ingress {
        from_port   = 80
        to_port     = 80
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
        description = "http"
    }

    ingress {
        from_port   = 8080
        to_port     = 8080
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
        description = "backend"
    }
    
    egress {
        from_port   = 0
        to_port     = 0
        protocol    = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }

    tags = {
        Name = "DevOps-ALB-SG"
    }
  
}



resource "aws_security_group" "backend_sg" {
  name        = "backend-sg"
  description = "Security group for backend containers"
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

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "ECS-SG"
  }
}