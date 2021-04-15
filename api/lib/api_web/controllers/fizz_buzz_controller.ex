defmodule ApiWeb.FizzBuzzController do
  use ApiWeb, :controller
  
  import Ecto.Query, only: [from: 2]

  def get(conn, %{"id" => id}) do
    number = String.to_integer(id)
    is_favourite = Api.Repo.exists?(from u in Api.Favourite, where: u.fizz_buzz_id == ^number)
    json(conn, %{:value => FizzBuzz.fizz_buzz(number), :id => number, :is_favourite => is_favourite})
  end

  def put(conn, %{"is_favourite" => is_favourite, "id" => id}) do
    number_id = String.to_integer(id)
    if is_favourite do
      Api.Repo.insert(%Api.Favourite{fizz_buzz_id: number_id})
    else
      Api.Repo.delete(%Api.Favourite{fizz_buzz_id: number_id})
    end
    json(conn, %{:value => FizzBuzz.fizz_buzz(number_id), :id => number_id, :is_favourite => is_favourite})
  end
end
