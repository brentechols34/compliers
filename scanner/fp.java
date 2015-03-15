package scanner;
import util.TokenType;
public class fp implements DFAS { 
	public fp() {
		currentState = "a";
	}
	private String currentState;

	public boolean rejected(char c) {
		switch(currentState) {
		case "a": currentState = a(c); break;
		case "b": currentState = b(c); break;
		case "c": currentState = c(c); break;
		case "d": currentState = d(c); break;
		case "e": currentState = e(c); break;
		case "f": currentState = f(c); break;
		case "g": currentState = g(c); break;
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
		case 'e': return "d";
		case 'E': return "d";
		case '.': return "c";
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

	public static String c(char c) {
		switch(c) {
		case '0': return "e";
		case '1': return "e";
		case '2': return "e";
		case '3': return "e";
		case '4': return "e";
		case '5': return "e";
		case '6': return "e";
		case '7': return "e";
		case '8': return "e";
		case '9': return "e";
		default: return null;
		}
	}

	public static String d(char c) {
		switch(c) {
		case '0': return "g";
		case '1': return "g";
		case '2': return "g";
		case '3': return "g";
		case '4': return "g";
		case '5': return "g";
		case '6': return "g";
		case '7': return "g";
		case '8': return "g";
		case '9': return "g";
		case '+': return "f";
		case '-': return "f";
		default: return null;
		}
	}

	public static String e(char c) {
		switch(c) {
		case '0': return "e";
		case '1': return "e";
		case '2': return "e";
		case '3': return "e";
		case '4': return "e";
		case '5': return "e";
		case 'e': return "d";
		case 'E': return "d";
		case '6': return "e";
		case '7': return "e";
		case '8': return "e";
		case '9': return "e";
		default: return null;
		}
	}

	public static String f(char c) {
		switch(c) {
		case '0': return "g";
		case '1': return "g";
		case '2': return "g";
		case '3': return "g";
		case '4': return "g";
		case '5': return "g";
		case '6': return "g";
		case '7': return "g";
		case '8': return "g";
		case '9': return "g";
		default: return null;
		}
	}

	public static String g(char c) {
		switch(c) {
		case '0': return "g";
		case '1': return "g";
		case '2': return "g";
		case '3': return "g";
		case '4': return "g";
		case '5': return "g";
		case '6': return "g";
		case '7': return "g";
		case '8': return "g";
		case '9': return "g";
		default: return null;
		}
	}

	public boolean isAccepting() {
		return currentState != null && currentState.equals("g");
	}
	public TokenType getType(String lexeme) {
		return TokenType.MP_FLOAT_LIT;
	}
}