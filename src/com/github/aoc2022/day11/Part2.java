package com.github.aoc2022.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Part2 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2022/day11.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        List<Monkey> monkeys = createMonkeys(lines);

        // Used to avoid long overflow, bigTest is the product of all test values
        int bigTest = 1;
        for (Monkey monkey : monkeys) {
            bigTest *= monkey.getTest();
        }

        // Rounds
        for (int i = 0; i < 10000; i++) {
            for (Monkey monkey : monkeys) {
                monkey.turn((target) -> monkeys.get(target), bigTest);
            }
        }

        int[] counts = monkeys.stream().mapToInt(Monkey::getCount).sorted().toArray();
        long monkeyBusiness = 1;
        for (int i = 0; i < 2; i++) {
            monkeyBusiness *= counts[counts.length - 1 - i];
        }

        System.out.println(monkeyBusiness);
    }

    private static List<Monkey> createMonkeys(String[] lines) {
        List<Monkey> monkeys = new ArrayList<>();
        for (int i = 0; i < lines.length; i++) {
            if (!lines[i].contains("Monkey")) {
                continue;
            }

            i++;
            List<Long> items = Arrays.stream(lines[i].split(": ")[1].split(", "))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            i++;
            String[] operation = lines[i].split(": ")[1].split(" ");

            i++;
            int test = Integer.parseInt(lines[i].split(": ")[1].split(" ")[2]);

            i++;
            int targetTrue = Integer.parseInt(lines[i].split(": ")[1].split(" ")[3]);

            i++;
            int targetFalse = Integer.parseInt(lines[i].split(": ")[1].split(" ")[3]);

            monkeys.add(new Monkey(items, operation, test, targetTrue, targetFalse));
        }
        return monkeys;
    }

}

class Monkey {
    private final Queue<Long> items;
    private final Function<Long, Long> operation;
    private final int test;
    private final int targetTrue;
    private final int targetFalse;

    private int count;

    public Monkey(List<Long> items, String[] operation, int test, int targetTrue, int targetFalse) {
        this.items = new LinkedList<>(items);
        this.operation = createOperation(operation);
        this.test = test;
        this.targetTrue = targetTrue;
        this.targetFalse = targetFalse;

        this.count = 0;
    }

    public void turn(Function<Integer, Monkey> next, int bigTest) {
        while (!items.isEmpty()) {
            long item = items.poll();
            count++;

            item = operation.apply(item);
            // An item doesn't have to bi greater than bigTest since it's mod will yield the same result.
            item %= bigTest;

            if (test(item)) {
                next.apply(targetTrue).addItem(item);
            } else {
                next.apply(targetFalse).addItem(item);
            }
        }
    }

    public void addItem(long item) {
        this.items.add(item);
    }

    public int getTest() {
        return test;
    }

    public int getCount() {
        return count;
    }

    private boolean test(long item) {
        return item % test == 0;
    }

    private Function<Long, Long> createOperation(String[] operation) {
        if (operation[4].contains("old")) {
            switch (operation[3]) {
                case "+":
                    return (old) -> old + old;
                case "*":
                    return (old) -> old * old;
                default:
                    throw new RuntimeException("Illegal input");
            }
        }

        long v = Long.parseLong(operation[4]);
        switch (operation[3]) {
            case "+":
                return (old) -> old + v;
            case "*":
                return (old) -> old * v;
            default:
                throw new RuntimeException("Illegal input");
        }
    }
}
