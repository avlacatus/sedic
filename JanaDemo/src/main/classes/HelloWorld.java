package main.classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.jena.atlas.lib.Pair;
import org.apache.jena.atlas.logging.Log;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.reasoner.TriplePattern;
import com.hp.hpl.jena.reasoner.transitiveReasoner.TransitiveGraphCache;
import com.hp.hpl.jena.reasoner.transitiveReasoner.TransitiveReasoner;
import com.hp.hpl.jena.reasoner.transitiveReasoner.TransitiveReasonerFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.RDFS;

public class HelloWorld {
	public static final String NS = "http://www.infoiasi.ro/wad/ontologies/sedic#";

	public static void main(String[] args) {
		String inputFileName = "../sedic.owl";

		OntModel om = createModelFromFile(inputFileName);
		assignUniqueIds(om);
		// addRemedies(om, "../plants.tsv");
		// addSubclassHierarchy(om, "../missing.txt");
		// addPlants(om, "../plants.tsv");
		// addPartPlant(om, "../partplant.txt");
		// inspectMissingResources(om, "../mesh_diseases_drugs.tsv");
		// om.write(System.out, "RDF/XML");
		 saveModelToFile(om, "../sedic1.owl");
	}

	private static void assignUniqueIds(OntModel om) {
		OntClass plantClass = om.getResource(NS + "Plant").as(OntClass.class);
		OntClass remedyClass = om.getResource(NS + "Remedy").as(OntClass.class);
		OntClass plantUsageClass = om.getResource(NS + "PlantUsage").as(OntClass.class);
		OntClass diseaseClass = om.getResource(NS + "Diseases").as(OntClass.class);
		OntClass drugsClass = om.getResource(NS + "Chemicals_and_Drugs").as(OntClass.class);
		
		ExtendedIterator<Individual> listIndividuals = om.listIndividuals(plantClass);
		Property hasId = om.getProperty(NS + "has_plant_id");
		int id = 10000;
		while (listIndividuals.hasNext()) {
			Individual plant = listIndividuals.next();
			plant.addProperty(hasId, id + "");
			id++;
			log(plant + "\n");
		}
		
		
		id = 100;
		listIndividuals = om.listIndividuals(plantUsageClass);
		hasId = om.getProperty(NS + "has_plant_usage_id");
		while (listIndividuals.hasNext()) {
			Individual plantUsage = listIndividuals.next();
			plantUsage.addProperty(hasId, id + "");
			id++;
			log(plantUsage + "\n");
		}
		

		hasId = om.getProperty(NS + "has_disease_id");
		id = 30000;
		ExtendedIterator<Individual> listIndividualss = om.listIndividuals(diseaseClass);
		while (listIndividualss.hasNext()) {
			Individual disease = listIndividualss.next();
			disease.addProperty(hasId, id + "");
			id++;
			log(disease + "\n");
		}
		
		
		
		hasId = om.getProperty(NS + "has_remedy_id");
		id = 20000;
		listIndividuals = om.listIndividuals(remedyClass);
		while (listIndividuals.hasNext()) {
			Individual remedy = listIndividuals.next();
			remedy.addProperty(hasId, id + "");
			id++;
			log(remedy + "\n");
		}
		
		
		hasId = om.getProperty(NS + "has_adjuvant_id");
		id = 40000;
		listIndividuals = om.listIndividuals(drugsClass);
		while (listIndividuals.hasNext()) {
			Individual drug = listIndividuals.next();
			drug.addProperty(hasId, id + "");
			id++;
			log(drug + "\n");
		}
	}

	private static void addRemedies(OntModel om, String fileName) {
		String string = readFile(fileName);
		String[] paragraphs = string.split(";;\n");
		OntClass plantClass = om.getResource(NS + "Plant").as(OntClass.class);
		OntClass remedyClass = om.getResource(NS + "Remedy").as(OntClass.class);
		OntClass plantUsageClass = om.getResource(NS + "PlantUsage").as(OntClass.class);

		Property hasFrequentUsage = om.getProperty(NS + "hasFrequentUsage");
		Property hasReportedUsage = om.getProperty(NS + "hasReportedUsage");

		for (String parapraph : paragraphs) {
			String[] sections = parapraph.split(";");
			String plantName = extractResourceName(sections[0]);
			String[] frequentUsages;
			String[] reportedUsages;
			log(plantName + "\n");
			String remedyName = NS + "Remedy_" + plantName;
			Resource remedyResource = getExistingResource(om, remedyName);
			Individual remedy = null;
			if (remedyResource != null) {
				remedy = om.getResource(remedyName).as(Individual.class);
			}

			if (sections.length > 3) {
				frequentUsages = sections[2].split("\n");
				reportedUsages = sections[3].split("\n");

				addRemedyUsagesProperties(om, remedy, frequentUsages, hasFrequentUsage);
				addRemedyUsagesProperties(om, remedy, reportedUsages, hasReportedUsage);
			} else {
				log("NOT ENOUGH SECTIONS");
			}
			log("\n");
		}
	}

	private static void addRemedyUsagesProperties(OntModel om, Individual remedy, String[] usages,
			Property frequencyProperty) {

		Property hasAdjuvantUsage = om.getProperty(NS + "hasAdjuvantUsage");
		Property hasTherapeuticalUsage = om.getProperty(NS + "hasTherapeuticalUsage");

		for (String usage : usages) {
			if (usage.length() > 0) {
				String resName = NS + extractResourceName(usage);
				Resource usageResource = getExistingResource(om, resName);
				if (usageResource != null) {
					usageResource = om.getResource(resName);
					Pair<Boolean, Boolean> resType = isResourceDisease(om, usageResource);
					if (resType.getLeft()) {
						log("disease " + usageResource.getLocalName() + "\n");
					} else {
						log("drug " + usageResource.getLocalName() + "\n");
					}
					if (resType.getRight()) {
						OntClass parentClass = usageResource.as(OntClass.class);
						Individual duplicateIndividual = parentClass.createIndividual(parentClass.getURI());
						remedy.addProperty(frequencyProperty, duplicateIndividual);
						if (resType.getLeft()) {
							remedy.addProperty(hasTherapeuticalUsage, duplicateIndividual);
						} else {
							remedy.addProperty(hasAdjuvantUsage, duplicateIndividual);
						}
						log(frequencyProperty.getLocalName() + " class: " + usageResource.getLocalName() + "\n");
					} else {
						if (resType.getLeft()) {
							remedy.addProperty(hasTherapeuticalUsage, usageResource);
						} else {
							remedy.addProperty(hasAdjuvantUsage, usageResource);
						}
						log(frequencyProperty.getLocalName() + " indv: " + usageResource.getLocalName() + "\n");
						remedy.addProperty(frequencyProperty, usageResource);
					}
				}
			}
		}

	}

	/**
	 * isDisease, isClass
	 * 
	 * @param om
	 * @param resource
	 * @return
	 */
	private static Pair<Boolean, Boolean> isResourceDisease(OntModel om, Resource resource) {
		OntClass diseasesClass = om.getResource(NS + "Diseases").as(OntClass.class);
		OntClass drugsClass = om.getResource(NS + "Chemicals_and_Drugs").as(OntClass.class);

		boolean isDisease = false;
		ExtendedIterator<OntClass> diseasesSubclasses = diseasesClass.listSubClasses();
		ExtendedIterator<OntClass> drugsSubclasses = drugsClass.listSubClasses();

		while (diseasesSubclasses.hasNext()) {
			OntClass next = diseasesSubclasses.next();
			if (next.getURI().equals(resource.getURI())) {
				// log(next.getLocalName() + " disease subclass found\n");
				return new Pair<Boolean, Boolean>(true, true);
			}
		}

		while (drugsSubclasses.hasNext()) {
			OntClass next = drugsSubclasses.next();
			if (next.getURI().equals(resource.getURI())) {
				// log(next.getLocalName() + " drug subclass found\n");
				return new Pair<Boolean, Boolean>(false, true);
			}
		}

		om.setStrictMode(false);
		if (resource.canAs(Individual.class)) {
			Individual indiv = resource.as(Individual.class);
			ExtendedIterator<OntClass> indivOntClasses = indiv.listOntClasses(false);
			while (indivOntClasses.hasNext()) {
				OntClass next = indivOntClasses.next();
				if (next.getURI().startsWith(NS)) {
					if (isResourceDisease(om, next).getLeft()) {
						// log(indiv.getLocalName() + " disease instance found\n");
						return new Pair<Boolean, Boolean>(true, false);
					}
				}
			}

			// log(indiv.getLocalName() + " drug instance found\n");
			return new Pair<Boolean, Boolean>(false, false);
		}
		om.setStrictMode(true);
		return new Pair<Boolean, Boolean>(false, false);

	}

	private static void addPlants(OntModel om, String fileName) {
		String string = readFile(fileName);
		OntClass plantClass = om.getResource(NS + "Plant").as(OntClass.class);
		OntClass remedyClass = om.getResource(NS + "Remedy").as(OntClass.class);

		String[] paragraphs = string.split(";;\n");
		for (String parapraph : paragraphs) {
			String[] lines = parapraph.split("\n");
			String plantResName = extractResourceName(lines[0]);
			String remedyResName = "Remedy_" + plantResName;
			log("Plant: " + plantResName + "\n");
			log("Remedy: " + remedyResName + "\n\n");
			// addInstanceToClass(om, plantClass, plantResName);
			addInstanceToClass(om, remedyClass, remedyResName);
		}
	}

	private static void addPartPlant(OntModel om, String filename) {
		String string = readFile(filename);
		String[] lines = string.split("\n");
		OntClass headerClass = om.getResource(NS + "PlantUsage").as(OntClass.class);
		for (String line : lines) {
			String plantUsage = line.trim().replace(' ', '_');
			log(plantUsage + "\n");
			addInstanceToClass(om, headerClass, plantUsage);
		}
	}

	private static void log(String str) {
		System.out.printf(str);
	}

	private static void inspectMissingResources(OntModel om, String filename) {
		String string = readFile(filename);
		String[] lines = string.split("\n");
		char[] tab = { 9 };
		String tabString = new String(tab);
		int count = 0;
		for (String line : lines) {
			String[] values = line.split(tabString);
			// String header = extractOntName(lines[0]);
			// OntClass headerClass = om.getResource(NS +
			// header).as(OntClass.class);
			// System.out.println("header: " + headerClass);
			String value = extractResourceName(values[0]);
			if (getExistingResource(om, NS + value) != null) {

			} else {
				count++;
				System.out.println(values[0]);
			}
		}
		System.out.println(count);
	}

	private static Resource getExistingResource(OntModel om, String resName) {
		Resource toSearch = ResourceFactory.createResource(resName);
		if (om.containsResource(toSearch)) {
			return toSearch;
		} else {
			return null;
		}
	}

	private static void addSubclassHierarchy(OntModel om, String filename) {
		String string = readFile(filename);
		String[] paragraphs = string.split("\n\n");
		for (String par : paragraphs) {
			if (par.length() > 0) {
				System.out.println("\n");
				String[] lines = par.split("\n");
				if (lines.length > 0 && lines[0].length() > 0) {
					String header = extractOntName(lines[0]);
					OntClass headerClass = om.getResource(NS + header).as(OntClass.class);
					System.out.println("header: " + headerClass);
					for (int i = 1; i < lines.length; i++) {
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
			}
		}
	}

	// Map<String, ArrayList<String>> plantsPartsMapping = new HashMap<String,
	// ArrayList<String>>();
	// String partss = readFile("../plants_and_parts.txt");
	// String[] pars = partss.split("\n\n");
	// for (String par : pars) {
	// String[] lines = par.split("\n");
	// ArrayList<String> parts = new ArrayList<>();
	// for (int i = 1; i < lines.length; i++) {
	// parts.add(lines[i]);
	// }
	// plantsPartsMapping.put(lines[0], parts);
	// }
	//
	// Property hasUsageProp = om.getProperty(NS + "hasUsage");
	//
	// ExtendedIterator<? extends OntResource> remedyInstances = remedyClass
	// .listInstances();
	// while (remedyInstances.hasNext()) {
	// Individual next = (Individual) remedyInstances.next();
	// log(next.getLocalName()+ "\n");
	// String plantName = next.getLocalName().replaceAll("Remedy_", "");
	// ArrayList<String> parts = plantsPartsMapping.get(plantName);
	// for (String part : parts) {
	// Individual plantUsageIndividual = om.getResource(NS +
	// part).as(Individual.class);
	// log(plantUsageIndividual + "\n");
	// next.addProperty(hasUsageProp, plantUsageIndividual);
	// }
	// log("\n");
	// }

	private static String extractOntName(String line) {
		return line.trim().substring(0, line.indexOf("[")).trim().replace(' ', '_');
	}

	private static String extractResourceName(String line) {
		return line.replaceAll("\"", "").trim().replace(' ', '_');
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

	private static void addSubclassToClass(OntModel om, OntClass superClass, String subclass) {
		OntClass subClass = om.createClass(NS + subclass);
		superClass.addSubClass(subClass);
	}

	private static void addInstanceToClass(OntModel om, OntClass superClass, String subclass) {
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
			throw new IllegalArgumentException("File: " + filename + " not found");
		}

		model.read(in, null);
		OntModel om = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, model);
		return om;
	}
}
