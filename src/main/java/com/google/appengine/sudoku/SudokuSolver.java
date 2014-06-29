package com.google.appengine.sudoku;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SudokuSolver
{
  private static final Logger LOG = Logger.getLogger(
      SudokuSolver.class.getName());

  // Solves the sudoku given by the 9x9 array 'numbers'.
  // Returns true if the sudoku was solved (and the entire solution is
  // stored in 'numbers'), false if it was unsolvable.
  // We have the convention that if a field is not known, 0 is stored.
  public static boolean solveSudoku(int[][] numbers) {
    LOG.info("solveSudoku: " + getString(numbers));

    // Solve the sudoku...
    
    return true;
  }

  private static String getString(int[][] numbers) {
    String result = "";
    for (int i = 0; i < 9; ++i) {
      for (int j = 0; j < 9; ++j) {
        result += numbers[i][j];
        if (i < 8 || j < 8) {  // No comma after last number.
          result += ",";
        }
      }
    }
    return result;
  }
}