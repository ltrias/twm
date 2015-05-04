package br.com.lptrias.twm.model;

import org.junit.Test;


public class MeshEntryTest {
	@Test(expected=IllegalArgumentException.class)
	public void createLoopingEntry(){
		new MeshEntry("", "", 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void createNegativeCostEntryToMesh(){
		new MeshEntry("A", "B", -10);
	}
}
