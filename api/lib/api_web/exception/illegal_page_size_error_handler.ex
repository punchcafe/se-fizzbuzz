defimpl Plug.Exception, for: IllegalPageSizeError do
  def status(_exception), do: 400
end