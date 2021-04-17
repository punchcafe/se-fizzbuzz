defmodule FizzBuzz.Services do

    import Ecto.Query, only: [from: 2]

    @fizz_buzz_max 100000000

    def get_fizz_buzz(fizz_buzz_id) when fizz_buzz_id < 1 or fizz_buzz_id > @fizz_buzz_max do
        raise FizzBuzzNotFoundError
    end

    def get_fizz_buzz(fizz_buzz_id) do
        is_favourite = Api.Repo.exists?(from u in Api.Favourite, where: u.fizz_buzz_id == ^fizz_buzz_id)
        %{:value => FizzBuzz.fizz_buzz(fizz_buzz_id), :id => fizz_buzz_id, :is_favourite => is_favourite}
    end

    def page_fizz_buzz(page_number, page_size) when page_size < 1 or page_size > 200 or page_number < 1 do
        raise IllegalPageParametersError
    end

    def page_fizz_buzz(page_number, page_size) do
      start_index = (page_size * (page_number - 1)) + 1
      if start_index > @fizz_buzz_max, do: raise FizzBuzzPageNotFoundError
      end_index_inclusive = start_index + page_size - 1
      |> clamp_to_max_fizz_buzz_id()
      favourite_ids = find_favourites_in_range(start_index, end_index_inclusive)
      data = start_index..end_index_inclusive
      |> Enum.map(fn fizz_buzz_id -> %{:value => FizzBuzz.fizz_buzz(fizz_buzz_id), :id => fizz_buzz_id, :is_favourite => false} end)
      |> Enum.map(fn fizz_buzz -> apply_favourite_status(fizz_buzz, favourite_ids) end)
      %{data: data, page: %{ page_size: page_size, page_number: page_number }}
    end

    def favourite_fizz_buzz(fizz_buzz_id) when fizz_buzz_id < 1 or fizz_buzz_id > @fizz_buzz_max do
        raise FizzBuzzNotFoundError
    end

    def favourite_fizz_buzz(fizz_buzz_id) do
        Api.Repo.insert(%Api.Favourite{fizz_buzz_id: fizz_buzz_id})
        %{:value => FizzBuzz.fizz_buzz(fizz_buzz_id), :id => fizz_buzz_id, :is_favourite => true}
    end

    def unfavourite_fizz_buzz(fizz_buzz_id)  when fizz_buzz_id < 1 or fizz_buzz_id > @fizz_buzz_max do
        raise FizzBuzzNotFoundError
    end

    def unfavourite_fizz_buzz(fizz_buzz_id) do
        Api.Repo.delete(%Api.Favourite{fizz_buzz_id: fizz_buzz_id})
        %{:value => FizzBuzz.fizz_buzz(fizz_buzz_id), :id => fizz_buzz_id, :is_favourite => false}
    end

    defp apply_favourite_status(%{ id: id } = fizz_buzz, favourite_ids) do
        Map.put(fizz_buzz, :is_favourite, Enum.member?(favourite_ids, id))
    end

    defp find_favourites_in_range(start_range_inclusive, end_range_inclusive) do
        Api.Repo.all(from u in Api.Favourite, where: u.fizz_buzz_id >= ^start_range_inclusive and u.fizz_buzz_id <= ^end_range_inclusive)
        |> Enum.map(fn favourite -> favourite.fizz_buzz_id end)
    end

    defp clamp_to_max_fizz_buzz_id(fizz_buzz_id) do
        if fizz_buzz_id > @fizz_buzz_max, do: @fizz_buzz_max, else: fizz_buzz_id
    end
end