package scanner;


import java.io.FileNotFoundException;
import java.io.IOException;

import util.Token;
import util.TokenType;

/**
 * Please be aware that this code was almost entirely autogenerated by a scanner tool we produced. The source for this can be found
 * in the DFA package
 * @author Alex
 *
 */
public class Scanner {
	DoubleReader pr;
	public Scanner(String fname) throws FileNotFoundException {
		pr = new DoubleReader(fname);
	}
	
	public Token nextToken() throws IOException, ScannerException {
		DFAS[] allStates = new DFAS[] {new integer(), new fixed(), new fp(), new identifier(), new unclosedComment(), new comment(), new stringLiteral(), new unclosedString(), new arith(), new equality(), new nequal(), new oparen(), new cparen(), new set(), new scolon(), new comma(), new period(), new colon(), new equals()};
		boolean[] notRejected = new boolean[] {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};
		boolean[] wereNotRejected = new boolean[] {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};
		boolean[] wereAccepting = new boolean[] {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};
		int notRejected_count = 19;
		String s = "";
		int r=0;
		char c=0;
		while(true){
			r=pr.read();
			if(r==-1)return null;
			c=Character.toUpperCase(Character.toChars(r)[0]);
			if(!Character.isWhitespace(c)){
				pr.unread(c);
				break;
			}
		} int col = pr.col;
		int row = pr.row;
		while (notRejected_count > 0) {
			for (int i = 0; i < allStates.length; i++) {wereNotRejected[i] = notRejected[i]; if (wereNotRejected[i]) wereAccepting[i] = allStates[i].isAccepting();}
			r = pr.read();
			if (r == -1) {
				break;
			}
			c = Character.toUpperCase(Character.toChars(r)[0]);
			s+=c;
			for (int i = 0; i < allStates.length; i++) {
				if (notRejected[i] && allStates[i].rejected(c)) {
					notRejected[i] = false; notRejected_count--;
				}
			}
		}
		s = s.replace("'", "\"");
		if (r!=-1) {s = s.substring(0,s.length()-1);pr.unread(r);}
		for (int i = 0; i < allStates.length; i++) {
			if (wereNotRejected[i] && wereAccepting[i]) return new Token(allStates[i].getType(s), s, col, row);
		}
		if (r==-1) return null;
		throw new ScannerException(s,r,c);
	}

}