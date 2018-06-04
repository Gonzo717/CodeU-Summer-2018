package codeu.model.data;

/**
 * Maintains the state of a Tic-Tac-Toe game. Uses the conventions:
 * 
 * 1 means X 
 * -1 means O
 * 0 means empty.
 * 
 * 
 */
public class Tictactoe implements java.io.Serializable {
	/**
	 * The current state of the board.
	 */
	private int[][] board;
	
	/**
	 * rowSum[i] is the sum of the i-th row. Used 
	 * to determine winners.
	 */
	private int[] rowSum;

	/**
	 * colSum[i] is the sum of the i-th column. Used to determine winners.
	 */	
	private int[] colSum;

	/**
	 * diagSum[0] is the sum of the main diagonal, and diagSum[1] is the sum of
	 * the off-diagonal. Used to determine winners.
	 */
	private int[] diagSum;

	/**
	 * True iff the game is over.
	 */
	private boolean over = false;

	/**
	 * Construct a new, empty game.
	 */
	public void Game() {
		board = new int[3][3];
		rowSum = new int[3];
		colSum = new int[3];
		diagSum = new int[2];
		reset();
	}

	/**
	 * Reset the game.
	 */
	public void reset() {
		over = false;
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				board[r][c] = 0;
			}
		}

		for (int i = 0; i < 3; i++) {
			rowSum[i] = 0;
			colSum[i] = 0;
			if (i < 2) {
				diagSum[i] = 0;
			}
		}

	}

	/**
	 * Puts the value val in position (r,c).
	 * 
	 * @param r the row
	 * @param c the col
	 * @param val the value to place in position (r,c)
	 */
	public void set(int r, int c, int val) {
		if (board[r][c] != 0) {
            throw new IllegalStateException("Attempted to set(" + r + ", " + c + ", " + val
                    + "), but that board position already has the non-zero value " + board[r][c]);
        }
		
        int old = board[r][c];
		board[r][c] = val;

		int diff = val - old;
		rowSum[r] += diff;
		colSum[c] += diff;
		if (r == c) {
			diagSum[0] += diff;
		}
		if (r == 2 - c) {
			diagSum[1] += diff;
		}
	}

	/**
	 * Returns the value at position (r,c).  1 for X, -1 for O, 0 for empty.
	 * 
	 * @param r the row
	 * @param c the col
	 * @return the value at position (r,c).  1 for X, -1 for O, 0 for empty.
	 */
	public int get(int r, int c) {
		return board[r][c];
	}

	/**
	 * Returns true iff the game is over.
	 * 
	 * @return true iff the game is over.
	 */
	public boolean isOver() {
		return over;
	}

	/**
	 * Returns true iff row r is full.
	 * 
	 * @param r the row
	 * @return true iff row r is full.
	 */
	public boolean isRowFull(int r) {
		for (int c = 0; c < 3; c++) {
			if (board[r][c] == 0) {
				return false;
			}
		}
		return true;
	}

    /**
     * Returns true iff col c is full.
     * 
     * @param r the row
     * @return true iff col c is full.
     */
	public boolean isColFull(int c) {
		for (int r = 0; r < 3; r++) {
			if (board[r][c] == 0) {
				return false;
			}
		}
		return true;
	}

    /**
     * Returns true iff the whole board is full. In that case, it also declares the game over.
     * 
     * @return true iff the whole board is full.
     */
	public boolean isFull() {
		for (int r = 0; r < 3; r++) {
			if (!isRowFull(r)) {
				return false;
			}
		}
		over = true;
		return true;
	}

    /**
     * Returns 1 if X has won, -1 if O has won, and 0 if neither has won (including the case of a
     * tie). If a player has won, then also declares the game over.
     * 
     * @return 1 if X has won, -1 if O has won, and 0 if neither has won.
     */
	public int checkWin() {
		// Check rows
		for (int i = 0; i < 3; i++) {
			if (Math.abs(rowSum[i]) == 3) {
				over = true;
				return rowSum[i] / Math.abs(rowSum[i]);
			}
		}

		// Check cols
		for (int i = 0; i < 3; i++) {
			if (Math.abs(colSum[i]) == 3) {
				over = true;
				return colSum[i] / Math.abs(colSum[i]);
			}
		}

		// Check diags
		for (int i = 0; i < 2; i++) {
			if (Math.abs(diagSum[i]) == 3) {
				over = true;
				return diagSum[i] / Math.abs(diagSum[i]);
			}
		}

		return 0;
	}

	// A helper method for computerMove()
	private String done(int r, int c) {
		set(r, c, -1);
		return r + "" + c;
	}

    /**
     * The computer makes its move and returns a string representation of its choice. The string
     * consists of two digits, each between 0 and 2 inclusive, representing the row and column it
     * chose to move in.
     * 
     * @return a String representation of the computer's move
     */
	public String computerMove() {
		// Look to win in a row
		for (int r = 0; r < 3; r++) {
			if (rowSum[r] == -2) {
				// Find the empty space
				for (int c = 0; c < 3; c++) {
					if (board[r][c] == 0) {
						return done(r, c);
					}
				}
			}
		}

		// Look to win in a col
		for (int c = 0; c < 3; c++) {
			if (colSum[c] == -2) {
				// Find the empty space
				for (int r = 0; r < 3; r++) {
					if (board[r][c] == 0) {
						return done(r, c);
					}
				}
			}
		}

		// Look to win a diag
		for (int d = 0; d < 2; d++) {
			if (diagSum[d] == -2) {
				// Find the empty space
				for (int r = 0; r < 3; r++) {
					int c = d == 0 ? r : 2 - r;
					if (board[r][c] == 0) {
						return done(r, c);
					}
				}
			}
		}

		// Look to block a row
		for (int r = 0; r < 3; r++) {
			if (rowSum[r] == 2) {
				// Find the empty space
				for (int c = 0; c < 3; c++) {
					if (board[r][c] == 0) {
						return done(r, c);
					}
				}
			}
		}

		// Look to block a col
		for (int c = 0; c < 3; c++) {
			if (colSum[c] == 2) {
				// Find the empty space
				for (int r = 0; r < 3; r++) {
					if (board[r][c] == 0) {
						return done(r, c);
					}
				}
			}
		}

		// Look to block a diag
		for (int d = 0; d < 2; d++) {
			if (diagSum[d] == 2) {
				// Find the empty space
				for (int r = 0; r < 3; r++) {
					int c = d == 0 ? r : 2 - r;
					if (board[r][c] == 0) {
						return done(r, c);
					}
				}
			}
		}

		// Find the non-full row and col w/ least val
		int bestR = -1;
		int bestC = -1;
		int minSum = 10;
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				if (board[r][c] == 0) {
					int sum = rowSum[r] + colSum[c];
					if (sum < minSum) {
						minSum = sum;
						bestR = r;
						bestC = c;
					}
				}
			}
		}

		return done(bestR, bestC);
	}

}
