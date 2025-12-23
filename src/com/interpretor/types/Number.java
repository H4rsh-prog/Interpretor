package com.interpretor.types;

public class Number<T>{
	T Data;
	public Number(T Data) {
		this.Data = Data;
	}
	@Override
	public String toString() {
		return (this.Data)+"";
	}
}