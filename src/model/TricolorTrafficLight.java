package model;

public class TricolorTrafficLight extends TrafficLight {

	/**
	 * Constructeur par default du TricolorTrafficlight
	 * 
	 * @param color
	 *            du TricolorTrafficLight
	 *
	 */
	public TricolorTrafficLight(Color color) {
		super(color);
	}

	/**
	 * Constructeur par defaut du TricolorTrafficLight
	 */
	public TricolorTrafficLight() {
		super();
	}

	@Override
	/**
	 * Redefinition du toString
	 */
	public String toString() {
		return "<TricolorTrafficLight> " + super.toString();
	}

	@Override
	/**
	 * Change la couleur du feu de vert a orange, de orange a rouge et de rouge
	 * a vert
	 */
	public void changeState() {
		if (this.color == Color.RED)
			this.color = Color.GREEN;
		else if (this.color == Color.GREEN)
			this.color = Color.ORANGE;
		else if (this.color == Color.ORANGE)
			this.color = Color.RED;
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
		if (color == Color.ORANGE)
			return speed - (piece.getLength() - 1 - position);
		else
			return super.getMaxMovement(speed, piece, position);
	}
}
