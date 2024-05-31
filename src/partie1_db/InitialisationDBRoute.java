package partie1_db;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

public class InitialisationDBRoute {

	public static void main(String[] args) throws Exception {


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
			
			//SUPPRESSION DE LA TABLE ROUTE
			String requete = "DROP TABLE ROUTE IF EXISTS;";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requete );												//On execute la requete redigée ci-dessus
			}
			
			//CREATION DE LA TABLE ROUTE
			requete = "CREATE TABLE ROUTE ("
					+"depart int,"
					+"arrivee int,"
					+"PRIMARY KEY(depart, arrivee),"
					+"FOREIGN KEY (depart) REFERENCES SITE (idSite) ON DELETE RESTRICT,"
					+"FOREIGN KEY (arrivee) REFERENCES SITE (idSite) ON DELETE RESTRICT);";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requete );
			}

			//REMPLISSAGE DE LA TABLE ROUTE
			requete = "INSERT INTO ROUTE (depart, arrivee) VALUES";
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
