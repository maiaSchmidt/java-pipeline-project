pipeline {
  agent none
 
  options {
    buildDiscarder(logRotator(numToKeepStr: '2', artifactNumToKeepStr: '1'))
  }

  environment {
    MAJOR_VERSION = 1
  }

  stages {
    stage ('Build') {
      agent {
        label 'apache'
      }
      steps {
        sh 'ant -f build.xml -v'
      }
      post {
        success {
          archiveArtifacts artifacts: 'dist/*.jar', fingerprint: true
        }
      }			
    }
    stage ('Unit tests') {
      agent {
        label 'apache'
      }
      steps {
        sh 'ant -f test.xml -v'
        junit 'reports/result.xml'
      }
    }
    stage ('Deploy') {
      agent {
        label 'apache'
      }
      steps {
        sh "mkdir -p /var/www/html/rectangles/all/${env.BRANCH_NAME}"
        sh "cp dist/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar /var/www/html/rectangles/all/${env.BRANCH_NAME}/" 
      }
    }
    stage ('Running on CentOS') {
      agent {
        label 'CentOS'
      }
      steps {
        sh "wget http://fabioms1.mylabserver.com/rectangles/all/${env.BRANCH_NAME}/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar"
        sh "java -jar rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar 3 4"
      }
    }
    stage ('Running on Debian') {
      agent {
        docker 'openjdk:8u121-jre'        
      }
      steps {
        sh "wget http://fabioms1.mylabserver.com/rectangles/all/${env.BRANCH_NAME}/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar"
        sh "java -jar rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar 3 4"
      }
    }
    stage ('Promote to Green') {
      agent {
        label 'apache'
      }
      when {
        branch 'master'
      }	
      steps {      
        sh "cp /var/www/html/rectangles/all/${env.BRANCH_NAME}/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar /var/www/html/rectangles/green/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar"
      }			
    }
    stage ('Promote development branch to master') {
      agent {
	label 'apache'			
      }
      when {
        branch 'development'
      }
      steps {
        echo 'Stashing any local changes.'
        sh 'git stash'
	echo 'Git checkout development.'
        sh 'git checkout development'
        sh 'git pull origin development'
	echo 'Git chechout master'
	sh 'git checkout master'
        sh 'git pull'
	echo 'Merging development branch into master branch.'
        sh 'git merge development'
        echo 'Pushing to origin mastert'
        sh 'git push origin master'
        echo 'Tagging the release'
        sh "git tag rectangle-${env.MAJOR_VERSION}.${env.BUILD_NUMBER}"
        sh "git push origin rectangle-${env.MAJOR_VERSION}.${env.BUILD_NUMBER}" 
      }
    }
  }    
  post {
    failure {
      emailext {
        subject: "${env.JOB_NAME} [${env.BUILD_NUMBER}] failed !"
        body: "Check console output <a href='${env.BUILD_URL}'>Console output </a: href>"
        to: "sofortal@yahoo.com.br" 	
      }
    } 
  }
}
