package parser;

import java.util.ArrayList;
import java.util.Stack;
import util.*;

public class TableControl {
	
	Token[] tokens; //Token Stream
	
	public TableControl (Token[] tokens){
		this.tokens = tokens;
	}
	
	
	//Token Stream is array
	public ArrayList<SymbolTable> popSymTab(){
		
		ArrayList<SymbolTable> SymbolTables = new ArrayList<>();	//What we return
		Stack<SymbolTable> tableStack = new Stack<>();
		String name;
		int nestingLevel = 0;
		int labelCount = 0;
		int offset = 0;
		
		Token lastNotId = null;											//To find types associated with Id's
		
		boolean isParam = false;											//flag to differentiate between param and var
		
		for(int i = 0; i < tokens.length; i++){
			
			if (tokens[i].type == TokenType.MP_PROCEDURE
					|| tokens[i].type == TokenType.MP_FUNCTION){
				
				isParam = true;
			}else if(tokens[i].type == TokenType.MP_LPAREN){
				isParam = false;
			}
			
			//Saves last token that is not an integer
			if(tokens[i].type != TokenType.MP_IDENTIFIER || !tokens[i].val.equals("in") ||!tokens[i].val.equals("out")){
				lastNotId = tokens[i];
			}
			
			//If token is a program, a procedure, or a function, push a new Symbol Table and increment
			//nesting level
			if(tokens[i].type == TokenType.MP_PROGRAM
					||tokens[i].type == TokenType.MP_PROCEDURE
					||tokens[i].type == TokenType.MP_FUNCTION){
				tableStack.push(new SymbolTable(tokens[i].val, ("L" + labelCount++) , nestingLevel++));
			}
			
			//If Token is an end, pop into ArrayList, decrement nestingLevel
			if(tokens[i].type == TokenType.MP_END){
				SymbolTables.add(tableStack.pop());
				nestingLevel--;
			}
			
			//If token is an identifier, add new TableEntry to the SymbolTable on top of the stack. 
			if(tokens[i].type == TokenType.MP_IDENTIFIER){
				if(lastNotId.type == TokenType.MP_PROCEDURE || lastNotId.type == TokenType.MP_FUNCTION){
					tableStack.peek().addProcFunc(tokens[i].val,"Procedure", new ArrayList<String>());
					name = tokens[i].val;
				}else if(isParam){                                                                                    
					//If it is a parameter
					tableStack.peek().addParameter(tokens[i].val,lastNotId.type, "parameter", tokens[i - 1].val, tableStack.peek().size()-1);
					tableStack.peek().entries.get(tokens[i].val).parameters.add(tokens[i].val);
				}else {
					//If it is a variable
					tableStack.peek().addVariable(tokens[i].val, lastNotId.type, "variable", tableStack.peek().size());
				}
			}
		}
		return SymbolTables;
	}
}
