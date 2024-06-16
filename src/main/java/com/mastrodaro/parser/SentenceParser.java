package com.mastrodaro.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mastrodaro.exporters.ExporterProvider;
import com.mastrodaro.lang.Dictionary;
import com.mastrodaro.lang.SentenceRecognizer;
import com.mastrodaro.lang.WordSorter;

public class SentenceParser {	
	private static final Logger logger = LogManager.getLogger(SentenceParser.class);

	private Dictionary dictionary;
	private ExporterProvider exporterProvider;
	private SentenceRecognizer sentenceRecognizer;
	private WordsExtractor wordsExtractor;

	private List<short[]> sentences = new ArrayList<>(25);
	private int maxWordsInSentence = 0;

	@Inject
	public SentenceParser(Dictionary dictionary, ExporterProvider exporterProvider,
			SentenceRecognizer sentenceRecognizer, WordsExtractor wordsExtractor) {
		this.dictionary = dictionary;
		this.exporterProvider = exporterProvider;
		this.sentenceRecognizer = sentenceRecognizer;
		this.wordsExtractor = wordsExtractor;
	}

	public SentenceParser() {
		// Default constructor logic, if needed
	}

	/**
	 * Processing the input to given format output.
	 * 
	 * @param in     input stream
	 * @param out    output stream
	 * @param format format option
	 */	
	public void parse(InputStream in, OutputStream out, OutputFormat format) {
		logger.info("Parsing - start");
		read(in, format);
		logger.debug("Parsing - input read successful, starting export: {}", format);
		export(out, format);
		logger.debug("Parsing - done");
	}

	/**
	 * Read input in stream fashion way.
	 * 
	 * @param in
	 * @param format
	 */
	private void read(InputStream in, OutputFormat format) {
		try (Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
			int c;
			char letter;
			StringBuilder sb = new StringBuilder();

			while ((c = reader.read()) != -1) {
				letter = (char) c;

				if (sentenceRecognizer.isSentence(letter, sb.toString())) {

					processSentence(sb.toString(), format);
					sb = new StringBuilder();
					continue;
				}
				sb.append(letter);
			}

		} catch (IOException e) {
			logger.error("Error during reading input: {}", e);
			System.exit(-1);
		}
	}

	/**
	 * Processes a single sentence.
	 * 
	 * @param sentence
	 * @param format
	 */
	private void processSentence(String sentence, OutputFormat format) {
		List<String> words = wordsExtractor.setMode(format).extract(sentence);
		saveLongestWordsCount(words);
		words.sort(WordSorter.alphabeticalCapitalLsst);
		short[] indexes = toIndexes(words);
		sentences.add(indexes);
		logger.trace("Processed sentence: {}, word: {}, indexes: {}", sentence, words, indexes);
	}

	/**
	 * Transforms words to their indexes.
	 * 
	 * @param words
	 * @return
	 */
	private short[] toIndexes(List<String> words) {
		short[] wordsIndexed = new short[words.size()];
		int i = 0;
		Iterator<String> iterator = words.iterator();
		while (iterator.hasNext()) {
			String word = iterator.next();
			wordsIndexed[i++] = dictionary.getWordIndex(word);
		}
		return wordsIndexed;
	}

	/**
	 * Saves the longest word count in sentences.
	 * 
	 * @param words
	 */
	private void saveLongestWordsCount(List<String> words) {
		if (words.size() > maxWordsInSentence) {
			maxWordsInSentence = words.size();
		}
	}

	/**
	 * Exports parsed data for given format output.
	 * 
	 * @param out
	 * @param format
	 */
	private void export(OutputStream out, OutputFormat format) {
		exporterProvider.getExporter(format).export(out, new SentenceIterator(sentences, dictionary),
				maxWordsInSentence);
	}

}
