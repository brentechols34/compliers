package scanner;

import util.TokenType;

public class comment implements DFAS { 
	public comment() {
		currentState = "a";
	}
	private String currentState;

	public boolean rejected(char c) {
		switch(currentState) {
		case "a": currentState = a(c); break;
		case "b": currentState = b(c); break;
		case "c": currentState = c(c); break;
		}
		if (currentState == null) return true;
		return false;
	}
	public static String a(char c) {
		switch(c) {
		case '{': return "b";
		default: return null;
		}
	}

	public static String b(char c) {
		switch(c) {
		case '}': return "c";
		
		default: return "b";
		}
	}

	public static String c(char c) {
		switch(c) {
		default: return null;
		}
	}

	public boolean isAccepting() {
		return currentState != null && currentState.equals("c");
	}
	
	public TokenType getType(String s) {
		return TokenType.MP_COMMENT;
	}

}