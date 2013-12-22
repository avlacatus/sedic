package main.classes;

import java.io.InputStream;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class HelloWorld {

	public static void main(String[] args) {
		System.out.println("hei - ilu");
		String inputFileName = "sedic.owl";
		Model m = ModelFactory.createDefaultModel();
		InputStream in = FileManager.get().open(inputFileName);
		if (in == null) {
			throw new IllegalArgumentException("File: " + inputFileName
					+ " not found");
		}

		m.read(in, null);
		m.write(System.out, "Turtle");
	}

}
