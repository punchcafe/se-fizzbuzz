package dev.punchcafe.sefizzbuzz.cli.exception;

import java.util.List;

public class MissingCommandException extends RuntimeException {

    //TODO: ensure this error message indicates possible commands
    public MissingCommandException(final List<String> options){}
}
