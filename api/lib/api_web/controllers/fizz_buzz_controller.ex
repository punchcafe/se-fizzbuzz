defmodule ApiWeb.FizzBuzzController do
  use ApiWeb, :controller

  def get(conn, %{"id" => id}) do
    number = String.to_integer(id)
    json(conn, %{:value => FizzBuzz.fizz_buzz(number), :id => number})
  end
end
