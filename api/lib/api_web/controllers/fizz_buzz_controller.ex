defmodule ApiWeb.FizzBuzzController do
  use ApiWeb, :controller
  
  import Ecto.Query, only: [from: 2]

  def get(conn, %{"id" => id}) do
    fizz_buzz = String.to_integer(id)
    |> FizzBuzz.Services.get_fizz_buzz()
    json(conn, fizz_buzz)
  end

  def put(conn, %{"is_favourite" => is_favourite, "id" => id}) do
    number_id = String.to_integer(id)
    if is_favourite do
      FizzBuzz.Services.favourite_fizz_buzz(number_id)
    else
      FizzBuzz.Services.unfavourite_fizz_buzz(number_id)
    end
    json(conn, %{:value => FizzBuzz.fizz_buzz(number_id), :id => number_id, :is_favourite => is_favourite})
  end
end
