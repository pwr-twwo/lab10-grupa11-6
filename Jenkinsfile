pipeline {
    agent any
    environment {
        REGION = "us-east-1"
        ECR_REPO_NAME = "REPO"
    }
    stages {
        stage('Build stage') {
            agent { label 'BUILD' }
            steps {
                sh 'echo "${REGION}"'
                sh 'pwd'
                sh 'sudo docker images'
                sh 'echo global ENV test: ${GITHUB_TOKEN} '
                sh 'aws cli login test:'
                sh 'aws ecr get-login-password --region us-east-1'
            }
        }

        stage("Deploy stage"){
            agent { label 'Controller' } 
            steps {
                sh 'echo "adresURL:${ECR_REPO_NAME}" '
            }
        }
    }
}
