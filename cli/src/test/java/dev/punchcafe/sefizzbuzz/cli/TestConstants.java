package dev.punchcafe.sefizzbuzz.cli;

import dev.punchcafe.sefizzbuzz.cli.client.FizzBuzzEntity;
import dev.punchcafe.sefizzbuzz.cli.client.PageResponse;

import java.util.List;

public interface TestConstants {

    /**
     * BROWSING TEST CONSTANTS
     */

    // FIRST PAGE

    List<FizzBuzzEntity> BROWSE_FIRST_PAGE_FIZZ_BUZZ = List.of(
            new FizzBuzzEntity(1, "1", false),
            new FizzBuzzEntity(2, "2", false),
            new FizzBuzzEntity(3, "Fizz", false),
            new FizzBuzzEntity(4, "4", false),
            new FizzBuzzEntity(5, "Buzz", false));

    PageResponse.PageData BROWSE_FIRST_PAGE_PAGE_DATA = new PageResponse.PageData(true, false, 1, 5);

    PageResponse BROWSE_FIRST_PAGE_RESPONSE = new PageResponse(
            BROWSE_FIRST_PAGE_FIZZ_BUZZ,
            BROWSE_FIRST_PAGE_PAGE_DATA);

    // SECOND PAGE

    List<FizzBuzzEntity> BROWSE_SECOND_PAGE_FIZZ_BUZZ = List.of(
            new FizzBuzzEntity(6, "Fizz", false),
            new FizzBuzzEntity(7, "7", false),
            new FizzBuzzEntity(8, "8", false),
            new FizzBuzzEntity(9, "Fizz", false),
            new FizzBuzzEntity(10, "Buzz", false));

    PageResponse.PageData BROWSE_SECOND_PAGE_PAGE_DATA = new PageResponse.PageData(true, true, 2, 5);

    PageResponse BROWSE_SECOND_PAGE_RESPONSE = new PageResponse(
            BROWSE_SECOND_PAGE_FIZZ_BUZZ,
            BROWSE_SECOND_PAGE_PAGE_DATA);

    // THIRD PAGE

    List<FizzBuzzEntity> BROWSE_THIRD_PAGE_FIZZ_BUZZ = List.of(
            new FizzBuzzEntity(11, "11", false),
            new FizzBuzzEntity(12, "Fizz", false),
            new FizzBuzzEntity(13, "13", false),
            new FizzBuzzEntity(14, "14", false),
            new FizzBuzzEntity(15, "FizzBuzz", false));

    PageResponse.PageData BROWSE_THIRD_PAGE_PAGE_DATA = new PageResponse.PageData(true, true, 3, 5);

    PageResponse BROWSE_THIRD_PAGE_RESPONSE = new PageResponse(
            BROWSE_THIRD_PAGE_FIZZ_BUZZ,
            BROWSE_THIRD_PAGE_PAGE_DATA);


    // DIFFERENT SIZE PAGE

    List<FizzBuzzEntity> BROWSE_DIFFERENT_SIZE_PAGE_FIZZ_BUZZ = List.of(
            new FizzBuzzEntity(1, "1", false),
            new FizzBuzzEntity(2, "2", false),
            new FizzBuzzEntity(3, "Fizz", true),
            new FizzBuzzEntity(4, "4", false),
            new FizzBuzzEntity(5, "Buzz", false),
            new FizzBuzzEntity(6, "Fizz", false));

    PageResponse.PageData BROWSE_DIFFERENT_SIZE_PAGE_DATA = new PageResponse.PageData(true, false, 1, 6);

    PageResponse BROWSE_DIFFERENT_SIZE_PAGE_RESPONSE = new PageResponse(
            BROWSE_DIFFERENT_SIZE_PAGE_FIZZ_BUZZ,
            BROWSE_DIFFERENT_SIZE_PAGE_DATA);

}
