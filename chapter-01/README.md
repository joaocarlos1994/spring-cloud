<h2>Some notes about Spring In Action Microservices book</h2>


<ol>
  <li>
      Java: This directory contains the Java source code used to build the service.
  </li>
  <li>
      Docker: This directory contains two files needed to build a Docker image for
      each service. The first file will always be called Dockerfile and contains the step-
      by-step instructions used by Docker to build the Docker image. The second file,
      run.sh, is a custom Bash script that runs inside the Docker container. This script
      ensures that the service doesn’t start until certain key dependencies (database is
      up and running) become available.
  </li>
  <li>
      Resources: The resources directory contains all the services’ application.yml
      files. While application configuration is stored in the Spring Cloud Config, all
      services have configuration that’s stored locally in the application.yml. Also, the
      resources directory will contain a schema.sql file containing all the SQL com-
      mands used to create the tables and pre-load data for the services into the Post-
      gres database.
  </li>
</ol>

<h4>This part of explain is continuation of pom.xml file</h4>

<ol>
  <li>
    It copies the executable jar for the service, along with the contents of the src/ main/docker directory, to target/docker.   </li>
  <li>
    It executes the Dockerfile defined in the target/docker directory. The Dockerfile is a list of commands that are executed       whenever a new Docker image for that service is provisioned.
  </li>
  <li>
    It pushes the Docker image to the local Docker image repository that’s installed when you install Docker.
  </li>
</ol>

<p>
  The following listing shows the contents of the Dockerfile from your licensing service.
</p>

FROM openjdk:8-jdk-alpine -> This is the Linux Docker image that you’re going to use in your Docker run-time. This installation is optimized for Java applications.

RUN apk update && apk upgrade && apk add netcat-openbsd -> You install nc (netcat), a utility that you’ll use to ping dependent services to see if they are up.

RUN mkdir -p /usr/local/licensingservice

ADD licensing-service-0.0.1-SNAPSHOT.jar /usr/local/licensingservice/ -> The Docker ADD command copies the executable JAR from the local filesystem to the Docker image.

ADD run.sh run.sh -> You add a custom BASH shell script that will monitor for service dependencies and then launch the actual service.

RUN chmod +x run.sh

CMD ./run.sh

