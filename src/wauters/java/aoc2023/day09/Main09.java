package wauters.java.aoc2023.day09;

import wauters.java.Solution;

import java.util.ArrayList;
import java.util.List;

public class Main09 extends Solution {
    @Override
    protected void solve(String[] lines) {
        long lastSum = 0;
        long firstSum = 0;
        for (String line : lines) {
            Sequence sequence = new Sequence(parse(line));

            lastSum += sequence.getLast();
            firstSum += sequence.getFirst();
        }

        System.out.println("Part 1: " + lastSum);
        System.out.println("Part 2: " + firstSum);
    }

    private long[] parse(String line) {
        String[] split = line.split(" ");
        long[] values = new long[split.length];
        for (int i = 0; i < split.length; i++) {
            values[i] = Long.parseLong(split[i]);
        }
        return values;
    }

    static class Sequence {
        List<List<Long>> values;

        public Sequence(long[] values) {
            this.values = new ArrayList<>();
            this.values.add(new ArrayList<>());
            for (long value : values) {
                this.values.get(0).add(value);
            }
            fill(0);
        }

        private void fill(int level) {
            List<Long> levelValues = values.get(level);
            List<Long> diff = new ArrayList<>(levelValues.size() - 1);
            for (int i = 1; i < levelValues.size(); i++) {
                diff.add(levelValues.get(i) - levelValues.get(i - 1));
            }
            values.add(diff);

            if (!diff.stream().allMatch(d -> d == 0)) fill(level + 1);
        }

        private long getFirst() {
            long current = 0;
            for (int i = values.size() - 1; i >= 0; i--) {
                current = values.get(i).get(0) - current;
            }

            return current;
        }

        public long getLast() {
            return values.stream().mapToLong(List::getLast).sum();
        }
    }
}
