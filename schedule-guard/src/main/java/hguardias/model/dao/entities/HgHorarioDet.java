package hguardias.model.dao.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Time;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the hg_horario_det database table.
 * 
 */
@Entity
@Table(name="hg_horario_det")
@NamedQuery(name="HgHorarioDet.findAll", query="SELECT h FROM HgHorarioDet h")
public class HgHorarioDet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="HG_HORARIO_DET_HDETID_GENERATOR", sequenceName="SEQ_HG_HORARIO_DET", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HG_HORARIO_DET_HDETID_GENERATOR")
	@Column(name="hdet_id")
	private Integer hdetId;

	@Column(name="hdet_estado", columnDefinition="bpchar", length=1)
	private String hdetEstado;

	@Temporal(TemporalType.DATE)
	@Column(name="hdet_fecha_fin")
	private Date hdetFechaFin;

	@Temporal(TemporalType.DATE)
	@Column(name="hdet_fecha_inicio")
	private Date hdetFechaInicio;

	@Column(name="hdet_hora_fin")
	private Time hdetHoraFin;

	@Column(name="hdet_hora_inicio")
	private Time hdetHoraInicio;

	//bi-directional many-to-one association to HgGuardia
	@ManyToOne
	@JoinColumn(name="gua_cedula")
	private HgGuardia hgGuardia;

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
	
	//bi-directional many-to-one association to HgFalto
	@OneToMany(mappedBy="hgHorarioDet")
	private List<HgFalto> hgFaltos;

	public HgHorarioDet() {
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

	public HgGuardia getHgGuardia() {
		return this.hgGuardia;
	}

	public void setHgGuardia(HgGuardia hgGuardia) {
		this.hgGuardia = hgGuardia;
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
	
	public List<HgFalto> getHgFaltos() {
		return this.hgFaltos;
	}

	public void setHgFaltos(List<HgFalto> hgFaltos) {
		this.hgFaltos = hgFaltos;
	}

	public HgFalto addHgFalto(HgFalto hgFalto) {
		getHgFaltos().add(hgFalto);
		hgFalto.setHgHorarioDet(this);

		return hgFalto;
	}

	public HgFalto removeHgFalto(HgFalto hgFalto) {
		getHgFaltos().remove(hgFalto);
		hgFalto.setHgHorarioDet(null);

		return hgFalto;
	}

}