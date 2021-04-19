package dev.punchcafe.sefizzbuzz.cli.config;

import dev.punchcafe.sefizzbuzz.cli.client.FizzBuzzClient;
import dev.punchcafe.sefizzbuzz.cli.io.UserInputReader;
import dev.punchcafe.sefizzbuzz.cli.io.UserOutputWriter;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AppConfig {
    private final UserOutputWriter userOutputWriter;
    private final UserInputReader userInputReader;
    private final FizzBuzzClient fizzBuzzClient;
}
