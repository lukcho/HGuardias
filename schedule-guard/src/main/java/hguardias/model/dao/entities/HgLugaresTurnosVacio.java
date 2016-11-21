package hguardias.model.dao.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the hg_lugares_turnos_vacios database table.
 * 
 */
@Entity
@Table(name="hg_lugares_turnos_vacios")
@NamedQuery(name="HgLugaresTurnosVacio.findAll", query="SELECT h FROM HgLugaresTurnosVacio h")
public class HgLugaresTurnosVacio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="hglugtur_id")
	private Integer hglugturId;

	@Temporal(TemporalType.DATE)
	@Column(name="hglugtur_fecha_inicio")
	private Date hglugturFechaInicio;

	//bi-directional many-to-one association to HgHorarioCab
	@ManyToOne
	@JoinColumn(name="hcab_id")
	private HgHorarioCab hgHorarioCab;

	//bi-directional many-to-one association to HgLugare
	@ManyToOne
	@JoinColumn(name="lug_id")
	private HgLugare hgLugare;

	//bi-directional many-to-one association to HgTurno
	@ManyToOne
	@JoinColumn(name="tur_id")
	private HgTurno hgTurno;

	public HgLugaresTurnosVacio() {
	}

	public Integer getHglugturId() {
		return this.hglugturId;
	}

	public void setHglugturId(Integer hglugturId) {
		this.hglugturId = hglugturId;
	}

	public Date getHglugturFechaInicio() {
		return this.hglugturFechaInicio;
	}

	public void setHglugturFechaInicio(Date hglugturFechaInicio) {
		this.hglugturFechaInicio = hglugturFechaInicio;
	}

	public HgHorarioCab getHgHorarioCab() {
		return this.hgHorarioCab;
	}

	public void setHgHorarioCab(HgHorarioCab hgHorarioCab) {
		this.hgHorarioCab = hgHorarioCab;
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