package com.mastrodaro.lang;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Container for words in structure ID < - > String for memory opitimzation to
 * just store one instance of each word
 */
@Singleton
public class Dictionary {

	private static final Logger logger = LogManager.getLogger(Dictionary.class);

	private Map<Short, String> dictionary;
	private Map<String, Short> reverseDictionary;
	private short index;
	// pomyslec nad usunieciem reversedictionary i zastapienie go para

	@Inject
	public void init() {
		dictionary = new HashMap<>();
		reverseDictionary = new HashMap<>();
		index = 0;
		logger.debug("Dictionary initialized.");
	}

	private short addWord(String word) {
		dictionary.put(++index, word);
		reverseDictionary.put(word, index);
		logger.trace("Added word \"{}\" with index: {}", word, index);
		return index;
	}

	/**
	 * Gets word
	 * 
	 * @param index index of word
	 * @return word
	 */
	public String getWord(short index) {
		return dictionary.get(index);
	}

	/**
	 * Gives id of given word. If dictionary does not contain the word it it will be
	 * stored.
	 * 
	 * @param word word to get id of
	 * @return id of given word
	 */
	public short getWordIndex(String word) {
		short wordIndex;
		if (!dictionary.containsValue(word)) {
			wordIndex = addWord(word);
		} else {
			wordIndex = reverseDictionary.get(word);
		}
		logger.trace("Word \"{}\" index = {}", word, wordIndex);
		return wordIndex;
	}
}
