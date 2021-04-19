package dev.punchcafe.sefizzbuzz.cli.process.browse;

import dev.punchcafe.sefizzbuzz.cli.client.FizzBuzzClient;
import dev.punchcafe.sefizzbuzz.cli.io.UserInputReader;
import dev.punchcafe.sefizzbuzz.cli.io.UserOutputWriter;
import dev.punchcafe.sefizzbuzz.cli.process.AppProcess;
import lombok.Builder;

import java.util.List;
import java.util.regex.Pattern;

import static dev.punchcafe.sefizzbuzz.cli.process.browse.PageRenderer.renderPage;

@Builder
public class Browse implements AppProcess {

    private static Pattern CHANGE_PAGE_SIZE = Pattern.compile("psize (\\d+)");
    private static Pattern CHANGE_PAGE = Pattern.compile("pjump (\\d+)");
    private static String NEXT_PAGE = "n";
    private static String PREVIOUS_PAGE = "p";
    private static String EXIT = "exit";
    private static String HELP = "help";
    private static String HELP_MESSAGE = "available commands are: p, n, psize NUM, pjump NUM, help, exit";
    // default to page one
    private int page;
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
        final var firstPage = fizzBuzzClient.getPage(pageSize, page);
        userOutputWriter.printToUser(renderPage(firstPage.getData(), firstPage.getPage()));
        var command = userInputReader.getUserInput().trim();
        while(!EXIT.equals(command)){
            final var changePageNumberMatcher = CHANGE_PAGE.matcher(command);
            final var changePageSizeMatcher = CHANGE_PAGE_SIZE.matcher(command);
            if(HELP.equals(command)){
                userOutputWriter.printToUser(HELP_MESSAGE);
                command = userInputReader.getUserInput();
                continue;
            } else if(NEXT_PAGE.equals(command)){
                page++;
            } else if(PREVIOUS_PAGE.equals(command)){
                if(page > 1){
                    page--;
                }
            } else if(changePageNumberMatcher.matches()){
                page = Integer.parseInt(changePageNumberMatcher.group(1));
            } else if(changePageSizeMatcher.matches()){
                System.out.println("you got it!");
                pageSize = Integer.parseInt(changePageSizeMatcher.group(1));
            }
            final var nextPage = fizzBuzzClient.getPage(pageSize, page);
            userOutputWriter.printToUser(renderPage(nextPage.getData(), nextPage.getPage()));
            command = userInputReader.getUserInput();
        }
    }
}
