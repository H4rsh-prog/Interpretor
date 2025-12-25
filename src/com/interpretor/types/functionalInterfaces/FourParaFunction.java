package com.interpretor.types.functionalInterfaces;

@FunctionalInterface
public interface FourParaFunction<A,B,C,D,R> extends FunctionInterface{
	public R apply(A a, B b, C c, D d);
}