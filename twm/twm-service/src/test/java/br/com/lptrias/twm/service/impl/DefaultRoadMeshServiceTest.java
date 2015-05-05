package br.com.lptrias.twm.service.impl;

import static br.com.lptrias.twm.service.conf.GraphDataProperties.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.lptrias.twm.model.RoadMesh;

import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.VertexQuery;

public class DefaultRoadMeshServiceTest {

	private DefaultRoadMeshService service;
	
	@Mock
	private TitanGraph graph;
	
	@Before
	public void setupClass(){
		MockitoAnnotations.initMocks(this);
		service = new DefaultRoadMeshService(graph);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void saveAlreadySavedMesh(){
		ArrayList<Edge> edges = new ArrayList<>();
		edges.add(new DummyEdge());
		when(graph.getEdges(eq(MESH_NAME), anyString())).thenReturn(edges);
		
		service.saveMesh(new RoadMesh(""));
	}
	
	@Test
	public void saveNewMesh(){
		when(graph.addVertex(anyObject())).thenReturn(new DummyVertex());
		
		RoadMesh mesh = new RoadMesh("Test Mesh");
		mesh.addEntry("A", "B", 10);
		mesh.addEntry("B", "C", 14);
		
		service.saveMesh(mesh);
		
		InOrder io = inOrder(graph);
		io.verify(graph).getEdges(eq(MESH_NAME), eq("Test Mesh"));
		io.verify(graph).getVertices(eq(LOCATION_NAME), eq("A"));
		io.verify(graph).addVertex(any(Vertex.class));
		io.verify(graph).getVertices(eq(LOCATION_NAME), eq("B"));
		io.verify(graph).addVertex(any(Vertex.class));
		io.verify(graph).addEdge(eq(null), any(Vertex.class), any(Vertex.class), eq("Test Mesh"));
		io.verify(graph).getVertices(eq(LOCATION_NAME), eq("B"));
		io.verify(graph).addVertex(any(Vertex.class));
		io.verify(graph).getVertices(eq(LOCATION_NAME), eq("C"));
		io.verify(graph).addVertex(any(Vertex.class));
		io.verify(graph).addEdge(eq(null), any(Vertex.class), any(Vertex.class), eq("Test Mesh"));
		
		io.verify(((TitanGraph)graph)).commit();
		
		verifyNoMoreInteractions(graph);
	}
	
	@Test
	public void saveNewWithExistingLocations(){
		ArrayList<Vertex> vertices = new ArrayList<>();
		vertices.add(new DummyVertex());
		
		when(graph.getVertices(eq(LOCATION_NAME), eq("B"))).thenReturn(vertices);
		when(graph.addVertex(anyObject())).thenReturn(new DummyVertex());
		
		RoadMesh mesh = new RoadMesh("Test Mesh");
		mesh.addEntry("A", "B", 10);
		mesh.addEntry("B", "C", 14);
		
		service.saveMesh(mesh);
		
		InOrder io = inOrder(graph);
		io.verify(graph).getEdges(eq(MESH_NAME), eq("Test Mesh"));
		io.verify(graph).getVertices(eq(LOCATION_NAME), eq("A"));
		io.verify(graph).addVertex(any(Vertex.class));
		io.verify(graph).getVertices(eq(LOCATION_NAME), eq("B"));
		io.verify(graph).addEdge(eq(null), any(Vertex.class), any(Vertex.class), eq("Test Mesh"));
		io.verify(graph).getVertices(eq(LOCATION_NAME), eq("B"));
		io.verify(graph).getVertices(eq(LOCATION_NAME), eq("C"));
		io.verify(graph).addVertex(any(Vertex.class));
		io.verify(graph).addEdge(eq(null), any(Vertex.class), any(Vertex.class), eq("Test Mesh"));
		
		io.verify(((TitanGraph)graph)).commit();
		
		verifyNoMoreInteractions(graph);
	}
	
	private static final class DummyEdge implements Edge {

		@Override
		public Object getId() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T getProperty(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<String> getPropertyKeys() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub

		}

		@Override
		public <T> T removeProperty(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setProperty(String arg0, Object arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Vertex getVertex(Direction arg0) throws IllegalArgumentException {
			// TODO Auto-generated method stub
			return null;
		}

	}
	
	private static final class DummyVertex implements Vertex {

		@Override
		public Object getId() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T getProperty(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<String> getPropertyKeys() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub

		}

		@Override
		public <T> T removeProperty(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setProperty(String arg0, Object arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public Edge addEdge(String arg0, Vertex arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Iterable<Edge> getEdges(Direction arg0, String... arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Iterable<Vertex> getVertices(Direction arg0, String... arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public VertexQuery query() {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
