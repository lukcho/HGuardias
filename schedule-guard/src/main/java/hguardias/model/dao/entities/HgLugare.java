package hguardias.model.dao.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the hg_lugares database table.
 * 
 */
@Entity
@Table(name="hg_lugares")
@NamedQuery(name="HgLugare.findAll", query="SELECT h FROM HgLugare h")
public class HgLugare implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="lug_id")
	private Integer lugId;

	@Column(name="lug_cctv")
	private Boolean lugCctv;

	@Column(name="lug_ciudad", length=50)
	private String lugCiudad;

	@Column(name="lug_control_accesos")
	private Boolean lugControlAccesos;

	@Column(name="lug_domingo")
	private Boolean lugDomingo;

	@Column(name="lug_estado", columnDefinition="bpchar", length=1)
	private String lugEstado;

	@Column(name="lug_jueves")
	private Boolean lugJueves;

	@Column(name="lug_lunes")
	private Boolean lugLunes;

	@Column(name="lug_martes")
	private Boolean lugMartes;

	@Column(name="lug_miercoles")
	private Boolean lugMiercoles;

	@Column(name="lug_nombre", length=50)
	private String lugNombre;

	@Column(name="lug_nro_guardias")
	private Integer lugNroGuardias;

	@Column(name="lug_prioridad")
	private Integer lugPrioridad;

	@Column(name="lug_sabado")
	private Boolean lugSabado;

	@Column(name="lug_viernes")
	private Boolean lugViernes;

	//bi-directional many-to-one association to HgHorarioDet
	@OneToMany(mappedBy="hgLugare")
	private List<HgHorarioDet> hgHorarioDets;

	//bi-directional many-to-one association to HgLugarTurno
	@OneToMany(mappedBy="hgLugare")
	private List<HgLugarTurno> hgLugarTurnos;

	//bi-directional many-to-one association to HgLugaresTurnosVacio
	@OneToMany(mappedBy="hgLugare")
	private List<HgLugaresTurnosVacio> hgLugaresTurnosVacios;

	public HgLugare() {
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

	public Boolean getLugDomingo() {
		return this.lugDomingo;
	}

	public void setLugDomingo(Boolean lugDomingo) {
		this.lugDomingo = lugDomingo;
	}

	public String getLugEstado() {
		return this.lugEstado;
	}

	public void setLugEstado(String lugEstado) {
		this.lugEstado = lugEstado;
	}

	public Boolean getLugJueves() {
		return this.lugJueves;
	}

	public void setLugJueves(Boolean lugJueves) {
		this.lugJueves = lugJueves;
	}

	public Boolean getLugLunes() {
		return this.lugLunes;
	}

	public void setLugLunes(Boolean lugLunes) {
		this.lugLunes = lugLunes;
	}

	public Boolean getLugMartes() {
		return this.lugMartes;
	}

	public void setLugMartes(Boolean lugMartes) {
		this.lugMartes = lugMartes;
	}

	public Boolean getLugMiercoles() {
		return this.lugMiercoles;
	}

	public void setLugMiercoles(Boolean lugMiercoles) {
		this.lugMiercoles = lugMiercoles;
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

	public Integer getLugPrioridad() {
		return this.lugPrioridad;
	}

	public void setLugPrioridad(Integer lugPrioridad) {
		this.lugPrioridad = lugPrioridad;
	}

	public Boolean getLugSabado() {
		return this.lugSabado;
	}

	public void setLugSabado(Boolean lugSabado) {
		this.lugSabado = lugSabado;
	}

	public Boolean getLugViernes() {
		return this.lugViernes;
	}

	public void setLugViernes(Boolean lugViernes) {
		this.lugViernes = lugViernes;
	}

	public List<HgHorarioDet> getHgHorarioDets() {
		return this.hgHorarioDets;
	}

	public void setHgHorarioDets(List<HgHorarioDet> hgHorarioDets) {
		this.hgHorarioDets = hgHorarioDets;
	}

	public HgHorarioDet addHgHorarioDet(HgHorarioDet hgHorarioDet) {
		getHgHorarioDets().add(hgHorarioDet);
		hgHorarioDet.setHgLugare(this);

		return hgHorarioDet;
	}

	public HgHorarioDet removeHgHorarioDet(HgHorarioDet hgHorarioDet) {
		getHgHorarioDets().remove(hgHorarioDet);
		hgHorarioDet.setHgLugare(null);

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
		hgLugarTurno.setHgLugare(this);

		return hgLugarTurno;
	}

	public HgLugarTurno removeHgLugarTurno(HgLugarTurno hgLugarTurno) {
		getHgLugarTurnos().remove(hgLugarTurno);
		hgLugarTurno.setHgLugare(null);

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
		hgLugaresTurnosVacio.setHgLugare(this);

		return hgLugaresTurnosVacio;
	}

	public HgLugaresTurnosVacio removeHgLugaresTurnosVacio(HgLugaresTurnosVacio hgLugaresTurnosVacio) {
		getHgLugaresTurnosVacios().remove(hgLugaresTurnosVacio);
		hgLugaresTurnosVacio.setHgLugare(null);

		return hgLugaresTurnosVacio;
	}

}