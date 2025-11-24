pipeline {
  agent any

  // Global pipeline options
  options {
    timestamps()
    ansiColor('xterm')
    buildDiscarder(logRotator(numToKeepStr: '20'))
    durabilityHint('PERFORMANCE_OPTIMIZED')
  }

  // Make sure this matches a configured JDK tool name in Jenkins (Manage Jenkins > Tools)
  tools {
    // Adjust if your Jenkins tool is named differently, e.g., 'Temurin-17' or 'JDK17'
    jdk 'jdk-17'
  }

  environment {
    // Gradle tuning (Jenkins generally recommends --no-daemon)
    GRADLE_OPTS = '-Dorg.gradle.daemon=false -Dorg.gradle.jvmargs=-Xmx2g'
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
        echo "Branch: ${env.BRANCH_NAME ?: 'N/A'} | Build: #${env.BUILD_NUMBER}"
      }
    }

    stage('Prepare') {
      steps {
        sh 'chmod +x gradlew'
      }
    }

    stage('Build') {
      steps {
        // If you need secrets (e.g., to run integration tests), configure credentials in Jenkins
        // and uncomment/adapt the block below with your credentials IDs.
        // withCredentials([
        //   string(credentialsId: 'rapyd_access_key', variable: 'RAPYD_ACCESS_KEY'),
        //   string(credentialsId: 'rapyd_secret_key', variable: 'RAPYD_SECRET_KEY')
        // ]) {
        //   sh './gradlew --no-daemon clean build'
        // }

        sh './gradlew --no-daemon clean build'
      }
    }

    stage('Test Reports') {
      steps {
        junit allowEmptyResults: true, testResults: '**/build/test-results/test/*.xml'
      }
    }

    stage('Archive Artifacts') {
      when {
        anyOf { branch 'main'; branch 'master' }
      }
      steps {
        sh './gradlew --no-daemon publish'
        archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true, onlyIfSuccessful: true

      }
    }
  }

  post {
    always {
      echo "Finished with status: ${currentBuild.currentResult}"
    }
    success {
      echo 'Build succeeded.'
    }
    failure {
      echo 'Build failed.'
    }
  }
}
