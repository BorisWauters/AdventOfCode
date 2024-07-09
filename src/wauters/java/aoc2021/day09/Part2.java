package wauters.java.aoc2021.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Part2 {

    private static Set<Point> basinPoints = new HashSet<>();

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2021/day09.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        int[][] heightMap = new int[lines.length][lines[0].length()];
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                char c = lines[i].charAt(j);
                int value = Integer.parseInt(String.valueOf(c));
                heightMap[i][j] = value;
            }
        }
        List<Integer> basinList = new ArrayList<>();
        for (int i = 0; i < heightMap.length; i++) {
            for (int j = 0; j < heightMap[0].length; j++) {
                if (higherUp(heightMap, i, j) && higherDown(heightMap, i, j) && higherLeft(heightMap, i, j) && higherRight(heightMap, i, j)) {
                    basinList.add(getBasinSize(heightMap, i, j));
                }
            }
        }
        basinList.sort(Collections.reverseOrder());
        int sum = basinList.get(0) * basinList.get(1) * basinList.get(2);
        System.out.println(sum);
    }

    private static boolean higherUp(int[][] heightMap, int i, int j) {
        if (i < 1) return true;
        return heightMap[i][j] < heightMap[i - 1][j];
    }

    private static boolean higherDown(int[][] heightMap, int i, int j) {
        if (i > heightMap.length - 2) return true;
        return heightMap[i][j] < heightMap[i + 1][j];
    }

    private static boolean higherLeft(int[][] heightMap, int i, int j) {
        if (j < 1) return true;
        return heightMap[i][j] < heightMap[i][j - 1];
    }

    private static boolean higherRight(int[][] heightMap, int i, int j) {
        if (j > heightMap[0].length - 2) return true;
        return heightMap[i][j] < heightMap[i][j + 1];
    }

    private static int getBasinSize(int[][] heightMap, int i, int j) {
        Point point = new Point(i, j);
        if (basinPoints.contains(point))
            return 0;

        int sum = 1;
        if (basinUp(heightMap, i, j)) {
            sum += getBasinSize(heightMap, i - 1, j);
        }
        if (basinDown(heightMap, i, j)) {
            sum += getBasinSize(heightMap, i + 1, j);
        }
        if (basinLeft(heightMap, i, j)) {
            sum += getBasinSize(heightMap, i, j - 1);
        }
        if (basinRight(heightMap, i, j)) {
            sum += getBasinSize(heightMap, i, j + 1);
        }

        basinPoints.add(point);
        return sum;
    }

    private static boolean basinUp(int[][] heightMap, int i, int j) {
        if (i < 1) return false;
        if (heightMap[i - 1][j] == 9) return false;
        return heightMap[i][j] < heightMap[i - 1][j];
    }

    private static boolean basinDown(int[][] heightMap, int i, int j) {
        if (i > heightMap.length - 2) return false;
        if (heightMap[i + 1][j] == 9) return false;
        return heightMap[i][j] < heightMap[i + 1][j];
    }

    private static boolean basinLeft(int[][] heightMap, int i, int j) {
        if (j < 1) return false;
        if (heightMap[i][j - 1] == 9) return false;
        return heightMap[i][j] < heightMap[i][j - 1];
    }

    private static boolean basinRight(int[][] heightMap, int i, int j) {
        if (j > heightMap[0].length - 2) return false;
        if (heightMap[i][j + 1] == 9) return false;
        return heightMap[i][j] < heightMap[i][j + 1];
    }

    private static class Point {
        int i;
        int j;

        public Point(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return i == point.i && j == point.j;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j);
        }
    }
}

