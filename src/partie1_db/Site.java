package partie1_db;

public class Site {
	
	private int id_site;
	private int pos_x;
	private int pos_y;
	
	public Site(int id_site, int pos_x, int pos_y) {
		/*if (id_site==null || pos_x==null || pos_y==null) {
			throw new IllegalArgumentException("Donnée(s) manquante(s)");
		} */
		this.id_site = id_site;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
	}

	public int getId() {
		return id_site;
	}

	public int getPosX() {
		return pos_x;
	}

	public int getPosY() {
		return pos_y;
	}
	
	public boolean equals(Object o) {
		return  o!=null
				&& o instanceof Site
				&& ( this.getId() == ((Site)o).getId()
				|| ( this.getPosX() == ((Site)o).getPosX()
				&& this.getPosY() == ((Site)o).getPosY()));
	}

}
