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
		case ' ': return "b";
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
		case 'A': return "b";
		case 'B': return "b";
		case 'C': return "b";
		case 'D': return "b";
		case 'E': return "b";
		case 'F': return "b";
		case 'G': return "b";
		case 'H': return "b";
		case 'I': return "b";
		case 'J': return "b";
		case 'K': return "b";
		case 'L': return "b";
		case 'M': return "b";
		case 'N': return "b";
		case 'O': return "b";
		case 'P': return "b";
		case 'Q': return "b";
		case 'R': return "b";
		case 'S': return "b";
		case 'T': return "b";
		case 'U': return "b";
		case 'V': return "b";
		case 'W': return "b";
		case 'X': return "b";
		case 'Y': return "b";
		case 'Z': return "b";
		case 'a': return "b";
		case 'b': return "b";
		case 'c': return "b";
		case 'd': return "b";
		case 'e': return "b";
		case 'f': return "b";
		case 'g': return "b";
		case 'h': return "b";
		case 'i': return "b";
		case 'j': return "b";
		case 'k': return "b";
		case 'l': return "b";
		case 'm': return "b";
		case 'n': return "b";
		case 'o': return "b";
		case 'p': return "b";
		case 'q': return "b";
		case 'r': return "b";
		case 's': return "b";
		case 't': return "b";
		case 'u': return "b";
		case 'v': return "b";
		case 'w': return "b";
		case 'x': return "b";
		case 'y': return "b";
		case 'z': return "b";
		case '}': return "c";
		default: return null;
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