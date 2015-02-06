package DFA;

import java.io.PrintWriter;

public class Generator {

	public static void generate(String[] names, String[] strs, String[] reserved) {
		String DFAS = DFAS();
		String master = master(names,strs, reserved);
		String token = token();
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

		String master = "import java.io.PushbackReader;\nimport java.io.IOException;\n\n";
		master += "public class Master {\n"
				+ "\tpublic static Token nextToken(PushbackReader pr) throws IOException {\n";
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
		String the = "while (notRejected_count > 0) {\n";
		the+= "for (int i = 0; i < allStates.length; i++) {wereNotRejected[i] = notRejected[i]; wereAccepting[i] = allStates[i].isAccepting();}\n";
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
		the+= "if (r!=-1) {s = s.substring(0,s.length()-1);}";
		the+= "pr.unread(r);\n";
		the+= "for (int i = 0; i < allStates.length; i++) {\n";
		the+= "if (wereNotRejected[i] && wereAccepting[i]) return new Token(s, allStates[i], isReserved(s));\n";
		the+= "}\n";
		the+= "return Token.ERROR;\n}\n";
		
		return the;
	}
	
	public static String token() {
		String token = "";
		token+= "public class Token {\n"
				+ "\tpublic String val;\n"
				+ "\tpublic DFAS type;\n"
				+ "\tpublic boolean isReserved;\n"
				+ "\tpublic final static Token ERROR = new Token(\"\",null,true);\n"
				+ "\tpublic Token(String val, DFAS type, boolean isReserved) {\n"
				+ "\t\tthis.val=val;\n"
				+ "\t\tthis.type=type;\n"
				+ "\t\tthis.isReserved=isReserved;\n"
				+ "\t}\n}";
		return token;			
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
