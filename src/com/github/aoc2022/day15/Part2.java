package com.github.aoc2022.day15;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import com.github.helper.IntPair;

public class Part2 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2022/day15.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        List<SensorEntry> entries = SensorEntry.fetch(lines);

        Set<IntPair> edgePositions = new HashSet<>();
        for (var entry : entries) {
            edgePositions.addAll(entry.getEdgePositions());
        }

        for (var edgePosition : edgePositions) {
            boolean found = true;
            for (var entry : entries) {
                if (entry.contains(edgePosition)) {
                    found = false;
                    break;
                }
            }

            if (found) {
                BigInteger x = new BigInteger(edgePosition.getA().toString());
                BigInteger m = new BigInteger("4000000");
                BigInteger y = new BigInteger(edgePosition.getB().toString());

                BigInteger tuningFrequency = x.multiply(m).add(y);

                System.out.println(tuningFrequency.toString());
                return;
            }
        }
        throw new RuntimeException("Illegal input");
    }
}
