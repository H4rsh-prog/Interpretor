package com.interpretor.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.interpretor.types.Data;
import com.interpretor.types.Value;
import com.interpretor.types.functionalInterfaces.TwoParaFunction;

public class Parser {
	private Set<String> declarationKeyword = Set.of("var", "const", "let");
	private Set<String> operatorKeyword = Set.of("+", "-", "*", "/", "=");
	private void parseValues(StackMemoryNODE root, boolean declaration, String variableName) throws Exception {
		if(root.getLeft()==null) {
			return;
		}
		if(!operatorKeyword.contains(root.getOPERAND())) {
			root.setDATA(parseData(root.getOPERAND()));
		}
		if(root.getRight()!=null) {
			parseValues(root.getRight(), declaration, variableName);
			parseValues(root.getLeft(), declaration, variableName);
		}
		if(root.getTop()==null) {
			return;
		}
		if(root.getTop().getRight()==null || root.getTop().getRight()==root) {
			if(operatorKeyword.contains(root.getTop().getOPERAND())) {
				switch (root.getTop().getOPERAND()){
					case "+":
						root.getTop().setDATA((Data) root.getDATA().add(root.getTop().getLeft().getDATA()));
						break;
					case "-":
						root.getTop().setDATA((Data) root.getDATA().sub(root.getTop().getLeft().getDATA()));
						break;
					case "*":
						root.getTop().setDATA((Data) root.getDATA().mul(root.getTop().getLeft().getDATA()));
						break;
					case "/":
						root.getTop().setDATA((Data) root.getDATA().div(root.getTop().getLeft().getDATA()));
						break;
					default:
						root.getTop().setDATA(root.getDATA());
				}
			}
		}
	}
	<T> T parseData(String OPERAND) throws NumberFormatException, Exception {
		if(OPERAND.startsWith("\"") || OPERAND.startsWith("'")) {
			return (T) OPERAND;
		} else {
			if(isNumeric(OPERAND)) {
				if(OPERAND.contains(".")) {
					return (T) Value.allocateDataType(Double.valueOf(OPERAND));
				} else {
					return (T) Value.allocateDataType(Long.valueOf(OPERAND));
				}
			} else if(OPERAND == "true" || OPERAND == "false" ) {
				if(OPERAND == "true") {
					return (T) Value.allocateDataType(true);
				} else {
					return (T) Value.allocateDataType(false);
				}
			} else {
				return (T) Interpretor.Heap.getOrDefault(OPERAND, null);
			}
		}
	}
	boolean isNumeric(String string){
		for(char c : string.toCharArray()) {
			int c_int = (int) c;
			if((c_int >= 65 && c_int<=90) || c_int >= 97 && c_int<=122) {
				return false;
			}
		}
		return true;
	}
}
