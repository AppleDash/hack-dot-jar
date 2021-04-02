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

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HackPrimitivesHolder {
    @SerializedName("nouns")
    private List<String> nouns;

    @SerializedName("verbs")
    private String[][] verbs;

    @SerializedName("services")
    private List<String> services;

    @SerializedName("hack_objects")
    private List<String> hackObjects;

    @SerializedName("singular_tools")
    private List<String> singularTools;

    @SerializedName("plural_tools")
    private List<String> pluralTools;

    @SerializedName("people")
    private List<String> people;

    @SerializedName("systems")
    private List<String> systems;

    @SerializedName("times")
    private List<String> times;

    @SerializedName("advice")
    private List<String> advice;

    public List<String> getNouns() {
        return this.nouns;
    }

    public String[][] getVerbs() {
        return this.verbs;
    }

    public List<String> getServices() {
        return this.services;
    }

    public List<String> getHackObjects() {
        return this.hackObjects;
    }

    public List<String> getSingularTools() {
        return this.singularTools;
    }

    public List<String> getPluralTools() {
        return this.pluralTools;
    }

    public List<String> getPeople() {
        return this.people;
    }

    public List<String> getSystems() {
        return this.systems;
    }

    public List<String> getTimes() {
        return this.times;
    }

    public List<String> getAdvice() {
        return this.advice;
    }
}
