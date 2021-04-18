package dev.punchcafe.sefizzbuzz.cli.process;

import dev.punchcafe.sefizzbuzz.cli.exception.UnknownArgumentException;
import dev.punchcafe.sefizzbuzz.cli.io.UserOutputWriter;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class Help implements AppProcess {

    private static String HELP_NAME = "help";
    private static String HELP_GUIDE = "WELCOME TO THE FIZZBUZZ CLI";

    private final UserOutputWriter out;

    @Override
    public String getProcessName() {
        return HELP_NAME;
    }

    @Override
    public void execute(List<String> args) {
        if (args.size() > 0) {
            throw new UnknownArgumentException(String.join(" ", args));
        }
        out.printToUser(HELP_GUIDE);
    }
}
