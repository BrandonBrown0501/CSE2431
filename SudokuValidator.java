/**
 *
 *
 *
 *
 *
 * 
 *
 **/
package Sudoku;

import java.util.Vector;
import java.lang.Integer;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SudokuValidator {
    private static final int THREAD_COUNT = 9;
    private static final int ROW_COLUMN_LENGTH = 9;
    private static final int VALID_SUM = 45;
    private static final int SQUARE_LENGTH = 3;



    public static void main(String[] args) {
        Vector<Vector<Integer>> puzzle = new Vector<>();
        Scanner scanner = new Scanner(System.in);

        //read in puzzle
        for (int i = 0; i < ROW_COLUMN_LENGTH; i++) {
            Vector<Integer> newColumn = new Vector<>();
            int input;
            System.out.println("Please enter the 9 integers for column " + (i + 1) + ".");
            input = scanner.nextInt();
            do {
                newColumn.add(input % 10);
                input /= 10;
            } while (input > 0);
            puzzle.add(newColumn);
        }

        //create thread pool
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        for(int i = 0; i < ROW_COLUMN_LENGTH; i++) {
            executor.execute(new Rows(i, puzzle));
            executor.execute(new Columns(i, puzzle));
            executor.execute(new Squares(i, puzzle));
        }

        executor.shutdown();
    }

    // The task for checking the rows of numbers
    public static class Rows implements Runnable {
        private Vector<Vector<Integer>> sudoku; // The puzzle being tested
        private int row; // The square being tested
        private Rows(int rowNumber, Vector<Vector<Integer>> puzzle) {
            sudoku = puzzle;
            row = rowNumber;
        }

        @Override
        /** Override the run() method to tell the system
         *  what the task to perform
         */
        public void run() {
            int sum = 0;
            for (int i = 0; i < ROW_COLUMN_LENGTH; i++) {
                sum += sudoku.get(row).get(i);
            }
            if (sum != VALID_SUM) {
                System.out.println("Invalid Solution @ row " + (row + 1) + ".");
            }
        }
    }

    // The task for checking a column of numbers
    public static class Columns implements Runnable {
        private Vector<Vector<Integer>> sudoku; // The puzzle being tested
        private int column; // The column being tested

        private Columns(int columnNumber, Vector<Vector<Integer>> puzzle) {
            sudoku = puzzle;
            column = columnNumber;
        }

        @Override
        /** Override the run() method to tell the system
         *  what the task to perform
         */
        public void run() {
            int sum = 0;
            for (int i = 0; i < ROW_COLUMN_LENGTH; i++) {
                sum += sudoku.get(i).get(column);
            }
            if (sum != VALID_SUM) {
                System.out.println("Invalid Solution @ column " + (column + 1) + ".");
            }
        }
    }

    // The task for checking the first
    public static class Squares implements Runnable {
        private Vector<Vector<Integer>> sudoku; // The puzzle being tested
        private int square; // The square being tested
        public Squares(int squareNumber, Vector<Vector<Integer>> puzzle) {
            sudoku = puzzle;
            square = squareNumber;
        }

        @Override
        /** Override the run() method to tell the system
         *  what the task to perform
         */
        public void run() {
        int sum = 0;
            for (int c = (square % 3)*3; c < (square % 3)*3 + SQUARE_LENGTH; c++) {
                for (int r = (square % 3)*3; r < (square % 3)*3 + SQUARE_LENGTH; r++) {
                    sum += sudoku.get(r).get(c);
                }
            }
            if (sum != VALID_SUM) {
                System.out.println("Invalid Solution @ square " + (square + 1) + ".");
            }
        }
    }
}




