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
package org.appledash.hack;

import org.appledash.hack.internal.HackImpl;

public interface Hack {
    /**
     * Get some L33T hacking advice.
     *
     * @return Hacking advice, in String format.
     */
    String getAdvice();
    String evaluate(String expr);

    static Hack create() {
        return new HackImpl();
    }
    static Hack create(HackDatabase hackDatabase) {
        return new HackImpl(hackDatabase);
    }
}
