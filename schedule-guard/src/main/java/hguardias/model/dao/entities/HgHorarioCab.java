package hguardias.model.dao.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the hg_horario_cab database table.
 * 
 */
@Entity
@Table(name="hg_horario_cab")
@NamedQuery(name="HgHorarioCab.findAll", query="SELECT h FROM HgHorarioCab h")
public class HgHorarioCab implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="hcab_id")
	private Integer hcabId;

	@Temporal(TemporalType.DATE)
	@Column(name="hcab_fecha_fin")
	private Date hcabFechaFin;

	@Temporal(TemporalType.DATE)
	@Column(name="hcab_fecha_inicio")
	private Date hcabFechaInicio;

	@Column(name="hcab_fecha_registro")
	private Timestamp hcabFechaRegistro;

	@Column(name="hcab_nombre", length=255)
	private String hcabNombre;

	@Column(name="hcab_numero_lugares_vacios")
	private Integer hcabNumeroLugaresVacios;

	@Column(name="hcab_numero_registros_creados")
	private Integer hcabNumeroRegistrosCreados;

	@Column(name="hcab_numero_registros_total")
	private Integer hcabNumeroRegistrosTotal;

	@Column(name="hcab_usuario", length=255)
	private String hcabUsuario;

	//bi-directional many-to-one association to HgHorarioDet
	@OneToMany(mappedBy="hgHorarioCab", cascade=CascadeType.ALL)
	private List<HgHorarioDet> hgHorarioDets;

	//bi-directional many-to-one association to HgLugaresTurnosVacio
	@OneToMany(mappedBy="hgHorarioCab")
	private List<HgLugaresTurnosVacio> hgLugaresTurnosVacios;

	//bi-directional many-to-one association to HgHistorialMovimiento
	@OneToMany(mappedBy="hgHorarioCab")
	private List<HgHistorialMovimiento> hgHistorialMovimientos;

	public HgHorarioCab() {
	}

	public Integer getHcabId() {
		return this.hcabId;
	}

	public void setHcabId(Integer hcabId) {
		this.hcabId = hcabId;
	}

	public Date getHcabFechaFin() {
		return this.hcabFechaFin;
	}

	public void setHcabFechaFin(Date hcabFechaFin) {
		this.hcabFechaFin = hcabFechaFin;
	}

	public Date getHcabFechaInicio() {
		return this.hcabFechaInicio;
	}

	public void setHcabFechaInicio(Date hcabFechaInicio) {
		this.hcabFechaInicio = hcabFechaInicio;
	}

	public Timestamp getHcabFechaRegistro() {
		return this.hcabFechaRegistro;
	}

	public void setHcabFechaRegistro(Timestamp hcabFechaRegistro) {
		this.hcabFechaRegistro = hcabFechaRegistro;
	}

	public String getHcabNombre() {
		return this.hcabNombre;
	}

	public void setHcabNombre(String hcabNombre) {
		this.hcabNombre = hcabNombre;
	}

	public Integer getHcabNumeroLugaresVacios() {
		return this.hcabNumeroLugaresVacios;
	}

	public void setHcabNumeroLugaresVacios(Integer hcabNumeroLugaresVacios) {
		this.hcabNumeroLugaresVacios = hcabNumeroLugaresVacios;
	}

	public Integer getHcabNumeroRegistrosCreados() {
		return this.hcabNumeroRegistrosCreados;
	}

	public void setHcabNumeroRegistrosCreados(Integer hcabNumeroRegistrosCreados) {
		this.hcabNumeroRegistrosCreados = hcabNumeroRegistrosCreados;
	}

	public Integer getHcabNumeroRegistrosTotal() {
		return this.hcabNumeroRegistrosTotal;
	}

	public void setHcabNumeroRegistrosTotal(Integer hcabNumeroRegistrosTotal) {
		this.hcabNumeroRegistrosTotal = hcabNumeroRegistrosTotal;
	}

	public String getHcabUsuario() {
		return this.hcabUsuario;
	}

	public void setHcabUsuario(String hcabUsuario) {
		this.hcabUsuario = hcabUsuario;
	}

	public List<HgHorarioDet> getHgHorarioDets() {
		return this.hgHorarioDets;
	}

	public void setHgHorarioDets(List<HgHorarioDet> hgHorarioDets) {
		this.hgHorarioDets = hgHorarioDets;
	}

	public HgHorarioDet addHgHorarioDet(HgHorarioDet hgHorarioDet) {
		getHgHorarioDets().add(hgHorarioDet);
		hgHorarioDet.setHgHorarioCab(this);

		return hgHorarioDet;
	}

	public HgHorarioDet removeHgHorarioDet(HgHorarioDet hgHorarioDet) {
		getHgHorarioDets().remove(hgHorarioDet);
		hgHorarioDet.setHgHorarioCab(null);

		return hgHorarioDet;
	}

	public List<HgLugaresTurnosVacio> getHgLugaresTurnosVacios() {
		return this.hgLugaresTurnosVacios;
	}

	public void setHgLugaresTurnosVacios(List<HgLugaresTurnosVacio> hgLugaresTurnosVacios) {
		this.hgLugaresTurnosVacios = hgLugaresTurnosVacios;
	}

	public HgLugaresTurnosVacio addHgLugaresTurnosVacio(HgLugaresTurnosVacio hgLugaresTurnosVacio) {
		getHgLugaresTurnosVacios().add(hgLugaresTurnosVacio);
		hgLugaresTurnosVacio.setHgHorarioCab(this);

		return hgLugaresTurnosVacio;
	}

	public HgLugaresTurnosVacio removeHgLugaresTurnosVacio(HgLugaresTurnosVacio hgLugaresTurnosVacio) {
		getHgLugaresTurnosVacios().remove(hgLugaresTurnosVacio);
		hgLugaresTurnosVacio.setHgHorarioCab(null);

		return hgLugaresTurnosVacio;
	}

	public List<HgHistorialMovimiento> getHgHistorialMovimientos() {
		return this.hgHistorialMovimientos;
	}

	public void setHgHistorialMovimientos(List<HgHistorialMovimiento> hgHistorialMovimientos) {
		this.hgHistorialMovimientos = hgHistorialMovimientos;
	}

	public HgHistorialMovimiento addHgHistorialMovimiento(HgHistorialMovimiento hgHistorialMovimiento) {
		getHgHistorialMovimientos().add(hgHistorialMovimiento);
		hgHistorialMovimiento.setHgHorarioCab(this);

		return hgHistorialMovimiento;
	}

	public HgHistorialMovimiento removeHgHistorialMovimiento(HgHistorialMovimiento hgHistorialMovimiento) {
		getHgHistorialMovimientos().remove(hgHistorialMovimiento);
		hgHistorialMovimiento.setHgHorarioCab(null);

		return hgHistorialMovimiento;
	}

}