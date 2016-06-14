import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.StringIndexOutOfBoundsException;
import java.io.PrintWriter;

class FileOperator {
	
	private static char TOM_RUTE_TEGN = '.';
	private static int rowsInBox = 0;
	private static int columnsInBox = 0;

	// public static void main(String[] args) throws FileNotFoundException, IncorrectCharacterInFile, IllegalValueIntervalException, IncorrectAmountOfSymbols, TheBoardIsToLargeException{
	// 	Brett board = lesFil(args[0]);
	// 	board.opprettDatastruktur();
	// 	board.solve();
	// 	Rute[][] doubleArrayBoard = board.getBoard();
	// 	//printOut(doubleArrayBoard);
	// 	System.out.println("Sudoku size: " + board.getSudoku().hentAntallLosninger());
		
	// 	PrintWriter out = new PrintWriter(new File(args[1]));
	// 	while (SudokuBeholder.counter != board.getSudoku().hentAntallLosninger()) {
	// 	//	printOut(board.getSudoku().taUt().getASolution2D());
	// 		out.println((SudokuBeholder.counter + 1) + ": " + board.getSudoku().taUt().getSolutionForOutput());
	// 	}
	// 	out.close();

	// 	// Test loop to print out solutions in terminal.
	// 	// while (SudokuBeholder.counter != board.getSudoku().hentAntallLosninger()) {
	// 	// 	printOut(board.getSudoku().taUt().getASolution2D());
	// 	// 	System.out.println(board.getSudoku().taUt().getSolutionForOutput());
	// 	// }
	// }
/**
* Reads information about the board from a file.
*
* @param String	Name of the file with the info about the board.
*/
	public static Brett lesFil(File file) throws FileNotFoundException, IncorrectCharacterInFile, IllegalValueIntervalException, IncorrectAmountOfSymbols, TheBoardIsToLargeException{
		Scanner read = new Scanner(file);
		rowsInBox = Integer.parseInt(read.nextLine());
		columnsInBox = Integer.parseInt(read.nextLine());
		if (rowsInBox > 8 || columnsInBox > 8) {
			throw new TheBoardIsToLargeException();
		}
		int LENGTH = rowsInBox * columnsInBox;
		
		Brett board = new Brett(rowsInBox, columnsInBox);

		int i = 0;
		while(i < LENGTH) {
			String row = read.nextLine();
			Rute[] cellArray = new Rute[LENGTH];
			if (row.length() > LENGTH) throw new IncorrectAmountOfSymbols();
			for (int j = 0; j < LENGTH; j++) {
				cellArray[j] = new Rute(tegnTilVerdi(row.charAt(j)), board);
				if (cellArray[j].getNr() < 0 || cellArray[j].getNr() > LENGTH) {
					throw new IllegalValueIntervalException();
				}
			}
			board.fillIn(cellArray, i);
			i++;
		}
		return board;
	}

/**
* Prints out the board on the screen.
*
* @param Rute[][]	Two-dimensional array of cells.
*/
	public static void printOut(Rute[][] board) {

		int tmpBox = 0;
		
		try{
			for (int i = 0; i < board.length; i++) {
		
				int c1;
				int c2 = 0;
		
				if (i%rowsInBox == 0) {
					c1 = i;
					tmpBox = i;

					if (i != 0) {
						int a = 0;
						int b = 0;
						System.out.println();
						while (b <= rowsInBox - 1) {
							while (a < columnsInBox) {
								System.out.print("-");
								a++;
							}
							b++;
							if (b <= rowsInBox - 1) System.out.print("+");
							a = 0;

						}
					}
				}else{
					c1 = tmpBox;
				}

				System.out.println();
				for (int j = 0; j < board[i].length; j++) {
					if (c2 < columnsInBox) {
						System.out.print(verdiTilTegn(board[i][j].getNr(), ' '));
						c2++;
					} else if (c2 == columnsInBox) {
						c2 = 0;
						System.out.print("|");
						c1++;
						System.out.print(verdiTilTegn(board[i][j].getNr(), ' '));
						c2++;					
					}
				}
			}
			System.out.println();
		}catch (UgyldigVerdiUnntak e) {
			System.out.println("Error! Incorrect char!");
		}
	}

/**
* Prints out the board on the screen.
*
* @param Rute[][]	Two-dimensional array of cell-values.
*/
	public static void printOut(int[][] board) {

		int tmpBox = 0;
		
		try{
			for (int i = 0; i < board.length; i++) {
		
				int c1;
				int c2 = 0;
		
				if (i%rowsInBox == 0) {
					c1 = i;
					tmpBox = i;

					if (i != 0) {
						int a = 0;
						int b = 0;
						System.out.println();
						while (b <= rowsInBox - 1) {
							while (a < columnsInBox) {
								System.out.print("-");
								a++;
							}
							b++;
							if (b <= rowsInBox - 1) System.out.print("+");
							a = 0;

						}
					}
				}else{
					c1 = tmpBox;
				}

				System.out.println();
				for (int j = 0; j < board[i].length; j++) {
					if (c2 < columnsInBox) {
						System.out.print(verdiTilTegn(board[i][j], ' '));
						c2++;
					} else if (c2 == columnsInBox) {
						c2 = 0;
						System.out.print("|");
						c1++;
						System.out.print(verdiTilTegn(board[i][j], ' '));
						c2++;					
					}
				}
			}
			System.out.println();
		}catch (UgyldigVerdiUnntak e) {
			System.out.println("Error! Incorrect char!");
		}
	}

/**
* Prints all the solutions to a given file.
*
* @param String	File name.
*/
	public void saveToFile(String filename) throws FileNotFoundException{

	}

/**
* Help method to get the int parameter of the board, ex: rows, columns, cells in a box,
* based on the provided parameter to the method.
* @param String	"row", "col"
* @return int	rowsInBox, columnsInBox.
*/
	public int getParameter(String key) {
		if (key.equals("row")) {
			return rowsInBox;
		} else if (key.equals("col")) {
			return columnsInBox;
		} else {
			return -1;
		}
	}

// imported from http://heim.ifi.uio.no/inf1010/2016/oblig/8/filer/2hjelpemetoder.java
/**
 * Oversetter et tegn (char) til en tallverdi (int)
 *
 * @param tegn      tegnet som skal oversettes
 * @return          verdien som tegnet tilsvarer
 */
    public static int tegnTilVerdi(char tegn) throws IncorrectCharacterInFile {
        if (tegn == TOM_RUTE_TEGN) {                // tom rute, DENNE KONSTANTEN MAA DEKLARERES
            return 0;
        } else if ('1' <= tegn && tegn <= '9') {    // tegn er i [1, 9]
            return tegn - '0';
        } else if ('A' <= tegn && tegn <= 'Z') {    // tegn er i [A, Z]
            return tegn - 'A' + 10;
        } else if (tegn == '@') {                   // tegn er @
            return 36;
        } else if (tegn == '#') {                   // tegn er #
            return 37;
        } else if (tegn == '&') {                   // tegn er &
            return 38;
        } else if ('a' <= tegn && tegn <= 'z') {    // tegn er i [a, z]
            return tegn - 'a' + 39;
        } else {                                    // tegn er ugyldig
            throw new IncorrectCharacterInFile(tegn);
        }
    }

/**
 * Oversetter en tallverdi (int) til et tegn (char)
 *
 * @param verdi     verdien som skal oversettes
 * @param tom       tegnet som brukes for aa representere 0 (tom rute)
 * @return          tegnet som verdien tilsvarer
 */
    public static char verdiTilTegn(int verdi, char tom) throws UgyldigVerdiUnntak {
        if (verdi == 0) {                           // tom
            return tom;
        } else if (1 <= verdi && verdi <= 9) {      // tegn er i [1, 9]
            return (char) (verdi + '0');
        } else if (10 <= verdi && verdi <= 35) {    // tegn er i [A, Z]
            return (char) (verdi + 'A' - 10);
        } else if (verdi == 36) {                   // tegn er @
            return '@';
        } else if (verdi == 37) {                   // tegn er #
            return '#';
        } else if (verdi == 38) {                   // tegn er &
            return '&';
        } else if (39 <= verdi && verdi <= 64) {    // tegn er i [a, z]
            return (char) (verdi + 'a' - 39);
        } else {                                    // tegn er ugyldig
            throw new UgyldigVerdiUnntak(verdi);    // HUSK DEFINISJON AV UNNTAKSKLASSE
        }
    }

}