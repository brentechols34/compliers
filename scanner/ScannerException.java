package scanner;

public class ScannerException extends Exception {
	String lexeme;
	int row;
	int col;
	
	public ScannerException(String lexeme, int row, int col) {
		this.lexeme = lexeme;
		this.row = row;
		this.col = col;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
