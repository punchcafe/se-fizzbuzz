package dev.punchcafe.sefizzbuzz.cli.client;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class FavouritePayload {

    public static FavouritePayload buildFrom(final boolean isFavourite){
        return new FavouritePayload(isFavourite);
    }

    private final boolean is_favourite;
}
