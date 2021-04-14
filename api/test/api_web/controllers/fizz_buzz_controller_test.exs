defmodule ApiWeb.FizzBuzzControllerTest do
  use ApiWeb.ConnCase

  test "GET /fizzbuzz/1", %{conn: conn} do
    conn = get(conn, "/fizzbuzz/1")
    body = json_response(conn, 200)
    assert body["value"] == "1"
    assert body["id"] == 1
  end

  test "GET /fizzbuzz/3", %{conn: conn} do
    conn = get(conn, "/fizzbuzz/3")
    body = json_response(conn, 200)
    assert body["value"] == "Fizz"
    assert body["id"] == 3
  end

  test "GET /fizzbuzz/5", %{conn: conn} do
    conn = get(conn, "/fizzbuzz/5")
    body = json_response(conn, 200)
    assert body["value"] == "Buzz"
    assert body["id"] == 5
  end

    test "GET /fizzbuzz/15", %{conn: conn} do
    conn = get(conn, "/fizzbuzz/15")
    body = json_response(conn, 200)
    assert body["value"] == "FizzBuzz"
    assert body["id"] == 15
  end
end
