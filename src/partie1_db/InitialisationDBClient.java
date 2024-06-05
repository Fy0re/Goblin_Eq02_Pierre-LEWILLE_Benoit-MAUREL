package partie1_db;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

public class InitialisationDBClient {

	public static void main(String[] args) throws Exception {

		//-------------------------------------------------------------------------------
		//AFFECTATION A UNE LISTE "clients" L'ENSEMBLE DES CLIENT LUS DANS LE FICHIER CSV
		//-------------------------------------------------------------------------------
		List<Client> clients = new ArrayList<Client>();											//Création de notre liste
		CSVReader readerClient = null;															//Declaration de la variable
		try {																					//Permet d'annuler proprement la commande en cas d'erreur
			readerClient = new CSVReader(new FileReader("Jeux_de_donnees"+File.separator+"petit"+File.separator+"init-clients-30-10-Carre.csv")); 		//Nom du fichier lu
			String[] nextLineClient;															//Declaration de la variable
			String nom, mail;																	//Declaration de la variable
			int emplacementClient;																//Declaration de la variable
			readerClient.readNext();															//Permet d'ignorer la première ligne (les en-têtes)
			while ((nextLineClient = readerClient.readNext()) != null) {						//Tant qu'il y a un élément dans la ligne suivante
				for (String tokenClient : nextLineClient) {										//Pour chaque ligne du fichier
					nom = tokenClient.split(";")[0];											//On récupère le 1er élément de la ligne (les séparateurs étant ";")
					mail = tokenClient.split(";")[1];											//On récupère le 2eme élément de la ligne (les séparateurs étant ";")
					emplacementClient = Integer.parseInt(tokenClient.split(";")[2]);			//On récupère le 3eme élément de la ligne (les séparateurs étant ";") et on le transforme en int
					Client client = new Client(nom, mail, emplacementClient);					//On utilise les données récupérées pour les mettre dans un objet de classe client
					clients.add(client);														//On ajoute cette objet à la liste "clients"
				client.resetDemande();
				}
			}
		}
		catch(Exception eClient) {																//S'il y a eu une erreur
			eClient.printStackTrace();															//Dire d'où elle vient
		}
		
		//-------------------------
		//CREATION TABLE / DATABASE
		//-------------------------
		Class.forName( "org.hsqldb.jdbcDriver"  );												//Appel au driver qui permet de faire des databases
		String url = "jdbc:hsqldb:file:database"+File.separator+"goblin;shutdown=true";			//Emplacement dans le projet de notre database
		String login = "sa";
		String password = "";

		try (Connection connection = DriverManager.getConnection( url, login, password )){		//On se connecte à la db et si la connexion saute, on a un message d'exeption
			
			//SUPPRESSION DE TABLE CLIENT
			String requete = "DROP TABLE CLIENT IF EXISTS;";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requete );
			}
			
			//CREATION DE TABLE CLIENT
			requete = "CREATE TABLE CLIENT ("
					+"nom varchar(20),"
					+"mail varchar(50),"
					+"emplacement int,"
					+"demande int,"
					+"PRIMARY KEY(mail),"
					+"FOREIGN KEY (emplacement) REFERENCES SITE (idSite) ON DELETE RESTRICT);";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requete );
			}

			//REMPLISSAGE DE TABLE CLIENT
			requete = "INSERT INTO CLIENT (nom, mail, emplacement, demande) VALUES";
			for (int i=0; i < clients.size()-1; i++) {
				requete += "('"+ clients.get(i).getNom() +"',";
				requete += "'"+ clients.get(i).getMail() +"',";
				requete += clients.get(i).getEmplacement() +",";
				requete += clients.get(i).getDemande() +"),";
			}
			requete += "('"+ clients.get(clients.size()-1).getNom() +"',";
			requete += "'"+ clients.get(clients.size()-1).getMail() +"',";
			requete += clients.get(clients.size()-1).getEmplacement() +", ";
			requete += clients.get(clients.size()-1).getDemande() +");";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requete );
			}
		}
	}
}
