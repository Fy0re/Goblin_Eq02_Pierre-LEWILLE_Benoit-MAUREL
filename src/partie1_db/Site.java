package partie1_db;

public class Site {
	
	private int idSite;
	private int posX;
	private int posY;
	
	public Site(int idSite, int posX, int posY) {
		this.idSite = idSite;
		this.posX = posX;
		this.posY = posY;
	}

	public int getId() {
		return this.idSite;
	}

	public int getPosX() {
		return this.posX;
	}

	public int getPosY() {
		return this.posY;
	}
	
	public String toString() {
		return "("+ this.getId() +", "+ this.getPosX() +", "+ this.getPosY() +")";
	}
	
	//Permet de connaître la distance à vol d'oiseau entre 2 sites
	//(arrondi à l'entier supérieur)
	public Double distanceEntre(Site s) {
		return Math.ceil( 
				Math.sqrt( Math.pow(this.posX - s.getPosX(),2) + Math.pow(this.posY - s.getPosY(),2) ));
	}

}
