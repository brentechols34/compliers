package DFA;

import java.util.Collection;
import java.util.HashMap;

/**
 * Simple DFA, buncha nodes with integer ids
 * @author Alex
 *
 */
public class DFA {
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

