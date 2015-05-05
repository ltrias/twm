package br.com.lptrias.twm.service.impl;

import static br.com.lptrias.twm.service.conf.GraphDataProperties.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lptrias.twm.model.RoadMesh;
import br.com.lptrias.twm.model.RoadMesh.EntryKey;
import br.com.lptrias.twm.service.RoadMeshService;

import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * 
 * @author ltrias
 *
 */
@Service
public class DefaultRoadMeshService implements RoadMeshService {
	private static final Logger LOGGER = LogManager.getLogger();
	
	private TitanGraph graph;

	@Autowired
	public DefaultRoadMeshService(TitanGraph graph) {
		this.graph = graph;
	}

	@Override
	public void saveMesh(RoadMesh mesh) {
		LOGGER.debug("saveMesh called with: " + mesh);
		validSave(mesh);
		
		for (EntryKey t : mesh.getTransitions()) {
			Vertex o = addVertexIfNecessary(t.getOrigin());
			Vertex d = addVertexIfNecessary(t.getDestination());
			
			graph.addEdge(null, o, d, mesh.getName());
		}
		
		graph.commit();
	}

	private Vertex addVertexIfNecessary(String locationName) {
		Vertex v = null;
		
		Iterable<Vertex> vertices = graph.getVertices(LOCATION_NAME, locationName);
		if( vertices == null || !vertices.iterator().hasNext() ){
			v = graph.addVertex(null);
			v.setProperty(LOCATION_NAME, locationName);
		} else {
			v = vertices.iterator().next();
		}
		
		return v;
	}

	private void validSave(RoadMesh mesh) {
		if( mesh == null ){
			return;
		}
		
		Iterable<Edge> edges = graph.getEdges(MESH_NAME, mesh.getName());
		
		if( edges != null && edges.iterator().hasNext() ){
			throw new UnsupportedOperationException("Mesh " + mesh.getName() + " has already been saved, use update instead");
		}
	}

	@Override
	public void updateMesh(RoadMesh mesh) {
		LOGGER.debug("updateMesh called with: " + mesh);
	}

}
