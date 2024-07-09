package com.github.aoc2023.day05;

import java.util.*;

public class RangeMap {

    private final String source;
    private final String destination;
    private final List<RangeEntry> entries;

    public RangeMap(String source, String destination) {
        this.source = source;
        this.destination = destination;
        this.entries = new ArrayList<>();
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public void addEntry(long destinationStart, long sourceStart, long length) {
        entries.add(new RangeEntry(destinationStart, sourceStart, length));
    }

    public long get(long source) {
        for (RangeEntry entry : entries) {
            if (entry.inRange(source)) {
                return entry.getDestination(source);
            }
        }
        return source;
    }

    public record RangeEntry(long destinationStart, long sourceStart, long length) {

        public boolean inRange(long source) {
            return source >= sourceStart && source < sourceStart + length;
        }

        public long getDestination(long source) {
            return destinationStart + (source - sourceStart);
        }
    }
}
