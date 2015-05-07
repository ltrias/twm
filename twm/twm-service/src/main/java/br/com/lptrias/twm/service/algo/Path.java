package br.com.lptrias.twm.service.algo;

import java.util.ArrayList;
import java.util.List;

import com.tinkerpop.blueprints.Vertex;

public class Path {
	protected List<Vertex> steps;
	
	public Path() {
		steps = new ArrayList<>();
	}

	public List<Vertex> getSteps() {
		return steps;
	}
	
	String getShortPath(String propertyName){
		StringBuilder sb = new StringBuilder();
		
		for (Vertex v : steps) {
			sb.append(v.getProperty(propertyName));
		}
		
		return sb.toString();
	}
}
