package dev.punchcafe.sefizzbuzz.cli.process;

import java.util.List;

public interface Process {

    String getProcessName();
    void execute(List<String> args);

}
