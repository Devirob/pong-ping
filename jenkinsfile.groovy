node {
    stage('Stage 1') {
        echo 'Starting with TA-TEST for IDMS'
        sh 'mvn -B -DskipTests clean package'

    }
}