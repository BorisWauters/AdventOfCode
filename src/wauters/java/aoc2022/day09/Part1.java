package wauters.java.aoc2022.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import com.github.helper.IntPair;

public class Part1 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2022/day09.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        RopeL2 rope = new RopeL2();
        Arrays.stream(lines)
                .map(line -> new Move(line.split(" ")))
                .forEach(move -> rope.move(move));

        System.out.println(rope.visitedPositions.size());
    }
}

class RopeL2 {
    private IntPair headPosition;
    private IntPair tailPosition;

    final Set<IntPair> visitedPositions;

    public RopeL2() {
        this.headPosition = new IntPair(0, 0);
        this.tailPosition = new IntPair(0, 0);

        this.visitedPositions = new HashSet<>();
        addCurrentTailPosition();
    }

    public void move(Move move) {
        System.out.println(move.direction + " " + move.steps + "\t");

        for (int i = 0; i < move.steps; i++) {
            moveOnce(move);
        }
    }

    private void moveOnce(Move move) {
        IntPair previousHeadPosition = new IntPair(headPosition.getA(), headPosition.getB());
        switch (move.direction) {
            case "U":
                headPosition.setA(headPosition.getA() + 1);
                if (headPosition.getA() > tailPosition.getA() + 1) {
                    tailPosition = previousHeadPosition;
                    addCurrentTailPosition();
                }
                break;
            case "D":
                headPosition.setA(headPosition.getA() - 1);
                if (headPosition.getA() < tailPosition.getA() - 1) {
                    tailPosition = previousHeadPosition;
                    addCurrentTailPosition();
                }
                break;
            case "L":
                headPosition.setB(headPosition.getB() - 1);
                if (headPosition.getB() < tailPosition.getB() - 1) {
                    tailPosition = previousHeadPosition;
                    addCurrentTailPosition();
                }
                break;
            case "R":
                headPosition.setB(headPosition.getB() + 1);
                if (headPosition.getB() > tailPosition.getB() + 1) {
                    tailPosition = previousHeadPosition;
                    addCurrentTailPosition();
                }
                break;
            default:
                throw new RuntimeException("Illegal input");
        }
    }

    private void addCurrentTailPosition() {
        IntPair copy = new IntPair(this.tailPosition.getA(), this.tailPosition.getB());
        this.visitedPositions.add(copy);
    }
}
