package es.uv.twcam.projects.airporject.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.uv.twcam.projects.airporject.Exceptions.SeatsNotAvailableException;
import es.uv.twcam.projects.airporject.requestDTO.PassengerRequestDTO;
import es.uv.twcam.projects.airporject.requestDTO.ReservationRequestDTO;
import es.uv.twcam.projects.airproject.EntityException.FlightNotFoundException;
import es.uv.twcam.projects.airproject.EntityException.PersonNotFoundException;
import es.uv.twcam.projects.airproject.entity.Flight;
import es.uv.twcam.projects.airproject.entity.FlightReservation;
import es.uv.twcam.projects.airproject.entity.PassegerReservation;
import es.uv.twcam.projects.airproject.entity.Payment;
import es.uv.twcam.projects.airproject.entity.Person;
import es.uv.twcam.projects.airproject.entity.Reservation;
import es.uv.twcam.projects.airproject.entity.Reservation.ReservationType;
import es.uv.twcam.projects.airproject.entity.Seat;
import es.uv.twcam.projects.airproject.repositoryDAO.DataDAOFactory;
import es.uv.twcam.projects.airproject.repositoryDAO.DataDAOFactory.TYPE;
import es.uv.twcam.projects.airproject.service.IFlightDAO;
import es.uv.twcam.projects.airproject.service.IPaymentDAO;
import es.uv.twcam.projects.airproject.service.IPersonDAO;
import es.uv.twcam.projects.airproject.service.IReservationDAO;
import es.uv.twcam.projects.airproject.service.ISeatDAO;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

	private IFlightDAO flightService;
	private IReservationDAO reservationService;
	private IPersonDAO personService;
	private ISeatDAO seatService;
	private IPaymentDAO paymentService;
	
	
	public ReservationController() {
		DataDAOFactory factory = DataDAOFactory.getDAOFactory(TYPE.JPA);
		flightService = factory.getFlightDAO();
		reservationService = factory.getReservationDAO();
		personService = factory.getPersonDAO();
		seatService = factory.getSeatDAO();
		paymentService = factory.getPaymentDAO();

	}

	@PostMapping
	public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequestDTO reservationDTO) {

		Flight flight = flightService.getFlight(reservationDTO.getIdFlight());

		int numberSeats = reservationDTO.getPassengers().size();
		int numberBaggage = 0;
		int numberPriority = 0;

		if (flight == null)
			throw new FlightNotFoundException();

		if (flight.getAvailableSeats() < numberSeats )
			throw new SeatsNotAvailableException("El número de asientos no esta disponible");
		
		if (!isAvailbaleSeat(flight.getSeats(), reservationDTO.getPassengers()) )
			throw new SeatsNotAvailableException("Los asientos seleccionados no estan disponibles");
		
		
		flight.setAvailableSeats(flight.getAvailableSeats() - numberSeats);
		
		Person customer = getOrCreateCustomer(reservationDTO.getPassengers(), reservationDTO.getDniCustomer());
		
		Reservation newReservation = new Reservation(ReservationType.one, numberBaggage, numberPriority, customer);
		reservationService.createReservation(newReservation);

		List<PassegerReservation> passengers = new ArrayList<PassegerReservation>();
		Person passenger;

		for (PassengerRequestDTO pas : reservationDTO.getPassengers()) {
			passenger = getOrCreatePassenger(pas);
			if (pas.getDni().equals(reservationDTO.getDniCustomer()))
				customer = Person.copyPerson(passenger);
			passengers.add(new PassegerReservation(passenger, newReservation, pas.isPrioritario(), false, pas.getAsiento()));

			if (pas.isPrioritario())
				numberPriority++;
			numberBaggage += pas.getEquipaje();
			
			reservationSeat(flight.getSeats(), pas.getAsiento());
			
//			passenReseService.createPassegerReservation(new PassegerReservation(passenger, newReservation, pas.isPrioritario(), false, pas.getAsiento()));

		}

		List<FlightReservation> flights = new ArrayList<FlightReservation>();
		
		flights.add(new FlightReservation(flight, newReservation, LocalDate.now()));
		
		newReservation.setBaggageNo(numberBaggage);
		newReservation.setPrioriryNo(numberPriority);
		newReservation.setFlights(flights);
		newReservation.setPasseger(passengers);
//		flightReseService.createFlightReservation(flights.get(0));

		reservationService.updateReservation(newReservation);
		
		return new ResponseEntity<Reservation>(newReservation, HttpStatus.CREATED);
	}

	
	private Person getOrCreatePassenger(PassengerRequestDTO passenger) {
		Person person;
		try {
			person = personService.findPersonByDNI(passenger.getDni());
			if(person == null)
				person = new Person(passenger.getDni(), passenger.getNombre(), passenger.getApellido());
		} catch (PersonNotFoundException e) {
			System.out.println(e.getMessage());
			person = new Person(passenger.getDni(), passenger.getNombre(), passenger.getApellido());
			personService.createPerson(person);
		}
		return person;

	}
	
	private Person getOrCreateCustomer(List<PassengerRequestDTO> listPassengerDTO, String dni) {
		Person person;
		PassengerRequestDTO passagerCustomer = null;
		try {
			for(PassengerRequestDTO p: listPassengerDTO) {
				if(p.getDni().equals(dni)) {
					passagerCustomer = p;
				}
			}
			person = personService.findPersonByDNI(passagerCustomer.getDni());
			if(person == null)
				person = new Person(passagerCustomer.getDni(), passagerCustomer.getNombre(), passagerCustomer.getApellido());
		} catch (PersonNotFoundException e) {
			System.out.println(e.getMessage());
			person = new Person(passagerCustomer.getDni(), passagerCustomer.getNombre(), passagerCustomer.getApellido());
			personService.createPerson(person);
		}
		return person;

	}

	private void reservationSeat(List<Seat> seats, String asiento) {
		for (Seat s : seats) {
			if (s.getCode().equals(asiento)) {
				s.setAvailable(false);
				seatService.updateSeat(s);
			}
		}
	}
	private boolean isAvailbaleSeat(List<Seat> seats, List<PassengerRequestDTO> listPassengerDTO) {
		int countSeat = 0;
		int codeRepetido = 0;
		for(PassengerRequestDTO p: listPassengerDTO ) {
			for (Seat s : seats) {
				if (s.getCode().equals(p.getAsiento()) && s.isAvailable() && !(s.getId() == codeRepetido)) {
					countSeat++;
					codeRepetido = s.getId();
				}
			}
		}
		
		if(countSeat != listPassengerDTO.size())
			throw new SeatsNotAvailableException("Asisentos: ocupado, invalidos o repetidos");
		
		return true;
	}
	@GetMapping("/{id}/pay")
	public ResponseEntity<Payment> generatePago(@PathVariable("id") int id) {
		
		Reservation reservation = reservationService.getReservation(id);
		
		float subtotalBaggage = reservation.getBaggageNo() * reservation.getFlights().get(0).getFlight().getBaggageCost();
		float subtotalPrioriry = reservation.getPrioriryNo() * reservation.getFlights().get(0).getFlight().getPriorityCost();
		float subtotalFlight = reservation.getPasseger().size() * reservation.getFlights().get(0).getFlight().getCost();
		float total = subtotalBaggage + subtotalPrioriry + subtotalFlight;
		Payment payment = new Payment(subtotalBaggage, subtotalPrioriry, subtotalFlight,total, reservation);
		
		paymentService.createPayment(payment);
		
		return new ResponseEntity<Payment>(payment,HttpStatus.CREATED);
	}

}
