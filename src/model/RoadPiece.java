package model;

import java.util.ArrayList;
import java.util.Random;

public abstract class RoadPiece {
	protected String name;
	protected ArrayList<RoadPiece> endList;
	protected int endsNumber;
	protected ArrayList<Vehicle> vehicleList;
	protected int length;

	/**
	 * Constructeur du RoadPiece
	 * 
	 * @param name
	 *            du RoadPiece
	 * @param endsNumber
	 *            du RoadPiece
	 * @param length
	 *            du RoadPiece
	 */
	protected RoadPiece(String name, int endsNumber, int length) {
		this.name = name;
		this.endList = new ArrayList<RoadPiece>();
		this.endsNumber = endsNumber;
		this.length = length;
	}

	/**
	 * Getter du nom
	 * 
	 * @return nom du RoadPiece
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Getter de la longueur
	 * 
	 * @return longueur du RoadPiece
	 */
	public int getLength() {
		return this.length;
	}

	/**
	 * Getter de l'ArrayList de Vehicule
	 * 
	 * @return l'ArrayList de Vehicule
	 */
	public ArrayList<Vehicle> getVehicleList() {
		return this.vehicleList;
	}

	/**
	 * Verifie si deux RoadPieces sont connectés ou non
	 * 
	 * @param other
	 *            Piece a tester
	 * @return return TRUE si les pieces sont liees, FALSE sinon
	 */
	public boolean isLinkedTo(RoadPiece other) {
		return this.endList.contains(other);
	}

	/**
	 * Renvoie le déplacement réel maximal d'un véhicule en prenant en compte
	 * les Sémaphores
	 * 
	 * @param speed
	 *            Le déplacement maximal à effectuer
	 * @param piece
	 *            RoadPiece sur laquelle le véhicule est situé
	 * @param direction
	 *            RoadPiece vers lequel va le véhicule
	 * @param position
	 *            Position du véhicule sur piece
	 * @return Un entier représentant le déplacement maximal réel du vehicule
	 */
	public abstract int getMaxMovement(int speed, RoadPiece piece, RoadPiece direction, int position);

	/**
	 * Renvoie une nouvelle direction pour un véhicule
	 * 
	 * @param oldPiece
	 *            Ancienne piece de route sur laquelle se trouvait le vehicule
	 * @param rand
	 *            Generateur de nombre aleatoire
	 */
	public abstract RoadPiece getNewDirection(RoadPiece oldPiece, Random rand);

	/**
	 * Ajoute une connexion a une RoadPiece
	 * 
	 * @param end
	 *            La RoadPiece a ajouter
	 * @throws RoadPieceException
	 *             Erreur si on ne peut pas ajouter la connexion
	 */
	public void addEnd(RoadPiece end) throws RoadPieceException {
		if (this.endList == null || this.endList.size() >= this.endsNumber) {
			throw new RoadPieceException("Impossible de lier cette pièce: il y a trop de liens.");
		}
		if (!this.endList.contains(end)) {
			this.endList.add(end);
		}
	}

	/**
	 * Ajoute un Sensor à une RoadPiece.
	 * 
	 * @param sensor
	 *            Sensor à ajouter
	 * @param direction
	 *            RoadPiece à laquelle ajouter le Sensor
	 * @throws RoadPieceException
	 *             Exception dans le cas où la RoadPiece est invalide
	 */
	public abstract void addSensor(Sensor sensor, RoadPiece direction) throws RoadPieceException;

	/**
	 * Ajoute un Semaphore à une RoadPiece.
	 * 
	 * @param semaphore
	 *            Semaphore a ajouter
	 * @param direction
	 *            RoadPiece a laquelle ajouter le Semaphore
	 * @throws RoadPieceException
	 *             Exception dans le cas ou la RoadPiece est invalide
	 */
	public abstract void addSemaphore(Semaphore semaphore, RoadPiece direction) throws RoadPieceException;

	/**
	 * Supprime une connexion avec la RoadPiece appelante
	 * 
	 * @param end
	 *            RoadPiece a supprimer
	 * @throws RoadPieceException
	 *             Exception envoyée si les deux pièces ne sont pas connectées
	 */
	protected void removeEnd(RoadPiece end) throws RoadPieceException {
		if (!this.isLinkedTo(end))
			throw new RoadPieceException("Erreur, les deux pièces ne sont pas connectées.");
		else
			this.endList.remove(end);
	}

	/**
	 * Active les capteurs sur lesquels une voiture donnée passe
	 * 
	 * @param car
	 *            La voiture qui active un sensor
	 * @param startPos
	 *            Sa position de depart
	 * @param endPos
	 *            Sa position d'arrivee
	 */
	public void activateSensors(Vehicle car, int startPos, int endPos) {

	}

	/**
	 * Effectue les opérations à faire en premier comme la réinitialisation des
	 * sensors
	 */
	public void preTimeOperations() {

	}

	/**
	 * Effectue les opérations à faire après l'incrément de temps comme le
	 * changement d'état des sémaphores
	 */
	public void postTimeOperations() {

	}

	/**
	 * Affiche un message d'Alerte
	 */
	public void printAlertsMsg() {

	}

	/**
	 * Permet de vérifier que la pièce a bien été initialisée.
	 * 
	 * @return true si elle est bien initialisée; false sinon
	 */
	public boolean isInitialized() {
		return endList.size() == endsNumber;
	}
}