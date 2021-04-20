/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package dev.punchcafe.sefizzbuzz.cli;

import dev.punchcafe.sefizzbuzz.cli.client.FizzBuzzClient;
import dev.punchcafe.sefizzbuzz.cli.config.AppConfig;
import dev.punchcafe.sefizzbuzz.cli.config.AppFactory;
import dev.punchcafe.sefizzbuzz.cli.io.UserInputReader;
import dev.punchcafe.sefizzbuzz.cli.io.UserOutputWriter;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import lombok.Data;

import java.util.Arrays;
import java.util.Scanner;

@Data
public class App {

    public static void main(String[] args) {
        UserOutputWriter userOutputWriter = new UserOutputWriter(System.out);

        final var appConfig = AppConfig.builder()
                .userOutputWriter(userOutputWriter)
                .userInputReader(new UserInputReader(new Scanner(System.in)))
                .fizzBuzzClient(Feign.builder()
                        .encoder(new GsonEncoder())
                        .decoder(new GsonDecoder())
                        .target(FizzBuzzClient.class, "http://localhost:4000"))
                .build();

        final var app = new AppFactory(appConfig).buildApp();
        app.execute(Arrays.asList(args));
    }
}
