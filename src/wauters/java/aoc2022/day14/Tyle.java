package wauters.java.aoc2022.day14;

public enum Tyle {
    AIR, ROCK, SAND;

    public boolean isBlocked() {
        return this == ROCK || this == SAND;
    }

    @Override
    public String toString() {
        switch (this) {
            case AIR:
                return ".";
            case ROCK:
                return "#";
            case SAND:
                return "o";
        }

        throw new RuntimeException("Invalid input");
    }
}
