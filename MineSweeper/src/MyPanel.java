import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JPanel;

public class MyPanel extends JPanel {
	private static final long serialVersionUID = 3426940946811133635L;
	private static final int GRID_X = 25;
	private static final int GRID_Y = 25;
	private static final int INNER_CELL_SIZE = 40;
	private static final int TOTAL_COLUMNS = 9;
	private static final int TOTAL_ROWS = 9;
	private int[][] bombs = makeBombArray();
	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
	public MyPanel() {   //This is the constructor... this code runs first to initialize
		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (TOTAL_ROWS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}
		for (int x = 0; x < TOTAL_COLUMNS; x++) {   
			for (int y = 0; y < TOTAL_ROWS; y++) {
				colorArray[x][y] = Color.WHITE;
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//Compute interior coordinates
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		int x2 = getWidth() - myInsets.right - 1;
		int y2 = getHeight() - myInsets.bottom - 1;
		int width = x2 - x1;
		int height = y2 - y1;

		//Paint the background
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x1, y1, width + 1, height + 1);

		//By default, the grid will be 10x10 (see above: TOTAL_COLUMNS and TOTAL_ROWS) 
		g.setColor(Color.BLACK);
		for (int y = 0; y <= TOTAL_ROWS; y++) {
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		for (int x = 0; x <= TOTAL_COLUMNS; x++) {
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS)));
		}

		//Paint cell colors
		for (int x = 0; x < TOTAL_COLUMNS; x++) {
			for (int y = 0; y < TOTAL_ROWS; y++) {
				Color c = colorArray[x][y];
				g.setColor(c);
				g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
		
				if (c.equals(Color.BLUE)) {
					g.setColor(Color.WHITE);
					g.drawString(Integer.toString(hasAdjacent(x,y)), (x+1)*(INNER_CELL_SIZE+1), (y+1)*(INNER_CELL_SIZE+1) );
				}
			}
		}
	}

	// This method makes an array of bombs 10 x 2
	// x coordinates are in the first column and y coordinates are in the second column
	public int[][] makeBombArray() {     
		
		int[][] bombArray = new int[10][2];
		
		for (int i = 0; i < 10; i++) {
			Bomb bomb = new Bomb();
			bombArray[i][0] = bomb.getX();
			bombArray[i][1] = bomb.getY();
	
			for (int k = 0; k < 10; k++) {
				if (i == 0) {
					break;
				}
				else if (k == i) {
					break;
				}
				else if ((bombArray[i][0] == bombArray[k][0]) && (bombArray[i][1] == bombArray[k][1])) {
					i += -1;
				}				
			}	
		}
		return bombArray;
	}
	
	public int[][] getBombs() {
		return bombs;
	}
	
	// This method helps to find the adjacent boxes that don't have a mine.
	// It is partially implemented since the verify hasn't been discussed in class
	// Verify that the coordinates in the parameters are valid.
	// Also verifies if there are any mines around the x,y coordinate
	public void revealAdjacent(int x, int y){
		if((x<0) || (y<0) || (x>=9) || (y>=9)){return;}

		else {
			colorArray[x][y] = Color.GRAY;
			revealAdjacent(x-1, y);
			revealAdjacent(x+1, y);
			revealAdjacent(x, y-1);
			revealAdjacent(x, y+1);
		}
		
		System.out.println("Test");

	}
	
	// This method finds the number of bombs adjacent to the square clicked 
		public int hasAdjacent(int x, int y) {
			int numAdjacentBombs = 0;
			for (int s = -1; s < 2; s++) {															 // to check all 8 surrounding spaces
				for (int r = 0; r < 10; r++) {       												 // to check all 10 bombs
					if ((this.getBombs()[r][0] == (x+s)) && (this.getBombs()[r][1] == (y-1))) {
						numAdjacentBombs += 1;
					}
					if ((this.getBombs()[r][0] == (x+s)) && (this.getBombs()[r][1] == (y))) {
						numAdjacentBombs += 1;
					}
					if ((this.getBombs()[r][0] == (x+s)) && (this.getBombs()[r][1] == (y+1))) {
						numAdjacentBombs += 1;
					}
				}
			}
			return numAdjacentBombs;
		}
	
	

	public int getGridX(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x == 0 && y == TOTAL_ROWS - 1) {    //The lower left extra cell
			return x;
		}
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 1) {   //Outside the rest of the grid
			return -1;
		}
		return x;
	}
	
	public int getGridY(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		
		if (x == 0 && y == TOTAL_ROWS - 1) {    // -1 The lower left extra cell
			return y;
		}
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 1) {   // Outside the rest of the grid
			return - 1; //-1
		}
		return y;
	}
}


