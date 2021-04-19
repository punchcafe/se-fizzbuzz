package dev.punchcafe.sefizzbuzz.cli.process;

import java.util.List;

public interface AppProcess {

    String getProcessName();

    void execute(List<String> args);

}
