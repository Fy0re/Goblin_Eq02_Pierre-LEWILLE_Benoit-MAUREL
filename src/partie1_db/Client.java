package partie1_db;

public class Client {

	private String nom;
	private String mail;
	private int emplacement;
	private int demande;
	
	public Client(String nom, String mail, int emplacement) {
		/*if (id_site==null || pos_x==null || pos_y==null) {
			throw new IllegalArgumentException("Donnée(s) manquante(s)");
		} */
		this.nom = nom;
		this.mail = mail;
		this.emplacement = emplacement;
		this.demande = 0;
	}

	public String getNom() {
		return this.nom;
	}

	public String getMail() {
		return this.mail;
	}
	
	public int getEmplacement() {
		return this.emplacement;
	}
	
	public void setDemande(int demande) {
		this.demande = demande;
	}
	
	public void resetDemande() {
		this.demande = 0;
	}

	public boolean equals(Object o) {
		return  o!=null
				&& o instanceof Client
				&& ( this.getEmplacement() == ((Client)o).getEmplacement()
				|| ( this.getNom() == ((Client)o).getNom()
				&& this.getMail() == ((Client)o).getMail()));
	}

	public String toString() {
		return "("+ this.getNom() +", "+ this.getMail() +", "+ this.getEmplacement() +")";
	}

}
