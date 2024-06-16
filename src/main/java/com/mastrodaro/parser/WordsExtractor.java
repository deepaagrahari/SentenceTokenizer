package com.mastrodaro.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.mastrodaro.parser.OutputFormat.*;

public class WordsExtractor {

	private static final Logger logger = LogManager.getLogger(WordsExtractor.class);

    private OutputFormat mode;

    public WordsExtractor setMode(OutputFormat mode) {
        this.mode = mode;
        return this;
    }

    /**
     * Extracs given sentence to words.
     * @param sentence
     * @return
     */
    public List<String> extract(String sentence) {
        List<String> words = new ArrayList<>();
        if(mode == XML) {
            words = extractForXML(sentence);
        } else if(mode == CSV) {
            words = extractForCSV(sentence);
        }
        return words;
    }

    private String[] split(String sentence) {
        return sentence.replaceAll(",([^ ])", ", $1").split("\\s+");
    }

    /**
     * Sentence extraction for XML purposes.
     * Removes : ! , ? ( ) from word and changes ' ` ’ to encoded entity &amp;apos;
     * Removes single characters like -
     * @param sentence
     * @return
     */
    private List<String> extractForXML(String sentence) {
        List<String> words = new ArrayList<>();
        String[] wordsArr = split(sentence);

        for(String w : wordsArr) {
            if(!w.trim().isEmpty()) {
                String word = w.trim().replaceAll("[:!,\\?\\(\\)]", "").replaceAll("['`’]", "&apos;");
                if(word.length() > 1 || word.matches("[a-zA-Z]"))
                    words.add(word);
            }
        }
        logger.trace("Extracted words: {}. From sentence: {}", words, sentence);
        return words;
    }

    /**
     * Sentence extraction for CSV purposes.
     * Removes : ! , ? ( ) from word and normalizes ' ` ’ to '
     * Removes single characters like -
     * @param sentence
     * @return
     */
    private List<String> extractForCSV(String sentence) {
        List<String> words = new ArrayList<>();
        String[] wordsArr = split(sentence);

        for(String w : wordsArr) {
            if(!w.trim().isEmpty()) {
                String word = w.trim().replaceAll("[:!,\\?\\(\\)]", "").replaceAll("['`’]", "'");
                if(word.length() > 1 || word.matches("[a-zA-Z]"))
                    words.add(word);
            }
        }
        logger.trace("Extracted words: {}. From sentence: {}", words, sentence);
        return words;
    }
}
