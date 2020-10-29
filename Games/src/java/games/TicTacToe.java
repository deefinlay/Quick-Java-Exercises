package java.games;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Simple and very basic game of Tic Tac Toe, the user chooses a position on a
 * grid layout board. The computer selects a square using the Random
 * functionality with no further processing
 * 
 * @author DeeFinlay
 *
 */
public class TicTacToe {

	static ArrayList<Integer> humanPositions = new ArrayList<Integer>();
	static ArrayList<Integer> cpuPositions = new ArrayList<Integer>();

	public static void main(String[] args) {
		// Layout a 2d array of chars as the game board
		// The board is 5x5, including separator characters
		char[][] gameBoard = { { ' ', '|', ' ', '|', ' ' }, { '-', '+', '-', '+', '-' }, { ' ', '|', ' ', '|', ' ' },
				{ '-', '+', '-', '+', '-' }, { ' ', '|', ' ', '|', ' ' } };
		int playerSquare;
		printGameBoard(gameBoard);
		// Open scanner to get user's selected square
		Scanner getEntry = new Scanner(System.in);
		// the main infinite game loop, stopped only when a result is returned by the
		// checkWinner() method
		while (true) {
			// prompt the user to enter their value in a square, labelled as 1 thru 9
			System.out.println("Enter your square (1-9)");
			playerSquare = getEntry.nextInt();
			// check if that square is already taken
			while (humanPositions.contains(playerSquare) || cpuPositions.contains(playerSquare)) {
				System.out.println("Position taken, try again");
				playerSquare = getEntry.nextInt();
			}
			printGameBoard(gameBoard);
			populateSquare(gameBoard, playerSquare, "Human");
			String result = checkWinner();
			// test for game over
			if (result.length() > 0) {
				System.out.println(result);
				printGameBoard(gameBoard);
				break;
			}
			// computer's turn
			Random rand = new Random();
			int cpuSquare = rand.nextInt(8) + 1;
			while (humanPositions.contains(cpuSquare) || cpuPositions.contains(cpuSquare)) {
				cpuSquare = rand.nextInt(8) + 1;
			}
			populateSquare(gameBoard, cpuSquare, "CPU");
			printGameBoard(gameBoard);
			result = checkWinner();
			// test for game over
			if (result.length() > 0) {
				System.out.println(result);
				printGameBoard(gameBoard);
				break;
			}
		}
		// close the scanner to prevent resource leak
		getEntry.close();
	}

	/**
	 * Prints out the game board to screen
	 * 
	 * @param board is a 2d character array representing the board
	 */
	public static void printGameBoard(char[][] board) {
		for (char[] row : board) {
			for (char entry : row) {
				System.out.print(entry);
			}
			System.out.println();
		}
	}

	/**
	 * Allocates the correct piece, X or O in the selected square on the board
	 * 
	 * @param board    is the game board
	 * @param position is the position on the board as a 2d array
	 * @param user     as designated by Human or CPU
	 */
	public static void populateSquare(char[][] board, int position, String user) {
		char symbol = ' ';
		if (user == "Human") {
			symbol = 'X';
			humanPositions.add(position);
		} else if (user == "CPU") {
			symbol = 'O';
			cpuPositions.add(position);
		}
		switch (position) {
		// remember there are non-play-square separators in this array!
		case 1:
			board[0][0] = symbol;
			break;
		case 2:
			board[0][2] = symbol;
			break;
		case 3:
			board[0][4] = symbol;
			break;
		case 4:
			board[2][0] = symbol;
			break;
		case 5:
			board[2][2] = symbol;
			break;
		case 6:
			board[2][4] = symbol;
			break;
		case 7:
			board[4][0] = symbol;
			break;
		case 8:
			board[4][2] = symbol;
			break;
		case 9:
			board[4][4] = symbol;
			break;
		}

	}

	/**
	 * Checks whether either player has squares that comprise a winning combination
	 * or whether neither do but all squares are occupied, in which case a draw is
	 * declared
	 * 
	 * @return the winner or the declaration of a draw
	 */
	public static String checkWinner() {
		// declare the winning permutations
		List topRow = Arrays.asList(1, 2, 3);
		List middleRow = Arrays.asList(4, 5, 6);
		List bottomRow = Arrays.asList(7, 8, 9);
		List leftCol = Arrays.asList(1, 4, 7);
		List middleCol = Arrays.asList(2, 5, 8);
		List rightCol = Arrays.asList(3, 6, 9);
		List topLeftDiag = Arrays.asList(1, 5, 9);
		List topRightDiag = Arrays.asList(3, 5, 7);

		// add all these into one big list
		List<List> winningCombos = new ArrayList<List>();
		winningCombos.add(topRow);
		winningCombos.add(middleRow);
		winningCombos.add(bottomRow);
		winningCombos.add(leftCol);
		winningCombos.add(middleCol);
		winningCombos.add(rightCol);
		winningCombos.add(topLeftDiag);
		winningCombos.add(topRightDiag);

		// scan the list to determine if either player's squares comprise a winning
		// combination
		for (List l : winningCombos) {
			if (humanPositions.containsAll(l)) {
				return "Congratulations, you win!";
			} else if (cpuPositions.containsAll(l)) {
				return "Computer wins!";
			}
		}
		// if all positions are occupied and not contained in the winning combinations
		// list
		if (humanPositions.size() + cpuPositions.size() == 9) {
			return "It's a draw!";
		}
		return "";
	}

}