package hguardias.model.dao.entidades;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


public class HorarioCab {

	private Integer hcabId;
	private Date hcabFechaFin;
	private Date hcabFechaInicio;
	private Timestamp hcabFechaRegistro;
	private String hcabNombre;
	private String hcabUsuario;
	private List<HorarioDet> HorarioDets;

	
	public HorarioCab( Integer hcabId, Date hcabFechaFin, Date hcabFechaInicio, Timestamp hcabFechaRegistro, String hcabNombre, String hcabUsuario, List<HorarioDet> HorarioDets) {
		this.hcabId = hcabId;
		this.hcabFechaFin= hcabFechaFin;
		this.hcabFechaInicio= hcabFechaInicio;
		this.hcabFechaRegistro= hcabFechaRegistro;
		this.hcabNombre =hcabNombre;
		this.hcabUsuario = hcabUsuario;
		this.HorarioDets = HorarioDets;
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

	public List<HorarioDet> getHorarioDets() {
		return this.HorarioDets;
	}

	public void setHorarioDets(List<HorarioDet> HorarioDets) {
		this.HorarioDets = HorarioDets;
	}

	public HorarioDet addHorarioDet(HorarioDet HorarioDet) {
		getHorarioDets().add(HorarioDet);
		HorarioDet.setHorarioCab(this);
		return HorarioDet;
	}

	public HorarioDet removeHgHorarioDet(HorarioDet HorarioDet) {
		getHorarioDets().remove(HorarioDet);
		HorarioDet.setHorarioCab(null);
		return HorarioDet;
	}

}