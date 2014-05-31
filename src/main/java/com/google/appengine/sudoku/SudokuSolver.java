package com.google.appengine.sudoku;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SudokuSolver
{
  private static final Logger LOG = Logger.getLogger(
      SudokuSolver.class.getName());

  // Checks whether there is no direct contradition to numbers[row][col]==candidate.
  private static boolean isCandidate(int[][] numbers, int row, int col, int candidate) {
    assert row >= 0 && row < 9 && col >= 0 && col < 9 && candidate >= 1 && candidate <= 9;
    for (int i = 0; i < 9; ++i) {
      if (i == row) {
        continue;
      }
      if (numbers[i][col] == candidate) {
        // LOG.info("(" + row + "," + col + "): " + candidate +
        //          " not possible (same row, i=" + i + ").");
        return false;
      }
    }
    for (int j = 0; j < 9; ++j) {
      if (j == col) {
        continue;
      }
      if (numbers[row][j] == candidate) {
        // LOG.info("(" + row + "," + col + "): " + candidate +
        //          " not possible (same col, j=" + j + ").");
        return false;
      }
    }
    int rstart = (row / 3) * 3;
    int cstart = (col / 3) * 3;
    assert row >= rstart && row <= rstart + 2 && col >= cstart && col <= cstart + 2;
    for (int i = rstart; i < rstart + 3; ++i) {
      for (int j = cstart; j < cstart + 3; ++j) {
        if (i == row && j == col) {
          continue;
        }
        if (numbers[i][j] == candidate) {
          // LOG.info("(" + row + "," + col + "): " + candidate +
          //          " not possible (same block, i=" + i + " j=" + j + ").");
          return false;
        }
      }
    }
    return true;
  }
  
  // Gets all the possible values for numbers[row][col].
  private static ArrayList<Integer> getCandidates(int[][] numbers, int row, int col) {
    ArrayList<Integer> result = new ArrayList<Integer>();
    for (int c = 1; c <= 9; ++c) {
      if (isCandidate(numbers, row, col, c)) {
        result.add(c);
      }
    }
    // LOG.info("(" + row + "," + col + ") has " + result.size() + " candidates.");
    return result;
  }
  
  // Overwrites 'output' with 'input' (both should be 9x9 matrices).
  private static void copy(int[][] input, int[][] output) {
    for (int i = 0; i < 9; ++i) {
      for (int j = 0; j < 9; ++j) {
        output[i][j] = input[i][j];
      }
    }
  }

  // Checks if all fields of 'numbers' are set.
  private static int numUnsolvedFields(int[][] numbers) {
    int result = 0;
    for (int i = 0; i < 9; ++i) {
      for (int j = 0; j < 9; ++j) {
        if (numbers[i][j] == 0) {
          ++result;
        }
      }
    }
    return result;
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
  
  // Solves the sudoku given by 'numbers'.
  // Returns true if the sudoku was solved (and the entire solution is
  // stored in 'numbers'), false if it was unsolvable.
  public static boolean solveSudoku(int[][] numbers) {
    LOG.info("solveSudoku: " + getString(numbers));
      
    if (numUnsolvedFields(numbers) == 0) {
      // There is nothing to do!
      LOG.info("Done! (2)");
      return true;
    }

    // Store a list of possible values of every field in 'candidates'.
    ArrayList<Integer>[][] candidates = (ArrayList<Integer>[][]) new ArrayList[9][9];
    for (int i = 0; i < 9; ++i) {
      for (int j = 0; j < 9; ++j) {
        candidates[i][j] = new ArrayList<Integer>();
      }
    }
    for (int row = 0; row < 9; ++row) {
      for (int col = 0; col < 9; ++col) {
        if (numbers[row][col] == 0) {
          candidates[row][col] = getCandidates(numbers, row, col);
          if (candidates[row][col].size() == 1) {
            // Only one candidate -- set that field in 'numbers'.
            numbers[row][col] = candidates[row][col].get(0);
          }
          if (candidates[row][col].size() == 0) {
            // It's impossible to solve the sudoku.
            LOG.info("Impossible to solve due to (" + row + "," + col + ") [1]");
            return false;
          }
        } else {
          candidates[row][col].add(numbers[row][col]);
        }
      }
    }

    if (numUnsolvedFields(numbers) == 0) {
      LOG.info("Done! (2)");
      return true;
    }
    LOG.info(numUnsolvedFields(numbers) + " fields remaining.");
    
    // There is no easy solution, we will set some field to one to one of
    // its candidates and see if it can be solved with that.
    
    // Create a copy of the input.
    int[][] numbers2 = new int[9][9];
    for (int i = 0; i < 9; ++i) {
      for (int j = 0; j < 9; ++j) {
        numbers2[i][j] = numbers[i][j];
      }
    }
    while (true) {
      // Pick a cell that has the least number of candidates.
      int row = -1, col = -1, min_num_candidates = 10;
      for (int i = 0; i < 9; ++i) {
        for (int j = 0; j < 9; ++j) {
          if (numbers2[i][j] == 0 && candidates[i][j].size() == 0) {
            // We've come to a dead end.
            // One of the higher-up choices must have been wrong.
            LOG.info("Impossible to solve due to (" + i + "," + j + ") [2]");
            return false;
          }
          if (numbers2[i][j] == 0 && candidates[i][j].size() < min_num_candidates) {
            min_num_candidates = candidates[i][j].size();
            row = i;
            col = j;
          }
        }
      }
      // Set numbers2[row][col] to the first candidate and see if we can solve it.
      numbers2[row][col] = candidates[row][col].get(0);
      LOG.info("Set (" + row + "," + col + ") to " + numbers2[row][col] +
               " (out of " + candidates[row][col].size() + " candidates).");
      
      boolean solved = solveSudoku(numbers2);  // Recursive call.
      if (solved) {
        // We got lucky. Transfer the entire solved sudoku back to the original result.
        // We can't just transfer numbers2[row][col] back because
        // several fields might have been solved at once.
        copy(numbers2, numbers);
        LOG.info("Success.");
        return true;
      } else {
        // Remove the candidate from the list, try again.
        candidates[row][col].remove(0);
        // We also have to reset 'numbers2' to 'numbers' since it was was altered
        // in the solveSudoku call.
        copy(numbers, numbers2);
        LOG.info("Try again.");
      }
    } 
  }
}