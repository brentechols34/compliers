package scanner;

import java.io.FileNotFoundException;
import java.io.IOException;

import util.Token;

public class Test {
	
	public static void main(String[] args) throws IOException, ScannerException {
		String fname = "test.txt";
		Scanner sc = new Scanner(fname);
		
		Token t = null;
		do {
			t = sc.nextToken();
			System.out.println(t);
		} while (t != null);
	}

}
