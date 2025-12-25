package com.interpretor.types.functionalInterfaces;

@FunctionalInterface
public interface ThreeParaFunction<A,B,C,R> extends FunctionInterface{
	public R apply(A a, B b, C c);
}