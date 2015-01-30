package scan;

import java.io.IOException;


class Main {
	public static void main(String[]args){
		Dispatcher d = new Dispatcher("test.txt");
		while(true){
			try {
				Token t = d.run();
				System.out.println(t);
				if (t == null) return;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}