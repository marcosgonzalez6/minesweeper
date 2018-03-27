import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyMouseAdapter extends MouseAdapter {
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
				
				if (myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.RED))
				break;
				if (myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.BLUE))
					break;
				
//				myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.WHITE;
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
				else if (myPanel2.colorArray[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY].equals(Color.GRAY))
					break;
				
				else if (myPanel2.colorArray[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY].equals(Color.BLUE))
					break;
			
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
				} 
				else if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing
				} 
				else if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
					//Released the mouse button on a different cell where it was pressed
					//Do nothing
				} 	
				else {
					Color newColor = Color.GRAY;
					if(myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.RED)) {
						break;
					}
					myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;	
				}	
			
				for(int i = 0; i < 10; i++) {	
					if ((gridX == myPanel.getBombs()[i][0]) && (gridY == myPanel.getBombs()[i][1])) {
						System.out.println("GAME OVER");
						myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.BLACK;
						myPanel.repaint();
						
						for (int t = 0; t < 10; t++) {
							int xBombLocation = myPanel.getBombs()[t][0];
							int yBombLocation = myPanel.getBombs()[t][1];
							myPanel.colorArray[xBombLocation][yBombLocation] = Color.BLACK;
						}
						myPanel.repaint();
						JOptionPane.showMessageDialog(null,  "GAME OVER!");
						System.exit(0);
					}
				}
				if ((myPanel.hasAdjacent(gridX, gridY) != 0) && !(myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.BLACK))) {
					System.out.println(myPanel.hasAdjacent(gridX, gridY));
					myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.BLUE;
					myPanel.repaint();
					break;
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
								
								if(myPanel2.colorArray[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY].equals(Color.RED)) {
									myPanel2.repaint();
									break;
								
								}
								if (myPanel2.colorArray[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY].equals(Color.BLUE))
									break;
								
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