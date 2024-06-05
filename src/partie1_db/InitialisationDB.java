package partie1_db;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class InitialisationDB {

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

		//------------------------------------------------------------------------------------
		//AFFECTATION A UNE LISTE "entrepots" L'ENSEMBLE DES ENTREPOTS LUS DANS LE FICHIER CSV
		//------------------------------------------------------------------------------------
		List<Entrepot> entrepots = new ArrayList<Entrepot>();
		CSVReader readerEntrepot = null;
		try {
			readerEntrepot = new CSVReader(new FileReader("Jeux_de_donnees"+File.separator+"petit"+File.separator+"init-entrepots-30-5-Carre.csv"));
			String[] nextLineEntrepot;
			int idEntrepot, emplacementEntrepot, coutFixe, stock;
			readerEntrepot.readNext();
			while ((nextLineEntrepot = readerEntrepot.readNext()) != null) {
				for (String tokenEntrepot : nextLineEntrepot) {
					idEntrepot = Integer.parseInt(tokenEntrepot.split(";")[0]);
					emplacementEntrepot = Integer.parseInt(tokenEntrepot.split(";")[1]);
					coutFixe = Integer.parseInt(tokenEntrepot.split(";")[2]); 
					stock = Integer.parseInt(tokenEntrepot.split(";")[2]);    
					Entrepot entrepot = new Entrepot(idEntrepot, emplacementEntrepot, coutFixe, stock);
					entrepots.add(entrepot);
				}
			}
		}
		catch(Exception eEntrepot) {
			eEntrepot.printStackTrace();
		}

		//----------------------------------------------------------------------------
		//AFFECTATION A UNE LISTE "sites" L'ENSEMBLE DES SITES LUS DANS LE FICHIER CSV
		//----------------------------------------------------------------------------
		List<Site> sites = new ArrayList<Site>();
		CSVReader readerSite = null;
		try {
			readerSite = new CSVReader(new FileReader("Jeux_de_donnees"+File.separator+"petit"+File.separator+"init-sites-30-Carre.csv"));
			String[] nextLineSite;
			int idSite, posX, posY;
			readerSite.readNext();
			while ((nextLineSite = readerSite.readNext()) != null) {
				for (String tokenSite : nextLineSite) {
					idSite = Integer.parseInt(tokenSite.split(";")[0]);
					posX = Integer.parseInt(tokenSite.split(";")[1]);
					posY = Integer.parseInt(tokenSite.split(";")[2]);      
					Site site = new Site(idSite, posX, posY);
					sites.add(site);
				}
			}
		}
		catch(Exception eSite) {
			eSite.printStackTrace();
		}

		//-------------------------------------------------------------------------------
		//AFFECTATION A UNE LISTE "routes" L'ENSEMBLE DES ROUTES LUES DANS LE FICHIER CSV
		//-------------------------------------------------------------------------------
		List<Route> routes = new ArrayList<Route>();
		CSVReader readerRoute = null;
		try {
			readerRoute = new CSVReader(new FileReader("Jeux_de_donnees"+File.separator+"petit"+File.separator+"init-routes-30-45-Carre.csv"));
			String[] nextLineRoute;
			int depart, arrivee;
			readerRoute.readNext();
			while ((nextLineRoute = readerRoute.readNext()) != null) {
				for (String tokenRoute : nextLineRoute) {
					depart = Integer.parseInt(tokenRoute.split(";")[0]);
					arrivee = Integer.parseInt(tokenRoute.split(";")[1]);      
					Route route = new Route(depart, arrivee);
					routes.add(route);
				}
			}
		}
		catch(Exception eRoute) {
			eRoute.printStackTrace();
		}

		//--------------------------
		//CREATION TABLES / DATABASE
		//--------------------------
		Class.forName( "org.hsqldb.jdbcDriver"  );												//Appel au driver qui permet de faire des databases
		String url = "jdbc:hsqldb:file:database"+File.separator+"goblin;shutdown=true";			//Emplacement dans le projet de notre database
		String login = "sa";
		String password = "";

		try (Connection connection = DriverManager.getConnection( url, login, password )){		//On se connecte à la db et si la connexion saute, on a un message d'exeption
			
			//SUPPRESSION DES TABLES
			String requete = "DROP TABLE CLIENT IF EXISTS;"
					+"DROP TABLE ENTREPOT IF EXISTS;"
					+"DROP TABLE ROUTE IF EXISTS;"
					+"DROP TABLE COUT IF EXISTS;"
					+"DROP TABLE SITE IF EXISTS;";												//Table SITE faite en dernier car il y a des clés étrangères qui lui sont reliées
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requete );												//On execute la requete redigée ci-dessus
			}
			
			//CREATION DES TABLES
			requete = "CREATE TABLE SITE ("														//Table SITE faite en première car il y a des clés étrangères qui lui sont reliées
					+"idSite int,"
					+"posX int,"
					+"posY int,"
					+"PRIMARY KEY(idSite));"
					+""
					+"CREATE TABLE CLIENT ("
					+"nom varchar(20),"
					+"mail varchar(50),"
					+"emplacement int,"
					+"demande int,"
					+"PRIMARY KEY(mail),"
					+"FOREIGN KEY (emplacement) REFERENCES SITE (idSite) ON DELETE RESTRICT);"
					+""
					+"CREATE TABLE ENTREPOT ("
					+"idEntrepot int,"
					+"idSite int,"
					+"coutFixe int,"
					+"stock int,"
					+"PRIMARY KEY(idEntrepot),"
					+"FOREIGN KEY (idSite) REFERENCES SITE (idSite) ON DELETE RESTRICT);"
					+""
					+"CREATE TABLE ROUTE ("
					+"depart int,"
					+"arrivee int,"
					+"PRIMARY KEY(depart, arrivee),"
					+"FOREIGN KEY (depart) REFERENCES SITE (idSite) ON DELETE RESTRICT,"
					+"FOREIGN KEY (arrivee) REFERENCES SITE (idSite) ON DELETE RESTRICT);";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requete );
			}

			//REMPLISSAGE DES TABLES
			requete = "INSERT INTO SITE (idSite, posX, posY) VALUES";							//Table SITE faite en première car il y a des clés étrangères qui lui sont reliées
			for (int i=0; i < sites.size()-1; i++) {											//On regarde tous les éléments de la liste "sites" sauf le dernier
				requete += sites.get(i)+",";													//On ajoute tous les éléments dans notre requete
			}
			requete += sites.get(sites.size()-1)+";";											//On ajoute le dernier élément de la liste sans "," et avec un ";"

			requete += "INSERT INTO CLIENT (nom, mail, emplacement, demande) VALUES";
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

			requete += "INSERT INTO ENTREPOT (idEntrepot, idSite, coutFixe, stock) VALUES";
			for (int i=0; i < entrepots.size()-1; i++) {
				requete += entrepots.get(i)+",";
			}
			requete += entrepots.get(entrepots.size()-1)+";";
			
			requete += "INSERT INTO ROUTE (depart, arrivee) VALUES";
			for (int i=0; i < routes.size()-1; i++) {
				requete += routes.get(i)+",";
			}
			requete += routes.get(routes.size()-1)+";";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requete );
			}
		}
	}
}
