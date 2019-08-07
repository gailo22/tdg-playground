pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'echo "Start building..."'
                sh '''
                    cd spring-kafka/payment-service
                    ./gradlew clean build
                    ls -lah
                '''
            }
        }
    }
}