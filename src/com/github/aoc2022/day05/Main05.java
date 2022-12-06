package com.github.aoc2022.day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.stream.Stream;

public class Main05 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2022/day05.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        int sep = findSeparation(lines);

        int size = (int) Arrays.stream(lines[sep - 1].split("")).distinct().count() - 1;
        CrateMover crateMover9000 = new CrateMover9000(size);
        CrateMover crateMover9001 = new CrateMover9001(size);

        for (int i = 0; i < size; i++) {
            int index = 1 + 4 * i;
            for (int j = sep - 2; j >= 0; j--) {
                char c = lines[j].charAt(index);
                if (c != ' ') {
                    crateMover9000.add(i, c);
                    crateMover9001.add(i, c);
                }
            }
        }

        for (int i = sep + 1; i < lines.length; i++) {
            String[] moveOperation = lines[i].split(" ");

            int amount = Integer.parseInt(moveOperation[1]);
            int from = Integer.parseInt(moveOperation[3]) - 1;
            int to = Integer.parseInt(moveOperation[5]) - 1;

            crateMover9000.move(amount, from, to);
            crateMover9001.move(amount, from, to);
        }

        System.out.print("Part 1: ");
        crateMover9000.printTopCrates();
        System.out.print("\nPart 2: ");
        crateMover9001.printTopCrates();
    }

    private static int findSeparation(String[] lines) {
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].isBlank())
                return i;
        }
        throw new RuntimeException("Illegal input");
    }
}

abstract class CrateMover {
    protected List<Deque<Character>> crates;

    public CrateMover(int size) {
        crates = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            crates.add(new ArrayDeque<>());
        }
    }

    public void add(int row, char content) {
        crates.get(row).push(content);
    }

    public void printTopCrates() {
        for (Deque<Character> row : crates) {
            System.out.print(row.peek());
        }
    }

    public abstract void move(int amount, int from, int to);
}

class CrateMover9000 extends CrateMover {

    public CrateMover9000(int size) {
        super(size);
    }

    @Override
    public void move(int amount, int from, int to) {
        for (int i = 0; i < amount; i++) {
            move(from, to);
        }
    }

    private void move(int from, int to) {
        char item = crates.get(from).pop();
        crates.get(to).push(item);
    }
}

class CrateMover9001 extends CrateMover {

    public CrateMover9001(int size) {
        super(size);
    }

    @Override
    public void move(int amount, int from, int to) {
        Deque<Character> cratesToMove = new ArrayDeque<>(amount);
        for (int i = 0; i < amount; i++) {
            cratesToMove.push((crates.get(from).pop()));
        }

        for (Character character : cratesToMove) {
            crates.get(to).push(character);
        }
    }
}
