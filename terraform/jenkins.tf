resource "aws_instance" "jenkins" {
  ami           = "ami-0c02fb55956c7d316"
  instance_type = "t2.micro"
  key_name      = "vockey"
  security_groups = [aws_security_group.jenkins_sg.name]

  tags = {
    Name = "Jenkins Server"
  }

  user_data = <<-EOF
    #!/bin/bash
    sudo apt update -y
    sudo apt install -y openjdk-11-jdk
    wget -q -O - https://pkg.jenkins.io/debian-stable/jenkins.io.key | sudo apt-key add -
    sudo sh -c 'echo deb http://pkg.jenkins.io/debian-stable binary/ > /etc/apt/sources.list.d/jenkins.list'
    sudo apt update -y
    sudo apt install -y jenkins
    sudo systemctl start jenkins
    sudo systemctl enable jenkins
  EOF
}

output "jenkins_public_ip" {
  value = aws_instance.jenkins.public_ip
}