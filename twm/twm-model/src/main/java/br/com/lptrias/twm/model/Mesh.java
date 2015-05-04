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
public class Mesh {
	private String name;
	private Set<MeshEntry> entries;
	
	public Mesh(String name){
		this.name = name;
		this.entries = new HashSet<>();
	}
	
	public void addEntry(MeshEntry entry){
		
	}
}
