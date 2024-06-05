package partie1_db;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

public class InitialisationDBEntrepot {

	public static void main(String[] args)  throws Exception {

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
					stock = Integer.parseInt(tokenEntrepot.split(";")[3]);    
					Entrepot entrepot = new Entrepot(idEntrepot, emplacementEntrepot, coutFixe, stock);
					entrepots.add(entrepot);
				}
			}
		}
		catch(Exception eEntrepot) {
			eEntrepot.printStackTrace();
		}

		//-------------------------
		//CREATION TABLE / DATABASE
		//-------------------------
		Class.forName( "org.hsqldb.jdbcDriver"  );												//Appel au driver qui permet de faire des databases
		String url = "jdbc:hsqldb:file:database"+File.separator+"goblin;shutdown=true";			//Emplacement dans le projet de notre database
		String login = "sa";
		String password = "";

		try (Connection connection = DriverManager.getConnection( url, login, password )){		//On se connecte à la db et si la connexion saute, on a un message d'exeption
			
			//SUPPRESSION DE LA TABLE ENTREPOT
			String requete = "DROP TABLE ENTREPOT IF EXISTS;";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requete );												//On execute la requete redigée ci-dessus
			}
			
			//CREATION DE LA TABLE ENTREPOT
			requete = "CREATE TABLE ENTREPOT ("
					+"idEntrepot int,"
					+"idSite int,"
					+"coutFixe int,"
					+"stock int,"
					+"PRIMARY KEY(idEntrepot),"
					+"FOREIGN KEY (idSite) REFERENCES SITE (idSite) ON DELETE RESTRICT);";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requete );
			}

			//REMPLISSAGE DE LA TABLE ENTREPOT
			requete = "INSERT INTO ENTREPOT (idEntrepot, idSite, coutFixe, stock) VALUES";
			for (int i=0; i < entrepots.size()-1; i++) {
				requete += entrepots.get(i)+",";
			}
			requete += entrepots.get(entrepots.size()-1)+";";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requete );
			}
		}
	}
}
