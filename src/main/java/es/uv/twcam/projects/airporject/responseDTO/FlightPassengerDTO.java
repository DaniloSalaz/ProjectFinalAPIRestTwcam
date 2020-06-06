package es.uv.twcam.projects.airporject.responseDTO;

public class FlightPassengerDTO {

	private int idFlight;
	private String Passenger;
	private String checkIn;
	private String email;
	
	public FlightPassengerDTO() {
		
	}

	public FlightPassengerDTO(int idFlight, String passenger, String checkIn, String email) {
		super();
		this.idFlight = idFlight;
		Passenger = passenger;
		this.checkIn = checkIn;
		this.email = email;
	}

	public int getIdFlight() {
		return idFlight;
	}

	public void setIdFlight(int idFlight) {
		this.idFlight = idFlight;
	}

	public String getPassenger() {
		return Passenger;
	}

	public void setPassenger(String passenger) {
		Passenger = passenger;
	}

	public String getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
