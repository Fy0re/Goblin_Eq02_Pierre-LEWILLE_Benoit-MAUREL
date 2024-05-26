package partie1_db;

public class Site {
	
	private int idSite;
	private int posX;
	private int posY;
	
	public Site(int idSite, int posX, int posY) {
		/*if (id_site==null || pos_x==null || pos_y==null) {
			throw new IllegalArgumentException("Donnée(s) manquante(s)");
		} */
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
	
	public boolean equals(Object o) {
		return  o!=null
				&& o instanceof Site
				&& ( this.getId() == ((Site)o).getId()
				|| ( this.getPosX() == ((Site)o).getPosX()
				&& this.getPosY() == ((Site)o).getPosY()));
	}

}
