package br.com.lptrias.twm.service.algo;



import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

import static br.com.lptrias.twm.service.conf.GraphDataProperties.*;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public class DijkstraPathFinder implements PathFinder{
	
	private static final String MIN_DIST = "minDist";
	private String meshName;
	
	public DijkstraPathFinder(String meshName) {
		this.meshName = meshName;
	}
	
	@Override
	public DijkstraPath findPathBetween(Vertex origin, Vertex destination) {
		computePaths(origin);

		if( destination.getProperty("prev") == null ){
			return null;
		}
		
		DijkstraPath result = new DijkstraPath();
		
		for (Vertex vertex = destination; vertex != null; vertex = vertex.getProperty("prev")){
			result.steps.add(vertex);
		}
		result.setCost((Integer)destination.getProperty(MIN_DIST));
		
		Collections.reverse(result.steps);
		
		return result;
	}

	private void computePaths(Vertex start) {
		start.setProperty(MIN_DIST, 0);
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>(11, new VertexComparator());
		vertexQueue.add(start);

		while (!vertexQueue.isEmpty()) {
			Vertex cur = vertexQueue.poll();

			for (Edge e : cur.getEdges(Direction.OUT, meshName)) {
				Vertex v = e.getVertex(Direction.IN);
				
				int weight = e.getProperty(TRANSITION_COST);
				int distanceThroughCur = (Integer)cur.getProperty(MIN_DIST) + weight;
				if (distanceThroughCur < distOrInf(v) ) {
					vertexQueue.remove(v);
					v.setProperty(MIN_DIST, distanceThroughCur);
					v.setProperty("prev", cur);
					vertexQueue.add(v);
				}
			}
		}
	}

	private static int distOrInf(Vertex v) {
		Object dist = v.getProperty(MIN_DIST);
		
		return dist != null ? (Integer) dist : Integer.MAX_VALUE;
	}
	
	private static final class VertexComparator implements Comparator<Vertex> {
		public int compare(Vertex v, Vertex o) {
			Integer distV = v.getProperty(MIN_DIST);
			Integer distO = o.getProperty(MIN_DIST);
			
			return Integer.compare(distV, distO);
		}

	}
}
