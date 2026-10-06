//No Collaborations
import java.util.*;

public class A3Q1 {
	static final String FOOD = ".";
	static final String EXPLORED = "#";
	static final String BARRIER = "X";
	static final String ENTRY = "!";

	public static int[] saving_frogs(String[][] board) {
		int[] ans = new int[2];
		List<int[]> entry = new ArrayList<>();
		int totalFood = 0, eatenFood = 0, foodFound, frogs = 0;

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {//Getting all entry points
				if (Character.isUpperCase(board[i][j].charAt(0)) &&
						!(board[i][j].equals(BARRIER) || board[i][j].equals("Y") || board[i][j].equals("Z"))) {
					entry.add(new int[]{i, j});
					board[i][j] = ENTRY;
				}

				if (board[i][j].equals(FOOD)) {//Counting total food
					totalFood++;
				}
			}
		}

		for (int[] position : entry) {
			if (board[position[0]][position[1]].equals(EXPLORED)) {
				continue;
			}

			foodFound = feed(board, position[0], position[1]);

			if (foodFound > 0) {//A path with food was found
				frogs++;
				eatenFood += foodFound;
			}

			if (totalFood - eatenFood == 0) {//Early termination
				break;
			}
		}

		ans[0] = frogs;
		ans[1] = totalFood - eatenFood;
		return ans;
	}

	private static int feed(String[][] board, int i, int j) {
		int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
		int foodEaten = 0;
		Stack<int[]> stack = new Stack<>();
		stack.push(new int[]{i, j});
		board[i][j] = EXPLORED;

		while (!stack.isEmpty()) {//Using an iterative DFS to explore paths
			int[] position = stack.pop();
			int x = position[0], y = position[1];

			if (board[x][y].equals(FOOD)) {
				foodEaten++;
			}

			for (int[] direction : directions) {
				int newX = x + direction[0], newY = y + direction[1];

				if (validPosition(board, newX, newY)) {
					if (board[newX][newY].equals(FOOD)) {
						foodEaten++;
					}

					board[newX][newY] = EXPLORED;
					stack.push(new int[]{newX, newY});
				}
			}
		}
		return foodEaten;
	}

	private static boolean validPosition(String[][] board, int i, int j) {
		return i >= 0 && j >= 0 && i < board.length && j < board[0].length &&
				!(board[i][j].equals(EXPLORED) || board[i][j].equals(BARRIER) || board[i][j].equals(ENTRY));
	}
}