package dev.punchcafe.sefizzbuzz.cli.config;

import dev.punchcafe.sefizzbuzz.cli.client.FizzBuzzClient;
import dev.punchcafe.sefizzbuzz.cli.process.AppProcess;
import dev.punchcafe.sefizzbuzz.cli.process.Calculate;
import dev.punchcafe.sefizzbuzz.cli.process.Help;
import dev.punchcafe.sefizzbuzz.cli.process.OptionSelector;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AppFactory {

    private final AppConfig config;

    public AppProcess buildApp(){
        final var feignClient = Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(FizzBuzzClient.class, "http://localhost:4000");
        final var helpCommand = new Help(config.getUserOutputWriter());
        final var calculateCommand = new Calculate(config.getUserOutputWriter(), feignClient);
        final var initialSelector = OptionSelector.builder()
                .processName("main")
                .subAppProcesses(List.of(helpCommand, calculateCommand)).build();
        return initialSelector;
    }
}
