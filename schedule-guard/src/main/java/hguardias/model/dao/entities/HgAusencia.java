package hguardias.model.dao.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the hg_ausencias database table.
 * 
 */
@Entity
@Table(name="hg_ausencias")
@NamedQuery(name="HgAusencia.findAll", query="SELECT h FROM HgAusencia h")
public class HgAusencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="HG_AUSENCIAS_AUSID_GENERATOR", sequenceName="SEQ_HG_AUSENCIAS", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HG_AUSENCIAS_AUSID_GENERATOR")
	@Column(name="aus_id")
	private Integer ausId;

	@Column(name="aus_descripcion", length=255)
	private String ausDescripcion;

	@Temporal(TemporalType.DATE)
	@Column(name="aus_fecha_fin")
	private Date ausFechaFin;

	@Temporal(TemporalType.DATE)
	@Column(name="aus_fecha_inicio")
	private Date ausFechaInicio;

	//bi-directional many-to-one association to HgGuardia
	@ManyToOne
	@JoinColumn(name="gua_cedula")
	private HgGuardia hgGuardia;

	public HgAusencia() {
	}

	public Integer getAusId() {
		return this.ausId;
	}

	public void setAusId(Integer ausId) {
		this.ausId = ausId;
	}

	public String getAusDescripcion() {
		return this.ausDescripcion;
	}

	public void setAusDescripcion(String ausDescripcion) {
		this.ausDescripcion = ausDescripcion;
	}

	public Date getAusFechaFin() {
		return this.ausFechaFin;
	}

	public void setAusFechaFin(Date ausFechaFin) {
		this.ausFechaFin = ausFechaFin;
	}

	public Date getAusFechaInicio() {
		return this.ausFechaInicio;
	}

	public void setAusFechaInicio(Date ausFechaInicio) {
		this.ausFechaInicio = ausFechaInicio;
	}

	public HgGuardia getHgGuardia() {
		return this.hgGuardia;
	}

	public void setHgGuardia(HgGuardia hgGuardia) {
		this.hgGuardia = hgGuardia;
	}

}