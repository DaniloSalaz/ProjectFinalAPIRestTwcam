package es.uv.twcam.projects.airporject.requestDTO;

import java.io.Serializable;

public class PassengerRequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String dni;
	private String nombre;
	private String apellido;
	private boolean prioritario;
	private int equipaje;
	private String asiento;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public boolean isPrioritario() {
		return prioritario;
	}

	public void setPrioritario(boolean prioritario) {
		this.prioritario = prioritario;
	}

	public int getEquipaje() {
		return equipaje;
	}

	public void setEquipaje(int equipaje) {
		this.equipaje = equipaje;
	}

	public String getAsiento() {
		return asiento;
	}

	public void setAsiento(String asiento) {
		this.asiento = asiento;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

}
