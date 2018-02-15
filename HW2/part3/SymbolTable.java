import java.util.*;

public class SymbolTable {

	Stack <ArrayList<Symbol>> stack;

	public SymbolTable() {
		stack = new Stack <ArrayList<Symbol>> ();
	}

	public void push() {
		ArrayList<Symbol> list = new ArrayList<Symbol> ();
		stack.push(list);
	}

	public void pop() {
		stack.pop();
	}

	public void declare(String id, int lineNumber) {
		if(!check_list(id, stack.peek())){
			Symbol symbol = new Symbol(id, lineNumber);
			stack.peek().add(symbol);
		} else {
			System.err.println("redeclaration of variable " + id);
		}
	}

	public void check(String id, int lineNumber) {
		if(!check_stack(id)) {
			System.err.println(id + " is an undeclared variable on line " + lineNumber);
			System.exit(1);
		}
	}

	public void check_scoping(String id, int lineNumber, int lvl) {
		ArrayList<Symbol> list;
		if(lvl < stack.size()) {
			list = stack.get(stack.size() - lvl - 1);
			if(!check_list(id, list)) {
				System.err.println("no such variable ~" + lvl + id + " on line " + lineNumber);
				System.exit(1);
			}
		}
		else {
			System.err.println("no such variable ~" + lvl + id + " on line " + lineNumber);
			System.exit(1);
		}
	}

	public void check_global(String id, int lineNumber) {
		ArrayList<Symbol> list = stack.get(0);
		if(!check_list(id, list)) {
			System.err.println("no such variable ~" + id + " on line " + lineNumber);
			System.exit(1);
		}
	}

	public boolean check_stack(String id) {
		for(ArrayList<Symbol> list : stack)
			if(check_list(id, list))
				return true;
		return false;
	}

	public boolean check_list(String id, ArrayList<Symbol> list) {
		for(Symbol s : list)
			if(s.id.equals(id))
				return true;
		return false;
	}
}