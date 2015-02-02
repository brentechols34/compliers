package scan2;

public class Token {
	public Regex type;
	public String val;
	public Token(Regex type, String val) {
		this.type = type;
		this.val = val;
	}
}
