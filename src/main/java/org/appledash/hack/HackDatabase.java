package org.appledash.hack;

import org.appledash.hack.internal.HackDatabaseImpl;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

public interface HackDatabase {
    @NotNull String getValue(@NotNull String key);

    static @NotNull HackDatabase create(@NotNull InputStream inputStream) {
        return new HackDatabaseImpl(inputStream);
    }
}
