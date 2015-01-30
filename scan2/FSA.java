package scan2;

import java.io.PushbackReader;

public interface FSA {
	public Token run(PushbackReader sc);

}
