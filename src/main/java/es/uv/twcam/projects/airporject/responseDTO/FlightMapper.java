package es.uv.twcam.projects.airporject.responseDTO;

import java.util.ArrayList;
import java.util.List;

import es.uv.twcam.projects.airproject.entity.Flight;
import es.uv.twcam.projects.airproject.entity.PassegerReservation;
import es.uv.twcam.projects.airproject.entity.Reservation;

public class FlightMapper {

	public static FlightAvailableDTO fromEntitToFlightAvailable(Flight flight) {

		if (flight == null)
			return null;

		FlightAvailableDTO flightDto = new FlightAvailableDTO();
		String departureTime = flight.getDay() + "/" + flight.getMonth() + "/" + flight.getYear() + " "
				+ flight.getDepartureTime().substring(0, 1) + ":" + flight.getDepartureTime().substring(2);
		flightDto.setIdFlight(flight.getId());
		flightDto.setDepartureTime(departureTime);
		flightDto.setAirportFrom(flight.getOrigin().getIata_code());
		flightDto.setAirportTo(flight.getDestination().getIata_code());
		flightDto.setSeatAvailable(flight.getAvailableSeats());

		return flightDto;
	}

	public static List<FlightAvailableDTO> fromEntityListAvailable(List<Flight> listFlights) {

		if (listFlights == null)
			return null;

		List<FlightAvailableDTO> listFlightDto = new ArrayList<FlightAvailableDTO>();

		for (Flight flight : listFlights) {
			listFlightDto.add(fromEntitToFlightAvailable(flight));
		}

		return listFlightDto;
	}
	
	public static FlightStatusDTO fromEntityToFLightStatus(Flight flight) {
		if (flight == null)
			return null;

		FlightStatusDTO flightDto = new FlightStatusDTO();
		flightDto.setIdFlight(flight.getId());
		String departureTime = flight.getDay() + "/" + flight.getMonth() + "/" + flight.getYear() + " "
				+ flight.getDepartureTime().substring(0, 1) + ":" + flight.getDepartureTime().substring(2);
		flightDto.setDepartureTime(departureTime);
		String arrival =  flight.getArrival() != null ?flight.getArrival().toString(): ""; 
		flightDto.setArrivalTime(arrival);
		flightDto.setStatus(flight.getStatus().name());
		
		return flightDto;
	}
	
	public static List<FlightStatusDTO> fromEntityListStatus(List<Flight> listFlights) {

		if (listFlights == null)
			return null;

		List<FlightStatusDTO> listFlightDto = new ArrayList<FlightStatusDTO>();

		for (Flight flight : listFlights) {
			listFlightDto.add(fromEntityToFLightStatus(flight));
		}

		return listFlightDto;
	}
	
	public static List<FlightPassengerDTO> fromEnityToListFlightPassengerDTO(Reservation res) {
		if (res == null)
			return null;
		List<FlightPassengerDTO> listFlightsDto = new ArrayList<FlightPassengerDTO>();
		
		for(PassegerReservation ps: res.getPasseger()) {
			String passengerNames = ps.getPasseger().getName() + " " + ps.getPasseger().getLastName();
			String chekIn = ps.isCheckIn() ? "YES": "NO";
			
			listFlightsDto.add(new FlightPassengerDTO(res.getFlights().get(0).getFlight().getId(), passengerNames,chekIn, ps.getPasseger().getEmail()));
		}
		
		return listFlightsDto;
	}
	
	public static List<FlightSeatsDTO> fromEnityToListFlightSeatsDTO(Reservation res) {
		
		if (res == null)
			return null;
		
		List<FlightSeatsDTO> listFlightsDto = new ArrayList<FlightSeatsDTO>();
		
		for(PassegerReservation ps: res.getPasseger()) {
			
			String passengerNames = ps.getPasseger().getName() + " " + ps.getPasseger().getLastName();
			String seat = ps.getSeat();
			
			listFlightsDto.add(new FlightSeatsDTO(res.getFlights().get(0).getFlight().getId(), passengerNames, seat));
		}
		
		return listFlightsDto;
	}

}
