defmodule FizzBuzz do
    
    def _concat_if_multiple_of(tuple, catstring, multiple) do
        if(rem(elem(tuple,0), multiple)==0) do
            { div(elem(tuple,0),multiple), elem(tuple,1)<>catstring }
        else
            tuple
        end
    end

    def fizz_buzz(num) do
        _concat_if_multiple_of({num,""}, 3, "Fizz")
        |> _concat_if_multiple_of(5, "Buzz")
        |> elem(1)
    end

end

IO.puts(FizzBuzz.fizz_buzz(1))