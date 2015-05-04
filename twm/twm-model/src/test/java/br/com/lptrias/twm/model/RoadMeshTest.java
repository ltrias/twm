package br.com.lptrias.twm.model;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import br.com.lptrias.twm.model.RoadMesh;
import br.com.lptrias.twm.model.MeshEntry;


public class RoadMeshTest {
	
	private RoadMesh mesh;
	private static MeshEntry E;
	private static MeshEntry SAME_COST_THAN_E;
	private static MeshEntry CHEAPER_THAN_E;
	private static MeshEntry MORE_EXPENSIVE_THAN_E;
	
	@BeforeClass
	public static void setupClass(){
		E = new MeshEntry("A", "B", 10);
		SAME_COST_THAN_E = new MeshEntry("A", "B", 10);
		CHEAPER_THAN_E = new MeshEntry("A", "B", 5);
		MORE_EXPENSIVE_THAN_E = new MeshEntry("A", "B", 15);
	}
	
	@Before
	public void setup(){
		mesh = new RoadMesh("Test mesh");
	}

	@Test
	public void addEntryToEmptyMesh(){
		mesh.addEntry(E);
		
		MeshEntry entry = mesh.getEntry(E.getOrigin(), E.getDestination());
		assertSame(E, entry);
	}
	
	@Test
	public void addSameCostEntryToMesh(){
		mesh.addEntry(E);
		mesh.addEntry(SAME_COST_THAN_E);
		
		MeshEntry entry = mesh.getEntry(E.getOrigin(), E.getDestination());
		assertSame(E, entry);
	}
	
	@Test
	public void addCheaperEntryToMesh(){
		mesh.addEntry(E);
		mesh.addEntry(CHEAPER_THAN_E);
		
		MeshEntry entry = mesh.getEntry(E.getOrigin(), E.getDestination());
		
		assertSame(CHEAPER_THAN_E, entry);
		assertEquals(1, mesh.getSize());
	}
	
	@Test
	public void addMoreExpensiveEntryToMesh(){
		mesh.addEntry(E);
		mesh.addEntry(MORE_EXPENSIVE_THAN_E);
		
		MeshEntry entry = mesh.getEntry(E.getOrigin(), E.getDestination());
		
		assertSame(E, entry);
		assertEquals(1, mesh.getSize());
	}
}
