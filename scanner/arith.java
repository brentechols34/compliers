package scanner;

import util.TokenType;

public class arith implements DFAS { 
	public arith() {
		currentState = "a";
	}
	private String currentState;

	public boolean rejected(char c) {
		switch(currentState) {
		case "a": currentState = a(c); break;
		case "b": currentState = b(c); break;
		}
		if (currentState == null) return true;
		return false;
	}
	public static String a(char c) {
		switch(c) {
		case '*': return "b";
		case '+': return "b";
		case '-': return "b";
		case '/': return "b";
		default: return null;
		}
	}

	public static String b(char c) {
		switch(c) {
		default: return null;
		}
	}

	public boolean isAccepting() {
		return currentState != null && currentState.equals("b");
	}
	
	public TokenType getType(String lexeme) {
		switch (lexeme) {
		case "+": return TokenType.MP_PLUS;
		case "-": return TokenType.MP_MINUS;
		case "*": return TokenType.MP_TIMES;
		case "/": return TokenType.MP_FLOAT_DIVIDE;
		default: return null;
		}
	}

}