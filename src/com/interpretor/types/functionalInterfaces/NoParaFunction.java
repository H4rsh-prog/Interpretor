package com.interpretor.types.functionalInterfaces;

@FunctionalInterface
public interface NoParaFunction<R> extends FunctionInterface {
	public R apply() throws Exception;
}

