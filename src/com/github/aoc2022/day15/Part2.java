package com.github.aoc2022.day15;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
        List<Position> positions = new ArrayList<>();
        for (String line : lines) {
            List<Integer> list = Arrays.stream(line.split(" "))
                    .map(s -> s.replace(",", ""))
                    .map(s -> s.replace(":", ""))
                    .map(s -> s.replace("x=", ""))
                    .map(s -> s.replace("y=", ""))
                    .filter(s -> s.matches(".?\\d+"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            positions.add(new Position(list.get(0), list.get(1), list.get(2), list.get(3)));
        }

        Set<IntPair> set = new HashSet<>();
        for (var bp : positions) {
            set.addAll(bp.getEdgePositions());
        }

        for (var p : set) {
            boolean found = true;
            for (var bp : positions) {
                if (bp.fallsWithin(p)) {
                    found = false;
                    break;
                }
            }

            if (found) {
                BigInteger x = new BigInteger(p.getA().toString());
                BigInteger m = new BigInteger("4000000");
                BigInteger y = new BigInteger(p.getB().toString());
                BigInteger tuningFrequency = x.multiply(m).add(y);
                System.out.println(tuningFrequency.toString());
                return;
            }
        }
        throw new RuntimeException("Illegal input");
    }
}

class Position {
    IntPair sensor;
    IntPair beacon;

    public Position(int sx, int sy, int bx, int by) {
        sensor = new IntPair(sx, sy);
        beacon = new IntPair(bx, by);
    }

    public boolean fallsWithin(IntPair p) {
        return getManhattanDistance() > getDistanceTo(p);
    }

    public Set<IntPair> getEdgePositions() {
        int md = getManhattanDistance() + 1;
        Set<IntPair> points = new HashSet<>();

        for (int x = 0; x <= md; x++) {
            int y = md - x;

            int sx = sensor.getA() + x;
            int sy = sensor.getB() + y;

            if (validateXY(sx, sy)) {
                points.add(new IntPair(sx, sy));
            }

            if (validateXY(sx, -sy)) {
                points.add(new IntPair(sx, -sy));
            }

            if (validateXY(sx, sy)) {
                points.add(new IntPair(sx, sy));
            }

            if (validateXY(-sx, -sy)) {
                points.add(new IntPair(-sx, -sy));
            }
        }

        return points;
    }

    private boolean validateXY(int x, int y) {
        if (x > 4000000 || x < 0)
            return false;
        return y <= 4000000 && y >= 0;
    }

    private int getManhattanDistance() {
        int xDiff = Math.abs(sensor.getA() - beacon.getA());
        int yDiff = Math.abs(sensor.getB() - beacon.getB());
        return xDiff + yDiff;
    }

    private int getDistanceTo(IntPair p) {
        int xDiff = Math.abs(sensor.getA() - p.getA());
        int yDiff = Math.abs(sensor.getB() - p.getB());
        return xDiff + yDiff;
    }
}
