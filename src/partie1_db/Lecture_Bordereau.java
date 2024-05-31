package partie1_db;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Lecture_Bordereau {
	
	public static void main(String[] args) throws Exception {
				
        String inputFilePath = "Jeux_de_donnees"+File.separator+"petit"+File.separator+ "init-bordereau-commande-2021-12-25.txt"; // Chemin vers le fichier texte source
        String outputFilePath = "Jeux_de_donnees"+File.separator+"petit"+File.separator+"output.json"; // Chemin vers le fichier JSON de sortie

        Gson gson = new Gson(); // création d'une instance de Gson pour la conversion

        List<Entrepot> entrepotsDispos = new ArrayList<>(); 
        List<Client> clients = new ArrayList<>(); 
        String DateLivraison;
        int n;

		Class.forName( "org.hsqldb.jdbcDriver"  );												//Appel au driver qui permet de faire des databases
		String url = "jdbc:hsqldb:file:database"+File.separator+"goblin;shutdown=true";			//Emplacement dans le projet de notre database
		String login = "sa";
		String password = "";

		try (Connection connection = DriverManager.getConnection( url, login, password )){		//On se connecte à la db et si la connexion saute, on a un message d'exeption
			String requete = "SELECT * FROM CLIENT";
			try ( Statement statement = connection.createStatement() ) {
				try (ResultSet resultSet = statement.executeQuery( requete ) ) {
					while( resultSet.next() ) {
						String nom = resultSet.getString( "nom" );
						String mail = resultSet.getString( "mail" );
						int emplacement = resultSet.getInt( "emplacement" );
						int demande = resultSet.getInt( "demande" );
						clients.add(new Client(nom, mail, emplacement, demande));
					}
				}
			}
		}
		
		try (Connection connection = DriverManager.getConnection( url, login, password )){		//On se connecte à la db et si la connexion saute, on a un message d'exeption
			String requete = "SELECT * FROM ENTREPOT";
			try ( Statement statement = connection.createStatement() ) {
				try (ResultSet resultSet = statement.executeQuery( requete ) ) {
					while( resultSet.next() ) {
						int idEntrepot = resultSet.getInt( "idEntrepot" );
						int idSite = resultSet.getInt( "idSite" );
						int coutFixe = resultSet.getInt( "coutFixe" );
						int stock = resultSet.getInt( "stock" );
						entrepotsDispos.add(new Entrepot(idEntrepot, idSite, coutFixe, stock));
					}
				}
			}
		}
        
        // essayer d'ouvrir et lire le fichier texte
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String line = reader.readLine(); // variable pour stocker chaque ligne lue du fichier
            reader.readLine(); // ignorer la première ligne
            DateLivraison = line; 
            n = Integer.parseInt(line);
            for (int i = 0; i < n ; i++) { // d'abord modifier classe Client avec quantité
                if (line != null) {
                	String[] parts = line.split(":"); // diviser chaque ligne en utilisant la virgule comme délimiteur
                	if (parts.length >= 2) { // Assurer que la ligne contient au moins deux parties
                        String mail = parts[0]; // Extraire et nettoyer le nom
                        int demande = Integer.parseInt(parts[1]);
                        clients.add(new Client(" ", mail, 0, demande));// Vérifier dans la base de données un client avec le même mail et lui ajouter la demande correspondante
                }
            }
        }
            /*
            String[] parts2 = line.split(","); // diviser chaque ligne en utilisant la virgule comme délimiteur
            int n2 = parts2.length;
            
            for (int i = 0; i < n ; i++) {
            	entrepots_dispos.
                clients.add(new Client(" ", mail, 0, demande));// Vérifier dans la base de données un client avec le même mail et lui ajouter la demande correspondante
            } */
        }
            catch (IOException e) {
            e.printStackTrace(); // Gestion des exceptions d'entrée/sortie
        }

        // Convertir la liste de Personne en une chaîne JSON
        String json = gson.toJson(clients);

        // Essayer d'écrire la chaîne JSON dans un nouveau fichier
        try (FileWriter writer = new FileWriter(outputFilePath)) {
            writer.write(json); // Écrire le JSON dans le fichier
        } catch (IOException e) {
            e.printStackTrace(); // Gestion des exceptions d'entrée/sortie
        }

        System.out.println("Conversion terminée."); // Confirmer la fin de la conversion
    }

  }
