defmodule FizzBuzz.Test do
  use ExUnit.Case

  test "should throw a runtime exception on 0" do
    assert_raise RuntimeError, fn () -> FizzBuzz.fizz_buzz(0) end
  end

  test "should throw a runtime exception on less than 0" do
    assert_raise RuntimeError, fn () -> FizzBuzz.fizz_buzz(-1) end
  end

  test "should return empty on 1" do
    assert FizzBuzz.fizz_buzz(1) == ""
  end

  test "should return empty on 299" do
    assert FizzBuzz.fizz_buzz(299) == ""
  end

  test "should return Fizz on 3" do
    assert FizzBuzz.fizz_buzz(3) == "Fizz"
  end

  test "should return Fizz on 6" do
    assert FizzBuzz.fizz_buzz(6) == "Fizz"
  end

  test "should return Fizz on 297" do
    assert FizzBuzz.fizz_buzz(297) == "Fizz"
  end

  test "should return Buzz on 5" do
    assert FizzBuzz.fizz_buzz(5) == "Buzz"
  end

  test "should return Buzz on 10" do
    assert FizzBuzz.fizz_buzz(10) == "Buzz"
  end

  test "should return Buzz on 95" do
    assert FizzBuzz.fizz_buzz(95) == "Buzz"
  end

  test "should return FizzBuzz on 15" do
    assert FizzBuzz.fizz_buzz(15) == "FizzBuzz"
  end

  test "should return FizzBuzz on 45" do
    assert FizzBuzz.fizz_buzz(45) == "FizzBuzz"
  end

end