package com.mastrodaro.exporters;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mastrodaro.parser.SentenceIterator;

class CSVExporter implements Exporter {

	private static final Logger logger = LogManager.getLogger(CSVExporter.class);

	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String ELEMENT_SENTENCE = "Sentence";
	private static final String ELEMENT_WORD = "Word";

	/**
	 * Exports given SentenceIterator to OutputStream using CSVPrinter from Apache
	 * Commons CSV See https://commons.apache.org/proper/commons-csv/
	 */
	@Override
	public void export(OutputStream out, SentenceIterator sentences, int maxWordsInSentence) {
		try (Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
			CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
			CSVPrinter csvFilePrinter = new CSVPrinter(writer, csvFileFormat);

			List<String> header = new ArrayList<>();
			header.add(null);
			IntStream.range(1, maxWordsInSentence + 1).forEach(value -> header.add(ELEMENT_WORD + " " + value));
			csvFilePrinter.printRecord(header);

			int i = 0;
			while (sentences.hasNext()) {
				List<String> words = new ArrayList<>();
				words.add(ELEMENT_SENTENCE + " " + (++i));
				words.addAll(sentences.next().collect(Collectors.toList()));
				csvFilePrinter.printRecord(words);
			}
			writer.flush();
		} catch (IOException e) {
			logger.error("Error during CSV export: {}", e);
			System.exit(-1);
		}
	}
}
