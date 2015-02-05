package DFA;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class Str2DFA {
	//format is
	//The first node is the start node
	//1.the name of your node
	//followed by a colon
	//2. followed by an open paren
	//followed by all characters that connect to a specific node
	//followed by a close paren
	//followed by that node's name
	//IF THERE ARE MORE EDGES FOR THIS NODE followed by a comma GO TO STEP 2 DUMBASS
	//OTHERWISE IF THERE ARE MORE NODES GO TO STEP 1
	//OTHERWISE YOU BE DUN
	//don't fuck up

	/**
	 * Converts a string representing a DFA into a DFA
	 * I was going to comment this, and then I realized that I had no idea how it worked
	 * Good luck I guess
	 * @param s
	 * @param start
	 * @return
	 */
	public static DFA String2DFA(String s) {
		String[] nodes = s.split(" "); //Split on spaces
		int count = 0;
		HashMap<String, Integer> graph = new HashMap<>();
		String t, to;
		String[] edges;
		boolean accepting;
		HashMap<Integer, Node> forDFA = new HashMap<Integer, Node>();
		String first_name = null;
		for (String str : nodes) {
			int nameDex = str.indexOf(':');
			String name = str.substring(0, nameDex);
			accepting =  (name.length() > 1 && name.substring(name.length() - 2, name.length()).equals("<<"));
			if (accepting) {
				name = name.substring(0,name.length() - 2);
			}
			if (first_name == null) first_name = name;
			if (!graph.containsKey(name)) {
				graph.put(name,count++);
			}
			t = str.substring(nameDex+1, str.length());
			edges = t.split("(?<!\\\\),"); //lol
			HashMap<Character, Integer> ed = new HashMap<>();
			for (String e : edges) {
				int close = e.lastIndexOf(")")+1;
				to = e.substring(close, e.length()); //this is where the edge goes
				if (!graph.containsKey(to)) {
					graph.put(to,count++);
				}
				char[] t2 = e.substring(1,close-1).toCharArray(); //these are the chars we need
				for (int i = 0; i < t2.length; i++) {
					if (t2[i]=='\\') {
						i++;
						switch(t2[i]) {
						case 'a': {
							for (char c2 : "abcedfghijklmopqrstuvwxyz".toCharArray()) {
								ed.put(c2, graph.get(to));
							}
							break;
						}
						case 'd': {
							for (char c2 : "0123456789".toCharArray()) {
								ed.put(c2, graph.get(to));
							}
						}
						default: {ed.put(t2[i], graph.get(to));}
						}

					} else {ed.put(t2[i], graph.get(to));}
				}

			}
			Node n =  new Node(name, ed, accepting);
			forDFA.put(graph.get(name), n);

		}
		return new DFA(forDFA.get(graph.get(first_name)), forDFA);
	}

	/**
	 * So the general gist is that we have a class variable representing the state we are in
	 * Then we have a run function that accepts a string
	 * and it calls a function dependent on the class variable for each character it reads.
	 * we'll scan through the dfa and get all possible states and make a big ol switch
	 * @param dfa
	 * @param className
	 * @return
	 */
	public static String DFA2Class(DFA dfa, String className) {
		Node[] allStates = dfa.getStates();

		ArrayList<Node> accepting = new ArrayList<>();
		for (Node n : allStates) {
			if (n.accepting) accepting.add(n);
		}
		String header = "public class " + className + " implements DFAS { \n";
		String vars = "private String currentState;\n\n";
		String constr = "public " + className + "() {\n"
				+ "currentState = \"" + dfa.start.name +"\";\n}\n";
		String func = "public boolean rejected(char c) {\n"
				+ "\t switch(currentState) {\n";
		for (Node n : allStates) {
			func += "\t\tcase " + "\"" + n.name + "\"" + ": currentState = " + n.name +"(c); break;\n";
		}
		func += "\t}\n\tif (currentState == null) return true;\n";
		func += "\treturn false;\n}\n";
		for (Node n : allStates) {
			func+= nodeToFunc(dfa,n)+"\n\n";
		}
		func += "public boolean isAccepting() {\n";
		func += "return currentState != null && ";
		boolean m = false;
		for (Node n : accepting) {
			if (m) func += " || ";
			else m = true;
			func += "currentState.equals(\""+n.name+"\")";
		}
		func += ";\n}\n\n";
		func+="}";
		return header + constr + vars +func;
	}
	/**
	 * (";
		boolean m = false;
		for (Node n : accepting) {
			if (m) func += " || ";
			else m = true;
			func += "currentState.equals(\""+n.name+"\")";
		}
		func += ");\n}\n\n";
	 *
	 *
	 *
	 *
	 */


	private static String nodeToFunc(DFA dfa, Node n) {
		String funcHeader = "public static String " + n.name + "(char c) {";
		String swtch = "switch(c) {\n";
		HashMap<Character, Integer> edges = n.edges;
		for (char c : edges.keySet()) {
			swtch+= "case " + "'" + c + "'" + ": return " + "\"" + dfa.nodes.get(edges.get(c)).name + "\";\n";
		}
		swtch += "default: return null;";
		return funcHeader + "\n" + swtch + "\n}\n" + "}";
	}

	public static void make(String format, String fname) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(fname + ".java");
		DFA d = String2DFA(format);
		String clazz = DFA2Class(d, fname);
		pw.print(clazz);
		pw.close();
	}
}
