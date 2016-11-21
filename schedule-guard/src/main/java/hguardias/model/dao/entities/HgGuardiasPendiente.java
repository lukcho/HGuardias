package hguardias.model.dao.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the hg_guardias_pendiente database table.
 * 
 */
@Entity
@Table(name="hg_guardias_pendiente")
@NamedQuery(name="HgGuardiasPendiente.findAll", query="SELECT h FROM HgGuardiasPendiente h")
public class HgGuardiasPendiente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="guapen_id")
	private Integer guapenId;

	@Temporal(TemporalType.DATE)
	@Column(name="guapen_fecha")
	private Date guapenFecha;

	//bi-directional many-to-one association to HgGuardia
	@ManyToOne
	@JoinColumn(name="gua_cedula")
	private HgGuardia hgGuardia;

	public HgGuardiasPendiente() {
	}

	public Integer getGuapenId() {
		return this.guapenId;
	}

	public void setGuapenId(Integer guapenId) {
		this.guapenId = guapenId;
	}

	public Date getGuapenFecha() {
		return this.guapenFecha;
	}

	public void setGuapenFecha(Date guapenFecha) {
		this.guapenFecha = guapenFecha;
	}

	public HgGuardia getHgGuardia() {
		return this.hgGuardia;
	}

	public void setHgGuardia(HgGuardia hgGuardia) {
		this.hgGuardia = hgGuardia;
	}

}