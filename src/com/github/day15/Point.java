package com.github.day15;

public class Point {
    public final int i;
    public final int j;
    public final int risk;

    private int totalRisk;

    public Point(int i, int j, int risk) {
        this.i = i;
        this.j = j;
        this.risk = risk;
        totalRisk = Integer.MAX_VALUE;
    }

    public int compareTo(Point point) {
        return Integer.compare(totalRisk, point.totalRisk);
    }

    public int getTotalRisk() {
        return totalRisk;
    }

    public void setTotalRisk(int totalRisk) {
        this.totalRisk = totalRisk;
    }
}
