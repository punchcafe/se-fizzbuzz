package dev.punchcafe.sefizzbuzz.cli.utils;

import dev.punchcafe.sefizzbuzz.cli.client.FizzBuzzEntity;

public interface Formatting {

    static String formatFizzBuzzValue(final FizzBuzzEntity entity) {
        return String.format("Result:  id: %d,  value: %s", entity.getId(), entity.getValue());
    }
}
