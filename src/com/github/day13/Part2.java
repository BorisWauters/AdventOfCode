package com.github.day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Part2 {

    private static int xLength;
    private static int yLength;

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/day13.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        Set<Point> points = new HashSet<>();
        List<String> folds = new ArrayList<>();
        for (String line : lines) {
            if (line.matches("\\b\\d+,\\d+\\b"))
                addPoint(points, new Point(line.split(",")));
            else if (!line.isBlank())
                folds.add(line.replace("fold along ", ""));
        }

        for (var fold : folds) {
            points = fold(points, fold);
        }

        for (int i = 0; i <= yLength; i++) {
            for (int j = 0; j <= xLength; j++) {
                if (points.contains(new Point(j, i))) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    private static void addPoint(Set<Point> points, Point p) {
        points.add(p);
        xLength = Math.max(xLength, p.x);
        yLength = Math.max(yLength, p.y);
    }

    private static Set<Point> fold(Set<Point> points, String fold) {
        String[] foldDetails = fold.split("=");
        boolean foldOverX = "x".equals(foldDetails[0]);
        int foldLine = Integer.parseInt(foldDetails[1]);

        Set<Point> newPoints = new HashSet<>();
        int other = foldOverX ? yLength : xLength;

        xLength = 0;
        yLength = 0;

        for (int i = 0; i < foldLine; i++) {
            int j = 2 * foldLine - i;

            for (int k = 0; k <= other; k++) {
                Point p1;
                Point p2;
                if (foldOverX) {
                    p1 = new Point(i, k);
                    p2 = new Point(j, k);
                } else {
                    p1 = new Point(k, i);
                    p2 = new Point(k, j);
                }

                if (points.contains(p1) || points.contains(p2))
                    addPoint(newPoints, p1);
            }
        }

        return newPoints;
    }

    private static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point(String[] line) {
            this.x = Integer.parseInt(line[0]);
            this.y = Integer.parseInt(line[1]);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
