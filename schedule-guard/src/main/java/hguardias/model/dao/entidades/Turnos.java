package hguardias.model.dao.entidades;

import java.sql.Time;
import java.util.List;


public class Turnos {

	private Integer turId;
	private String turDescripcion;
	private String turEstado;
	private Time turHoraFin;
	private Time turHoraInicio;
	private List<Guardias> Guardias;
	
	public Turnos(Integer turId, String turDescripcion, String turEstado,
	 Time turHoraFin, Time turHoraInicio, List<Guardias> Guardias) {
		
		 this.turId = turId; 
		 this.turDescripcion= turDescripcion;
		 this.turEstado= turEstado;
		 this.turHoraFin= turHoraFin;
		 this.turHoraInicio= turHoraInicio;
		 this.Guardias= Guardias;
	}

	public Integer getTurId() {
		return this.turId;
	}

	public void setTurId(Integer turId) {
		this.turId = turId;
	}

	public String getTurDescripcion() {
		return this.turDescripcion;
	}

	public void setTurDescripcion(String turDescripcion) {
		this.turDescripcion = turDescripcion;
	}

	public String getTurEstado() {
		return this.turEstado;
	}

	public void setTurEstado(String turEstado) {
		this.turEstado = turEstado;
	}

	public Time getTurHoraFin() {
		return this.turHoraFin;
	}

	public void setTurHoraFin(Time turHoraFin) {
		this.turHoraFin = turHoraFin;
	}

	public Time getTurHoraInicio() {
		return this.turHoraInicio;
	}

	public void setTurHoraInicio(Time turHoraInicio) {
		this.turHoraInicio = turHoraInicio;
	}
	
	public List<Guardias> getGuardias() {
		return Guardias;
	}
	
	public void setGuardias(List<Guardias> guardias) {
		Guardias = guardias;
	}
}