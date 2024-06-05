package partie1_db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InitialisationDBCoutVariable {

	public static void main(String[] args) throws Exception {

		//----------------------------------------------
		//DECLARATION ET INITIALISATION DE NOS VARIABLES
		//----------------------------------------------
		Class.forName( "org.hsqldb.jdbcDriver"  );												//Appel au driver qui permet de faire des databases
		String url = "jdbc:hsqldb:file:database"+File.separator+"goblin;shutdown=true";			//Emplacement dans le projet de notre database
		String login = "sa";
		String password = "";
		String requete = "";
		int idSite, posX, posY;
		int nbSite = 0;

		List<Site> sites = new ArrayList<Site>();
		List<Integer> voisins = new ArrayList<Integer>();
		List<CoutDeplacement> couts = new ArrayList<CoutDeplacement>();

		try (Connection connection = DriverManager.getConnection( url, login, password )){		//On se connecte à la database

			//-----------------------------------------------------
			//ON CHERCHE LE NOMBRE DE SITES DANS LA BASE DE DONNEES
			//-----------------------------------------------------
			requete = "SELECT COUNT(idSite) FROM SITE";
			try ( Statement statement = connection.createStatement() ) {
				try ( ResultSet resultSet = statement.executeQuery( requete ) ) {
					while( resultSet.next() ) {
						nbSite = resultSet.getInt(1);
					}
				}
			}

			//-------------------------------------------------
			//ON CREE UNE LISTE DE SITES QUI CORRESPOND A LA BD
			//-------------------------------------------------
			requete = "SELECT * FROM SITE";
			try ( Statement statement = connection.createStatement() ) {
				try ( ResultSet resultSet = statement.executeQuery( requete ) ) {
					while( resultSet.next() ) {
						idSite = resultSet.getInt( "idSite" );
						posX = resultSet.getInt( "posX" );
						posY = resultSet.getInt( "posY" );
						Site site = new Site(idSite, posX, posY);
						sites.add(site);
					}
				}
			}

			//---------------------------------------------------------------------
			//ON CREE UNE MATRICE DE COUT QUI CORRESPOND AUX ROUTES DEJA EXISTANTES
			//---------------------------------------------------------------------
			Double[][] cout = new Double[nbSite][nbSite];										//On crée une matrice carre qui représente le cout de deplacement entre chaque site 
			int cout1;
			String[][] chemin = new String[nbSite][nbSite];										//On crée une matrice carre qui représente le chemin entre chaque site
			String chemin1;

			int voisin;

			for (int i=1; i<=nbSite; i++) {
				voisins.clear();																//On clear la liste voisin
				requete = "SELECT arrivee FROM ROUTE";											//On cherche tous les voisins de i
				requete += " WHERE ROUTE.depart = " + i;
				try ( Statement statement = connection.createStatement() ) {
					try ( ResultSet resultSet = statement.executeQuery( requete ) ) {
						while( resultSet.next() ) {
							voisin = resultSet.getInt("arrivee");
							voisins.add(voisin);												//On ajoute tous ces voisins dans une liste
						}
					}
				}
				for (int j=i; j<=nbSite; j++) {
					if (i == j) {																//Si on regarde le déplacement entre le site et lui même
						cout[i-1][j-1] = 0.0;													//On dit que le cout de déplacement vaut 0
						chemin[i-1][j-1] = null;												//On dit qu'il n'y a pas de chemin pour venir sur soit même
					} else if (voisins.contains(j)){											//Si j est un voisin de i
						cout[i-1][j-1] = sites.get(i-1).distanceEntre(sites.get(j-1));			//On lui affecte comme coût la distance des deux sites
						cout[j-1][i-1] = cout[i-1][j-1];										//Car matrice est symétrique
						chemin[i-1][j-1] = i +" - "+ j;											//On indique le chemin entre les 2 sites
						chemin[j-1][i-1] = j +" - "+ i;
					} else {																	//Sinon (ni lui-même, ni voisin)
						cout[i-1][j-1] = Double.MAX_VALUE;										//On lui affecte l'infini
						cout[j-1][i-1] = cout[i-1][j-1];
						chemin[i-1][j-1] = null;												//On dit qu'il n'y a pas de chemin entre les 2 sites
						chemin[j-1][i-1] = null;
					}
				}
			}

			//------------------------------------------------------------------------------
			//ALGORYTHME FLOYD-WARSHALL QUI PERMET DE CALCULER TOUS LES COUTS DE SITE A SITE
			//------------------------------------------------------------------------------
			for (int k=0; k < nbSite; k++) {
				for (int i=0; i < nbSite; i++) {
					for (int j=0; j < nbSite; j++) {
						if (cout[i][k] + cout[k][j] < cout[i][j]) {
							cout[i][j] = cout[i][k] + cout[k][j];
							chemin[i][j] = chemin[i][k] +" -"+ chemin[k][j].split("-", 2)[1];
						}
					}
				}
			}

			//-----------------------------------------------------------------
			//AFFECTATION A UNE LISTE "couts" L'ENSEMBLE DES ELEMENTS CI-DESSUS
			//-----------------------------------------------------------------
			for (int i=1; i<=nbSite; i++) {
				for (int j=1; j<=nbSite; j++) {
					cout1 = (int)Math.ceil(cout[i-1][j-1]);
					chemin1 = chemin[i-1][j-1];
					CoutDeplacement coutDeplacement = new CoutDeplacement(i, j, cout1, chemin1);
					couts.add(coutDeplacement);
				}
			}

			//------------------------------
			//CREATION TABLE / DATABASE COUT
			//------------------------------
			//SUPPRESSION DE LA TABLE COUT
			requete = "DROP TABLE COUT IF EXISTS;";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requete );
			}

			//CREATION DE LA TABLE COUT
			requete = "CREATE TABLE COUT ("
					+"depart int,"
					+"arrivee int,"
					+"cout int,"
					+"chemin varchar(150),"
					+"PRIMARY KEY(depart, arrivee),"
					+"FOREIGN KEY (depart) REFERENCES SITE (idSite) ON DELETE RESTRICT,"
					+"FOREIGN KEY (arrivee) REFERENCES SITE (idSite) ON DELETE RESTRICT);";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requete );
			}

			//REMPLISSAGE DE LA TABLE COUT
			requete = "INSERT INTO COUT (depart, arrivee, cout, chemin) VALUES";
			for (int i=0; i < couts.size()-1; i++) {
				requete += "(" + couts.get(i).getDepart() + ",";
				requete += couts.get(i).getArrivee() + ",";
				requete += couts.get(i).getCout() + ",'";
				requete += couts.get(i).getChemin() + "'),";
			}
			requete += "(" + couts.get(couts.size()-1).getDepart() + ",";
			requete += couts.get(couts.size()-1).getArrivee() + ",";
			requete += couts.get(couts.size()-1).getCout() + ",'";
			requete += couts.get(couts.size()-1).getChemin() + "');";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requete );
			}

			//------------------------------------------------
			//PERMET DE FAIRE DES TESTS DE LECTURE DE MATRICES
			//------------------------------------------------
			/*String texte = "";
			for (int numLigne = 0 ; numLigne < nbSite ; numLigne++) {
				texte += "[";
				for (int i = 0 ; i < nbSite-1 ; i++) {
					texte += chemin[numLigne][i] + ", ";
				}
				if (nbSite > 0) {
					texte += chemin[numLigne][nbSite-1];
				}
				texte += "]" + "\n";
			}
			System.out.print(texte);*/
		}
	}
}
