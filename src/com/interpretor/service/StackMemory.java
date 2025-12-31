package com.interpretor.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.interpretor.exception.InvalidSyntaxException;
import com.interpretor.types.StackMemoryNODE;
import com.interpretor.types.TYPE_Function;

import lombok.Data;

@Data
public class StackMemory {
	private Set<Character> DELIMITERS = Set.of('+', '-', '*', '/', ' ', '=');
	private StackMemoryNODE entryPoint = null;
	private Set<String> declaredFunctions = ((Map<String, TYPE_Function>)Interpretor.Heap.get("functionHeap")).keySet();
	private Parser spareParser = new Parser(null, Interpretor.Heap);
	public StackMemory(String CODE, boolean parse){
		try {
			System.out.println("````````````````````POPULATING STACK````````````````````````");
			this.entryPoint = ParseAndFill(this.entryPoint, new StackMemoryNODE("TERMINATOR"), CODE.trim(), false, (parse)?true:false);
			Traverse(this.entryPoint, 0);
			System.out.println("````````````````````STACK POPULATED`````````````````````````");
			if(parse) new Parser(this.entryPoint, Interpretor.Heap);
			System.out.println("````````````````````STACK EXECUTED``````````````````````````");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Traverse(this.entryPoint, 0);
		System.out.println("````````````````````STACK TRAVERSED`````````````````````````");
		System.out.println(this.entryPoint);
	}
	public StackMemoryNODE ParseAndFill(StackMemoryNODE root, StackMemoryNODE parent, String CODE, boolean swapFlag, boolean parse) throws Exception {
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
				root.setLeft(ParseAndFill(root.getLeft(), root, newCODE, false, (parse)?true:false));
			} else if(CODE.startsWith("(")) {
				int endIndx = findClosingParenthesis(CODE);
				System.out.println("parenthesis closed CODE = "+CODE.substring(1,endIndx));
				StackMemory recursiveStack = new StackMemory(CODE.substring(1,endIndx), (parse)?true:false);
				if(recursiveStack==null) {
					return null;
				}
//				root = new StackMemoryNODE("NESTED_STACK");
//				root.setDATA(recursiveStack.getEntryPoint().getDATA());
				root = recursiveStack.getEntryPoint();
				root.setTop(parent);
				if(CODE.substring(endIndx+1).trim()=="") {
					return root;
				}
				String newCODE = CODE.substring(endIndx+1).trim();
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
					root.setRight(ParseAndFill(root.getRight(), root, newCODE.substring(1), true, (parse)?true:false));
					System.out.println("PARENT RETURNED "+ root);
				} else {
					root.setLeft(ParseAndFill(root.getLeft(), root, newCODE, false, (parse)?true:false));
				}
				System.out.println(root);
			} else if(CODE.indexOf('(')!=-1 && this.declaredFunctions.contains(CODE.substring(0,CODE.indexOf('(')))) {
				TYPE_Function fn = ((Map<String,TYPE_Function>)Interpretor.Heap.get("functionHeap")).get(CODE.substring(0,CODE.indexOf('(')));
				String args = CODE.substring(CODE.indexOf('(')+1,CODE.indexOf(')')).trim();
				String newCODE = CODE.substring(CODE.indexOf(')')+1).trim();
				if(args!="") {
					ArrayList<Object> ARGUMENTS = new ArrayList();
					for(String argument : args.split("[,]")) {
						System.out.println("parsing argument = "+argument);
						try {
							ARGUMENTS.add(spareParser.parseData(argument));
						} catch (Exception e) {
							throw new Exception("SOMETHING WENT WRONG PARSING ARGUMENTS ARGUMENT_REFERENCE:- "+argument);
						}
					}
					fn.setARGUMENTS(ARGUMENTS);
					
				}
				root = new StackMemoryNODE("FUNCTION_CALL_RETURN");
				root.setTop(parent);
				root.setLeft(new StackMemoryNODE("FUNCTION_CALL"));
				root.getLeft().setDATA(fn);
				root.getLeft().setTop(root);
				root.setRight(ParseAndFill(root.getRight(), root, newCODE, false, (parse)?true:false));
			} else {
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
						root.setRight(ParseAndFill(root.getRight(), root, newCODE.substring(1), true, (parse)?true:false));
					} else {
						root.setLeft(ParseAndFill(root.getLeft(), root, newCODE, false, (parse)?true:false));
					}
				}
			}
		}
		return root;
	}
//	public functionDeclaration(String CODE, String functionNmae, String returnType) {
//		
//	}
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
	int firstDelimiterIndex(String CODE, Set<Character> delimiters) {
		int indx = -1;
		for(char c : CODE.toCharArray()) {
			indx++;
			if(delimiters.contains(c)) {
				return indx;
			}
		}
		return -1;
	}
	public int findClosingParenthesis(String CODE) throws InvalidSyntaxException {
		int endIndx = 0;
		int curIndx = -1;
		for(char c : CODE.toCharArray()) {
			curIndx++;
			if(c=='(') {
				endIndx++;
			} else if(c==')') {
				endIndx--;
			}
			if(endIndx==0) {
				return curIndx;
			} else if(endIndx<0) {
				throw new InvalidSyntaxException();
			}
		}
		throw new InvalidSyntaxException();
	}
	public int findClosingParenthesis(String CODE, char start, char end, boolean verboose) throws InvalidSyntaxException {
		if(verboose) System.out.println("finding parentheis of :-"+CODE);
		int endIndx = 0;
		int curIndx = -1;
		boolean foundEntry = false;
		for(char c : CODE.toCharArray()) {
			curIndx++;
			if(verboose) System.out.println("character "+ c+ " at current indexing "+curIndx);
			if(c==start) {
				endIndx++;
				foundEntry = true;
				if(verboose) System.out.println("index incr now "+endIndx );
			} else if(c==end) {
				endIndx--;
				if(verboose) System.out.println("index decr now "+endIndx );
			}
			if(endIndx==0 && foundEntry) {
				if(verboose) System.out.println("closing found at "+curIndx);
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
