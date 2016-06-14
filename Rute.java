import java.util.ArrayList;

class Rute {
	private int number; // 0 if no value
	private Rad row;
	private Kolonne column;
	private Boks box;
	private Brett board;

	private final boolean isSet;

	private Rute next = null;

	Rute(int number, Brett board) {
		this.number = number;
		this.board = board;
		if (number != 0) isSet = true;
		else isSet = false;
	}
/**
* Returns the number in the cell.
*
* @return int	value of the number in the cell
*/
	public int getNr() {
		return number;
	}

/**
* Returns true if the cell is set originally and the value on in can not be changed.
*
* @return boolean	True if the cell has a set value, else false.
*/
	public boolean isSet() {
		return isSet;
	}
/**
* Increments the value of the cell.
*
*/
	public void incr() {
		if (number == Brett.SIZE) number = 0;
		else number++;
	}

/**
* Dicrements the value of the cell.
*
*/
	public void decr() {
		if (number == 0) number = Brett.SIZE;
		else number--;
	}

/**
* Returns the row where the cell is located.
*
* @return Rad	the row object where the cell is located.
*/
	public Rad getRow() {
		return row;
	}

/**
* Returns the column where the cell is located.
*
* @return Kolonne	the column object where the cell is located.
*/

	public Kolonne getColumn() {
		return column;
	}

/**
* Returns the box where the cell is located.
*
* @return Boks	the box object where the cell is located.
*/
	public Boks getBox() {
		return box;
	}

/**
* Sets the row where the cell is located.
*
* @param Rad	Row where the cell is located.
*/
	public void setRow(Rad row) {
		this.row = row;
	}

/**
* Sets the column where the cell is located.
*
* @param Kolonne	Column where the cell is located.
*/
	public void setColumn(Kolonne column) {
		this.column = column;
	}

/**
* Sets the box where the cell is located.
*
* @param Boks	Box where the cell is located.
*/
	public void setBox(Boks box) {
		this.box = box;
	}

/**
* Sets the pointer next to point to the next empy cell on the board.
*
* @param Rute	Cell-object that is next empty to the current empty cell.
*/

	public void setNext(Rute cell) {
		next = cell;
	}

	public void setNr(int number) {
		this.number = number;
	}

/**
* Calculates all the possible values that the cell can have.
*
* @return int[]	Array with the possible values.
*/
	public int[] finnAlleMuligeTall() {
		if (!isSet) {	
			ArrayList<Integer> rowsNumbers = row.getAllFor(this);
			ArrayList<Integer> columnsNumbers = column.getAllFor(this);
			ArrayList<Integer> boxsNumbers = box.getAllFor(this);

			ArrayList<Integer> possibleNumbersPool = new ArrayList<Integer>();
			ArrayList<Integer> actualNumbers = new ArrayList<Integer>();
			int x = 1;
			while (x <= box.getSize()) {
				possibleNumbersPool.add(x);
				x++;
			}

			for (int i = 0; i < possibleNumbersPool.size(); i++) {
				if (!(rowsNumbers.contains(possibleNumbersPool.get(i)) || columnsNumbers.contains(possibleNumbersPool.get(i)) || boxsNumbers.contains(possibleNumbersPool.get(i)))) {
					actualNumbers.add(possibleNumbersPool.get(i));
				}
			}

			int[] allThePossibleNumbers = new int[actualNumbers.size()];
			for (int i = 0; i < allThePossibleNumbers.length; i++) {
				allThePossibleNumbers[i] = actualNumbers.get(i);
			}
			return allThePossibleNumbers;
		}else{
			System.out.println("The value of the cell is set by board:");
			int[] theNumber = {number};
			return theNumber;
		}
	}

/**
* Finds out the value of the cell and all other cells.
*
*
*/
	public void fyllUtDenneOgResten() {
		boolean oneSolution = false;
		if (SudokuBeholder.amountFoundSolutions < 3500) {
			int[] possibleSolutions = finnAlleMuligeTall();
			if (next == null) {
				if (possibleSolutions.length != 1) {
					System.out.println("END!");

				}else{
					this.number = possibleSolutions[0];
					System.out.println("Solution found!");
					Solution solution = new Solution(board.getBoard());	
					board.getSudoku().settInn(solution);
					this.number = 0;
				}
			}else{
				if (possibleSolutions.length == 0) {
					return;
				}else{
					for (int i = 0; i < possibleSolutions.length; i++) {
						this.number = possibleSolutions[i];
						next.fyllUtDenneOgResten();
					}
					this.number = 0;
				}
			}
		}
	}

	//Test method to print ot the info about the cell.
	// public void printInfo() {
	// 	System.out.println("The value of the cell is: " + number);
	// 	System.out.println("It is located in row " + row.getID());
	// 	System.out.println("in column " + column.getID());
	// 	System.out.println("and in box " + box.getID());
	// 	if (!isSet) System.out.println("Possible solutions: ");
	// 		for (int i : finnAlleMuligeTall()) {
	// 			System.out.print(i + " ");
	// 		}
	// 		System.out.println();
	// 	System.out.println("------");
	// }

}