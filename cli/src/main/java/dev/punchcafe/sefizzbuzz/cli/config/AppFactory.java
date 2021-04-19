package dev.punchcafe.sefizzbuzz.cli.config;

import dev.punchcafe.sefizzbuzz.cli.process.*;
import dev.punchcafe.sefizzbuzz.cli.process.browse.Browse;
import lombok.AllArgsConstructor;

import java.util.List;

import static dev.punchcafe.sefizzbuzz.cli.config.Defaults.DEFAULT_BROWSE_PAGE_SIZE;
import static dev.punchcafe.sefizzbuzz.cli.config.Defaults.DEFAULT_BROWSE_STARTING_PAGE;

@AllArgsConstructor
public class AppFactory {

    private final AppConfig config;

    public AppProcess buildApp() {
        final var helpCommand = new Help(config.getUserOutputWriter());
        final var calculateCommand = new Calculate(config.getUserOutputWriter(), config.getFizzBuzzClient());
        final var favouriteCommand = Favouriting.builder()
                .processName("favourite")
                .isFavourite(true)
                .client(config.getFizzBuzzClient())
                .out(config.getUserOutputWriter())
                .build();
        final var unfavouriteCommand = Favouriting.builder()
                .processName("unfavourite")
                .isFavourite(false)
                .client(config.getFizzBuzzClient())
                .out(config.getUserOutputWriter())
                .build();
        final var browse = Browse.builder()
                .fizzBuzzClient(config.getFizzBuzzClient())
                .userOutputWriter(config.getUserOutputWriter())
                .userInputReader(config.getUserInputReader())
                .page(DEFAULT_BROWSE_STARTING_PAGE)
                .pageSize(DEFAULT_BROWSE_PAGE_SIZE)
                .build();
        final var rootProcess = OptionSelector.builder()
                .processName("main")
                .subAppProcesses(List.of(helpCommand, calculateCommand, favouriteCommand, unfavouriteCommand, browse)).build();
        return rootProcess;
    }
}
