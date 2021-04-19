/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package dev.punchcafe.sefizzbuzz.cli;

import dev.punchcafe.sefizzbuzz.cli.config.AppConfig;
import dev.punchcafe.sefizzbuzz.cli.config.AppFactory;
import dev.punchcafe.sefizzbuzz.cli.io.UserOutputWriter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static dev.punchcafe.sefizzbuzz.cli.constant.MessageConstants.HELP_MESSAGE;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AppTest {

    @Test
    void userCanCallHelpCommand() {
        UserOutputWriter mockOutput = Mockito.spy(new UserOutputWriter());
        final var appConfig = AppConfig.builder()
                .userOutputWriter(mockOutput)
                .build();
        final var app = new AppFactory(appConfig).buildApp();
        app.execute(List.of("help"));
        verify(mockOutput, times(1)).printToUser(HELP_MESSAGE);
    }
}