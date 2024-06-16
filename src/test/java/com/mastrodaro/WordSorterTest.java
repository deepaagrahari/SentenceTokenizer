package com.mastrodaro;

import com.mastrodaro.lang.WordSorter;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class WordSorterTest {

    @Test
    public void shouldSortWords() {
        List<String> test = Stream.of("bbbb", "a", "A", "Markets", "markets", "你这肮脏的掠夺者").collect(Collectors.toList());
        List<String> expect = Stream.of("a", "A", "bbbb", "markets", "Markets", "你这肮脏的掠夺者").collect(Collectors.toList());
        test.sort(WordSorter.alphabeticalCapitalLsst);
        assertEquals(expect, test);
    }

}