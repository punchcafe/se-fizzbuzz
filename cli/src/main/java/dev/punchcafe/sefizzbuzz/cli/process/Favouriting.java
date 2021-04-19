package dev.punchcafe.sefizzbuzz.cli.process;

import dev.punchcafe.sefizzbuzz.cli.client.FavouritePayload;
import dev.punchcafe.sefizzbuzz.cli.client.FizzBuzzClient;
import dev.punchcafe.sefizzbuzz.cli.client.FizzBuzzEntity;
import dev.punchcafe.sefizzbuzz.cli.exception.ArgumentNeededException;
import dev.punchcafe.sefizzbuzz.cli.exception.UnknownArgumentException;
import dev.punchcafe.sefizzbuzz.cli.io.UserOutputWriter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.List;

@Builder
@Getter
public class Favouriting implements AppProcess {

    private final String processName;
    private final boolean isFavourite;
    private final UserOutputWriter out;
    private final FizzBuzzClient client;

    @Override
    public String getProcessName() {
        return this.processName;
    }

    @Override
    public void execute(List<String> args) {
        if (args.size() == 0) {
            throw new ArgumentNeededException();
        } else if (args.size() > 1) {
            throw new UnknownArgumentException(String.join(" ", args.subList(1, args.size())));
        }
        args.stream()
                .map(Integer::parseInt)
                .map(id -> client.updateFavourite(id, FavouritePayload.buildFrom(this.isFavourite)))
                .map(this::formatFizzBuzzValue)
                .findAny()
                .ifPresent(out::printToUser);
    }

    private String formatFizzBuzzValue(final FizzBuzzEntity entity) {
        return String.format("Result:  id: %d,  value: %s", entity.getId(), entity.getValue());
    }

}
