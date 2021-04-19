package dev.punchcafe.sefizzbuzz.cli.process;

import dev.punchcafe.sefizzbuzz.cli.client.FizzBuzzClient;
import dev.punchcafe.sefizzbuzz.cli.client.FizzBuzzEntity;
import dev.punchcafe.sefizzbuzz.cli.io.UserOutputWriter;
import lombok.Builder;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Builder
public class Browse implements AppProcess {

    private static Pattern CHANGE_PAGE_SIZE = Pattern.compile("psize (\\d)");
    private static Pattern CHANGE_PAGE = Pattern.compile("pjump (\\d)");
    private static Pattern NEXT_PAGE = Pattern.compile("n");
    private static Pattern PREVIOUS_PAGE = Pattern.compile("p");

    private static String VALUE_ELEMENT_WIDTH = "                    ";

    // default to page one
    private int page = 1;
    private int pageSize = 15;
    private UserOutputWriter userOutputWriter;
    private FizzBuzzClient fizzBuzzClient;

    private String renderPage(final List<FizzBuzzEntity> entities, final Object pageData){
        return entities.stream()
                .map(this::renderFizzBuzzEntity)
                .collect(Collectors.joining(" "));
    }

    private String renderFizzBuzzEntity(final FizzBuzzEntity entity){
        final var missingAmountOfWhiteSpace = VALUE_ELEMENT_WIDTH.length() - entity.getValue().length();
        final var formattedName = entity.getValue() + VALUE_ELEMENT_WIDTH.substring(0, missingAmountOfWhiteSpace);
        return String.format("%d%s. %s  ", entity.getId(), entity.is_favourite() ? "*":" ", formattedName);
    }

    @Override
    public String getProcessName() {
        return "browse";
    }

    @Override
    public void execute(List<String> args) {
        final var firstPage = fizzBuzzClient.getPage(15, 1);
        userOutputWriter.printToUser(renderPage(firstPage.getData(), null));
    }
}
