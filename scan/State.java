package scan;

import java.io.PushbackReader;
import java.io.IOException;

public enum State {
	LETTER("[a-zA-Z](_?[\\w])*"),
	DIGIT ("\\d+(\\.\\d+)?"),
	LEFT_PAREN ("\\("),
	RIGHT_PAREN ("\\)"),
	EQUALITY ("([<>]=?)|=="),
	OPERATOR ("[\\+\\-\\*/]");
	
	private final String regex;
	
	private State(String regex){
		this.regex = regex;
		
	}
	
	public String run(PushbackReader s){
		StringBuilder token;
		try {
			char c = Character.toChars(s.read())[0];
			token = new StringBuilder(""+c);
			int r = -1;
			while(token.toString().matches(regex)){
				r = s.read();
				if (r==-1) break;
				token.append(Character.toChars(r)[0]);
			}
			if (r!=-1) {
				s.unread((char) r);
				token.deleteCharAt(token.length()-1);
			}
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