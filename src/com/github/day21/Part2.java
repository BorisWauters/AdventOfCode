package com.github.day21;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Part2 {

    // key = sum of three dice rolls, value = amount that sum was thrown
    private static final Map<Integer, Integer> dieValues = Map.of(
            3, 1,
            4, 3,
            5, 6,
            6, 7,
            7, 6,
            8, 3,
            9, 1
    );

    private static final long[] numberOfWins = {0, 0};

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/day21.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        int[] playerPosition = new int[2];
        int[] playerScore = {0, 0};
        Map<Game, Long> games = new HashMap<>();

        for (int i = 0; i < lines.length; i++)
            playerPosition[i] = Integer.parseInt(lines[i].replaceAll("\\D+\\d\\D+", "")) - 1;

        games.put(new Game(playerPosition, playerScore), 1L);

        int currentPlayer = 0;
        while (!games.isEmpty()) {
            Map<Game, Long> nextMapOfGames = new HashMap<>();
            for (var gameEntry : games.entrySet()) {
                Map<Game, Long> newUniverseGames = playGameRound(gameEntry.getKey(), currentPlayer, gameEntry.getValue());
                for (var newUniverseGameEntry : newUniverseGames.entrySet()) {
                    nextMapOfGames.putIfAbsent(newUniverseGameEntry.getKey(), 0L);
                    nextMapOfGames.compute(newUniverseGameEntry.getKey(), (k, v) -> v += newUniverseGameEntry.getValue());
                }
            }
            games = nextMapOfGames;

            currentPlayer++;
            currentPlayer %= 2;
        }

        System.out.println(Arrays.stream(numberOfWins).max().orElseThrow());
    }

    private static Map<Game, Long> playGameRound(Game game, int currentPlayer, long amountOfUniverses) {
        Map<Game, Long> gameMap = new HashMap<>();

        for (var dieEntry : dieValues.entrySet()) {
            Game currentGame = new Game(game);

            int newPosition = adjustPosition(currentGame.playerPosition[currentPlayer], dieEntry.getKey());

            currentGame.playerPosition[currentPlayer] = newPosition;
            currentGame.playerScore[currentPlayer] += newPosition + 1;

            if (currentGame.playerScore[currentPlayer] >= 21)
                numberOfWins[currentPlayer] += dieEntry.getValue() * amountOfUniverses;
            else {
                gameMap.putIfAbsent(currentGame, 0L);
                gameMap.compute(currentGame, (k, v) -> v += (dieEntry.getValue() * amountOfUniverses));
            }
        }
        return gameMap;
    }

    private static int adjustPosition(int currentPosition, int dieValue) {
        return (currentPosition + dieValue) % 10;
    }
}

class Game {
    int[] playerPosition;
    int[] playerScore;

    public Game(int[] playerPosition, int[] playerScore) {
        this.playerPosition = playerPosition;
        this.playerScore = playerScore;
    }

    public Game(Game game) {
        playerPosition = game.playerPosition.clone();
        playerScore = game.playerScore.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Arrays.equals(playerPosition, game.playerPosition) && Arrays.equals(playerScore, game.playerScore);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(playerPosition);
        result = 31 * result + Arrays.hashCode(playerScore);
        return result;
    }
}
