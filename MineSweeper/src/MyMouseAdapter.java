import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JFrame;

public class MyMouseAdapter extends MouseAdapter {
	private Random generator = new Random();
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
			case 1:		//Left mouse button
				Component c = e.getComponent();
				while (!(c instanceof JFrame)) {
					c = c.getParent();
					if (c == null) {
						return;
					}
				}
				JFrame myFrame = (JFrame) c;
				MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
				Insets myInsets = myFrame.getInsets();
				int x1 = myInsets.left;
				int y1 = myInsets.top;
				e.translatePoint(-x1, -y1);
				int x = e.getX();
				int y = e.getY();
				myPanel.x = x;
				myPanel.y = y;
				myPanel.mouseDownGridX = myPanel.getGridX(x, y);
				myPanel.mouseDownGridY = myPanel.getGridY(x, y);
				
				Color newColor = null;
				switch (generator.nextInt(5)) {
				case 0:
					newColor = Color.YELLOW;
					break;
				case 1:
					newColor = Color.MAGENTA;
					break;
				case 2:
					newColor = Color.BLACK;
					break;
					//					case 3:
					//						newColor = new Color(0x964B00);   //Brown (from http://simple.wikipedia.org/wiki/List_of_colors)
					//						break;
				case 4:
					newColor = new Color(0xB57EDC);   //Lavender (from http://simple.wikipedia.org/wiki/List_of_colors)
					break;
				}
				myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
				myPanel.repaint();
				break;
			case 3:		//Right mouse button
				Component d = e.getComponent();
				while (!(d instanceof JFrame)) {
					d = d.getParent();
					if (d == null) {
						return;
					}
				}
				JFrame myFrame2 = (JFrame) d;
				MyPanel myPanel2 = (MyPanel) myFrame2.getContentPane().getComponent(0);
				Insets myInsets2 = myFrame2.getInsets();
				int x2 = myInsets2.left;
				int y2 = myInsets2.top;
				e.translatePoint(-x2, -y2);
				int xx = e.getX();
				int yy = e.getY();
				myPanel2.x = xx;
				myPanel2.y = yy;
				myPanel2.mouseDownGridX = myPanel2.getGridX(xx, yy);
				myPanel2.mouseDownGridY = myPanel2.getGridY(xx, yy);
				
				Color newColor2 = Color.RED;
		
				if(myPanel2.colorArray[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY].equals(Color.RED)) {
					newColor2 = Color.WHITE;
				}
			
		myPanel2.colorArray[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY] = newColor2;
				myPanel2.repaint();
				break;
			default:    //Some other button (2 = Middle mouse button, etc.)
				//Do nothing
				break;
		}
	}
	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()) {
			case 1:		//Left mouse button
				Component c = e.getComponent();
				while (!(c instanceof JFrame)) {
					c = c.getParent();
				if (c == null) {
						return;
					}
				}
				JFrame myFrame = (JFrame)c;
				MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
				Insets myInsets = myFrame.getInsets();
				int x1 = myInsets.left;
				int y1 = myInsets.top;
				e.translatePoint(-x1, -y1);
				int x = e.getX();
				int y = e.getY();
				myPanel.x = x;
				myPanel.y = y;
				int gridX = myPanel.getGridX(x, y);
				int gridY = myPanel.getGridY(x, y);
				if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
					//Had pressed outside
					//Do nothing
				} else {
					if ((gridX == -1) || (gridY == -1)) {
						//Is releasing outside
						//Do nothing
					} else {
						if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
							//Released the mouse button on a different cell where it was pressed
							//Do nothing
						} else {
							//Released the mouse button on the same cell where it was pressed
							if ((gridX == 0) || (gridY == 0)) {
								//On the left column and on the top row... do nothing
							} else {
								//On the grid other than on the left column and on the top row:
								Color newColor = null;
								switch (generator.nextInt(5)) {
									case 0:
										newColor = Color.YELLOW;
										break;
									case 1:
										newColor = Color.MAGENTA;
										break;
									case 2:
										newColor = Color.BLACK;
										break;
									case 3:
										newColor = new Color(0x964B00);   //Brown (from http://simple.wikipedia.org/wiki/List_of_colors)
										break;
									case 4:
										newColor = new Color(0xB57EDC);   //Lavender (from http://simple.wikipedia.org/wiki/List_of_colors)
										break;
								}
								myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
								myPanel.repaint();
							}
						}
					}
				}
				myPanel.repaint();
				break;
			case 3:		//Right mouse button
				Component d = e.getComponent();
				while (!(d instanceof JFrame)) {
					d = d.getParent();
					if (d == null) {
						return;
					}
				}
				JFrame myFrame2 = (JFrame)d;
				MyPanel myPanel2 = (MyPanel) myFrame2.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
				Insets myInsets2 = myFrame2.getInsets();
				int x2 = myInsets2.left;
				int y2 = myInsets2.top;
				e.translatePoint(-x2, -y2);
				int xx = e.getX();
				int yy = e.getY();
				myPanel2.x = xx;
				myPanel2.y = yy;
				int gridXX = myPanel2.getGridX(xx, yy);
				int gridYY = myPanel2.getGridY(xx, yy);
				if ((myPanel2.mouseDownGridX == -1) || (myPanel2.mouseDownGridY == -1)) {
					//Had pressed outside
					//Do nothing
				} else {
					if ((gridXX == -1) || (gridYY == -1)) {
						//Is releasing outside
						//Do nothing
					} else {
						if ((myPanel2.mouseDownGridX != gridXX) || (myPanel2.mouseDownGridY != gridYY)) {
							//Released the mouse button on a different cell where it was pressed
							//Do nothing
						} else {
							//Released the mouse button on the same cell where it was pressed
							if ((gridXX == 0) || (gridYY == 0)) {
								//On the left column and on the top row... do nothing
							} else {
								//On the grid other than on the left column and on the top row:
								
//								switch (generator.nextInt(5)) {
//									case 0:
//										newColor = Color.YELLOW;
//										break;
//									case 1:
//										newColor = Color.MAGENTA;
//										break;
//									case 2:
//										newColor = Color.BLACK;
//										break;
//									case 3:
//										newColor = new Color(0x964B00);   //Brown (from http://simple.wikipedia.org/wiki/List_of_colors)
//										break;
//									case 4:
//										newColor = new Color(0xB57EDC);   //Lavender (from http://simple.wikipedia.org/wiki/List_of_colors)
//										break;
//								}
								Color newColor = Color.RED;
								
								if(myPanel2.colorArray[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY].equals(Color.RED)) {
									Color newColor2 = Color.WHITE;
									myPanel2.repaint();
									break;
								}
								
							}
						}
					}
				}
				myPanel2.repaint();
				break;
	
			default:    //Some other button (2 = Middle mouse button, etc.)
				//Do nothing
				break;
		}
	}
}