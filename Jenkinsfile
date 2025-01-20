pipeline {
    agent { label 'Docker-Node' }
    environment {
        ECR_REPO_URI = "<your_account_id>.dkr.ecr.us-east-1.amazonaws.com/my-app"
        REGION = "us-east-1"
    }
    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/pwr-twwo/lab10-grupa11-6.git'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t my-app .'
            }
        }
        stage('Tag Image') {
            steps {
                sh 'docker tag my-app $ECR_REPO_URI:latest'
            }
        }
        stage('Push to ECR') {
            steps {
                sh '''
                aws ecr get-login-password --region $REGION | docker login --username AWS --password-stdin $ECR_REPO_URI
                docker push $ECR_REPO_URI:latest
                '''
            }
        }
        stage('Deploy to ECS') {
            steps {
                sh '''
                
                aws ecs update-service --cluster app-cluster --service app-service --force-new-deployment
                '''
            }
        }
    }
}
