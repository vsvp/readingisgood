# readingisgood
getir

To start up the project you can either pull and import project to your IDE and directly run main class or by running a container.
To run a container following commands must be run

To create a docker image
"docker build -t reading-is-good-docker ."

To run a container from the image
"docker run -p 8080:8080 reading-is-good-docker ."

Container will try to connect a container-local port for 5432  
"docker run --rm --add-host=host:<ip> --name readingisgood -p 8080:8080 reading-is-good-docker ."

