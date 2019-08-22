package model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Grid {

	private final int size;
	private List<Square> squares;
	private Square marker;
	private List<Square> villains;
	
	{
		squares = new ArrayList<>();
		marker = new Square(1, 1, 0);
		
	}
	
	public Grid(int size) {
		this.size = size;
		for(int i = 1; i <= size; i++) {
			for(int j = 1; j <= size; j++) {
				squares.add(new Square(i, j, 100));
			}
		}
		villains = List.of(new Square(size, size, 1),  new Square(1, size, 2), new Square(size, 1, 3));
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
