package model;

public class BicolorTrafficLight extends TrafficLight {

	/**
	 * Constructeur d'un feu bicolore avec une couleur en paramètre
	 * 
	 * @param color
	 *            Couleur initial
	 */
	public BicolorTrafficLight(Color color) {
		super(color);
	}

	/**
	 * Constructeur d'un feu bicolore
	 */
	public BicolorTrafficLight() {
		super();
	}

	@Override
	/**
	 * Redefinition du toString
	 */
	public String toString() {
		return "<BicolorTrafficLight> " + super.toString();
	}

	@Override
	/**
	 * Change la couleur du feu du vert a rouge et inversement
	 */
	public void changeState() {
		color = color == Color.RED ? Color.GREEN : Color.RED;
	}
}
