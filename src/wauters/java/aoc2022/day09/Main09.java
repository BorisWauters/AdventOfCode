package wauters.java.aoc2022.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import com.github.helper.IntPair;

public class Main09 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2022/day09.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        Rope rope1 = createRope(2);
        Rope rope2 = createRope(10);
        Arrays.stream(lines)
                .map(line -> new Move(line.split(" ")))
                .forEach(move -> {
                    rope1.move(move);
                    rope2.move(move);
                });

        System.out.println("Part 1: " + rope1.getVisitedTailPositions());
        System.out.println("Part 2: " + rope2.getVisitedTailPositions());
    }

    private static Rope createRope(int length) {
        RopePiece tail = new RopePiece();
        RopePiece currentRopePiece = tail;
        for (int i = 0; i < length - 2; i++) {
            RopePiece ropePiece = new RopePiece(currentRopePiece);
            currentRopePiece = ropePiece;
        }
        return new Rope(currentRopePiece, tail);
    }
}

class RopePiece {
    private IntPair position;
    private final RopePiece next;

    // Only for tail
    final Set<IntPair> visitedPositions;

    // Constructor for tail piece
    public RopePiece() {
        this.position = new IntPair(0, 0);
        this.next = null;

        this.visitedPositions = new HashSet<>();
        addCurrentPosition();
    }

    // Constructor for middle pieces
    public RopePiece(RopePiece next) {
        this.position = new IntPair(0, 0);
        this.next = next;

        this.visitedPositions = null;
    }

    public int getVisitedPositions() {
        if (visitedPositions == null)
            return 0;
        return visitedPositions.size();
    }

    public void correctPosition(IntPair headPosition) {
        if (fallsWithin(headPosition))
            return;

        position = new IntPair(
                position.getA() + getOffset(headPosition.getA(), position.getA()),
                position.getB() + getOffset(headPosition.getB(), position.getB()));
        addCurrentPosition();

        if (next != null)
            next.correctPosition(position);
    }

    private boolean fallsWithin(IntPair head) {
        return fallsWithin(head.getA(), position.getA()) == 0 && fallsWithin(head.getB(), position.getB()) == 0;
    }

    private int fallsWithin(int head, int tail) {
        if (head + 1 == tail || head - 1 == tail || head == tail)
            return 0;

        if (head + 1 > tail)
            return 1;

        return -1;
    }

    private int getOffset(int head, int tail) {
        if (head == tail)
            return 0;

        if (head > tail)
            return 1;

        return -1;
    }

    private void addCurrentPosition() {
        // Only set vistite positions in tail
        if (visitedPositions == null)
            return;

        IntPair copy = new IntPair(position.getA(), position.getB());
        this.visitedPositions.add(copy);
    }
}

class Rope {
    private IntPair headPosition;
    private final RopePiece next;
    private final RopePiece tail;

    public Rope(RopePiece next, RopePiece tail) {
        this.headPosition = new IntPair(0, 0);
        this.next = next;
        this.tail = tail;
    }

    public void move(Move move) {
        for (int i = 0; i < move.steps; i++) {
            moveOnce(move);
        }
    }

    private void moveOnce(Move move) {
        switch (move.direction) {
            case "U":
                headPosition.setA(headPosition.getA() + 1);
                break;
            case "D":
                headPosition.setA(headPosition.getA() - 1);
                break;
            case "L":
                headPosition.setB(headPosition.getB() - 1);
                break;
            case "R":
                headPosition.setB(headPosition.getB() + 1);
                break;
            default:
                throw new RuntimeException("Illegal input");
        }

        next.correctPosition(headPosition);
    }

    public int getVisitedTailPositions() {
        return tail.getVisitedPositions();
    }
}