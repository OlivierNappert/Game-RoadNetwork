package model;

import java.util.HashMap;

public abstract class Sensor {

	protected final int position;
	protected SensorType type;

	protected HashMap<String, Integer> informations;

	/**
	 * Constructeur du Sensor
	 * 
	 * @param position
	 *            du Sensor
	 */
	public Sensor(int position) {
		this.position = position;
		informations = new HashMap<String, Integer>();
	}

	/**
	 * Getter du Sensor
	 * 
	 * @return position du Sensor
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Getter de l'HashMap avec les informations
	 * 
	 * @return l'HashMap avec les informations
	 */
	public HashMap<String, Integer> getInformations() {
		return informations;
	}

	/**
	 * Getter du type de Sensor
	 * 
	 * @return type de Sensor
	 */
	public SensorType getSensorType() {
		return this.type;
	}

	/**
	 * Setter du Sensor
	 * 
	 * @param type
	 *            de Sensor a modifier
	 */
	public void setSensorType(SensorType type) {
		this.type = type;
	}

	/**
	 * Réinitialise l'état du capteur au début d'un pas de temps.
	 * 
	 * Il faudra faire un informations = new ArrayList<Truc> (impossible à faire
	 * dans cette classe puisqu'on peut pas faire de new ArrayList<T>).
	 */
	public void reset() {
		informations.clear();
	}

	/**
	 * Retourne ou non (null) un message d'alerte formaté (avec possiblement des
	 * \n) selon l'état actuel du capteur.
	 * 
	 * Par ex pour le presencesensor ça retournera un message s'il a repéré deux
	 * voitures.
	 * 
	 * @return Une chaîne de caractères contenant le message
	 */
	public abstract String printAlertMsg();

	/**
	 * Permet d'ajouter une Vehicle dans les informations du capteur
	 * 
	 * @param v
	 *            Vehicle a ajouter
	 */
	public abstract void activate(Vehicle v);

}
