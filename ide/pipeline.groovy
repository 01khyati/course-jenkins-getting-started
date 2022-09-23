pipeline {
    agent any
    triggers { pollSCM('* * * * *') }
    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/01khyati/jgsu-spring-petclinic.git', branch: 'main'
            }            
        }
        stage('Build') {
            steps {
                bat "mvn clean package"
                //-Dmaven.test.failure.ignore=true
            }
        
            post {
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
                // changed {
                //     emailext subject: "Job \'${JOB_NAME}\' (build ${BUILD_NUMBER}) ${currentBuild.result}",
                //         body: "Please go to ${BUILD_URL} and verify the build", 
                //         attachLog: true, 
                //         compressLog: true, 
                //         to: "test@jenkins",
                //         recipientProviders: [upstreamDevelopers(), requestor()]
                // }
            }
        }
    }
}
