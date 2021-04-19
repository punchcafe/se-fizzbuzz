package dev.punchcafe.sefizzbuzz.cli;

import dev.punchcafe.sefizzbuzz.cli.client.FizzBuzzClient;
import dev.punchcafe.sefizzbuzz.cli.config.AppConfig;
import dev.punchcafe.sefizzbuzz.cli.config.AppFactory;
import dev.punchcafe.sefizzbuzz.cli.io.UserInputReader;
import dev.punchcafe.sefizzbuzz.cli.io.UserOutputWriter;
import dev.punchcafe.sefizzbuzz.cli.process.AppProcess;
import dev.punchcafe.sefizzbuzz.cli.snapshot.SnapshotConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import static dev.punchcafe.sefizzbuzz.cli.TestConstants.BROWSE_FIRST_PAGE_RESPONSE;
import static io.github.jsonSnapshot.SnapshotMatcher.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class BrowseTest {


    private FizzBuzzClient fizzBuzzClient;
    private UserOutputWriter userOutputWriter;
    private UserInputReader userInputReader;
    private AppProcess appProcess;

    @BeforeAll
    static void beforeAll(){
        start(SnapshotConfig::asJsonString);
    }

    @AfterAll
    static void afterAll(){
        validateSnapshots();
    }

    @BeforeEach
    void beforeEach() {
        userOutputWriter = Mockito.spy(new UserOutputWriter());
        userInputReader = Mockito.mock(UserInputReader.class);
        fizzBuzzClient = Mockito.mock(FizzBuzzClient.class);
        final var appConfig = AppConfig.builder()
                .userOutputWriter(userOutputWriter)
                .userInputReader(userInputReader)
                .fizzBuzzClient(fizzBuzzClient)
                .build();
        appProcess = new AppFactory(appConfig).buildApp();
    }

    @Test
    void userCanBrowse() {
        // set up expected view
        when(fizzBuzzClient.getPage(eq(5), eq(1)))
                .thenReturn(BROWSE_FIRST_PAGE_RESPONSE);
        // set up user command
        when(userInputReader.getUserInput())
                .thenReturn("exit");
        appProcess.execute(List.of("browse"));
        verify(fizzBuzzClient, times(1)).getPage(5, 1);
        final var userOutputCaptor = ArgumentCaptor.forClass(String.class);
        verify(userOutputWriter, times(1))
                .printToUser(userOutputCaptor.capture());
        expect(userOutputCaptor.getValue()).toMatchSnapshot();
    }
}
