package model;

public class PresenceRegulator extends Regulator {

	/**
	 * Constructeur du PresenceRegulator
	 */
	public PresenceRegulator() {
	}

	@Override
	/**
	 * Permet de lancer l'algorithme du Regulateur
	 */
	public void changeStates() {
		for (RoadPiece e : junction.endList) {
			if (e.endList.indexOf(junction) == 0) {
				Segment s = (Segment) e;
				for (int i = 0; i < s.getSensorList0().size(); i++) {
					if (s.getSensorList0().get(i).getSensorType() == SensorType.PresenceSensor) {
						PresenceSensor ps = (PresenceSensor) s.getSensorList0().get(i);
						// si la hashmap d'un capteur de présence n'est
						// pas vide c'est qu'il y a au moins une voiture
						// qui est passée sur le capteur.
						// on change donc l'état des feux et on sort de
						// la boucle pour éviter qu'un éventuel autre
						// capteur de présence ne rechange l'état des
						// feux
						if (!ps.getInformations().isEmpty()) {
							for (int j = 0; j < s.getSemaphoreList0().size(); j++) {
								s.getSemaphoreList0().get(j).changeState();
							}
							break;
						}
					}
				}
			} else if (e.endList.indexOf(junction) == 1) {
				Segment s = (Segment) e;
				for (int i = 0; i < s.getSensorList1().size(); i++) {
					if (s.getSensorList1().get(i).getSensorType() == SensorType.PresenceSensor) {
						PresenceSensor ps = (PresenceSensor) s.getSensorList1().get(i);
						if (!ps.getInformations().isEmpty()) {
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
