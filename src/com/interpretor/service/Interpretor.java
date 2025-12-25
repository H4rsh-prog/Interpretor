package com.interpretor.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.interpretor.exception.InvalidNameException;
import com.interpretor.types.Value;
import com.interpretor.types.functionalInterfaces.TwoParaFunction;


public class Interpretor {
	StackMemory STACK = null;
	Map<String, Object> Heap = new HashMap();
	public Map<String, Object> getHeap(){
		return this.Heap;
	}
	Map<String, Object> keywords = new HashMap();
	public Interpretor(){
		this.keywords.put("var", new TwoParaFunction<String, Object, Void>() {
			@Override
			public Void apply(String variableName, Object variableData) throws InvalidNameException {
				if(((int)variableName.charAt(0))>=48 && ((int)variableName.charAt(0))<=57) {
					throw new InvalidNameException("Variable Names cannot start with a Numeric Value");
				}
				getHeap().put(variableName, variableData);
				return null;
			}
		});
	}
	public void render() throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\User\\Documents\\workspace-spring-tools-for-eclipse-4.31.0.RELEASE\\Interpretor\\src\\com\\interpretor\\script\\inp_script")));
//		while(br.ready()) {
//			String line = br.readLine();
//			System.out.println(line);
//			line = line.trim();
//			int trv_indx = line.indexOf(' ');
//			String word = line.substring(0, trv_indx!=-1?trv_indx:0);
//			if(keywords.containsKey(word)) {
//				keywords.get(word).apply(line.substring(trv_indx).trim());
//			} else {
//				if(Heap.containsKey(word)) {
//				} else {
//				}
//			}
//		}
		String[] code = br.readLine().split(";");
		for(String line : code) {
			System.out.println(line);
			if(line == "{}") {
				continue;
			}
			this.STACK = populateStack(line);
			StackMemory.Traverse(this.STACK.getEntryPoint());
			executeStack(this.STACK);
		}
		((TwoParaFunction<String, Object, Void>) this.keywords.get("var")).apply("e",24);
		((TwoParaFunction<String, Object, Void>) this.keywords.get("var")).apply("fe",new Object() { private int number = 0; public String toString() {return "" +(this.number);}});
		System.out.println(getHeap());
		System.out.println(Value.allocateDataType("helo world"));
	}
	private void executeStack(StackMemory stack) {
		
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
