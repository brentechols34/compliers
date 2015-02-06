package DFA;

import java.io.FileNotFoundException;

public class Stuff {
	private static String integer = "a:(\\d)b b<<:(\\d)b";
	private static String fixed = "a:(\\d)b b:(\\d)b,(.)c c:(\\d)d d<<:(\\d)d";
	private static String fp = "a:(\\d)b b:(\\d)b,(.)c,(eE)d c:(\\d)e d:(+-)f,(\\d)g e:(\\d)e,(eE)d f:(\\d)g g<<:(\\d)g";
	private static String identifier = "a:(\\a)c,(_)b b:(\\a\\d)c c<<:(_)d,(\\a\\d)c d:(\\a\\d)c";
	private static String unclosedComment = "a:({)b b:(\\a\\s)b,(\\$)c c<<:()c";
	private static String comment = "a:({)b b:(\\a\\s\\d)b,(})c c<<:()";
	private static String stringLiteral = "a:(')b b:(\\a\\d\\s)b,(')c c<<:(')b";
	
	private static String[] reserved = new String[] {"and", "begin", "Boolean", "div", "do", "downto", "else", "end", "false", "fixed", "float", "for", "function", "if", "integer", "mod", "not", "or", "procedure", "program", "read", "repeat", "string", "then", "true", "to", "type", "until", "var", "while", "write", "writeln"};
	public static void main(String[] args) throws FileNotFoundException {
		String[] stuff = new String[] {integer,fixed,fp,identifier, unclosedComment, comment, stringLiteral};
		String[] names = new String[] {"integer","fixed","fp","identifier", "unclosedComment", "comment", "stringLiteral"};
		Generator.generate(names, stuff, reserved);
	}
}
