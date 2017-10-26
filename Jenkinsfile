pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'echo "Shell step"' 
                api 'http://77.120.43.250:8200/api/get/helloworld'
            }
        }
    }
}
