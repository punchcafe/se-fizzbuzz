package dev.punchcafe.sefizzbuzz.cli.exception;

import java.util.List;

public class MissingCommandException extends RuntimeException {

    public MissingCommandException(final List<String> options){
        super(String.format("command needed, available options are: %s", String.join(" ", options)));
    }
}
