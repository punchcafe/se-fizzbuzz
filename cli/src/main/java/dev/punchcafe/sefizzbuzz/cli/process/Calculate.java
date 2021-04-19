package dev.punchcafe.sefizzbuzz.cli.process;

import dev.punchcafe.sefizzbuzz.cli.client.FizzBuzzClient;
import dev.punchcafe.sefizzbuzz.cli.client.FizzBuzzEntity;
import dev.punchcafe.sefizzbuzz.cli.exception.ArgumentNeededException;
import dev.punchcafe.sefizzbuzz.cli.exception.UnknownArgumentException;
import dev.punchcafe.sefizzbuzz.cli.io.UserOutputWriter;
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
        if (args.size() == 0) {
            throw new ArgumentNeededException();
        } else if (args.size() > 1) {
            throw new UnknownArgumentException(String.join(" ", args.subList(1, args.size())));
        }
        args.stream()
                .map(Integer::parseInt)
                .map(client::calculate)
                .map(this::formatFizzBuzzValue)
                .findAny()
                .ifPresent(out::printToUser);
    }

    private String formatFizzBuzzValue(final FizzBuzzEntity entity) {
        return String.format("Result:  id: %d,  value: %s", entity.getId(), entity.getValue());
    }
}
