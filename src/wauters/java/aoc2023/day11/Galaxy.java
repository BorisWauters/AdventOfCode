package wauters.java.aoc2023.day11;

import java.util.Set;

public class Galaxy {
    private int row;
    private int col;
    private final int offset;

    public Galaxy(int row, int col, int offset) {
        this.row = row;
        this.col = col;
        this.offset = offset;
    }

    public void correction(Set<Integer> expandedRows, Set<Integer> expandedCols) {
        int newRow = this.row;
        int newCol = this.col;

        for (int i = 0; i < this.row; i++) {
            if (expandedRows.contains(i)) {
                newRow += this.offset - 1;
            }
        }

        for (int i = 0; i < this.col; i++) {
            if (expandedCols.contains(i)) {
                newCol += this.offset - 1;
            }
        }

        this.row = newRow;
        this.col = newCol;
    }

    public int getDistance(Galaxy other) {
        return Math.abs(this.row - other.row) + Math.abs(this.col - other.col);
    }
}
