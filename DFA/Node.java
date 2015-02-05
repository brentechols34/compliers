package DFA;

import java.util.HashMap;


/**
 * Node class, for use with the DFA
 * @author Alex
 *
 */
public class Node {
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
	public boolean equals(Node n) {
		if (accepting != n.accepting) return false;
		HashMap<Integer,Integer> mapping = new HashMap<>();
		for (Character c : edges.keySet()) {
			int myEdge = edges.get(c);
			Integer mapped = mapping.get(n);
			if (mapped == null) {
				int hisEdge = n.edges.get(c);
				mapping.put(myEdge, hisEdge);
			} else {
				if (myEdge != mapped) { return false; }
			}
		}
		return true;
	}
}