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
	public abstract <T> T add(Data DATA);
	public abstract <T> T sub(Data DATA);
	public abstract <T> T mul(Data DATA);
	public abstract <T> T div(Data DATA);
}