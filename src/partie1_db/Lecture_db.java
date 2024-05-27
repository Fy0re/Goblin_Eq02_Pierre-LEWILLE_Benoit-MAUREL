package partie1_db;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVReader;



public class Lecture_db {

	public static void main(String[] args) {
	
		
		//-----------------------------------
		//Lecture du fichier pour les clients
		//-----------------------------------
		List<Client> clients = new ArrayList<Client>();
		CSVReader readerClient = null;
		try {
			readerClient = new CSVReader(new FileReader("Jeux_de_donnees"+File.separator+"petit"+File.separator+"init-clients-30-10-Carre.csv"));
			String[] nextLineClient;
			String nom, mail;
			int emplacementClient;
			//Ignore la première ligne (les en-têtes)
            readerClient.readNext();
			//Lis les lignes 1 par 1
			while ((nextLineClient = readerClient.readNext()) != null) {
				for (String tokenClient : nextLineClient) {
					nom = tokenClient.split(";")[0];
					mail = tokenClient.split(";")[1];
					emplacementClient = Integer.parseInt(tokenClient.split(";")[2]);      
					Client client = new Client(nom, mail, emplacementClient);
					clients.add(client);
				}
			}
		}
		catch(Exception eClient) {
			eClient.printStackTrace();
		}
		
		
		//-------------------------------------
		//Lecture du fichier pour les entrepots
		//-------------------------------------
		List<Entrepot> entrepots = new ArrayList<Entrepot>();
		CSVReader readerEntrepot = null;
		try {
			readerEntrepot = new CSVReader(new FileReader("Jeux_de_donnees"+File.separator+"petit"+File.separator+"init-entrepots-30-5-Carre.csv"));
			String[] nextLineEntrepot;
			int idEntrepot, emplacementEntrepot, coutFixe, stock;
			//Ignore la première ligne (les en-têtes)
            readerEntrepot.readNext();
			//Lis les lignes 1 par 1
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
		
		
		//---------------------------------
		//Lecture du fichier pour les sites
		//---------------------------------
		List<Site> sites = new ArrayList<Site>();
		CSVReader readerSite = null;
		try {
			readerSite = new CSVReader(new FileReader("Jeux_de_donnees"+File.separator+"petit"+File.separator+"init-sites-30-Carre.csv"));
			String[] nextLineSite;
			int idSite, posX, posY;
			//Ignore la première ligne (les en-têtes)
            readerSite.readNext();
			//Lis les lignes 1 par 1
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
		
		
		//----------------------------------
		//Lecture du fichier pour les routes
		//----------------------------------
		List<Route> routes = new ArrayList<Route>();
		CSVReader readerRoute = null;
		try {
			readerRoute = new CSVReader(new FileReader("Jeux_de_donnees"+File.separator+"petit"+File.separator+"init-routes-30-45-Carre.csv"));
			String[] nextLineRoute;
			int depart, arrivee;
			//Ignore la première ligne (les en-têtes)
            readerRoute.readNext();
			//Lis les lignes 1 par 1
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
		
		System.out.print(routes.toString());
	}
}
