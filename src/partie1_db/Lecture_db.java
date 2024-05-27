package partie1_db;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVReader;



public class Lecture_db {

	public static void main(String[] args) {

		List<Site> sites = new ArrayList<Site>();

		CSVReader reader = null;
		try {
			//Créer fct pour selectionner fichier à lire
			reader = new CSVReader(new FileReader("Jeux_de_donnees"+File.separator+"petit"+File.separator+"init-sites-30-Carre.csv"));
			String[] nextLine;
			int id, posx,posy;
			// Ignorez la première ligne (les en-têtes)
            reader.readNext();
			//read one line at a time 
			while ((nextLine = reader.readNext()) != null) { //readnext renvoie deja un tableau de valeur	
				for (String token : nextLine) {
					//sites.add(new sites(int idSite, int posX, int posY)); //Point pt1 = new Point(); et pt1 = new Point(1.0, 2.0);
					id = Integer.parseInt(token.split(";")[0]);
					posx = Integer.parseInt(token.split(";")[1]);
					posy = Integer.parseInt(token.split(";")[2]);      
					Site site = new Site(id, posx, posy);
					sites.add(site);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("sites : "+sites);
	}
}
