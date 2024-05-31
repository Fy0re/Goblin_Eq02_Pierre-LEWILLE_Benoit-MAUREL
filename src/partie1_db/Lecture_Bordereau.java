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

        List<Entrepot> entrepots = new ArrayList<>(); 
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
						entrepots.add(new Entrepot(idEntrepot, idSite, coutFixe, stock));
					}
				}
			}
		}
        
        // essayer d'ouvrir et lire le fichier texte
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            reader.readLine(); // ignorer la première ligne
            DateLivraison = reader.readLine(); 
            String line = reader.readLine(); // variable pour stocker chaque ligne lue du fichier
            n = Integer.parseInt(line);
            for (int i = 0; i < n ; i++) { 
            	line = reader.readLine();
                if (line != null) {
                	String[] parts = line.split(":"); // diviser chaque ligne en utilisant la virgule comme délimiteur
                        String mail = parts[0]; // Extraire et nettoyer le nom
                        int demande = Integer.parseInt(parts[1]);
                        //clients.get(clients.indexOf(mail)).setDemande(demande); 
                        for(Client client : clients) {
                            if(client.getMail().equals(mail)) {
                                client.setDemande(demande);
                                break;
                            }
                        } 
            }
        }
            line = reader.readLine(); // lire les données des entrepôts disponibles
            String[] parts2 = line.split(","); // diviser chaque ligne en utilisant la virgule comme délimiteur
            int n2 = entrepots.size();
            List<Integer> entrepotsIds = new ArrayList<>();
            
            for (int i = 0; i < parts2.length ; i++) {
            	entrepotsIds.add(Integer.parseInt(parts2[i]));
            }
            
            for (int i = 0; i < n2 ; i++) {
            	if (entrepotsIds.contains(entrepots.get(i).getIdEntrepot())== false){
                entrepots.remove(i);
                i--;
            	}
            	} 
        }
            catch (IOException e) {
            e.printStackTrace(); // Gestion des exceptions d'entrée/sortie
        }

        String clientsJson = gson.toJson(clients);
        String entrepotsJson = gson.toJson(entrepots);

        // Essayer d'écrire la chaîne JSON dans un nouveau fichier
        try (FileWriter writer = new FileWriter(outputFilePath)) {
            writer.write(clientsJson + "\n" + entrepotsJson); // Écrire le JSON dans le fichier
        } catch (IOException e) {
            e.printStackTrace(); // Gestion des exceptions d'entrée/sortie
        }

        System.out.println("Conversion terminée."); // Confirmer la fin de la conversion
    }

  }
