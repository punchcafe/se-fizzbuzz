defimpl Plug.Exception, for: IllegalPageParametersError do
  def status(_exception), do: 400
end