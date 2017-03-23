package model;

public abstract class Semaphore {

	/**
	 * Change l'etat d'une Semaphore
	 */
	public abstract void changeState();

	/**
	 * Retourne le nombre de mouvement maximal pour une voiture avec une
	 * position et une vitesse donnee pour un vehicule
	 */
	public abstract int getMaxMovement(int speed, RoadPiece piece, int position);
}
