defmodule ApiWeb.FizzBuzzController do
  use ApiWeb, :controller
  
  import Ecto.Query, only: [from: 2]

  def get(conn, %{"id" => id}) do
    fizz_buzz = String.to_integer(id)
    |> FizzBuzz.Services.get_fizz_buzz()
    |> return_fizz_buzz_as_json(conn)
  end

  def paginate(conn, %{"page_number" => page_number, "page_size" => page_size}) do
    page_number = String.to_integer(page_number)
    page_size = String.to_integer(page_size)
    FizzBuzz.Services.page_fizz_buzz(page_number, page_size)
    |> return_fizz_buzz_as_json(conn)
  end

  def paginate(conn, map) do
    page_number = get_or_default("page_number", map, "1")
    page_size = get_or_default("page_size", map, "5") 
    paginate(conn, %{"page_number" => page_number, "page_size" => page_size})
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

  defp get_or_default(key, map, default) do
    if map[key], do: map[key], else: default
  end
end