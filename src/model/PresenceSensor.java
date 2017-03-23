package model;

import java.util.Set;

public class PresenceSensor extends Sensor {

	/**
	 * Constructeur du PresenceSensor
	 * 
	 * @param position
	 *            de ce PresenceSensor
	 */
	public PresenceSensor(int position) {
		super(position);
		type = SensorType.PresenceSensor;
	}

	@Override
	/**
	 * Prévient des risques de collisions
	 */
	public String printAlertMsg() {
		if (this.informations.size() > 1) {
			String s1 = "Risque de collision entre plusieurs véhicules : ";
			Set<String> set = informations.keySet();
			for (String e : set)
				s1 += e.toString() + " | ";

			return s1;
		}

		else
			return null;
	}

	@Override
	/**
	 * Rajoute un Vehicule dans l'HashMap qui contient les informations
	 */
	public void activate(Vehicle v) {
		this.informations.put(v.getName(), 1);
	}
}
