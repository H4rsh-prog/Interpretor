package com.interpretor.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.interpretor.exception.InvalidSyntaxException;
import com.interpretor.service.Interpretor;
import com.interpretor.service.Parser;
import com.interpretor.service.StackMemory;

public class TYPE_LOOP implements Serializable {
	private StackMemoryNODE init = null;
	private StackMemoryNODE cond = null;
	private StackMemoryNODE upd = null;
	private List<StackMemoryNODE> stackNODES = new ArrayList();
	private Map<String, Object> loopHeap = null;
	private Map<String, Object> parentHeap = null;
	private StackMemory spareMemory = new StackMemory("",false);
	private Parser parser = null;
	public TYPE_LOOP(String CODE) throws Exception {
		String[] parameters = CODE.substring(1,CODE.indexOf(')')).split(";");
		if(parameters.length!=3) {
			throw new InvalidSyntaxException("UNEXPECTED NUMBER OF LOOP PARAMETERS");
		}
		this.init = this.spareMemory.ParseAndFill(null, new StackMemoryNODE("TERMINATOR"), parameters[0].trim(), false, false);
		this.cond = this.spareMemory.ParseAndFill(null, new StackMemoryNODE("TERMINATOR"), parameters[1].trim(), false, false);
//		this.parser.parseValues(this.init);
//		this.parser.parseValues(this.cond);
//		if(this.cond.getDATA()==null || !(Value_Boolean.class.isInstance(this.cond.getDATA()))) {
//			throw new InvalidSyntaxException("IMPROPER CONDITION FOR LOOP");
//		}
		this.upd = this.spareMemory.ParseAndFill(null, new StackMemoryNODE("TERMINATOR"), parameters[2].trim(), false, false);
		CODE = CODE.substring(CODE.indexOf(')')+1);
		CODE = CODE.join("\n", CODE.split(";"));
		CODE = CODE.substring(1, CODE.length()-1).trim();
		for(String line: CODE.split("\n")) {
			line = line.trim();
			this.stackNODES.add(this.spareMemory.ParseAndFill(null, new StackMemoryNODE("TERMINATOR"), line, false, false));
		}
	}
	public void startLoop(Map<String, Object> heapMemory, Map<String, Object> parentMemoryAddress) throws Exception {
		this.loopHeap =  heapMemory;
		this.parentHeap = parentMemoryAddress;
		this.parser = new Parser(null, this.loopHeap, this.parentHeap);
		System.out.println(this.loopHeap);
		this.parser.parseValues(this.init);
		this.parser.parseValues(this.cond);
		while(((Boolean)this.cond.getDATA().Data)) {
			for(StackMemoryNODE node : this.stackNODES) {
				this.parser.parseValues(node);
				this.spareMemory.Traverse(node, 0);
			}
			this.parser.parseValues(this.upd);
			this.parser.parseValues(this.cond);
		}
		System.out.println("MODIFYING CHANGES IN PARENT HEAP MEMORY FROM LOOP ");
		System.out.println("HEAP : "+this.loopHeap);
		System.out.println("PARENT : "+this.parentHeap);
		for(Entry<String, Object> mem : this.loopHeap.entrySet()) {
			if(this.parentHeap.containsKey(mem.getKey())) {
				System.out.println("LOOP KEY FOUND IN PARENT "+mem.getKey());
				if(!this.parentHeap.get(mem.getKey()).equals(mem.getValue())) {
					System.out.println("VALUE HAS CHANGED FROM "+this.parentHeap.get(mem.getKey())+" TO "+mem.getValue());
					this.parentHeap.put(mem.getKey(), mem.getValue());
				}
			}
		}
	}
}
