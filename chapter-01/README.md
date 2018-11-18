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
