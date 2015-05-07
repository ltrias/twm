package br.com.lptrias.twm.service.impl;

import static br.com.lptrias.twm.service.conf.GraphDataProperties.*;
import static br.com.lptrias.twm.service.util.GraphUtil.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lptrias.twm.model.RoadMesh;
import br.com.lptrias.twm.model.RoadMesh.EntryKey;
import br.com.lptrias.twm.service.RoadMeshService;
import br.com.lptrias.twm.service.exception.GraphModificationException;

import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;

/**
 * 
 * @author ltrias
 *
 */
@Service
public class DefaultRoadMeshService implements RoadMeshService {
	private static final Logger LOGGER = Logger.getLogger(DefaultRoadMeshService.class);
	
	private TitanGraph graph;

	@Autowired
	public DefaultRoadMeshService(TitanGraph graph) {
		this.graph = graph;
	}

	@Override
	public void saveMesh(RoadMesh mesh) throws GraphModificationException {
		try{
			if( !validSave(mesh) ){
				return;
			}
			
			for (EntryKey t : mesh.getTransitions()) {
				Vertex o = addVertexIfNecessary(graph, t.getOrigin());
				Vertex d = addVertexIfNecessary(graph, t.getDestination());
				
				int cost = mesh.getCost(t.getOrigin(), t.getDestination());
				
				LOGGER.debug("Adding edge with " + TRANSITION_COST + " = " + cost);
				Edge edge = graph.addEdge(null, o, d, mesh.getName());
				edge.setProperty(TRANSITION_COST, cost);
			}
			
			graph.commit();
		}catch(Exception e){
			graph.rollback();
			
			throw new GraphModificationException(e);
		}
		
		LOGGER.debug("Mesh: " + mesh + " saved");
	}
	
	@Override
	public void updateMesh(RoadMesh mesh) throws GraphModificationException {
		try{
			if( !validUpdate(mesh) ){
				return;
			}
			
			for (EntryKey t : mesh.getTransitions()) {
				LOGGER.debug("Checking vertices existence");
				Vertex o = addVertexIfNecessary(graph, t.getOrigin());
				Vertex d = addVertexIfNecessary(graph, t.getDestination());
				
				Edge e = findEdgeBetween(o, d, mesh.getName());
				
				if( e == null ){
					e = graph.addEdge(null, o, d, mesh.getName());
				}
				
				
				int cost = mesh.getCost(t.getOrigin(), t.getDestination());
				LOGGER.debug("Setting " + TRANSITION_COST + " = " + cost + " to edge " + e);
				e.setProperty(TRANSITION_COST, cost);
			}
			
			graph.commit();
		}catch(Exception e){
			graph.rollback();
			
			throw new GraphModificationException(e);
		}
		
		LOGGER.debug("Mesh: " + mesh + " updated");
	}
	
	Edge findEdgeBetween(Vertex a, Vertex b, String meshName){
		Iterable<Edge> edge = new GremlinPipeline(a).outE(meshName).as("edge").inV().back("edge");
		
		
		return edge != null && edge.iterator().hasNext() ? edge.iterator().next() : null;
	}

	private boolean validUpdate(RoadMesh mesh) {
		if( mesh == null ){
			return false;
		}
		
		
		if( !hasEdges(mesh.getName()) ){
			throw new UnsupportedOperationException("Mesh " + mesh.getName() + " has not been saved yet, use save instead");
		}
		
		return true;
	}

	boolean hasEdges(String name) {
		Iterable<Edge> edges = graph.getEdges(LABEL, name);
		
		return edges != null && edges.iterator().hasNext();
	}

	private boolean validSave(RoadMesh mesh) {
		if( mesh == null ){
			return false;
		}
		
		if( hasEdges(mesh.getName()) ){
			throw new UnsupportedOperationException("Mesh " + mesh.getName() + " has already been saved, use update instead");
		}
		
		return true;
	}
}
