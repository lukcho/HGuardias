package hguardias.model.dao.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
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

	@Column(name="hcab_usuario", length=255)
	private String hcabUsuario;

	//bi-directional many-to-one association to HgHorarioDet
	@OneToMany(mappedBy="hgHorarioCab")
	private List<HgHorarioDet> hgHorarioDets;

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

}