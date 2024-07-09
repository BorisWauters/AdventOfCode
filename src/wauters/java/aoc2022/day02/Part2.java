package wauters.java.aoc2022.day02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Part2 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/aoc2022/day02.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        int score = 0;
        for (String line : lines) {
            score += computeScore(line.charAt(0), line.charAt(2));
        }

        System.out.println(score);
    }

    private static int computeScore(char other, char res) {
        Sign otherSign = Sign.fromChar(other);
        Result result = Result.fromChar(res);

        Sign ownSign = otherSign.getResponse(result);

        return ownSign.getPoints() + result.points;
    }

    enum Sign {
        ROCK('A'),
        PAPER('B'),
        SCISSORS('C');
    
        private final int representation;
    
        Sign(char rep) {
            this.representation = rep;
        }
    
        public int beats(Sign other) {
            int result = this.ordinal() - other.ordinal();
            if (result == 0)
                return 0;
            if (Math.abs(result) == 1)
                return result;
            return -result;
        }
    
        public int getPoints() {
            return this.ordinal() + 1;
        }
    
        public Sign getResponse(Result result) {
            int ordinal = 0;
            switch (result) {
                case WIN:
                    ordinal = this.ordinal() + 1;
                    break;
                case DRAW:
                    ordinal = this.ordinal();
                    break;
                case LOSE:
                    ordinal = this.ordinal() - 1;
                    break;
            }
    
            if (ordinal < 0)
                ordinal = 2;
            if (ordinal > 2)
                ordinal = 0;
            return Sign.values()[ordinal];
        }
    
        public static Sign fromChar(char c) {
            for (Sign sign : Sign.values()) {
                if (sign.representation == c)
                    return sign;
            }
            throw new RuntimeException("Illegal char");
        }
    }
    
    enum Result {
        WIN('Z', 6),
        DRAW('Y', 3),
        LOSE('X', 0);
    
        private final int representation;
        public final int points;
    
        Result(int rep, int points) {
            this.representation = rep;
            this.points = points;
        }
    
        public static Result fromChar(char c) {
            for (Result result : Result.values()) {
                if (result.representation == c)
                    return result;
            }
            throw new RuntimeException("Illegal char");
        }
    }
}
