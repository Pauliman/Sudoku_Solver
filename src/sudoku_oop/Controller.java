package sudoku_oop;

/**
 * This is the controller class that prepares the Sudoku grid and contains the
 * logic for solving it. Much of the condition checking logic is done by each individual cell.
 * 
 * @author Pauliman
 *
 */
public class Controller {

	private static Cell[] grid;
	private static int[] grid_values;
	private static int count;

	public static void main(String[] args) {

		grid = new Cell[81]; 					// Array to hold all Cell objects representing the Sudoku grid.
		grid_values = new int[81]; 				// Array to hold the values to be inserted into the grid. A values will be set to zero at instantiation time.
		count = 0;								// Recursion counter
		for (int i = 0; i < grid.length; i++) { // Fills the grid with Cell objects.
			grid[i] = new Cell();
		}
		readNumbers(); 							// Reads the fixed values into the cells of the array.		
		
		for (int i = 0; i < grid.length; i++) { // If a fixed value is encountered a flag is set in this cell say that this value is immutable.
			grid[i].setCell_value(grid_values[i]);
			if (grid_values[i] != 0)
				grid[i].setSetValue(true);
		}
		update(); 								// Writes the current array of cells into each cell as instance variable to enable row, column and quadrant checks.
		System.out.println("Testrun before calculation.");
		testrun();								 // Prints the zeroed grid plus immutable values.
		calculate(0, 1); 						// Call of the method that does the matching using back tracking algorithm.
		System.out.println("Could not find the right values.");
		testrun(); 								// This time testrun() shows the result of the matching process.
		System.out.println("\nNumber of recursions: " + count);
	} // end of main()
	

	private static void update() { // Writes the grid into every cell object
		for (Cell cell : grid) {
			cell.updateGrid(grid);
		}
	} // end of update()

	private static void testrun() {
		for (Cell x : grid) {
			if (x.getCELL_INDEX() % 9 == 0)
				System.out.println();
			System.out.print(x.getCell_value() + " ");
		}	
		System.out.println("\n");
	} // end of testrun()

	private static void readNumbers() { // Sets the fixed numbers as given in your Sudoku riddle.
		grid_values[7] = 4;
		grid_values[8] = 7;
		grid_values[14] = 3;
		grid_values[17] = 8;
		grid_values[19] = 9;
		grid_values[23] = 6;
		grid_values[28] = 6;
		grid_values[29] = 4;
		grid_values[31] = 8;
		grid_values[34] = 5;
		grid_values[38] = 5;
		grid_values[42] = 7;
		grid_values[43] = 9;
		grid_values[49] = 6;
		grid_values[50] = 2;
		grid_values[54] = 1;
		grid_values[57] = 8;
		grid_values[63] = 4;
		grid_values[65] = 2;
		grid_values[66] = 1;
		grid_values[78] = 5;
		grid_values[80] = 4;
	} // end of readNumbers()

	// ++++++++ End of board preparation / Start of logic ++++++++++++++++++++++++++

	private static void calculate(int index, int value) {							//index = the cell index (0-80) , value = cell value (1-9) 			
		count++;																	
		if (index >= 0 && index <= 80) { 											// Checks if indexes are plausible
			if (grid[index].isSetValue()) { 										// Checks if current cell contains immutable value.
				index = index + 1;
				value = 1;
			} else {																// if cell is not immutable, try to find the right value
				while (value <= 9) { 												// if value is inside range, do the check
					grid[index].setCell_value(value);								// set cell_value to value for testing purposes
					if (grid[index].isUniqueInRow() && grid[index].isUniqueInColumn() && grid[index].isUniqueInQuadrant()) { // Check if value of cell is unique in row, column and quadrant
						calculate(index + 1, 1); 									// if value was correct, check the next cell in the grid, starting with value 1
					}								
					value = value + 1;												// if value was not correct, check the same cell again using the current value + 1
				} // end of while()				
				grid[index].setCell_value(0);										// if all values have been tested unsuccessfully, set the current cell back to 0
				return;																// close this method cycle and return to the calling method
			}
		} else {																	// if the program enters this section the cell index is beyond the grid
			if (index > 80) {														// if the index is larger than 80 the Sudoku was solved successfully
				System.out.println("\nDone"); // If index > 80 Sudoku must be solved
				testrun();
				System.out.println("Number of recursions: " + count);
				System.exit(0);				
			} 
		}
		calculate(index, value); // Recursive call for all situations that are not a 'match'. May have different
									// argument values.
	} // end of calculate()
} // end of class()
