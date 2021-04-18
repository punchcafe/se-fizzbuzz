defmodule FizzBuzz.Services.Pagination do

    import Ecto.Query, only: [from: 2]
    
    def page_fizz_buzz(page_number, page_size) when page_size < 1 or page_size > 200 or page_number < 1 do
        raise IllegalPageParametersError
    end

    def page_fizz_buzz(page_number, page_size) do
      max_fizz_buzz_id = FizzBuzz.Services.fizz_buzz_max()
      start_index = calculate_start_index_from_page_details(page_number, page_size)
      if start_index >max_fizz_buzz_id, do: raise FizzBuzzPageNotFoundError

      end_index_inclusive = start_index + page_size - 1
      |> clamp_to_max_fizz_buzz_id(max_fizz_buzz_id)

      data = get_fizz_buzz_for_range(start_index, end_index_inclusive)
      has_next_page = end_index_inclusive < max_fizz_buzz_id
      has_previous_page = start_index > 1
      
      %{data: data, 
        page: %{ page_size: page_size, page_number: page_number, has_next_page: has_next_page, has_previous_page: has_previous_page }}
    end

    defp calculate_start_index_from_page_details(page_number, page_size) do
        (page_size * (page_number - 1)) + 1
    end

    defp get_fizz_buzz_for_range(start_index, end_index_inclusive) do
      favourite_ids = find_favourites_in_range(start_index, end_index_inclusive)
      start_index..end_index_inclusive
      |> Enum.map(& FizzBuzz.Services.build_fizz_buzz/1)
      |> Enum.map(fn fizz_buzz -> apply_favourite_status(fizz_buzz, favourite_ids) end)
    end

    defp clamp_to_max_fizz_buzz_id(fizz_buzz_id, max) do
        if fizz_buzz_id > max, do: max, else: fizz_buzz_id
    end

    defp find_favourites_in_range(start_range_inclusive, end_range_inclusive) do
        Api.Repo.all(from u in Api.Favourite, where: u.fizz_buzz_id >= ^start_range_inclusive and u.fizz_buzz_id <= ^end_range_inclusive)
        |> Enum.map(fn favourite -> favourite.fizz_buzz_id end)
    end

    defp apply_favourite_status(%{ id: id } = fizz_buzz, favourite_ids) do
        Map.put(fizz_buzz, :is_favourite, Enum.member?(favourite_ids, id))
    end
end