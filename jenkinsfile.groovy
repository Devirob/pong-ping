pipeline {
    agent any

    environment {
        USE_JDK = 'true'
        JDK ='c:/Java/jdk1.8'
    }

    stages {
        stage('Build') {
            steps {
                sh 'printenv'
            }
        }
    }
}