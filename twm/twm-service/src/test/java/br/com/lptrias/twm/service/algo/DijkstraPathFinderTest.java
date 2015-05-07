package br.com.lptrias.twm.service.algo;

import static br.com.lptrias.twm.service.conf.GraphDataProperties.*;
import static org.junit.Assert.*;

import org.junit.Test;

import br.com.lptrias.twm.service.util.GraphUtil;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;

public class DijkstraPathFinderTest {
	
	private static final String TEST_MESH = "test_mesh";
	private static final String CHALLENGE_BASE_CASE = "A B 10 B D 15 A C 20 C D 30 B E 50 D E 30";
	
	private Graph g;

	
	@Test
	public void testChallengeBaseCase(){
		g = graphFrom(CHALLENGE_BASE_CASE);
		Vertex o = g.getVertices(LOCATION_NAME, "A").iterator().next();
		Vertex d = g.getVertices(LOCATION_NAME, "D").iterator().next();
		
		DijkstraPath path = new DijkstraPathFinder(TEST_MESH).findPathBetween(o, d);
		assertEquals(25, path.getCost());
		assertEquals("ABD", path.getShortPath(LOCATION_NAME));
	}
	
	@Test
	public void testInexistentPath(){
		g = graphFrom("A B 10 B D 15 A C 20 C D 30 B E 50 D E 30");
		Vertex o = g.getVertices(LOCATION_NAME, "D").iterator().next();
		Vertex d = g.getVertices(LOCATION_NAME, "A").iterator().next();
		
		DijkstraPath path = new DijkstraPathFinder(TEST_MESH).findPathBetween(o, d);
		
		assertNull(path);
	}
	
	@Test
	public void testPathToSameVertex(){
		g = graphFrom("A B 10 B D 15 A C 20 C D 30 B E 50 D E 30");
		Vertex o = g.getVertices(LOCATION_NAME, "D").iterator().next();
		
		DijkstraPath path = new DijkstraPathFinder(TEST_MESH).findPathBetween(o, o);
		
		assertEquals(0, path.getCost());
		assertEquals("D", path.getShortPath(LOCATION_NAME));
	}
	
	@Test
	public void testPathFromVertexWithoutOutEdges(){
		g = graphFrom("A B 10 B D 15 A C 20 C D 30 B E 50 D E 30");
		Vertex o = g.getVertices(LOCATION_NAME, "E").iterator().next();
		Vertex d = g.getVertices(LOCATION_NAME, "A").iterator().next();
		
		DijkstraPath path = new DijkstraPathFinder(TEST_MESH).findPathBetween(o, d);
		
		assertNull(path);
	}
	
	@Test
	public void testCreationOrderOnEquivalentPaths(){
		g = graphFrom("A B 10 A C 10 B D 5 C D 5");
		Vertex o = g.getVertices(LOCATION_NAME, "A").iterator().next();
		Vertex d = g.getVertices(LOCATION_NAME, "D").iterator().next();
		
		DijkstraPath path = new DijkstraPathFinder(TEST_MESH).findPathBetween(o, d);
		
		assertEquals(15, path.getCost());
		assertEquals("ABD", path.getShortPath(LOCATION_NAME));
	}
	
	@Test
	public void testLoop(){
		g = graphFrom("A B 1 B C 1 C D 1 C A 1 D A 1");
		Vertex o = g.getVertices(LOCATION_NAME, "A").iterator().next();
		Vertex d = g.getVertices(LOCATION_NAME, "D").iterator().next();
		
		DijkstraPath path = new DijkstraPathFinder(TEST_MESH).findPathBetween(o, d);
		
		assertEquals(3, path.getCost());
		assertEquals("ABCD", path.getShortPath(LOCATION_NAME));
	}
	
	@Test
	public void testFreeTransitions(){
		g = graphFrom("A B 0 B C 0 C D 0 D E 0 E F 0 A F 1");
		Vertex o = g.getVertices(LOCATION_NAME, "A").iterator().next();
		Vertex d = g.getVertices(LOCATION_NAME, "F").iterator().next();
		
		DijkstraPath path = new DijkstraPathFinder(TEST_MESH).findPathBetween(o, d);
		
		assertEquals(0, path.getCost());
		assertEquals("ABCDEF", path.getShortPath(LOCATION_NAME));
	}
	
	private Graph graphFrom(String data){
		g = new TinkerGraph();
		String[] tokens = data.split(" ");
		
		if( tokens.length % 3 != 0 ){
			throw new RuntimeException("Invalid graph configuration " + data);
		}
		
		for(int i = 0; i < tokens.length; i +=3){
			Vertex o = GraphUtil.addVertexIfNecessary(g, tokens[i]);
			Vertex d = GraphUtil.addVertexIfNecessary(g, tokens[i + 1]);
			
			Edge e = g.addEdge(null, o, d, TEST_MESH);
			e.setProperty(TRANSITION_COST, Integer.valueOf(tokens[i + 2]));
		}
		
		return  g;
	}
}

