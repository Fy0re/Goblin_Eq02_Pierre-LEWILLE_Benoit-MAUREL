package partie1_db;

public class Route {
	
	private int depart;
	private int arrivee;
	
	public Route(int depart, int arrivee) {
		this.depart = depart;
		this.arrivee = arrivee;
	}

	public int getDepart() {
		return this.depart;
	}

	public int getArrivee() {
		return this.arrivee;
	}
	
	public String toString() {
		return "("+ this.getDepart() +", "+ this.getArrivee() +")";
	}
	
}
