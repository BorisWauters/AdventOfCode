package com.github.aoc2022.day16;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Valve implements Comparable<Valve> {
    final String key;
    final int flowRate;
    final List<String> tunnels;

    final Map<String, Integer> distances;

    public Valve(String key, int flowRate, String[] tunnels) {
        this.key = key;
        this.flowRate = flowRate;
        this.tunnels = Arrays.stream(tunnels).toList();

        this.distances = new HashMap<>();
        this.distances.put(key, 0);
        for (String tunnel : tunnels) {
            this.distances.put(tunnel, 1);
        }
    }

    @Override
    public int compareTo(Valve o) {
        return Integer.compare(this.flowRate, o.flowRate);
    }
}
