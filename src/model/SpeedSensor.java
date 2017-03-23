package model;

public class SpeedSensor extends Sensor{
	/**
	 * Constructeur du SpeedSensor
	 * @param position du capteur de vitesse
	 */
	public SpeedSensor(int position) {
		super(position);
		type = SensorType.SpeedSensor;
	}

	@Override
	/**
	 * Retourne le message d'alerte
	 */
	public String printAlertMsg() {
		return "";
	}

	@Override
	/**
	 * Rajoute un Vehicule dans l'HashMap qui contient les informations
	 */
	public void activate(Vehicle v) {
		this.informations.put(v.getName(), v.getSpeed());
	}
}