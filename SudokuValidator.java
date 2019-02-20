/**
 * This program validates that user given input file with sudoku puzzle 
 * is valid using 27 threads to check the row, columns, and 3x3 areas. 
 *
 * CSE 2431 TTH 2:20 - 3:40
 * Lab 2
 * Created By: Mike Gargasz & Brandon Brown
 * Created On: February 24, 2019
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

	// Check that user inputted file to parse puzzle from
	if(args.length < 1){
	    System.out.println("Error. No input file was specified.");
	    System.exit(1);
	}

	SudokuArray puzzleArray = new SudokuArray(args[0]); 		// Parse input file for puzzle
	int[][] puzzle = puzzleArray.getPuzzle();			// Get puzzle after parsing input file
        Vector<Integer> errors = new Vector<>();			// Errors that occurred when validating puzzle

        // Create thread pool
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

	// Check the sudoku puzzle
        for(int i = 0; i < ROW_COLUMN_LENGTH; i++) {
            executor.execute(new Rows(i, puzzle, errors));
            executor.execute(new Columns(i, puzzle, errors));
            executor.execute(new Squares(i, puzzle, errors));
        }

	// Close threads
        executor.shutdown();

        if(errors.size() == 0) {
            System.out.println("This solution is correct!");
        } else {
            System.out.println("This solution is not correct and there are " + errors.size() + " errors. Please review the above messages for insight.");
        }
    }

    // The task for checking the rows of numbers
    public static class Rows implements Runnable {

        private int[][] sudoku; 		// The puzzle being tested
        private int row; 			// The row being tested
        private Vector<Integer> errors; 	// Any errors caught

        private Rows(int rowNumber, int[][] puzzle, Vector<Integer> numErrors) {
            sudoku = puzzle;
            row = rowNumber;
            errors = numErrors;
        }

        @Override
        public void run() {
            int sum = 0;

            for (int i = 0; i < ROW_COLUMN_LENGTH; i++) {
                sum += sudoku[row][i];
            }

            if (sum != VALID_SUM) {
                errors.add(1); // count error
                System.out.println("Invalid Solution @ row " + (row + 1) + ".");
            }
        }
    }

    // The task for checking a column of numbers
    public static class Columns implements Runnable {

        private int[][] sudoku;		         // The puzzle being tested
        private int column; 			 // The column being tested
        private Vector<Integer> errors;		 // Any errors caught

        private Columns(int columnNumber, int[][] puzzle, Vector<Integer> numErrors) {
            sudoku = puzzle;
            column = columnNumber;
            errors = numErrors;
        }

        @Override
        public void run() {
            int sum = 0;

            for (int i = 0; i < ROW_COLUMN_LENGTH; i++) {
                sum += sudoku[i][column];
            }

            if (sum != VALID_SUM) {
                errors.add(1); // count error
                System.out.println("Invalid Solution @ column " + (column + 1) + ".");
            }
        }
    }

    // The task for checking the first
    public static class Squares implements Runnable {

        private int[][] sudoku; 		// The puzzle being tested
        private int square; 			// The square being tested
        private Vector<Integer> errors;	 	// Any errors caught

        public Squares(int squareNumber, int[][] puzzle, Vector<Integer> numErrors) {
            sudoku = puzzle;
            square = squareNumber;
            errors = numErrors;
        }

        @Override
        public void run() {
            int sum = 0;
 
            for (int c = (square % 3)*3; c < (square % 3)*3 + SQUARE_LENGTH; c++) {
                for (int r = (square % 3)*3; r < (square % 3)*3 + SQUARE_LENGTH; r++) {
		    sum += sudoku[c][r];
                }
            }

            if (sum != VALID_SUM) {
                errors.add(1); // count error
                System.out.println("Invalid Solution @ square " + (square + 1) + ".");
            }
        }
    }
}




