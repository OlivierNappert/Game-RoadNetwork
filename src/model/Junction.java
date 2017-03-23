package model;

import java.util.Random;

public abstract class Junction extends RoadPiece {
	protected Regulator regulator;

	/**
	 * Constructeur d'une jonction
	 * 
	 * @param name
	 *            de la junction
	 * @param endsNumber
	 *            de la junction
	 */
	protected Junction(String name, int endsNumber) {
		super(name, endsNumber, 1);
	}

	/**
	 * Changer le regulator
	 * 
	 * @param regulator
	 *            que l'on souhaite changer
	 */
	public void setRegulator(Regulator regulator) {
		this.regulator = regulator;
	}

	/**
	 * Retourne le nombre de mouvements maximal
	 * 
	 * @param speed
	 *            vitesse du véhicule
	 * @param piece
	 *            RoadPiece actuelle
	 * @param direction
	 *            RoadPiece vers laquelle il se dirige
	 * @param position
	 *            du vehicule actuel
	 * 
	 */
	public int getMaxMovement(int speed, RoadPiece piece, RoadPiece direction, int position) {
		return speed;
	}

	/**
	 * Renvoie une nouvelle direction pour un véhicule
	 * 
	 * @param oldPiece
	 *            Ancienne RoadPiece sur laquelle se trouvait le vehicule
	 * @param rand
	 *            Generateur de nombre aleatoire
	 */
	public RoadPiece getNewDirection(RoadPiece old, Random rand) {
		return this.endList.get(rand.nextInt(this.endsNumber));
	}

	/**
	 * Va permettre d'actionner les actions sur le regulateur
	 */
	public void postTimeOperations() {
		if (regulator != null)
			regulator.changeStates();
	}

	/**
	 * Ajoute un Sensor à une jonction
	 * 
	 * @param sensor
	 *            Sensor à ajouter
	 * @param direction
	 *            RoadPiece à laquelle ajouter le Sensor
	 * @throws RoadPieceException
	 *             Exception dans le cas où la RoadPiece est invalide
	 */
	public void addSensor(Sensor sensor, RoadPiece direction) throws RoadPieceException {
		throw new RoadPieceException(String.valueOf(this.name) + " est une jonction et ne peut recevoir de capteurs.");
	}

	/**
	 * Ajoute un Semaphore à une jonction
	 * 
	 * @param semaphore
	 *            Semaphore à ajouter
	 * @param direction
	 *            RoadPiece à laquelle ajouter le Sensor
	 * @throws RoadPieceException
	 *             Exception dans le cas où la RoadPiece est invalide
	 */
	public void addSemaphore(Semaphore sensor, RoadPiece direction) throws RoadPieceException {
		throw new RoadPieceException(
				String.valueOf(this.name) + " est une jonction et ne peut recevoir de sémaphores.");
	}
}