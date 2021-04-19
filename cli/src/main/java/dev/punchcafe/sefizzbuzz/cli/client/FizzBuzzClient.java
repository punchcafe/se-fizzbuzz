package dev.punchcafe.sefizzbuzz.cli.client;

import feign.Param;
import feign.RequestLine;

public interface FizzBuzzClient {

    @RequestLine("GET /fizzbuzz/{fizzBuzzId}")
    FizzBuzzEntity calculate(@Param("fizzBuzzId") int fizzBuzzId);
}
