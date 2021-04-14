defmodule ApiWeb.FizzBuzzController do
  use ApiWeb, :controller

  def get(conn, _params) do
    json(conn, %{:value => "1", :id => 1})
  end
end
