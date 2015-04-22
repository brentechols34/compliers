package SemanticAnalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.ArrayList;

import parser.RuleApplication;
import util.Token;

import java.util.Stack;

public class SemanticAnalyzer {
	
	PrintWriter pw;
	Stack<CodeChunk> code;
	ArrayList<RuleApplication> rules;
	
	public SemanticAnalyzer(String fname,ArrayList<RuleApplication> rules) throws FileNotFoundException{
		pw = new PrintWriter(new File(fname));
		code = new Stack<>();
		this.rules = rules;
	}
	
	public void convert(Stack<Token> tokens){
		Token next = tokens.pop();
		switch(next.val){
			
		}
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
		int ruleNum;
		
		public CodeChunk(){
			uCode = new ArrayList<>();
		}
	}
}