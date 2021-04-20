# FizzBuzz CLI

This is a CLI which provides command line tooling for accessing the FizzBuzz API.

This project builds a JAR artifact which can be executed by:
```
$ java -jar /path/to/your/jar/fizzbuzz-cli.jar calculate 15
>> Result:  id: 15,  value: FizzBuzz
```
to see a list of available commands:
```
$ java -jar /path/to/your/jar/fizzbuzz-cli.jar help
```
alternatively, you can alias `java -jar /path/to/your/jar/fizzbuzz-cli.jar` to a keyword of your choosing.
### Dependecies
- This project requires a Java Runtime in order to run the jar, and and a full JDK in order to be able to build from source
- This project requires a FizzBuzz server running on `localhost:4000` 

### Building the source jar
You can build the jar by running the following:
```
$ ./gradlew clean build
```
The resulting jar will be in `./build/libs/fizzbuzz-cli.jar`
