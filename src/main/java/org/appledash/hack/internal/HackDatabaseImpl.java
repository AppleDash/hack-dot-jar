/*
 * This file is part of hack.jar.
 *
 * hack.jar is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * hack.jar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with hack.jar.  If not, see <https://www.gnu.org/licenses/>.
 */
package org.appledash.hack.internal;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.appledash.hack.HackDatabase;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

public class HackDatabaseImpl implements HackDatabase {
    private static final Random RANDOM = new Random();
    private final Map<String, Supplier<String>> reductionSuppliers = new HashMap<>();

    public HackDatabaseImpl() {
        this(HackDatabaseImpl.class.getClassLoader().getResourceAsStream("hack.json"));
    }

    public HackDatabaseImpl(InputStream inputStream) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(new InputStreamReader(inputStream)).getAsJsonObject();

        for (String key : jsonObject.keySet()) {
            String[] values = jsonArrayToStringArray(jsonObject.getAsJsonArray(key));
            this.reductionSuppliers.put(key, values.length == 1 ? (() -> values[0]) : () -> values[RANDOM.nextInt(values.length)]);
        }
    }

    @Override
    public String getValue(String key) {
        if (!this.reductionSuppliers.containsKey(key)) {
            return "?" + key;
        }

        return this.reductionSuppliers.get(key).get();
    }

    private static String[] jsonArrayToStringArray(JsonArray array) {
        String[] stringArray = new String[array.size()];

        for (int i = 0; i < stringArray.length; i++) {
            stringArray[i] = array.get(i).getAsString();
        }

        return stringArray;
    }
}
