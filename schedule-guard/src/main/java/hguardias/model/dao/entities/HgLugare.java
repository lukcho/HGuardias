package hguardias.model.dao.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the hg_lugares database table.
 * 
 */
@Entity
@Table(name="hg_lugares")
@NamedQuery(name="HgLugare.findAll", query="SELECT h FROM HgLugare h")
public class HgLugare implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="HG_LUGARES_LUGID_GENERATOR", sequenceName="SEQ_HG_LUGARES", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HG_LUGARES_LUGID_GENERATOR")
	@Column(name="lug_id")
	private Integer lugId;

	@Column(name="lug_cctv")
	private Boolean lugCctv;

	@Column(name="lug_ciudad", length=50)
	private String lugCiudad;

	@Column(name="lug_control_accesos")
	private Boolean lugControlAccesos;

	@Column(name="lug_estado", columnDefinition="bpchar", length=1)
	private String lugEstado;

	@Column(name="lug_nombre", length=50)
	private String lugNombre;

	@Column(name="lug_nro_guardias")
	private Integer lugNroGuardias;

	//bi-directional many-to-one association to HgHorarioDet
	@OneToMany(mappedBy="hgLugare")
	private List<HgHorarioDet> hgHorarioDets;

	public HgLugare() {
	}

	public Integer getLugId() {
		return this.lugId;
	}

	public void setLugId(Integer lugId) {
		this.lugId = lugId;
	}

	public Boolean getLugCctv() {
		return this.lugCctv;
	}

	public void setLugCctv(Boolean lugCctv) {
		this.lugCctv = lugCctv;
	}

	public String getLugCiudad() {
		return this.lugCiudad;
	}

	public void setLugCiudad(String lugCiudad) {
		this.lugCiudad = lugCiudad;
	}

	public Boolean getLugControlAccesos() {
		return this.lugControlAccesos;
	}

	public void setLugControlAccesos(Boolean lugControlAccesos) {
		this.lugControlAccesos = lugControlAccesos;
	}

	public String getLugEstado() {
		return this.lugEstado;
	}

	public void setLugEstado(String lugEstado) {
		this.lugEstado = lugEstado;
	}

	public String getLugNombre() {
		return this.lugNombre;
	}

	public void setLugNombre(String lugNombre) {
		this.lugNombre = lugNombre;
	}

	public Integer getLugNroGuardias() {
		return this.lugNroGuardias;
	}

	public void setLugNroGuardias(Integer lugNroGuardias) {
		this.lugNroGuardias = lugNroGuardias;
	}

	public List<HgHorarioDet> getHgHorarioDets() {
		return this.hgHorarioDets;
	}

	public void setHgHorarioDets(List<HgHorarioDet> hgHorarioDets) {
		this.hgHorarioDets = hgHorarioDets;
	}

	public HgHorarioDet addHgHorarioDet(HgHorarioDet hgHorarioDet) {
		getHgHorarioDets().add(hgHorarioDet);
		hgHorarioDet.setHgLugare(this);

		return hgHorarioDet;
	}

	public HgHorarioDet removeHgHorarioDet(HgHorarioDet hgHorarioDet) {
		getHgHorarioDets().remove(hgHorarioDet);
		hgHorarioDet.setHgLugare(null);

		return hgHorarioDet;
	}

}