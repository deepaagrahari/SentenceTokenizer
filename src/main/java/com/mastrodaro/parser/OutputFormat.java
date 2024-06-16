package com.mastrodaro.parser;

public enum OutputFormat {
    XML("xml"), CSV("csv");

    private String format;

    OutputFormat(String format) {
        this.format = format;
    }
}
