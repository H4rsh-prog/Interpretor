package com.interpretor.types.functionalInterfaces;

import com.interpretor.exception.InvalidNameException;

@FunctionalInterface
public interface TwoParaFunction<A,B,R> extends FunctionInterface{
	public R apply(A a, B b) throws Exception;
}
