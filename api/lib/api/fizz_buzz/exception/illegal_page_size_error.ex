defmodule IllegalPageSizeError do
  defexception message: "Page size must not be less than one or greater than 200"
end