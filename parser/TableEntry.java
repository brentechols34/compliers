package parser;

public class TableEntry {
	
	String type,
		kind,
		mode;
	
	int size;
	
	String[] parameters;
	
	//Encompasses All
	public TableEntry(String type, String kind, String mode, int size, String[] parameters){
		this.type = type;
		this.kind = kind;
		this.mode = mode;
		this.size = size;
		this.parameters = parameters;
	}
	
	//Variables
	public TableEntry(String type, String kind, int size){
		this.type = type;
		this.kind = kind;
		this.size = size;
	}
	
	//Parameters
	public TableEntry(String type, String kind, String mode, int size){
		this.type = type;
		this.kind = kind;
		this.mode = mode;
		this.size = size;
	}
	
	//Procedures/functions
	public TableEntry(String kind, String[] parameters){
		this.parameters = parameters;
		this.kind = kind;
	}
	//Getters
	public String getType() { return type; }
	public String getKind() { return kind; }
	public String getMode() { return mode; }
	public int size() { return size; }
	public String[] getParameters() { return parameters; }
}


