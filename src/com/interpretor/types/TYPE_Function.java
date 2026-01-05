package com.interpretor.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.interpretor.exception.InvalidSyntaxException;
import com.interpretor.service.Interpretor;
import com.interpretor.service.Parser;
import com.interpretor.service.StackMemory;

import lombok.Getter;
import lombok.Setter;

public class TYPE_Function extends com.interpretor.types.Data {
	private List<StackMemoryNODE> stackNODES = new ArrayList();
	@Getter
	private StackMemoryNODE returnNODE = null;
	private Map<String, Object> functionHeap = null;
	private Map<String, Object> parentHeap = null;
	@Setter
	private List<Object> ARGUMENTS = new ArrayList();
	private List<Class> PARAMETER_CLASS = new ArrayList();
	private List<String> PARAMETER_NAME = new ArrayList();
	private StackMemory spareMemory = null;
	private boolean hasParameters;
	private Class returnTypeClass = null;
	
	public TYPE_Function(String CODE, String returnType, boolean parametersFlag) throws Exception{
		System.out.println("```````````````````````````````````````FUNCTION CONSTRUCTOR");
		this.spareMemory = new StackMemory("",false);
		Class returnTypeClass = null;
		try {
			returnTypeClass = Class.forName("com.interpretor.types."+returnType);
			this.returnTypeClass = returnTypeClass;
		} catch (Exception e) {
			throw new InvalidSyntaxException("INVALID RETURN TYPE");
		}
		this.hasParameters = parametersFlag;
		if(this.hasParameters) {
			String[] PARAMETERS = CODE.substring(1,CODE.indexOf(')')).split("[,]");
			int indx=0;
			for(String para : PARAMETERS) {
				para = para.trim();
				indx++;
				if(!para.contains(" ")) {
					throw new InvalidSyntaxException("MISSING NAME IDENTIFIER FOR PARAMETER "+indx);
				}
				String type = para.substring(0,para.indexOf(' '));
				String name = para.substring(para.indexOf(' ')).trim();
				System.out.println("TYPE :: "+type+" NAME :: "+name);
				if(name.contains(" ")) {
					throw new InvalidSyntaxException("UNEXPECTED IDENTIFIER AFTER IN PARAMETER "+indx);
				}
				try {
					this.PARAMETER_CLASS.add(Class.forName("com.interpretor.types."+type));
				} catch (ClassNotFoundException e) {
					throw new InvalidSyntaxException("INVALID PARAMETER TYPE");
				}
				this.PARAMETER_NAME.add(name);
			}
		}
		CODE = CODE.substring(CODE.indexOf('{')+1,CODE.length()-1).trim();
		if(CODE=="") {
			return;
		}
		boolean returnFlag = false;
		for(String line : CODE.split("\n")) {
			line = line.trim();
			if(line.startsWith("return ")) {
				returnFlag = true;
			}
			this.stackNODES.add(this.spareMemory.ParseAndFill(null, new StackMemoryNODE("TERMINATOR"), line, false, false));
			if(returnFlag) {
				break;
			}
		}
		if(!returnFlag) {
			throw new InvalidSyntaxException("FUNCTION MUST HAVE A RETURN STATEMENT");
		} else {
			this.returnNODE = this.stackNODES.get(this.stackNODES.size()-1).getLeft();
		}
	}
	public TYPE_Function(String CODE, boolean parametersFlag) throws Exception {
		this.hasParameters = parametersFlag;
		if(this.hasParameters) {
			String[] PARAMETERS = CODE.substring(1,CODE.indexOf(')')).split("[,]");
			int indx=0;
			for(String para : PARAMETERS) {
				para = para.trim();
				indx++;
				if(!para.contains(" ")) {
					throw new InvalidSyntaxException("MISSING NAME IDENTIFIER FOR PARAMETER "+indx);
				}
				String type = para.substring(0,para.indexOf(' '));
				String name = para.substring(para.indexOf(' ')).trim();
				System.out.println("TYPE :: "+type+" NAME :: "+name);
				if(name.contains(" ")) {
					throw new InvalidSyntaxException("UNEXPECTED IDENTIFIER AFTER IN PARAMETER "+indx);
				}
				try {
					this.PARAMETER_CLASS.add(Class.forName("com.interpretor.types."+type));
				} catch (ClassNotFoundException e) {
					throw new InvalidSyntaxException("INVALID PARAMETER TYPE");
				}
				this.PARAMETER_NAME.add(name);
			}
		}
		CODE = CODE.substring(CODE.indexOf('{')+1,CODE.length()-1).trim();
		if(CODE=="") {
			return;
		}
		for(String line : CODE.split("\n")) {
			line = line.trim();
			if(line.startsWith("return ")) {
				throw new InvalidSyntaxException("VOID TYPE FUNCTIONS CANNOT HAVE A RETURN STATEMENT");
			}
			this.stackNODES.add(this.spareMemory.ParseAndFill(null, new StackMemoryNODE("TERMINATOR"), line, false, false));
		}
	}
	public void call(Map<String, Object> heapMemory, Map<String, Object> parentMemoryAddress) throws InvalidSyntaxException {
		this.functionHeap = heapMemory;
		this.parentHeap = parentMemoryAddress;
		System.out.println("FUNCTION CALLED "+this);
		int indx = 0;
		if(this.hasParameters) {
			for(Class clazz : this.PARAMETER_CLASS) {
				System.out.println(this.ARGUMENTS.get(indx));
				if(!(this.ARGUMENTS.get(indx).getClass()==clazz)) {
					throw new InvalidSyntaxException("INVALID DATA TYPE AT ARGUMENT "+indx+1 +" EXPECTED "+clazz+" BUT FOUND "+this.ARGUMENTS.get(indx));
				}
				this.functionHeap.put(this.PARAMETER_NAME.get(indx), this.ARGUMENTS.get(indx));
				indx++;
			}
		}
		this.functionHeap.put("REST", new ArrayList<Object>());
		for(indx=indx;indx<this.ARGUMENTS.size();indx++) {
			((ArrayList)this.functionHeap.get("REST")).add(this.ARGUMENTS.get(indx));
		}
		for(StackMemoryNODE node : this.stackNODES) {
			new Parser(node, this.functionHeap, this.parentHeap);
			this.spareMemory.Traverse(node, 0);
		}
		if(this.returnNODE.getDATA().getClass()!=this.returnTypeClass) {
			throw new InvalidSyntaxException("RETURN TYPE MISMATCH : EXPECTED RETURN TYPE = "+this.returnTypeClass+" BUT FOUND = "+this.returnNODE.getDATA().getClass());
		}
		System.out.println("MODIFYING CHANGES IN PARENT HEAP MEMORY FROM FUNCTION ");
		System.out.println("HEAP : "+this.functionHeap);
		System.out.println("PARENT : "+this.parentHeap);
		for(Entry<String, Object> mem : this.functionHeap.entrySet()) {
			if(this.parentHeap.containsKey(mem.getKey())) {
				System.out.println("FUNCTION KEY FOUND IN PARENT "+mem.getKey());
				if(!this.parentHeap.get(mem.getKey()).equals(mem.getValue())) {
					System.out.println("FUNCTION HAS CHANGED FROM "+this.parentHeap.get(mem.getKey())+" TO "+mem.getValue());
					this.parentHeap.put(mem.getKey(), mem.getValue());
				}
			}
		}
		System.out.println("`````````````````````````````````````````````````````````````````````````````FN HEAP");
		System.out.println(this.functionHeap);
		System.out.println("`````````````````````````````````````````````````````````````````````````````FN HEAP");
	}
//	public String toString() {
//		return "FUNCTION[ stackNODES:"+this.stackNODES+"; returnNODE:"+this.returnNODE+"; PARAMETERS[ CLASS:"+this.PARAMETER_CLASS+"; NAME:"+this.PARAMETER_NAME+"]; ARGUMENTS:"+this.ARGUMENTS+"; RETURN_TYPE:"+this.returnTypeClass+";]";
//	}
}
