package DFA;

import java.io.PrintWriter;

public class Generator {

	public static void generate(String[] names, String[] strs, String[] reserved) {
		String DFAS = DFAS();
		String master = master(names,strs, reserved);
		String token = token();
		String dr = DoubleReader();
		try {
			PrintWriter ms = new PrintWriter("out/Master.java");
			ms.print(master);
			ms.close();
			ms = new PrintWriter("out/DFAS.java");
			ms.print(DFAS);
			ms.close();
			ms = new PrintWriter("out/Token.java");
			ms.print(token);
			ms.close();
			ms = new PrintWriter("out/DoubleReader.java");
			ms.print(dr);
			ms.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String master(String[] names, String[] strs, String[] reserved) {
		DFA[] dfas = new DFA[strs.length];
		String[] files = new String[strs.length];
		for (int i = 0; i < strs.length; i++) { //generate a dfa for each of these
			dfas[i] = Str2DFA.String2DFA(strs[i]);
			files[i] = Str2DFA.DFA2Class(dfas[i], names[i]); //generate class file
			try {
				PrintWriter pw = new PrintWriter("out/"+names[i]+ ".java");
				pw.print(files[i]);
				pw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		//now we gotta generate a master class file

		String master = "\nimport java.io.IOException;\n\n";
		master += "public class Master {\n"
				+ "\tpublic static Token nextToken(DoubleReader pr) throws IOException {\n";
		String allStates = "\t\tDFAS[] allStates = new DFAS[] {";
		String notRejected = "\t\tboolean[] notRejected = new boolean[] {";
		String wereNotRejected = "\t\tboolean[] wereNotRejected = new boolean[] {";
		String wereAccepting = "\t\tboolean[] wereAccepting = new boolean[] {";
		boolean b = false;
		for (String s : names) {
			if (b) {
				allStates += ", ";
				notRejected += ", ";
				wereNotRejected += ", ";
				wereAccepting += ", ";
			}
			else b = !b;
			allStates += "new " + s + "()";
			notRejected += "true";
			wereNotRejected += "true";
			wereAccepting += "true";
		}
		master+=allStates+"};\n";
		master+= notRejected+"};\n";
		master+= wereNotRejected+"};\n";
		master+= wereAccepting+"};\n";
		master+="\t\tint notRejected_count = " + strs.length + ";\n";
		master+= "\t\tString s = \"\";\n";
		master += "\t\tint r=0;\n";
		master += "\t\tchar c=0;\n";
		master += theFunction(names.length);
		master += "\n" + reservedSwitch(reserved) + "\n";
		master+="\t}";
		return master;
	}
	
	private static String reservedSwitch(String[] reserved) {
		String s = "public static boolean isReserved(String s) {\n";
		s+= "switch(s) {";
		for (String r : reserved) {
			s += "case \"" + r + "\":\n";
		}
		s += "return true;\n";
		s += "default: return false;\n";
		s += "}\n}\n";
		return s;
	}
	
	private static String theFunction(int count) {
		String the = "while(true){\nr=pr.read();\nif(r==-1)return null;\nc=Character.toChars(r)[0];\nif(!Character.isWhitespace(c)){\npr.unread(c);\nbreak;\n}\n}";
		the+= "int col = pr.col;\n";
		the+= "int row = pr.row;\n";
		the+= "while (notRejected_count > 0) {\n";
		the+= "for (int i = 0; i < allStates.length; i++) {wereNotRejected[i] = notRejected[i]; if (wereNotRejected[i]) wereAccepting[i] = allStates[i].isAccepting();}\n";
		the+= "r = pr.read();\n";
		the+= "if (r == -1) {\n"; //if we have reached the end of file
		the+= "break;\n}\n";
		the+="c = Character.toChars(r)[0];\n";
		the+= "s+=c;\n";
		the+="for (int i = 0; i < allStates.length; i++) {\n";
		the+= "if (notRejected[i] && allStates[i].rejected(c)) {\n";
		the+= "notRejected[i] = false; notRejected_count--;\n";
		the+= "}\n}\n";
		the+= "}\n";
		the+= "if (r!=-1) {s = s.substring(0,s.length()-1);";
		the+= "pr.unread(r);}\n";
		the+= "for (int i = 0; i < allStates.length; i++) {\n";
		the+= "if (wereNotRejected[i] && wereAccepting[i]) return new Token(s, allStates[i], isReserved(s), col, row);\n";
		the+= "}\n";
		the+= "if (r==-1) return null;\n";
		the+= "return Token.ERROR;\n}\n";
		
		return the;
	}
	
	public static String token() {
		String token = "";
		token+= "public class Token {\n"
				+ "\tpublic String val;\n"
				+ "\tpublic DFAS type;\n"
				+ "\tpublic boolean isReserved;\n"
				+ "\tpublic int col;\n"
				+ "\tpublic int row;\n"
				+ "\tpublic final static Token ERROR = new Token(\"\",null,true,-1,-1);\n"
				+ "\tpublic Token(String val, DFAS type, boolean isReserved, int col, int row) {\n"
				+ "\t\tthis.val=val;\n"
				+ "\t\tthis.type=type;\n"
				+ "\t\tthis.isReserved=isReserved;\n"
				+ "\t\tthis.row=row;\n"
				+ "\t\tthis.col=col;\n"
				+ "\t}\n}";
		return token;			
	}
	
	public static String DoubleReader() {
		String r = "import java.io.FileNotFoundException;\n"
				+ "import java.io.FileReader;\n"
				+ "import java.io.IOException;\n"
				+ "import java.io.PushbackReader;\n"
				+ "public class DoubleReader {"
				+ "\tprivate PushbackReader pr;\n"
				+ "\tpublic int row;\n"
				+ "\tpublic int col;\n"
				+ "public DoubleReader(String fname) throws FileNotFoundException {\n"
				+ "\tthis.pr = new PushbackReader(new FileReader(fname));"
				+ "\tthis.row=0;"
				+ "\tthis.col=0;"
				+ "}"
				+ "public int read() throws IOException {\n"
				+ "\tint r = pr.read();\n"
				+ "\tif(r==-1) return -1;"
				+ "\tchar c = Character.toChars(r)[0];\n"
				+ "\tif (c== \'\\n\') row++;\n"
				+ "else col++;\n"
				+ "return r;\n"
				+ "}"
				+ "public void unread(int r) throws IOException {\n"
				+ "\tpr.unread(r);\n"
				+ "\tchar c = Character.toChars(r)[0];\n"
				+ "\tif (c== \'\\n\') row--;\n"
				+ "else col--;\n"
				+ "}"
				+ "}";
		return r;
	}
	
	public static String DFAS() {
		String DFAS = "";
		DFAS += "public interface DFAS { \n"
				+ "\tpublic boolean rejected(char c);\n"
				+ "\tpublic boolean isAccepting();"
				+ "}";
		return DFAS;
	}
}
