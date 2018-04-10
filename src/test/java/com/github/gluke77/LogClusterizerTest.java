package com.github.gluke77;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LogClusterizerTest {
    @Test
    public void test() {
        LogClusterizer logClusterizer = new LogClusterizer();

        logClusterizer.append(new LogEntry("1 2 aa bbb ccc"));
        logClusterizer.append(new LogEntry("1 2 aaa bb ccc"));
        logClusterizer.append(new LogEntry("1 2 aaa bbb cc"));

        logClusterizer.append(new LogEntry("1 3 aa bbb cc"));
        logClusterizer.append(new LogEntry("1 3 aaa bb cc"));

        logClusterizer.append(new LogEntry("1 4 aa bbb c"));


        List<LogEntryCluster> clusters = logClusterizer.getClusters();

        assertEquals(3, clusters.size());

        LogEntryCluster cluster0 = clusters.get(0);

        assertArrayEquals(new String[]{"aa", "bbb", "ccc"}, cluster0.getPattern());
        assertEquals(2, cluster0.getAlteringWordPosition());
        assertEquals(Arrays.asList(
                "1 2 aa bbb ccc",
                "1 3 aa bbb cc",
                "1 4 aa bbb c"
        ), cluster0.getMessages());

        Set<String> expectedAlteringWords0 = new HashSet<>();
        expectedAlteringWords0.add("ccc");
        expectedAlteringWords0.add("cc");
        expectedAlteringWords0.add("c");

        assertEquals(expectedAlteringWords0.size(), cluster0.getAlteringWords().size());
        assertTrue(expectedAlteringWords0.containsAll(cluster0.getAlteringWords()));



        LogEntryCluster cluster1 = clusters.get(1);

        assertArrayEquals(new String[]{"aaa", "bb", "ccc"}, cluster1.getPattern());
        assertEquals(2, cluster1.getAlteringWordPosition());
        assertEquals(Arrays.asList(
                "1 2 aaa bb ccc",
                "1 3 aaa bb cc"
        ), cluster1.getMessages());

        Set<String> expectedAlteringWords1 = new HashSet<>();
        expectedAlteringWords1.add("ccc");
        expectedAlteringWords1.add("cc");

        assertEquals(expectedAlteringWords1.size(), cluster1.getAlteringWords().size());
        assertTrue(expectedAlteringWords1.containsAll(cluster1.getAlteringWords()));


        LogEntryCluster cluster2 = clusters.get(2);

        assertArrayEquals(new String[]{"aaa", "bbb", "cc"}, cluster2.getPattern());
        assertEquals(-1, cluster2.getAlteringWordPosition());
        assertEquals(Arrays.asList(
                "1 2 aaa bbb cc"
        ), cluster2.getMessages());

        assertTrue(cluster2.getAlteringWords().isEmpty());

    }
}
