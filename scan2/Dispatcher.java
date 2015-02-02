package scan2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.RandomAccessFile;

public class Dispatcher {
	PushbackReader f;
	public Dispatcher(String fname) {
		try {
			f = new PushbackReader(new FileReader(fname));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public Token run() throws IOException {
		int r = f.read();
		if (r == -1) return null;
		char c = Character.toChars(r)[0];

		while (Character.isWhitespace(c)) {
			r = f.read();
			c=Character.toChars(r)[0]; //scan out all whitespace
		}
		f.unread(r);
		Regex found = null;
		String s = "";
		for (Regex re : Regex.values()) {
			if (re.matches(""+c)) {
				found = re;
				s = re.scan(f,"");
				break;
			}
		}
		switch(found) {
		case intRegex: {
			r = f.read();
			c = Character.toChars(r)[0];
			if (c=='.') {
				s=Regex.fixedRegex.scan(f, s+c);
				found = Regex.fixedRegex;
				r = f.read();
				c = Character.toChars(r)[0];
				if (c=='e'||c=='E') {
					s = Regex.fpRegex.scan(f, s+c);
					found = Regex.fpRegex;
				} else {
					f.unread(r);
				}
			} else { 
				if (c=='e'||c=='E') {
					s = Regex.fpRegex.scan(f, s+c);
					found = Regex.fpRegex;
				} else f.unread(r);
			}
		}
		case identifierRegex: {
			if (isReservedWord(s)) {
				return new Token(Regex.reserved,s);
			}
		}
		case strRegex:
		case opRegex:
		case commentRegex:
			return new Token(found, s);
		default: {
			System.out.println("SHIT");
			System.exit(0);
			return null;
		}
		}

	}

	public boolean isReservedWord(String token) {
		switch (token) {
		case "and":
		case "begin":
		case "Boolean":
		case "div":
		case "do":
		case "downto":
		case "else":
		case "end":
		case "false":
		case "fixed":
		case "float":
		case "for":
		case "function":
		case "if":
		case "integer":
		case "mod":
		case "not":
		case "or":
		case "procedure":
		case "program":
		case "read":
		case "repeat":
		case "string":
		case "then":
		case "true":
		case "to":
		case "type":
		case "until":
		case "var":
		case "while":
		case "write":
		case "writeln":
			return true;
		default: return false;
		}
	}
}
