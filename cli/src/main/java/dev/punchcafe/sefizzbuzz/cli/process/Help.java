package dev.punchcafe.sefizzbuzz.cli.process;

import dev.punchcafe.sefizzbuzz.cli.exception.UnknownArgumentException;
import dev.punchcafe.sefizzbuzz.cli.io.UserOutputWriter;
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
        if (args.size() > 0) {
            throw new UnknownArgumentException(String.join(" ", args));
        }
        out.printToUser(HELP_MESSAGE);
    }
}
