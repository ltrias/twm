package br.com.lptrias.twm.service.impl;


import static br.com.lptrias.twm.service.conf.GraphDataProperties.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.lptrias.twm.model.RoadMesh;
import br.com.lptrias.twm.service.exception.GraphModificationException;

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
	
	@Test(expected=GraphModificationException.class)
	public void saveAlreadySavedMesh() throws GraphModificationException{
		ArrayList<Edge> edges = new ArrayList<>();
		edges.add(new DummyEdge());
		when(graph.getEdges(eq(LABEL), anyString())).thenReturn(edges);
		
		service.saveMesh(new RoadMesh(""));
	}
	
	@Test
	public void saveNullMesh() throws GraphModificationException{
		service.saveMesh(null);
		verifyNoMoreInteractions(graph);
	}
	
	@Test
	public void saveNewMesh() throws GraphModificationException{
		when(graph.addVertex(anyObject())).thenReturn(new DummyVertex());
		when(graph.addEdge(anyObject(), any(Vertex.class), any(Vertex.class), anyString())).thenReturn(new DummyEdge());
		
		RoadMesh mesh = new RoadMesh("Test Mesh");
		mesh.addEntry("A", "B", 10);
		mesh.addEntry("B", "C", 14);
		
		service.saveMesh(mesh);
		
		InOrder io = inOrder(graph);
		io.verify(graph).getEdges(eq(LABEL), eq("Test Mesh"));
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
	public void saveNewWithExistingLocations() throws GraphModificationException{
		ArrayList<Vertex> vertices = new ArrayList<>();
		vertices.add(new DummyVertex());
		
		when(graph.getVertices(eq(LOCATION_NAME), eq("B"))).thenReturn(vertices);
		when(graph.addVertex(anyObject())).thenReturn(new DummyVertex());
		when(graph.addEdge(anyObject(), any(Vertex.class), any(Vertex.class), anyString())).thenReturn(new DummyEdge());
		
		RoadMesh mesh = new RoadMesh("Test Mesh");
		mesh.addEntry("A", "B", 10);
		mesh.addEntry("B", "C", 14);
		
		service.saveMesh(mesh);
		
		InOrder io = inOrder(graph);
		io.verify(graph).getEdges(eq(LABEL), eq("Test Mesh"));
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
	
	@Test
	public void updateNullMesh() throws GraphModificationException{
		service.updateMesh(null);
		verifyNoMoreInteractions(graph);
	}
	
	@Test(expected=GraphModificationException.class)
	public void updateNotSavedMesh() throws GraphModificationException{
		when(graph.getEdges(eq(MESH_NAME), anyString())).thenReturn(Collections.EMPTY_LIST);
		
		service.updateMesh(new RoadMesh(""));
	}
	
	@Test @Ignore
	public void updateFullySavedMesh() throws GraphModificationException{
		DummyVertex aVertex = spy(new DummyVertex());
		aVertex.setProperty(LOCATION_NAME, "A");
		List<Vertex> aVertices = new ArrayList<>();
		aVertices.add(aVertex);
		when(graph.getVertices(eq(LOCATION_NAME), eq("A"))).thenReturn(aVertices);
		
		DummyVertex bVertex = new DummyVertex();
		bVertex.setProperty(LOCATION_NAME, "B");
		List<Vertex> bVertices = new ArrayList<>();
		bVertices.add(bVertex);
		when(graph.getVertices(eq(LOCATION_NAME), eq("B"))).thenReturn(bVertices);
		
		DummyEdge abEdge = spy(new DummyEdge());
		abEdge.setVertex(aVertex, Direction.IN);
		abEdge.setVertex(bVertex, Direction.OUT);
		
		DummyVertex cVertex = new DummyVertex();
		cVertex.setProperty(LOCATION_NAME, "C");
		List<Vertex> cVertices = new ArrayList<>();
		cVertices.add(cVertex);
		when(graph.getVertices(eq(LOCATION_NAME), eq("C"))).thenReturn(cVertices);
		
		
		
		RoadMesh mesh = new RoadMesh("Test Mesh");
		mesh.addEntry("A", "B", 10);
//		mesh.addEntry("B", "C", 14);
		
		service.updateMesh(mesh);
		
		InOrder io = inOrder(graph);
		io.verify(graph).getEdges(eq(MESH_NAME), eq("Test Mesh"));
		io.verify(graph).getVertices(eq(LOCATION_NAME), eq("A"));
		io.verify(graph).getVertices(eq(LOCATION_NAME), eq("B"));
		io.verify(aVertex).getEdges(Direction.OUT, eq("Test Mesh"));
		
		
//		
//		io.verify(graph).addVertex(any(Vertex.class));
//		io.verify(graph).addVertex(any(Vertex.class));
//		io.verify(graph).addEdge(eq(null), any(Vertex.class), any(Vertex.class), eq("Test Mesh"));
//		io.verify(graph).getVertices(eq(LOCATION_NAME), eq("B"));
//		io.verify(graph).addVertex(any(Vertex.class));
//		io.verify(graph).getVertices(eq(LOCATION_NAME), eq("C"));
//		io.verify(graph).addVertex(any(Vertex.class));
//		io.verify(graph).addEdge(eq(null), any(Vertex.class), any(Vertex.class), eq("Test Mesh"));
		
		io.verify(((TitanGraph)graph)).commit();
		
		verifyNoMoreInteractions(graph);
	}
	
	private static final class DummyEdge implements Edge {
		
		private Map<String, Object> p = new HashMap<>();
		private Vertex in, out;

		@Override
		public Object getId() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T getProperty(String arg0) {
			return (T)p.get(arg0);
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
			p.put(arg0, arg1);
		}

		@Override
		public String getLabel() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Vertex getVertex(Direction d) throws IllegalArgumentException {
			Vertex result = null;
			
			if( d == Direction.IN ){
				result = in;
			}else if ( d == Direction.OUT ){
				result = out;
			}else{
				throw new IllegalArgumentException();
			}
			
			return result;
		}
		
		public void setVertex(Vertex vertex, Direction d){
			if( d == Direction.IN ){
				in = vertex;
			}else if ( d == Direction.OUT ){
				out = vertex;
			}
		}
	}
	
	private static final class DummyVertex implements Vertex {
		
		private Map<String, Object> p = new HashMap<>();

		@Override
		public Object getId() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T getProperty(String arg0) {
			return (T)p.get(arg0);
		}

		@Override
		public Set<String> getPropertyKeys() {
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
			p.put(arg0, arg1);
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
