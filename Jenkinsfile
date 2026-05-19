def runMaven(String args) {
  if (isUnix()) {
    sh "mvn -Dmaven.repo.local=\"${env.JENKINS_HOME}/.m2/repository\" ${args}"
  } else {
    bat "mvn \"-Dmaven.repo.local=%JENKINS_HOME%\\.m2\\repository\" ${args}"
  }
}

pipeline {
  agent any

  tools {
    maven 'Maven'
  }

  stages {
    stage('Clean') {
      steps {
        script { runMaven('clean') }
      }
    }

    stage('Compile') {
      steps {
        script { runMaven('install -DskipTests') }
      }
    }

    stage('Test') {
      steps {
        script { runMaven('test -Dmaven.test.failure.ignore=true') }
      }
    }

    stage('PMD') {
      steps {
        script { runMaven('org.apache.maven.plugins:maven-pmd-plugin:3.21.0:pmd') }
      }
    }

    stage('JaCoCo') {
      steps {
        script { runMaven('org.jacoco:jacoco-maven-plugin:0.8.11:report') }
      }
    }

    stage('Site') {
      steps {
        script { runMaven('site -DgenerateReports=false') }
      }
    }

    stage('Package') {
      steps {
        script { runMaven('package -DskipTests') }
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: '**/target/site/**/*.*', allowEmptyArchive: true, fingerprint: true
      archiveArtifacts artifacts: '**/target/**/*.jar',   allowEmptyArchive: true, fingerprint: true
      archiveArtifacts artifacts: '**/target/**/*.war',   allowEmptyArchive: true, fingerprint: true
      junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
    }
  }
}
