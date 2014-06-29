package com.google.appengine.sudoku;

import com.google.appengine.sudoku.SudokuSolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A sudoku solver servlet.
 *
 */
public class SudokuServlet extends HttpServlet {

  // Stores the input numbers and then the solution.
  int[][] sudokuNumbers = new int[9][9];

  private static final long serialVersionUID = 1L;

  private static final String DEFAULT_SUDOKU_ONE =
      "0,0,0," + "0,0,0," + "5,4,0," +
      "9,0,0," + "8,0,7," + "3,0,0," +
      "3,8,0," + "4,0,1," + "0,0,0," +
   // ------------------------------ //
      "0,1,5," + "0,0,0," + "9,8,0," +
      "0,0,0," + "0,0,0," + "0,0,0," +
      "0,3,7," + "0,0,0," + "6,2,0," +
   // ------------------------------ //
      "0,0,0," + "1,0,9," + "0,5,6," +
      "0,0,3," + "2,0,8," + "0,0,4," +
      "0,6,9," + "0,0,0," + "0,0,0";

  private static final String DEFAULT_SUDOKU_TWO =
      "6,0,0," + "0,0,0," + "0,0,0," +
      "0,7,0," + "1,8,0," + "9,0,0," +
      "0,3,0," + "4,0,0," + "0,0,0," +
   // ------------------------------ //
      "0,0,4," + "0,0,0," + "0,0,3," +
      "3,6,0," + "0,0,0," + "4,0,0," +
      "0,0,8," + "0,0,0," + "0,0,1," +
   // ------------------------------ //
      "0,0,0," + "0,1,6," + "0,2,4," +
      "9,0,0," + "0,3,5," + "0,0,0," +
      "2,0,1," + "0,0,0," + "0,3,0";

  private static final Logger LOG = Logger.getLogger(
      SudokuServlet.class.getName());

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    if (req.getParameter("solve") != null) {
      LOG.info("Solving a sudoku...");
      // sudokuHandler sets the "values" attribute to the solution.
      sudokuHandler(req);
      // The "solve" attribute is no longer needed.
      req.removeAttribute("solve");
    } else {
      LOG.info("Displaying default sudoku.");
      // Display a default, unsolved sudoku.
      req.setAttribute("values_from_java", DEFAULT_SUDOKU_TWO);
    }
    // Forward the request (with "values" set) to display.jsp.
    req.getRequestDispatcher("display.jsp").forward(req, resp);
  }

  private static void parseString(String values, int[][] numbers) {
    // Parse the values. We expect 9x9=81 integers between 1 and 9.
    StringTokenizer tokenizer = new StringTokenizer(values, ",");
    int i = 0;  // Row index (0..8).
    int j = 0;  // Column index (0..8).
    while (tokenizer.hasMoreTokens()) {
      String value = tokenizer.nextToken();  // Advances the tokenizer.
      int v = 0;
      if (value.length() == 1) {  // Could be empty.
        v = Integer.parseInt(value);
      }       
      numbers[i][j] = v;
      ++j;
      if (j > 8) {
        // Next row.
        j = 0;
        ++i;
      }
    }
    if (i != 9 || j != 0) {
      LOG.log(Level.SEVERE, "Did not parse 81 numbers. i=" + i + " j=" + j);
    }
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

  /**
   * 1) Get the input numbers from the HTTP request.
   * 2) Solve the sudoku.
   * 3) Serialize the solution as a string.
   * 4) Set that string as an attribute "values" in the HTTP request.
   */
  private void sudokuHandler(HttpServletRequest req) {  
    // This method should only be called if there is a parameter
    // solve=1,2,,4,1,8,,,6,,,,,
    String values_from_user = req.getParameter("solve");
    assert values_from_user != null;
    parseString(values_from_user, sudokuNumbers);
    LOG.log(Level.INFO, "Called sudokuHandler with " + getString(sudokuNumbers));
    
    // Solve!
    if (SudokuSolver.solveSudoku(sudokuNumbers)) {
     LOG.info("Solution: " + getString(sudokuNumbers)); 
    } else {
      LOG.info("Could not solve the sudoku.");
    }
    
    // Transfer the solution to a comma-delimited string.
    // Set the "values_from_java" attribute for use in the .JSP template.
    req.setAttribute("values_from_java", getString(sudokuNumbers));
  }
}
