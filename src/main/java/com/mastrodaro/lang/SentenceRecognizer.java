package com.mastrodaro.lang;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SentenceRecognizer {

	private static final Logger logger = LogManager.getLogger(Dictionary.class);

	/**
	 * Checks if given state is considered as a sentence.
	 * 
	 * @param letter   Current read letter
	 * @param sentence Buffered sentence
	 * @return true - sentence recognized, else false
	 */
	public boolean isSentence(char letter, String sentence) {

		boolean result = false;
		if (letter == '!' || letter == '?') {
			result = true;
		} else if (letter == '.' && languageExceptions(sentence)) {
			result = true;
		}

		if (result) {
			logger.trace("Sentence: \"{}\" recogonized as: {}", sentence, result);
		}
		return result;
	}

	private boolean languageExceptions(String sentence) {
		return !sentence.endsWith("Mr") && !sentence.endsWith("Mrs") && !sentence.endsWith("Ms");
	}
}
