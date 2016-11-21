package hguardias.model.dao.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the hg_tipo_ausencia database table.
 * 
 */
@Entity
@Table(name="hg_tipo_ausencia")
@NamedQuery(name="HgTipoAusencia.findAll", query="SELECT h FROM HgTipoAusencia h")
public class HgTipoAusencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tip_aus_id")
	private Integer tipAusId;

	@Column(name="tip_aus_descripcion", length=100)
	private String tipAusDescripcion;

	@Column(name="tip_aus_nombre", length=100)
	private String tipAusNombre;

	//bi-directional many-to-one association to HgAusencia
	@OneToMany(mappedBy="hgTipoAusencia")
	private List<HgAusencia> hgAusencias;

	public HgTipoAusencia() {
	}

	public Integer getTipAusId() {
		return this.tipAusId;
	}

	public void setTipAusId(Integer tipAusId) {
		this.tipAusId = tipAusId;
	}

	public String getTipAusDescripcion() {
		return this.tipAusDescripcion;
	}

	public void setTipAusDescripcion(String tipAusDescripcion) {
		this.tipAusDescripcion = tipAusDescripcion;
	}

	public String getTipAusNombre() {
		return this.tipAusNombre;
	}

	public void setTipAusNombre(String tipAusNombre) {
		this.tipAusNombre = tipAusNombre;
	}

	public List<HgAusencia> getHgAusencias() {
		return this.hgAusencias;
	}

	public void setHgAusencias(List<HgAusencia> hgAusencias) {
		this.hgAusencias = hgAusencias;
	}

	public HgAusencia addHgAusencia(HgAusencia hgAusencia) {
		getHgAusencias().add(hgAusencia);
		hgAusencia.setHgTipoAusencia(this);

		return hgAusencia;
	}

	public HgAusencia removeHgAusencia(HgAusencia hgAusencia) {
		getHgAusencias().remove(hgAusencia);
		hgAusencia.setHgTipoAusencia(null);

		return hgAusencia;
	}

}