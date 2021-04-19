package dev.punchcafe.sefizzbuzz.cli.process.browse;

import dev.punchcafe.sefizzbuzz.cli.client.FizzBuzzClient;
import dev.punchcafe.sefizzbuzz.cli.io.UserOutputWriter;
import dev.punchcafe.sefizzbuzz.cli.process.AppProcess;
import lombok.Builder;

import java.util.List;
import java.util.regex.Pattern;

@Builder
public class Browse implements AppProcess {

    private static Pattern CHANGE_PAGE_SIZE = Pattern.compile("psize (\\d)");
    private static Pattern CHANGE_PAGE = Pattern.compile("pjump (\\d)");
    private static Pattern NEXT_PAGE = Pattern.compile("n");
    private static Pattern PREVIOUS_PAGE = Pattern.compile("p");

    // default to page one
    private int page = 1;
    private int pageSize = 15;
    private UserOutputWriter userOutputWriter;
    private FizzBuzzClient fizzBuzzClient;

    @Override
    public String getProcessName() {
        return "browse";
    }

    @Override
    public void execute(List<String> args) {
        final var firstPage = fizzBuzzClient.getPage(15, 1);
        userOutputWriter.printToUser(PageRenderer.renderPage(firstPage.getData(), firstPage.getPage()));
    }

}
