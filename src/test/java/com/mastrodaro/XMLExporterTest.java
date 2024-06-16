package com.mastrodaro;

import com.mastrodaro.exporters.ExporterProvider;
import com.mastrodaro.lang.Dictionary;
import com.mastrodaro.parser.SentenceIterator;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.mastrodaro.parser.OutputFormat.XML;
import static org.junit.Assert.assertEquals;

public class XMLExporterTest {

    private static ExporterProvider exporter;

    @BeforeClass
    public static void configure() {
        exporter = new ExporterProvider();
        exporter.bootstrap();
    }

    @Test
    public void shouldExportToXml() {
        List<short[]> sentences = new ArrayList<>();
        sentences.add(new short[] {1, 2, 3, 4, 5, 6});
        sentences.add(new short[] {7, 2, 3, 8, 9, 6});

        Dictionary dictionary = new Dictionary();
        dictionary.init();
        dictionary.getWordIndex("one");
        dictionary.getWordIndex("two");
        dictionary.getWordIndex("three");
        dictionary.getWordIndex("four");
        dictionary.getWordIndex("five");
        dictionary.getWordIndex("six");
        dictionary.getWordIndex("seven");
        dictionary.getWordIndex("eight");
        dictionary.getWordIndex("nine");

        SentenceIterator sentenceIterator = new SentenceIterator(sentences, dictionary);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        exporter.getExporter(XML).export(baos, sentenceIterator, 6);

        String expect = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<text>\n" +
                "<sentence><word>one</word><word>two</word><word>three</word><word>four</word><word>five</word><word>six</word></sentence>\n" +
                "<sentence><word>seven</word><word>two</word><word>three</word><word>eight</word><word>nine</word><word>six</word></sentence>\n" +
                "</text>";
        assertEquals(expect, new String(baos.toByteArray(), StandardCharsets.UTF_8));
    }
}