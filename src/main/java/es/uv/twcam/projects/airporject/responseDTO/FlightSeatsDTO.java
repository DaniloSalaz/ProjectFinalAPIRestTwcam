package es.uv.twcam.projects.airporject.responseDTO;

public class FlightSeatsDTO {

	private int idFlight;
	private String passengerNames;
	private String seat;

	public int getIdFlight() {
		return idFlight;
	}

	public void setIdFlight(int idFlight) {
		this.idFlight = idFlight;
	}

	public String getPassengerNames() {
		return passengerNames;
	}

	public void setPassengerNames(String passengerNames) {
		this.passengerNames = passengerNames;
	}

	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public FlightSeatsDTO() {

	}

	public FlightSeatsDTO(int idFlight, String passengerNames, String seat) {
		super();
		this.idFlight = idFlight;
		this.passengerNames = passengerNames;
		this.seat = seat;
	}

}
