package es.uv.twcam.projects.airporject.controller;


import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.uv.twcam.projects.airporject.responseDTO.FlightAvailableDTO;
import es.uv.twcam.projects.airporject.responseDTO.FlightAvailableMapper;
import es.uv.twcam.projects.airproject.entity.Flight;
import es.uv.twcam.projects.airproject.repositoryDAO.DataDAOFactory;
import es.uv.twcam.projects.airproject.repositoryDAO.DataDAOFactory.TYPE;
import es.uv.twcam.projects.airproject.service.IFlightDAO;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

	private IFlightDAO flightService;
	
	public FlightController() {
		DataDAOFactory factory = DataDAOFactory.getDAOFactory(TYPE.JPA);
		flightService = factory.getFlightDAO();
	}
	
	@GetMapping
	public List<FlightAvailableDTO> getFligths(
			@RequestParam("des") @Validated String destination,
			@RequestParam("pasNo") int passagerN,
			@RequestParam("date") String dateFlight,
			@RequestParam(defaultValue = "false", name="return") String retuFlight) {
		
		//TODO manejador bad request 
		//MissingServletRequestParameterException cuando los parametros no son los adecuados
		//NumberFormatException conversion de numeros en la fecha
		
		String[] fechaVuelo = dateFlight.split("-");
		int year = Integer.parseInt(fechaVuelo[2]);
		int month = Integer.parseInt(fechaVuelo[1]);
		int day = Integer.parseInt(fechaVuelo[0]);
		
		
		
		List<Flight> listFligths = flightService.findFligthsByDate(year,month,day,"VLC",destination,passagerN);
		return FlightAvailableMapper.fromEntityList(listFligths);
	}

}
