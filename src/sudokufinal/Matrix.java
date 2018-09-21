package sudokufinal;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;


public class Matrix extends JFrame {

	
	private static final long serialVersionUID = 1L;

	private String title;

	private Container pane;

	private LayoutManager manager;

	private GridBagConstraints gbc;

	private final int FIELD_DIM = 50;

	private JButton btn_go, btn_stop, btn_esc;

	private int[] grid_values;
	
	

	static {
		JFrame.setDefaultLookAndFeelDecorated(true); 			//This attribute needs to be set prior to instantiation of the GUI-class to have an effect. 
	}

	// All JFrame related settings are done in the constructor
	public Matrix(String var) {
		this.title = var;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle(title);
		init();
	} // end of constructor()

	// All JComponents are initialized here
	private void init() {			
		this.grid_values = new int[81];
		this.pane = this.getContentPane();
		this.manager = new GridBagLayout();
		this.pane.setLayout(manager);
		this.gbc = new GridBagConstraints();
		this.btn_go = new JButton("GET VALUES");
		btn_go.addActionListener((x) -> collectValues());  // Everthing that is calld from here is run in the Event Dispatch Thread.
		this.btn_esc = new JButton("GO");
		btn_esc.addActionListener((y) -> this.startSolver()); // Everthing that is calld from here is run in the Event Dispatch Thread.
		this.btn_stop = new JButton("STATUS: idle");
		this.btn_stop.addActionListener((z) -> this.reset());
		this.fillGrid();
		constrainer(0, 9, 3, btn_go);
		constrainer(3, 9, 3, btn_esc);
		constrainer(6, 9, 3, btn_stop);
	} // end of init()

	// Fills the grid with JComboBoxes for value selection
	private void fillGrid() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				JComboBox<String> cb = getBox();
				constrainer(j, i, 1, cb);				
			}
		}		
	} // end of fillGrid()

	// Sets the constraints for each Component and puts it on the content pane
	private void constrainer(int x, int y, int width, JComponent comp) {
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.insets = new Insets(2, 2, 2, 2);
		pane.add(comp, gbc);
	}

	// Creates, configures and returns box.
	private JComboBox<String> getBox() {
		String[] nums = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		JComboBox<String> field = new JComboBox<>(nums);
		field.setPreferredSize(new Dimension(FIELD_DIM, FIELD_DIM));
		field.setBackground(Color.LIGHT_GRAY);
		field.setSelectedIndex(0);
		field.setFont(new Font("Arial", Font.BOLD, 20));
		return field;
	} // end of getField()
	

	// Packs the components and sets the JFrame to visible
	public void start() {
		this.pack();
		this.setVisible(true);
	} // end of start()

	// Gathers all values that the user set on the GUI.
	public void collectValues() {
		for (int i = 0; i < 81; i++) {
			JComboBox<String> b = (JComboBox<String>) pane.getComponent(i);
			grid_values[i] = Integer.parseInt((String) b.getSelectedItem());
		}
	} // end of collectValues()
	
	// Sends the collected data to the Controller class and initiates the start of the solving process
	private void startSolver() {
		Controller.readNumbers(grid_values);
		JButton b = (JButton) pane.getComponent(83);
		JButton c = (JButton) pane.getComponent(82);
		b.setText("Processing...");
		String t = Controller.startSolver();
		c.setText(t);
		b.setText("RESET");
	} // end of startSolver()

	// Resets all necessary field for a new lap
	private void reset() {		
			Controller.reset();
			for (int i = 0; i < 81; i++) {
				JComboBox<String> bo = (JComboBox<String>) pane.getComponent(i);
				bo.setSelectedItem("0");
			}
			JButton but = (JButton) pane.getComponent(83);
			JButton butu = (JButton) pane.getComponent(82);
			but.setText("STATUS: idle");	
			butu.setText("GO");
	} // end of reset()
	
	// This method is called from the Controller class and sets a box value everytime the algorithmus has found a match
	@SuppressWarnings("static-access")
	public void setFields(int index, int value) throws InterruptedException {
		JComboBox<String> box = (JComboBox<String>) pane.getComponent(index);
		box.setSelectedItem(String.valueOf(value));			
	} // end of setFields()
} // end of class
