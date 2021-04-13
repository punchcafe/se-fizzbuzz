defmodule FizzBuzz do
    
    def fizz_buzz(num) do
        %{:number => num, :string_val => ""}
        |> concat_if_multiple_of("Fizz", 3)
        |> concat_if_multiple_of("Buzz", 5)
        |> Map.get(:string_val)
    end

    defp concat_if_multiple_of(map, catstring, multiple) do
        if rem(map[:number], multiple) == 0 do
            %{ :number => div(map[:number],multiple), :string_val => map[:string_val] <> catstring }
        else
            map
        end
    end
end

IO.puts(FizzBuzz.fizz_buzz(3))
IO.puts(FizzBuzz.fizz_buzz(5))
IO.puts(FizzBuzz.fizz_buzz(15))