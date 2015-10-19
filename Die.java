import java.util.Random;

public class Die {
	public int rollDie() {
		Random rand = new Random();
		int roll = rand.nextInt(6) + 1;
		return roll;
	}
}