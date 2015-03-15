package scanner;
import util.TokenType;
public class integer implements DFAS { 
	public integer() {
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
		case '0': return "b";
		case '1': return "b";
		case '2': return "b";
		case '3': return "b";
		case '4': return "b";
		case '5': return "b";
		case '6': return "b";
		case '7': return "b";
		case '8': return "b";
		case '9': return "b";
		default: return null;
		}
	}

	public static String b(char c) {
		switch(c) {
		case '0': return "b";
		case '1': return "b";
		case '2': return "b";
		case '3': return "b";
		case '4': return "b";
		case '5': return "b";
		case '6': return "b";
		case '7': return "b";
		case '8': return "b";
		case '9': return "b";
		default: return null;
		}
	}

	public boolean isAccepting() {
		return currentState != null && currentState.equals("b");
	}
	
	public TokenType getType(String lexeme) {
		return TokenType.MP_INTEGER_LIT;
	}

}