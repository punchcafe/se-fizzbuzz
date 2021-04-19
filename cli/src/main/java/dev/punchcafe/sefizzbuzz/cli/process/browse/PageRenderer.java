package dev.punchcafe.sefizzbuzz.cli.process.browse;

import dev.punchcafe.sefizzbuzz.cli.client.FizzBuzzEntity;
import dev.punchcafe.sefizzbuzz.cli.client.PageResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dev.punchcafe.sefizzbuzz.cli.process.browse.RenderConstants.*;

public class PageRenderer {

    static String renderHelpMessage() {
        return HELP_MESSAGE;
    }

    static String renderPage(final List<FizzBuzzEntity> entities, final PageResponse.PageData pageData) {
        return Optional.of(renderFizzBuzzPage(entities))
                .map(entitiesPage -> prependPageData(entitiesPage, pageData))
                .map(PageRenderer::appendInstructions)
                .map(PageRenderer::prependScreenClear)
                .get();
    }

    private static String renderFizzBuzzPage(final List<FizzBuzzEntity> entities) {
        return splitFizzBuzzesIntoRenderedRows(entities)
                .collect(Collectors.joining("\n"));
    }

    private static Stream<String> splitFizzBuzzesIntoRenderedRows(final List<FizzBuzzEntity> entities) {
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

    private static String prependPageData(final String string, final PageResponse.PageData pageData) {
        return String.format("PAGE     :  %d  Displaying : %d entities per page \n %s",
                pageData.getPage_number(),
                pageData.getPage_size(),
                string);
    }

    private static String prependScreenClear(String string) {
        return CLEAR_PAGE_BLOCK + string;
    }

    private static String renderFizzBuzzEntity(final FizzBuzzEntity entity) {
        final var missingAmountOfWhiteSpace = VALUE_ELEMENT_WIDTH.length() - entity.getValue().length();
        final var formattedName = entity.getValue() + VALUE_ELEMENT_WIDTH.substring(0, missingAmountOfWhiteSpace);
        final var favouriteSymbol = entity.is_favourite() ? FAVOURITE_FIZZ_BUZZ_SYMBOL : NOT_FAVOURITE_FIZZ_BUZZ_SYMBOL;
        return String.format("%d%s. %s  ", entity.getId(), favouriteSymbol, formattedName);
    }

    private static String appendInstructions(final String string) {
        return string.concat("\n enter help for list of instructions");
    }

}
