package dev.punchcafe.sefizzbuzz.cli.config;

import dev.punchcafe.sefizzbuzz.cli.process.*;
import lombok.AllArgsConstructor;

import java.util.List;

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
        final var initialSelector = OptionSelector.builder()
                .processName("main")
                .subAppProcesses(List.of(helpCommand, calculateCommand, favouriteCommand, unfavouriteCommand)).build();
        return initialSelector;
    }
}
