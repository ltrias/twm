package br.com.lptrias.twm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Represents a road mesh for request parsing purposes.
 *
 * @author ltrias
 * 
 */
public class RoadMesh {
	private String name;
	private Set<MeshEntry> entries;
	
	public RoadMesh(String name){
		this.name = name;
		this.entries = new HashSet<>();
	}
	
	public void addEntry(MeshEntry entry){
		
	}
	
	MeshEntry getEntry(String origin, String destination){
		return null;
	}
	
	int getSize(){
		return entries.size();
	}
}
