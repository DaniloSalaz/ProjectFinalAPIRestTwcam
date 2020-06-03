package es.uv.twcam.projects.airporject.responseDTO;

import java.util.ArrayList;
import java.util.List;

import es.uv.twcam.projects.airproject.entity.Flight;

public class FlightAvailableMapper {
	
	public static FlightAvailableDTO fromEntity(Flight flight) {
		
		if(flight == null)
			return null;
		
		FlightAvailableDTO flightDto = new FlightAvailableDTO();
		String departureTime = flight.getDay() + "/" + flight.getMonth() + "/"  + flight.getYear() + " " + 
					flight.getDepartureTime().substring(0, 1) + ":" + flight.getDepartureTime().substring(2);
		flightDto.setIdFlight(flight.getId());
		flightDto.setDepartureTime(departureTime);
		flightDto.setAirportFrom(flight.getOrigin().getIata_code());
		flightDto.setAirportTo(flight.getDestination().getIata_code());
		flightDto.setSeatAvailable(flight.getAvailableSeats());
		
		return flightDto;
	}
	
	public static List<FlightAvailableDTO> fromEntityList(List<Flight> listFlights) {
			
			if(listFlights == null)
				return null;
			
			List<FlightAvailableDTO> listFlightDto = new ArrayList<FlightAvailableDTO>();
			
			for(Flight flight: listFlights) {
				listFlightDto.add(fromEntity(flight));
			}
			
			return listFlightDto;
		}
}
