defimpl Plug.Exception, for: FizzBuzzNotFoundError do
  def status(_exception), do: 404
end