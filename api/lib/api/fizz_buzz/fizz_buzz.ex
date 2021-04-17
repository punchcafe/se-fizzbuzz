defmodule FizzBuzz do

    def fizz_buzz(0)do
        "0"
    end

    def fizz_buzz(number) do
        %{:number => number, :string_val => ""}
        |> concat_if_multiple_of("Fizz", 3)
        |> concat_if_multiple_of("Buzz", 5)
        |> number_if_string_empty()
    end

    defp concat_if_multiple_of(%{number: number, string_val: string_val} = map, catstring, factor) do
        if rem(number, factor) == 0, do: %{ map | :string_val => string_val <> catstring }, else: map
    end

    defp number_if_string_empty(%{number: number, string_val: string_val}) do
        if string_val == "", do: Integer.to_string(number), else: string_val
    end
end