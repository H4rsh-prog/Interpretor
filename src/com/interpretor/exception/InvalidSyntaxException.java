package com.interpretor.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidSyntaxException extends Exception {

	public InvalidSyntaxException(String string) {
		System.err.println(string);
	}

}
