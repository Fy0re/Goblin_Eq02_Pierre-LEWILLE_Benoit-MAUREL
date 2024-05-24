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
	      //read one line at a time  
	      while ((nextLine = reader.readNext()) != null) {
	        for (String token: nextLine) {
	          System.out.println(token);
	        }
	        System.out.print("\n");
	      }
	    }
	    catch(Exception e) {
	      e.printStackTrace();
	    }
	}

}
