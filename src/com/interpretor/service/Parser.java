package com.interpretor.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.interpretor.types.Value;

public class Parser {
	private Set<String> declarationKeyword = new HashSet();
	public Parser() {
		Collections.addAll(this.declarationKeyword, "var", "const", "let");
	}
	private StackMemoryNODE Execute(StackMemoryNODE root, boolean declaration) {
		if(root.getLeft()==null) {
			return null;
		}
		if(declarationKeyword.contains(root.getOPERAND())) {
			Execute(root.getLeft(), true);
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
