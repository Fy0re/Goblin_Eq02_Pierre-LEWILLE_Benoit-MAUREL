package partie1_db;

public class Route {
	
	private int depart;
	private int arrivee;
	
	public Route(int depart, int arrivee) {
		/*if (id_site==null || pos_x==null || pos_y==null) {
			throw new IllegalArgumentException("Donnée(s) manquante(s)");
		} */
		this.depart = depart;
		this.arrivee = arrivee;
	}

	public int getDepart() {
		return this.depart;
	}

	public int getArrivee() {
		return this.arrivee;
	}
	
	public boolean equals(Object o) {
		return  o!=null
				&& o instanceof Route
				&& this.getDepart() == ((Route)o).getDepart()
				&& this.getArrivee() == ((Route)o).getArrivee();
	}
	
	public String toString() {
		return "("+ this.getDepart() +", "+ this.getArrivee() +")";
	}
	
}
