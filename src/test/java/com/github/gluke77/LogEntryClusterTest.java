package com.github.gluke77;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class LogEntryClusterTest {

    @Test
    public void testMatches1() {
        LogEntryCluster logEntryCluster = new LogEntryCluster(new LogEntry("1 2 aaa bbb ccc"));
        assertEquals(0, logEntryCluster.match(new String[]{"aa", "bbb", "ccc"}));
    }

    @Test
    public void testMatches2() {
        LogEntryCluster logEntryCluster = new LogEntryCluster(new LogEntry("1 2 aaa bbb ccc"));
        assertEquals(1, logEntryCluster.match(new String[]{"aaa", "bb", "ccc"}));
    }

    @Test
    public void testDoesNotMatchWrongLength1() {
        LogEntryCluster logEntryCluster = new LogEntryCluster(new LogEntry("1 2 aaa bbb ccc"));
        assertEquals(-1, logEntryCluster.match(new String[]{"aaa", "bbb", "ccc", "ddd"}));
    }

    @Test
    public void testDoesNotMatchWrongLength2() {
        LogEntryCluster logEntryCluster = new LogEntryCluster(new LogEntry("1 2 aaa bbb ccc"));
        assertEquals(-1, logEntryCluster.match(new String[]{"aaa", "bbb"}));
    }

    @Test
    public void testDoesNotMatchMoreThanOneWordDiffers() {
        LogEntryCluster logEntryCluster = new LogEntryCluster(new LogEntry("1 2 aaa bbb ccc"));
        assertEquals(-1, logEntryCluster.match(new String[]{"aa", "bbb", "cc"}));
    }

    @Test
    public void testAppendIfMatches1() {
        LogEntryCluster logEntryCluster = new LogEntryCluster(new LogEntry("1 2 aaa bbb ccc"));

        assertTrue(logEntryCluster.appendIfMatches(new LogEntry("1 2 aa bbb ccc")));

        assertEquals(0, logEntryCluster.getAlteringWordPosition());
        assertArrayEquals(new String[]{"aaa", "bbb", "ccc"}, logEntryCluster.getPattern());
        assertEquals(Arrays.asList("1 2 aaa bbb ccc", "1 2 aa bbb ccc"), logEntryCluster.getMessages());

        Set<String> expectedAlteringWords = new HashSet<>();
        expectedAlteringWords.add("aaa");
        expectedAlteringWords.add("aa");

        assertEquals(expectedAlteringWords.size(), logEntryCluster.getAlteringWords().size());
        assertTrue(expectedAlteringWords.containsAll(logEntryCluster.getAlteringWords()));
    }

    @Test
    public void testAppendIfMatches2() {
        LogEntryCluster logEntryCluster = new LogEntryCluster(new LogEntry("1 2 aaa bbb ccc"));

        assertTrue(logEntryCluster.appendIfMatches(new LogEntry("1 2 aaa bb ccc")));
        assertTrue(logEntryCluster.appendIfMatches(new LogEntry("1 2 aaa b ccc")));

        assertEquals(1, logEntryCluster.getAlteringWordPosition());
        assertArrayEquals(new String[]{"aaa", "bbb", "ccc"}, logEntryCluster.getPattern());
        assertEquals(Arrays.asList("1 2 aaa bbb ccc", "1 2 aaa bb ccc", "1 2 aaa b ccc"), logEntryCluster.getMessages());

        Set<String> expectedAlteringWords = new HashSet<>();
        expectedAlteringWords.add("bbb");
        expectedAlteringWords.add("bb");
        expectedAlteringWords.add("b");

        assertEquals(expectedAlteringWords.size(), logEntryCluster.getAlteringWords().size());
        assertTrue(expectedAlteringWords.containsAll(logEntryCluster.getAlteringWords()));
    }

    @Test
    public void testAppendIfMatchesSecondDoesNotMatch() {
        LogEntryCluster logEntryCluster = new LogEntryCluster(new LogEntry("1 2 aaa bbb ccc"));

        assertTrue(logEntryCluster.appendIfMatches(new LogEntry("1 2 aaa bb ccc")));
        assertFalse(logEntryCluster.appendIfMatches(new LogEntry("1 2 aa bbb ccc")));
    }
}
