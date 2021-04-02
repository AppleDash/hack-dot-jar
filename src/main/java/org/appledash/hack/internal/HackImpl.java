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

import java.util.Arrays;

public class HackImpl implements Hack {
    private final HackDatabase database = new HackDatabase();

    @Override
    public String getAdvice() {
        return this.evaluate(this.database.getValue("advice"));
    }

    @Override
    public String evaluate(String expr) {
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

    private boolean canReduce(String expr) {
        return expr.indexOf('<') != -1 && expr.indexOf('>') != -1;
    }

    private String reduce(String expr) {
        String[] split = expr.split("[<>]");

        for (int i = 0; i < split.length; i++) {
            if (i % 2 == 0) {
                continue;
            }

            boolean uppercase = false;
            boolean plural = false;

            String word = split[i];
            int colonIndex = word.indexOf(':');

            if (colonIndex != -1) {
                char modifier = word.charAt(colonIndex + 1);

                if (modifier == 'U') {
                    uppercase = true;
                } else if (modifier == 'p') {
                    plural = true;
                }

                word = word.substring(0, colonIndex);
            }

            word = this.database.getValue(word);

            if (plural) {
                word = this.attemptPluralization(word);
            }

            if (uppercase) {
                word = word.toUpperCase();
            }

            split[i] = word;
        }

        return String.join("", Arrays.asList(split));
    }

    private String indefiniteArticles(String s) {
        return s.replaceAll("([aA])\\(([nN])\\) ([AEFHILMNORSXaeiou])", "$1$2 $3")
                .replaceAll("([aA])\\([nN]\\) ", "$1 ");
    }

    private String attemptPluralization(String s) {
        if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(s.charAt(s.length() - 1)) == -1) {
            return s + "'s";
        }

        if ("xs".indexOf(s.toLowerCase().charAt(s.length() - 1)) != -1) {
            return s + "es";
        }

        return s + "s";
    }
}
