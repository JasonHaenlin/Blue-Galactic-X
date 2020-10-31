
def modules = ["team-j-rocket-ws", "team-j-weather-ws","team-j-mission-ws","team-j-telemetry-ws","team-j-booster-ws", "team-j-payload-ws"]
def gates = ""
def results = ""

pipeline{
    agent any
    tools {
        jdk 'LOCAL_JDK11'
    }
    options {
        disableConcurrentBuilds()
        timeout(time: 1, unit: "HOURS")
    }
    stages {
        stage('Proceed with the modules') {
            steps {
                script {
                    for(int i=0; i < modules.size(); i++) {
                        gate = "\n - Quality gate was not proceed due to build error"
                        try {
                            stage("Compile ${modules[i]}") {
                                dir("./${modules[i]}") {
                                    echo "Compile WebService"
                                    sh "chmod +x ./mvnw"
                                    sh "./mvnw clean compile -DskipTests"
                                }
                            }
                            stage("Test ${modules[i]}") {
                                dir("./${modules[i]}") {
                                    echo "Test WebService"
                                    sh "./mvnw test"
                                }
                            }
                            stage('Sonarqube') {
                                withSonarQubeEnv('Sonarqube_env') {
                                    dir("./${modules[i]}") {
                                        echo 'Sonar Analysis'
                                        sh './mvnw sonar:sonar -Dsonar.pitest.mode=reuseReport'
                                    }
                                }
                            }
                            try {
                                timeout(time: 1, unit: "HOURS") {
                                    waitForQualityGate true
                                }
                                script {
                                    gate = "\n - Quality gate was successful"
                                }
                            } catch(Exception e) {
                                script {
                                    gate = "\n - Quality gate was failed"
                                }
                            }
                            stage('Result') {
                                script {
                                    results += "\n[${modules[i]}] : SUCCESS" + gate
                                }
                            }
                        } catch(Exception e) {
                            script {
                                results += "\n[${modules[i]}] : FAILURE" + gate
                            }
                        }
                    }
                }
            }
        }
        stage("Verify the results") {
            steps {
                script {
                    def isFailure = results.contains("FAILURE")
                    if(env.JOB_NAME.contains("PR")) {
                        results = isFailure ? "\n[PR] FAILURE" : "\n[PR] SUCCESS"
                    }
                    if(isFailure) {
                        exit 1
                    }
                }
            }
        }
    }
    post{
        success {
            slackSend(
            channel: 'soa-team-j-status',
            notifyCommitters: true,
            failOnError: true,
            color: 'good',
            token: env.SLACK_TOKEN,
            message: 'Job: ' + env.JOB_NAME + ' with buildnumber ' + env.BUILD_NUMBER + ' was successful' + results,
            baseUrl: env.SLACK_WEBHOOK)
            echo "======== pipeline executed successfully ========"
        }
        failure {
            slackSend(
            channel: 'soa-team-j-status',
            notifyCommitters: true,
            failOnError: true,
            color: 'danger',
            token: env.SLACK_TOKEN,
            message: 'Job: ' + env.JOB_NAME + ' with buildnumber ' + env.BUILD_NUMBER + ' was failed' + results,
            baseUrl: env.SLACK_WEBHOOK)
            echo "======== pipeline execution failed========"
        }
    }
}
