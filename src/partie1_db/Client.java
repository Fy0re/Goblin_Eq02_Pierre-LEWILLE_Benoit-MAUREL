package partie1_db;

public class Client {

	private String nom;
	private String mail;
	private int emplacement;
	private int demande;
	
	public Client(String nom, String mail, int emplacement, int demande) {
		this.nom = nom;
		this.mail = mail;
		this.emplacement = emplacement;
		this.demande = demande;
	}
	
	public Client(String nom, String mail, int emplacement) {
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
	
	public int getDemande() {
		return this.demande;
	}
	
	public void setDemande(int d) {
		this.demande = d;
	}
	
	//Permet de mettre la demande du client à 0
	public void resetDemande() {
		this.demande = 0;
	}

	public String toString() {
		return "("+ this.getNom() +", "+ this.getMail() +", "+ this.getEmplacement() +")";
	}

}
