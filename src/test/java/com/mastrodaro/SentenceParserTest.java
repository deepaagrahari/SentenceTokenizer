package com.mastrodaro;

import com.mastrodaro.exporters.ExporterProvider;
import com.mastrodaro.lang.Dictionary;
import com.mastrodaro.lang.SentenceRecognizer;
import com.mastrodaro.parser.SentenceParser;
import com.mastrodaro.parser.WordsExtractor;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xmlunit.builder.Input;
import org.xmlunit.matchers.CompareMatcher;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.mastrodaro.parser.OutputFormat.*;
import static org.junit.Assert.*;
import static org.xmlunit.matchers.CompareMatcher.*;

public class SentenceParserTest {

    private SentenceParser parser;

    @Before
    public void setUp() {
        Dictionary dict = new Dictionary();
        dict.init();
        ExporterProvider exporter = new ExporterProvider();
        exporter.bootstrap();
        parser = new SentenceParser(dict, exporter, new SentenceRecognizer(), new WordsExtractor());
    }

    @Test
    public void shouldParseTextToXml() throws FileNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        parser.parse(new FileInputStream(Paths.get("src/test/resources/small.in").toFile()), baos, XML);

        assertThat(Input.fromString(new String(baos.toByteArray(), StandardCharsets.UTF_8)),
                isIdenticalTo(Input.fromFile("src/test/resources/small.xml")));
    }

    @Test
    public void shouldParseTextToCsv() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        parser.parse(new FileInputStream(Paths.get("src/test/resources/small.in").toFile()), baos, CSV);

        String expect = new String(Files.readAllBytes(Paths.get("src/test/resources/small.csv")));
        String result = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        assertEquals(expect, result);
    }
}