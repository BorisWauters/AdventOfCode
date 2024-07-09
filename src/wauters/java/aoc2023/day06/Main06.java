package wauters.java.aoc2023.day06;

import wauters.java.Solution;

import java.util.Arrays;

public class Main06 extends Solution {
    @Override
    protected void solve(String[] lines) {
        long[] times = getValues(lines[0]);
        long[] distances = getValues(lines[1]);

        int result = 1;
        for (int i = 0; i < times.length; i++) {
            result*= getAmountOfSolutions(times[i], distances[i]);
        }
        System.out.println("Part 1: " + result);

        System.out.println("Part 2: " + getAmountOfSolutions(getValue(lines[0]), getValue(lines[1])));
    }

    private long[] getValues(String line) {
        return Arrays.stream(line.replaceAll("[a-zA-Z]", "")
                .replace(':', ' ')
                .trim()
                .replaceAll("\\s+", "-")
                .split("-")
        ).mapToLong(Long::parseLong).toArray();
    }

    private long getValue(String line) {
        return Long.parseLong(line
                .replaceAll("[a-zA-Z]", "")
                .replace(':', ' ')
                .replaceAll("\\s+", "")
        );
    }

    private long getAmountOfSolutions(long time, long distance) {
        long halfTime = (time + 1) / 2;

        int solutions = 0;
        for (long i = 0; i < halfTime; i++) {
            if (i * (time - i) > distance) {
                solutions++;
            }
        }

        solutions *= 2;

        if (time % 2 == 0 && halfTime * halfTime > distance) {
            solutions++;
        }

        return solutions;
    }
}
