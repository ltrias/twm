package br.com.lptrias.twm.model;

/**
 * Represents a road mesh entry for parsing purposes.
 * 
 * @author ltrias
 *
 */

public class MeshEntry {
	private final String origin;
	private final String destination;
	private final int cost;
	
	public MeshEntry(String origin, String destination, int cost) {
		this.origin = origin;
		this.destination = destination;
		this.cost = cost;
	}

	public String getOrigin() {
		return origin;
	}

	public String getDestination() {
		return destination;
	}

	public int getCost() {
		return cost;
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
