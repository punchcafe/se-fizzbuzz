FROM elixir:latest

# Create app directory and copy the Elixir projects into it
RUN mkdir /app
COPY . /app
WORKDIR /app

RUN mix local.hex --force
RUN mix deps.get
RUN mix compile

ENTRYPOINT ["/bin/bash", "./startup.sh"]