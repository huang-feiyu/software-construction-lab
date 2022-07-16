/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
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

    // board: sizeY * sizeX cells (NOTE: x,y is reversed; need a process before applying to public method)
    private final int sizeX;
    private final int sizeY;
    private int mineNum;

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
     * Build a board from a specific format file.
     *
     * @param file the file to read from
     */
    public Board(File file) throws IOException {
        // get lines
        List<String> lines = Files.readAllLines(file.toPath());

        // first line: col row
        if (!lines.get(0).matches("[0-9]+ [0-9]+")) {
            throw new RuntimeException("Wrong format");
        }
        String[] firstLine = lines.get(0).split(" ");
        sizeY = Integer.parseInt(firstLine[0]);
        sizeX = Integer.parseInt(firstLine[1]);

        board = new char[sizeX][sizeY];
        mineBoard = new boolean[sizeX][sizeY];
        untouchedBoard = new boolean[sizeX][sizeY];
        flaggedBoard = new boolean[sizeX][sizeY];
        mineNumBoard = new int[sizeX][sizeY];

        lines.remove(0);
        // check format
        if (sizeX != lines.size()) {
            throw new RuntimeException("The file is improperly formatted");
        }
        lines.forEach(line -> {
            String[] cells = line.split(" ");
            if (cells.length != sizeY) {
                throw new RuntimeException("The file is improperly formatted");
            }
        });

        // fill the board
        for (int x = 0; x < lines.size(); x++) {
            String[] cells = lines.get(x).split(" ");

            for (int y = 0; y < cells.length; y++) {
                if (cells[y].equals("1")) {
                    mineBoard[x][y] = true;
                    mineNum++;
                }
            }
        }

        updateBoard();
        checkRep();
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
     * Update <code>board</code> according to other 2D arrays.
     */
    private void updateBoard() {
        checkRep();
        calculateBoard();
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
     * Dig a cell:
     * * cell is untouched, then open it (and its neighbors);
     * * cell is a mine, then remove the mine, game over;
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
                mineNum--;
                mineBoard[x][y] = false;
                updateBoard();
                // game over
                return true;
            } else {
                // open the cell
                open(x, y);
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

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (validateCoordinate(i, j) && untouchedBoard[i][j] && !mineBoard[i][j]) {
                    open(i, j);
                }
            }
        }
    }

    /**
     * Flag a cell:
     * * cell is untouched, then flag it;
     * * otherwise, do not change anything.
     */
    public void flag(int x, int y) {
        checkRep();
        if (!validateCoordinate(x, y)) {
            // NOTE: should do nothing
            throw new IllegalArgumentException("Invalid coordinate.");
        }
        if (untouchedBoard[x][y]) {
            flaggedBoard[x][y] = true;
            updateBoard();
        }
        checkRep();
    }

    /**
     * Deflag a cell:
     * * cell is flagged, then deflag it;
     * * otherwise, do not change anything.
     */
    public void deflag(int x, int y) {
        checkRep();
        if (!validateCoordinate(x, y)) {
            // NOTE: should do nothing
            throw new IllegalArgumentException("Invalid coordinate.");
        }
        if (flaggedBoard[x][y]) {
            flaggedBoard[x][y] = false;
            updateBoard();
        }
        checkRep();
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
        sb.append("sizeX: ").append(sizeX).append(" sizeY: ").append(sizeY).append("\n");
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

    public static void main(String[] args) throws IOException {
        Board board = new Board(5, 10, Optional.empty());
        System.out.println(board.debugPrintAllInfo());
        System.out.println(board);
        board.dig(3, 4);
        System.out.println(board);
        board.flag(2, 3);
        board.flag(3, 3);
        System.out.println(board);
        board.deflag(2, 3);
        System.out.println(board);
        System.out.println(board.debugPrintAllInfo());

        Board board1 = new Board(new File("./ps4/test/3x4Board.txt"));
        System.out.println(board1.debugPrintAllInfo());
    }
}
