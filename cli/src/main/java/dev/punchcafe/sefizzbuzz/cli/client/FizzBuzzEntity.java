package dev.punchcafe.sefizzbuzz.cli.client;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class FizzBuzzEntity {
    private final int id;
    private final String value;
    private final boolean isFavourite;
}
