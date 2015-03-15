package scanner;

import util.TokenType;

public interface DFAS { 
	public boolean rejected(char c);
	public boolean isAccepting();
	public TokenType getType(String lexeme);
}