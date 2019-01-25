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

import com.google.gson.GsonBuilder;

import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Supplier;

public class HackDatabase {
    private static final Random RANDOM = new Random();
    private final HackPrimitivesHolder primitivesHolder;
    private final Map<String, Supplier<String>> reductionSuppliers = new HashMap<>();

    public HackDatabase() {
        this.primitivesHolder = new GsonBuilder().setLenient().create().fromJson(
                new InputStreamReader(
                        this.getClass().getClassLoader().getResourceAsStream("hack.json")
                ),
                HackPrimitivesHolder.class
        );

        this.reductionSuppliers.put("verb", () -> this.computeVerb(0));
        this.reductionSuppliers.put("verbs", () -> this.computeVerb(1));
        this.reductionSuppliers.put("verbed", () -> this.computeVerb(2));
        this.reductionSuppliers.put("verber", () -> this.computeVerb(3));
        this.reductionSuppliers.put("verbing", () -> this.computeVerb(4));

        this.reductionSuppliers.put("noun", () -> this.randomChoice(this.primitivesHolder.getNouns()));
        this.reductionSuppliers.put("nouns", () -> {
            String noun = this.randomChoice(this.primitivesHolder.getNouns());
            if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(noun.charAt(noun.length() - 1)) == -1) {
                return noun + "'s";
            }

            if ("xs".indexOf(noun.toLowerCase().charAt(noun.length() - 1)) != -1) {
                return noun + "es";
            }

            return noun + "s";
        });

        this.reductionSuppliers.put("hack",
                () -> this.computeVerb(0) + " " + this.getHackObject()
        );
        this.reductionSuppliers.put("hacks",
                () -> this.computeVerb(1) + " " + this.getHackObject()
        );
        this.reductionSuppliers.put("hacked",
                () -> this.computeVerb(2) + " " + this.getHackObject()
        );
        this.reductionSuppliers.put("hacking",
                () -> this.computeVerb(4) + " " + this.getHackObject()
        );

        this.reductionSuppliers.put("service", () -> this.randomChoice(this.primitivesHolder.getServices()));

        this.reductionSuppliers.put("tool", () -> this.randomChoice(this.primitivesHolder.getSingularTools()));
        this.reductionSuppliers.put("tools", () -> this.randomChoice(this.primitivesHolder.getPluralTools()));

        this.reductionSuppliers.put("person", () -> this.randomChoice(this.primitivesHolder.getPeople()));
        this.reductionSuppliers.put("system", () -> this.randomChoice(this.primitivesHolder.getSystems()));
        this.reductionSuppliers.put("time", () -> this.randomChoice(this.primitivesHolder.getTimes()));
    }

    public String getBaseAdvice() {
        return this.randomChoice(this.primitivesHolder.getAdvice());
    }

    public String getValue(String key) {
        if (!this.reductionSuppliers.containsKey(key)) {
            return "?" + key;
        }

        return this.reductionSuppliers.get(key).get();
    }

    private String[] getBaseVerb() {
        return randomChoice(Arrays.asList(this.primitivesHolder.getVerbs()));
    }

    private String getHackObject() {
        return randomChoice(this.primitivesHolder.getHackObjects());
    }

    private String computeVerb(int n) {
        String[] verbInfo = this.getBaseVerb();
        String base = verbInfo[0];
        String suffix = verbInfo[n];

        if (suffix.charAt(0) != '-') {
            return suffix; // suffix is not a suffix at all in this case, but a replacement.
        }

        return base + suffix.substring(1);
    }

    private <T> T randomChoice(List<T> collection) {
        return collection.get(RANDOM.nextInt(collection.size()));
    }
}
