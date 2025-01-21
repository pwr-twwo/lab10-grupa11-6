# ----------------------------------------------------
# EC2 Build Node - Jenkins Agent
# ----------------------------------------------------


resource "aws_instance" "build_node" {
  ami           = "ami-04b4f1a9cf54c11d0" #z instancji ec2
  instance_type = "t2.micro"
  key_name      = aws_key_pair.jenkins_key.key_name
  vpc_security_group_ids = [aws_security_group.jenkins_sg.id]
  subnet_id = aws_subnet.ci_cd_subnet_1.id

  #iam_instance_profile = data.aws_iam_instance_profile.ec2_profile.id
  iam_instance_profile   = "LabInstanceProfile"

  user_data = <<-EOF
    #!/bin/bash
    sudo apt update -y
    sudo apt upgrade -y

    sudo apt install -y openjdk-17-jdk curl unzip jq

    #docker przez apt (inaczej niż przez snap..)
    sudo apt-get install docker.io -y

    # Install AWS CLI - required for pushing to ecr (login beforehand, i think...) 
    curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
    unzip awscliv2.zip
    sudo ./aws/install --update
    rm -rf awscliv2.zip aws

    echo "AWS CLI version"
    aws --version

    # Start and enable Docker
    sudo systemctl start docker
    sudo systemctl enable docker
    sudo usermod -aG docker ubuntu

    echo "--- jenkins agent - build node configured. ---"

  EOF

  tags = {
    Name = "Build Node"
  }


  depends_on = [aws_internet_gateway.igw]
}


# ----------------------------------------------------
# outputs
# ----------------------------------------------------


output "build_node_public_ip" {
  value = aws_instance.build_node.public_ip
}