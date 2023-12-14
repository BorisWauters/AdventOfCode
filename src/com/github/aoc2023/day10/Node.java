package com.github.aoc2023.day10;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private Pipe pipe;

    private int i;
    private int j;

    private final List<Node> neighbours = new ArrayList<>();
    private int distance = -1;

    public Node(char pipe) {
        this.pipe = Pipe.fromChar(pipe);
        if (this.pipe == Pipe.START) {
            this.distance = 0;
        }
    }

    public Node(char pipe, int i, int j) {
        this.pipe = Pipe.fromChar(pipe);
        this.i = i;
        this.j = j;
        if (this.pipe == Pipe.START) {
            this.distance = 0;
        }
    }

    public Pipe getPipe() {
        return this.pipe;
    }

    public void setPipe(Pipe pipe) {
        this.pipe = pipe;
    }

    public int getI() {
        return this.i;
    }

    public int getJ() {
        return this.j;
    }

    public List<Node> getNeighbours() {
        return this.neighbours;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void linkBottom(Node node) {
        if (this.pipe.pointsSouth() && node.pipe.pointsNorth()) {
            this.neighbours.add(node);
            node.neighbours.add(this);
        }
    }

    public void linkRight(Node node) {
        if (this.pipe.pointsEast() && node.pipe.pointsWest()) {
            this.neighbours.add(node);
            node.neighbours.add(this);
        }
    }
}
