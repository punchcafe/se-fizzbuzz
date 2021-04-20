package dev.punchcafe.sefizzbuzz.cli.exception;

public class UnknownArgumentException extends RuntimeException {

    public UnknownArgumentException(final String commandName){
        super(String.format("unknown argument: %s", commandName));
    }
}
