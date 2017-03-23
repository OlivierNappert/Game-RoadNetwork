package model;

public abstract class Regulator {

	protected Junction junction;

	/**
	 * 
	 * @return la junction presente sur le Regulateur
	 */
	public Junction getJunction() {
		return junction;
	}

	/**
	 * Modifie la jonction par celle presente en parametre
	 * 
	 * @param junction
	 *            a changer
	 */
	public void setJunction(Junction junction) {
		this.junction = junction;
	}

	/**
	 * Methode abstraite qui va derouler l'algorithme des Regulateurs
	 */
	public abstract void changeStates();
}
