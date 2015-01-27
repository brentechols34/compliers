package scan;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.nio.charset.UnsupportedCharsetException;


public class Dispatcher {

	private PushbackReader reader;

	public Dispatcher(String fName){
		try {
			reader = new PushbackReader(new FileReader(fName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
	}

	public String run() throws IOException{
		char temp;
		
		do {
			int r = reader.read();
			if (r == -1) return null;
			temp = Character.toChars(r)[0];
		} while(Character.isWhitespace(temp));
		reader.unread(temp);
		for(State s: State.values()){
			if(s.matches(temp)){
				System.out.println(s.name());
				return s.run(reader);
			}
		}
		return null;
	}
}
