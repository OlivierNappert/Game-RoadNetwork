package model;

public class RoadPieceException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public RoadPieceException(String message) {
		super(message);
	}

	public RoadPieceException(String message, Throwable cause) {
		super(message, cause);
	}

	public RoadPieceException(Throwable cause) {
		super(cause);
	}
}