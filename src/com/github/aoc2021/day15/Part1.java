package com.github.aoc2021.day15;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Part1 {

    private static int height;
    private static int width;
    private static Point[][] cave;

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2021/day15.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        height = lines.length;
        width = lines[0].length();
        cave = new Point[height][width];

        // Create priority queue containing all vertices
        PriorityQueue<Point> vertices = new PriorityQueue<>(Point::compareTo);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int weight = Integer.parseInt(lines[i].substring(j, j + 1));
                Point point = new Point(i, j, weight);
                cave[i][j] = point;
                vertices.add(point);
            }
        }
        cave[0][0].setTotalRisk(0);
        vertices.remove(cave[0][0]);
        vertices.add(cave[0][0]);

        // Solve using Dijkstra's algorithm
        while (!vertices.isEmpty()) {
            Point point = vertices.poll();

            if (point.i == height - 1 && point.j == width - 1)
                break;

            for (Point neighbor : getNeighbours(point)) {
                int distanceToNeighbor = point.getTotalRisk() + neighbor.risk;
                if (distanceToNeighbor < neighbor.getTotalRisk()) {
                    neighbor.setTotalRisk(distanceToNeighbor);

                    vertices.remove(neighbor);
                    vertices.add(neighbor);
                }
            }
        }

        // Solution
        System.out.println(cave[height - 1][width - 1].getTotalRisk());
    }

    public static Set<Point> getNeighbours(Point point) {
        Set<Point> neighbors = new HashSet<>();

        if (point.i != 0) neighbors.add(cave[point.i - 1][point.j]);
        if (point.i < height - 1) neighbors.add(cave[point.i + 1][point.j]);
        if (point.j != 0) neighbors.add(cave[point.i][point.j - 1]);
        if (point.j < width - 1) neighbors.add(cave[point.i][point.j + 1]);

        return neighbors;
    }
}
