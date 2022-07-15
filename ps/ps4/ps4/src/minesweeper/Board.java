/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import java.util.Optional;
import java.util.Random;

public class Board {

    // Abstraction function:
    //   if a cell is untouched, it can be flagged or open.
    //   if a cell is flagged, it can be deflagged.
    //   if a cell is being opening, the game will end iff the cell is a mine;
    //                               the board will open all non-mined cells neighboring it until meet a non-non-mined cell.
    //      (a cell is non-mined iff there is no mine in its neighbors)
    //   if ONLY every mine is flagged and there is no untouched cell, you win.
    // Rep invariant:
    //   a 2D board is filled with four marks
    //     "-": untouched
    //     " ": a non-mined opened cell
    //     "f": flagged
    //     "#": represents there is # mines in its neighbors
    // Rep exposure:
    //   provide non-mutated method to public, the class is safe from rep exposure.
    // Thread safety:

    private static final char UNTOUCHED = '-';
    private static final char FLAGGED = 'F';
    private static final char TOUCHED = ' ';

    // board: ySize * xSize cells
    private final int sizeX;
    private final int sizeY;
    private final int mineNum;

    private final char[][] board; // sent to client, update when changed
    private final boolean[][] mineBoard; // is there a bomb
    private final boolean[][] untouchedBoard; // "-" or " ", true <=> "-"
    private final boolean[][] flaggedBoard; // "f"
    private final int[][] mineNumBoard; // "#"

    private void checkRep() {
        assert sizeX > 0;
        assert sizeY > 0;
        assert mineNum > 0 && mineNum <= sizeX * sizeY;
    }

    /**
     * Random board constructor.
     */
    public Board(int sizeX, int sizeY, Optional<Integer> mineNum) {
        if (sizeX <= 0 || sizeY <= 0) {
            throw new IllegalArgumentException("Init board with negative size.");
        }
        if (mineNum.orElse(1) <= 0 || mineNum.orElse(1) >= sizeX * sizeY) {
            throw new IllegalArgumentException("Init board with negative mineNum.");
        }

        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.mineNum = mineNum.orElse(sizeX * sizeY / 4);

        board = new char[sizeX][sizeY];
        mineBoard = new boolean[sizeX][sizeY];
        untouchedBoard = new boolean[sizeX][sizeY];
        flaggedBoard = new boolean[sizeX][sizeY];
        mineNumBoard = new int[sizeX][sizeY];

        initRandomBoard();
        calculateBoard();
        updateBoard();

        checkRep();
    }

    /**
     * Only called by the Constructor method <code>Board()</code> to initialize
     * a random board; init <code>untouchedBoard</code>, <code>flaggedBoard</code>
     * & <code>mineBoard</code>.
     */
    private void initRandomBoard() {
        checkRep();
        // untouchedBoard: every cell is untouched
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                untouchedBoard[i][j] = true;
            }
        }
        // flaggedBoard: every cell is unflagged
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                flaggedBoard[i][j] = false;
            }
        }
        // mineBoard
        int fillMine = 0;
        while (fillMine < mineNum) {
            int randomNum = new Random().nextInt(sizeX * sizeY);
            int y = randomNum / sizeX;
            int x = (randomNum - sizeX * y) % sizeX;
            if (mineBoard[x][y]) {
                continue;
            }
            mineBoard[x][y] = true;
            fillMine++;
        }
        checkRep();
    }

    /**
     * Calculate the number of mines in the 8 neighbors of a cell, and store it
     * in <code>mineNumBoard</code>.
     */
    private void calculateBoard() {
        checkRep();
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if (mineBoard[i][j]) {
                    mineNumBoard[i][j] = -1; // bomb
                    continue;
                }
                mineNumBoard[i][j] = 0;
                for (int ii = i - 1; ii <= i + 1; ii++) {
                    for (int jj = j - 1; jj <= j + 1; jj++) {
                        if (!validateCoordinate(ii, jj) || (ii == i && jj == j)) {
                            continue;
                        }
                        mineNumBoard[i][j] += mineBoard[ii][jj] ? 1 : 0;
                    }
                }
            }
        }
        checkRep();
    }

    /**
     * Update <code>board</code> according to other 2D arrays.
     */
    private void updateBoard() {
        checkRep();
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if (flaggedBoard[i][j]) {
                    board[i][j] = FLAGGED;
                } else if (untouchedBoard[i][j]) {
                    board[i][j] = UNTOUCHED;
                } else if (mineNumBoard[i][j] > 0) {
                    board[i][j] = (char) (mineNumBoard[i][j] + '0');
                } else {
                    // a BOMB or a non-mined cell
                    board[i][j] = TOUCHED;
                }
            }
        }
        checkRep();
    }

    /**
     * Dig a cell:
     * * cell is untouched, then open it;
     * * cell is a mine, then game over;
     * * otherwise, do not change anything.
     *
     * @return true if the game is over, false otherwise.
     */
    public boolean dig(int x, int y) {
        checkRep();
        if (!validateCoordinate(x, y)) {
            // NOTE: should do nothing
            throw new IllegalArgumentException("Invalid coordinate.");
        }
        if (untouchedBoard[x][y]) {
            if (mineBoard[x][y]) {
                // game over
                return true;
            } else {
                // open the cell
                open(x, y);
                calculateBoard();
                updateBoard();
            }
        }
        checkRep();
        return false;
    }

    /**
     * Open a cell and its neighbors until meet a mine or a non-non-mined cell.
     */
    private void open(int x, int y) {
        if (!validateCoordinate(x, y)) {
            throw new IllegalArgumentException("Invalid coordinate.");
        }
        if (mineBoard[x][y]) {
            throw new IllegalArgumentException("Cannot open an mine cell in open function!");
        }

        untouchedBoard[x][y] = false;

        // meet a non-non-mined cell
        if (mineNumBoard[x][y] > 0) {
            return;
        }

        for (int i = y - 1; i < y + 1; i++) {
            for (int j = x - 1; j < x + 1; j++) {
                if (validateCoordinate(i, j) && untouchedBoard[i][j] && !mineBoard[i][j]) {
                    open(i, j);
                }
            }
        }
    }

    /**
     * Validate (x,y) coordinate.
     */
    private boolean validateCoordinate(int x, int y) {
        return x >= 0 && x < sizeX && y >= 0 && y < sizeY;
    }

    /**
     * Return the board in string format.
     */
    public String toString() {
        checkRep();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                sb.append(board[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * debug: print all information
     */
    private String debugPrintAllInfo() {
        checkRep();
        StringBuilder sb = new StringBuilder();
        sb.append("=====mineBoard=====\n");
        sb.append("mineNum: ").append(mineNum).append("\n");
        sb.append("mineNumBoard: \n");
        for (int i = 0; i < sizeX; i++) {
            sb.append("  ");
            for (int j = 0; j < sizeY; j++) {
                sb.append(mineNumBoard[i][j] < 0 ? "*" : mineNumBoard[i][j] + "");
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Board board = new Board(5, 4, Optional.empty());
        System.out.println(board.debugPrintAllInfo());
        System.out.println(board);
        board.dig(0, 0);
        System.out.println(board);
    }
}
