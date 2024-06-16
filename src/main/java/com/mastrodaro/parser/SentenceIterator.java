package com.mastrodaro.parser;

import com.mastrodaro.lang.Dictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * SentenceIterator is responsible for handling sentence reporesentation as a list od words. Resolves indexes to words.
 */
public class SentenceIterator {

    private List<short[]> sentences;
    private Dictionary dictionary;
    private int i;

    public SentenceIterator(List<short[]> sentences, Dictionary dictionary) {
        this.sentences = sentences;
        this.dictionary = dictionary;
        i = 0;
    }

    /**
     * Checks if there is any more sentence.
     * @return true if there is sentence to be served
     */
    public boolean hasNext() {
        return i != sentences.size();
    }

    /**
     * Gets next sentence presented as a stream of words (not indexes)
     * @return stream of words
     */
    public Stream<String> next() {
        short[] wordIndexes = sentences.get(i++);

        List<String> words = new ArrayList<>(wordIndexes.length);
        for (short index : wordIndexes) {
            words.add(dictionary.getWord(index));
        }

        return words.stream();
    }

}
