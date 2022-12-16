package com.github.aoc2022.day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.helper.IntPair;

public class Part2 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2022/day14.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        int xMin = 500;
        int xMax = 500;
        int yMax = 0;
        for (String line : lines) {
            String[] points = line.split(" -> ");

            for (String point : points) {
                String[] xy = point.split(",");
                int x = Integer.parseInt(xy[0]);
                int y = Integer.parseInt(xy[1]);

                xMin = Math.min(xMin, x);
                xMax = Math.max(xMax, x);
                yMax = Math.max(yMax, y);
            }
        }

        Cave cave = new Cave(xMin, xMax, yMax);

        for (String line : lines) {
            String[] points = line.split(" -> ");
            List<IntPair> pairs = Arrays.stream(points).map(p -> {
                String[] xy = p.split(",");
                int x = Integer.parseInt(xy[0]);
                int y = Integer.parseInt(xy[1]);

                return new IntPair(x, y);
            }).collect(Collectors.toList());

            for (int i = 0; i < pairs.size() - 1; i++) {
                cave.setRock(pairs.get(i), pairs.get(i + 1));
            }
        }

        System.out.println(cave + "\n\n");
        int result = 0;
        for (int i = 0; i < 10000; i++) {
            if (cave.dropSand()) {
                System.out.println(cave + "\n\n");
                System.out.println("Fallen sand: " +  i);
                result += i;
                break;
            }
        }

        result += cave.calculateOverflow();
        System.out.println("\nResult: " + result);
    }
}

class Cave {
    private final int xMin;
    private final int xMax;
    private final int yMax;

    private final Tyle[][] slice;

    public Cave(int xMin, int xMax, int yMax) {
        this.xMin = xMin - 1;
        this.xMax = xMax + 2;
        this.yMax = yMax + 2;

        this.slice = new Tyle[this.yMax][xSize()];

        for (int i = 0; i < this.yMax; i++) {
            for (int j = 0; j < xSize(); j++) {
                slice[i][j] = Tyle.AIR;
            }
        }
    }

    public boolean dropSand() {
        IntPair position = new IntPair(500, 0);

        if (get(position).isBlocked()) {
            // End condition
            return true;
        }

        while (true) {
            IntPair down = new IntPair(position.getA(), position.getB() + 1);
            if (down.getB() >= yMax) {
                // Set sand at the bottom
                set(position, Tyle.SAND);
                return false;
            }

            if (!get(down).isBlocked()) {
                position = down;
                continue;
            }

            if (down.getB() < yMax) {
                IntPair left = new IntPair(down.getA() - 1, down.getB());
                // Threat overflow boundary as wall and calculate overflow afterwards
                if (left.getA() >= xMin) {
                    if (!get(left).isBlocked()) {
                        position = left;
                        continue;
                    }
                }

                IntPair right = new IntPair(down.getA() + 1, down.getB());
                // Threat overflow boundary as wall and calculate overflow afterwards
                if (right.getA() < xMax) {
                    if (!get(right).isBlocked()) {
                        position = right;
                        continue;
                    }
                }
            }

            set(position, Tyle.SAND);
            return false;
        }
    }

    public void setRock(IntPair start, IntPair end) {
        for (IntPair pair : convert(start, end)) {
            set(pair, Tyle.ROCK);
        }
    }

    public int calculateOverflow() {
        int overflow = 0;
        // Calculate the left overflow
        for (int i = 0; i < yMax; i++) {
            if (slice[i][0].isBlocked()) {
                int height = yMax - i - 1;
                int amount = height * (height + 1) / 2;
                overflow += amount;
                System.out.println("Left overflow: " + amount);
                break;
            }
        }

        // Calculate the right overflow
        for (int i = 0; i < yMax; i++) {
            if (slice[i][xSize() - 1].isBlocked()) {
                int height = yMax - i - 1;
                int amount = height * (height + 1) / 2;
                overflow += amount;
                System.out.println("Right overflow: " + amount);
                break;
            }
        }
        return overflow;
    }

    private IntPair[] convert(IntPair start, IntPair end) {
        List<IntPair> pairs = new ArrayList<>();
        pairs.add(start);
        pairs.add(end);

        if (start.getA().equals(end.getA())) {
            int min = Math.min(start.getB(), end.getB());
            int max = Math.max(start.getB(), end.getB());
            for (int i = min + 1; i < max; i++) {
                pairs.add(new IntPair(start.getA(), i));
            }
            return pairs.toArray(new IntPair[pairs.size()]);
        }

        int min = Math.min(start.getA(), end.getA());
        int max = Math.max(start.getA(), end.getA());

        for (int i = min + 1; i < max; i++) {
            pairs.add(new IntPair(i, start.getB()));
        }
        return pairs.toArray(new IntPair[pairs.size()]);
    }

    private Tyle get(IntPair pair) {
        return this.slice[pair.getB()][pair.getA() - xMin];
    }

    private void set(IntPair pair, Tyle tyle) {
        this.slice[pair.getB()][pair.getA() - xMin] = tyle;
    }

    private int xSize() {
        return this.xMax - this.xMin;
    }

    @Override
    public String toString() {
        StringBuilder stb = new StringBuilder();

        for (int i = 0; i < 500 - xMin; i++) {
            stb.append(" ");
        }
        stb.append("|\n");

        for (int i = 0; i < yMax; i++) {
            for (int j = 0; j < xSize(); j++) {
                stb.append(slice[i][j]);
            }
            stb.append("\n");
        }

        return stb.toString();
    }
}
