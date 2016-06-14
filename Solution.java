class Solution {
	private int[][] solution;

	Solution(Rute[][] boardState) {
		solution = new int[boardState.length][boardState.length];
		for (int i = 0; i < solution.length; i++) {
			for (int j = 0; j < solution[i].length; j++) {
				solution[i][j] = boardState[i][j].getNr();
			}
		}
	}
/**
* Returns a solution as a 2D-array of integers that represent the values 
* the cells.
*
* @return int[][]	Two-dimensionsl array of integers.
*/
	public int[][] getASolution2D() {
		return solution;
	}

/**
* Returns a string representation of the solution that fits the file format for the output.
*
* @return String	String-line of the solution in form xxx//xxx//xxx//xxx//xxx//...
*/
	public String getSolutionForOutput() {
		String solutionSudoku = "";
		
		for (int i = 0; i < solution.length; i++) {
			String solutionInLine = "";
			for (int j = 0; j < solution[i].length; j++) {
				solutionInLine += solution[i][j];
			}
			solutionSudoku += solutionInLine + "//";
		}
		return solutionSudoku;
	}
}