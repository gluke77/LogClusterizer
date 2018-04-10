package com.github.gluke77;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class LogEntryTest {

    @Test
    public void testConstructor() {
        LogEntry logEntry = new LogEntry("1 2 aaa    bbb   ccc");
        assertEquals("1 2 aaa    bbb   ccc", logEntry.getString());
        assertArrayEquals(new String[]{"aaa", "bbb", "ccc"}, logEntry.getWords());
    }
}
