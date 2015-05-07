package br.com.lptrias.twm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lptrias.twm.model.Route;
import br.com.lptrias.twm.service.RouteService;
import br.com.lptrias.twm.service.algo.DijkstraPath;
import br.com.lptrias.twm.service.algo.DijkstraPathFinder;
import br.com.lptrias.twm.service.conf.GraphDataProperties;

import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;

@Service
public class DefaultRouteService implements RouteService {
	
	@Autowired
	private TitanGraph graph;

	@Override
	public Route findCheapestRoute(String origin, String destination, String meshName) {
		try{
			Vertex originVertex = findVertexForLocation(origin);
			Vertex destinationVertex = findVertexForLocation(destination);
			
			
			DijkstraPath path = new DijkstraPathFinder(meshName).findPathBetween(originVertex, destinationVertex);
			
			return buildRouteFrom(meshName, path);
		}finally{
			graph.rollback();
		}
	}

	private Route buildRouteFrom(String meshName, DijkstraPath path) {
		Route result = null;
		
		if( path != null && !path.getSteps().isEmpty() ){
			List<Vertex> steps = path.getSteps();
			result = new Route();
			
			result.setCost(path.getCost());
			result.setMeshName(meshName);
			
			for (Vertex v : steps) {
				result.getSteps().add((String) v.getProperty(GraphDataProperties.LOCATION_NAME));
			}
		}
		
		return result;
	}

	private Vertex findVertexForLocation(String name) {
		Vertex result = null;
		
		Iterable<Vertex> i = graph.getVertices(GraphDataProperties.LOCATION_NAME, name);
		
		if( i.iterator() != null && i.iterator().hasNext() ){
			result = i.iterator().next();
		}
		
		return result;
	}

	private Route stubRoute(String origin, String destination, String meshName) {
		Route r = new Route();
		r.setMeshName(meshName);
		r.getSteps().add(origin);
		r.getSteps().add("intermediary step");
		r.getSteps().add(destination);
		
		return r;
	}

}
