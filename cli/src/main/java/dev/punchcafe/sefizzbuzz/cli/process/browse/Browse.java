package dev.punchcafe.sefizzbuzz.cli.process.browse;

import dev.punchcafe.sefizzbuzz.cli.client.FizzBuzzClient;
import dev.punchcafe.sefizzbuzz.cli.io.UserInputReader;
import dev.punchcafe.sefizzbuzz.cli.io.UserOutputWriter;
import dev.punchcafe.sefizzbuzz.cli.process.AppProcess;
import dev.punchcafe.sefizzbuzz.cli.utils.ArgumentValidators;
import lombok.Builder;

import java.util.List;
import java.util.regex.Pattern;

import static dev.punchcafe.sefizzbuzz.cli.process.browse.PageRenderer.renderPage;

@Builder
public class Browse implements AppProcess {

    // Input Command Matchers
    private static Pattern CHANGE_PAGE_SIZE = Pattern.compile("psize (\\d+)");
    private static Pattern CHANGE_PAGE = Pattern.compile("pjump (\\d+)");
    private static String NEXT_PAGE = "n";
    private static String PREVIOUS_PAGE = "p";
    private static String EXIT = "exit";
    private static String HELP = "help";

    private int pageNumber;
    private int pageSize;
    private UserOutputWriter userOutputWriter;
    private UserInputReader userInputReader;
    private FizzBuzzClient fizzBuzzClient;

    @Override
    public String getProcessName() {
        return "browse";
    }

    @Override
    public void execute(List<String> args) {
        ArgumentValidators.assertHasNoArguments(args);
        try {
            final var firstPage = fizzBuzzClient.getPage(pageSize, pageNumber);
            userOutputWriter.printToUser(renderPage(firstPage.getData(), firstPage.getPage()));
            executeUserInputLoop();
        } catch (RuntimeException ex) {
            userOutputWriter.printToUser(String.format("something went wrong while browsing: %s", ex.getMessage()));
        }
    }

    private void executeUserInputLoop() {
        var command = userInputReader.getUserInput().trim();
        while (!EXIT.equals(command)) {
            final var changePageNumberMatcher = CHANGE_PAGE.matcher(command);
            final var changePageSizeMatcher = CHANGE_PAGE_SIZE.matcher(command);
            if (HELP.equals(command)) {
                userOutputWriter.printToUser(PageRenderer.renderHelpMessage());
                command = userInputReader.getUserInput();
                continue;
            } else if (NEXT_PAGE.equals(command)) {
                pageNumber++;
            } else if (PREVIOUS_PAGE.equals(command)) {
                if (pageNumber > 1) {
                    pageNumber--;
                }
            } else if (changePageNumberMatcher.matches()) {
                pageNumber = Integer.parseInt(changePageNumberMatcher.group(1));
            } else if (changePageSizeMatcher.matches()) {
                pageSize = Integer.parseInt(changePageSizeMatcher.group(1));
            } else {
                userOutputWriter.printToUser(PageRenderer.renderHelpMessage());
                command = userInputReader.getUserInput();
                continue;
            }
            final var nextPage = fizzBuzzClient.getPage(pageSize, pageNumber);
            userOutputWriter.printToUser(renderPage(nextPage.getData(), nextPage.getPage()));
            command = userInputReader.getUserInput();
        }
    }
}
