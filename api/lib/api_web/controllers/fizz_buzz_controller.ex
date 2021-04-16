defmodule ApiWeb.FizzBuzzController do
  use ApiWeb, :controller
  
  import Ecto.Query, only: [from: 2]

  def get(conn, %{"id" => id}) do
    fizz_buzz = String.to_integer(id)
    |> FizzBuzz.Services.get_fizz_buzz()
    |> return_fizz_buzz_as_json(conn)
  end

  def put(conn, %{"is_favourite" => is_favourite, "id" => id}) do
    number_id = String.to_integer(id)
    if is_favourite do
      FizzBuzz.Services.favourite_fizz_buzz(number_id)
    else
      FizzBuzz.Services.unfavourite_fizz_buzz(number_id)
    end
    |> return_fizz_buzz_as_json(conn)
  end

  defp return_fizz_buzz_as_json(fizz_buzz, conn) do
    json(conn, fizz_buzz)
  end
end
