package wauters.java.aoc2022.day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.helper.IntPair;

public class Main12 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2022/day12.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        int length = lines.length;
        int width = lines[0].length();
        int[][] grid = new int[length][width];
        IntPair start = new IntPair(0, 0);
        IntPair end = new IntPair(0, 0);
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                char c = lines[i].charAt(j);

                if (c == 'S') {
                    start = new IntPair(i, j);
                    c = 'a';
                } else if (c == 'E') {
                    end = new IntPair(i, j);
                    c = 'z';
                }

                grid[i][j] = mapToHeight(c);
            }
        }

        Dijkstra dijkstra = new Dijkstra(grid, end);
        System.out.println("Part 1: " + dijkstra.solve(List.of(start)));
        System.out.println("Part 2: " + dijkstra.solve(dijkstra.findAllPossibleStartPositions()));
    }

    private static int mapToHeight(char c) {
        return c - 'a';
    }
}

class Dijkstra {
    private final int[][] grid;
    private final IntPair end;

    private final int length;
    private final int width;

    public Dijkstra(int[][] grid, IntPair end) {
        this.grid = grid;
        this.end = end;

        this.length = grid.length;
        this.width = grid[0].length;
    }

    public List<IntPair> findAllPossibleStartPositions() {
        List<IntPair> startPositions = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == 0) {
                    startPositions.add(new IntPair(i, j));
                }
            }
        }
        return startPositions;
    }

    public int solve(List<IntPair> startNodes) {
        Map<IntPair, Integer> helper = new HashMap<>();
        Set<IntPair> visitedNodes = new HashSet<>();
        Queue<IntPair> queue = new PriorityQueue<>((p1, p2) -> Integer.compare(helper.get(p1), helper.get(p2)));

        for (IntPair start : startNodes) {
            helper.put(start, 0);
            queue.add(start);
        }

        while (!queue.isEmpty()) {
            IntPair current = queue.poll();
            visitedNodes.add(current);
            int currentPathLength = helper.get(current);

            if (current.equals(end)) {
                return helper.get(current);
            }

            for (IntPair node : getAdjacentNodes(current)) {
                if (visitedNodes.contains(node))
                    continue;

                if (queue.contains(node)) {
                    queue.remove(node);
                }

                if (helper.containsKey(node)) {
                    if (currentPathLength + 1 < helper.get(node)) {
                        helper.put(node, currentPathLength + 1);
                    }
                } else {
                    helper.put(node, currentPathLength + 1);
                }

                queue.add(node);
            }
        }

        throw new RuntimeException("Invalid input");
    }

    private List<IntPair> getAdjacentNodes(IntPair current) {
        return List.of(
            new IntPair(current.getA() + 1, current.getB()),
            new IntPair(current.getA() - 1, current.getB()),
            new IntPair(current.getA(), current.getB() + 1),
            new IntPair(current.getA(), current.getB() - 1)
        ).stream().filter(p -> {
            if (p.getA() < 0)
                return false;
            if (p.getA() >= length)
                return false;
            if (p.getB() < 0)
                return false;
            if (p.getB() >= width)
                return false;
            return grid(p) - grid(current) <= 1;
        }).collect(Collectors.toList());
    }

    private int grid(IntPair p) {
        return grid[p.getA()][p.getB()];
    }
}
