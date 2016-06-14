class IncorrectCharacterInFile extends Exception {
	char character;

	IncorrectCharacterInFile(char character) {
		this.character = character;
	}
/**
* Returns the detail message string of this throwable.
*
* @param String	the detail message string of this Throwable instance (which may be null).
*/
	@Override
	public String getMessage() {
		return "The file you have chosen contains one or more incorrect characters.";
	}
}