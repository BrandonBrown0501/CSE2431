/**
 * 
 * CSE 2431 Lab 2
 * Mike Gargasz & Brandon Brown
 *
 **/
import java.util.Vector;
import java.lang.Integer;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SudokuValidator {
    private static final int THREAD_COUNT = 27;
    private static final int ROW_COLUMN_LENGTH = 9;
    private static final int VALID_SUM = 45;
    private static final int SQUARE_LENGTH = 3;

    public static void main(String[] args) {
        Vector<Vector<Integer>> puzzle = new Vector<>();
        Scanner scanner = new Scanner(System.in);
        Vector<Integer> errors = new Vector<>();

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
            executor.execute(new Rows(i, puzzle, errors));
            executor.execute(new Columns(i, puzzle, errors));
            executor.execute(new Squares(i, puzzle, errors));
        }
        executor.shutdown();

        if(errors.size() == 0) {
            System.out.println("This solution is correct!");
        } else {
            System.out.println("This solution is not correct and there are " + errors.size() + " errors. Please review the above messages for insight.");
        }
    }

    // The task for checking the rows of numbers
    public static class Rows implements Runnable {
        private Vector<Vector<Integer>> sudoku; // The puzzle being tested
        private int row; // The row being tested
        private Vector<Integer> errors; // Any errors caught
        private Rows(int rowNumber, Vector<Vector<Integer>> puzzle, Vector<Integer> numErrors) {
            sudoku = puzzle;
            row = rowNumber;
            errors = numErrors;
        }
        @Override
        public void run() {
            int sum = 0;
            for (int i = 0; i < ROW_COLUMN_LENGTH; i++) {
                sum += sudoku.get(row).get(i);
            }
            if (sum != VALID_SUM) {
                errors.add(1); // count error
                System.out.println("Invalid Solution @ row " + (row + 1) + ".");
            }
        }
    }

    // The task for checking a column of numbers
    public static class Columns implements Runnable {
        private Vector<Vector<Integer>> sudoku; // The puzzle being tested
        private int column; // The column being tested
        private Vector<Integer> errors; // Any errors caught
        private Columns(int columnNumber, Vector<Vector<Integer>> puzzle, Vector<Integer> numErrors) {
            sudoku = puzzle;
            column = columnNumber;
            errors = numErrors;
        }
        @Override
        public void run() {
            int sum = 0;
            for (int i = 0; i < ROW_COLUMN_LENGTH; i++) {
                sum += sudoku.get(i).get(column);
            }
            if (sum != VALID_SUM) {
                errors.add(1); // count error
                System.out.println("Invalid Solution @ column " + (column + 1) + ".");
            }
        }
    }

    // The task for checking the first
    public static class Squares implements Runnable {
        private Vector<Vector<Integer>> sudoku; // The puzzle being tested
        private int square; // The square being tested
        private Vector<Integer> errors; // Any errors caught
        public Squares(int squareNumber, Vector<Vector<Integer>> puzzle, Vector<Integer> numErrors) {
            sudoku = puzzle;
            square = squareNumber;
            errors = numErrors;
        }
        @Override
        public void run() {
        int sum = 0;
            for (int c = (square % 3)*3; c < (square % 3)*3 + SQUARE_LENGTH; c++) {
                for (int r = (square % 3)*3; r < (square % 3)*3 + SQUARE_LENGTH; r++) {
                    sum += sudoku.get(r).get(c);
                }
            }
            if (sum != VALID_SUM) {
                errors.add(1); // count error
                System.out.println("Invalid Solution @ square " + (square + 1) + ".");
            }
        }
    }
}




