package com.mastrodaro;

import com.mastrodaro.lang.SentenceRecognizer;
import com.mastrodaro.parser.WordsExtractor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SentenceRecognizerTest {

    private SentenceRecognizer recognizer;

    @Before
    public void setUp() {
        recognizer = new SentenceRecognizer();
    }

    @After
    public void tearDown() {
        recognizer = null;
    }

    @Test
    public void shouldRecogonizeSentence() {
        assertTrue(recognizer.isSentence('.', "I couldn't understand a word,perhaps because Chinese \n" +
                " isn't my mother tongue"));
        assertTrue(recognizer.isSentence('!', "What\the  shouted was shocking:  停在那儿, 你这肮脏的掠夺者"));
        assertTrue(recognizer.isSentence('.', "In fact - in all of the Nordics, you’d have a hard time finding " +
                "a product range as strong and diversified as ours (and we can give you excellent liquidity" +
                " in local currencies too)"));
        assertTrue(recognizer.isSentence('.', "This,is,also,a,sentence"));
        assertTrue(recognizer.isSentence('.', "Hello Mrs. Bundy"));
        assertTrue(recognizer.isSentence('.', " Mary had a little lamb .\n" +
                "\n" +
                "Peter called for the wolf , and Aesop came .\n" +
                "\n" +
                "Cinderella likes shoes.."));
    }

    @Test
    public void shouldNotRecogonizeSentence() {
        assertFalse(recognizer.isSentence('.', "I was just standing there watching Mr"));
        assertFalse(recognizer.isSentence(' ', "I was just standing there watching Mr."));
        assertFalse(recognizer.isSentence('n', "Not going to be a sente"));
    }
}