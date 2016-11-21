package hguardias.model.dao.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the hg_historial_movimientos database table.
 * 
 */
@Entity
@Table(name="hg_historial_movimientos")
@NamedQuery(name="HgHistorialMovimiento.findAll", query="SELECT h FROM HgHistorialMovimiento h")
public class HgHistorialMovimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="histo_id")
	private Integer histoId;

	@Column(name="gua_cedula_desde", length=100)
	private String guaCedulaDesde;

	@Column(name="gua_cedula_hacia", length=100)
	private String guaCedulaHacia;

	@Temporal(TemporalType.DATE)
	@Column(name="hdet_fecha")
	private Date hdetFecha;

	@Column(name="histo_fecha_movimiento")
	private Timestamp histoFechaMovimiento;

	@Column(name="lug_id_desde")
	private Integer lugIdDesde;

	@Column(name="lug_id_hacia")
	private Integer lugIdHacia;

	//bi-directional many-to-one association to HgHorarioCab
	@ManyToOne
	@JoinColumn(name="hcab_id")
	private HgHorarioCab hgHorarioCab;

	public HgHistorialMovimiento() {
	}

	public Integer getHistoId() {
		return this.histoId;
	}

	public void setHistoId(Integer histoId) {
		this.histoId = histoId;
	}

	public String getGuaCedulaDesde() {
		return this.guaCedulaDesde;
	}

	public void setGuaCedulaDesde(String guaCedulaDesde) {
		this.guaCedulaDesde = guaCedulaDesde;
	}

	public String getGuaCedulaHacia() {
		return this.guaCedulaHacia;
	}

	public void setGuaCedulaHacia(String guaCedulaHacia) {
		this.guaCedulaHacia = guaCedulaHacia;
	}

	public Date getHdetFecha() {
		return this.hdetFecha;
	}

	public void setHdetFecha(Date hdetFecha) {
		this.hdetFecha = hdetFecha;
	}

	public Timestamp getHistoFechaMovimiento() {
		return this.histoFechaMovimiento;
	}

	public void setHistoFechaMovimiento(Timestamp histoFechaMovimiento) {
		this.histoFechaMovimiento = histoFechaMovimiento;
	}

	public Integer getLugIdDesde() {
		return this.lugIdDesde;
	}

	public void setLugIdDesde(Integer lugIdDesde) {
		this.lugIdDesde = lugIdDesde;
	}

	public Integer getLugIdHacia() {
		return this.lugIdHacia;
	}

	public void setLugIdHacia(Integer lugIdHacia) {
		this.lugIdHacia = lugIdHacia;
	}

	public HgHorarioCab getHgHorarioCab() {
		return this.hgHorarioCab;
	}

	public void setHgHorarioCab(HgHorarioCab hgHorarioCab) {
		this.hgHorarioCab = hgHorarioCab;
	}

}