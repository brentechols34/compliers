package scanner;
 import util.TokenType;
public class equals implements DFAS { 
	public equals() {
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
		case '=': return "b";
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
		return TokenType.MP_EQUAL;
	}
}