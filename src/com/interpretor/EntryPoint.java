package com.interpretor;

import com.interpretor.service.Interpretor;

public class EntryPoint {
	private static Interpretor INTERPRETOR = new Interpretor(); 
	public static void main(String gg[]) {
		try {
			INTERPRETOR.render();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
