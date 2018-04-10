package com.github.gluke77;

import java.util.ArrayList;
import java.util.List;

public class LogClusterizer {
    private List<LogEntryCluster> clusters;

    public LogClusterizer() {
        clusters = new ArrayList<>();
    }

    public List<LogEntryCluster> getClusters() {
        return clusters;
    }

    public void append(LogEntry logEntry) {
        for (LogEntryCluster cluster: clusters) {
            //TODO: We expect that a message must only belong to no more than one cluster, right?
            if (cluster.appendIfMatches(logEntry)) {
                return;
            }
        }

        clusters.add(new LogEntryCluster(logEntry));
    }

    public void append(String s) {
        append(new LogEntry(s));
    }
}
