package hguardias.model.dao.entidades;

import java.util.List;

public class Lugares {

	private Integer lugId;
	private Boolean lugCctv;
	private String lugCiudad;
	private Boolean lugControlAccesos;
	private String lugEstado;
	private String lugNombre;
	private Integer lugNroGuardias;
	private List<Turnos> Turnos;

	public Lugares(Integer lugId, Boolean lugCctv, String lugCiudad,
	 Boolean lugControlAccesos, String lugEstado, String lugNombre,
	 Integer lugNroGuardias,List<Turnos> Turnos) {

		this.lugId = lugId;
		 this.lugCctv= lugCctv;
		 this.lugCiudad= lugCiudad;
		 this.lugControlAccesos= lugControlAccesos;
		 this.lugEstado= lugEstado;
		 this.lugNombre= lugNombre;
		 this.lugNroGuardias= lugNroGuardias;
		 this.Turnos = Turnos;
	}

	public Integer getLugId() {
		return this.lugId;
	}

	public void setLugId(Integer lugId) {
		this.lugId = lugId;
	}

	public Boolean getLugCctv() {
		return this.lugCctv;
	}

	public void setLugCctv(Boolean lugCctv) {
		this.lugCctv = lugCctv;
	}

	public String getLugCiudad() {
		return this.lugCiudad;
	}

	public void setLugCiudad(String lugCiudad) {
		this.lugCiudad = lugCiudad;
	}

	public Boolean getLugControlAccesos() {
		return this.lugControlAccesos;
	}

	public void setLugControlAccesos(Boolean lugControlAccesos) {
		this.lugControlAccesos = lugControlAccesos;
	}

	public String getLugEstado() {
		return this.lugEstado;
	}

	public void setLugEstado(String lugEstado) {
		this.lugEstado = lugEstado;
	}

	public String getLugNombre() {
		return this.lugNombre;
	}

	public void setLugNombre(String lugNombre) {
		this.lugNombre = lugNombre;
	}

	public Integer getLugNroGuardias() {
		return this.lugNroGuardias;
	}

	public void setLugNroGuardias(Integer lugNroGuardias) {
		this.lugNroGuardias = lugNroGuardias;
	}
	public List<Turnos> getTurnos() {
		return Turnos;
	}
	
	public void setTurnos(List<Turnos> turnos) {
		Turnos = turnos;
	}
}