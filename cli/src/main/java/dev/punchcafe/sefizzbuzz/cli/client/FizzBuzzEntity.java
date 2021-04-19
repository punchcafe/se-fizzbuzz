package dev.punchcafe.sefizzbuzz.cli.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FizzBuzzEntity {
    private final int id;
    private final String value;
    private final boolean is_favourite;
}
