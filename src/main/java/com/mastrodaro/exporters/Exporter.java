package com.mastrodaro.exporters;

import com.mastrodaro.parser.SentenceIterator;

import java.io.OutputStream;

public interface Exporter {

    /**
     * Exports given SentenceIterator to OutputStream
     * @param out OutputStream to be written to
     * @param sentences Sentences to be written
     * @param maxWordsInSentence word count in the longest sentence
     */
    void export(OutputStream out, SentenceIterator sentences, int maxWordsInSentence);
}
