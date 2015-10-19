public class Player {
	private String firstName;
	private String lastName;
	private double remainingMoney;
	private int roundsPlayed;
	private int roundsWon;
	
	public void setFirstName(String name) {
		firstName = name;
	}
	public void setLastName(String name) {
		lastName = name;
	}
	public void setRemainingMoney(double money) {
		remainingMoney = money;
	}
	public void setRoundsPlayed(int rounds) {
		roundsPlayed = rounds;
	}
	public void setRoundsWon(int rounds) {
		roundsWon = rounds;
	}
	public String getName() {
		return firstName + " " + lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public double getRemainingMoney() {
		return remainingMoney;
	}
	public int getRoundsPlayed() {
		return roundsPlayed;
	}
	public int getRoundsWon() {
		return roundsWon;
	}
	public void addMoney(double money) {
		remainingMoney = remainingMoney + money;
	}
	public void subtractMoney(double money) {
		remainingMoney = remainingMoney - money;
	}
	public void wonAGame() {
		roundsWon++;
		roundsPlayed++;
	}
	public void lostAGame() {
		roundsPlayed++;
	}
	public String toString() {
		String str = "Name: " + firstName + " " + lastName +
					"\nMoney left: " + "$" + remainingMoney +
					"\nTotal rounds played: " + roundsPlayed +
					"\nTotalRoundsWon: " + roundsWon;
		return str;		
	}
}