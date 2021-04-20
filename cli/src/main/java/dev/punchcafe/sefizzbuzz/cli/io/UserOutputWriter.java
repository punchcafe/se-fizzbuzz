package dev.punchcafe.sefizzbuzz.cli.io;

import java.io.PrintStream;

public class UserOutputWriter {

    private final PrintStream outputStream;

    public UserOutputWriter(final PrintStream outputStream){
        this.outputStream = outputStream;
    }

    public void printToUser(final String output){
        outputStream.println(output);
    }
}
