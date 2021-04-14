defmodule FizzBuzz do

    def fizz_buzz(number) when number <= 0 do
        raise "Number must be greater than 0"
    end

    def fizz_buzz(number) when number > 0 do
        %{:number => number, :string_val => ""}
        |> concat_if_multiple_of("Fizz", 3)
        |> concat_if_multiple_of("Buzz", 5)
        |> Map.get(:string_val)
    end

    defp concat_if_multiple_of(%{number: number, string_val: string_val} = map, catstring, factor) do
        if rem(number, factor) == 0 do
            %{ :number => div(number,factor), :string_val => string_val <> catstring }
        else
            map
        end
    end
end