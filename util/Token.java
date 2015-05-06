package util;

public class Token {
	public TokenType type;
	public String val;
	public int col;
	public int row;
	public Token(TokenType type, String val, int col, int row) {
		this.type = type;
		this.val = val;
		this.col = col;
		this.row = row;
	}

    public Token(Token token) {
        this.type = token.type;
        this.val = token.val;
        this.col = token.col;
        this.row = token.row;
    }
	
	public String toString() {
		return "{" + type + ": " + val + ", (" + (1+col) + ", " + (1+row) + ")}"; 
	}
}
