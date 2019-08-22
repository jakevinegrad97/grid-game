package model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Grid {

	private final int size, numberOfVillains;
	private List<Square> squares;
	private Square marker;
	private List<Square> villains;
	
	{
		squares = new ArrayList<>();
		marker = new Square(1, 1);
		
	}
	
	public Grid(int size, int numberOfVillains) {
		this.size = size;
		this.numberOfVillains = numberOfVillains;
		for(int i = 1; i <= size; i++) {
			for(int j = 1; j <= size; j++) {
				squares.add(new Square(i, j));
			}
		}
		villains = new ArrayList<>();
		for(int i = 0; i < numberOfVillains; i++) {
			int x = (int) (3 + (size - 3) * Math.random());
			int y = (int) (3 + (size - 3) * Math.random());
			while(x % 2 == 0 || y % 2 == 0) {
				x = (int) (3 + (size - 3) * Math.random());
				y = (int) (3 + (size - 3) * Math.random());
			}
			villains.add(new Square(x, y));
		}
	}
	
	public void out() {
		String result = "";
		for(int i = 1; i <= size; i++) {
			result += " _";
		}
		result += "\n";
		for(int i = 1; i <= size; i++) {
			String line = "|";
			for(int j = 1; j <= size; j++) {
				if(isVillain(villains, i, j)) {
					line += "x|";
				} else if(i == marker.getY() && j == marker.getX()) {
					line += "o|";
				} else if(i == size && j == size) {
					line += "O|";
				} else {
					line += "_|";
				}
			}
			result += line + "\n";
		}
		System.out.println(result);
	}

	private boolean isVillain(List<Square> villains2, int i, int j) {
		boolean result = false;
		for(Square villain : villains) {
			if(villain.getX() == j && villain.getY() == i) {
				result = true;
			}
		}
		return result;
	}
	
}
