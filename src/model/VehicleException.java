package model;

public class VehicleException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public VehicleException(String message) {
		super(message);
	}

	public VehicleException(String message, Throwable cause) {
		super(message, cause);
	}

	public VehicleException(Throwable cause) {
		super(cause);
	}
}