package dev.punchcafe.sefizzbuzz.cli.process.browse;

import dev.punchcafe.sefizzbuzz.cli.client.FizzBuzzEntity;
import dev.punchcafe.sefizzbuzz.cli.client.PageResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PageRenderer {

    private static String VALUE_ELEMENT_WIDTH = "                    ";
    private static int FIZZBUZZ_PER_COLUMN = 3;
    private static String CLEAR_PAGE_BLOCK =
            "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                    + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                    + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                    + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                    + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";

    static String renderPage(final List<FizzBuzzEntity> entities, final PageResponse.PageData pageData) {
        return Optional.of(renderFizzBuzzPage(entities))
                .map(entitiesPage -> prependPageData(entitiesPage, pageData))
                .map(PageRenderer::appendInstructions)
                .map(PageRenderer::prependScreenClear)
                .get();
    }

    private static String renderFizzBuzzPage(final List<FizzBuzzEntity> entities) {
        return splitFizzBuzzesIntoLines(entities)
                .collect(Collectors.joining("\n"));
    }

    private static String prependScreenClear(String string) {
        return CLEAR_PAGE_BLOCK + string;
    }

    private static String renderFizzBuzzEntity(final FizzBuzzEntity entity) {
        final var missingAmountOfWhiteSpace = VALUE_ELEMENT_WIDTH.length() - entity.getValue().length();
        final var formattedName = entity.getValue() + VALUE_ELEMENT_WIDTH.substring(0, missingAmountOfWhiteSpace);
        return String.format("%d%s. %s  ", entity.getId(), entity.is_favourite() ? "*" : " ", formattedName);
    }

    private static String prependPageData(final String string, final PageResponse.PageData pageData) {
        System.out.println(pageData);
        return "PAGE     : " + pageData.getPage_number() + "Displaying : " + pageData.getPage_size() + "entities per page \n"
                + string;
    }

    private static String appendInstructions(final String string) {
        return string + "\n enter help for list of instructions";
    }

    private static Stream<String> splitFizzBuzzesIntoLines(final List<FizzBuzzEntity> entities) {
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
                .map(row -> row.map(PageRenderer::renderFizzBuzzEntity)
                        .collect(Collectors.joining()));
    }
}
