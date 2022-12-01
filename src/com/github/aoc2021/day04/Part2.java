package com.github.aoc2021.day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Part2 {

    public static void main(String[] args) {
        try (Stream<String> stream = Files.lines(Paths.get("input/day04.txt"))) {
            solve(stream.toArray(String[]::new));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void solve(String[] lines) {
        //first line are all number to be called
        int[] numbers = Arrays.stream(lines[0].split(",")).mapToInt(Integer::parseInt).toArray();
        int numberOfBoards = (lines.length - 1) / 6;

        List<int[][]> boards = new ArrayList<>();
        List<boolean[][]> statusOfBoards = new ArrayList<>();

        //get all 5x5 boards
        for (int i = 0; i < numberOfBoards; i++) {
            boards.add(createBoard(lines, i));
            statusOfBoards.add(new boolean[5][5]);
        }

        //solve boards
        for (int number : numbers) {
            List<Integer> boardsToRemove = new ArrayList<>();
            for (int i = 0; i < boards.size(); i++) {
                int[][] board = boards.get(i);
                int[] indicesOfNumber = boardHasNumber(board, number);
                if (indicesOfNumber.length < 2) {
                    continue;
                }

                boolean[][] boardStatus = statusOfBoards.get(i);
                boardStatus[indicesOfNumber[0]][indicesOfNumber[1]] = true;
                if (!boardHasBingo(boardStatus)) continue;

                //Winning board found -> remove boards until one remains
                if (boards.size() > 1) {
                    boardsToRemove.add(i);
                    continue;
                }
                int boardScore = calculateBoardScore(board, boardStatus);
                System.out.println(boardScore * number);
                return;
            }
            boardsToRemove.sort(Collections.reverseOrder());
            for(int board : boardsToRemove) {
                boards.remove(board);
                statusOfBoards.remove(board);
            }
        }
    }

    private static int[][] createBoard(String[] lines, int i) {
        int[][] board = new int[5][5];
        for (int j = 1; j < 6; j++) {
            int index = i * 6 + j + 1;
            String line = lines[index].trim();
            int[] lineNumbers = Arrays.stream(line.split("\\s+")).mapToInt(Integer::parseInt).toArray();
            board[j-1] = lineNumbers;
        }
        return board;
    }

    private static int[] boardHasNumber(int[][] board, int number) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == number)
                    return new int[]{i, j};
            }
        }
        return new int[0];
    }

    private static boolean boardHasBingo(boolean[][] board) {
        for (int i = 0; i < 5; i++) {
            int countH = 0;
            int countV = 0;
            for (int j = 0; j < 5; j++) {
                if (board[i][j])
                    countV++;
                if (board[j][i])
                    countH++;
                if (countH <= j && countV <= j)
                    break;
            }
            if (countH == 5 || countV == 5)
                return true;
        }
        return false;
    }

    private static int calculateBoardScore(int[][] board, boolean[][] boardStatus) {
        int sum = 0;
        for (int i = 0; i <5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!boardStatus[i][j])
                    sum += board[i][j];
            }
        }
        return sum;
    }
}
