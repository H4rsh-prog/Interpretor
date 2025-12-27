package com.interpretor.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.interpretor.exception.InvalidSyntaxException;
import com.interpretor.types.Data;
import com.interpretor.types.Value;
import com.interpretor.types.functionalInterfaces.TwoParaFunction;


public class Parser {
	private Set<String> declarationKeyword = Set.of("var", "const", "let");
	private Set<String> operatorKeyword = Set.of("+", "-", "*", "/", "=");
	private StackMemoryNODE entryNODE = null;
	public Parser(StackMemoryNODE root) {
		this.entryNODE = root;
		try {
			parseValues(root);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void parseValues(StackMemoryNODE root) throws Exception {
		if(root==null) {
			return;
		}
		System.out.println("``````````````````DATA PARSER````````````````````");
		System.out.println("parsing current node = "+root);
		if(!operatorKeyword.contains(root.getOPERAND()) && root.getDATA()==null) {
			System.out.println("NODE DOESNT CONTAIN AN OPERATOR = "+root.getOPERAND());
			root.setDATA(parseData(root.getOPERAND()));
			System.out.println("data set to "+root.getDATA());
		}
		if(root.getRight()!=null) {
			System.out.println("root has right shifted with the left node = " +root.getLeft().getOPERAND()+" and the right node = "+root.getRight().getOPERAND());
			parseValues(root.getLeft());
			parseValues(root.getRight());
			System.out.println("done parsing right shifted child nodes of "+root.getOPERAND());
		} else {
			parseValues(root.getLeft());
			System.out.println("done parsing child node of "+root.getOPERAND());
		}
		if(root.getTop()==null) {
			return;
		}
		System.out.println("CURRENT ROOT IS RIGHT CHILD = " + (root.getTop().getRight()==root));
		System.out.println("CURRENT ROOT IS LEFT CHILD = " + (root.getTop().getRight()==null));
		if(root.getTop().getRight()==null || root.getTop().getRight()==root) {
			System.out.println("OPERATING ON CURRENT NODE");
			if(operatorKeyword.contains(root.getTop().getOPERAND())) {
//				if(root.getTop().getRight()==null) {throw new InvalidSyntaxException("TRIED USING OPERATORS WITH ONLY ONE OPERAND");}
				switch (root.getTop().getOPERAND()){
					case "+":
						root.getTop().setDATA(Data.add(root.getTop().getLeft().getDATA(), root.getDATA()));
						break;
					case "-":
						root.getTop().setDATA(Data.subtract(root.getTop().getLeft().getDATA(), root.getDATA()));
						break;
					case "*":
						root.getTop().setDATA(Data.multiply(root.getTop().getLeft().getDATA(), root.getDATA()));
						break;
					case "/":
						root.getTop().setDATA(Data.divide(root.getTop().getLeft().getDATA(), root.getDATA()));
						break;
					case "=":
						root.getTop().setDATA(root.getDATA());
						break;
				}
			} else {
				System.out.println("encountered a variable top");
				if(root.getOPERAND().equals("=")) {
					if(this.declarationKeyword.contains(root.getTop().getOPERAND()) || Interpretor.Heap.containsKey(root.getLeft().getOPERAND())) {
						((TwoParaFunction) Interpretor.keywords.get("pushHeap")).apply(root.getLeft().getOPERAND(),root.getDATA());
					}
				}
			}
		}
		System.out.println("done processing current node = "+root+" with its parent modified to "+root.getTop());
	}
	static <T> T parseData(String OPERAND) throws NumberFormatException, Exception {
		System.out.println("``````````````````DATA PARSER````````````````````");
		if(OPERAND.startsWith("\"") || OPERAND.startsWith("'")) {
			System.out.println("operand is a string");
			return (T) OPERAND;
		} else {
			if(isNumeric(OPERAND)) {
				System.out.println("operand is numeric");
				if(OPERAND.contains(".")) {
					System.out.println("operand is floating");
					return (T) Value.allocateDataType(Double.valueOf(OPERAND));
				} else {
					System.out.println("operand is natural");
					return (T) Value.allocateDataType(Long.valueOf(OPERAND));
				}
			} else if(OPERAND == "true" || OPERAND == "false" ) {
				System.out.println("operand is boolean");
				if(OPERAND == "true") {
					return (T) Value.allocateDataType(true);
				} else {
					return (T) Value.allocateDataType(false);
				}
			} else {
				System.out.println("operand is object");
				return (T) Interpretor.Heap.getOrDefault(OPERAND, null);
			}
		}
	}
	static boolean isNumeric(String string){
		for(char c : string.toCharArray()) {
			int c_int = (int) c;
			if((c_int >= 65 && c_int<=90) || c_int >= 97 && c_int<=122) {
				return false;
			}
		}
		return true;
	}
}
