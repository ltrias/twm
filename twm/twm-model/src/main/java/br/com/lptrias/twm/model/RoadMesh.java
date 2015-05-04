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
	private final String name;
	private final Set<MeshEntry> entries;
	
	public RoadMesh(String name){
		this.name = name;
		this.entries = new HashSet<>();
	}
	
	public void addEntry(MeshEntry entry){
		MeshEntry toAdd = entry;
		
		if( entries.contains(entry) )
		{
			MeshEntry current = getEntry(entry.getOrigin(), entry.getDestination());
			
			if( entry.getCost() < current.getCost() ){
				entries.remove(current);
			} else {
				return;
			}
		}
		
		entries.add(toAdd);
	}
	
	public String getName() {
		return name;
	}
	
	MeshEntry getEntry(String origin, String destination){
		MeshEntry result = null;
		
		for (MeshEntry e : entries) {
			if( e.getOrigin().equals(origin) && e.getDestination().equals(destination) ){
				result = e;
				break;
			}
		}
		
		return result;
	}
	
	int getSize(){
		return entries.size();
	}
}
