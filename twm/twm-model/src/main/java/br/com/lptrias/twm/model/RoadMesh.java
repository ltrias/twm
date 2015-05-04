package br.com.lptrias.twm.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Represents a road mesh for request parsing purposes.
 *
 * @author ltrias
 * 
 */
public class RoadMesh {
	private final String name;
	private final Map<EntryKey, Integer> entries;
	
	public RoadMesh(String name){
		this.name = name;
		this.entries = new HashMap<>();
	}
	
	public void addEntry(String origin, String destination, int cost){
		if( valid(origin, destination) && valid(cost) ){
			EntryKey key = new EntryKey(origin, destination);
			Integer currentCost = entries.get(key);
			
			if( currentCost  == null || currentCost > cost ){
				entries.put(key, cost);
			}
		}
	}
	
	public String getName() {
		return name;
	}
	
	public int getCost(String origin, String destination){
		return entries.get(new EntryKey(origin, destination));
	}
	
	public int getSize(){
		return entries.size();
	}
	
	private boolean valid(String origin, String destination) {
		if( origin == null || origin.equals(destination) ){
			throw new IllegalArgumentException("Origin and destination are the same: " + origin);
		}
		
		return true;
	}

	private boolean valid(int cost) {
		if( cost < 0 ){
			throw new IllegalArgumentException("Cost must not be negative: " + cost);
		}
		
		return true;
	}
	
	private static final class EntryKey {
		final String origin, destination;

		public EntryKey(String origin, String destination) {
			this.origin = origin;
			this.destination = destination;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((destination == null) ? 0 : destination.hashCode());
			result = prime * result
					+ ((origin == null) ? 0 : origin.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			EntryKey other = (EntryKey) obj;
			if (destination == null) {
				if (other.destination != null)
					return false;
			} else if (!destination.equals(other.destination))
				return false;
			if (origin == null) {
				if (other.origin != null)
					return false;
			} else if (!origin.equals(other.origin))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "EntryKey [origin=" + origin + ", destination="
					+ destination + "]";
		}
	}
}
