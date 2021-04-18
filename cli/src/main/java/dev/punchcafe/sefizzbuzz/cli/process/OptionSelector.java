package dev.punchcafe.sefizzbuzz.cli.process;

import dev.punchcafe.sefizzbuzz.cli.exception.MissingCommandException;
import dev.punchcafe.sefizzbuzz.cli.exception.UnknownArgumentException;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Value
public class OptionSelector implements Process {

    private final String processName;
    private final List<Process> subProcesses;

    @Override
    public void execute(List<String> args) {
        if (args.size() == 0) {
            throw new MissingCommandException(subProcesses.stream()
                    .map(Process::getProcessName)
                    .collect(Collectors.toList()));
        }

        final var selectedCommand = args.get(0);

        final var selectedProcess = subProcesses.stream()
                .filter(process -> selectedCommand.toLowerCase().equals(process.getProcessName().toLowerCase()))
                .findFirst();

        if (selectedProcess.isEmpty()) {
            throw new UnknownArgumentException(selectedCommand);
        }
        final List<String> remainingArgs;
        if (args.size() > 1) {
            remainingArgs = args.subList(1, args.size());
        } else {
            remainingArgs = List.of();
        }

        selectedProcess.get().execute(remainingArgs);
    }
}
