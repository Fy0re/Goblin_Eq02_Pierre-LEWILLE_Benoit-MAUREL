package partie1_db;

public class Entrepot {

	private int idEntrepot;
	private int idSite;
	private int coutFixe;
	private int stock;
	
	public Entrepot(int idEntrepot, int idSite, int coutFixe, int stock) {
		/*if (id_site==null || pos_x==null || pos_y==null) {
			throw new IllegalArgumentException("Donnée(s) manquante(s)");
		} */
		this.idEntrepot = idEntrepot;
		this.idSite = idSite;
		this.coutFixe = coutFixe;
		this.stock = stock;
	}

	public int getIdEntrepot() {
		return this.idEntrepot;
	}
	
	public int getIdSite() {
		return this.idSite;
	}

	public int getCoutFixe() {
		return this.coutFixe;
	}

	public int getStock() {
		return this.stock;
	}
	
	public boolean equals(Object o) {
		return  o!=null
				&& o instanceof Entrepot
				&& ( this.getIdSite() == ((Entrepot)o).getIdSite()
				|| this.getIdEntrepot() == ((Entrepot)o).getIdEntrepot());
	}

}
