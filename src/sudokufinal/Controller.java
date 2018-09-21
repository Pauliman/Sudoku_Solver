package sudokufinal;

import javax.swing.*;



public class Controller {
	
	private static Matrix m;   // The GUI-class
	
	private static Cell[] grid;  // The array holding all cell objects
	
	private static int[] grid_values;	// The array storing the values 	
	
	private static int count;		// Recursion counter

	public static void main(String[] args) {									
		init();	
		fillGrid();
	} // end of main()
	
	
	private static void init() {
		m = new Matrix("SUDOKU");							// Initializes the GUI 
		SwingUtilities.invokeLater(() -> m.start()); 		// Starts the GUI on the EventDispatch-Thread
		count = 0;
		grid = new Cell[81];
		grid_values = new int[81]; 		 
	} // end of init()
	
	// Fills the grid array with self configuring Cell objects.
	private static void fillGrid() {
		for (int i = 0; i < grid.length; i++) { 
			grid[i] = new Cell();				
		}
	} // end of fillGrid()
	
	// Receives an array of user values and writes them to the local array of values
	public static void readNumbers(int[] values) { 
		for (int i = 0; i < 81; i++) {
			if(values[i] != 0)
				grid_values[i] = values[i];
		}
		flagCells();
	} // end of readNumbers()
	
	// Sets the flags in all cells. If a fixed value (user value) is encountered a flag is set in this cell saying that this value is immutable.
	private static void flagCells() {
		for (int i = 0; i < grid.length; i++) { 
			grid[i].setCell_value(grid_values[i]);
			if (grid_values[i] != 0)
				grid[i].setSetValue(true);
			else
				grid[i].setSetValue(false); // To reuse the flags old flags need to be reset.
		}
		update();
	} // end of flagCells()
	
	// Writes the grid into every cell object
	private static void update() { 
		for (Cell cell : grid) {
			cell.updateGrid(grid);
		}
	} // end of update()
	
	/* Uncomment for debugging purposes
	public static void testrun() {
		for (Cell x : grid) {
			if (x.getCELL_INDEX() % 9 == 0)
				System.out.println();
			System.out.print(x.getCell_value() + " ");
		}
		System.out.println("\n");
	} // end of testrun() */
	
	// Starts the recursive process and breaks out via Exception when either 81 values have been set or 100000 recursions were not enough to solve it.
	public static String startSolver() {
		String a = "";
		System.out.println("StartSolver is EventDispatch Thread: " + SwingUtilities.isEventDispatchThread());
		try {
		calculate(0, 1);
		} catch(Exception e) {
			a = e.getMessage();
		}
		//testrun();  Uncomment for debugging purposes.
		return a;
	} // end of startSolver()
	
	public static void reset() {
		grid_values = new int[81];
		count = 0;
	} // end of reset()
	
	
	public static void calculate(int index, int value) throws Exception{ 				// index = the cell index (0-80) , value = cell value (1-9)
		if(count > 100000)
			throw new Exception("No Solution");
		count++;
		if (index >= 0 && index <= 80) { 												// Checks if indexes are plausible
			if (grid[index].isSetValue()) {												// Checks if current cell contains immutable value.
				index = index + 1;
				value = 1;
			} else { 																	// if cell is not immutable, try to find the right value
				while (value <= 9) { 													// if value is inside range, do the check
					grid[index].setCell_value(value); 									// set cell_value to value for testing purposes
					if (grid[index].isUniqueInRow() && grid[index].isUniqueInColumn()
							&& grid[index].isUniqueInQuadrant()) { 						// Check if value of cell is unique in row, column and quadrant						
						m.setFields(index,value);						
						calculate(index + 1, 1); 										// if value was correct, check the next cell in the grid, startin with value 1
					}
					value = value + 1; 													// if value was not correct, check the same cell again using the current value + 1
				} // end of while()
				grid[index].setCell_value(0); 											// if all values have been tested unsuccessfully, set the current cell back to 0				
				return; 																// close this method cycle and return to the calling method
			}
		} else { 																		// if the program enters this section the cell index is beyond the grid
			if (index > 80) { 															// if the index is larger than 80 the Sudoku was solved successfully. If index > 80 Sudoku must be solved				
				throw new Exception("Done");
				}
		}
		calculate(index, value); 														// Recursive call for all situations that are not a 'match'. May have different argument values.
	} // end of calculate()
} // end of class
