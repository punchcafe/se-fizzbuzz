package dev.punchcafe.sefizzbuzz.cli.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PageResponse {

    @AllArgsConstructor
    @Getter
    public static class PageData {
        private final boolean hasNextPage;
        private final boolean hasPreviousPage;
        private final int pageNumber;
        private final int pageSize;
    }

    private final List<FizzBuzzEntity> data;
    private final PageData pageData;
}
