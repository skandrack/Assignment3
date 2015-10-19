import java.util.Scanner;
import java.io.*;
/*
	Stephen Kandrack
	CS 0401, Monday/Wednesday 3:00-4:15pm
	This program will allow the user to play the game of Over and Under, where
	The user tries to guess if the total roll of 2 dice will be over 7, under 7, or
	exactly 7. The user will enter his or her name, as well as his or her initial money
	used to make the bets for the game. This information, along with the users win/loss record,
	will then be stored in a file and be available to be accessed should that particular user
	want to come and play the game again.
*/

public class Assig3 {
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter your first name:");	// Ask for user's name
		String firstName = sc.nextLine();
		File newPlayer = new File(firstName + ".txt");			// Determine if user has played the game before by
		boolean doesPlayerExist = newPlayer.exists();			// seeing if a file exists with his or her first name.
		Player currentPlayer = new Player();
		if (doesPlayerExist == false) {
			System.out.println("Welcome New Player!");
			System.out.println("Please enter your information below:");	// Allow the user to enter his or her information to be stored
			System.out.println("Last name:");							// in a file and to be used throughout the game.
			String lastName = sc.nextLine();
			System.out.println("Initial money:");
			double initialMoney = sc.nextDouble();
			currentPlayer.setFirstName(firstName);
			currentPlayer.setLastName(lastName);
			currentPlayer.setRemainingMoney(initialMoney);
			currentPlayer.setRoundsPlayed(0);	// The player is new so he or she has not yet played or won any rounds.
			currentPlayer.setRoundsWon(0);
			PrintWriter newPlayerFile = new PrintWriter(firstName + ".txt");	// Begin to write the entered data a file.
			newPlayerFile.println(lastName);
			newPlayerFile.println(firstName);
			double money = currentPlayer.getRemainingMoney();
			newPlayerFile.println(money);
			int plays = currentPlayer.getRoundsPlayed();
			newPlayerFile.println(plays);
			int wins = currentPlayer.getRoundsWon();
			newPlayerFile.println(wins);
			newPlayerFile.close();
		}
		else {
			System.out.println("Welcome back, " + firstName + "!");	// Welcome a returning player back and read the date from their file to
			File playerFile = new File(firstName + ".txt");			// be used throughout the game.
			Scanner readFile = new Scanner(playerFile);
			String lastName = readFile.nextLine();
			firstName = readFile.nextLine();
			double money = readFile.nextDouble();
			int plays = readFile.nextInt();
			int wins = readFile.nextInt();
			currentPlayer.setFirstName(firstName);
			currentPlayer.setLastName(lastName);
			currentPlayer.setRemainingMoney(money);
			currentPlayer.setRoundsPlayed(plays);
			currentPlayer.setRoundsWon(wins);
		}
		System.out.println("Welcome to Over and Under!");
		System.out.println("Here is your current information:");
		String playerString = currentPlayer.toString();		// List off the user's information using the "toString()" method
		System.out.println(playerString);
		boolean playGame = play();	// This is used to determine if the user would like to play the game.
		int singleGameRounds = 0;		// These variables are accumulator variables that will keep track of how many
		int singleGameWins = 0;			// rounds the user has played and won, as well as how much money he or she has won or lost.
		double singleGameWinnings = 0.0;
		while (playGame) {	// Play the game while the user says that he or she wants to play.
			singleGameRounds++;	// Add 1 to the accumulator variable for rounds played.
			double currentMoney = currentPlayer.getRemainingMoney();	// Determine if the user has enough money to play (>0)
			if (currentMoney > 0) {
				System.out.printf("How much would you like to bet? (<= $%,.2f)\n", currentMoney);
				double checkBet = sc.nextInt();
				double bet = checkValidBet(currentMoney, checkBet);	// Determines if the user has given a negative bet or a bet worth more money that what the user has available.
				System.out.println("Select your choice: (O)ver, (U)nder, or (S)even:");
				char checkChoice = sc.next().charAt(0);
				char choice = checkValidChoice(checkChoice);	// Determines if the user has given a valid choice and then sets their choice to the choice variable.
				Die die1 = new Die();		// Creates 2 instances of the Die object.
				Die die2 = new Die();
				int roll1 = die1.rollDie();	// Call the rollDie method for both dice.
				int roll2 = die2.rollDie();
				int totalRoll = roll1 + roll2;	// total the rolls.
				System.out.println("The Dice have been rolled...");
				System.out.println("the first die reads " + roll1 + " and the second reads " + roll2 +
									" for a total of " + totalRoll + "!!");
				boolean win = winOrLose(choice, totalRoll);	// Determine if the user won or lost.
				if (win == true) {
					System.out.println("Congratulations, you WON this round!");
					currentPlayer.wonAGame();	// Adds 1 to both the roundsWon and roundsPlayed variables for the currentPlayer object.
					singleGameWins++;			// Adds 1 to the singleGameWins accumulator variable.
					double winnings = bet;		// This is to differentiate between the bet itself and the outcome  of the bet.
					if (choice == 'O' || choice == 'U') {
						currentPlayer.addMoney(winnings);					// Adds the winnings to all of the accumulator variables.
						currentMoney = currentPlayer.getRemainingMoney();
						singleGameWinnings = singleGameWinnings + winnings;
					}
					if (choice == 'S') {
						winnings = winnings * 4;	// Winning with choice S quadruples your money.
						currentPlayer.addMoney(winnings);
						currentMoney = currentPlayer.getRemainingMoney();
						singleGameWinnings = singleGameWinnings + winnings;
					}
					System.out.printf("You have won $%,.2f\n", winnings);	// Notifies the user of his or her winnings and then will update their total.
					System.out.printf("Your updated money total is $%,.2f\n", currentMoney);
				}
				else {
					System.out.println("Sorry, you lost this round!");
					currentPlayer.lostAGame();
					double loss = bet;	// Used to differentiate between the bet itself and how much the user loses.
					currentPlayer.subtractMoney(loss);					// The amount that the user used to bet is now subtracted
					currentMoney = currentPlayer.getRemainingMoney();	// from all totals used to keep track of the user's money.
					singleGameWinnings = singleGameWinnings - loss;
					System.out.printf("You have lost $%,.2f\n", loss);
					System.out.printf("Your updated money total is $%,.2f\n", currentMoney);
				}
				playerString = currentPlayer.toString();	// Again, notify the user of their information.
				System.out.println("Here is your updated information:");
				System.out.println(playerString);
				playGame = play();	// Determine if the player wants to play again.
			}
			else {
				System.out.println("Sorry, looks like you're out of money!");				// If the user has no money but wants to play the game, this is displayed.
				System.out.println("Gambling problem? Call 1-800-522-4700");
				playGame = false;	// The user is not allowed to play the game because he or she is broke! He or she has a serious gambling problem and should seek help.
			}
		}
		System.out.println("Thank you for playing Over and Under!");	// List out the user's updated info after they have finished playing the game.
		playerString = currentPlayer.toString();
		System.out.println("Here is your updated information:");
		System.out.println(playerString);
		determineWinnings(singleGameWinnings);	// Used to determine if the user won or lost money and notifies him or her of how much he or she won or lost.
		if (singleGameRounds == 1) {		// notifies the user of how many rounds he or she played and how many rounds he or she won.
			System.out.println("You played " + singleGameRounds + " round and won " + singleGameWins + " of them!");
		}								// There's two different ones because wording, and none for 0 rounds played because that wouldn't make sense.
		if (singleGameRounds > 1) {
			System.out.println("You played " + singleGameRounds + " rounds and won " + singleGameWins + " of them!");
		}
		PrintWriter newPlayerFile = new PrintWriter(firstName + ".txt");	// Now just write the updated info from after the games back to same file.
		String lastName =  currentPlayer.getLastName();
		newPlayerFile.println(lastName);
		newPlayerFile.println(firstName);
		double money = currentPlayer.getRemainingMoney();
		newPlayerFile.println(money);
		int plays = currentPlayer.getRoundsPlayed();
		newPlayerFile.println(plays);
		int wins = currentPlayer.getRoundsWon();
		newPlayerFile.println(wins);
		newPlayerFile.close();
	}

	public static boolean play() {
		Scanner sc = new Scanner(System.in);
		boolean keepAsking = true;
		int playerAnswer = 0;
		while (keepAsking == true) {
			System.out.print("Would you like to play a game? (1 for yes, 2 for no) ");
			if (sc.hasNextInt()) {			// Only allow 1 or 2 to be acceptable input.
				playerAnswer = sc.nextInt();
				if (playerAnswer == 1) {
					keepAsking = false;
				} else if (playerAnswer == 2) {
					keepAsking = false;
				} else {
					System.out.println("Please enter a 1 for yes and 2 for no!");
				}
			} else {
				sc.next();
				System.out.println("Please enter a 1 for yes and 2 for no!");
			}
		} if (playerAnswer == 1) {	// true if the player wants to play.
			return true;
		} else {
			return false;
		}
	}

	public static double checkValidBet(double money, double input) {
			Scanner sc = new Scanner(System.in);
			double changeInput = input;
			while (changeInput < 0 || changeInput > money) {	// Don't accept negative integers!
				System.out.println("Please enter a valid integer!");
				changeInput = sc.nextInt();
			}
			return changeInput; // Return an integer once the user has entered a positive integer.
	}

	public static char checkValidChoice(char input) {	// Determines if the user has entered a valid option for the game.
		Scanner sc = new Scanner(System.in);
		char changeInput = input;
		boolean keepAsking = true;
		do {
			if (changeInput == 'o' || changeInput == 'O') {			// Accepts o or O for the "over" option.
				changeInput = 'O';
				keepAsking = false;
			}
			else if (changeInput == 'u' || changeInput == 'U') {	// Accepts u or U for the "under" option.
				changeInput = 'U';
				keepAsking = false;
			}
			else if (changeInput == 's' || changeInput == 'S') {	// Accepts s or S for the "seven" option.
				changeInput = 'S';
				keepAsking = false;
			}
			else {
				System.out.println("Please enter a valid character!");
				changeInput = sc.next().charAt(0);
				keepAsking = true;
			}
		} while (keepAsking == true);	// Continues to ask the user for an option until they have entered a O, U, or S.
		return changeInput;
	}
	public static boolean winOrLose(char choice, int roll) {	// Returns true if the user wins and false if they lose.
		boolean win = true;
		if (choice == 'O') {	// If the user selects O, then they win when the total roll is over 7.
			if (roll > 7) {
				win = true;
			}
			else {				// ...otherwise they lose.
				win = false;
			}
		}
		if (choice == 'U') {	// If the user selects U, then they win when the total roll is under 7.
			if (roll < 7) {
				win = true;
			}
			else {				// ...otherwise they lose.
				win = false;
			}
		}
		if (choice == 'S') {	// If the user selects S, then they win only when the total roll is equal to 7.
			if (roll == 7) {
				win = true;
			}
			else {				// ...otherwise, you guessed it, they lose.
				win = false;
			}
		}
		return win;
	}
	public static void determineWinnings(double winnings) {	// Prints out the winnings at the end of the game. Determines if they made money, lost money, or broke even.
		if (winnings > 0) {
			System.out.printf("You won a total of $%,.2f!\n", winnings);
		}
		else if (winnings < 0) {
			winnings = winnings * (-1);
			System.out.printf("You lost a total of $%,.2f!\n", winnings);	
		}
		else {
			System.out.println("You broke even!");
		}
	}
}