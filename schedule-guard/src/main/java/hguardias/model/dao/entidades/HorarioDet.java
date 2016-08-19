package hguardias.model.dao.entidades;

import java.sql.Time;
import java.util.Date;


/**
 * The persistent class for the hg_horario_det database table.
 * 
 */
public class HorarioDet {

	private Integer hdetId;
	private String hdetEstado;
	private Date hdetFechaFin;
	private Date hdetFechaInicio;
	private Time hdetHoraFin;
	private Time hdetHoraInicio;
	private Guardias Guardias;
	private Dias dia;
	private Semanas semana;

	private HorarioCab HorarioCab;
	
	private Lugares Lugares;

	private Turnos Turnos;

	public HorarioDet(Guardias Guardias, Turnos turnos, Lugares lugares,Dias dia, Semanas semana) {
		  this.Guardias= Guardias;
		  this.Turnos  = turnos;
		  this.Lugares = lugares;
		  this.dia = dia;
		  this.semana = semana;
		
	}

	public Integer getHdetId() {
		return this.hdetId;
	}

	public void setHdetId(Integer hdetId) {
		this.hdetId = hdetId;
	}

	public String getHdetEstado() {
		return this.hdetEstado;
	}

	public void setHdetEstado(String hdetEstado) {
		this.hdetEstado = hdetEstado;
	}

	public Date getHdetFechaFin() {
		return this.hdetFechaFin;
	}

	public void setHdetFechaFin(Date hdetFechaFin) {
		this.hdetFechaFin = hdetFechaFin;
	}

	public Date getHdetFechaInicio() {
		return this.hdetFechaInicio;
	}

	public void setHdetFechaInicio(Date hdetFechaInicio) {
		this.hdetFechaInicio = hdetFechaInicio;
	}

	public Time getHdetHoraFin() {
		return this.hdetHoraFin;
	}

	public void setHdetHoraFin(Time hdetHoraFin) {
		this.hdetHoraFin = hdetHoraFin;
	}

	public Time getHdetHoraInicio() {
		return this.hdetHoraInicio;
	}

	public void setHdetHoraInicio(Time hdetHoraInicio) {
		this.hdetHoraInicio = hdetHoraInicio;
	}

	public Guardias getGuardias() {
		return Guardias;
	}
	
	public void setGuardias(Guardias guardias) {
		Guardias = guardias;
	}
	
	public HorarioCab getHorarioCab() {
		return this.HorarioCab;
	}

	public void setHorarioCab(HorarioCab HorarioCab) {
		this.HorarioCab = HorarioCab;
	}

	public Lugares getLugares() {
		return this.Lugares;
	}

	public void setLugares(Lugares Lugares) {
		this.Lugares = Lugares;
	}

	public Turnos getTurnos() {
		return this.Turnos;
	}

	public void setHgTurno(Turnos Turnos) {
		this.Turnos = Turnos;
	}
	
	public Dias getDia() {
		return dia;
	}
	
	public void setDia(Dias dia) {
		this.dia = dia;
	}
	
	public Semanas getSemana() {
		return semana;
	}
	
	public void setSemana(Semanas semana) {
		this.semana = semana;
	}

}