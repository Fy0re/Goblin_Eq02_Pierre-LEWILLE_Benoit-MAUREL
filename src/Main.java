
import partie1_db.InitialisationDB;
import partie1_db.InitialisationDBClient;
import partie1_db.InitialisationDBEntrepot;
import partie1_db.InitialisationDBRoute;

public class Main {
	public static void main(String[] args) throws Exception {
		String answerStr;
		
		do {
			System.out.println(" +-------------------------------------+");
			System.out.println(" |               GOBLIN                |");
			System.out.println(" +-------------------------------------+");
			System.out.println(" [0] Initialisation");
			System.out.println(" [1] Calcul bordereau de livraison");
			System.out.println(" [Q] Quitter");
			answerStr = Clavier.lireString();
			switch (answerStr) {
			case "0" :
				System.out.println(" +-------------------------------------+");
				System.out.println(" |       INITIALISATION DATABASE       |");
				System.out.println(" +-------------------------------------+");
				System.out.println(" [1] (Re)Initialiser la database complete");
				System.out.println(" [2] (Re)Initialiser la database Client");
				System.out.println(" [3] (Re)Initialiser la database Entrepot");
				System.out.println(" [4] (Re)Initialiser la database Route");
				System.out.println(" [Q] Quitter");
				//Penser à ajouter si l'utilisateur mets une autre valeur, le retour en arrière et quitter
				answerStr = Clavier.lireString();
				switch (answerStr) {
				case "1" :
					InitialisationDB.main(args);
					System.out.println(" +-------------------------------------+");
					System.out.println(" |     (RE)INITIALISATION REUSSIE      |");
					System.out.println(" +-------------------------------------+");
					break;
				case "2" :
					InitialisationDBClient.main(args);
					System.out.println(" +-------------------------------------+");
					System.out.println(" |     (RE)INITIALISATION REUSSIE      |");
					System.out.println(" +-------------------------------------+");
					break;
				case "3" :
					InitialisationDBEntrepot.main(args);
					System.out.println(" +-------------------------------------+");
					System.out.println(" |     (RE)INITIALISATION REUSSIE      |");
					System.out.println(" +-------------------------------------+");
					break;
				case "4" :
					InitialisationDBRoute.main(args);
					System.out.println(" +-------------------------------------+");
					System.out.println(" |     (RE)INITIALISATION REUSSIE      |");
					System.out.println(" +-------------------------------------+");
					break;
				}
				break;
			case "1" :
				System.out.println(" +-------------------------------------+");
				System.out.println(" |    CALCUL BORDEREAU DE LIVRAISON    |");
				System.out.println(" +-------------------------------------+");
				System.out.println("Vous souhaitez faire le calcul pour quelle date ?");
				System.out.println("(AAAA-MM-JJ)");
				//Faire une variable de type date pour chercher le bordereau associé
				//Penser à vérifier si la donnée est bonne
				break;
			}
		} while (!answerStr.toUpperCase().equals("Q"));
	}
}
