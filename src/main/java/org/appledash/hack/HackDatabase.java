package org.appledash.hack;

import org.appledash.hack.internal.HackDatabaseImpl;

import java.io.InputStream;

public interface HackDatabase {
    String getValue(String key);

    static HackDatabase create(InputStream inputStream) {
        return new HackDatabaseImpl(inputStream);
    }
}
