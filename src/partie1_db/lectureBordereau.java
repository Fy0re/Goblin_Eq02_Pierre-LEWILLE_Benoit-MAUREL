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


public class lectureBordereau {
	
	public static void main(String[] args) throws Exception {
				
        String inputFilePath = "Jeux_de_donnees"+File.separator+"petit"+File.separator+ "init-bordereau-commande-2021-12-25.txt"; // Chemin vers le fichier texte source
        String outputFilePath = "Jeux_de_donnees"+File.separator+"petit"+File.separator+"output.json"; // Chemin vers le fichier JSON de sortie

        Gson gson = new Gson(); // création d'une instance de Gson pour la conversion

        List<Entrepot> entrepots = new ArrayList<>(); 
        List<Client> clients = new ArrayList<>(); 
        List<Client> affichageClient = new ArrayList<>();
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
                	String[] parts = line.split(" : "); // diviser chaque ligne en utilisant la virgule comme délimiteur
                        String mail = parts[0]; // Extraire le nom
                        int demande = Integer.parseInt(parts[1]);
                        //clients.get(clients.indexOf(mail)).setDemande(demande); 
                        for(Client client : clients) {
                            if(client.getMail().equals(mail)) {
                                client.setDemande(demande);
                                affichageClient.add(client);
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
            
            for (int i = 0; i < entrepots.size(); i++) {
            	if (entrepotsIds.contains(entrepots.get(i).getIdEntrepot())== false){
                entrepots.remove(i);
                i--;
            	}
            	} 
        }
            catch (IOException e) {
            e.printStackTrace(); // Gestion des exceptions d'entrée/sortie
        }

        String clientsJson = gson.toJson(affichageClient);
        String entrepotsJson = gson.toJson(entrepots);
        
        List<Integer> capacity_facility = new ArrayList<>(); 
        List<Integer> fixed_cost_facility = new ArrayList<>(); 
        List<Integer> demand_customer = new ArrayList<>(); 
        List<String> cost_matrix = new ArrayList<>(); 
        int num_facility_locations = entrepots.size();
        int num_customers = affichageClient.size();
        
        for (int i = 0; i < entrepots.size(); i++) {
            	capacity_facility.add(entrepots.get(i).getStock());
            	fixed_cost_facility.add(entrepots.get(i).getCoutFixe());
            	}
        for (int i = 0; i < affichageClient.size(); i++) {
        	demand_customer.add(affichageClient.get(i).getDemande());
        	}

        // Essayer d'écrire la chaîne JSON dans un nouveau fichier
        /*try (FileWriter writer = new FileWriter(outputFilePath)) {
            writer.write(clientsJson + "\n" + entrepotsJson); // Écrire le JSON dans le fichier
        } catch (IOException e) {
            e.printStackTrace(); // Gestion des exceptions d'entrée/sortie
        }
        try (FileWriter writer = new FileWriter(outputFilePath)) {
            writer.write("{"+"\n" + '"'+"capacity_facility"+'"'+": " + capacity_facility + "," + "\n" + '"'+"fixed_cost_facility"+'"'+": " +  fixed_cost_facility + "," + "\n" + '"'+"demand_customer"+'"'+": " + demand_customer + "," + "\n" +  '"'+"num_facility_locations"+'"'+": " + num_facility_locations +"," + "\n" + '"'+"num_customers"+'"'+": " + num_customers +"\n" + "}"); // Écrire le JSON dans le fichier
        } catch (IOException e) {
            e.printStackTrace(); // Gestion des exceptions d'entrée/sortie
        }
*/
     // Création d'un FileWriter pour écrire dans un fichier spécifié.
        try (FileWriter writer = new FileWriter(outputFilePath)) {
            // Début de l'écriture de l'objet JSON avec une accolade ouvrante.
            writer.write("{\n");
            
            // Écriture du tableau JSON pour 'capacity_facility'.
            writer.write("\"capacity_facility\": [\n");
            // Boucle pour écrire chaque capacité de l'installation, avec une virgule si ce n'est pas le dernier élément.
            for (int i = 0; i < capacity_facility.size(); i++) {
                writer.write("    " + capacity_facility.get(i) + (i < capacity_facility.size() - 1 ? ",\n" : "\n"));
            }
            // Fermeture du tableau 'capacity_facility'.
            writer.write("],\n");
            
            // Écriture du tableau JSON pour 'fixed_cost_facility'.
            writer.write("\"fixed_cost_facility\": [\n");
            // Boucle pour écrire chaque coût fixe, avec une virgule si ce n'est pas le dernier élément.
            for (int i = 0; i < fixed_cost_facility.size(); i++) {
                writer.write("    " + fixed_cost_facility.get(i) + (i < fixed_cost_facility.size() - 1 ? ",\n" : "\n"));
            }
            // Fermeture du tableau 'fixed_cost_facility'.
            writer.write("],\n");
            
            // Écriture du tableau JSON pour 'demand_customer'.
            writer.write("\"demand_customer\": [\n");
            // Boucle pour écrire chaque demande du client, avec une virgule si ce n'est pas le dernier élément.
            for (int i = 0; i < demand_customer.size(); i++) {
                writer.write("    " + demand_customer.get(i) + (i < demand_customer.size() - 1 ? ",\n" : "\n"));
            }
            // Fermeture du tableau 'demand_customer'.
            writer.write("],\n");
            
            // Écriture du nombre total de localisations des installations.
            writer.write("\"num_facility_locations\": " + num_facility_locations + ",\n");
            // Écriture du nombre total de clients.
            writer.write("\"num_customers\": " + num_customers + "\n");
            
            // Fermeture de l'objet JSON.
            writer.write("}\n");
        // Gestion des exceptions d'entrée/sortie.
        } catch (IOException e) {
            // Impression de la trace de l'erreur en cas d'exception.
            e.printStackTrace();
        }

        System.out.println("Conversion terminée."); // Confirmer la fin de la conversion
    }

  }
