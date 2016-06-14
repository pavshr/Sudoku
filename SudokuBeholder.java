import java.util.ArrayList;

class SudokuBeholder {
	ArrayList<Solution> alWithSolutions = new ArrayList<Solution>();
	static int counter = 0;
	static int amountFoundSolutions = 0;

/**
* Adds a solution in the solution beholder.
*
* @param Solution	solution for the board.
*/
	public void settInn(Solution solution) {
		alWithSolutions.add(solution);
		amountFoundSolutions++;
	}
/**
* Takes out a next solution from an ArrayList.
*
* @return Solution	One of the solutions for sudoku.
*/
	public Solution taUt() {
		if (alWithSolutions.size() == 0) {
			System.out.println("There is no solutions!");
			return null;
		}
		else if (counter == amountFoundSolutions) {
			counter = 0;
			return alWithSolutions.get(counter);
		}else{
			counter++;
			return alWithSolutions.get(counter - 1);
		}
	}
/**
* Return ammout of the solutions for the sudoku.
*
* @return int	Number of solutions.
*/
	public int hentAntallLosninger() {
		return alWithSolutions.size();
	}
}