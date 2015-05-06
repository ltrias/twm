package br.com.lptrias.twm.service.impl;

import static br.com.lptrias.twm.service.conf.GraphDataProperties.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lptrias.twm.model.RoadMesh;
import br.com.lptrias.twm.model.RoadMesh.EntryKey;
import br.com.lptrias.twm.service.RoadMeshService;
import br.com.lptrias.twm.service.conf.GraphDataProperties;
import br.com.lptrias.twm.service.exception.GraphModificationException;

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
	public void saveMesh(RoadMesh mesh) throws GraphModificationException {
		LOGGER.debug("saveMesh called with: " + mesh);
		try{
			if( !validSave(mesh) ){
				return;
			}
			
			for (EntryKey t : mesh.getTransitions()) {
				Vertex o = addVertexIfNecessary(t.getOrigin());
				Vertex d = addVertexIfNecessary(t.getDestination());
				
				Edge edge = graph.addEdge(null, o, d, mesh.getName());
				edge.setProperty(TRANSITION_COST, mesh.getCost(t.getOrigin(), t.getDestination()));
			}
			
			graph.commit();
		}catch(Exception e){
			graph.rollback();
			
			throw new GraphModificationException(e);
		}
		
	}
	
	@Override
	public void updateMesh(RoadMesh mesh) throws GraphModificationException {
		LOGGER.debug("updateMesh called with: " + mesh);
		try{
			if( !validUpdate(mesh) ){
				return;
			}
			
		}catch(Exception e){
			graph.rollback();
			
			throw new GraphModificationException(e);
		}
		
	}

	private boolean validUpdate(RoadMesh mesh) {
		if( mesh == null ){
			return false;
		}
		
		Iterable<Edge> edges = graph.getEdges(MESH_NAME, mesh.getName());
		
		
		if( edges == null || !edges.iterator().hasNext() ){
			throw new UnsupportedOperationException("Mesh " + mesh.getName() + " has not been saved yet, use save instead");
		}
		
		return true;
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

	private boolean validSave(RoadMesh mesh) {
		if( mesh == null ){
			return false;
		}
		
		Iterable<Edge> edges = graph.getEdges(GraphDataProperties.LABEL, mesh.getName());
		
		if( edges != null && edges.iterator().hasNext() ){
			throw new UnsupportedOperationException("Mesh " + mesh.getName() + " has already been saved, use update instead");
		}
		
		return true;
	}
}
