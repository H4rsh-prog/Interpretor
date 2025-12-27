package com.interpretor.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.interpretor.exception.InvalidNameException;
import com.interpretor.types.Value;
import com.interpretor.types.functionalInterfaces.TwoParaFunction;


public final class Interpretor {
	StackMemory STACK = null;
	static Map<String, Object> Heap = new HashMap();
	static Map<String, Object> keywords = new TreeMap<String,Object>(
			Collections.unmodifiableMap(Map.of(
			"pushHeap", new TwoParaFunction<String, Object, Void>() {
				@Override
				public Void apply(String variableName, Object variableData) throws InvalidNameException {
					if(((int)variableName.charAt(0))>=48 && ((int)variableName.charAt(0))<=57) {
						throw new InvalidNameException("Variable Names cannot start with a Numeric Value");
					}
					Interpretor.Heap.put(variableName, variableData);
					return null;
				}
			}
			)));
	public void render() throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\User\\Documents\\workspace-spring-tools-for-eclipse-4.31.0.RELEASE\\Interpretor\\src\\com\\interpretor\\script\\inp_script")));
		while(br.ready()) {
			String[] code = br.readLine().split(";");
			System.out.println(keywords.getClass().getTypeName());
			for(String line : code) {
				System.out.println("````````````````````NEW LINE ENCOUNTERED````````````````````````");
				System.out.println(line);
				if(line.equals("{}") || line.equals("")) {
					continue;
				}
				StackMemory currentSTACK = new StackMemory(line);
				System.out.println(currentSTACK);		//BUT WHEN IM PRINTING THE SAME NODE OUTSIDE THE CONSTRUCTOR IT IS NULL
			}
		}
		System.out.println("````````````````````FINISHED INTERPRETING````````````````````````");
		for(int i=0;i<30;i++) {
			System.out.println("\n");
		}
		System.out.println("````````````````````HEAP MEMORY````````````````````````");
		System.out.println(this.Heap);
		System.out.println("````````````````````HEAP MEMORY````````````````````````");
	}
	private void executeStack(StackMemory stack) {
		new Parser(stack.getEntryPoint());
		System.out.println(stack.getEntryPoint());
	}
	private StackMemory populateStack(String code) {
		return new StackMemory(code);
	}
	Object parseData(String code) throws Exception{
		if(code.startsWith("\"")) {
			code = code.substring(1, code.lastIndexOf('"'));
			return new String(code);
		} else if(code.startsWith("\'")) {
			code = code.substring(1, code.lastIndexOf('\''));
			return new String(code);
		} else {
			if(code.contains(".")) {
				return Value.allocateDataType(Double.parseDouble(code));
			}
			return Value.allocateDataType(Long.parseLong(code));
		}
	}
}
