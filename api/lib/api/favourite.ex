defmodule Api.Favourite do
  use Ecto.Schema
  import Ecto.Changeset

  @primary_key {:fizz_buzz_id, :id, autogenerate: false}
  schema "favourites" do
  end

  @doc false
  def changeset(favourite, attrs) do
    favourite
    |> cast(attrs, [:fizz_buzz_id])
    |> validate_required([:fizz_buzz_id])
  end
end
