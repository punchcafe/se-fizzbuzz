defmodule FizzBuzz.Test do
  use ExUnit.Case

  test "should return 0 on 0" do
    assert FizzBuzz.fizz_buzz(0) == "0"
  end

  test "should return number on -1" do
    assert FizzBuzz.fizz_buzz(-1) == "-1"
  end

  test "should return number on -17" do
    assert FizzBuzz.fizz_buzz(-17) == "-17"
  end

  test "should return Fizz on -6" do
    assert FizzBuzz.fizz_buzz(-6) == "Fizz"
  end

  test "should return Buzz on -10" do
    assert FizzBuzz.fizz_buzz(-10) == "Buzz"
  end

  test "should return FizzBuzz on -30" do
    assert FizzBuzz.fizz_buzz(-30) == "FizzBuzz"
  end

  test "should return number on 1" do
    assert FizzBuzz.fizz_buzz(1) == "1"
  end

  test "should return number on 299" do
    assert FizzBuzz.fizz_buzz(299) == "299"
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