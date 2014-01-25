package ro.infoiasi.sedic.model;

import java.io.InputStream;

import ro.infoiasi.sedic.OntologyUtils;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class EntityHelper {
	private static OntModel om;

	protected EntityHelper() {
		if (om == null) {
			String inputFileName = OntologyUtils.getOntologyFilePath();
			om = createModelFromFile(inputFileName);
		}
	}

	private OntModel createModelFromFile(String filename) {
		Model model = ModelFactory.createDefaultModel();
		InputStream in = FileManager.get().open(filename);
		if (in == null) {
			throw new IllegalArgumentException("File: " + filename + " not found");
		}

		model.read(in, null);
		OntModel om = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, model);
		return om;
	}

	public OntModel getOntModel() {
		return om;
	}
	
	protected String getEntityName(String uri) {
		return uri.substring(OntologyUtils.NS.length()).replaceAll("_", " ");
	}
}
