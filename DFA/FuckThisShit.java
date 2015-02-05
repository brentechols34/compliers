package DFA;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class FuckThisShit {
	//format is
	//name:(edge-letters)name,(edge-letters)name,....... name: .... name:....
	//a "<<" IMMEDIATELY AFTER a name makes that state accepting
	//don't fuck up
	
	static class Node {
		HashMap<Character, Integer> edges;
		boolean accepting;
		String name;
		public Node(String name, HashMap<Character, Integer> edges, boolean accepting) {
			this.name = name;
			this.edges = edges;
			this.accepting = accepting;
		}
		public Integer read(char c) {
			return edges.get(c);
		}
	}
	
	static class DFA {
		HashMap<Integer, Node> nodes;
		Node start;
		public DFA(Node start, HashMap<Integer, Node> nodes) {
			this.start = start;
			this.nodes = nodes;
		}
		public boolean matches(String s) {
			Node t = start;
			for (char c : s.toCharArray()) {
				t = nodes.get(t.read(c));
				if (t == null) return false;
			}
			return t.accepting;
		}
		
		public Node[] getStates() {
			Collection<Node> n = nodes.values();
			Node[] n2 = new Node[n.size()];
			int i = 0; 
			for (Node nr : n) {
				n2[i++] = nr;
			}
			return n2;
		}
	}
	
	public static final String alphabetic = "abcdefghijklmopqrstuvwxyz";
	
	public static DFA String2DFA(String s, String start) {
		String[] nodes = s.split(" ");
		System.out.println(Arrays.toString(nodes));
		int count = 0;
		HashMap<String, Integer> graph = new HashMap<>();
		String t, to;
		String[] edges;
		boolean accepting;
		HashMap<Integer, Node> forDFA = new HashMap<Integer, Node>();
		for (String str : nodes) {
			int nameDex = str.indexOf(':');
			String name = str.substring(0, nameDex);
			accepting =  (name.length() > 1 && name.substring(name.length() - 2, name.length()).equals("<<"));
			if (accepting) {
				name = name.substring(0,name.length() - 2);
			}
			if (!graph.containsKey(name)) {
				graph.put(name,count++);
			}
			t = str.substring(nameDex+1, str.length());
			edges = t.split("(?<!\\\\),");
			System.out.println(Arrays.toString(edges));
			HashMap<Character, Integer> ed = new HashMap<>();
			for (String e : edges) {
				int close = e.lastIndexOf(")")+1;
				to = e.substring(close, e.length()); //this is where the edge goes
				if (!graph.containsKey(to)) {
					graph.put(to,count++);
				}
				char[] t2 = e.substring(1,close-1).toCharArray(); //these are the chars we need
				System.out.println(Arrays.toString(t2));
				for (int i = 0; i < t2.length; i++) {
					if (t2[i]=='\\') {
						i++;
					}
					ed.put(t2[i], graph.get(to));
				}
				
			}
			Node n =  new Node(name, ed, accepting);
			System.out.println("Node: " + name + " " + ed.keySet().toString());
			forDFA.put(graph.get(name), n);
			
		}
		return new DFA(forDFA.get(graph.get(start)), forDFA);
	}
	
	
	
	
	public static void main(String[] args) {
		String NUMBER = "a:(0123456789)b b<<:(0123456789)b";
		try {
			make(NUMBER, "a", "Number");
		} catch (Exception e) { }
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
		String header = "public class " + className + " { \n";
		String vars = "private static String currentState;\n\n";
		String func = "public static boolean matches(String s) {\n"
				+ "for (char c : s.toCharArray()) {\n"
				+ "\t switch(currentState) {\n";
		for (Node n : allStates) {
			func += "case " + "\"" + n.name + "\"" + ": currentState = " + n.name +"(c); break;\n";
		}
		func += "if (currentState == null) return false;}\n}\n";
		func += "return (";
		boolean m = false;
		for (Node n : accepting) {
			if (m) func += " || ";
			else m = true;
			func += "currentState.equals(\""+n.name+"\")";
		}
		func += ");\n}\n\n";
		for (Node n : allStates) {
			func+= nodeToFunc(dfa,n)+"\n\n";
		}
		func+="}";
		return header + vars +func;
	}
	
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
	
	public static void make(String format, String startState, String fname) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(fname + ".java");
		DFA d = String2DFA(format, startState);
		String clazz = DFA2Class(d, fname);
		pw.print(clazz);
		pw.close();
	}
}
