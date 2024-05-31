import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import partie1_db.Client;
import partie1_db.Entrepot;
import partie1_db.InitialisationDB;
import partie1_db.InitialisationDBClient;
import partie1_db.InitialisationDBEntrepot;
import partie1_db.InitialisationDBRoute;
import partie1_db.Route;
import partie1_db.Site;

public class Main {
	public static void main(String[] args) throws Exception {
		String answerStr;
		int answerInt;
		Class.forName( "org.hsqldb.jdbcDriver"  );												//Appel au driver qui permet de faire des databases
		String url = "jdbc:hsqldb:file:database"+File.separator+"goblin;shutdown=true";			//Emplacement dans le projet de notre database
		String login = "sa";
		String password = "";
		String requete = "";
		
		do {
			System.out.println(" +-------------------------------------+");
			System.out.println(" |               GOBLIN                |");
			System.out.println(" +-------------------------------------+");
			System.out.println(" [0] Initialisation");
			System.out.println(" [1] Calcul bordereau de livraison");
			System.out.println(" [2] Modification database");
			System.out.println(" [Q] Quitter");
			answerStr = Clavier.lireString();
			switch (answerStr) {
			case "0" :
				System.out.println(" +-------------------------------------+");
				System.out.println(" |       INITIALISATION DATABASE       |");
				System.out.println(" +-------------------------------------+");
				System.out.println(" [1] (Re)Initialiser la database complete");
				System.out.println(" [2] (Re)Initialiser la database Client");
				System.out.println(" [3] (Re)Initialiser la database Entrepot");
				System.out.println(" [4] (Re)Initialiser la database Route");
				System.out.println(" [Q] Quitter");
				//Penser à ajouter si l'utilisateur mets une autre valeur, le retour en arrière et quitter
				answerStr = Clavier.lireString();
				switch (answerStr) {
				case "1" :
					InitialisationDB.main(args);
					System.out.println(" +-------------------------------------+");
					System.out.println(" |     (RE)INITIALISATION REUSSIE      |");
					System.out.println(" +-------------------------------------+");
					break;
				case "2" :
					InitialisationDBClient.main(args);
					System.out.println(" +-------------------------------------+");
					System.out.println(" |     (RE)INITIALISATION REUSSIE      |");
					System.out.println(" +-------------------------------------+");
					break;
				case "3" :
					InitialisationDBEntrepot.main(args);
					System.out.println(" +-------------------------------------+");
					System.out.println(" |     (RE)INITIALISATION REUSSIE      |");
					System.out.println(" +-------------------------------------+");
					break;
				case "4" :
					InitialisationDBRoute.main(args);
					System.out.println(" +-------------------------------------+");
					System.out.println(" |     (RE)INITIALISATION REUSSIE      |");
					System.out.println(" +-------------------------------------+");
					break;
				}
				break;
			case "1" :
				System.out.println(" +-------------------------------------+");
				System.out.println(" |    CALCUL BORDEREAU DE LIVRAISON    |");
				System.out.println(" +-------------------------------------+");
				System.out.println("Vous souhaitez faire le calcul pour quelle date ?");
				System.out.println("(AAAA-MM-JJ)");
				//Faire une variable de type date pour chercher le bordereau associé
				//Penser à vérifier si la donnée est bonne
				break;
			case "2" : 
				System.out.println(" +-------------------------------------+");
				System.out.println(" |       MODIFICATION DATABASE         |");
				System.out.println(" +-------------------------------------+");
				System.out.println(" [1] Modifier la liste des clients");
				System.out.println(" [2] Modifier la liste des entrepôts");
				System.out.println(" [3] Modifier la liste des sites");
				System.out.println(" [4] Modifier la liste des routes");
				System.out.println(" [Q] Quitter");
				//Penser à ajouter si l'utilisateur mets une autre valeur, le retour en arrière et quitter
				answerStr = Clavier.lireString();
				switch (answerStr) {
					case "1" : 
						System.out.println(" +-------------------------------------+");
						System.out.println(" |       MODIFICATION BD CLIENTS       |");
						System.out.println(" +-------------------------------------+");
						System.out.println(" [1] Modifier un client dans la database");
						System.out.println(" [2] Ajouter un client dans la database");
						System.out.println(" [3] Supprimer un client dans la database");
						System.out.println(" [Q] Quitter");
						//Penser à ajouter si l'utilisateur mets une autre valeur, le retour en arrière et quitter
						answerStr = Clavier.lireString();
						switch (answerStr) {
							case "1" : 
								String nom = "";
								String mailOld = "";
								String mail = "";
								int emplacement = 0;
								System.out.println(" +-------------------------------------+");
								System.out.println(" |     MODIFICATION DONNEES CLIENT     |");
								System.out.println(" +-------------------------------------+");
								System.out.println(" Veuillez indiquez son mail :");
								answerStr = Clavier.lireString();
								try (Connection connection = DriverManager.getConnection( url, login, password )){
									requete = "SELECT COUNT(*) FROM CLIENT ";
									requete += "WHERE CLIENT.mail = "+answerStr;
									try ( Statement statement = connection.createStatement() ) {
										try (ResultSet resultSet = statement.executeQuery( requete ) ) {
											if (resultSet.next()) {
												requete = "SELECT * FROM CLIENT ";
												requete += "WHERE CLIENT.mail = "+answerStr;
												try ( Statement statement2 = connection.createStatement() ) {
													try (ResultSet resultSet2 = statement2.executeQuery( requete ) ) {
														nom = resultSet2.getString( "nom" );
														mail = resultSet2.getString( "mail" );
														emplacement = resultSet2.getInt( "emplacement" );
													}
												}
											}
										}
									}
								}
								Client client = new Client(nom, mail, emplacement);
								System.out.println(" Veuillez indiquer son nouveau nom");
								System.out.println(" ('M' pour garder l'ancien) :");
								answerStr = Clavier.lireString();
								if (answerStr != "M") {
									client.setNom(answerStr);
								}
								System.out.println(" Veuillez indiquer son nouveau mail");
								System.out.println(" ('M' pour garder l'ancien) :");
								answerStr = Clavier.lireString();
								if (answerStr != "M") {
									client.setMail(answerStr);
								}
								System.out.println(" Veuillez indiquer son nouvel emplacement");
								System.out.println(" ('M' pour garder l'ancien) :");
								answerInt = Clavier.lireInt();
								if (answerStr != "M") {
									client.setEmplacement(answerInt);
								}
								try (Connection connection = DriverManager.getConnection( url, login, password )){
									requete = "UPDATE CLIENT";
									requete += "SET nom = " + client.getNom() +",";
									requete += "mail = " + client.getMail() +",";
									requete += "emplacement = " + client.getEmplacement();
									requete += "WHERE mail = " + mail;
									try ( Statement statement = connection.createStatement() ) {
										statement.executeUpdate( requete );
									}
								}
								System.out.println(" +-------------------------------------+");
								System.out.println(" |         MODIFICATION REUSSIE        |");
								System.out.println(" +-------------------------------------+");
								break;
							case "2" :
								String nomAjoutClient;
								String mailAjoutClient;
								int emplacementAjoutClient;
								System.out.println(" +-------------------------------------+");
								System.out.println(" |            AJOUT CLIENT             |");
								System.out.println(" +-------------------------------------+");
								System.out.println(" Veuillez indiquez son nom");
								nomAjoutClient = Clavier.lireString();
								System.out.println(" Veuillez indiquez son mail");
								mailAjoutClient = Clavier.lireString();
								System.out.println(" Veuillez indiquez son emplacement");
								emplacementAjoutClient = Clavier.lireInt();
								client = new Client(nomAjoutClient, mailAjoutClient, emplacementAjoutClient);
								//Non prise en compte de la mauvaise écriture du client
								break;
							case "3" : 
								System.out.println(" +-------------------------------------+");
								System.out.println(" |         SUPPRESSION CLIENT          |");
								System.out.println(" +-------------------------------------+");
								System.out.println(" [1] Selectionner un client par son nom");
								System.out.println(" [2] Selectionner un client par son mail");
								System.out.println(" [Q] Quitter");
								//Penser à ajouter si l'utilisateur mets une autre valeur, le retour en arrière et quitter
								answerInt = Clavier.lireInt();
								if (answerInt == 1) {
									System.out.println("Veuillez indiquez son nom");
									//Faire une variable de type string et chercher dans db le client associé
									//Penser à client non existant
								} else if (answerInt == 2) {
									System.out.println("Veuillez indiquez son mail");
									//Faire une variable de type string et chercher dans db le client associé
									//Penser à client non existant
								} else {
									System.out.println("Choix non reconnue");
									//Choix en boucle
								}
								break;
						}
						break;
					case "2" : 
						System.out.println(" +-------------------------------------+");
						System.out.println(" |      MODIFICATION BD ENTREPOTS      |");
						System.out.println(" +-------------------------------------+");
						System.out.println(" [1] Modifier un entrepot dans la database");
						System.out.println(" [2] Ajouter un entrepot dans la database");
						System.out.println(" [3] Supprimer un entrepot dans la database");
						System.out.println(" [Q] Quitter");
						//Penser à ajouter si l'utilisateur mets une autre valeur, le retour en arrière et quitter
						answerStr = Clavier.lireString();
						switch (answerStr) {
							case "1" : 
								System.out.println(" +-------------------------------------+");
								System.out.println(" |    MODIFICATION DONNEES ENTREPOT    |");
								System.out.println(" +-------------------------------------+");
								System.out.println(" Veuillez indiquer son ID");
								System.out.println(" (Il s'agit de l'ID entrepot et non de l'ID site)");
								//Faire une variable de type int et chercher dans db l'entrepot associé
								break;
							case "2" :
								int idEntrepotAjoutEntrepot;
								int idSiteAjoutEntrepot;
								int coutFixeAjoutEntrepot;
								int stockAjoutEntrepot;
								System.out.println(" +-------------------------------------+");
								System.out.println(" |           AJOUT ENTREPOT            |");
								System.out.println(" +-------------------------------------+");
								System.out.println(" Veuillez indiquez son id");
								idEntrepotAjoutEntrepot = Clavier.lireInt();
								System.out.println(" Veuillez indiquez son emplacement");
								idSiteAjoutEntrepot = Clavier.lireInt();
								System.out.println(" Veuillez indiquez son cout fixe");
								coutFixeAjoutEntrepot = Clavier.lireInt();
								System.out.println(" Veuillez indiquez son stock");
								stockAjoutEntrepot = Clavier.lireInt();
								Entrepot entrepot = new Entrepot(idEntrepotAjoutEntrepot, idSiteAjoutEntrepot, coutFixeAjoutEntrepot, stockAjoutEntrepot);
								//Non prise en compte de la mauvaise écriture de l'entrepot
								break;
							case "3" : 
								System.out.println(" +-------------------------------------+");
								System.out.println(" |        SUPPRESSION ENTREPOT         |");
								System.out.println(" +-------------------------------------+");
								System.out.println(" Veuillez indiquer son ID");
								System.out.println(" (Il s'agit de l'ID entrepot et non de l'ID site)");
								//Faire une variable de type int et chercher dans db l'entrepot associé
								break;
						}
						break;
					case "3" : 
						System.out.println(" +-------------------------------------+");
						System.out.println(" |        MODIFICATION BD SITES       |");
						System.out.println(" +-------------------------------------+");
						System.out.println(" [1] Modifier un site dans la database");
						System.out.println(" [2] Ajouter un site dans la database");
						System.out.println(" [3] Supprimer un site dans la database");
						System.out.println(" [Q] Quitter");
						//Penser à ajouter si l'utilisateur mets une autre valeur, le retour en arrière et quitter
						answerStr = Clavier.lireString();
						switch (answerStr) {
							case "1" : 
								System.out.println(" +-------------------------------------+");
								System.out.println(" |      MODIFICATION DONNEES SITE      |");
								System.out.println(" +-------------------------------------+");
								System.out.println(" Veuillez indiquer son ID");
								//Faire une variable de type int et chercher dans db le site associé
								break;
							case "2" :
								int posXAjoutSite;
								int posYAjoutSite;
								System.out.println(" +-------------------------------------+");
								System.out.println(" |             AJOUT SITE              |");
								System.out.println(" +-------------------------------------+");
								System.out.println(" Veuillez indiquez sa position X");
								posXAjoutSite = Clavier.lireInt();
								System.out.println(" Veuillez indiquez sa position Y");
								posYAjoutSite = Clavier.lireInt();
								//Faire id incrémentation auto
								int id = 0;
								Site site = new Site(id, posXAjoutSite, posYAjoutSite);
								//Non prise en compte de la mauvaise écriture du site
								break;
							case "3" : 
								System.out.println(" +-------------------------------------+");
								System.out.println(" |          SUPPRESSION SITE           |");
								System.out.println(" +-------------------------------------+");
								System.out.println(" Veuillez indiquer son ID");
								//Faire une variable de type int et chercher dans db le site associé
								break;
						}
						break;
					case "4" : 
						int departRoute;
						int ArriveeRoute;
						System.out.println(" +-------------------------------------+");
						System.out.println(" |        MODIFICATION BD ROUTES       |");
						System.out.println(" +-------------------------------------+");
						System.out.println(" [1] Ajouter une route dans la database");
						System.out.println(" [2] Supprimer une route dans la database");
						System.out.println(" [Q] Quitter");
						//Penser à ajouter si l'utilisateur mets une autre valeur, le retour en arrière et quitter
						answerStr = Clavier.lireString();
						switch (answerStr) {
							case "1" :
								System.out.println(" +-------------------------------------+");
								System.out.println(" |             AJOUT ROUTE             |");
								System.out.println(" +-------------------------------------+");
								System.out.println(" Veuillez indiquez son depart");
								departRoute = Clavier.lireInt();
								System.out.println(" Veuillez indiquez son arrivee");
								ArriveeRoute = Clavier.lireInt();
								Route route = new Route(departRoute, ArriveeRoute);
								//Non prise en compte de la mauvaise écriture de la route
								break;
							case "2" : 
								System.out.println(" +-------------------------------------+");
								System.out.println(" |          SUPPRESSION ROUTE          |");
								System.out.println(" +-------------------------------------+");
								System.out.println(" Veuillez indiquez son depart");
								departRoute = Clavier.lireInt();
								System.out.println(" Veuillez indiquez son arrivee");
								ArriveeRoute = Clavier.lireInt();
								//Chercher dans db la route associée
								break;
						}
						break;
				}
				break;
			}
		} while (!answerStr.toUpperCase().equals("Q"));
	}
}
