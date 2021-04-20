# FizzBuzz
Welcome to this FizzBuzz project!

### Getting Started
In order to get up and running as quick as possible, execute the following docker-compose command in this directory:
```
$ docker-compose up --build
```
You will now have the FizzBuzz UI available at `localhost:80`, as well as the REST api at `localhost:4000`. In order to use the CLI, please build it from source from the gradle build file:
```
$ cd ./cli
$ ./gradlew clean build
```
A more detailed guide can be found in the sub project's `README.md`