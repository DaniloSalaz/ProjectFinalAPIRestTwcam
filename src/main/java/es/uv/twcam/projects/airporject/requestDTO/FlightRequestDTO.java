package es.uv.twcam.projects.airporject.requestDTO;

import java.io.Serializable;
import java.time.LocalDate;


public class FlightRequestDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalDate reservationDate;
	private int year;
	private int month;
	private int day;
	private float cost;
	private String departureTime;
	private String boardingTime;
	private LocalDate arrival; 
	private String arrivalTime;
	private String iataAirline;
	private String codeAircraft;
	private String iataAirportDestination;
	private String iataAirportOrigin;

	public LocalDate getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(LocalDate reservationDate) {
		this.reservationDate = reservationDate;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getBoardingTime() {
		return boardingTime;
	}

	public void setBoardingTime(String boardingTime) {
		this.boardingTime = boardingTime;
	}

	public String getIataAirline() {
		return iataAirline;
	}

	public void setIataAirline(String iataAirline) {
		this.iataAirline = iataAirline;
	}

	public String getCodeAircraft() {
		return codeAircraft;
	}

	public void setCodeAircraft(String codeAircraft) {
		this.codeAircraft = codeAircraft;
	}

	public String getIataAirportDestination() {
		return iataAirportDestination;
	}

	public void setIataAirportDestination(String iataAirportDestination) {
		this.iataAirportDestination = iataAirportDestination;
	}

	public String getIataAirportOrigin() {
		return iataAirportOrigin;
	}

	public void setIataAirportOrigin(String iataAirportOrigin) {
		this.iataAirportOrigin = iataAirportOrigin;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public LocalDate getArrival() {
		return arrival;
	}

	public void setArrival(LocalDate arrival) {
		this.arrival = arrival;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

}
