package com.interpretor.types;

public abstract class Data<T>{
	T Data;
	public Data(T Data) {
		this.Data = Data;
	}
	@Override
	public String toString() {
		return (this.Data)+"";
	}
//	abstract <T> T add(T DATA);
}