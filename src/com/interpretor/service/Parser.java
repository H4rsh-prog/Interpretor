package com.interpretor.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.interpretor.types.Data;
import com.interpretor.types.StackMemoryNODE;
import com.interpretor.types.TYPE_Function;
import com.interpretor.types.TYPE_LOOP;
import com.interpretor.types.Value;
import com.interpretor.types.functionalInterfaces.TwoParaFunction;


public class Parser {
	Map<String, Object> Heap= null;
	private Set<String> declarationKeyword = Set.of("var", "const", "let");
	private Set<String> operatorKeyword = Set.of("+", "-", "*", "/", "%", "=", "==", "!=");
	private StackMemoryNODE entryNODE = null;
	public Parser(StackMemoryNODE root, Map<String, Object> heapMemory) {
		this.Heap = heapMemory;
		this.entryNODE = root;
		try {
			parseValues(root);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void parseValues(StackMemoryNODE root) throws Exception {
		if(root==null) {
			return;
		}
		System.out.println("``````````````````VALUE PARSER````````````````````");
		System.out.println("parsing current node = "+root);
		if(!operatorKeyword.contains(root.getOPERAND())) {
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
		if(root.getOPERAND()=="FUNCTION_CALL") {
			System.out.println("ROOT IS A FUNCTION");
			TYPE_Function fn = ((TYPE_Function)root.getDATA());
			fn.call();
			root.getTop().setDATA(fn.getReturnNODE().getDATA());
		} else if(root.getOPERAND().startsWith("LOOP-ID_")) {
			((HashMap<String, TYPE_LOOP>) Interpretor.Heap.get("loopHeap")).get(root.getOPERAND()).startLoop();
		}
		if(root.getTop()==null) {
			return;
		}
		System.out.println("CURRENT ROOT IS RIGHT CHILD = " + (root.getTop().getRight()==root));
		System.out.println("CURRENT ROOT IS LEFT CHILD = " + (root.getTop().getRight()==null));
		if(root.getTop().getRight()==null || root.getTop().getRight()==root) {
			System.out.println("OPERATING ON CURRENT NODE " +root+ "AND PARENT = "+root.getTop());
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
					case "%":
						root.getTop().setDATA(Data.modulus(root.getTop().getLeft().getDATA(), root.getDATA()));
						break;
					case "=":
						root.getTop().setDATA(root.getDATA());
						break;
					case "==":
						root.getTop().setDATA(Data.equals(root.getTop().getLeft().getDATA(), root.getDATA()));
						break;
					case "!=":
						root.getTop().setDATA(Data.notEquals(root.getTop().getLeft().getDATA(), root.getDATA()));
						break;
				}
			} else {
				System.out.println("encountered a variable top");
				if(root.getOPERAND().equals("=")) {
					System.out.println("variable value in heap is"+this.Heap.getOrDefault(root.getLeft().getOPERAND(), null)+" and the root value of the variable is "+root.getDATA());
					if(this.declarationKeyword.contains(root.getTop().getOPERAND()) || this.Heap.containsKey(root.getLeft().getOPERAND())) {
						this.Heap.put(root.getLeft().getOPERAND(),root.getDATA());
						if(Interpretor.Heap.containsKey(root.getLeft().getOPERAND())) {
							Interpretor.Heap.put(root.getLeft().getOPERAND(),root.getDATA());
						}
					} else if(this.Heap.getOrDefault(root.getLeft().getOPERAND(), null)!=null) {
						System.out.println("saving existing vraible");
						this.Heap.put(root.getLeft().getOPERAND(),root.getDATA());
						System.out.println("heap is now "+this.Heap);
					}
				}
			}
		}
		System.out.println("done processing current node = "+root+" with its parent modified to "+root.getTop());
	}
	public <T> T parseData(String OPERAND) throws NumberFormatException, Exception {
		System.out.println("``````````````````DATA PARSER````````````````````");
		OPERAND = OPERAND.trim();
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
			} else if(OPERAND.toLowerCase().equals("true") || OPERAND.toLowerCase().equals("false") ) {
				System.out.println("operand is boolean");
				if(OPERAND.equals("true")) {
					return (T) Value.allocateDataType(true);
				} else {
					return (T) Value.allocateDataType(false);
				}
			} else {
				System.out.println("operand is object");
				return (T) this.Heap.getOrDefault(OPERAND, null);
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
