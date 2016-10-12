package hguardias.model.dao.entidades;

import hguardias.model.dao.entities.HgLugare;
import hguardias.model.dao.entities.HgTurno;

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
	
	private HgLugare Lugares;

	private HgTurno Turnos;

	public HorarioDet(HgTurno turno, HgLugare lugar,Date hdetFechaInicio) {
		  this.Turnos  = turno;
		  this.Lugares = lugar;
		  this.hdetFechaInicio = hdetFechaInicio;
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
	
	public HgTurno getTurnos() {
		return Turnos;
	}
	
	public void setTurnos(HgTurno turnos) {
		Turnos = turnos;
	}
	
	public HgLugare getLugares() {
		return Lugares;
	}
	public void setLugares(HgLugare lugares) {
		Lugares = lugares;
	}

}