provider "aws" {
  region = "us-east-1"
}

# VPC and Subnet
resource "aws_vpc" "ci_cd_vpc" {
  cidr_block = "10.0.0.0/16"
  enable_dns_support = true
  enable_dns_hostnames = true
}

resource "aws_subnet" "ci_cd_subnet" {
  vpc_id            = aws_vpc.ci_cd_vpc.id
  cidr_block        = "10.0.1.0/24"
  map_public_ip_on_launch = true
}

