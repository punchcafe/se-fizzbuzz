# FizzBuzz UI
This is the UI for interacting with the FizzBuzz API.

### Building 
This UI can be built and run using docker. While in this repository:
```
$ docker build . -t fizz-buzz-ui:0.0.1-SNAPSHOT
$ docker run -p 80:80 fizz-buzz-ui:0.0.1-SNAPSHOT
```
### Dependencies
- A FizzBuzz API running on `localhost:4000`