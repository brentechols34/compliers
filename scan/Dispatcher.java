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
		int r = reader.read();
		if (r == -1) return null;
		temp = Character.toChars(r)[0];
		reader.unread(temp);
		for(State s: State.values()){
			if(s.matches(temp)){
				String ret = s.run(reader);
				System.out.println("(" + ret + ", " + s.name() + ")");
				return ret;
			}
		}
		return null;
	}
}
