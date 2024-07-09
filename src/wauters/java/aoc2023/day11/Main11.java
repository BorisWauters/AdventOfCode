package wauters.java.aoc2023.day11;

import wauters.java.Solution;

import java.util.HashSet;
import java.util.Set;

public class Main11 extends Solution {
    @Override
    protected void solve(String[] lines) {
        System.out.println("Part 1: " + getDistance(lines, 2));
        System.out.println("Part 2: " + getDistance(lines, 1000000));
    }

    private long getDistance(String[] lines, int offset) {
        Set<Integer> expandedRows = new HashSet<>();
        Set<Integer> expandedCols = new HashSet<>();
        for (int i = 0; i < lines.length; i++) {
            expandedRows.add(i);
        }
        for (int i = 0; i < lines[0].length(); i++) {
            expandedCols.add(i);
        }

        Set<Galaxy> galaxies = new HashSet<>();

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);

                if (c == '#') {
                    Galaxy galaxy = new Galaxy(i, j, offset);
                    galaxies.add(galaxy);

                    expandedRows.remove(i);
                    expandedCols.remove(j);
                }
            }
        }

        for (Galaxy galaxy : galaxies) {
            galaxy.correction(expandedRows, expandedCols);
        }


        long sum = 0;
        Set<Galaxy> remainingGalaxies = new HashSet<>(galaxies);
        for (Galaxy galaxy : galaxies) {
            remainingGalaxies.remove(galaxy);
            sum += remainingGalaxies.stream()
                    .mapToLong(galaxy::getDistance)
                    .sum();
        }
        return sum;
    }
}
