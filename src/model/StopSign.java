package model;

public class StopSign extends Semaphore {
	private boolean active; // Actif actuellement ou non

	/**
	 * Constructeur du StopSign
	 * 
	 * @param state
	 *            du StopSign
	 */
	public StopSign(boolean state) {
		active = state;
	}

	/**
	 * Constructeur du StopSigne par defaut
	 */
	public StopSign() {
		active = false;
	}

	/**
	 * Change l'etat du panneau d'actif a inactif et inversement
	 */
	public void changeState() {
		active = !active;
	}

	/**
	 * Getter de l'etat du StopSign
	 * 
	 * @return l'etat du StopSign
	 */
	public boolean getState() {
		return active;
	}

	@Override
	/**
	 * Redefinition du toString
	 */
	public String toString() {
		return "<StopSign> Etat : " + ((active) ? "actif" : "inactif");
	}

	/**
	 * 
	 * @param speed
	 *            la vitesse actuelle de la voiture
	 * 
	 * @param piece
	 *            piece sur laquelle sa trouve la voiture
	 * 
	 * @param position
	 *            la position de la voiture
	 * 
	 * @return retourne la longueur maximale du mouvement que la voiture peut
	 *         faire pour ce pas de temps
	 */
	public int getMaxMovement(int speed, RoadPiece piece, int position) {
		if (!active || piece.getLength() - 1 - position == 0)
			return speed;
		else
			return piece.getLength() - 1 - position;
	}
}
