package model;

import java.util.Random;

public class Vehicle {
	private int maxSpeed;
	private int speed;
	private String name;
	private RoadPiece piece;
	private int position;
	private RoadPiece direction;

	public Vehicle(String name, int maxSpeed) {
		this.name = name;
		this.maxSpeed = maxSpeed;
		this.speed = 0;
	}

	/**
	 * Place un vehicule a une position donnée sur une RoadPiece précise
	 * 
	 * @param piece
	 *            RoadPiece a laquelle ajouter le véhicule
	 * @param position
	 *            Position a laquelle ajouter le véhicule
	 * @param direction
	 *            Direction vers laquelle se déplace le véhicule
	 * @throws RoadPieceException
	 *             Exception si les deux morceaux de routes ne sont pas
	 *             connectés ou si la position est hors de la route
	 */
	public void setPosition(RoadPiece piece, int position, RoadPiece direction) throws RoadPieceException {
		if (!piece.isLinkedTo(direction)) {
			throw new RoadPieceException(
					String.valueOf(piece.getName()) + " et " + direction.getName() + " ne sont pas liées!");
		}
		if (position >= piece.getLength()) {
			throw new RoadPieceException("La position spécifiée est en dehors de la route !");
		}
		if (piece != null && piece.vehicleList != null)
			for (Vehicle e : piece.vehicleList) {
				if (e.position == position)
					throw new RoadPieceException("La position spécifiée est déjà occupée par un véhicule !");
			}

		this.piece = piece;
		this.position = position;
		this.direction = direction;
	}

	/**
	 * Getter de position
	 * 
	 * @return position du Vehicle
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Getter de la direction
	 * 
	 * @return direction du Vehicle
	 */
	public RoadPiece getDirection() {
		return direction;
	}

	/**
	 * Getter de la vitesse
	 * 
	 * @return la vitesse du Vehicle
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Getter de la vitesse maximale du Vehicle
	 * 
	 * @return vitesse maximale du Vehicle
	 */
	public int getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * Getter du nom du Vehicle
	 * 
	 * @return nom du Vehicle
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Permet de vérifier que la voiture a bien été initialisée.
	 * 
	 * @return true si elle est bien initialisée; false sinon
	 */
	public boolean isInitialized() {
		if (piece == null || direction == null)
			return false;
		else
			return true;
	}

	@Override
	public String toString() {
		return "Véhicule [" + name + "]";
	}

	/**
	 * Affiche l'état d'un véhicule
	 * 
	 * @return String spécifiant l'état du véhicule
	 */
	public String printState() {
		if (this.piece == null || this.direction == null) {
			return "<" + this.name + "> n'est nul part...";
		}
		return "<" + this.name + "> est sur [" + this.piece.getName() + "], à ["
				+ (this.piece.getLength() - this.position) + "] unités de [" + this.direction.getName() + "].";
	}

	/**
	 * Déplace un véhicule selon son état et celui du réseau.
	 * 
	 * @param rand
	 *            Variable d'aléatoire
	 */
	public void move(Random rand) {
		int moves = maxSpeed;
		int tempSpeed = 0;
		do {
			// On demande le mouvement maximum possible pour la voiture selon sa
			// position
			moves = piece.getMaxMovement(moves, piece, direction, position);

			if (moves > 0) {
				// Cas on reste sur meme piece
				if (position + moves <= piece.getLength() - 1) {
					int oldpos = position;
					position += moves;
					tempSpeed += moves;
					moves = 0;
					piece.activateSensors(this, oldpos, position);
				}
				// Cas on change de piece
				else {
					moves -= piece.getLength() - 1 - position;
					tempSpeed += piece.getLength() - 1 - position;
					RoadPiece oldPiece = piece;
					oldPiece.activateSensors(this, position, piece.getLength() - 1);
					piece = direction;
					direction = piece.getNewDirection(oldPiece, rand);
					position = 0;
				}
			}

			else if (moves == 0) {
				piece.activateSensors(this, position, position);
			}
		} while (moves > 0);
		speed = tempSpeed;
	}
}
