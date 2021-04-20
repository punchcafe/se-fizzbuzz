/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package dev.punchcafe.sefizzbuzz.cli.process;

import dev.punchcafe.sefizzbuzz.cli.client.FizzBuzzClient;
import dev.punchcafe.sefizzbuzz.cli.config.AppConfig;
import dev.punchcafe.sefizzbuzz.cli.config.AppFactory;
import dev.punchcafe.sefizzbuzz.cli.exception.MissingCommandException;
import dev.punchcafe.sefizzbuzz.cli.exception.UnknownArgumentException;
import dev.punchcafe.sefizzbuzz.cli.io.UserInputReader;
import dev.punchcafe.sefizzbuzz.cli.io.UserOutputWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Scanner;

import static dev.punchcafe.sefizzbuzz.cli.constant.MessageConstants.HELP_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class RootTest {

    private FizzBuzzClient fizzBuzzClient;
    private UserOutputWriter userOutputWriter;
    private UserInputReader userInputReader;
    private Scanner inputScanner;
    private AppProcess appProcess;

    @BeforeEach
    void beforeEach() {
        inputScanner = Mockito.mock(Scanner.class);
        userOutputWriter = Mockito.spy(new UserOutputWriter(System.out));
        userInputReader = new UserInputReader(inputScanner);
        fizzBuzzClient = Mockito.mock(FizzBuzzClient.class);
        final var appConfig = AppConfig.builder()
                .userOutputWriter(userOutputWriter)
                .userInputReader(userInputReader)
                .fizzBuzzClient(fizzBuzzClient)
                .build();
        appProcess = new AppFactory(appConfig).buildApp();
    }

    @Test
    void appThrowsExceptionIfUserHasntProvidedArgs() {
        assertThrows(MissingCommandException.class, () -> appProcess.execute(List.of()));
    }

    @Test
    void appThrowsExceptionIfUserHasProvidedUnknownArgs() {
        assertThrows(UnknownArgumentException.class, () -> appProcess.execute(List.of("hello-world")));
    }
}
