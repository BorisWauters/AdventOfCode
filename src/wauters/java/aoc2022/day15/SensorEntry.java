package wauters.java.aoc2022.day15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.helper.IntPair;

public class SensorEntry {
    private final IntPair sensor;
    private final IntPair beacon;

    public SensorEntry(int sx, int sy, int bx, int by) {
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

    public boolean contains(IntPair p) {
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

    public static List<SensorEntry> fetch(String [] lines) {
        List<SensorEntry> entries = new ArrayList<>();
        for (String line : lines) {
            List<Integer> list = Arrays.stream(line.split(" "))
                    .map(s -> s.replace(",", ""))
                    .map(s -> s.replace(":", ""))
                    .map(s -> s.replace("x=", ""))
                    .map(s -> s.replace("y=", ""))
                    .filter(s -> s.matches(".?\\d+"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            entries.add(new SensorEntry(list.get(0), list.get(1), list.get(2), list.get(3)));
        }

        return entries;
    }
}
