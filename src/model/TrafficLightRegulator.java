package model;

public class TrafficLightRegulator extends Regulator {

	/**
	 * Constructeur du TrafficLightRegulator
	 */
	public TrafficLightRegulator() {
	}

	@Override
	/**
	 * Permet de changer l'etat du Regulateur actuel
	 */
	public void changeStates() {
		for (RoadPiece e : junction.endList) {
			if (e.endList.indexOf(junction) == 0) {
				Segment s = (Segment) e;
				for (int i = 0; i < s.getSemaphoreList0().size(); i++) {
					s.getSemaphoreList0().get(i).changeState();
				}
			} else if (e.endList.indexOf(junction) == 1) {
				Segment s = (Segment) e;
				for (int i = 0; i < s.getSemaphoreList1().size(); i++) {
					s.getSemaphoreList1().get(i).changeState();
				}
			}
		}
	}

}
