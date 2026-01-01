package com.interpretor.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.interpretor.exception.InvalidSyntaxException;
import com.interpretor.service.Interpretor;
import com.interpretor.service.Parser;
import com.interpretor.service.StackMemory;

public class TYPE_LOOP {
	private StackMemoryNODE init = null;
	private StackMemoryNODE cond = null;
	private StackMemoryNODE upd = null;
	private List<StackMemoryNODE> stackNODES = new ArrayList();
	private Map<String, Object> loopHeap = new HashMap(Interpretor.Heap);
	private StackMemory spareMemory = new StackMemory("",false);
	private Parser parser = new Parser(null, loopHeap);
	public TYPE_LOOP(String CODE) throws Exception {
		String[] parameters = CODE.substring(1,CODE.indexOf(')')).split(";");
		if(parameters.length!=3) {
			throw new InvalidSyntaxException("UNEXPECTED NUMBER OF LOOP PARAMETERS");
		}
		this.init = this.spareMemory.ParseAndFill(null, new StackMemoryNODE("TERMINATOR"), parameters[0].trim(), false, false);
		this.cond = this.spareMemory.ParseAndFill(null, new StackMemoryNODE("TERMINATOR"), parameters[1].trim(), false, false);
		this.parser.parseValues(this.init);
		this.parser.parseValues(this.cond);
		if(this.cond.getDATA()==null || !(Value_Boolean.class.isInstance(this.cond.getDATA()))) {
			throw new InvalidSyntaxException("IMPROPER CONDITION FOR LOOP");
		}
		this.upd = this.spareMemory.ParseAndFill(null, new StackMemoryNODE("TERMINATOR"), parameters[2].trim(), false, false);
		CODE = CODE.substring(CODE.indexOf(')')+1);
		CODE = CODE.join("\n", CODE.split(";"));
		CODE = CODE.substring(1, CODE.length()-1).trim();
		for(String line: CODE.split("\n")) {
			line = line.trim();
			this.stackNODES.add(this.spareMemory.ParseAndFill(null, new StackMemoryNODE("TERMINATOR"), line, false, false));
		}
		this.spareMemory.Traverse(this.init, 0);
		this.spareMemory.Traverse(this.cond, 0);
		this.spareMemory.Traverse(this.upd, 0);
	}
	public void startLoop() throws Exception {
		this.spareMemory.Traverse(this.init,0);
		this.parser.parseValues(this.init);
		System.out.println("````````````````````````````````````````````````LOOPHEAP");
		System.out.println(this.loopHeap);
		System.out.println("````````````````````````````````````````````````LOOPHEAP");
		this.parser.parseValues(this.cond);
		int n = 0;
		while(((Boolean)this.cond.getDATA().Data)) {
			for(StackMemoryNODE node : this.stackNODES) {
				this.parser.parseValues(node);
			}
			this.parser.parseValues(this.upd);
			this.parser.parseValues(this.cond);
			System.out.println("````````````````````````````````````````````````LOOPHEAP");
			System.out.println(this.loopHeap);
			System.out.println("````````````````````````````````````````````````LOOPHEAP");
			n++;
			if(n>10) {
				this.cond.getRight().setOPERAND("2");
				this.spareMemory.Traverse(this.cond, 0);
			}
		}
	}
}
