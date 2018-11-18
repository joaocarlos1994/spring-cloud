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
