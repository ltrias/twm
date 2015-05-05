package br.com.lptrias.twm.web.controller;

import org.junit.Before;
import org.junit.Test;

import br.com.lptrias.twm.model.RoadMesh;

public class MeshControllerTest {
	
	private MeshController controller = new MeshController();
	private RoadMesh mesh;
	
	@Before
	public void setup(){
		mesh = new RoadMesh("Controller test mesh");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMissingToken(){
		String meshData = "A B";
		
		controller.addDataTo(mesh, meshData);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testExtraToken(){
		String meshData = "A B 10 C D 20";
		
		controller.addDataTo(mesh, meshData);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidCost(){
		String meshData = "A B P";
		
		controller.addDataTo(mesh, meshData);
	}
}
