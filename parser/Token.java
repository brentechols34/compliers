package parser;

public class Token {
	public TokenType type;
	public String val;
	public Token(TokenType type, String val) {
		this.type = type;
		this.val = val;
	}
}
