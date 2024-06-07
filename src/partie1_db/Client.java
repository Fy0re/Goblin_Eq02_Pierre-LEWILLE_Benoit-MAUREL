package partie1_db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

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
	
	public void setDemande(int d) throws Exception {
		this.demande = d;
		Class.forName( "org.hsqldb.jdbcDriver"  );												//Appel au driver qui permet de faire des databases
		String url = "jdbc:hsqldb:file:database"+File.separator+"goblin;shutdown=true";			//Emplacement dans le projet de notre database
		String login = "sa";
		String password = "";
		String requete;

		try (Connection connection = DriverManager.getConnection( url, login, password )){		//On se connecte à la db et si la connexion saute, on a un message d'exeption
			requete = "UPDATE CLIENT";
			requete += " SET demande = " + this.getDemande();
			requete += " WHERE CLIENT.mail = " +"'"+ this.getMail() +"'";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requete );
			}
		}
	}
	
	//Permet de mettre la demande du client à 0
	public void resetDemande() throws Exception {
		this.demande = 0;
		Class.forName( "org.hsqldb.jdbcDriver"  );												//Appel au driver qui permet de faire des databases
		String url = "jdbc:hsqldb:file:database"+File.separator+"goblin;shutdown=true";			//Emplacement dans le projet de notre database
		String login = "sa";
		String password = "";
		String requete;

		try (Connection connection = DriverManager.getConnection( url, login, password )){		//On se connecte à la db et si la connexion saute, on a un message d'exeption
			requete = "UPDATE CLIENT";
			requete += " SET demande = " + this.getDemande();
			requete += " WHERE CLIENT.mail = " + this.getMail();
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requete );
			}
		}
	}

	public String toString() {
		return "("+ this.getNom() +", "+ this.getMail() +", "+ this.getEmplacement() +")";
	}

}
