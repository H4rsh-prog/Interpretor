package com.interpretor.exception;


public class InvalidNameException extends Exception{

	public InvalidNameException(String string) {
		System.err.println(string);
	}
	
}