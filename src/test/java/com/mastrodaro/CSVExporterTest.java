package com.mastrodaro;

import com.mastrodaro.exporters.Exporter;
import com.mastrodaro.exporters.ExporterProvider;
import com.mastrodaro.lang.Dictionary;
import com.mastrodaro.parser.OutputFormat;
import com.mastrodaro.parser.SentenceIterator;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.mastrodaro.parser.OutputFormat.*;
import static org.junit.Assert.*;

public class CSVExporterTest {

    private static ExporterProvider exporter;

    @BeforeClass
    public static void configure() {
        exporter = new ExporterProvider();
        exporter.bootstrap();
    }

    @Test
    public void shouldExportToCSV() {
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
        exporter.getExporter(CSV).export(baos, sentenceIterator, 6);

        String expect = ",Word 1,Word 2,Word 3,Word 4,Word 5,Word 6\n" +
                "Sentence 1,one,two,three,four,five,six\n" +
                "Sentence 2,seven,two,three,eight,nine,six\n";
        assertEquals(expect, new String(baos.toByteArray(), StandardCharsets.UTF_8));
    }
}