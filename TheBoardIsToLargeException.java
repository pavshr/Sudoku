class TheBoardIsToLargeException extends Exception {
/**
* Returns the detail message string of this throwable.
*
* @param String	the detail message string of this Throwable instance (which may be null).
*/
	@Override
	public String getMessage() {
		return "The file you have chosen contains the board which is too large.";
	}
}