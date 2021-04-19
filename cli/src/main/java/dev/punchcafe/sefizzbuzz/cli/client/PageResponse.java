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
        private final boolean has_next_page;
        private final boolean has_previous_page;
        private final int page_number;
        private final int page_size;
    }

    private final List<FizzBuzzEntity> data;
    private final PageData page;
}
