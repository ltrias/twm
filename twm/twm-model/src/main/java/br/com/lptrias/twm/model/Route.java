package br.com.lptrias.twm.model;

import java.util.ArrayList;
import java.util.List;

public class Route {
	private String meshName;
	private float cost;
	private List<String> steps;
	
	public Route(){
		steps = new ArrayList<>();
	}
	
	public String getMeshName() {
		return meshName;
	}
	public void setMeshName(String meshName) {
		this.meshName = meshName;
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	public List<String> getSteps() {
		return steps;
	}
	public void setSteps(List<String> steps) {
		this.steps = steps;
	}

	@Override
	public String toString() {
		return "Route [meshName=" + meshName + ", cost=" + cost + ", steps="
				+ steps + "]";
	}
	
	
}
