package com.github.aoc2022.day16;

record Walkers(Walker a, Walker b) {
    public Walker best() {
        if (a.time() > b.time()) {
            return a;
        }
        return b;
    }

    public Walker other() {
        if (a.time() > b.time()) {
            return b;
        }
        return a;
    }
}
