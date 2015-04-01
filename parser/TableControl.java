package parser;

import java.util.ArrayList;
import java.util.Stack;
import util.*;

public class TableControl {
	
	Token[] tokens; //Token Stream
	
	public TableControl (Token[] tokens){
		this.tokens = tokens;
	}
	
	public ArrayList<SymbolTable> popSymTab(){
		
		ArrayList<SymbolTable> SymbolTables = new ArrayList<>();	//What we return
		Stack<SymbolTable> tableStack = new Stack<>();
		int nestingLevel = 0;
		int labelCount = 0;
		int offset = 0;
		
		Token lastNotId;											//To find types associated with Id's
		
		boolean isParam;											//flag to differentiate between param and var
		
		for(int i = 0; i < tokens.length; i++){
			
			if (tokens[i].type == TokenType.MP_PROCEDURE
					|| tokens[i].type == TokenType.MP_FUNCTION){
				
				isParam = true;
			}else if(tokens[i].type == TokenType.MP_LPAREN){
				isParam = false;
			}
			
			//Saves last token that is not an integer
			if(tokens[i].type == TokenType.MP_IDENTIFIER){
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
					tableStack.peek().addProcFunc(tokens[i].val,"Procedure", null); //Parameters??
				}else if(isParam){
					//If it is a parameter
					tableStack.peek().addParameter(tokens[i].val,lastNotId.type, "parameter", "", tableStack.peek().size()); //mode??
				}else {
					//If it is a variable
					tableStack.peek().addVariable(tokens[i].val, lastNotId.type, "variable", tableStack.peek().size());
				}
			}
		}
		return SymbolTables;
	}
}
