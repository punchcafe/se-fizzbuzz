package dev.punchcafe.sefizzbuzz.cli.io;

import java.util.Scanner;

public class UserInputReader {

    private final Scanner userInput;

    public UserInputReader(final Scanner userInput){
        this.userInput = userInput;
    }

    public String getUserInput(){
        return userInput.nextLine();
    }
}
