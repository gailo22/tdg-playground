defmodule TweetAppTest do
  use ExUnit.Case
  doctest TweetApp

  test "greets the world" do
    assert TweetApp.hello() == :world
  end
end
