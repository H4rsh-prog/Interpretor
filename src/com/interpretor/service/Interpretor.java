package com.interpretor.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.interpretor.exception.InvalidNameException;
import com.interpretor.types.Data;
import com.interpretor.types.Value;
import com.interpretor.types.functionalInterfaces.TwoParaFunction;


public final class Interpretor {
	StackMemory STACK = null;
	static Map<String, Object> Heap = new HashMap();
	static Map<String, Object> keywords = Map.of(
			"var", new TwoParaFunction<String, Object, Void>() {
				@Override
				public Void apply(String variableName, Object variableData) throws InvalidNameException {
					if(((int)variableName.charAt(0))>=48 && ((int)variableName.charAt(0))<=57) {
						throw new InvalidNameException("Variable Names cannot start with a Numeric Value");
					}
					Interpretor.Heap.put(variableName, variableData);
					return null;
				}
			}
			);
	public void render() throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\User\\Documents\\workspace-spring-tools-for-eclipse-4.31.0.RELEASE\\Interpretor\\src\\com\\interpretor\\script\\inp_script")));
		String[] code = br.readLine().split(";");
		for(String line : code) {
			System.out.println(line);
			if(line == "{}") {
				continue;
			}
			System.out.println("````````````````````POPULATING STACK````````````````````````");
			this.STACK = populateStack(line);
			System.out.println("````````````````````STACK POPULATED````````````````````````");
			executeStack(this.STACK);
			System.out.println("````````````````````STACK EXECUTED````````````````````````");
			StackMemory.Traverse(this.STACK.getEntryPoint(), 0);
			System.out.println("````````````````````STACK TRAVERSED````````````````````````");
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
