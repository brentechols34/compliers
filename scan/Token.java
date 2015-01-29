package scan;

public class Token {
	public final String val;
	public final State type;
	public Token(String val, State type){
		this.type = type;
		this.val = val;
	}
	
}
