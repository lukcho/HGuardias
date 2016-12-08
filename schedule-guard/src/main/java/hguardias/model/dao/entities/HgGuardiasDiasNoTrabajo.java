package hguardias.model.dao.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the hg_guardias_dias_no_trabajo database table.
 * 
 */
@Entity
@Table(name="hg_guardias_dias_no_trabajo")
@NamedQuery(name="HgGuardiasDiasNoTrabajo.findAll", query="SELECT h FROM HgGuardiasDiasNoTrabajo h")
public class HgGuardiasDiasNoTrabajo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="gua_dia_trabajo_id")
	private Integer guaDiaTrabajoId;

	@Column(name="gua_dia_trabajo_domingo")
	private Boolean guaDiaTrabajoDomingo;

	@Column(name="gua_dia_trabajo_jueves")
	private Boolean guaDiaTrabajoJueves;

	@Column(name="gua_dia_trabajo_lunes")
	private Boolean guaDiaTrabajoLunes;

	@Column(name="gua_dia_trabajo_martes")
	private Boolean guaDiaTrabajoMartes;

	@Column(name="gua_dia_trabajo_miercoles")
	private Boolean guaDiaTrabajoMiercoles;

	@Column(name="gua_dia_trabajo_sabado")
	private Boolean guaDiaTrabajoSabado;

	@Column(name="gua_dia_trabajo_viernes")
	private Boolean guaDiaTrabajoViernes;

	//bi-directional many-to-one association to HgGuardia
	@ManyToOne
	@JoinColumn(name="gua_cedula")
	private HgGuardia hgGuardia;

	public HgGuardiasDiasNoTrabajo() {
	}

	public Integer getGuaDiaTrabajoId() {
		return this.guaDiaTrabajoId;
	}

	public void setGuaDiaTrabajoId(Integer guaDiaTrabajoId) {
		this.guaDiaTrabajoId = guaDiaTrabajoId;
	}

	public Boolean getGuaDiaTrabajoDomingo() {
		return this.guaDiaTrabajoDomingo;
	}

	public void setGuaDiaTrabajoDomingo(Boolean guaDiaTrabajoDomingo) {
		this.guaDiaTrabajoDomingo = guaDiaTrabajoDomingo;
	}

	public Boolean getGuaDiaTrabajoJueves() {
		return this.guaDiaTrabajoJueves;
	}

	public void setGuaDiaTrabajoJueves(Boolean guaDiaTrabajoJueves) {
		this.guaDiaTrabajoJueves = guaDiaTrabajoJueves;
	}

	public Boolean getGuaDiaTrabajoLunes() {
		return this.guaDiaTrabajoLunes;
	}

	public void setGuaDiaTrabajoLunes(Boolean guaDiaTrabajoLunes) {
		this.guaDiaTrabajoLunes = guaDiaTrabajoLunes;
	}

	public Boolean getGuaDiaTrabajoMartes() {
		return this.guaDiaTrabajoMartes;
	}

	public void setGuaDiaTrabajoMartes(Boolean guaDiaTrabajoMartes) {
		this.guaDiaTrabajoMartes = guaDiaTrabajoMartes;
	}

	public Boolean getGuaDiaTrabajoMiercoles() {
		return this.guaDiaTrabajoMiercoles;
	}

	public void setGuaDiaTrabajoMiercoles(Boolean guaDiaTrabajoMiercoles) {
		this.guaDiaTrabajoMiercoles = guaDiaTrabajoMiercoles;
	}

	public Boolean getGuaDiaTrabajoSabado() {
		return this.guaDiaTrabajoSabado;
	}

	public void setGuaDiaTrabajoSabado(Boolean guaDiaTrabajoSabado) {
		this.guaDiaTrabajoSabado = guaDiaTrabajoSabado;
	}

	public Boolean getGuaDiaTrabajoViernes() {
		return this.guaDiaTrabajoViernes;
	}

	public void setGuaDiaTrabajoViernes(Boolean guaDiaTrabajoViernes) {
		this.guaDiaTrabajoViernes = guaDiaTrabajoViernes;
	}

	public HgGuardia getHgGuardia() {
		return this.hgGuardia;
	}

	public void setHgGuardia(HgGuardia hgGuardia) {
		this.hgGuardia = hgGuardia;
	}

}