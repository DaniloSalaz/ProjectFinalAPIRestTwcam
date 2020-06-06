package es.uv.twcam.projects.airporject.Exceptions;

public class SeatsNotAvailableException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5001353912013872026L;
	
	public SeatsNotAvailableException() {
		this("Asientos no disponibles");
	}
	
	public SeatsNotAvailableException(String message) {
		this(message,null);
	}
	
	public SeatsNotAvailableException(String message, Throwable cause) {
		super(message,cause);
	}

}
