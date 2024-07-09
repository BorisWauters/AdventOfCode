package com.github.aoc2023.day10;

public enum Pipe {
    VERTICAL('|'),
    HORIZONTAL('-'),
    NORTH_EAST('L'),
    NORTH_WEST('J'),
    SOUTH_EAST('F'),
    SOUTH_WEST('7'),
    START('S'),
    GROUND('.');

    private final char c;

    Pipe(char c) {
        this.c = c;
    }

    public static Pipe fromChar(char c) {
        for (Pipe pipe : Pipe.values()) {
            if (pipe.c == c) {
                return pipe;
            }
        }
        return null;
    }

    public char getC() {
        return c;
    }

    public boolean isPipe() {
        return this != GROUND;
    }

    public boolean pointsNorth() {
        return this == START || this == VERTICAL || this == NORTH_EAST || this == NORTH_WEST;
    }

    public boolean pointsSouth() {
        return this == START || this == VERTICAL || this == SOUTH_EAST || this == SOUTH_WEST;
    }

    public boolean pointsEast() {
        return this == START || this == HORIZONTAL || this == NORTH_EAST || this == SOUTH_EAST;
    }

    public boolean pointsWest() {
        return this == START || this == HORIZONTAL || this == NORTH_WEST || this == SOUTH_WEST;
    }

    public boolean isLF() {
        return this == NORTH_EAST|| this == SOUTH_EAST;
    }

    public boolean isJ7() {
        return this == NORTH_WEST|| this == SOUTH_WEST;
    }
}
