pipeline {
    agent any
    environment {
        AWS_DEFAULT_REGION = "us-east-1"
        ECR_REPO_NAME = "REPO"
        REPO_OWNER="pwr-twwo"
        REPO_NAME="lab10-grupa11-6" 
    }
    stages {
        stage('Build stage') {
            agent { label 'BUILD' }
            steps {
                sh 'echo "${AWS_DEFAULT_REGION}"'
                sh 'pwd'
                sh 'sudo docker images'
                sh 'echo global ENV test: ${GITHUB_TOKEN} '
                sh '''
                    aws --version
                    aws ecr get-login-password --region ${AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin ${AWS_REPO_USER_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com
                    '''
                sh '''
                    git clone https://${GITHUB_TOKEN}@github.com/$REPO_OWNER/$REPO_NAME.git
                    echo "cloned"
                    '''
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
