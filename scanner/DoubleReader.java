package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
public class DoubleReader {	
	private PushbackReader pr;
	public int row;
	public int col;
	private int old_col;
	public DoubleReader(String fname) throws FileNotFoundException {
		this.pr = new PushbackReader(new FileReader(fname));	
		this.row=0;	this.col=0; this.old_col = 0;
	}
	public int read() throws IOException {
		int r = pr.read();
		if(r==-1) return -1;	char c = Character.toChars(r)[0];
		if (c== '\n') {
			row++;
			old_col = col;
			col=0;
		}
		else col++;
		return r;
	}
	public void unread(int r) throws IOException {
		pr.unread(r);
		char c = Character.toChars(r)[0];
		if (c== '\n') {
			row--;
			col=old_col;
			old_col = 0;
		}
		else col--;
	}
}