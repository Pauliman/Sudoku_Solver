package sudoku_oop;

/**
 * This class represents a single cell of the 9x9 Sudoku grid.
 * It is self initializing using the different behavior of class and instance fields.
 * It provides all necessary functions to check whether its value is unique in a row, column or quadrant.
 * @author Pauliman
 *
 */
public class Cell {

	// +++++++++++++ INSTANCE FIELDS +++++++++++++++
	private int CELL_INDEX;								// Cell index or the position of the cell in the Sudoku grid.
	private int ROW_INDEX;								// Indicating which row the cell belongs to.
	private int COLUMN_INDEX;							// Indicates which column the cell belongs to.
	private int QUADRANT_INDEX;							// Indicates which Quadrant the cell belongs to.
	private int cell_value;								// The current value of the cell.
	private Cell[] grid;								// The current array of cells.
	private boolean setValue;							// Flag indicating if Cell has a set value or not.	
	
	// +++++++++++ Class Field +++++++++++++++++++++
	
	private static int INDEXER = 0;						//Increments with every new instance and provides the cell_index for each cell.
	
	// +++++++++++ Accessor & Mutator Methods ++++++++++++++
	
	public int getCell_value() {
		return this.cell_value;
	}

	public void setCell_value(int cell_value) {
		this.cell_value = cell_value;
				
	}

	public int getCELL_INDEX() {
		return this.CELL_INDEX;
	}

	public int getROW_INDEX() {
		return this.ROW_INDEX;
	}

	public int getCOLUMN_INDEX() {
		return this.COLUMN_INDEX;
	}

	public int getQUADRANT_INDEX() {
		return this.QUADRANT_INDEX;
	}
	
	public void setSetValue(boolean var) {
		this.setValue = var;
	}
	
	public boolean isSetValue() {
		return this.setValue;
	}		

	// +++++++++++++++++++++ Constructor ++++++++++++++++++++

	public Cell() {
		this.CELL_INDEX = INDEXER++;
		setAllIndexes();			
	} // end of constructor()

	private void setAllIndexes() {						// Derives all indexes from the cell_index mathematically. The whole grid instantiates itself.
		switch (CELL_INDEX % 9) {
		case 0:
			this.COLUMN_INDEX = 0;
			break;
		case 1:
			this.COLUMN_INDEX = 1;
			break;
		case 2:
			this.COLUMN_INDEX = 2;
			break;
		case 3:
			this.COLUMN_INDEX = 3;
			break;
		case 4:
			this.COLUMN_INDEX = 4;
			break;
		case 5:
			this.COLUMN_INDEX = 5;
			break;
		case 6:
			this.COLUMN_INDEX = 6;
			break;
		case 7:
			this.COLUMN_INDEX = 7;
			break;
		case 8:
			this.COLUMN_INDEX = 8;
			break;
		default: // do nothing
		} // end of switch
		if (CELL_INDEX > 71)
			this.ROW_INDEX = 8;
		else if (CELL_INDEX > 62)
			this.ROW_INDEX = 7;
		else if (CELL_INDEX > 53)
			this.ROW_INDEX = 6;
		else if (CELL_INDEX > 44)
			this.ROW_INDEX = 5;
		else if (CELL_INDEX > 35)
			this.ROW_INDEX = 4;
		else if (CELL_INDEX > 26)
			this.ROW_INDEX = 3;
		else if (CELL_INDEX > 17)
			this.ROW_INDEX = 2;
		else if (CELL_INDEX > 8)
			this.ROW_INDEX = 1;
		else
			this.ROW_INDEX = 0;

		switch ((int) COLUMN_INDEX / 3) {
		case 0: {
			switch ((int) ROW_INDEX / 3) {
			case 0:
				QUADRANT_INDEX = 0;
				break;
			case 1:
				QUADRANT_INDEX = 3;
				break;
			case 2:
				QUADRANT_INDEX = 6;
				break;
			default: // do nothing
			}
		} break;
		case 1 : {
			switch ((int) ROW_INDEX / 3) {
			case 0:
				QUADRANT_INDEX = 1;
				break;
			case 1:
				QUADRANT_INDEX = 4;
				break;
			case 2:
				QUADRANT_INDEX = 7;
				break;
			default: // do nothing
			}
		} break;
		case 2 : {
			switch ((int) ROW_INDEX / 3) {
			case 0:
				QUADRANT_INDEX = 2;
				break;
			case 1:
				QUADRANT_INDEX = 5;
				break;
			case 2:
				QUADRANT_INDEX = 8;
				break;
			default: // do nothing
			}
		}
		} // end of switch()		
	} // end of setAllIndexes()
	
	public boolean isUniqueInRow() {						// Checks against the grid whether the cell is unique in its row.
		boolean check = true;
		for(Cell f : this.grid) {
			if(f.getROW_INDEX() == this.ROW_INDEX) {
				if(f.getCELL_INDEX() == this.CELL_INDEX)
					continue;
				if(f.getCell_value() == this.cell_value) 
					check = false;				
			}				
		}		
		return check;
	} // end of isUniqueInRow()
	
	public boolean isUniqueInColumn() {						// Checks against the grid whether the cell is unique in its column. 
		boolean check = true;
		for(Cell f : this.grid) {
			if(f.getCOLUMN_INDEX() == this.COLUMN_INDEX) {
				if(f.getCELL_INDEX() == this.CELL_INDEX)
					continue;
				if(f.getCell_value() == this.cell_value)
					check = false;
			}
		}
		return check;
	} // end of isUniqueInColumn()
	
	public boolean isUniqueInQuadrant() {					// Checks against the grid whether the cell is unique in its quadrant.
		boolean check = true;
		for(Cell f : this.grid) {
			if(f.getQUADRANT_INDEX() == this.QUADRANT_INDEX && f.getCELL_INDEX() != this.CELL_INDEX) {
				if(f.getCell_value() == this.cell_value)
					check = false; 
			}
		}
		return check;
	} // end of isUniqueInQuadrant()
	
	public void updateGrid(Cell[] var) {				// Passes on the 'grid' array of cells. Every cell may check all other cells for values.
		this.grid = var;
	} // end of updateGrid()		
} // end of class
