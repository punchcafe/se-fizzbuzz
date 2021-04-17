defimpl Plug.Exception, for: FizzBuzzPageNotFoundError do
  def status(_exception), do: 404
end