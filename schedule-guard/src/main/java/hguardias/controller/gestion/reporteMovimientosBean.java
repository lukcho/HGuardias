package hguardias.controller.gestion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import hguardias.controller.access.SesionBean;
import hguardias.model.dao.entities.HgGuardia;
import hguardias.model.dao.entities.HgHistorialMovimiento;
import hguardias.model.manager.ManagerBuscar;
import hguardias.model.manager.ManagerGestion;
import hguardias.model.manager.ManagerHistorial;
import hguardias.model.manager.ManagerHorario;

@SessionScoped
@ManagedBean
public class reporteMovimientosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerGestion managerGest;
	
	@EJB
	private ManagerHorario managerHor;
	
	@EJB
	private ManagerHistorial managerhistorial;

	@EJB
	private ManagerBuscar managerBuscar;

	// guardias
	private Integer histo_id;
	private Integer hcab_id;
	private Date histo_fecha;
	private String guardia1_cedula;
	private String guardia2_cedula;
	private Integer lugardesde;
	private Integer lugarhacia;
	private Date fechacraeacion;

	private HgHistorialMovimiento movimientos;

	private List<HgHistorialMovimiento> listamovimientos;

	private String usuario;

	@Inject
	SesionBean ms;

	public reporteMovimientosBean() {
	}

	@PostConstruct
	public void ini() {
		listamovimientos = managerhistorial.findAllMovientos();
		usuario = ms.validarSesion("hg_reportemovimientos.xhtml");
	}
	
	public Integer getHisto_id() {
		return histo_id;
	}

	public void setHisto_id(Integer histo_id) {
		this.histo_id = histo_id;
	}

	public Integer getHcab_id() {
		return hcab_id;
	}

	public void setHcab_id(Integer hcab_id) {
		this.hcab_id = hcab_id;
	}

	public Date getHisto_fecha() {
		return histo_fecha;
	}

	public void setHisto_fecha(Date histo_fecha) {
		this.histo_fecha = histo_fecha;
	}

	public String getGuardia1_cedula() {
		return guardia1_cedula;
	}

	public void setGuardia1_cedula(String guardia1_cedula) {
		this.guardia1_cedula = guardia1_cedula;
	}

	public String getGuardia2_cedula() {
		return guardia2_cedula;
	}

	public void setGuardia2_cedula(String guardia2_cedula) {
		this.guardia2_cedula = guardia2_cedula;
	}

	public Integer getLugardesde() {
		return lugardesde;
	}

	public void setLugardesde(Integer lugardesde) {
		this.lugardesde = lugardesde;
	}

	public Integer getLugarhacia() {
		return lugarhacia;
	}

	public void setLugarhacia(Integer lugarhacia) {
		this.lugarhacia = lugarhacia;
	}

	public Date getFechacraeacion() {
		return fechacraeacion;
	}

	public void setFechacraeacion(Date fechacraeacion) {
		this.fechacraeacion = fechacraeacion;
	}

	public List<HgHistorialMovimiento> getListamovimientos() {
		return listamovimientos;
	}

	public void setListamovimientos(List<HgHistorialMovimiento> listamovimientos) {
		this.listamovimientos = listamovimientos;
	}

	public String getUsuario() {
		return usuario;
	}

	// movimientos
	public void limpiarCampos(){
		histo_id = null;
		hcab_id = null;
		histo_fecha = null;
		guardia1_cedula=null;
		guardia2_cedula=null;
		lugardesde=null;
		lugarhacia=null;
		fechacraeacion=null;
	}

	/**
	 * accion para cargar los datos en el formulario
	 * 
	 * @param guardia_id
	 * @param guardia_descripcion
	 * @param lug_ciudad
	 * @param guardia_estado
	 * @throws Exception
	 */
	public String cargarGuardia(HgHistorialMovimiento historial) {
		try {
			histo_id = historial.getHistoId();
			hcab_id = historial.getHgHorarioCab().getHcabId();
			guardia1_cedula = historial.getGuaCedulaDesde();
			guardia2_cedula = historial.getGuaCedulaHacia();
			histo_fecha = (Date) historial.getHdetFecha();
			lugardesde = historial.getLugIdDesde();
			lugarhacia = historial.getLugIdHacia();
			fechacraeacion = (Date) historial.getHistoFechaMovimiento();
			return "hg_nhistorial?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	
	/**
	 * limpia la informacion de guardia
	 * 
	 * @return
	 * @throws Exception
	 */
	public String volverGuardia() throws Exception {
		histo_id = null;
		hcab_id = null;
		histo_fecha = null;
		guardia1_cedula=null;
		guardia2_cedula=null;
		lugardesde=null;
		lugarhacia=null;
		fechacraeacion=null;
		return "hg_reportemovimientos?faces-redirect=true";
	}
	
	public String cambiarDescripcId(Integer param){
		String descrid=null;
		try {
			descrid = "ID: "+managerHor.horarioCabByID(param).getHcabId()+", "+managerHor.horarioCabByID(param).getHcabNombre();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return descrid;
	}
	
	public String cambiarNombreLugar(Integer param){
		String lugarNombre=null;
		try {
			lugarNombre = managerGest.LugarByID(param).getLugNombre();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lugarNombre;
	}
	
	public String cambiarNombreGuardia(String param){
		String guardia=null;
		try {
			guardia = ""+managerGest.guardiaByID(param).getGuaApellido()+" "+managerGest.guardiaByID(param).getGuaNombre()+"";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return guardia;
	}
}