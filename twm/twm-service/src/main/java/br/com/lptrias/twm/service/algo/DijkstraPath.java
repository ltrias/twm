package br.com.lptrias.twm.service.algo;

public class DijkstraPath extends Path{
	private int cost;
	
	public DijkstraPath(){
		super();
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "DijkstraPath [cost=" + cost + ", steps=" + steps + "]";
	}
}