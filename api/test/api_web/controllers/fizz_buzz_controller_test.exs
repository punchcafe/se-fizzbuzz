defmodule ApiWeb.FizzBuzzControllerTest do
  use ApiWeb.ConnCase

  test "GET /idontexist: should return JSON not found", %{conn: conn} do
    {code, headers, message} = assert_error_sent 404, fn () -> get(conn, "/idontexist") end
    assert message == "\"Not Found\""
    assert Enum.at(headers,0) == {"content-type", "application/json; charset=utf-8"}
  end

  test "GET /fizzbuzz/0: should raise 404", %{conn: conn} do
    {code, headers, message} = assert_error_sent 404, fn () -> get(conn, "/fizzbuzz/0") end
    assert message == "{\"errors\":{\"detail\":\"No FizzBuzz found for id. Valid fizz buzz ids are between 1 - 100,000,000\"}}"
  end

  test "GET /fizzbuzz/100000001: should raise 404", %{conn: conn} do
    {code, headers, message} = assert_error_sent 404, fn () -> get(conn, "/fizzbuzz/100000001") end
    assert message == "{\"errors\":{\"detail\":\"No FizzBuzz found for id. Valid fizz buzz ids are between 1 - 100,000,000\"}}"
  end

  test "GET /fizzbuzz/1", %{conn: conn} do
    conn = get(conn, "/fizzbuzz/1")
    body = json_response(conn, 200)
    assert body["value"] == "1"
    assert body["id"] == 1
  end

  test "GET /fizzbuzz/100000000", %{conn: conn} do
    conn = get(conn, "/fizzbuzz/100000000")
    body = json_response(conn, 200)
    assert body["value"] == "Buzz"
    assert body["id"] == 100000000
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

  test "GET /fizzbuzz: returns first page of default size when neither page number or size specified", %{conn: conn} do
    conn = get(conn, "/fizzbuzz")
    body = json_response(conn, 200)
    data = body["data"]
    page = body["page"]

    expected_data = [%{"id" => 1, "value" => "1", "is_favourite" => false},
                     %{"id" => 2, "value" => "2", "is_favourite" => false},
                     %{"id" => 3, "value" => "Fizz", "is_favourite" => false},
                     %{"id" => 4, "value" => "4", "is_favourite" => false},
                     %{"id" => 5, "value" => "Buzz", "is_favourite" => false}]

    assert page["page_number"] == 1
    assert page["page_size"] == 5
    assert data == expected_data
  end

  test "GET /fizzbuzz?page_number=1: should have next page but not previous page", %{conn: conn} do
    conn = get(conn, "/fizzbuzz?page_number=1")
    body = json_response(conn, 200)
    page = body["page"]

    assert page["page_number"] == 1
    assert page["page_size"] == 5
    assert page["has_next_page"] == true
    assert page["has_previous_page"] == false
  end

  test "GET /fizzbuzz?page_number=2: should have next page and previous page", %{conn: conn} do
    conn = get(conn, "/fizzbuzz?page_number=2")
    body = json_response(conn, 200)
    page = body["page"]

    assert page["page_number"] == 2
    assert page["page_size"] == 5
    assert page["has_next_page"] == true
    assert page["has_previous_page"] == true
  end

  test "GET /fizzbuzz: last page should not have next page", %{conn: conn} do
    conn = get(conn, "/fizzbuzz?page_size=3&page_number=33333334")
    body = json_response(conn, 200)
    page = body["page"]

    assert page["page_number"] == 33333334
    assert page["page_size"] == 3
    assert page["has_next_page"] == false
    assert page["has_previous_page"] == true
  end

  test "GET /fizzbuzz?page_size=0: returns 400 for page size too small", %{conn: conn} do
    {code, headers, message} = assert_error_sent 400, fn () -> get(conn, "/fizzbuzz?page_size=0") end
    assert message == "{\"errors\":{\"detail\":\"Page size must not be less than one or greater than 200, and page number must be 1 or greater.\"}}"
  end

  test "GET /fizzbuzz?page_size=-2: returns 400 for page size too small", %{conn: conn} do
    {code, headers, message} = assert_error_sent 400, fn () -> get(conn, "/fizzbuzz?page_size=-2") end
    assert message == "{\"errors\":{\"detail\":\"Page size must not be less than one or greater than 200, and page number must be 1 or greater.\"}}"
  end

  test "GET /fizzbuzz?page_size=201: returns 400 for page size too large", %{conn: conn} do
    {code, headers, message} = assert_error_sent 400, fn () -> get(conn, "/fizzbuzz?page_size=201") end
    assert message == "{\"errors\":{\"detail\":\"Page size must not be less than one or greater than 200, and page number must be 1 or greater.\"}}"
  end

  test "GET /fizzbuzz?page_number=0: returns 400 for page number invalid", %{conn: conn} do
    {code, headers, message} = assert_error_sent 400, fn () -> get(conn, "/fizzbuzz?page_number=0") end
    assert message == "{\"errors\":{\"detail\":\"Page size must not be less than one or greater than 200, and page number must be 1 or greater.\"}}"
  end

  test "GET /fizzbuzz?page_number=-1: returns 400 for page number invalid", %{conn: conn} do
    {code, headers, message} = assert_error_sent 400, fn () -> get(conn, "/fizzbuzz?page_number=-1") end
    assert message == "{\"errors\":{\"detail\":\"Page size must not be less than one or greater than 200, and page number must be 1 or greater.\"}}"
  end

  test "GET /fizzbuzz?page_size=100&page_number=1000001: returns 404 for page number out of range", %{conn: conn} do
    {code, headers, message} = assert_error_sent 404, fn () -> get(conn, "/fizzbuzz?page_size=100&page_number=1000001") end
    assert message == "{\"errors\":{\"detail\":\"Page not found. Have you gone past 100,000,000 entities?\"}}"
  end

  test "GET /fizzbuzz: paginated data contains favourite data", %{conn: conn} do
    put(conn, "/fizzbuzz/1", %{is_favourite: true})
    put(conn, "/fizzbuzz/3", %{is_favourite: true})
    conn = get(conn, "/fizzbuzz")
    body = json_response(conn, 200)
    data = body["data"]
    page = body["page"]

    expected_data = [%{"id" => 1, "value" => "1", "is_favourite" => true},
                     %{"id" => 2, "value" => "2", "is_favourite" => false},
                     %{"id" => 3, "value" => "Fizz", "is_favourite" => true},
                     %{"id" => 4, "value" => "4", "is_favourite" => false},
                     %{"id" => 5, "value" => "Buzz", "is_favourite" => false}]

    assert page["page_number"] == 1
    assert page["page_size"] == 5
    assert data == expected_data
  end

  test "GET /fizzbuzz: returns first page when no page number provided", %{conn: conn} do
    conn = get(conn, "/fizzbuzz?page_size=3")
    body = json_response(conn, 200)
    data = body["data"]
    page = body["page"]

    expected_data = [%{"id" => 1, "value" => "1", "is_favourite" => false},
                     %{"id" => 2, "value" => "2", "is_favourite" => false},
                     %{"id" => 3, "value" => "Fizz", "is_favourite" => false}]

    assert page["page_number"] == 1
    assert page["page_size"] == 3
    assert data == expected_data
  end

  test "GET /fizzbuzz: returns default page size when no page size provided", %{conn: conn} do
    conn = get(conn, "/fizzbuzz?page_number=2")
    body = json_response(conn, 200)
    data = body["data"]
    page = body["page"]

    expected_data = [%{"id" => 6, "value" => "Fizz", "is_favourite" => false},
                     %{"id" => 7, "value" => "7", "is_favourite" => false},
                     %{"id" => 8, "value" => "8", "is_favourite" => false},
                     %{"id" => 9, "value" => "Fizz", "is_favourite" => false},
                     %{"id" => 10, "value" => "Buzz", "is_favourite" => false}]

    assert page["page_number"] == 2
    assert page["page_size"] == 5
    assert data == expected_data
  end

  test "GET /fizzbuzz: can return specific page of specific size", %{conn: conn} do
    conn = get(conn, "/fizzbuzz?page_size=3&page_number=2")
    body = json_response(conn, 200)
    data = body["data"]
    page = body["page"]

    expected_data = [%{"id" => 4, "value" => "4", "is_favourite" => false},
                     %{"id" => 5, "value" => "Buzz", "is_favourite" => false},
                     %{"id" => 6, "value" => "Fizz", "is_favourite" => false}]

    assert page["page_number"] == 2
    assert page["page_size"] == 3
    assert data == expected_data
  end

  test "GET /fizzbuzz: returns upto but not past fizz buzz id: 100000000", %{conn: conn} do
    conn = get(conn, "/fizzbuzz?page_size=3&page_number=33333334")
    body = json_response(conn, 200)
    data = body["data"]
    page = body["page"]

    expected_data = [%{"id" => 100000000, "value" => "Buzz", "is_favourite" => false}]

    assert page["page_number"] == 33333334
    assert page["page_size"] == 3
    assert data == expected_data
  end

  test "PUT /fizzbuzz/15: should favorite fizz buzz", %{conn: conn} do
    conn = put(conn, "/fizzbuzz/15", %{is_favourite: true})
    body = json_response(conn, 200)
    assert body["value"] == "FizzBuzz"
    assert body["id"] == 15
    assert body["is_favourite"] == true
  end

    test "PUT /fizzbuzz/15: should not fail on favouriting same fizzbuzz twice", %{conn: conn} do
    put(conn, "/fizzbuzz/15", %{is_favourite: true})
    conn = put(conn, "/fizzbuzz/15", %{is_favourite: true})
    body = json_response(conn, 200)
    assert body["value"] == "FizzBuzz"
    assert body["id"] == 15
    assert body["is_favourite"] == true
  end

  test "PUT /fizzbuzz/100000000: should favorite fizz buzz at limit", %{conn: conn} do
    conn = put(conn, "/fizzbuzz/100000000", %{is_favourite: true})
    body = json_response(conn, 200)
    assert body["value"] == "Buzz"
    assert body["id"] == 100000000
    assert body["is_favourite"] == true
  end

  test "PUT /fizzbuzz/100000001: should return 404 when attempting to favourite out of range fizz buzz", %{conn: conn} do
    {code, headers, message} = assert_error_sent 404, fn () -> put(conn, "/fizzbuzz/100000001", %{is_favourite: true}) end
    assert message == "{\"errors\":{\"detail\":\"No FizzBuzz found for id. Valid fizz buzz ids are between 1 - 100,000,000\"}}"
  end

  test "PUT /fizzbuzz/0: should return 404 when attempting to favourite out of range fizz buzz", %{conn: conn} do
    {code, headers, message} = assert_error_sent 404, fn () -> put(conn, "/fizzbuzz/0", %{is_favourite: true}) end
    assert message == "{\"errors\":{\"detail\":\"No FizzBuzz found for id. Valid fizz buzz ids are between 1 - 100,000,000\"}}"
  end

  test "PUT /fizzbuzz/-1: should return 404 when attempting to favourite out of range fizz buzz", %{conn: conn} do
    {code, headers, message} = assert_error_sent 404, fn () -> put(conn, "/fizzbuzz/-1", %{is_favourite: true}) end
    assert message == "{\"errors\":{\"detail\":\"No FizzBuzz found for id. Valid fizz buzz ids are between 1 - 100,000,000\"}}"
  end

  test "PUT /fizzbuzz/100000001: should return 404 when attempting to unfavourite out of range fizz buzz", %{conn: conn} do
    {code, headers, message} = assert_error_sent 404, fn () -> put(conn, "/fizzbuzz/100000001", %{is_favourite: false}) end
    assert message == "{\"errors\":{\"detail\":\"No FizzBuzz found for id. Valid fizz buzz ids are between 1 - 100,000,000\"}}"
  end

  test "PUT /fizzbuzz/0: should return 404 when attempting to unfavourite out of range fizz buzz", %{conn: conn} do
    {code, headers, message} = assert_error_sent 404, fn () -> put(conn, "/fizzbuzz/0", %{is_favourite: false}) end
    assert message == "{\"errors\":{\"detail\":\"No FizzBuzz found for id. Valid fizz buzz ids are between 1 - 100,000,000\"}}"
  end

  test "PUT /fizzbuzz/-1: should return 404 when attempting to unfavourite out of range fizz buzz", %{conn: conn} do
    {code, headers, message} = assert_error_sent 404, fn () -> put(conn, "/fizzbuzz/-5", %{is_favourite: false}) end
    assert message == "{\"errors\":{\"detail\":\"No FizzBuzz found for id. Valid fizz buzz ids are between 1 - 100,000,000\"}}"
  end

  test "PUT /fizzbuzz/15: should unfavourite selected fizz buzz", %{conn: conn} do
    put(conn, "/fizzbuzz/15", %{is_favourite: true})
    conn = put(conn, "/fizzbuzz/15", %{is_favourite: false})
    body = json_response(conn, 200)
    assert body["value"] == "FizzBuzz"
    assert body["id"] == 15
    assert body["is_favourite"] == false
  end

  test "PUT /fizzbuzz/35: should not fail on unfavourite not favourited fizz buzz", %{conn: conn} do
    conn = put(conn, "/fizzbuzz/35", %{is_favourite: false})
    body = json_response(conn, 200)
    assert body["value"] == "Buzz"
    assert body["id"] == 35
    assert body["is_favourite"] == false
  end

  test "PUT /fizzbuzz/40: should not fail on unfavourite fizz buzz twice", %{conn: conn} do
    put(conn, "/fizzbuzz/40", %{is_favourite: true})
    put(conn, "/fizzbuzz/40", %{is_favourite: false})
    conn = put(conn, "/fizzbuzz/40", %{is_favourite: false})
    body = json_response(conn, 200)
    assert body["value"] == "Buzz"
    assert body["id"] == 40
    assert body["is_favourite"] == false
  end
end
