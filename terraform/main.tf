# ----------------------------------------------------
# MAIN - Provider
# ----------------------------------------------------

provider "aws" {
  region = "us-east-1"
}

resource "aws_key_pair" "jenkins_key" {
  key_name   = "jenkins-key"
  public_key = file("~/.ssh/jenkins_key.pub")
}

