package com.interpretor.types;

public class Data<T>{
	T Data;
	public Data(T Data) {
		this.Data = Data;
	}
	@Override
	public String toString() {
		return (this.Data)+"";
	}
}