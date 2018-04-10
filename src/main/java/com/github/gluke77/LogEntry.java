package com.github.gluke77;

import java.util.Arrays;

public class LogEntry {
    private String string;
    private String[] words;

    public LogEntry(String string) {
        this.string = string;
        String[] splitted = string.split("\\s+");
        words = Arrays.copyOfRange(splitted, 2, splitted.length);
    }

    public String getString() {
        return string;
    }

    public String[] getWords() {
        return words;
    }
}
