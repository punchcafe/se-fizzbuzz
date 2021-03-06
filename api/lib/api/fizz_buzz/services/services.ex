defmodule FizzBuzz.Services do

    import Ecto.Query, only: [from: 2]

    @fizz_buzz_max 100000000
    def fizz_buzz_max(), do: @fizz_buzz_max

    def build_fizz_buzz(fizz_buzz_id, is_favourite \\ false) do
        %{:value => FizzBuzz.fizz_buzz(fizz_buzz_id), :id => fizz_buzz_id, :is_favourite => is_favourite}
    end

    def get_fizz_buzz!(fizz_buzz_id) when fizz_buzz_id < 1 or fizz_buzz_id > @fizz_buzz_max do
        raise FizzBuzzNotFoundError
    end

    def get_fizz_buzz!(fizz_buzz_id) do
        build_fizz_buzz(fizz_buzz_id, is_fizz_buzz_favourite?(fizz_buzz_id))
    end

    def favourite_fizz_buzz!(fizz_buzz_id) when fizz_buzz_id < 1 or fizz_buzz_id > @fizz_buzz_max do
        raise FizzBuzzNotFoundError
    end

    def favourite_fizz_buzz!(fizz_buzz_id) do
        # Adds a race condition, but in the very unlikely event can only result in a 500
        if !is_fizz_buzz_favourite?(fizz_buzz_id) do Api.Repo.insert(%Api.Favourite{fizz_buzz_id: fizz_buzz_id}) end
        build_fizz_buzz(fizz_buzz_id, true)
    end

    def unfavourite_fizz_buzz!(fizz_buzz_id)  when fizz_buzz_id < 1 or fizz_buzz_id > @fizz_buzz_max do
        raise FizzBuzzNotFoundError
    end

    def unfavourite_fizz_buzz!(fizz_buzz_id) do
        # Adds a race condition, but in the very unlikely event can only result in a 500
        if is_fizz_buzz_favourite?(fizz_buzz_id) do Api.Repo.delete(%Api.Favourite{fizz_buzz_id: fizz_buzz_id}) end
        build_fizz_buzz(fizz_buzz_id, false)
    end

    defp is_fizz_buzz_favourite?(fizz_buzz_id) do
        Api.Repo.exists?(from u in Api.Favourite, where: u.fizz_buzz_id == ^fizz_buzz_id)
    end

end