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
	@SequenceGenerator(name="HG_TURNO_TURID_GENERATOR", sequenceName="SEQ_HG_TURNO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HG_TURNO_TURID_GENERATOR")
	@Column(name="tur_id")
	private Integer turId;

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

	public HgTurno() {
	}

	public Integer getTurId() {
		return this.turId;
	}

	public void setTurId(Integer turId) {
		this.turId = turId;
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

}