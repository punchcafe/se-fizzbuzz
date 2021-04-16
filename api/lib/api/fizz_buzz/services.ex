defmodule FizzBuzz.Services do

    import Ecto.Query, only: [from: 2]

    def get_fizz_buzz(fizz_buzz_id) do
        is_favourite = Api.Repo.exists?(from u in Api.Favourite, where: u.fizz_buzz_id == ^fizz_buzz_id)
        %{:value => FizzBuzz.fizz_buzz(fizz_buzz_id), :id => fizz_buzz_id, :is_favourite => is_favourite}
    end

    def page_fizz_buzz(page_number, page_size) do
      page_number = if page_number, do: page_number, else: 1
      page_size = if page_size, do: page_size, else: 5
      start_index = (page_size * (page_number - 1)) + 1
      end_index_inclusive = start_index + page_size - 1
      data = start_index..end_index_inclusive
      |> Enum.map(fn fizz_buzz_id -> %{:value => FizzBuzz.fizz_buzz(fizz_buzz_id), :id => fizz_buzz_id, :is_favourite => false} end)
      %{data: data, page: %{ page_size: page_size, page_number: page_number }}
    end

    def favourite_fizz_buzz(fizz_buzz_id) do
        Api.Repo.insert(%Api.Favourite{fizz_buzz_id: fizz_buzz_id})
        %{:value => FizzBuzz.fizz_buzz(fizz_buzz_id), :id => fizz_buzz_id, :is_favourite => true}
    end

    def unfavourite_fizz_buzz(fizz_buzz_id) do
        Api.Repo.delete(%Api.Favourite{fizz_buzz_id: fizz_buzz_id})
        %{:value => FizzBuzz.fizz_buzz(fizz_buzz_id), :id => fizz_buzz_id, :is_favourite => false}
    end
end