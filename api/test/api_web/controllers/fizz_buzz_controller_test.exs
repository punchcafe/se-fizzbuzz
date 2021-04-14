defmodule ApiWeb.FizzBuzzControllerTest do
  use ApiWeb.ConnCase

  test "GET /fizzbuzz/1", %{conn: conn} do
    conn = get(conn, "/fizzbuzz/1")
    body = json_response(conn, 200)
    assert body["value"] == "1"
    assert body["id"] == 1
  end
end
