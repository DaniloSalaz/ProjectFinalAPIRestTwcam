package es.uv.twcam.projects.airporject.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.uv.twcam.projects.airporject.requestDTO.FlightRequestDTO;
import es.uv.twcam.projects.airporject.responseDTO.FlightAvailableDTO;
import es.uv.twcam.projects.airporject.responseDTO.FlightMapper;
import es.uv.twcam.projects.airporject.responseDTO.FlightPassengerDTO;
import es.uv.twcam.projects.airporject.responseDTO.FlightSeatsDTO;
import es.uv.twcam.projects.airporject.responseDTO.FlightStatusDTO;
import es.uv.twcam.projects.airproject.EntityException.AircraftNotFoundException;
import es.uv.twcam.projects.airproject.EntityException.AirlineNotFoundException;
import es.uv.twcam.projects.airproject.entity.Aircraft;
import es.uv.twcam.projects.airproject.entity.Airline;
import es.uv.twcam.projects.airproject.entity.Airport;
import es.uv.twcam.projects.airproject.entity.Flight;
import es.uv.twcam.projects.airproject.entity.Flight.Status;
import es.uv.twcam.projects.airproject.entity.Reservation;
import es.uv.twcam.projects.airproject.entity.Seat;
import es.uv.twcam.projects.airproject.repositoryDAO.DataDAOFactory;
import es.uv.twcam.projects.airproject.repositoryDAO.DataDAOFactory.TYPE;
import es.uv.twcam.projects.airproject.service.IAircraftDAO;
import es.uv.twcam.projects.airproject.service.IAirlineDAO;
import es.uv.twcam.projects.airproject.service.IAirportDAO;
import es.uv.twcam.projects.airproject.service.IFlightDAO;
import es.uv.twcam.projects.airproject.service.IReservationDAO;
import es.uv.twcam.projects.airproject.service.ISeatDAO;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

	private IFlightDAO flightService;
	private IAirlineDAO airlineService;
	private IAirportDAO airportService;
	private IAircraftDAO aircraftService;
	private ISeatDAO seatService;
	private IReservationDAO reservationService;
	
	public FlightController() {
		DataDAOFactory factory = DataDAOFactory.getDAOFactory(TYPE.JPA);
		flightService = factory.getFlightDAO();
		airlineService = factory.getAirlineDAO();
		airportService = factory.getAirportDAO();
		aircraftService = factory.getAircraftDAO();
		seatService = factory.getSeatDAO();
		reservationService = factory.getReservationDAO();
		
	}
	
	@GetMapping
	public ResponseEntity<List<FlightAvailableDTO>> getFligths(
			@RequestParam("des") @Validated String destination,
			@RequestParam("pasNo") int passagerN,
			@RequestParam("date") String dateFlight,
			@RequestParam(defaultValue = "false", name="return") String retuFlight) {
		
		//TODO manejador bad request 
		//MissingServletRequestParameterException cuando los parametros no son los adecuados
		//NumberFormatException conversion de numeros en la fecha
		
		String[] fechaVuelo = dateFlight.split("-");
		int year = Integer.parseInt(fechaVuelo[0]);
		int month = Integer.parseInt(fechaVuelo[1]);
		int day = Integer.parseInt(fechaVuelo[2]);
		
		
		
		List<Flight> listFligths = flightService.findFligthsByDate(year,month,day,"VLC",destination,passagerN);
		
		return new ResponseEntity<List<FlightAvailableDTO>>(FlightMapper.fromEntityListAvailable(listFligths),HttpStatus.OK);
	}
	
	@GetMapping("/limit")
	public ResponseEntity<List<FlightAvailableDTO>> getFligthsLimit(
			@RequestParam("date") String dateFlight) {
		
		String[] fechaVuelo = dateFlight.split("-");
		int year = Integer.parseInt(fechaVuelo[0]);
		int month = Integer.parseInt(fechaVuelo[1]);
		int day = Integer.parseInt(fechaVuelo[2]);
		
		
		
		List<Flight> listFlights = flightService.findFligthsByLTDateLimit(year, month, day, 3);
		List<Flight> listFlights2 = flightService.findFligthsByGTDateLimit(year, month, day, 3);
		Flight flight3 = flightService.findFligthByDate(year, month, day);
		listFlights.add(flight3);
		listFlights.addAll(listFlights2);
		
		return new ResponseEntity<List<FlightAvailableDTO>>(FlightMapper.fromEntityListAvailable(listFlights),HttpStatus.OK);
	}
	
	@GetMapping("/status")
	public ResponseEntity<List<FlightStatusDTO>> getFligthStatus() {
		
		
		List<Flight> listFlights = flightService.findFlightStatus();
		
		return new ResponseEntity<List<FlightStatusDTO>>(FlightMapper.fromEntityListStatus(listFlights),HttpStatus.OK);
	}
	
	@GetMapping("/{id}/passengers")
	public ResponseEntity<List<FlightPassengerDTO>> getFligthPassenger(@PathVariable("id") int idFlight) {
		
		Reservation reservation =  reservationService.findByFlight(idFlight);
		
		return new ResponseEntity<List<FlightPassengerDTO>>(FlightMapper.fromEnityToListFlightPassengerDTO(reservation),HttpStatus.OK);
	}
	
	@GetMapping("/{id}/seats-passenger")
	public ResponseEntity<List<FlightSeatsDTO>> getFligthSeat(@PathVariable("id") int idFlight) {
		
		Reservation reservation =  reservationService.findSeatsByFlight(idFlight);
		
		return new ResponseEntity<List<FlightSeatsDTO>>(FlightMapper.fromEnityToListFlightSeatsDTO(reservation),HttpStatus.OK);
	}
	
	@GetMapping("/{id}/status")
	public ResponseEntity<Flight> setStatusFligth(@PathVariable("id") int idFlight) {
		
		Flight flight = flightService.getFlight(idFlight);
		
		if(flight.getStatus().equals(Flight.Status.scheduled))
			flight.setStatus(Flight.Status.landed);
		else
			flight.setStatus(Flight.Status.scheduled);
			
		flightService.updateFlight(flight);
		
		return new ResponseEntity<Flight>(flight,HttpStatus.OK);
	}
	
	@GetMapping("/{id}/seats-available")
	public ResponseEntity<List<Seat>> getSeatsAvailabla(@PathVariable("id") int idFlight) {
		
		Flight flight = flightService.getFlight(idFlight);
		
		return new ResponseEntity<List<Seat>>(flight.getSeats(),HttpStatus.OK);
		
	}
	
	public ResponseEntity<Flight> setSattusFligth(@PathVariable("id") int idFlight) {
		
		Flight flight = flightService.getFlight(idFlight);
		
		if(flight.getStatus().equals(Flight.Status.scheduled))
			flight.setStatus(Flight.Status.landed);
		else
			flight.setStatus(Flight.Status.scheduled);
			
		flightService.updateFlight(flight);
		
		return new ResponseEntity<Flight>(flight,HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Flight> createFlight(@RequestBody @Validated FlightRequestDTO flightDTO) {
		
		Airport dest = airportService.findAirportByIATACode(flightDTO.getIataAirportDestination());
		Airport origin = airportService.findAirportByIATACode(flightDTO.getIataAirportOrigin());
		Airline airline = airlineService.findAirlineByIATACode(flightDTO.getIataAirline());
		Aircraft aircraft  = aircraftService.findAircraftByName(flightDTO.getCodeAircraft());
		
		LocalDate reservationDate = flightDTO.getReservationDate();
		LocalDate arrival = flightDTO.getArrival();
		
		int year = flightDTO.getYear();
		int month = flightDTO.getMonth();
		int day = flightDTO.getDay();
		float cost = flightDTO.getCost();
		
		String departureTime = flightDTO.getDepartureTime();
		String boardingTime = flightDTO.getBoardingTime();
		
		
		if(dest == null || origin == null)
			throw new AirlineNotFoundException();
		if(airline == null)
			throw new AirlineNotFoundException();
		if(aircraft == null)
			throw new AircraftNotFoundException();
		
		Flight newFlight = new Flight(reservationDate, year, month, day, departureTime, boardingTime,
				arrival, 0,50,cost,100f,100f,airline, dest, origin,aircraft, Status.scheduled); 
		flightService.createFlight(newFlight);
		insertarSeat(newFlight);
		
		return new ResponseEntity<Flight>(newFlight,HttpStatus.CREATED);
			
	}
	
	private void insertarSeat(Flight flight) {
		String codeSeat = "";
		for (int i = 0; i < 4; i++) {
			codeSeat = (i+1) + "A" ;
			seatService.createSeat(new Seat(codeSeat,flight,true));
			
			codeSeat = (i+1) + "B" ;
			seatService.createSeat(new Seat(codeSeat,flight,true));
			
			codeSeat = (i+1) + "C" ;
			seatService.createSeat(new Seat(codeSeat,flight,true));
			
			codeSeat = (i+1) + "D" ;
			seatService.createSeat(new Seat(codeSeat,flight,true));
			
			codeSeat = (i+1) + "E" ;
			seatService.createSeat(new Seat(codeSeat,flight,true));
		}
	}

}
