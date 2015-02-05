package DFA;

import java.util.Arrays;
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
	}
	
	public static final String alphabetic = "abcdefghijklmopqrstuvwxyz";
	public static final String numeric = "0123456789";
	
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
						ed.put(t2[i], graph.get(to));
					}
				}
				
			}
			Node n =  new Node(name, ed, accepting);
			System.out.println("Node: " + name);
			forDFA.put(graph.get(name), n);
			
		}
		return new DFA(forDFA.get(graph.get(start)), forDFA);
	}
	
	
	
	
	public static void main(String[] args) {
		String s = "a:("+alphabetic+")b b:(" + alphabetic +")a";
		String2DFA(s, "a");
	}
}
