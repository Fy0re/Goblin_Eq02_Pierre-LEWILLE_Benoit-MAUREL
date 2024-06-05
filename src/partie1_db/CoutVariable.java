package partie1_db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CoutVariable {

	public static void main(String[] args) throws Exception {

		Class.forName( "org.hsqldb.jdbcDriver"  );												//Appel au driver qui permet de faire des databases
		String url = "jdbc:hsqldb:file:database"+File.separator+"goblin;shutdown=true";			//Emplacement dans le projet de notre database
		String login = "sa";
		String password = "";
		String requete = "";
		int idSite, posX, posY;
		int nbSite = 0;
		List<Site> sites = new ArrayList<Site>();

		try (Connection connection = DriverManager.getConnection( url, login, password )){
			requete = "SELECT COUNT(idSite) FROM SITE";
			try ( Statement statement = connection.createStatement() ) {
				try (ResultSet resultSet = statement.executeQuery( requete ) ) {
					while( resultSet.next() ) {
						nbSite = resultSet.getInt(1);
					}
				}
			}
			requete = "SELECT * FROM SITE";
			try ( Statement statement = connection.createStatement() ) {
				try (ResultSet resultSet = statement.executeQuery( requete ) ) {
					while( resultSet.next() ) {
						idSite = resultSet.getInt( "idSite" );
						posX = resultSet.getInt( "posX" );
						posY = resultSet.getInt( "posY" );
						Site site = new Site(idSite, posX, posY);
						sites.add(site);
					}
				}
			}

			double [][]cout = new double [nbSite][nbSite];
			List<Integer> voisins = new ArrayList<Integer>();
			int voisin;

			for (int i=1; i<=nbSite; i++) {
				voisins.clear();
				requete = "SELECT arrivee FROM ROUTE";
				requete += " WHERE ROUTE.depart = " + i;
				try ( Statement statement = connection.createStatement() ) {
					try (ResultSet resultSet = statement.executeQuery( requete ) ) {
						while( resultSet.next() ) {
							voisin = resultSet.getInt("arrivee");
							voisins.add(voisin);
						}
					}
				}
				for (int j=i; j<=nbSite; j++) {		
					if (i == j) {
						cout[i-1][j-1] = 0;
					} else if (voisins.contains(j)){
						cout[i-1][j-1] = sites.get(i-1).distanceEntre(sites.get(j-1));
						cout[j-1][i-1] = cout[i-1][j-1];
					} else {
						cout[i-1][j-1] = Integer.MAX_VALUE;
						cout[j-1][i-1] = cout[i-1][j-1];
					}
				}
			}

			for (int k=0; k < nbSite; k++) {
				for (int i=0; i < nbSite; i++) {
					for (int j=0; j < nbSite; j++) {
						if (cout[i][k] + cout[k][j] < cout[i][j]) {
							cout[i][j] = cout[i][k] + cout[k][j];
						}
					}
				}
			}

			//-------------------------------------------------------------------------------
			//AFFECTATION A UNE LISTE "routes" L'ENSEMBLE DES ROUTES LUES DANS LE FICHIER CSV
			//-------------------------------------------------------------------------------
			List<CoutDeplacement> couts = new ArrayList<CoutDeplacement>();

			int cout1;
			String chemin;
			for (int i=1; i<=nbSite; i++) {
				for (int j=1; j<=nbSite; j++) {
					if (i == j) {
						cout1 = Integer.MAX_VALUE;
					} else {
						cout1 = (int)Math.ceil(cout[i-1][j-1]);
					}
					CoutDeplacement coutDeplacement = new CoutDeplacement(i, j, cout1);
					couts.add(coutDeplacement);
				}
			}

			//--------------------------
			//CREATION TABLES / DATABASE
			//--------------------------

			//SUPPRESSION DE LA TABLE COUT
			requete = "DROP TABLE COUT IF EXISTS;";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requete );												//On execute la requete redigée ci-dessus
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
				requete += "('"+ couts.get(i).getDepart() +"',";
				requete += "'"+ couts.get(i).getArrivee() +"',";
				requete += couts.get(i).getCout() +",";
				requete += couts.get(i).getChemin() +"),";
			}
			requete += "('"+ couts.get(couts.size()-1).getDepart() +"',";
			requete += "'"+ couts.get(couts.size()-1).getArrivee() +"',";
			requete += couts.get(couts.size()-1).getCout() +",";
			requete += couts.get(couts.size()-1).getChemin() +");";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requete );
			}
		}
	}
}

