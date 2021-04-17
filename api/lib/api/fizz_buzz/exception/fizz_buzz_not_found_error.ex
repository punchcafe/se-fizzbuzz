defmodule FizzBuzzNotFoundError do
  defexception message: "No FizzBuzz found for id. Valid fizz buzz ids are between 1 - 100,000,000"
end