package model;

public abstract class TrafficLight extends Semaphore {

	// Couleur du feu
	protected Color color;

	/**
	 * Constructeur du TrafficLight
	 * 
	 * @param color
	 *            du TrafficLight
	 */
	public TrafficLight(Color color) {
		this.color = color;
	}

	/**
	 * Constructeur par defaut du TrafficLight
	 */
	public TrafficLight() {
		this.color = Color.RED;
	}

	@Override
	/**
	 * Redefinition du toString
	 */
	public String toString() {
		return "Couleur du feu : " + this.color;
	}

	/**
	 * Methode abstraite qui permettrait de changer l'etat du TrafficLight (sa
	 * couleur)
	 */
	public abstract void changeState();

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
	@Override
	public int getMaxMovement(int speed, RoadPiece piece, int position) {
		if (this.color == Color.GREEN || piece.getLength() - 1 - position >= speed)
			return speed;
		else {
			return piece.getLength() - 1 - position;
		}
	}
}