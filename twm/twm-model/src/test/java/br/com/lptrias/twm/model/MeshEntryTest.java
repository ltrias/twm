package br.com.lptrias.twm.model;

import static org.junit.Assert.*;

import org.junit.Test;


public class MeshEntryTest {
	@Test(expected=IllegalArgumentException.class)
	public void addLoopingEntryToMesh(){
		assertTrue(false);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void addNEgativeCostEntryToMesh(){
		assertTrue(false);
	}
}
