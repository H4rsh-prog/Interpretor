package com.interpretor.types;

import lombok.Getter;
import lombok.Setter;

public class StackMemoryNODE<T> {
	@Getter
	@Setter
	private String OPERAND;
	@Getter
	@Setter
	private com.interpretor.types.Data<T> DATA = null;
	@Getter
	@Setter
	private StackMemoryNODE top = null;			//the toString() method will throw a stackOverflow exception because of this node 
												//but i don't feel like fixing it because in a way you shouldn't be able to log a stack node
	@Getter
	@Setter
	private StackMemoryNODE left = null;
	@Getter
	@Setter
	private StackMemoryNODE right = null;
	public StackMemoryNODE(String OPERAND){
		this.OPERAND = OPERAND;
	}
	
	public String toString() {
		return "NODE[OPERAND = "+getOPERAND()+"; DATA = "+getDATA()+"; LEFT = "+getLeft()+"; RIGHT = "+getRight()+"]";
	}
}