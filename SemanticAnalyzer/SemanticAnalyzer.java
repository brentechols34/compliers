package SemanticAnalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.ArrayList;

import parser.RuleApplication;
<<<<<<< HEAD
import util.Token;

import java.util.Stack;
=======
import parser.SymbolTable;
import parser.TableEntry;
import util.*;

>>>>>>> 0ada38fbd425dfb73ab2df66e265f15c027b64e9

public class SemanticAnalyzer {
	
	PrintWriter pw;
	Stack<CodeChunk> code;
	ArrayList<RuleApplication> rules;
        	
	public SemanticAnalyzer(String fname,ArrayList<RuleApplication> rules) throws FileNotFoundException{
		pw = new PrintWriter(new File(fname));
		code = new Stack<>();
		this.rules = rules;
	}
<<<<<<< HEAD
	
	public void convert(Stack<Token> tokens){
		Token next = tokens.pop();
		switch(next.val){
			
		}
=======
        
	public CodeChunk convert(ArrayList<Token> tokens,SymbolTable table){
            Token curToken = tokens.get(0);
            CodeChunk cc;
            switch(curToken.type){
                case MP_INTEGER_LIT:
                    cc = new CodeChunk("PUSH #" + curToken.val);
                    cc.type = TokenType.MP_INTEGER;
                    return cc;
                case MP_PLUS:
                    cc = new CodeChunk(convert(tokens,table),convert(tokens,table),new CodeChunk("ADDS"));
                    return cc;
                case MP_MINUS:
                    cc = new CodeChunk(convert(tokens,table),convert(tokens,table),new CodeChunk("SUBS"));
                    return cc;
                case MP_IDENTIFIER:
                    TableEntry entry = table.getEntry(curToken.val);
                    cc = new CodeChunk("PUSH " + entry.getSize() + "(D" + table.getNestingLevel());
                    return cc;
                default:
                    return null;
            }
>>>>>>> 0ada38fbd425dfb73ab2df66e265f15c027b64e9
	}	
	
	public CodeChunk ArithExp(){
		
		return null;
	}
	
	public CodeChunk AssignStatement(){
		
		return null;
	}
	
	
	public void printToFile(){
		Stack<CodeChunk> reversed = new Stack<>();
		//reverse code stack
		while(!code.isEmpty()){
			reversed.push(code.pop());
		}
		//print to file
		while(!reversed.isEmpty()){
			ArrayList<String> toPrint = reversed.pop().uCode;
			for(String s:toPrint){
				pw.write(s);
			}
		}
	}
	
	private class CodeChunk{
		ArrayList <String> uCode;
                TokenType type;
		int ruleNum;
		
		public CodeChunk(){
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
	}
}