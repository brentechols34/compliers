package scan;

import java.io.IOException;


class Main {
	public static void main(String[]args){
		Dispatcher d = new Dispatcher("test.txt");
		while(true){
			try {
				String s = d.run();
				if (s == null) return;
				else System.out.println(s);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}