package model;

import java.util.ArrayList;
import java.util.Random;

public class Segment extends RoadPiece {
	// Sémaphores et capteurs de la file de droite
	private ArrayList<Semaphore> semaphoreList0;
	private ArrayList<Sensor> sensorList0;

	// File de gauche
	private ArrayList<Semaphore> semaphoreList1;
	private ArrayList<Sensor> sensorList1;

	/**
	 * Constructeur d'un Segment
	 * 
	 * @param name
	 *            du Segment
	 * @param length
	 *            du Segment
	 */
	public Segment(String name, int length) {
		super(name, 2, length);
		this.semaphoreList0 = new ArrayList<Semaphore>();
		this.sensorList0 = new ArrayList<Sensor>();
		this.semaphoreList1 = new ArrayList<Semaphore>();
		this.sensorList1 = new ArrayList<Sensor>();
	}

	/**
	 * Getter de l'ArrayList de droite des Sensors
	 * 
	 * @return ArrayList de droite des Sensors
	 */
	public ArrayList<Sensor> getSensorList0() {
		return sensorList0;
	}

	/**
	 * Getter de l'ArrayList de gauche des Sensors
	 * 
	 * @return ArrayList de gauche des Sensors
	 */
	public ArrayList<Sensor> getSensorList1() {
		return sensorList1;
	}

	/**
	 * Getter de l'ArrayList de droite d'un certain type des Sensors
	 * 
	 * @param type
	 *            que l'on recherche
	 * @return ArrayList de droite d'un certain type de Sensors
	 */
	public ArrayList<Sensor> getSensorList0(SensorType type) {
		ArrayList<Sensor> temp = new ArrayList<Sensor>();
		for (Sensor s : sensorList0) {
			if (s.getSensorType() == type)
				temp.add(s);
		}
		return temp;
	}

	/**
	 * Getter de l'ArrayList de gauche d'un certain type des Sensors
	 * 
	 * @param type
	 *            que l'on recherche
	 * @return ArrayList de gauche d'un certain type de Sensors
	 */
	public ArrayList<Sensor> getSensorList1(SensorType type) {
		ArrayList<Sensor> temp = new ArrayList<Sensor>();
		for (Sensor s : sensorList1) {
			if (s.getSensorType() == type)
				temp.add(s);
		}
		return temp;
	}

	/**
	 * Getter de la liste de droite des Semaphores
	 * 
	 * @return liste de droite des Semaphores
	 */
	public ArrayList<Semaphore> getSemaphoreList0() {
		return semaphoreList0;
	}

	/**
	 * Getter de la liste de gauche des Semaphores
	 * 
	 * @return liste de gauche des Semaphores
	 */
	public ArrayList<Semaphore> getSemaphoreList1() {
		return semaphoreList1;
	}

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
	public void addSemaphore(Semaphore semaphore, RoadPiece direction) throws RoadPieceException {
		int index = this.endList.indexOf(direction);
		if (index < 0 || index > 1) {
			throw new RoadPieceException(String.valueOf(this.name) + " n'est pas lié à " + direction.name + ".");
		} else {
			if (index == 0) {
				this.semaphoreList0.add(semaphore);
			} else if (index == 1) {
				this.semaphoreList1.add(semaphore);
			}
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
	public void addSensor(Sensor sensor, RoadPiece direction) throws RoadPieceException {
		int index = this.endList.indexOf(direction);
		if (index < 0 || index > 1) {
			throw new RoadPieceException(String.valueOf(this.name) + " n'est pas lié à " + direction.name + ".");
		} else {
			if (index == 0) {
				this.sensorList0.add(sensor);
			} else if (index == 1) {
				this.sensorList1.add(sensor);
			}
		}
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
		int index = endList.indexOf(direction);
		if (index < 0 || index > 1) {
			throw new RoadPieceException(String.valueOf(this.name) + " n'est pas lié à " + direction.name + ".");
		} else {
			int res, min = speed;

			// File de droite
			if (index == 0) {
				for (Semaphore e : semaphoreList0) {
					res = e.getMaxMovement(speed, piece, position);
					if (min > res)
						min = res;
				}
			}

			// File de gauche
			else if (index == 1) {
				for (Semaphore e : semaphoreList1) {
					res = e.getMaxMovement(speed, piece, position);
					if (min > res)
						min = res;
				}
			}

			return min;
		}
	}

	/**
	 * Renvoie une nouvelle direction pour un véhicule
	 * 
	 * @param oldPiece
	 *            Ancienne RoadPiece sur laquelle se trouvait le vehicule
	 * @param rand
	 *            Generateur de nombre aleatoire
	 */
	public RoadPiece getNewDirection(RoadPiece oldPiece, Random rand) {
		RoadPiece newpiece = oldPiece;
		for (int i = 0; i < this.endsNumber; i++) {
			if (this.endList.get(i) != oldPiece) {
				newpiece = this.endList.get(i);
				break;
			}
		}

		return newpiece;
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
		int index = endList.indexOf(car.getDirection());
		if (index == 0) {
			for (Sensor e : getSensorList0()) {
				if (e.position >= startPos && e.position <= endPos)
					e.activate(car);
			}
		} else if (index == 1) {
			for (Sensor e : getSensorList1()) {
				if (e.position >= startPos && e.position <= endPos)
					e.activate(car);
			}
		}
	}

	/**
	 * Effectue les opérations à faire en premier comme la réinitialisation des
	 * sensors
	 */
	public void preTimeOperations() {
		for (Sensor s : getSensorList0())
			s.reset();

		for (Sensor s : getSensorList1())
			s.reset();
	}

	/**
	 * Affiche un message d'Alerte
	 */
	public void printAlertsMsg() {
		String msg;
		for (Sensor e : sensorList0) {
			msg = e.printAlertMsg();
			if (msg != null)
				System.out.println(msg);
		}
		for (Sensor e : sensorList1) {
			msg = e.printAlertMsg();
			if (msg != null)
				System.out.println(msg);
		}
	}
}