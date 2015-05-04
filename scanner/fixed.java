package scanner;
import util.TokenType;
public class fixed implements DFAS { 
	public fixed() {
		currentState = "a";
	}
	private String currentState;

	public boolean rejected(char c) {
		switch(currentState) {
		case "a": currentState = a(c); break;
		case "b": currentState = b(c); break;
		case "c": currentState = c(c); break;
		case "d": currentState = d(c); break;
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
		case '.': return "c";
		default: return null;
		}
	}

	public static String c(char c) {
		switch(c) {
		case '0': return "d";
		case '1': return "d";
		case '2': return "d";
		case '3': return "d";
		case '4': return "d";
		case '5': return "d";
		case '6': return "d";
		case '7': return "d";
		case '8': return "d";
		case '9': return "d";
		default: return null;
		}
	}

	public static String d(char c) {
		switch(c) {
		case '0': return "d";
		case '1': return "d";
		case '2': return "d";
		case '3': return "d";
		case '4': return "d";
		case '5': return "d";
		case '6': return "d";
		case '7': return "d";
		case '8': return "d";
		case '9': return "d";
		default: return null;
		}
	}
	
	public TokenType getType(String lexeme) {
		return TokenType.MP_FLOAT_LIT;
	}

	public boolean isAccepting() {
		return currentState != null && currentState.equals("d");
	}

}