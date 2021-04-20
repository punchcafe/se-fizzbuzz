package dev.punchcafe.sefizzbuzz.cli.process;

import dev.punchcafe.sefizzbuzz.cli.exception.MissingCommandException;
import dev.punchcafe.sefizzbuzz.cli.exception.UnknownArgumentException;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class OptionSelector implements AppProcess {

    private final String processName;
    private final List<AppProcess> subAppProcesses;

    @Override
    public void execute(List<String> args) {
        final var selectedCommand = extractSelectedCommand(args);
        final var selectedProcess = subAppProcesses.stream()
                .filter(appProcess -> this.processMatchesCommand(appProcess, selectedCommand))
                .findFirst();
        if (selectedProcess.isEmpty()) {
            throw new UnknownArgumentException(selectedCommand);
        }
        selectedProcess.get().execute(extractRemainingArgs(args));
    }

    private String extractSelectedCommand(List<String> args){
        if (args.size() == 0) {
            throw new MissingCommandException(subAppProcesses.stream()
                    .map(AppProcess::getProcessName)
                    .collect(Collectors.toList()));
        }
        return args.get(0);
    }

    private List<String> extractRemainingArgs(final List<String> args){
        return args.size() > 1 ? args.subList(1, args.size()) : List.of();
    }

    private boolean processMatchesCommand(final AppProcess process, final String selectedCommand){
        return selectedCommand.toLowerCase().equals(process.getProcessName().toLowerCase());
    }
}
