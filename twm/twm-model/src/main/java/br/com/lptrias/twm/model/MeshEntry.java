package br.com.lptrias.twm.model;

/**
 * Represents a road mesh entry for parsing purposes.
 * 
 * @author ltrias
 *
 */

public class MeshEntry {
	private String origin;
	private String destination;
	private int cost;
	
	public MeshEntry(String origin, String destination, int cost) {
		if( valid(cost) && valid(origin, destination) ){
			this.origin = origin;
			this.destination = destination;
			this.cost = cost;
		}
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

	public String getOrigin() {
		return origin;
	}

	public String getDestination() {
		return destination;
	}

	public int getCost() {
		return new Integer(cost).intValue();
	}

	@Override
	public String toString() {
		return "MeshEntry [origin=" + origin + ", destination=" + destination
				+ ", cost=" + cost + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
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
		MeshEntry other = (MeshEntry) obj;
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

}
