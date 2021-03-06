package parser;

import java.util.ArrayList;
import java.util.HashMap;

import util.TokenType;

public class SymbolTable {
	
	String name, label;
	int nestingLevel;
	int offset;
	
	HashMap<String,TableEntry> entries;
	
	public SymbolTable(String name, String label, int nestingLevel){
		this.name = name;
		this.label = label;
		this.nestingLevel = nestingLevel;
		offset = 0;
		
		entries = new HashMap<String,TableEntry>();
	}
	
	//Encompasses All
	public void addEntry(String lexeme, TokenType type, String kind, String mode, ArrayList<String> parameters){
		TableEntry entry = new TableEntry(type,kind,mode,offset++,parameters);
		entries.put(lexeme, entry);
	}
	
	//Variables
	public void addVariable(String lexeme, TokenType type, String kind){
		TableEntry entry = new TableEntry(type,kind,offset++);
		entries.put(lexeme, entry);
	}

	//Parameters
	public void addParameter(String lexeme, TokenType type, String kind, String mode){
		TableEntry entry = new TableEntry(type,kind,mode,offset++);
		entries.put(lexeme, entry);
	}
	
	//Procedures/functions
	public void addProcFunc(String lexeme, String kind, ArrayList<String> parameters){
		TableEntry entry = new TableEntry(kind,parameters);
		entries.put(lexeme, entry);
	}

    // Get the size of the local variables and function declarations
    public int localSize() {
        int size = 0;
        for (TableEntry entry : this.entries.values()) {
            String kind = entry.getKind();
            if (kind == "variable" || kind == "function" || kind == "procedure") {
                size++;
            }
        }

        return size;
    }

	public int size(){
		return entries.size();
	}
	
        public TableEntry getEntry(String lexeme){
            return entries.get(lexeme);
        }
        
        public int getNestingLevel(){
            return nestingLevel;
        }
                
	public String toString(){
		String str = "";
		for(String e:entries.keySet()){
			str+= e + ":" + entries.get(e).toString() + "\n";
		}
		return str;
	}
}