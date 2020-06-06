package es.uv.twcam.projects.airporject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.uv.twcam.projects.airproject.Utils.UtilsData;

@RestController
@RequestMapping("/utility/cargar-datos")
public class CargarDatosController {
	
	@GetMapping
	public String generarDatos(@RequestParam("path") String path) {
		if(path == null || path.equals(""))
			path = "/Users/danilosalaz/eclipse-workspace/project-data-air/";
		
		if(UtilsData.cargarDatos(path))
			return "Datos Cargados";
		return "Error al cargar los datos";
	}

}
