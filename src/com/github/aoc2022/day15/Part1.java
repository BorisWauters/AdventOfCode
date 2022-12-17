package com.github.aoc2022.day15;

import java.io.IOException;
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

public class Part1 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2022/day15.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        int y = 2000000;
        List<BeaconPosition> positions = new ArrayList<>();
        for (String line : lines) {
            List<Integer> list = Arrays.stream(line.split(" "))
                    .map(s -> s.replace(",", ""))
                    .map(s -> s.replace(":", ""))
                    .map(s -> s.replace("x=", ""))
                    .map(s -> s.replace("y=", ""))
                    .filter(s -> s.matches(".?\\d+"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            positions.add(new BeaconPosition(list.get(0), list.get(1), list.get(2), list.get(3)));
        }

        Set<Integer> set = new HashSet<>();

        for (var bp : positions) {
            set.addAll(bp.caclulateInfeasibleBeaconPoints(y));
        }

        System.out.println(set.size());
    }
}

class BeaconPosition {
    IntPair sensor;
    IntPair beacon;

    public BeaconPosition(int sx, int sy, int bx, int by) {
        sensor = new IntPair(sx, sy);
        beacon = new IntPair(bx, by);
    }

    public Set<Integer> caclulateInfeasibleBeaconPoints(int y) {
        int yMin = sensor.getB() - getManhattanDistance();
        int yMax = sensor.getB() + getManhattanDistance();

        if (yMin > y || y > yMax) {
            return Set.of();
        }

        int yDist = Math.abs(sensor.getB() - y);
        int xDist = getManhattanDistance() - yDist;

        Set<Integer> points = new HashSet<>();
        for (int i = 0; i <= xDist; i++) {
            points.add(sensor.getA() + i);
            points.add(sensor.getA() - i);
        }

        if (beacon.getB() == y) {
            points.remove(beacon.getA());
        }

        return points;
    }

    private int getManhattanDistance() {
        int xDiff = Math.abs(sensor.getA() - beacon.getA());
        int yDiff = Math.abs(sensor.getB() - beacon.getB());
        return xDiff + yDiff;
    }
}
