package partie1_db;

public class Entrepot {

	private int idEntrepot;
	private int idSite;
	private int coutFixe;
	private int stock;
	
	public Entrepot(int idEntrepot, int idSite, int coutFixe, int stock) {
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
	
	public String toString() {
		return "("+ this.getIdEntrepot() +", "+ this.getIdSite() +", "+ this.getCoutFixe()
				+", "+ this.getStock() +")";
	}

}
