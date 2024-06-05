package partie1_db;

public class CoutDeplacement {
	private int depart;
	private int arrivee;
	private int cout;
	private String chemin;
	
	public CoutDeplacement(int depart, int arrivee, int cout, String chemin) {
		this.depart = depart;
		this.arrivee = arrivee;
		this.cout = cout;
		this.chemin = chemin;
	}

	public CoutDeplacement(int depart, int arrivee, int cout) {
		this.depart = depart;
		this.arrivee = arrivee;
		this.cout = cout;
		this.chemin = null;
	}

	public int getDepart() {
		return this.depart;
	}

	public int getArrivee() {
		return this.arrivee;
	}

	public int getCout() {
		return this.cout;
	}

	public String getChemin() {
		return this.chemin;
	}
	
	public String toString() {
		return "(" + this.getDepart() + ", " + this.getArrivee() + ", "
				+ this.getCout() + ", " + this.getChemin() + ")";
	}
	
}
