import java.util.ArrayList;

class Brett {
	
	private Rute [][] board;
	
	private final int r;
	private final int c;

	static int SIZE;

	private Rad[] rows;
	private Kolonne[] columns;
	private Boks[] boxes;

	private SudokuBeholder sudoku;

	Brett(int rows, int columns) {
		int size = rows * columns;
		board = new Rute[size][size];
		r = rows;
		c = columns;
		SIZE = rows*columns;
		this.rows = new Rad[size];
		this.columns = new Kolonne[size];
		boxes = new Boks[size];
		for (int i = 0; i < size; i++) {
			boxes[i] = new Boks(i+1, rows, columns); // adding 1 to make it more natural to count.
		}
		sudoku = new SudokuBeholder();
	}

/**
* Fills in the board.
*
* @param Rute[]	Array with cells that represents row on a board.
* @param int	Index of the row on the board.
*/
	public void fillIn(Rute[] cellArray, int index) {
		board[index] = cellArray;
		
		// rows are set automatically in constructor.
		rows[index] = new Rad(index + 1, cellArray); // adding 1 to make it more natural to count.
	}
/**
* Initializes the structure of the board. Sets all the information in the cells
* about the row, column and box where the cell is located.
*
*/
	public void opprettDatastruktur() {
		// sets the columns.
		for (int i = 0; i < board.length; i++) {
			Rute[] cellArrayForColumn = new Rute[rows.length];
			for (int j = 0; j < board[i].length; j++) {
				cellArrayForColumn[j] = board[j][i];
			}
			Kolonne newColumn = new Kolonne(i + 1, cellArrayForColumn); // adding 1 to make it more natural to count.
			columns[i] = newColumn;
		}

		// sets the boxes.
		try {

			int tmpBox = 0;
			
			for (int i = 0; i < board.length; i++) {
		
				int c1;
				int c2 = 0;
		
				if (i%r == 0) {
					c1 = i;
					tmpBox = i;
				}else{
					c1 = tmpBox;
				}

				for (int j = 0; j < board[i].length; j++) {
					if (c2 < c) {
						boxes[c1].add(board[i][j]);
						c2++;
					} else if (c2 == c) {
						c2 = 0;
						c1++;
						boxes[c1].add(board[i][j]);
						c2++;
					}
				}
			}
		} catch (BoxIsFullException e) {
			System.out.println("Error: Box is full! Something went wrong.");
		}

		// initializes every cell to have the info about the row, column, box
		// the cell is located in.

		for (Rad r: rows) {
			r.initRow();
		}
		for (Kolonne c: columns) {
			c.initColumn();
		}
		for (Boks b: boxes) {
			b.initBox();
		}
	}

/**
* Returns the board as a double array of cells.
*
* @return Rute[][]	Double array of cells.
*/
	public Rute[][] getBoard() {
		return board;
	}

/**
* Solves the sudoku. Saves all the solutions in the instance of class SudokuBeholder.
*
*/
	public void solve() {
		ArrayList<Rute> emptyCells = new ArrayList<Rute>();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (!board[i][j].isSet()) emptyCells.add(board[i][j]);
			}
		}
		Rute[] emptyCellsArray = new Rute[emptyCells.size()];
		for (int i = 0; i < emptyCellsArray.length; i++) {
			emptyCellsArray[i] = emptyCells.get(i);
		}
		
		MergeSorter.sort(emptyCellsArray);

		for (int i = 0; i < emptyCellsArray.length - 1; i++) {
			emptyCellsArray[i].setNext(emptyCellsArray[i+1]);
		}
		emptyCellsArray[0].fyllUtDenneOgResten();
	}

	public SudokuBeholder getSudoku() {
		return sudoku;
	}

	//Test methods to print out different combinations below
	
	// public void printCellPossNr() {
	// 	for (int i : board[5][5].finnAlleMuligeTall()) {
	// 		System.out.println(i);
	// 	}
	// }

	// public void printOut2() {
	// 	for (int i = 0; i < board.length; i++) {
	// 		for (int j = 0; j < board[i].length; j++) {
	// 			System.out.print(board[i][j].getNr() + " ");
	// 		}
	// 		System.out.println();
	// 	}
	// }

	// public void printBox() {
	// 	for (Boks b: boxes) {
	// 		b.printBox();
	// 		System.out.println("-----");
	// 	}
	// }

	// public void printCells() {
	// 	for (int i = 0; i < board.length; i++) {
	// 		for (int j = 0; j < board[i].length; j++) {
	// 			board[i][j].printInfo();
	// 			System.out.println();
	// 			System.out.println("---------------");
	// 		}
			
	// 	}
	// } 

}