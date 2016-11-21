package hguardias.model.dao.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the hg_faltos database table.
 * 
 */
@Entity
@Table(name="hg_faltos")
@NamedQuery(name="HgFalto.findAll", query="SELECT h FROM HgFalto h")
public class HgFalto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="falto_id")
	private Integer faltoId;

	@Column(name="falto_descripcion", length=255)
	private String faltoDescripcion;

	@Column(name="falto_fecha_falta")
	private Timestamp faltoFechaFalta;

	//bi-directional many-to-one association to HgGuardia
	@ManyToOne
	@JoinColumn(name="gua_cedula")
	private HgGuardia hgGuardia;
	
	//bi-directional many-to-one association to HgHorarioDet
	@ManyToOne
	@JoinColumn(name="hdet_id")
	private HgHorarioDet hgHorarioDet;

	public HgFalto() {
	}

	public Integer getFaltoId() {
		return this.faltoId;
	}

	public void setFaltoId(Integer faltoId) {
		this.faltoId = faltoId;
	}

	public String getFaltoDescripcion() {
		return this.faltoDescripcion;
	}

	public void setFaltoDescripcion(String faltoDescripcion) {
		this.faltoDescripcion = faltoDescripcion;
	}

	public Timestamp getFaltoFechaFalta() {
		return this.faltoFechaFalta;
	}

	public void setFaltoFechaFalta(Timestamp faltoFechaFalta) {
		this.faltoFechaFalta = faltoFechaFalta;
	}
	
	public HgGuardia getHgGuardia() {
		return this.hgGuardia;
	}

	public void setHgGuardia(HgGuardia hgGuardia) {
		this.hgGuardia = hgGuardia;
	}

	public HgHorarioDet getHgHorarioDet() {
		return this.hgHorarioDet;
	}

	public void setHgHorarioDet(HgHorarioDet hgHorarioDet) {
		this.hgHorarioDet = hgHorarioDet;
	}

}