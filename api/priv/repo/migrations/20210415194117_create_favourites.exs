defmodule Api.Repo.Migrations.CreateFavourites do
  use Ecto.Migration

  def change do
    create table(:favourites, primary_key: false) do
      add :fizz_buzz_id, :integer, primary_key: true
    end

  end
end
