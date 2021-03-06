package util;

import java.util.ArrayList;

public class CodeChunk {
	public ArrayList<String> uCode;
	private TokenType type;

	public CodeChunk() {
		uCode = new ArrayList<>();
	}

	public CodeChunk(String s) {
		uCode = new ArrayList<>();
		uCode.add(s);
	}

	public CodeChunk(CodeChunk... cc) {
		uCode = new ArrayList<>();
		for (CodeChunk c : cc) {
			uCode.addAll(c.uCode);
		}
	}

	public void append(String s) {
		uCode.add(s);
	}

	public void append(CodeChunk c) {
		uCode.addAll(c.uCode);
	}

	public TokenType getType() {
		return type;
	}

	public void setType(TokenType type) {
		this.type = type;
	}
	
	public String toString() {
		String s = "";
		if (uCode.size()==0) return s;
		for (int i = 0; i < uCode.size()-1; i++) {
			s+=uCode.get(i)+"\n";
		}
		s+=uCode.get(uCode.size()-1);
		return s;
	}
}