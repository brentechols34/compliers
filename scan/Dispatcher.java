package scan;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Arrays;
import java.util.HashSet;


public class Dispatcher {

	private PushbackReader reader;


	public Dispatcher(String fName){
		try {
			reader = new PushbackReader(new FileReader(fName));
		} catch (FileNotFoundException e) {
			System.out.println("NON EXISTENT FILE DUMBASS");
			e.printStackTrace();
			System.exit(0);
		}
	}

	public Token run() throws IOException{
		char temp;
		int r = reader.read();
		if (r == -1) return null;
		temp = Character.toChars(r)[0];
		reader.unread(temp);
		for(State s: State.values()){
			if(s.matches(temp)){
				String ret = s.run(reader);
				State t = (isReservedWord(ret))?State.RESERVED:s;
				return new Token(ret, t);
			}
		}
		return null;
	}

	//EVEN BETTER
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
