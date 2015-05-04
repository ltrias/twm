package br.com.lptrias.twm.model;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class RoadMeshTest {
	
	private RoadMesh mesh;
	
	private static final String LOCATION_A = "A";
	private static final String LOCATION_B = "B";
	private static final int CHEAP_COST = 5;
	private static final int DEFAULT_COST = 10;
	private static final int EXPENSIVE_COST = 15;
	private static final int NEGATIVE_COST = -DEFAULT_COST;
	
	@Before
	public void setup(){
		mesh = new RoadMesh("Test mesh");
	}
	
	@Test
	public void addEntryToEmptyMesh(){
		mesh.addEntry(LOCATION_A, LOCATION_B, DEFAULT_COST);
		
		assertEquals(DEFAULT_COST, mesh.getCost(LOCATION_A, LOCATION_B));
	}
	
	@Test
	public void addSameEntryToMesh(){
		mesh.addEntry(LOCATION_A, LOCATION_B, DEFAULT_COST);
		mesh.addEntry(LOCATION_A, LOCATION_B, DEFAULT_COST);
		
		assertEquals(DEFAULT_COST, mesh.getCost(LOCATION_A, LOCATION_B));
		assertEquals(1, mesh.getSize());
	}
	
	@Test
	public void addCheaperEntryToMesh(){
		mesh.addEntry(LOCATION_A, LOCATION_B, DEFAULT_COST);
		mesh.addEntry(LOCATION_A, LOCATION_B, CHEAP_COST);

		assertEquals(CHEAP_COST, mesh.getCost(LOCATION_A, LOCATION_B));
		assertEquals(1, mesh.getSize());
	}
	
	@Test
	public void addMoreExpensiveEntryToMesh(){
		mesh.addEntry(LOCATION_A, LOCATION_B, DEFAULT_COST);
		mesh.addEntry(LOCATION_A, LOCATION_B, EXPENSIVE_COST);

		assertEquals(DEFAULT_COST, mesh.getCost(LOCATION_A, LOCATION_B));
		assertEquals(1, mesh.getSize());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void addLoopingEntry(){
		mesh.addEntry(LOCATION_A, LOCATION_A, DEFAULT_COST);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void addNegativeCostEntryToMesh(){
		mesh.addEntry(LOCATION_A, LOCATION_A, NEGATIVE_COST);
	}
}