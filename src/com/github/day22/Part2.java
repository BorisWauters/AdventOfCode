package com.github.day22;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The second part of the exercise was one of the more challenging ones.
 *
 * This exercise was solved by iterating over all steps in reverse order so already visited cuboids could be ignored.
 * This was done by keeping a set of non-overlapping cuboids to ignore.
 * When a cuboid was overlapping with another to-avoid cuboid, the cuboid was split into multiple cuboids leaving overlapping part out.
 * The newly split cuboids are used to update the sum and are added to the to-avoid set.
 * */
public class Part2 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/day22.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        Set<Cuboid> cuboidsToIgnore = new HashSet<>();
        long sum = 0;

        for (int i = lines.length - 1; i >= 0; i--) {
            boolean on = "on".equals(lines[i].split(" ")[0]);
            String[] ranges = lines[i].split(" ")[1].split(",");

            int[] xRange = Arrays.stream(ranges[0].substring(2).split("[.][.]"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            int[] yRange = Arrays.stream(ranges[1].substring(2).split("[.][.]"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            int[] zRange = Arrays.stream(ranges[2].substring(2).split("[.][.]"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            Set<Cuboid> cuboids = Set.of(new Cuboid(xRange[0], xRange[1], yRange[0], yRange[1], zRange[0], zRange[1]));

            for (var cuboidToIgnore : cuboidsToIgnore) {
                Set<Cuboid> newCuboids = new HashSet<>();
                for (var cuboid : cuboids)
                    newCuboids.addAll(cuboidToIgnore.splitIntoNonOverlappingCuboids(cuboid));
                cuboids = newCuboids;
            }
            if (on) {
                for (var cuboid : cuboids)
                    sum += cuboid.size();
            }
            cuboidsToIgnore.addAll(cuboids);
        }

        System.out.println(sum);
    }
}

class Cuboid {
    int xMin, xMax, yMin, yMax, zMin, zMax;

    public Cuboid(int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.zMin = zMin;
        this.zMax = zMax;
    }

    public long size() {
        return (long) (1 + xMax - xMin) * (1 + yMax - yMin) * (1 + zMax - zMin);
    }

    public boolean overlaps(Cuboid cuboid) {
        return overlapsX(cuboid) && overlapsY(cuboid) && overlapsZ(cuboid);
    }

    private boolean overlapsX(Cuboid cuboid) {
        return !(xMin > cuboid.xMax || cuboid.xMin > xMax);
    }

    private boolean overlapsY(Cuboid cuboid) {
        return !(yMin > cuboid.yMax || cuboid.yMin > yMax);
    }

    private boolean overlapsZ(Cuboid cuboid) {
        return !(zMin > cuboid.zMax || cuboid.zMin > zMax);
    }

    /**
     * Splits the given cuboid into smaller cuboids that don't overlap with this cuboid.
     * <p>
     * splitViaX, -Y and -Z contain the same logic just for a different axis.
     * todo -> merging these into one method passing the axis to split on would be better.
     */
    public Set<Cuboid> splitIntoNonOverlappingCuboids(Cuboid cuboid) {
        if (!this.overlaps(cuboid)) return Set.of(cuboid);

        Set<Cuboid> xCuboids = splitViaX(cuboid);
        Set<Cuboid> yCuboids = xCuboids.stream().flatMap(c -> splitViaY(c).stream()).collect(Collectors.toSet());
        Set<Cuboid> zCuboids = yCuboids.stream().flatMap(c -> splitViaZ(c).stream()).collect(Collectors.toSet());

        return zCuboids.stream().filter(c -> !overlaps(c)).collect(Collectors.toSet());
    }

    private Set<Cuboid> splitViaX(Cuboid other) {
        if (!this.overlaps(other)) return Set.of(other);
        Set<Cuboid> cuboidSet = new HashSet<>();

        int[] currentRange = {xMin, xMax};
        int[] otherRange = {other.xMin, other.xMax};
        int[] minRange = {Math.max(xMin, other.xMin), Math.min(xMax, other.xMax)};

        if (otherRange[0] < currentRange[0])
            cuboidSet.add(new Cuboid(otherRange[0], currentRange[0] - 1, other.yMin, other.yMax, other.zMin, other.zMax));
        if (otherRange[1] > currentRange[1])
            cuboidSet.add(new Cuboid(currentRange[1] + 1, otherRange[1], other.yMin, other.yMax, other.zMin, other.zMax));
        cuboidSet.add(new Cuboid(minRange[0], minRange[1], other.yMin, other.yMax, other.zMin, other.zMax));
        return cuboidSet;
    }

    private Set<Cuboid> splitViaY(Cuboid other) {
        if (!this.overlaps(other)) return Set.of(other);
        Set<Cuboid> cuboidSet = new HashSet<>();

        int[] currentRange = {yMin, yMax};
        int[] otherRange = {other.yMin, other.yMax};
        int[] minRange = {Math.max(yMin, other.yMin), Math.min(yMax, other.yMax)};

        if (otherRange[0] < currentRange[0])
            cuboidSet.add(new Cuboid(other.xMin, other.xMax, otherRange[0], currentRange[0] - 1, other.zMin, other.zMax));
        if (otherRange[1] > currentRange[1])
            cuboidSet.add(new Cuboid(other.xMin, other.xMax, currentRange[1] + 1, otherRange[1], other.zMin, other.zMax));
        cuboidSet.add(new Cuboid(other.xMin, other.xMax, minRange[0], minRange[1], other.zMin, other.zMax));
        return cuboidSet;
    }

    private Set<Cuboid> splitViaZ(Cuboid other) {
        if (!this.overlaps(other)) return Set.of(other);
        Set<Cuboid> cuboidSet = new HashSet<>();

        int[] currentRange = {zMin, zMax};
        int[] otherRange = {other.zMin, other.zMax};
        int[] minRange = {Math.max(zMin, other.zMin), Math.min(zMax, other.zMax)};

        if (otherRange[0] < currentRange[0])
            cuboidSet.add(new Cuboid(other.xMin, other.xMax, other.yMin, other.yMax, otherRange[0], currentRange[0] - 1));
        if (otherRange[1] > currentRange[1])
            cuboidSet.add(new Cuboid(other.xMin, other.xMax, other.yMin, other.yMax, currentRange[1] + 1, otherRange[1]));
        cuboidSet.add(new Cuboid(other.xMin, other.xMax, other.yMin, other.yMax, minRange[0], minRange[1]));
        return cuboidSet;
    }
}
