package model;

public class SpeedLimit extends Semaphore {
	private int limit;

	/**
	 * Constructeur du SpeedLimit
	 * 
	 * @param limit
	 *            de vitesse
	 */
	public SpeedLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * Renvoie le déplacement réel maximal d'un véhicule en prenant en compte ce
	 * SpeedLimit
	 * 
	 * @param speed
	 *            Le déplacement maximal à effectuer
	 * @param piece
	 *            RoadPiece sur laquelle le véhicule est situé
	 * @param position
	 *            Position du véhicule sur piece
	 * @return Un entier représentant le déplacement maximal réel du vehicule
	 */
	public int getMaxMovement(int speed, RoadPiece piece, int position) {
		int ratio = speed / limit - 1;
		if (ratio < 0)
			ratio = 0;

		int res = speed - ratio * (piece.getLength() - 1 - position);
		if (res > 0)
			return res;
		else
			return 0;
	}

	/**
	 * Getter de la limite de vitesse
	 * 
	 * @return limite de vitesse
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * Setter de la limite de vitesse
	 * 
	 * @param limit
	 *            de vitesse a changer
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * Changement d'etat du SpeedLimit
	 */
	@Override
	public void changeState() {
	}
}
