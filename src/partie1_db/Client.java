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

	public Client(String nom, String mail, int emplacement, int demande) {
		/*if (id_site==null || pos_x==null || pos_y==null) {
			throw new IllegalArgumentException("Donnée(s) manquante(s)");
		} */
		this.nom = nom;
		this.mail = mail;
		this.emplacement = emplacement;
		this.demande = demande;
	}
	
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getMail() {
		return this.mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public int getEmplacement() {
		return this.emplacement;
	}
	
	public void setEmplacement(int emplacement) {
		this.emplacement = emplacement;
	}
	
	public int getDemande() {
		return this.demande;
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
