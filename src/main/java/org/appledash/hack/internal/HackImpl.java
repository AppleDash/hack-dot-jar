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

import org.appledash.hack.Hack;
import org.appledash.hack.HackDatabase;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Random;

public class HackImpl implements Hack {
    private static final Random RANDOM = new Random();
    private final @NotNull HackDatabase database;

    public HackImpl() {
        this(new HackDatabaseImpl());
    }

    public HackImpl(@NotNull HackDatabase database) {
        this.database = database;
    }

    @Override
    public @NotNull String getAdvice() {
        return this.evaluate(this.database.getValue("advice"));
    }

    @Override
    public @NotNull String evaluate(@NotNull String expr) {
        while (this.canReduce(expr)) {
            expr = this.reduce(expr);
        }

        expr = this.indefiniteArticles(expr);

        return expr.substring(0, 1).toUpperCase() + expr.substring(1);
    }

    public static void main(String[] args) {
        HackImpl hackImpl = new HackImpl();

        System.out.println(hackImpl.getAdvice());
    }

    private boolean canReduce(@NotNull String expr) {
        return expr.indexOf('<') != -1 && expr.indexOf('>') != -1;
    }

    private @NotNull String reduce(@NotNull String expr) {
        String[] split = expr.split("[<>]");

        for (int i = 0; i < split.length; i++) {
            if (i % 2 == 0) {
                continue;
            }

            boolean uppercase = false;
            boolean plural = false;
            boolean conjunct = false;

            String word = split[i];
            int colonIndex = word.indexOf(':');

            if (colonIndex != -1) {
                char modifier = word.charAt(colonIndex + 1);

                if (modifier == 'U') {
                    uppercase = true;
                } else if (modifier == 'p') {
                    plural = true;
                } else if (modifier == 'P') {
                    plural = this.RANDOM.nextBoolean();
                } else if (modifier == 'c') {
                    conjunct = true;
                }

                word = word.substring(0, colonIndex);
            }

            word = this.database.getValue(word);

            if (plural) {
                word = this.attemptPluralization(word);
            }

            if (conjunct) {
                word = this.attemptConjunction(word);
            }

            if (uppercase) {
                word = word.toUpperCase();
            }

            split[i] = word;
        }

        return String.join("", Arrays.asList(split));
    }

    private @NotNull String indefiniteArticles(@NotNull String s) {
        return s.replaceAll("([aA])\\(([nN])\\) ([AEFHILMNORSXaeiou])", "$1$2 $3")
                .replaceAll("([aA])\\([nN]\\) ", "$1 ");
    }

    private @NotNull String attemptPluralization(@NotNull String s) {
        if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(s.charAt(s.length() - 1)) == -1) {
            return s + "'s";
        }

        if ("xs".indexOf(s.toLowerCase().charAt(s.length() - 1)) != -1) {
            return s + "es";
        }

        return s + "s";
    }

    private @NotNull String attemptConjunction(@NotNull String s) {
        if ("aeiou".indexOf(s.charAt(0)) != -1) {
            return "an " + s;
        }

        return "a " + s;
    }
}
