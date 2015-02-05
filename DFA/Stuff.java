package DFA;

import java.io.FileNotFoundException;

public class Stuff {
	private static String integer = "a:(\\d)b b<<:(\\d)b";
	private static String fixed = "a:(\\d)b b:(\\d)b,(.)c c:(\\d)d d<<:(\\d)d";
	private static String fp = "a:(\\d)b b:(\\d)b,(.)c,(eE)d c:(\\d)e d:(+-)f,(\\d)g e:(\\d)e,(eE)d f:(\\d)g g<<:(\\d)g";
	private static String identifier = "a:(\\a)c,(_)b b:(\\a\\d)c c<<:(_)d,(\\a\\d)c d:(\\a\\d)c";
	
	public static void main(String[] args) throws FileNotFoundException {
		String[] stuff = new String[] {integer,fixed,fp,identifier};
		String[] names = new String[] {"integer","fixed","fp","identifier"};
		Generator.generate(names, stuff, "MicroPascal");
	}
}
