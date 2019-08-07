pipeline {
  agent any
  stages {
    stage('Gradle Build') {
      steps {
        if (isUnix()) {
            dir('spring-kafka/payment-service') {sh './gradlew clean build'}
        } else {
            dir('spring-kafka/payment-service') {bat 'gradlew.bat clean build'}
        }
      }
    }
  }
}
