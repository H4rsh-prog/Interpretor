package com.interpretor.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.interpretor.exception.InvalidSyntaxException;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class StackMemory {
	private Set<Character> DELIMITERS = new HashSet();
	private StackMemoryNODE entryPoint = null;
	public StackMemory(String CODE){
		Collections.addAll(this.DELIMITERS, '+', '-', '*', '/');
		try {
			this.entryPoint = ParseAndFill(this.entryPoint, null, CODE.trim(), false);
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		} finally {
			Traverse(this.entryPoint, 0);
		}
	}
	private StackMemoryNODE ParseAndFill(StackMemoryNODE root, StackMemoryNODE parent, String CODE, boolean swapFlag) throws InvalidSyntaxException {
		CODE = CODE.trim();
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
			}
			if(!CODE.contains(" ")) {
				int indx = firstDelimiterIndex(CODE);
				if(indx==-1) {
					root = new StackMemoryNODE(CODE);
					root.setTop(parent);
				} else {
					root = new StackMemoryNODE(CODE.substring(0, indx));
					root.setTop(parent);
					String newCODE = CODE.substring(indx);
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
						root.setLeft(ParseAndFill(root.getLeft(), root, newCODE.substring(indx), false));
					}
				}
			} else {
				root = new StackMemoryNODE(CODE.substring(0,CODE.indexOf(' ')));
				root.setTop(parent);
				String newCODE = CODE.substring(CODE.indexOf(' ')+1);
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
				if(indx==0) {
					throw new InvalidSyntaxException();
				}
				return indx;
			}
		}
		return -1;
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