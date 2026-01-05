package com.interpretor;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import com.interpretor.service.Interpretor;
import com.interpretor.types.INTERPRETED_STACK_OBJECT;

public class EntryPoint {
	private static Interpretor INTERPRETOR = new Interpretor(); 
	public static void main(String gg[]) {
		try {
			INTERPRETOR.render(new File("input_SourceCode"));
			INTERPRETED_STACK_OBJECT outputObject = (INTERPRETED_STACK_OBJECT) new ObjectInputStream(new FileInputStream(new File("output_Object.o"))).readObject();
			outputObject.execute();
			System.out.println("`````````````````````STARTED TRAVERSAL`````````````````````");
			outputObject.TraverseNodes();
			System.out.println("`````````````````````FINISHED TRAVERSAL````````````````````");
			outputObject.printHeap();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
