package dev.punchcafe.sefizzbuzz.cli.config;

import dev.punchcafe.sefizzbuzz.cli.client.FizzBuzzClient;
import dev.punchcafe.sefizzbuzz.cli.io.UserOutputWriter;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AppConfig {
    private final UserOutputWriter userOutputWriter;
    private final FizzBuzzClient fizzBuzzClient;
}
