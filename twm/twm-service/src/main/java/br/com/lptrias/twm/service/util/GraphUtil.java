package br.com.lptrias.twm.service.util;

import static br.com.lptrias.twm.service.conf.GraphDataProperties.*;

import org.apache.log4j.Logger;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public abstract class GraphUtil {
	
	private static final Logger LOGGER = Logger.getLogger(GraphUtil.class);
	
	public static Vertex addVertexIfNecessary(Graph graph, String locationName) {
		Vertex v = null;
		
		Iterable<Vertex> vertices = graph.getVertices(LOCATION_NAME, locationName);
		if( vertices == null || !vertices.iterator().hasNext() ){
			LOGGER.debug("Adding vertex with " + LOCATION_NAME + " = " + locationName);
			
			v = graph.addVertex(null);
			v.setProperty(LOCATION_NAME, locationName);
		} else {
			v = vertices.iterator().next();
		}
		
		return v;
	}

}
