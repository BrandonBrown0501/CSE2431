/** 
 * This class reads in the user inputted file path the sudoku table. 
 *
 * Created By: Brandon Brown, Mike Gargasz 
 * Created On: February 26, 2019 
 *
 **/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SudokuArray{ 

    private BufferedReader inputFile;			  // File reader
    int ROW_COL_SIZE = 9;				  // Row & Column Length 
    int[][] puzzle = new int[ROW_COL_SIZE][ROW_COL_SIZE]; // Sudoku Table 

    // Constructor
    public SudokuArray(String fileName){
	openFileToRead(fileName);
	parseSudokuTable();
	closeFile();
    }

    // Open user inputted file to read from
    private void openFileToRead(String fileName){
        try{
	    inputFile = new BufferedReader(new FileReader(fileName));
	}catch(IOException e){
	    e.printStackTrace();
	}
    }

    // Close user inputted file to read from 
    private void closeFile(){
	try{
	    inputFile.close();
	}catch(IOException e){
	    e.printStackTrace(); 
	}
    }
    
    // Read in sudoku table
    private void parseSudokuTable(){
	
        String line = ""; 
	for(int i = 0; i < ROW_COL_SIZE; i++){
            // Store values into array 
	    try{
	        line = inputFile.readLine();
	        String[] values = line.split(" ");
                for(int j = 0; j < values.length; j++){
		    puzzle[i][j] = Integer.parseInt(values[j]);
                }
	    }catch(IOException e){
		e.printStackTrace();
	    }
	}

    }
      
}
