resource "aws_instance" "build_node" {
  ami           = "ami-0866a3c8686eaeeba"
  instance_type = "t2.micro"
  key_name      = "vockey"
  security_groups = [aws_security_group.jenkins_sg.name]

  tags = {
    Name = "Build Node"
  }

  user_data = <<-EOF
    #!/bin/bash
    sudo apt update -y
    sudo apt install -y docker.io
    sudo systemctl start docker
    sudo systemctl enable docker
    sudo usermod -aG docker ubuntu
  EOF
}


output "build_node_public_ip" {
  value = aws_instance.build_node.public_ip
}