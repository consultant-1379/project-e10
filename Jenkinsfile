#!/usr/bin/env groovy

def defaultBobImage = 'armdocker.rnd.ericsson.se/sandbox/adp-staging/adp-cicd/bob.2.0:1.7.0-83'

def envMap = ['INT_CHART_VERSION': env.INT_CHART_VERSION]

def bob = new BobCommand()
    .bobImage(defaultBobImage)
    .needDockerSocket(true)
    .envVars(envMap)
    .toString()

pipeline {
  agent{
      label 'Cloud-Native'
   }
    stages{
	    stage('Build Frontend Image'){
		    steps{
			    script {
				    sh "${bob} build-frontend-image"
			    }
		    }
	    }
	    stage('Build Backend Image'){
		    steps{
			    script{
				    sh "${bob} build-backend-image"
			    }
		    }
	    }
	    stage('Test Frontend'){
		    steps{
		        script{
		            sh "${bob} test-frontend"
		        }
		    }
	    }
	    stage('Test Backend'){
	        steps{
	            script{
                    sh "${bob} test-backend"
                }
	        }
	    }
	}
}

// More about @Builder: http://mrhaki.blogspot.com/2014/05/groovy-goodness-use-builder-ast.html
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class BobCommand {
    def bobImage = 'bob.2.0:latest'
    def envVars = [:]
    def needDockerSocket = false

    String toString() {
        def env = envVars
                .collect({ entry -> "-e ${entry.key}=\"${entry.value}\"" })
                .join(' ')

        def cmd = """\
            |docker run
            |--init
            |--rm
            |--workdir \${PWD}
            |--user \$(id -u):\$(id -g)
            |-v \${PWD}:\${PWD}
            |-v /etc/group:/etc/group:ro
            |-v /etc/passwd:/etc/passwd:ro
            |-v \${HOME}/.m2:\${HOME}/.m2
            |-v \${HOME}/.docker:\${HOME}/.docker
            |${needDockerSocket ? '-v /var/run/docker.sock:/var/run/docker.sock' : ''}
            |${env}
            |\$(for group in \$(id -G); do printf ' --group-add %s' "\$group"; done)
            |--group-add \$(stat -c '%g' /var/run/docker.sock)
            |${bobImage}
            |"""
        return cmd
                .stripMargin()           // remove indentation
                .replace('\n', ' ')      // join lines
                .replaceAll(/[ ]+/, ' ') // replace multiple spaces by one
    }
}
