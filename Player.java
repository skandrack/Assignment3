public class Player {
	private String firstName;		// These are variables for everything that this class stores.
	private String lastName;
	private double remainingMoney;
	private int roundsPlayed;
	private int roundsWon;
	
	public void setFirstName(String name) {			// This is how the first name can be set for the user.
		firstName = name;
	}
	public void setLastName(String name) {			// This is how the last name of the user can be set.
		lastName = name;
	}
	public void setRemainingMoney(double money) {	// This is used to set the user's money.
		remainingMoney = money;
	}
	public void setRoundsPlayed(int rounds) {		// This is used to set how many rounds the user has played.
		roundsPlayed = rounds;
	}
	public void setRoundsWon(int rounds) {			// This is used to set how many rounds the user has won.
		roundsWon = rounds;
	}
	public String getName() {				// This is used to retrieve the user's name.
		return firstName + " " + lastName;
	}
	public String getFirstName() {			// This is used to retreive just the user's first name.
		return firstName;
	}
	public String getLastName() {			// This is used to retrieve just the user's last name.
		return lastName;
	}
	public double getRemainingMoney() {		// This is used to retrieve the user's remaining money.
		return remainingMoney;
	}
	public int getRoundsPlayed() {			// This is used to retrieve the number of rounds the user has played.
		return roundsPlayed;
	}
	public int getRoundsWon() {				// This is used to retrieve the number of rounds the user has won.
		return roundsWon;
	}
	public void addMoney(double money) {			// This is used to add money to the user's total when they win.
		remainingMoney = remainingMoney + money;
	}
	public void subtractMoney(double money) {		// This is used to subtract money from the user's total when they lose.
		remainingMoney = remainingMoney - money;
	}
	public void wonAGame() {		// Adds 1 to the roundsWon and roundsPlayed accumulator variables when the user wins.
		roundsWon++;
		roundsPlayed++;
	}
	public void lostAGame() {		 // Adds 1 to the roundsPlayed accumulator variable when the user loses.
		roundsPlayed++;
	}
	public String toString() {		// returns the user's information as a string.
		String str = "Name: " + firstName + " " + lastName +
					"\nMoney left: " + "$" + remainingMoney +
					"\nTotal rounds played: " + roundsPlayed +
					"\nTotalRoundsWon: " + roundsWon;
		return str;		
	}
}