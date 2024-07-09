package wauters.java.aoc2022.day11;

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

public class Part1 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2022/day11.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        List<IntMonkey> monkeys = createMonkeys(lines);

        for (int i = 0; i < 20; i++) {
            monkeys.forEach(m -> {
                m.turn((target) -> monkeys.get(target));
            });
        }

        int[] counts = monkeys.stream().mapToInt(IntMonkey::getCount).sorted().toArray();
        int monkeyBusiness = 1;
        for (int i = 0; i < 2; i++) {
            monkeyBusiness *= counts[counts.length - 1 - i];
        }

        System.out.println(monkeyBusiness);
    }

    private static List<IntMonkey> createMonkeys(String[] lines) {
        List<IntMonkey> monkeys = new ArrayList<>();
        for (int i = 0; i < lines.length; i++) {
            if (!lines[i].contains("Monkey")) {
                continue;
            }

            i++;
            List<Integer> items = Arrays.stream(lines[i].split(": ")[1].split(", "))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            i++;
            String[] operation = lines[i].split(": ")[1].split(" ");

            i++;
            int test = Integer.parseInt(lines[i].split(": ")[1].split(" ")[2]);

            i++;
            int targetTrue = Integer.parseInt(lines[i].split(": ")[1].split(" ")[3]);

            i++;
            int targetFalse = Integer.parseInt(lines[i].split(": ")[1].split(" ")[3]);

            monkeys.add(new IntMonkey(items, operation, test, targetTrue, targetFalse));
        }
        return monkeys;
    }

}

class IntMonkey {
    private final Queue<Integer> items;
    private final Function<Integer, Integer> operation;
    private final Function<Integer, Boolean> test;
    private final int targetTrue;
    private final int targetFalse;

    private int count;

    public IntMonkey(List<Integer> items, String[] operation, int test, int targetTrue, int targetFalse) {
        this.items = new LinkedList<>(items);
        this.operation = createOperation(operation);
        this.test = (i) -> i % test == 0;
        this.targetTrue = targetTrue;
        this.targetFalse = targetFalse;

        this.count = 0;
    }

    public void turn(Function<Integer, IntMonkey> next) {
        while (!items.isEmpty()) {
            int item = items.poll();
            count++;

            item = operation.apply(item);
            item /= 3;

            if (test.apply(item)) {
                next.apply(targetTrue).addItem(item);
            } else {
                next.apply(targetFalse).addItem(item);
            }
        }
    }

    public void addItem(int item) {
        this.items.add(item);
    }

    public int getCount() {
        return count;
    }

    private Function<Integer, Integer> createOperation(String[] operation) {
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

        int v = Integer.parseInt(operation[4]);
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
