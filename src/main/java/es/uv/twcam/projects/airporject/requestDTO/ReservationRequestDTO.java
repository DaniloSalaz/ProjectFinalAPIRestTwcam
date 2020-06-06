package es.uv.twcam.projects.airporject.requestDTO;

import java.io.Serializable;
import java.util.List;

public class ReservationRequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7374700930135489733L;

	private List<PassengerRequestDTO> passengers;
	private String dniCustomer;
	private int idFlight;

	public List<PassengerRequestDTO> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<PassengerRequestDTO> passengers) {
		this.passengers = passengers;
	}

	public int getIdFlight() {
		return idFlight;
	}

	public void setIdFlight(int idFlight) {
		this.idFlight = idFlight;
	}

	public String getDniCustomer() {
		return dniCustomer;
	}

	public void setDniCustomer(String dniCustomer) {
		this.dniCustomer = dniCustomer;
	}

}
