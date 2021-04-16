defmodule FizzBuzz.Services do

    import Ecto.Query, only: [from: 2]

    def get_fizz_buzz(fizz_buzz_id) do
        is_favourite = Api.Repo.exists?(from u in Api.Favourite, where: u.fizz_buzz_id == ^fizz_buzz_id)
        %{:value => FizzBuzz.fizz_buzz(fizz_buzz_id), :id => fizz_buzz_id, :is_favourite => is_favourite}
    end

    def favourite_fizz_buzz(fizz_buzz_id) do
        Api.Repo.insert(%Api.Favourite{fizz_buzz_id: fizz_buzz_id})
    end

    def unfavourite_fizz_buzz(fizz_buzz_id) do
        Api.Repo.delete(%Api.Favourite{fizz_buzz_id: fizz_buzz_id})
    end
end