package dev.punchcafe.sefizzbuzz.cli.process;

import dev.punchcafe.sefizzbuzz.cli.io.UserOutputWriter;
import dev.punchcafe.sefizzbuzz.cli.utils.ArgumentValidators;
import lombok.AllArgsConstructor;

import java.util.List;

import static dev.punchcafe.sefizzbuzz.cli.constant.MessageConstants.HELP_MESSAGE;

@AllArgsConstructor
public class Help implements AppProcess {

    private static String HELP_NAME = "help";

    private final UserOutputWriter out;

    @Override
    public String getProcessName() {
        return HELP_NAME;
    }

    @Override
    public void execute(List<String> args) {
        ArgumentValidators.assertHasNoArguments(args);
        out.printToUser(HELP_MESSAGE);
    }
}
