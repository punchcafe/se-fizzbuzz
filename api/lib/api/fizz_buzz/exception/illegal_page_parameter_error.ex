defmodule IllegalPageParametersError do
  defexception message: "Page size must not be less than one or greater than 200, and page number must be 1 or greater."
end