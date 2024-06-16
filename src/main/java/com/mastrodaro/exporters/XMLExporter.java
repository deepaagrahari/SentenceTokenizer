package com.mastrodaro.exporters;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mastrodaro.parser.SentenceIterator;

class XMLExporter implements Exporter {

	private static final Logger logger = LogManager.getLogger(XMLExporter.class);

	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String TAG_TEXT = "text";
	private static final String TAG_SENTENCE = "sentence";
	private static final String TAG_WORD = "word";

	/**
	 * Exports given SentenceIterator to OutputStream using XMLStreamWriter
	 */
	@Override
	public void export(OutputStream out, SentenceIterator sentences, int maxWordsInSentence) {
		try (Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			factory.setProperty("escapeCharacters", false);

			// to keep original formating from example some parts are printed directly to
			// the OutputStream

			XMLStreamWriter xmlWriter = factory.createXMLStreamWriter(writer);
			// xmlWriter.writeStartDocument(StandardCharsets.UTF_8.name(), "1.0");
			writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + NEW_LINE_SEPARATOR + "<"
					+ TAG_TEXT + ">" + NEW_LINE_SEPARATOR);
			// xmlWriter.writeStartElement(TAG_TEXT);

			while (sentences.hasNext()) {
				xmlWriter.writeStartElement(TAG_SENTENCE);
				sentences.next().forEach(word -> uncheck(() -> {
					xmlWriter.writeStartElement(TAG_WORD);
					xmlWriter.writeCharacters(word);
					xmlWriter.writeEndElement();
				}));
				xmlWriter.writeEndElement();
				writer.write(NEW_LINE_SEPARATOR);
			}

			writer.write("</" + TAG_TEXT + ">");
			// xmlWriter.writeEndElement();
			// xmlWriter.writeEndDocument();
			xmlWriter.flush();
			xmlWriter.close();
		} catch (IOException | XMLStreamException e) {
			logger.error("Error during XML export: {}", e);
			System.exit(-1);
		}
	}

	private static void uncheck(RunnableXmlWrite runner) {
		try {
			runner.run();
		} catch (XMLStreamException e) {
			logger.error("Error during XML export: {}", e);
			System.exit(-1);
		}
	}
}
