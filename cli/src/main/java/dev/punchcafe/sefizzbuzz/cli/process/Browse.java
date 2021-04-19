package dev.punchcafe.sefizzbuzz.cli.process;

import dev.punchcafe.sefizzbuzz.cli.client.FizzBuzzClient;
import dev.punchcafe.sefizzbuzz.cli.client.FizzBuzzEntity;
import dev.punchcafe.sefizzbuzz.cli.client.PageResponse;
import dev.punchcafe.sefizzbuzz.cli.io.UserOutputWriter;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
public class Browse implements AppProcess {

    private static Pattern CHANGE_PAGE_SIZE = Pattern.compile("psize (\\d)");
    private static Pattern CHANGE_PAGE = Pattern.compile("pjump (\\d)");
    private static Pattern NEXT_PAGE = Pattern.compile("n");
    private static Pattern PREVIOUS_PAGE = Pattern.compile("p");

    private static String VALUE_ELEMENT_WIDTH = "                    ";
    private static int FIZZBUZZ_PER_COLUMN = 3;

    // default to page one
    private int page = 1;
    private int pageSize = 15;
    private UserOutputWriter userOutputWriter;
    private FizzBuzzClient fizzBuzzClient;

    private String renderPage(final List<FizzBuzzEntity> entities, final PageResponse.PageData pageData) {
            return Optional.of(renderFizzBuzzPage(entities))
                    .map(entitiesPage -> prependPageData(entitiesPage, pageData))
                    .map(this::appendInstructions)
                    .get();
    }

    private String renderFizzBuzzPage(final List<FizzBuzzEntity> entities){
        return splitFizzBuzzesIntoLines(entities)
                .collect(Collectors.joining("\n"));
    }

    private String renderFizzBuzzEntity(final FizzBuzzEntity entity) {
        final var missingAmountOfWhiteSpace = VALUE_ELEMENT_WIDTH.length() - entity.getValue().length();
        final var formattedName = entity.getValue() + VALUE_ELEMENT_WIDTH.substring(0, missingAmountOfWhiteSpace);
        return String.format("%d%s. %s  ", entity.getId(), entity.is_favourite() ? "*" : " ", formattedName);
    }

    @Override
    public String getProcessName() {
        return "browse";
    }

    @Override
    public void execute(List<String> args) {
        final var firstPage = fizzBuzzClient.getPage(15, 1);
        userOutputWriter.printToUser(renderPage(firstPage.getData(), firstPage.getPage()));
    }

    private String prependPageData(final String string, final PageResponse.PageData pageData){
        System.out.println(pageData);
        return "PAGE     : " + pageData.getPage_number() + "Displaying : " + pageData.getPage_size() + "entities per page \n"
                +string;
    }

    private String appendInstructions(final String string){
        return string + "\n enter help for list of instructions";
    }

    private Stream<String> splitFizzBuzzesIntoLines(final List<FizzBuzzEntity> entities) {
        final List<Stream<FizzBuzzEntity>> rowsOfEntities = new ArrayList<>();
        final var completeRows = entities.size() / FIZZBUZZ_PER_COLUMN;
        final var lastRowSize = entities.size() % FIZZBUZZ_PER_COLUMN;
        for (int i = 0; i < completeRows; i++) {
            final var startIndex = i * FIZZBUZZ_PER_COLUMN;
            final var endIndexExclusive = startIndex + FIZZBUZZ_PER_COLUMN;
            rowsOfEntities.add(entities.subList(startIndex, endIndexExclusive).stream());
        }
        if (lastRowSize > 0) {
            final Stream<FizzBuzzEntity> lastRow = entities.subList(entities.size() - lastRowSize, entities.size()).stream();
            rowsOfEntities.add(lastRow);
        }
        return rowsOfEntities.stream()
                .map(row -> row.map(this::renderFizzBuzzEntity)
                        .collect(Collectors.joining()));
    }
}
