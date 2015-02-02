package scan2;

import java.io.IOException;
import java.io.PushbackReader;

enum Regex {
	intRegex("\\d+"),
	fixedRegex("\\d+" + "\\.\\d+"),
	fpRegex("(\\d+|\\d+\\.\\d+)[eE][\\-\\+]?\\d+"),
	identifierRegex("([a-zA-Z]|_\\w)(_?[\\w])*"),
	strRegex("'(''|[^'])*.?"),
	opRegex("(:=?)|,|=|/|(>=?)|(<=?)|\\(|\\)|<|>|<>|\\-|\\.|\\+|;|\\*"),
	commentRegex("\\{([^\\}])*?[\\}]?"),
	reserved("");

	String myRegex;
	Regex(String myRegex) {
		this.myRegex = myRegex;
	}
	boolean matches(String s) {
		return (s.matches(myRegex));
	}
	String scan(PushbackReader r,String already) throws IOException {
		int i = r.read();
		char c = Character.toChars(i)[0];
		StringBuilder sb = new StringBuilder(already+c);
		while(matches(sb.toString())) {
			i = r.read();
			if (i==-1) return sb.toString();
			c = Character.toChars(i)[0];
			sb.append(c);
		}
		r.unread(i);
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
}
