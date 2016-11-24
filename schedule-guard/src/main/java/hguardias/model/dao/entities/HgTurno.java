package hguardias.model.dao.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.List;


/**
 * The persistent class for the hg_turno database table.
 * 
 */
@Entity
@Table(name="hg_turno")
@NamedQuery(name="HgTurno.findAll", query="SELECT h FROM HgTurno h")
public class HgTurno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tur_id")
	private Integer turId;
	
	@Column(name="tur_codigo_color")
	private String turCodigoColor;

	@Column(name="tur_descripcion", length=50)
	private String turDescripcion;

	@Column(name="tur_estado", columnDefinition="bpchar", length=1)
	private String turEstado;

	@Column(name="tur_hora_fin")
	private Time turHoraFin;

	@Column(name="tur_hora_inicio")
	private Time turHoraInicio;

	//bi-directional many-to-one association to HgHorarioDet
	@OneToMany(mappedBy="hgTurno")
	private List<HgHorarioDet> hgHorarioDets;

	//bi-directional many-to-one association to HgLugarTurno
	@OneToMany(mappedBy="hgTurno")
	private List<HgLugarTurno> hgLugarTurnos;

	//bi-directional many-to-one association to HgLugaresTurnosVacio
	@OneToMany(mappedBy="hgTurno")
	private List<HgLugaresTurnosVacio> hgLugaresTurnosVacios;

	public HgTurno() {
	}

	public Integer getTurId() {
		return this.turId;
	}

	public void setTurId(Integer turId) {
		this.turId = turId;
	}
	
	public String getTurCodigoColor() {
		return this.turCodigoColor;
	}

	public void setTurCodigoColor(String turCodigoColor) {
		this.turCodigoColor = turCodigoColor;
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

	public List<HgHorarioDet> getHgHorarioDets() {
		return this.hgHorarioDets;
	}

	public void setHgHorarioDets(List<HgHorarioDet> hgHorarioDets) {
		this.hgHorarioDets = hgHorarioDets;
	}

	public HgHorarioDet addHgHorarioDet(HgHorarioDet hgHorarioDet) {
		getHgHorarioDets().add(hgHorarioDet);
		hgHorarioDet.setHgTurno(this);

		return hgHorarioDet;
	}

	public HgHorarioDet removeHgHorarioDet(HgHorarioDet hgHorarioDet) {
		getHgHorarioDets().remove(hgHorarioDet);
		hgHorarioDet.setHgTurno(null);

		return hgHorarioDet;
	}

	public List<HgLugarTurno> getHgLugarTurnos() {
		return this.hgLugarTurnos;
	}

	public void setHgLugarTurnos(List<HgLugarTurno> hgLugarTurnos) {
		this.hgLugarTurnos = hgLugarTurnos;
	}

	public HgLugarTurno addHgLugarTurno(HgLugarTurno hgLugarTurno) {
		getHgLugarTurnos().add(hgLugarTurno);
		hgLugarTurno.setHgTurno(this);

		return hgLugarTurno;
	}

	public HgLugarTurno removeHgLugarTurno(HgLugarTurno hgLugarTurno) {
		getHgLugarTurnos().remove(hgLugarTurno);
		hgLugarTurno.setHgTurno(null);

		return hgLugarTurno;
	}

	public List<HgLugaresTurnosVacio> getHgLugaresTurnosVacios() {
		return this.hgLugaresTurnosVacios;
	}

	public void setHgLugaresTurnosVacios(List<HgLugaresTurnosVacio> hgLugaresTurnosVacios) {
		this.hgLugaresTurnosVacios = hgLugaresTurnosVacios;
	}

	public HgLugaresTurnosVacio addHgLugaresTurnosVacio(HgLugaresTurnosVacio hgLugaresTurnosVacio) {
		getHgLugaresTurnosVacios().add(hgLugaresTurnosVacio);
		hgLugaresTurnosVacio.setHgTurno(this);

		return hgLugaresTurnosVacio;
	}

	public HgLugaresTurnosVacio removeHgLugaresTurnosVacio(HgLugaresTurnosVacio hgLugaresTurnosVacio) {
		getHgLugaresTurnosVacios().remove(hgLugaresTurnosVacio);
		hgLugaresTurnosVacio.setHgTurno(null);

		return hgLugaresTurnosVacio;
	}

}