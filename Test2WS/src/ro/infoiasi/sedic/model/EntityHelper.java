package ro.infoiasi.sedic.model;

import java.io.InputStream;

import ro.infoiasi.sedic.OntologyConstants;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class EntityHelper {
	private OntModel om;

	protected EntityHelper() {
		String inputFileName = OntologyConstants.getOntologyFilePath();
		om = createModelFromFile(inputFileName);
	}

	private OntModel createModelFromFile(String filename) {
		Model model = ModelFactory.createDefaultModel();
		InputStream in = FileManager.get().open(filename);
		if (in == null) {
			throw new IllegalArgumentException("File: " + filename + " not found");
		}

		model.read(in, null);
		OntModel om = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_TRANS_INF, model);
		return om;
	}

	protected OntModel getOntModel() {
		return om;
	}
}
