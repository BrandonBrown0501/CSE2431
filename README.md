# CSE2431
# Lab 2 - Sudoku Validator using Threads 
# Created By: Brandon Brown, Mike Gargasz
# Created On: February 20, 2019

Description of Files
    DesignDecision.txt - The explaination on our solution/design choices 
    SudokuArray.java - Parses the inputted file to create 2D array of sudoku puzzle
    SudokuValidator.java - Validates the inputted file contains a valid sudoku puzzle by checking the rows, columns, and 3x3 matrices in the puzzle using 27 threads
    /test - folder to contain examples of valid and invalid sudoku puzzles

Input File Format (Each number in a row must be seperated by one space)
     Here is an example of a valid format for the puzzle: 
         6 2 4 5 3 9 1 8 7
         5 1 9 7 2 8 6 3 4
         8 3 7 6 1 4 2 9 5
         1 4 3 8 6 5 7 2 9 
         9 5 8 2 4 7 3 6 1
         7 6 2 3 9 1 4 5 8
         3 7 1 9 5 6 8 4 2
         4 9 6 1 8 2 5 7 3
         2 8 5 4 7 3 9 1 6

Compile Program 
    Compile the program by executing the Makefile by running the command: make

Execute Program 
    Run the command: java SudokuValidator <input-file> 
