package scan;

import java.io.PushbackReader;
import java.io.IOException;

public enum State {
	LETTER("[a-zA-Z](_?[\\w])*"),
	DIGIT ("\\d+(\\.\\d+)?"),
	LEFT_PAREN (""),
	RIGHT_PAREN (""),
	OPERATOR ("");
	
	private final String regex;
	
	private State(String regex){
		this.regex = regex;
		
	}
	
	public String run(PushbackReader s){
		StringBuilder token;
		try {
			char c = Character.toChars(s.read())[0];
			token = new StringBuilder(""+c);
			while(token.toString().matches(regex)){
				token.append(Character.toChars(s.read())[0]);
			}
			s.unread(token.charAt(token.length()-1));
			token.deleteCharAt(token.length()-1);
			return token.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public boolean matches(char c){
		return ("" + c).matches(regex);
	}
}