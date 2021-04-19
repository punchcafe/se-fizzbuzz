package dev.punchcafe.sefizzbuzz.cli.io;

import java.util.Scanner;

public class UserInputReader {

    private final Scanner userInput = new Scanner(System.in);

    public String getUserInput(){
        return userInput.nextLine();
    }
}
