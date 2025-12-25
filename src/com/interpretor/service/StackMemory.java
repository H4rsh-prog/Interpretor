package com.interpretor.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.interpretor.exception.InvalidSyntaxException;

import lombok.Data;

@Data
public class StackMemory {
	private Set<Character> OPERATORS = new HashSet();
	private StackMemoryNODE entryPoint = null;
	public StackMemory(String CODE){
		Collections.addAll(this.OPERATORS, '+', '-', '*', '/');
		try {
			this.entryPoint = ParseAndFill(this.entryPoint, null, CODE.trim(), false);
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}
	}
	private StackMemoryNODE ParseAndFill(StackMemoryNODE root, StackMemoryNODE parent, String CODE, boolean swapFlag) throws InvalidSyntaxException {
		CODE = CODE.trim();
		if(CODE == "") {
			return null;
		} else {
			System.out.println("REFACTORED CODE = " + CODE);
			if(!CODE.contains(" ")) {
				System.out.println("RETURNING ROOT "+new StackMemoryNODE(CODE));
				return new StackMemoryNODE(CODE);
			} else {
				System.out.println("CODE CONTAINS SPACE");
				root = new StackMemoryNODE(CODE.substring(0,CODE.indexOf(' ')));
				System.out.println("ROOT SET TO "+root);
				String newCODE = CODE.substring(CODE.indexOf(' ')+1);
				if(this.OPERATORS.contains(newCODE.charAt(0))) {
					System.out.println("SWAPPING LOGIC");
					StackMemoryNODE new_node = new StackMemoryNODE(newCODE.substring(0,newCODE.indexOf(' ')));
					new_node.setLeft(root);
					System.out.println(parent);
					if(swapFlag) {
						parent.setRight(new_node);
					} else {
						parent.setLeft(new_node);
					}
					root = new_node;
					System.out.println("ROOT = "+root+" PARENT = "+parent+" NEW_NODE = "+new_node);
					System.out.println("TRAVERSING RIGHT");
					root.setRight(ParseAndFill(root.getRight(), root, newCODE.substring(1), true));
				} else {
					System.out.println("TRAVERSING LEFT");
					root.setLeft(ParseAndFill(root.getLeft(), root, newCODE, false));
				}
			}
			System.out.println("RETURNING ROOT "+root);
		}
		return root;
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
		System.out.print("\""+root.getOPERAND()+"\" \n");
		for(int i =0;i<indent;i++) {
			System.out.print("\t");
		}
		if(root.getRight()!=null) {
			System.out.print("\\\n");
			indent++;
		} else {
			System.out.print("| ");
			System.out.println();
		}
		Traverse(root.getLeft(), Math.max(indent-1, 0));
		Traverse(root.getRight(), indent);
	}
}

@Data
class StackMemoryNODE<T> {
	private String OPERAND;
	private StackMemoryNODE left = null;
	private StackMemoryNODE right = null;
	public StackMemoryNODE(String OPERAND){
		this.OPERAND = OPERAND;
	}
}