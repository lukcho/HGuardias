package hguardias.model.dao.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the hg_lugar_turno database table.
 * 
 */
@Entity
@Table(name="hg_lugar_turno")
@NamedQuery(name="HgLugarTurno.findAll", query="SELECT h FROM HgLugarTurno h")
public class HgLugarTurno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="HG_LUGAR_TURNO_LUGTUR_GENERATOR", sequenceName="SEQ_HG_LUG_TUR", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HG_LUGAR_TURNO_LUGTUR_GENERATOR")
	@Column(name="lug_tur")
	private Integer lugTur;

	@Column(name="lug_tur_numero_guardias")
	private Integer lugTurNumeroGuardias;

	//bi-directional many-to-one association to HgLugare
	@ManyToOne
	@JoinColumn(name="lug_id")
	private HgLugare hgLugare;

	//bi-directional many-to-one association to HgTurno
	@ManyToOne
	@JoinColumn(name="tur_id")
	private HgTurno hgTurno;

	public HgLugarTurno() {
	}

	public Integer getLugTur() {
		return this.lugTur;
	}

	public void setLugTur(Integer lugTur) {
		this.lugTur = lugTur;
	}

	public Integer getLugTurNumeroGuardias() {
		return this.lugTurNumeroGuardias;
	}

	public void setLugTurNumeroGuardias(Integer lugTurNumeroGuardias) {
		this.lugTurNumeroGuardias = lugTurNumeroGuardias;
	}

	public HgLugare getHgLugare() {
		return this.hgLugare;
	}

	public void setHgLugare(HgLugare hgLugare) {
		this.hgLugare = hgLugare;
	}

	public HgTurno getHgTurno() {
		return this.hgTurno;
	}

	public void setHgTurno(HgTurno hgTurno) {
		this.hgTurno = hgTurno;
	}

}