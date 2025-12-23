package com.interpretor.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Function;

import com.interpretor.exception.InvalidNameException;
import com.interpretor.exception.InvalidSyntaxException;
import com.interpretor.types.Value;


public class Interpretor {
	Stack<String> StackMemory = new Stack<>();
	Map<String, Object> Heap = new HashMap();
	public Map<String, Object> getHeap(){
		return this.Heap;
	}
	Map<String, Function> keywords = new HashMap();
	public Interpretor(){
		this.keywords.put("var", new Function<String, Void>() {
			@Override
			public Void apply(String t) {
				try {
					createVariable(t);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}
	public void render() throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\User\\Documents\\workspace-spring-tools-for-eclipse-4.31.0.RELEASE\\Interpretor\\src\\com\\interpretor\\script\\inp_script")));
		while(br.ready()) {
			String line = br.readLine();
			System.out.println(line);
			line = line.trim();
			int trv_indx = line.indexOf(' ');
			String word = line.substring(0, trv_indx!=-1?trv_indx:0);
			if(keywords.containsKey(word)) {
				keywords.get(word).apply(line.substring(trv_indx).trim());
			} else {
				if(Heap.containsKey(word)) {
				} else {
//					throw new InvalidSyntaxException();
				}
			}
		}
//		String line = br.readLine();
//		while(line!=null) {
//			populateStack(line);
//		}
	}
//	public void populateStack(String code) {
//		
//	}
	public void createVariable(String code) throws InvalidNameException, Exception {
		String variableName = "";
		Object variableData = null;
		if(((int)code.charAt(0))>=48 && ((int)code.charAt(0))<=57) {
			throw new InvalidNameException("Variable Names cannot start with a Numeric Value");
		}
		int trv_indx = 0;
		for(char ch : code.toCharArray()) {
			if(ch!=' ' && ch!='=') {
				variableName += ch;
				trv_indx++;
			} else {
				break;
			}
		}
		code = code.substring(trv_indx).trim();
		if(code.startsWith("=")) {
			variableData = parseData(code.substring(1).trim());
			System.out.println(variableData.getClass()+" VAL = "+variableData);
		}
		Heap.put(variableName, variableData);
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
