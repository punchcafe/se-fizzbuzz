package dev.punchcafe.sefizzbuzz.cli.process;

import dev.punchcafe.sefizzbuzz.cli.client.FizzBuzzClient;
import dev.punchcafe.sefizzbuzz.cli.client.FizzBuzzEntity;
import dev.punchcafe.sefizzbuzz.cli.exception.ArgumentNeededException;
import dev.punchcafe.sefizzbuzz.cli.exception.UnknownArgumentException;
import dev.punchcafe.sefizzbuzz.cli.io.UserOutputWriter;
import dev.punchcafe.sefizzbuzz.cli.utils.ArgumentValidators;
import dev.punchcafe.sefizzbuzz.cli.utils.Formatting;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static dev.punchcafe.sefizzbuzz.cli.constant.MessageConstants.HELP_MESSAGE;

@AllArgsConstructor
public class Calculate implements AppProcess {

    private static String CALCULATE_NAME = "calculate";

    private final UserOutputWriter out;
    private final FizzBuzzClient client;

    @Override
    public String getProcessName() {
        return CALCULATE_NAME;
    }

    @Override
    public void execute(List<String> args) {
        ArgumentValidators.assertHasExactlyOneArgument(args);
        args.stream()
                .map(Integer::parseInt)
                .map(client::calculate)
                .map(Formatting::formatFizzBuzzValue)
                .findAny()
                .ifPresent(out::printToUser);
    }
}
