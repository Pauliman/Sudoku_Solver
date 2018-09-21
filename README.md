# Sudoku_Solver
A Sudoku solver using principles of object oriented programming
In folder sudoku_oop you will find a non-GUI version that just serves to prove functionality.
In sudokufinal you will find the code for the GUI version.
Eventually, you'll find a runnable jar-file in the folder Runnable_Files.
The idea behind this project was twofold. First the Sudoku grid should be reconstructed using Cell-objects that provided the 
data and the functionality to check the data. The controller class does the board preparation and recursive logic using a simple
brute force algorithm. On the front end side a GUI should mirror the cells and display the values in the cells. 
The actual task was to find a way to write back to the GUI via the Event-Dispatch-Queue. The program does what it's supposed to do,
but I paid little attention to the design aspect. So feel free to use it to solve your Sudokus. 
By the way I limited the amount of recursions to 100.000 to determine when it is impossible to find a solution.
