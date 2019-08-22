package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import model.Grid;
import model.Square;

public class View {

	public static final Logger LOGGER = Logger.getLogger(View.class);
	
	public static final int SIZE = 7;
	
	public static final int numberOfEnemies = 7;

	public static void main(String[] args) throws InterruptedException {
		List<String> possibleCommands;
		Scanner scanner = new Scanner(System.in);
		Grid grid = new Grid(SIZE, numberOfEnemies);
		int score = 0;
		while (true) {
			
			
			System.out.println("\nScore: " + score + "\n");
			grid.out();
			Square user = grid.getMarker();
			List<Square> villains = grid.getVillains();
			String command;

			possibleCommands = getPossibleCommands(user);
			String commandUser = scanner.nextLine().toLowerCase();
			if (!possibleCommands.contains(commandUser))
				continue;
			score++;
			
			for(Square villain : villains) {
				possibleCommands = getPossibleCommands(villain);
				command = getCommand(user, villain, possibleCommands);
				command(villain, command);
			}

			command(user, commandUser);

			boolean isCaught = isCaught(user, villains);
			if (isCaught) {
				grid.out();
				Thread.sleep(3000);
				System.err.println("You lose!\n");
				System.out.println("\nScore: " + score);
				break;
			}
			boolean isWon = user.getX() == SIZE && user.getY() == SIZE;
			if (isWon) {
				grid.out();
				Thread.sleep(3000);
				System.out.println("You win!\n");
				System.out.println("\nScore: " + score);
				break;
			}
		}
	}

	private static String getCommand(Square user, Square villain, List<String> possibleCommands) {
		String command = possibleCommands.get((int) Math.floor(possibleCommands.size() * Math.random()));
		int x = user.getX();
		int y = user.getY();
		int xDiff = villain.getX() - x;
		int yDiff = villain.getY() - y;
		if ((int) Math.abs(xDiff) == 1 && (int) Math.abs(yDiff) == 1) {
			List<String> possible = new ArrayList<>();
			if(xDiff < 0)
				possible.add("right");
			if(xDiff > 0)
				possible.add("left");
			if(yDiff < 0)
				possible.add("down");
			if(yDiff > 0)
				possible.add("up");
			command = possible.get((int) Math.floor(Math.random() * possible.size()));
				
		} else {
			if (xDiff > 0 && xDiff <= 2 && Math.abs(yDiff) < 2)
				command = "left";
			if (xDiff < 0 && xDiff >= -2 && Math.abs(yDiff) < 2)
				command = "right";
			if (yDiff > 0 && yDiff <= 2 && Math.abs(xDiff) < 2)
				command = "up";
			if (yDiff < 0 && yDiff >= -2 && Math.abs(xDiff) < 2)
				command = "down";
		}
		return command;
	}

	private static List<String> getPossibleCommands(Square marker) {
		int x = marker.getX();
		int y = marker.getY();
		List<String> result = new ArrayList<>();
		if (x < SIZE)
			result.add("right");
		if (x > 1)
			result.add("left");
		if (y < SIZE)
			result.add("down");
		if (y > 1)
			result.add("up");
		return result;
	}

	private static boolean isCaught(Square user, List<Square> villains) {
		int x = user.getX();
		int y = user.getY();
		boolean result = false;
		for(Square villain : villains) {
			if (x == villain.getX() && y == villain.getY())
				result = true;
		}
		return result;
	}

	public static void command(Square marker, String command) {
		switch (command) {
		case "up":
			if (marker.getY() > 1)
				marker.up();
			break;
		case "down":
			if (marker.getY() < SIZE)
				marker.down();
			break;
		case "left":
			if (marker.getX() > 1)
				marker.left();
			break;
		case "right":
			if (marker.getX() < SIZE)
				marker.right();
			break;
		default:
			System.err.println("\n***********Invalid command************\n");
		}
	}
}
