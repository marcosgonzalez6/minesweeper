import java.util.Random;

public class Bomb {
	
	private int x;
	private int y;
	private Random generator = new Random();
	
	// Constructs bombs with random x and y coordinates
	public Bomb() {
		this.x = generator.nextInt(9);
		this.y = generator.nextInt(9);
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}