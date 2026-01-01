package com.interpretor.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.interpretor.exception.InvalidNameException;
import com.interpretor.exception.InvalidSyntaxException;
import com.interpretor.types.TYPE_Function;
import com.interpretor.types.TYPE_LOOP;
import com.interpretor.types.Value;
import com.interpretor.types.functionalInterfaces.TwoParaFunction;

import lombok.Getter;


public final class Interpretor {
	StackMemory STACK = null;
	public static Map<String, Object> Heap = new HashMap(Map.of("functionHeap", new HashMap<String, TYPE_Function>()
																,"loopHeap", new HashMap<String, TYPE_LOOP>()
																));
	public static Map<String, Object> keywords = new TreeMap<String,Object>(
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
	static StackMemory spareMemory = new StackMemory("",false);
	public void render() throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\User\\Documents\\workspace-spring-tools-for-eclipse-4.31.0.RELEASE\\Interpretor\\src\\com\\interpretor\\script\\inp_script")));
		String CODE = "";
		while(br.ready()) {
			String line = br.readLine();
			if(line.trim().startsWith("//")) {
				continue;
			}
			CODE += line;
			CODE += "\n";
		}
		ArrayList<String> loops = new ArrayList();
		String loopBlock = "";int loopID = 0;
		boolean loopFlag = false;
		do {
			loopFlag = false;
			int loopIndx = CODE.indexOf("for");
			if(loopIndx == -1) {
				break;
			} else {
				loopFlag = true;
			}
			int endBlockIndx = spareMemory.findClosingParenthesis(CODE,'{','}',false);
			loops.add(CODE.substring(loopIndx, endBlockIndx+1));
			CODE = CODE.replace(CODE.substring(loopIndx, endBlockIndx+1), "LOOP-ID_"+loopID+"_");
			loopID++;
		} while(loopFlag);
		loopID = -1;
		for(String loop : loops) {
			loopID++;
			loop = loop.substring("for".length());
			if(!loop.startsWith("(")) {
				throw new InvalidSyntaxException("IMPROPER LOOP DECLARATION:- OPENING BRACKET EXPECTED AFTER FOR KEYWORD");
			}
			int endParenthesisIndx = loop.indexOf(')');
			if(endParenthesisIndx!=-1) {
				
				if(loop.substring(endParenthesisIndx+1).trim().startsWith("{")) {
					System.out.println("valid loop");
					createLoop(loop, "LOOP-ID_"+loopID+"_");
				} else {
					throw new InvalidSyntaxException("IMPROPER LOOP DECLARATION:- LOOP BLOCK EXPECTED");
				}
			} else {
				throw new InvalidSyntaxException("IMPROPER LOOP DECLARATION:- CLOSING BRACKET EXPECTED");
			}			
		}
		CODE = String.join("\n", CODE.split(";"));
		ArrayList<String> functions = new ArrayList();
		String functionBlock = "";
		boolean functionFlag = false;
		do {
			functionFlag = false;
			int funcIndx = CODE.indexOf("function");
			if(funcIndx == -1) {
				break;
			} else {
				functionFlag = true;
			}
			int endBlockIndx = spareMemory.findClosingParenthesis(CODE,'{','}',false);
			functions.add(CODE.substring(funcIndx, endBlockIndx+1));
			CODE = CODE.replace(CODE.substring(funcIndx, endBlockIndx+1),"");
		} while(functionFlag);
		for(String function : functions) {
			function = function.substring("function".length()).trim();
			int parenthesisIndx = function.indexOf('(');
			if(parenthesisIndx!=-1) {
				System.out.println("parenthesis exists");
 				int closingParenthesisIndx = function.indexOf(')');
 				if(closingParenthesisIndx!=-1) {
 					System.out.println("parenthesis closing exists");
 					if(function.substring(closingParenthesisIndx+1).trim().startsWith("{")) {
 						System.out.println("valid function");
 						boolean emptyParaFlag = false;
 						if(function.substring(function.indexOf('(')).startsWith("()")) {
 							System.out.println("empty parameter");
 							emptyParaFlag = true;
 						}
 						int spaceIndx = function.indexOf(' ');
 						if(spaceIndx==-1 || spaceIndx>parenthesisIndx) {
 							System.out.println("NO RETURN TYPE [" + function);
 							String functionName = function.substring(0,parenthesisIndx).trim();
 							createFunction(function.substring(parenthesisIndx), functionName, !emptyParaFlag);
 							continue;
 						} else {
 							System.out.println("RETURN TYPE ["+function);
 							String returnType = function.substring(0, spaceIndx);
 							String functionName = function.substring(spaceIndx,parenthesisIndx).trim();
 							createFunction(function.substring(parenthesisIndx), functionName, returnType, !emptyParaFlag);
 							continue;
 						}
 					}
 				}
			}
			throw new InvalidSyntaxException("IMPROPER FUNCTION DECLARATION");
		}
		String [] splitCODE = CODE.split("[;|\n]");
		for(String line : splitCODE) {
			System.out.println("````````````````````NEW LINE ENCOUNTERED````````````````````````");
			StackMemory currentSTACK = new StackMemory(line, true);
		}
		System.out.println("````````````````````FINISHED INTERPRETING````````````````````````");
		for(int i=0;i<30;i++) {
			System.out.println("\n");
		}
		System.out.println("````````````````````HEAP MEMORY````````````````````````");
		System.out.println(this.Heap);
		System.out.println("````````````````````HEAP MEMORY````````````````````````");
	}
	public void createFunction(String CODE, String functionName, String returnType, boolean parametersFlag) throws Exception{
		((Map<String,TYPE_Function>)this.Heap.get("functionHeap")).put(functionName, new TYPE_Function(CODE, returnType, parametersFlag));
	}
	public void createFunction(String CODE, String functionName, boolean parametersFlag) throws Exception{
		((Map<String,TYPE_Function>)this.Heap.get("functionHeap")).put(functionName, new TYPE_Function(CODE, parametersFlag));
	}
	public void createLoop(String CODE, String ID) throws Exception {
		((Map<String,TYPE_LOOP>)this.Heap.get("loopHeap")).put(ID, new TYPE_LOOP(CODE));
	}
	private StackMemory populateStack(String code) {
		return new StackMemory(code, true);
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
