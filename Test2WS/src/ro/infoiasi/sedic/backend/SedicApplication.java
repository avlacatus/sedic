package ro.infoiasi.sedic.backend;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ServerProperties;

@ApplicationPath("/")
public class SedicApplication extends Application {
	
	public SedicApplication() {
		System.out.println("sedic app constructor");
	}

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(DiseaseWS.class);
		s.add(PlantWS.class);
		s.add(RemedyWS.class);
		return s;
	}

	@Override
	public Map<String, Object> getProperties() {
		final Map<String, Object> properties = new HashMap<String, Object>();
		// Enable Tracing support.
		properties.put(ServerProperties.TRACING, "ALL");
		return properties;
	}

}
