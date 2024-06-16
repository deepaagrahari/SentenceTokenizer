package com.mastrodaro.lang;

import java.util.Comparator;

/**
 * Class for sorting strategies
 */
public class WordSorter {

    private WordSorter() {}

    /**
     * Alphabetical sorting method. Capitalized version of a word will be after the non-capitalized.
     */
    public static final Comparator<String> alphabeticalCapitalLsst = (s1, s2) -> {
        int result = s1.compareToIgnoreCase(s2);
        if(result == 0) {
            result = s2.compareTo(s1);
        }
        return result;
    };
}
