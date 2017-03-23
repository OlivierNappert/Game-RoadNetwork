package model;

import java.util.Collection;
import java.util.Iterator;

public class SpeedRegulator extends Regulator {
	private int speedLimit;

	/**
	 * Constructeur du SpeedRegulator
	 * 
	 * @param speedLimit
	 *            qui est la vitesse maximale du Regulateur
	 */
	public SpeedRegulator(int speedLimit) {
		this.speedLimit = speedLimit;
	}

	/**
	 * Permet de lancer l'algorithme du Regulateur
	 */
	@Override
	public void changeStates() {
		for (RoadPiece e : junction.endList) {
			if (e.endList.indexOf(junction) == 0) {
				Segment s = (Segment) e;
				for (int i = 0; i < s.getSensorList0().size(); i++) {
					if (s.getSensorList0().get(i).getSensorType() == SensorType.SpeedSensor) {
						PresenceSensor ps = (PresenceSensor) s.getSensorList0().get(i);
						// si la hashmap d'un capteur de vitesse
						// contient une vitesse supérieure à la limite
						// fixée on change l'état des feux
						// et on sort de la boucle pour éviter qu'un
						// éventuel autre capteur de présence ne
						// rechange l'état des feux
						Collection<Integer> speeds = ps.getInformations().values();
						Iterator<Integer> itr = speeds.iterator();
						while (itr.hasNext()) {
							if (itr.next() > speedLimit) {
								for (int j = 0; j < s.getSemaphoreList0().size(); j++) {
									s.getSemaphoreList0().get(j).changeState();
								}
								break;
							}
						}
					}
				}
			} else if (e.endList.indexOf(junction) == 1) {
				Segment s = (Segment) e;
				for (int i = 0; i < s.getSensorList1().size(); i++) {
					if (s.getSensorList1().get(i).getSensorType() == SensorType.SpeedSensor) {
						PresenceSensor ps = (PresenceSensor) s.getSensorList1().get(i);
						Collection<Integer> speeds = ps.getInformations().values();
						Iterator<Integer> itr = speeds.iterator();
						while (itr.hasNext()) {
							if (itr.next() > speedLimit) {
								for (int j = 0; j < s.getSemaphoreList1().size(); j++) {
									s.getSemaphoreList1().get(j).changeState();
								}
								break;
							}
						}
					}
				}
			}
		}
	}

}
