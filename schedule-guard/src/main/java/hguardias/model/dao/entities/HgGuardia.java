package hguardias.model.dao.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the hg_guardias database table.
 * 
 */
@Entity
@Table(name="hg_guardias")
@NamedQuery(name="HgGuardia.findAll", query="SELECT h FROM HgGuardia h")
public class HgGuardia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="gua_cedula", length=100)
	private String guaCedula;

	@Column(name="gua_apellido", length=100)
	private String guaApellido;

	@Column(name="gua_caso_estudio")
	private Boolean guaCasoEstudio;

	@Column(name="gua_caso_nocturno")
	private Boolean guaCasoNocturno;

	@Column(name="gua_caso_turno")
	private Integer guaCasoTurno;

	@Column(name="gua_cctv")
	private Boolean guaCctv;

	@Column(name="gua_celular", length=10)
	private String guaCelular;

	@Column(name="gua_chofer")
	private Boolean guaChofer;

	@Column(name="gua_ciudad", length=100)
	private String guaCiudad;

	@Column(name="gua_control_accesos")
	private Boolean guaControlAccesos;

	@Column(name="gua_correo", length=255)
	private String guaCorreo;

	@Column(name="gua_direccion", length=255)
	private String guaDireccion;

	@Column(name="gua_estado", columnDefinition="bpchar", length=1)
	private String guaEstado;

	@Temporal(TemporalType.DATE)
	@Column(name="gua_fechanac")
	private Date guaFechanac;

	@Column(name="gua_motorizado")
	private Boolean guaMotorizado;

	@Column(name="gua_nombre", length=100)
	private String guaNombre;

	@Column(name="gua_sexo", columnDefinition="bpchar", length=1)
	private String guaSexo;

	@Column(name="gua_telefono", length=100)
	private String guaTelefono;

	//bi-directional many-to-one association to HgAusencia
	@OneToMany(mappedBy="hgGuardia")
	private List<HgAusencia> hgAusencias;

	//bi-directional many-to-one association to HgHorarioDet
	@OneToMany(mappedBy="hgGuardia")
	private List<HgHorarioDet> hgHorarioDets;

	public HgGuardia() {
	}

	public String getGuaCedula() {
		return this.guaCedula;
	}

	public void setGuaCedula(String guaCedula) {
		this.guaCedula = guaCedula;
	}

	public String getGuaApellido() {
		return this.guaApellido;
	}

	public void setGuaApellido(String guaApellido) {
		this.guaApellido = guaApellido;
	}

	public Boolean getGuaCasoEstudio() {
		return this.guaCasoEstudio;
	}

	public void setGuaCasoEstudio(Boolean guaCasoEstudio) {
		this.guaCasoEstudio = guaCasoEstudio;
	}

	public Boolean getGuaCasoNocturno() {
		return this.guaCasoNocturno;
	}

	public void setGuaCasoNocturno(Boolean guaCasoNocturno) {
		this.guaCasoNocturno = guaCasoNocturno;
	}

	public Integer getGuaCasoTurno() {
		return this.guaCasoTurno;
	}

	public void setGuaCasoTurno(Integer guaCasoTurno) {
		this.guaCasoTurno = guaCasoTurno;
	}

	public Boolean getGuaCctv() {
		return this.guaCctv;
	}

	public void setGuaCctv(Boolean guaCctv) {
		this.guaCctv = guaCctv;
	}

	public String getGuaCelular() {
		return this.guaCelular;
	}

	public void setGuaCelular(String guaCelular) {
		this.guaCelular = guaCelular;
	}

	public Boolean getGuaChofer() {
		return this.guaChofer;
	}

	public void setGuaChofer(Boolean guaChofer) {
		this.guaChofer = guaChofer;
	}

	public String getGuaCiudad() {
		return this.guaCiudad;
	}

	public void setGuaCiudad(String guaCiudad) {
		this.guaCiudad = guaCiudad;
	}

	public Boolean getGuaControlAccesos() {
		return this.guaControlAccesos;
	}

	public void setGuaControlAccesos(Boolean guaControlAccesos) {
		this.guaControlAccesos = guaControlAccesos;
	}

	public String getGuaCorreo() {
		return this.guaCorreo;
	}

	public void setGuaCorreo(String guaCorreo) {
		this.guaCorreo = guaCorreo;
	}

	public String getGuaDireccion() {
		return this.guaDireccion;
	}

	public void setGuaDireccion(String guaDireccion) {
		this.guaDireccion = guaDireccion;
	}

	public String getGuaEstado() {
		return this.guaEstado;
	}

	public void setGuaEstado(String guaEstado) {
		this.guaEstado = guaEstado;
	}

	public Date getGuaFechanac() {
		return this.guaFechanac;
	}

	public void setGuaFechanac(Date guaFechanac) {
		this.guaFechanac = guaFechanac;
	}

	public Boolean getGuaMotorizado() {
		return this.guaMotorizado;
	}

	public void setGuaMotorizado(Boolean guaMotorizado) {
		this.guaMotorizado = guaMotorizado;
	}

	public String getGuaNombre() {
		return this.guaNombre;
	}

	public void setGuaNombre(String guaNombre) {
		this.guaNombre = guaNombre;
	}

	public String getGuaSexo() {
		return this.guaSexo;
	}

	public void setGuaSexo(String guaSexo) {
		this.guaSexo = guaSexo;
	}

	public String getGuaTelefono() {
		return this.guaTelefono;
	}

	public void setGuaTelefono(String guaTelefono) {
		this.guaTelefono = guaTelefono;
	}

	public List<HgAusencia> getHgAusencias() {
		return this.hgAusencias;
	}

	public void setHgAusencias(List<HgAusencia> hgAusencias) {
		this.hgAusencias = hgAusencias;
	}

	public HgAusencia addHgAusencia(HgAusencia hgAusencia) {
		getHgAusencias().add(hgAusencia);
		hgAusencia.setHgGuardia(this);

		return hgAusencia;
	}

	public HgAusencia removeHgAusencia(HgAusencia hgAusencia) {
		getHgAusencias().remove(hgAusencia);
		hgAusencia.setHgGuardia(null);

		return hgAusencia;
	}

	public List<HgHorarioDet> getHgHorarioDets() {
		return this.hgHorarioDets;
	}

	public void setHgHorarioDets(List<HgHorarioDet> hgHorarioDets) {
		this.hgHorarioDets = hgHorarioDets;
	}

	public HgHorarioDet addHgHorarioDet(HgHorarioDet hgHorarioDet) {
		getHgHorarioDets().add(hgHorarioDet);
		hgHorarioDet.setHgGuardia(this);

		return hgHorarioDet;
	}

	public HgHorarioDet removeHgHorarioDet(HgHorarioDet hgHorarioDet) {
		getHgHorarioDets().remove(hgHorarioDet);
		hgHorarioDet.setHgGuardia(null);

		return hgHorarioDet;
	}

}