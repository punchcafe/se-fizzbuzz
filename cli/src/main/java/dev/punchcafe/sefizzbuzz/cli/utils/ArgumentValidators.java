package dev.punchcafe.sefizzbuzz.cli.utils;

import dev.punchcafe.sefizzbuzz.cli.exception.ArgumentNeededException;
import dev.punchcafe.sefizzbuzz.cli.exception.UnknownArgumentException;

import java.util.List;

public interface ArgumentValidators {

    static void assertHasExactlyOneArgument(final List<String> args){
        if(args.size() > 1){
            throw  new UnknownArgumentException(String.join(" ", args.subList(1, args.size())));
        } else if (args.size() == 0) {
            throw new ArgumentNeededException();
        }
    }

    static void assertHasNoArguments(final List<String> args){
        if (args.size() > 0) {
            throw new UnknownArgumentException(String.join(" ", args));
        }
    }
}
