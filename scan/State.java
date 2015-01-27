package scan;

import java.util.Scanner;

public enum State {
	LETTER (""),
	DIGIT (""),
	LEFT_PAREN (""),
	RIGHT_PAREN (""),
	OPERATOR ("");
	
	private final String regex;
			
	private State(String regex){
		this.regex = regex;
	}
	
	public String run(Scanner s){
		return "";
	}
}