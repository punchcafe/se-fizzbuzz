package dev.punchcafe.sefizzbuzz.cli.process;

import dev.punchcafe.sefizzbuzz.cli.client.FizzBuzzClient;
import dev.punchcafe.sefizzbuzz.cli.config.AppConfig;
import dev.punchcafe.sefizzbuzz.cli.config.AppFactory;
import dev.punchcafe.sefizzbuzz.cli.io.UserInputReader;
import dev.punchcafe.sefizzbuzz.cli.io.UserOutputWriter;
import dev.punchcafe.sefizzbuzz.cli.config.SnapshotConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import static dev.punchcafe.sefizzbuzz.cli.TestConstants.*;
import static io.github.jsonSnapshot.SnapshotMatcher.*;
import static java.util.stream.Collectors.toList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class BrowseTest {


    private FizzBuzzClient fizzBuzzClient;
    private UserOutputWriter userOutputWriter;
    private UserInputReader userInputReader;
    private AppProcess appProcess;

    @BeforeAll
    static void beforeAll() {
        start(SnapshotConfig::asJsonString);
    }

    @AfterAll
    static void afterAll() {
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
        assertAllRenderedScreensMatchSnapShot(userOutputCaptor);
    }

    @Test
    void userCanQueryHelp() {
        // set up expected view
        when(fizzBuzzClient.getPage(eq(5), eq(1)))
                .thenReturn(BROWSE_FIRST_PAGE_RESPONSE);
        // set up user command
        when(userInputReader.getUserInput())
                .thenReturn("help", "exit");
        appProcess.execute(List.of("browse"));
        verify(fizzBuzzClient, times(1)).getPage(5, 1);
        final var userOutputCaptor = ArgumentCaptor.forClass(String.class);
        verify(userOutputWriter, times(2))
                .printToUser(userOutputCaptor.capture());
        assertAllRenderedScreensMatchSnapShot(userOutputCaptor);
    }

    @Test
    void userCanChangePage() {
        // set up expected view
        when(fizzBuzzClient.getPage(eq(5), eq(1)))
                .thenReturn(BROWSE_FIRST_PAGE_RESPONSE);
        when(fizzBuzzClient.getPage(eq(5), eq(2)))
                .thenReturn(BROWSE_SECOND_PAGE_RESPONSE);
        // set up user commands
        when(userInputReader.getUserInput())
                .thenReturn("n", "exit");
        appProcess.execute(List.of("browse"));

        verify(fizzBuzzClient, times(1)).getPage(5, 1);
        final var userOutputCaptor = ArgumentCaptor.forClass(String.class);
        verify(userOutputWriter, times(2))
                .printToUser(userOutputCaptor.capture());
        assertAllRenderedScreensMatchSnapShot(userOutputCaptor);
    }

    @Test
    void userCanChangePageAndReturn() {
        // set up expected view
        when(fizzBuzzClient.getPage(eq(5), eq(1)))
                .thenReturn(BROWSE_FIRST_PAGE_RESPONSE);
        when(fizzBuzzClient.getPage(eq(5), eq(2)))
                .thenReturn(BROWSE_SECOND_PAGE_RESPONSE);
        // set up user commands
        when(userInputReader.getUserInput())
                .thenReturn("n", "p", "exit");
        appProcess.execute(List.of("browse"));

        verify(fizzBuzzClient, times(2)).getPage(5, 1);
        verify(fizzBuzzClient, times(1)).getPage(5, 2);
        final var userOutputCaptor = ArgumentCaptor.forClass(String.class);
        verify(userOutputWriter, times(3))
                .printToUser(userOutputCaptor.capture());
        assertAllRenderedScreensMatchSnapShot(userOutputCaptor);
    }

    @Test
    void userCantGoBeforePageOne() {
        // set up expected view
        when(fizzBuzzClient.getPage(eq(5), eq(1)))
                .thenReturn(BROWSE_FIRST_PAGE_RESPONSE);
        // set up user commands
        when(userInputReader.getUserInput())
                .thenReturn("p", "exit");
        appProcess.execute(List.of("browse"));

        verify(fizzBuzzClient, times(2)).getPage(5, 1);
        final var userOutputCaptor = ArgumentCaptor.forClass(String.class);
        verify(userOutputWriter, times(2))
                .printToUser(userOutputCaptor.capture());
        assertAllRenderedScreensMatchSnapShot(userOutputCaptor);
    }

    @Test
    void userCanChangePageSize() {
        // set up expected view
        when(fizzBuzzClient.getPage(eq(5), eq(1)))
                .thenReturn(BROWSE_FIRST_PAGE_RESPONSE);
        when(fizzBuzzClient.getPage(eq(7), eq(1)))
                .thenReturn(BROWSE_DIFFERENT_SIZE_PAGE_RESPONSE);
        // set up user commands
        when(userInputReader.getUserInput())
                .thenReturn("psize 7", "exit");
        appProcess.execute(List.of("browse"));

        verify(fizzBuzzClient, times(1)).getPage(5, 1);
        verify(fizzBuzzClient, times(1)).getPage(7, 1);

        final var userOutputCaptor = ArgumentCaptor.forClass(String.class);
        verify(userOutputWriter, times(2))
                .printToUser(userOutputCaptor.capture());
        assertAllRenderedScreensMatchSnapShot(userOutputCaptor);
    }

    @Test
    void userCanJumpPages() {
        // set up expected view
        when(fizzBuzzClient.getPage(eq(5), eq(1)))
                .thenReturn(BROWSE_FIRST_PAGE_RESPONSE);
        when(fizzBuzzClient.getPage(eq(5), eq(3)))
                .thenReturn(BROWSE_THIRD_PAGE_RESPONSE);
        // set up user commands
        when(userInputReader.getUserInput())
                .thenReturn("pjump 3", "exit");
        appProcess.execute(List.of("browse"));

        verify(fizzBuzzClient, times(1)).getPage(5, 1);
        verify(fizzBuzzClient, times(1)).getPage(5, 3);

        final var userOutputCaptor = ArgumentCaptor.forClass(String.class);
        verify(userOutputWriter, times(2))
                .printToUser(userOutputCaptor.capture());
        assertAllRenderedScreensMatchSnapShot(userOutputCaptor);
    }

    @Test
    void handlesExceptionsWithSimpleMessage() {
        // set up expected view
        when(fizzBuzzClient.getPage(eq(5), eq(1)))
                .thenThrow(new RuntimeException("Test Generated Exception!"));
        // set up user commands
        when(userInputReader.getUserInput())
                .thenReturn("exit");
        appProcess.execute(List.of("browse"));

        final var userOutputCaptor = ArgumentCaptor.forClass(String.class);
        verify(userOutputWriter, times(1))
                .printToUser(userOutputCaptor.capture());
        assertAllRenderedScreensMatchSnapShot(userOutputCaptor);
    }


    private void assertAllRenderedScreensMatchSnapShot(final ArgumentCaptor<String> argumentCaptor) {
        expect(argumentCaptor.getAllValues().stream().map(String::trim).collect(toList())).toMatchSnapshot();
    }
}
