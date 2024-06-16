package com.mastrodaro.model;

import java.util.Arrays;

/**
 * Sentence representation. Contains an array of word indexes to be stored somewhere else.
 * Not used because of memory limits.
 */
public class Sentence {

    private short[] words;

    public Sentence(short[] words) {
        this.words = words;
    }

    public short[] getWords() {
        return words;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sentence sentence = (Sentence) o;
        return Arrays.equals(words, sentence.words);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(words);
    }
}
