
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
                    echo "${env.GIT_COMMIT}"
                    def baseRef = env.GIT_PREVIOUS_COMMIT
                    if(baseRef == null) {
                        baseRef = "origin/develop"
                    }
                    def modules = sh(
                        script: "git diff --name-only ${env.GIT_COMMIT} ${baseRef} | grep 'team-j-.*-ws' | cut -d'/' -f1 | sort -u",
                        returnStdout: true).split('\n') as List
                    println modules
                    if(modules.size() > 0 && modules[0] != "") {
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
                                stage('Result') {
                                    script {
                                        results += "\n[${modules[i]}] : SUCCESS"
                                    }
                                }
                            } catch(Exception e) {
                                script {
                                    results += "\n[${modules[i]}] : FAILURE"
                                }
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
