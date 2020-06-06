package es.uv.twcam.projects.airporject.responseDTO;

public class FlightStatusDTO {

	private int idFlight;
	private String departureTime;
	private String arrivalTime;
	private String Status;

	public int getIdFlight() {
		return idFlight;
	}

	public void setIdFlight(int idFlight) {
		this.idFlight = idFlight;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

}
