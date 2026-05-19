def runMaven(String args) {
  if (isUnix()) {
    sh "mvn -Dmaven.repo.local=.m2/repository ${args}"
  } else {
    bat "mvn -Dmaven.repo.local=.m2/repository ${args}"
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
        script {
          runMaven('clean')
        }
      }
    }

    stage('Compile') {
      steps {
        script {
          runMaven('install -DskipTests')
        }
      }
    }

    stage('Test') {
      steps {
        script {
          runMaven('test -Dmaven.test.failure.ignore=true')
        }
      }
    }

    stage('PMD') {
      steps {
        script {
          runMaven('pmd:pmd')
        }
      }
    }

    stage('JaCoCo') {
      steps {
        script {
          runMaven('jacoco:report')
        }
      }
    }

    stage('Site') {
      steps {
        script {
          runMaven('site')
        }
      }
    }

    stage('Package') {
      steps {
        script {
          runMaven('package -DskipTests')
        }
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: '**/target/site/**/*.*', allowEmptyArchive: true, fingerprint: true
      archiveArtifacts artifacts: '**/target/**/*.jar', allowEmptyArchive: true, fingerprint: true
      archiveArtifacts artifacts: '**/target/**/*.war', allowEmptyArchive: true, fingerprint: true
      junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
    }
  }
}
