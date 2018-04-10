package com.github.gluke77;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LogEntryCluster {
    private String[] pattern;
    private int alteringWordPosition = -1;
    private Set<String> alteringWords;
    private List<String> messages;

    public LogEntryCluster(LogEntry logEntry) {
        pattern = logEntry.getWords();
        messages = new ArrayList<>();
        messages.add(logEntry.getString());
        alteringWords = new HashSet<>();
    }

    public String[] getPattern() {
        return pattern;
    }

    public int getAlteringWordPosition() {
        return alteringWordPosition;
    }

    public Set<String> getAlteringWords() {
        return alteringWords;
    }

    public List<String> getMessages() {
        return messages;
    }


    /**
     * @param logEntry
     * @return true if logEntry matches the pattern and was appended,
     * false otherwise
     */
    public boolean appendIfMatches(LogEntry logEntry) {
        int pos = match(logEntry.getWords());
        if (pos > -1) {
            alteringWordPosition = pos;
            messages.add(logEntry.getString());
            alteringWords.add(logEntry.getWords()[alteringWordPosition]);
            alteringWords.add(pattern[alteringWordPosition]);
            return true;
        }
        return false;
    }

    /**
     * @param words
     * @return alteringWordPosition if matches,
     * -1 otherwise
     */
    public int match(String[] words) {
        if (words.length == pattern.length) {

            if (alteringWordPosition > -1) {
                for (int i = 0; i < pattern.length; i++) {
                    if (i != alteringWordPosition && !words[i].equals(pattern[i])) {
                        return -1;
                    }
                }

                return alteringWordPosition;
            } else {
                int pos = -1;

                for (int i = 0; i < pattern.length; i++) {
                    if (!words[i].equals(pattern[i])) {
                        if (pos < 0) {
                            pos = i;
                        } else {
                            return -1;
                        }
                    }
                }

                return pos;
            }

        }

        return -1;
    }

}
