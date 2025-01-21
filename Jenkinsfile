pipeline {
    agent any
    environment {
        AWS_DEFAULT_REGION = "us-east-1"
        ECR_REPO_NAME = "REPO"
        REPO_OWNER="pwr-twwo"
        REPO_NAME="lab10-grupa11-6" 
        TASK_FAMILY="app-task"
        TASK_DEFINITION_NAME="app-task"
        CLUSTER_NAME = "devops-cluster"
        SERVICE_NAME = "app-service"
    }
    stages {
        stage('Build stage') {
            agent { label 'BUILD' }
            steps {
                sh '''
                    aws --version
                    aws ecr get-login-password --region ${AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin ${AWS_REPO_USER_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com
                    '''

                sh '''
                    if [ -d ${REPO_NAME} ]; then
                        echo "Removing existing folder..."
                        rm -rf lab10-grupa11-6
                    fi
                    '''

                sh '''
                    git clone https://${GITHUB_TOKEN}@github.com/$REPO_OWNER/$REPO_NAME.git
                    echo "cloned"
                    ls
                    '''

                sh  '''
                    cd ./${REPO_NAME}/be
                    docker build -t kino_image .
                    docker images
                    docker tag kino_image:latest ${AWS_REPO_USER_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${AWS_REPO_NAME}:latest
                    docker push ${AWS_REPO_USER_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${AWS_REPO_NAME}:latest
                    echo "image pushed"
                    '''
            }
        }

        stage("Deploy stage"){
            agent { label 'Controller' } 
            steps {

                sh "aws ecs update-service \
                --cluster ${CLUSTER_NAME} \
                --service ${SERVICE_NAME} \
                --force-new-deployment"
                
            }
        }
    }
}
