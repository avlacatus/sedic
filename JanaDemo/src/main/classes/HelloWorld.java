package main.classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class HelloWorld {
	public static final String NS = "http://www.infoiasi.ro/wad/ontologies/sedic#";
	
	public static void main(String[] args) {
		String inputFileName = "../sedic.owl";
		
		OntModel om = createModelFromFile(inputFileName);
		String string = readFile("../ChemicalsAndDrugs.txt");
		String[] paragraphs = string.split("\n\n");
		for (String par : paragraphs) {
			System.out.println("\nnew par\n");
			String[] lines = par.split("\n");
			String header = extractOntName(lines[0]);
			OntClass headerClass = om.getResource(NS + header).as(
					OntClass.class);
			System.out.println("header: " + headerClass);
			for (int  i = 1; i < lines.length; i++) {
				boolean isInstance = shouldBeInstance(lines[i]);
				String line = extractOntName(lines[i]);
				if (isInstance) {
					addInstanceToClass(om, headerClass, line);
					System.out.println("instance: " + line);
				} else {
					addSubclassToClass(om, headerClass, line);
					System.out.println("class: " + line);
				}
				
			}
			
		}

		// om.write(System.out, "RDF/XML");
		// saveModelToFile(om, "../sedic.owl");
	}
	
	private static String extractOntName(String line) {
		return line.trim().substring(0, line.indexOf("[")).trim().replace(' ', '_');
	}
	
	private static boolean shouldBeInstance(String line) {
		return !line.trim().endsWith("+");
	}

	private static String readFile(String fileName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append('\n');
				line = br.readLine();
			}
			String everything = sb.toString();
			br.close();
			return everything;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	private static void addSubclassToClass(OntModel om, OntClass superClass,
			String subclass) {
		OntClass subClass = om.createClass(NS + subclass);
		superClass.addSubClass(subClass);
	}
	
	private static void addInstanceToClass(OntModel om, OntClass superClass,
			String subclass) {
		superClass.createIndividual(NS + subclass);
	}

	private static void saveModelToFile(OntModel om, String filename) {
		FileWriter out = null;
		try {
			out = new FileWriter(filename);
			om.write(out, "RDF/XML");
		} catch (IOException e) {

		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException ignore) {
				}
			}
		}
	}
	
	private static OntModel createModelFromFile(String filename) {
		Model model = ModelFactory.createDefaultModel();
		InputStream in = FileManager.get().open(filename);
		if (in == null) {
			throw new IllegalArgumentException("File: " + filename
					+ " not found");
		}

		model.read(in, null);
		OntModel om = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM,
				model);
		return om;
	}
}
