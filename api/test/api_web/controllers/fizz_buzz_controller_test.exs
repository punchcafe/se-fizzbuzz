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

  test "GET /fizzbuzz/15: fizzbuzz should be unfavourited by default", %{conn: conn} do
    conn = get(conn, "/fizzbuzz/15")
    body = json_response(conn, 200)
    assert body["value"] == "FizzBuzz"
    assert body["id"] == 15
    assert body["is_favourite"] == false
  end

  test "GET /fizzbuzz: returns first page of default size", %{conn: conn} do
    conn = get(conn, "/fizzbuzz")
    body = json_response(conn, 200)
    data = body["data"]
    page = body["page"]

    expected_data = [%{id: 1, value: "1", is_favourite: false},
                     %{id: 2, value: "2", is_favourite: false},
                     %{id: 3, value: "Fizz", is_favourite: false},
                     %{id: 4, value: "4", is_favourite: false},
                     %{id: 5, value: "Buzz", is_favourite: false}]

    assert page["page_number"] == 1
    assert page["page_size"] == 5
    assert data == expected_data
  end

  test "GET /fizzbuzz: can return specific page of specific size", %{conn: conn} do
    conn = get(conn, "/fizzbuzz?page_size=3&page_number=2")
    body = json_response(conn, 200)
    data = body["data"]
    page = body["page"]

    expected_data = [%{id: 4, value: "4", is_favourite: false},
                     %{id: 5, value: "Buzz", is_favourite: false},
                     %{id: 5, value: "Fizz", is_favourite: false}]

    assert page["page_number"] == 1
    assert page["page_size"] == 5
    assert data == expected_data
  end

  test "PUT /fizzbuzz/15: should favorite fizz buzz", %{conn: conn} do
    conn = put(conn, "/fizzbuzz/15", %{is_favourite: true})
    body = json_response(conn, 200)
    assert body["value"] == "FizzBuzz"
    assert body["id"] == 15
    assert body["is_favourite"] == true
  end

  test "PUT /fizzbuzz/15: should unfavourite selected fizz buzz", %{conn: conn} do
    put(conn, "/fizzbuzz/15", %{is_favourite: true})
    conn = put(conn, "/fizzbuzz/15", %{is_favourite: false})
    body = json_response(conn, 200)
    assert body["value"] == "FizzBuzz"
    assert body["id"] == 15
    assert body["is_favourite"] == false
  end
end
