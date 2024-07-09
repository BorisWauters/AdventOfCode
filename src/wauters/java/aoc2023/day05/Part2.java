package com.github.aoc2023.day05;

import com.github.Solution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Gets a correct solution but takes way too long to run
 * todo -> optimize
 */
public class Part2 extends Solution {
    @Override
    protected void solve(String[] lines) {
        // Parse input
        long[] seeds = Arrays.stream(lines[0].replace("seeds: ", "").split(" ")).mapToLong(Long::parseLong).toArray();
        Map<String, RangeMap> rangeMaps = new HashMap<>();

        int count = 2;
        for (int i = 0; i < 7; i++) {
            String[] sourceDestination = lines[count].replace(" map:", "").split("-to-");
            RangeMap rangeMap = new RangeMap(sourceDestination[0], sourceDestination[1]);

            count++;
            while (!lines[count].isBlank()) {
                String line = lines[count++];
                String[] entry = line.split(" ");
                rangeMap.addEntry(Long.parseLong(entry[0]), Long.parseLong(entry[1]), Long.parseLong(entry[2]));

                if (count >= lines.length) {
                    break;
                }
            }

            rangeMaps.put(rangeMap.getSource(), rangeMap);
            count++;
        }

        // Solve
        long min = Integer.MAX_VALUE;
        for (int i = 0; i < seeds.length/2; i++) {
            long seedStart = seeds[2 * i];
            for (int j = 0; j < seeds[(2 * i) + 1]; j++) {
                long location = getLocation(seedStart + j, rangeMaps);
                if (location < min) {
                    min = location;
                }
            }
        }

        System.out.println(min);
    }

    private long getLocation(long seed, Map<String, RangeMap> rangeMaps) {
        String state = "seed";

        while (!state.equals("location")) {
            RangeMap rangeMap = rangeMaps.get(state);
            seed = rangeMap.get(seed);
            state = rangeMap.getDestination();
        }

        return seed;
    }
}
