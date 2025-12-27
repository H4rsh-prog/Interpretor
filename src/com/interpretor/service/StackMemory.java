package com.interpretor.service;

import java.util.Set;

import com.interpretor.exception.InvalidSyntaxException;
import com.interpretor.types.Value;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class StackMemory {
	private Set<Character> DELIMITERS = Set.of('+', '-', '*', '/', ' ', '=');
	private StackMemoryNODE entryPoint = null;
	public StackMemory(String CODE){
		try {
			System.out.println("````````````````````POPULATING STACK````````````````````````");
			this.entryPoint = ParseAndFill(this.entryPoint, new StackMemoryNODE("TERMINATOR"), CODE.trim(), false);
			Traverse(this.entryPoint, 0);
			System.out.println("````````````````````STACK POPULATED`````````````````````````");
			new Parser(this.entryPoint);
			System.out.println("````````````````````STACK EXECUTED``````````````````````````");
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}
		Traverse(this.entryPoint, 0);
		System.out.println("````````````````````STACK TRAVERSED`````````````````````````");
		System.out.println(this.entryPoint);		//<-----THIS PRINT STATEMENT IS RUNNING FINE AND ENTRYPOINT HAS AN EXISTING NODE WITH THE VALUE 9
	}
	private StackMemoryNODE ParseAndFill(StackMemoryNODE root, StackMemoryNODE parent, String CODE, boolean swapFlag) throws InvalidSyntaxException {
		CODE = CODE.trim();
		System.out.println("CODE IN THIS ITERATION = "+CODE);
		if(CODE == "") {
			return null;
		} else {
			if(CODE.startsWith("'") || CODE.startsWith("\"")) {
				char c = CODE.charAt(0);
				CODE = CODE.substring(1);
				String newCODE = "";
				if(c == '\'') {
					root = new StackMemoryNODE("'"+CODE.substring(0,CODE.indexOf('\'')+1));
					newCODE = CODE.substring(CODE.indexOf('\'')+1);
				} else {
					root = new StackMemoryNODE("\""+CODE.substring(0,CODE.indexOf('"')+1));
					newCODE = CODE.substring(CODE.indexOf('"')+1);
				}
				root.setTop(parent);
				root.setLeft(ParseAndFill(root.getLeft(), root, newCODE, false));
				return root;
			} else if(CODE.startsWith("(")) {
				int endIndx = findClosingParenthesis(CODE);
				System.out.println("parenthesis closed CODE = "+CODE.substring(1,endIndx));
//				StackMemory recursiveStack = new StackMemory(CODE.substring(1,endIndx));
//				root = new StackMemoryNODE("NESTED_STACK");
//				try {
//					root.setDATA(Parser.parseData(String.valueOf(recursiveStack.getEntryPoint().getDATA())));
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
				root = new StackMemory(CODE.substring(1,endIndx)).getEntryPoint();
				root.setTop(parent);
				String newCODE = CODE.substring(endIndx+1).trim();
				if(newCODE=="") {
					return null;
				}
				if(this.DELIMITERS.contains(newCODE.charAt(0))) {
					StackMemoryNODE new_node = new StackMemoryNODE(""+newCODE.charAt(0));
					new_node.setLeft(root);
					root.setTop(new_node);
					new_node.setTop(parent);
					if(swapFlag) {
						parent.setRight(new_node);
					} else {
						parent.setLeft(new_node);
					}
					root = new_node;
					System.out.println("SENDING CODE "+newCODE.substring(1)+" TO RIGHT CHILD");
					root.setRight(ParseAndFill(root.getRight(), root, newCODE.substring(1), true));
					System.out.println("PARENT RETURNED "+ root);
				} else {
					root.setLeft(ParseAndFill(root.getLeft(), root, newCODE, false));
				}
				System.out.println(root);
				return root;
			}
			int delimitIndx = firstDelimiterIndex(CODE);
			if(delimitIndx==-1) {
				root = new StackMemoryNODE(CODE);
				root.setTop(parent);
			} else {
				root = new StackMemoryNODE(CODE.substring(0, delimitIndx));
				root.setTop(parent);
				String newCODE = CODE.substring(delimitIndx).trim();
				if(this.DELIMITERS.contains(newCODE.charAt(0))) {
					StackMemoryNODE new_node = new StackMemoryNODE(""+newCODE.charAt(0));
					new_node.setLeft(root);
					root.setTop(new_node);
					new_node.setTop(parent);
					if(swapFlag) {
						parent.setRight(new_node);
					} else {
						parent.setLeft(new_node);
					}
					root = new_node;
					root.setRight(ParseAndFill(root.getRight(), root, newCODE.substring(1), true));
				} else {
					root.setLeft(ParseAndFill(root.getLeft(), root, newCODE, false));
				}
			}
		}
		return root;
	}
	int firstDelimiterIndex(String CODE) throws InvalidSyntaxException{
		int indx = -1;
		for(char c : CODE.toCharArray()) {
			indx++;
			if(this.DELIMITERS.contains(c)) {
				return indx;
			}
		}
		return -1;
	}
	public int findClosingParenthesis(String CODE) throws InvalidSyntaxException {
		System.out.println("finding parentheis of :-"+CODE);
		int endIndx = 0;
		int curIndx = -1;
		for(char c : CODE.toCharArray()) {
			curIndx++;
			if(c=='(') {
				endIndx++;
				System.out.println("index incr now "+endIndx );
			} else if(c==')') {
				endIndx--;
				System.out.println("index decr now "+endIndx );
			}
			if(endIndx==0) {
				System.out.println("closing found at "+curIndx);
				return curIndx;
			} else if(endIndx<0) {
				throw new InvalidSyntaxException();
			}
		}
		throw new InvalidSyntaxException();
	}
	public void Traverse_LEFT(StackMemoryNODE root) {
		root = root.getLeft();
	}
	public void Traverse_RIGHT(StackMemoryNODE root) {
		root = root.getRight();
	}
	public static void Traverse(StackMemoryNODE root, int indent) {
		if(root == null) {return;}
		for(int i =0;i<indent;i++) {
			System.out.print("\t");
		}
		System.out.print("\""+root.getOPERAND()+"\" = "+root.getDATA()+" \n");
		for(int i =0;i<indent;i++) {
			System.out.print("\t");
		}
		if(root.getRight()!=null) {
			System.out.print("|\t\\\n");
			indent++;
		} else {
			if(root.getLeft()!=null) {
				System.out.print("| ");
			}
			System.out.println();
		}
		Traverse(root.getLeft(), Math.max(indent-1, 0));
		Traverse(root.getRight(), indent);
	}
}

class StackMemoryNODE<T> {
	@Getter
	@Setter
	private String OPERAND;
	@Getter
	@Setter
	private com.interpretor.types.Data<T> DATA = null;
	@Getter
	@Setter
	private StackMemoryNODE top = null;			//the toString() method will throw a stackOverflow exception because of this node 
												//but i don't feel like fixing it because in a way you shouldn't be able to log a stack node
	@Getter
	@Setter
	private StackMemoryNODE left = null;
	@Getter
	@Setter
	private StackMemoryNODE right = null;
	public StackMemoryNODE(String OPERAND){
		this.OPERAND = OPERAND;
	}
	
	public String toString() {
		return "NODE[OPERAND = "+getOPERAND()+"; DATA = "+getDATA()+"; LEFT = "+getLeft()+"; RIGHT = "+getRight()+"]";
	}
}