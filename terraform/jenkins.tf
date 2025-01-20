# ----------------------------------------------------
# EC2 Jekins Controller
# ----------------------------------------------------




resource "aws_instance" "jenkins" {

  ami           = "ami-04b4f1a9cf54c11d0"
  instance_type = "t2.small"
  key_name      = aws_key_pair.jenkins_key.key_name
  #security_groups = [aws_security_group.jenkins_sg.name]
  vpc_security_group_ids = [aws_security_group.jenkins_sg.id]

  iam_instance_profile = data.aws_iam_role.ec2_role

  subnet_id = aws_subnet.ci_cd_subnet_2.id

  #  user_data = file("user_data/jenkins.sh")

  #user_data = <<-EOF
  #  #!/bin/bash
  #  sudo apt update -y
  #  sudo apt install -y openjdk-17-jdk jq

    # JENKINS
  #  sudo wget -q -O /usr/share/keyrings/jenkins-keyring.asc https://pkg.jenkins.io/debian-stable/jenkins.io.key
  #  echo "deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] https://pkg.jenkins.io/debian-stable binary/" | sudo tee /etc/apt/sources.list.d/jenkins.list > /dev/null
  #  sudo apt update -y

  #  sudo apt install -y jenkins
  #  sudo systemctl start jenkins
  #  sudo systemctl enable jenkins

  #  echo "--- jenkins server configured. ---"

  #EOF

    tags = {
    Name = "Jenkins Controller Server"
  }

  depends_on = [aws_internet_gateway.igw]


}


# ------------------------------------------------------------------------------
# EC2 role policy 
# ------------------------------------------------------------------------------


data "aws_iam_role" "ec2_role" {
  name = "LabInstanceProfile"
}

output "ec2_role" {
  value = data.aws_iam_role.ec2_role.arn
}


# ----------------------------------------------------
# outputs
# ----------------------------------------------------

output "jenkins_public_ip" {
  value = aws_instance.jenkins.public_ip
}

output "jenkins_url" {
  value = "http://${aws_instance.jenkins.public_ip}:8080"
}
