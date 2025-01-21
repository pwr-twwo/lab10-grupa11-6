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
                sh 'echo "test"'
            }
        }

        stage("Deploy stage"){
            agent { label 'Built-In Node' } 
            steps {
                sh 'echo "adresURL:${REPO_NAME}" '
            }
        }
    }
}
