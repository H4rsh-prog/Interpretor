package com.interpretor.types;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.interpretor.service.Parser;
import com.interpretor.service.StackMemory;

public class INTERPRETED_STACK_OBJECT implements Serializable {
	private ArrayList<StackMemoryNODE> stackNODES = null;
	private Map<String, Object> Heap = new HashMap(Map.of("functionHeap", new HashMap<String, TYPE_Function>(),
															"loopHeap", new HashMap<String, TYPE_LOOP>()
															));
	private Parser parser = null;
	private StackMemory spareMemory = new StackMemory();
	public INTERPRETED_STACK_OBJECT(ArrayList<StackMemoryNODE> stackNODES, Map<String, Object> Heap) {
		this.stackNODES = stackNODES;
		this.Heap = Heap;
	}
	public void execute() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		System.out.println("`````````````````````STARTED EXECUTION`````````````````````");
		printHeap();
		for(StackMemoryNODE node : stackNODES) {
			new Parser(node,this.Heap,this.Heap);
			
		}
		System.out.println("`````````````````````FINISHED EXECUTION````````````````````");
	}
	public void TraverseNodes() {
		for(StackMemoryNODE node : stackNODES) {
			this.spareMemory.Traverse(node, 0);
		}
	}
	public void printHeap() {
		System.out.println(this.Heap);
	}
}
