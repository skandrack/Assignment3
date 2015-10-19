import java.util.Random;

public class Die {		// Allows the simulation of the rolling of one 6 sided die.
	public int rollDie() {
		Random rand = new Random();
		int roll = rand.nextInt(6) + 1;
		return roll;
	}
}