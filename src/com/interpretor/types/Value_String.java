package com.interpretor.types;

import java.sql.DriverManager;

public class Value_String {
	public Value_String(String value) throws Exception {
		new Value(value, String.class).getInstance(String.class);
		
	}
}
