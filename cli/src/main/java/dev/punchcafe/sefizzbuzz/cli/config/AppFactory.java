package dev.punchcafe.sefizzbuzz.cli.config;

import dev.punchcafe.sefizzbuzz.cli.process.AppProcess;
import dev.punchcafe.sefizzbuzz.cli.process.Help;
import dev.punchcafe.sefizzbuzz.cli.process.OptionSelector;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AppFactory {

    private final AppConfig config;

    public AppProcess buildApp(){
        final var helpCommand = new Help(config.getUserOutputWriter());
        final var initialSelector = OptionSelector.builder()
                .processName("main")
                .subAppProcesses(List.of(helpCommand)).build();
        return initialSelector;
    }
}
