# FizzBuzz REST api

This is a simple rest API which provides the following end points:
```
GET /fizzbuzz/${number}
// calculate the value of fizzbuzz for $number

GET /fizzbuzz?page_number=${p_number}&page_size=${p_size} 
// Paginate fizz buzz results. Default page size is 5, default page number is 1.

POST /fizzbuzz/${number}
{ "is_favourite" : true }
// Allows for favouriting of certain fizzbuzz values 
```

### Run on Docker Compose
To run this application using docker compose, execute in this directory:
```
 $ docker-compose up --build
```
This will spin up an instance of the app as well as a backing database. The app will be available on `port 4000`.

### Building from source

In order to build and run locally, you will need a configured instance of postgres running on `port 5432`. Please be aware certain distributions can have issues when Pheonix attempts to connect. To build and run the app from source:

```
 $ mix deps.get
 $ mix ecto.setup
 $ mix phx.server
```

### Testing Locally

In order to test locally you will need to configure an instance of postgres running on `port 5432`. Please be aware certain distributions can have issues when Pheonix attempts to connect. To run tests with coverage, execute:

```
>> $ mix test --cover
```