package dev.punchcafe.sefizzbuzz.cli.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface FizzBuzzClient {

    @RequestLine("GET /fizzbuzz/{fizzBuzzId}")
    FizzBuzzEntity calculate(@Param("fizzBuzzId") int fizzBuzzId);

    @RequestLine("PUT /fizzbuzz/{fizzBuzzId}")
    @Headers("Content-Type: application/json")
    FizzBuzzEntity updateFavourite(@Param("fizzBuzzId") int fizzBuzzId, FavouritePayload data);
}
