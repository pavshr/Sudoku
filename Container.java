import java.util.ArrayList;

public class Container {
	int id;
	Rute [] cells;

	Container(int id, Rute[] arr) {
		this.id = id;
		cells = arr;
	}

/**
* Returns rows id.
*
* @return int	Rows id number.
*/
	public int getID() {
		return id;
	}
}

class Rad extends Container {

	Rad(int id, Rute[] arr) {
		super(id, arr);
	}

/**
* Initializes the value of the row for all the cells in this row
*
* @param Rad	This row-object.
*/
	public void initRow() {
		for (Rute r: cells) {
			r.setRow(this);
		}
	}
/**
* Returns all the numbers in the row without the one that is sent as parameter.
*
* @param int	The number that doesn't have to be returned.
* @return ArrayList<Integer>	Array of all the numbers in the row except parameter.
*/
	public ArrayList<Integer> getAllFor(Rute r) {
		
		ArrayList<Integer> fixedNrs = new ArrayList<Integer>();

		for (int i = 0; i < cells.length; i++) {
			if (cells[i].getNr() != 0) {
				fixedNrs.add(cells[i].getNr());
			}
		}

		return fixedNrs;
	}
}

class Kolonne extends Container {

	Kolonne(int id, Rute[] arr) {
		super(id, arr);
	}
/**
* Returns columns id.
*
* @return int	Columns id number.
*/
	public int getID() {
		return id;
	}

/**
* Initializes the value of the column for all the cells in this column.
*
* @param Kolonne	This column-object.
*/
	public void initColumn() {
		for (Rute r: cells) {
			r.setColumn(this);
		}
	}
/**
* Returns all the numbers in the column without the one that is sent as parameter.
*
* @param int	The number that doesn't have to be returned.
* @return ArrayList<Integer>	Array of all the numbers in the row except parameter.
*/
	public ArrayList<Integer> getAllFor(Rute r) {

		ArrayList<Integer> fixedNrs = new ArrayList<Integer>();

		for (int i = 0; i < cells.length; i++) {
			if (cells[i].getNr() != 0) {
				fixedNrs.add(cells[i].getNr());
			}
		}

		return fixedNrs;
	}
}

class Boks extends Container {

	private int rows;
	private int columns;

	Boks(int id, int rows, int columns) {
		super(id, new Rute[rows*columns]);
		this.rows = rows;
		this.columns = columns;
	}
/**
* Returns the amount of cells in one box.
*
* @return int	Number of cells in the box.
*/
	public int getSize() {
		return cells.length;
	}

/**
* Adds an element in the box on the first place available (not null) place.
*
*/
	public void add(Rute cell) throws BoxIsFullException {
		for (int i = 0; i < cells.length; i++) {
			if (cells[i] == null) {
				cells[i] = cell;
				return;
			}
		}
		throw new BoxIsFullException();
	}

/**
* Initializes the value of the box for all the cells in this box.
*
* @param Boks	This box-object.
*/
	public void initBox() {
		for (Rute r: cells) {
			r.setBox(this);
		}
	}
/**
* Returns all the numbers in the box without the one that is sent as parameter.
*
* @param int	The number that doesn't have to be returned.
* @return ArrayList<Integer>	Array of all the numbers in the row except parameter.
*/
	public ArrayList<Integer> getAllFor(Rute r) {

		ArrayList<Integer> fixedNrs = new ArrayList<Integer>();

		for (int i = 0; i < cells.length; i++) {
			if (cells[i].getNr() != 0) {
				fixedNrs.add(cells[i].getNr());
			}
		}

		return fixedNrs;
	}

/* Test method.
	public void printBox() {
		for (Rute r: cells) {
			System.out.println(r.getNr());
		}
	}
*/
}
