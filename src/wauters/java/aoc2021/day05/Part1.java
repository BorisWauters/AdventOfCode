package com.github.aoc2021.day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Part1 {

    private static final Map<String, Integer> diagram = new HashMap<>();

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2021/day05.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        for (String line : lines) {
            String[] beginToEnd = line.split(" -> ");
            String beginPoint = beginToEnd[0];
            String endPoint = beginToEnd[1];
            for (String point : getCoveredPoints(beginPoint, endPoint)) {
                diagram.putIfAbsent(point, 0);
                diagram.compute(point, (k, v) -> ++v);
            }
        }
        AtomicInteger count = new AtomicInteger();
        diagram.forEach((k,v) -> {
            if(v > 1) {
                count.getAndIncrement();
            }
        });
        System.out.println(count.get());
    }

    private static Set<String> getCoveredPoints(String begin, String end) {
        Set<String> result = new java.util.HashSet<>(Set.of(begin, end));

        int[] beginPoint = Arrays.stream(begin.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] endPoint = Arrays.stream(end.split(",")).mapToInt(Integer::parseInt).toArray();
        int deltaX = endPoint[0] - beginPoint[0];
        int deltaY = endPoint[1] - beginPoint[1];

        if (deltaX != 0 && deltaY == 0) {
            //Horizontal lines
            int factor = deltaX > 0 ? 1 : -1;
            for (int i = 1; i < Math.abs(deltaX); i++) {
                int[] point = new int[]{beginPoint[0] + i * factor, beginPoint[1]};
                result.add(point[0] + "," + point[1]);
            }
        } else if (deltaX == 0 && deltaY != 0) {
            //Vertical lines
            int factor = deltaY > 0 ? 1 : -1;
            for (int i = 1; i < Math.abs(deltaY); i++) {
                int[] point = new int[]{beginPoint[0], beginPoint[1] + i * factor};
                result.add(point[0] + "," + point[1]);
            }
        } else if (deltaX != 0 && deltaY != 0) {
            //Diagonal lines
            return Set.of();
        }

        return result;
    }
}
