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
import hguardias.model.dao.entities.HgGuardiasPendiente;
import hguardias.model.dao.entities.HgHorarioDet;
import hguardias.model.manager.ManagerGestion;
import hguardias.model.manager.ManagerHorario;

@SessionScoped
@ManagedBean
public class pendienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerGestion managergest;
	
	@EJB
	private ManagerHorario managerhorario;

	// usuario logeado conecta
	private String usuario;

	// mostrar
	private boolean edicion;
	private boolean ediciontipo;

	// falto
	private Integer pendienteId;
	private Date pendiente_fecha;
	private HgGuardia guardia;
	
	//vista
	private String nombreguardia1;
	private String apellidoguardia1;
	// detalle
	private List<HgHorarioDet> horarioDet;
	private String hcab_id;
	private String hcab_descripcion;
	private String hcab_turno;
	private String hcab_lugar;
	
	private List<HgGuardiasPendiente> ListaGuardiasPendiente;

	@Inject
	SesionBean ms;

	public pendienteBean() {
	}

	@PostConstruct
	public void ini() {
		usuario = ms.validarSesion("hg_pendientes.xhtml");
		pendiente_fecha = null;
		edicion = false;
		ediciontipo = false;
		guardia = new HgGuardia();
		ListaGuardiasPendiente = managergest.findAllGuardiasPendientes();
	}

	public String getUsuario() {
		return usuario;
	}

	public boolean isEdicion() {
		return edicion;
	}
	
	public List<HgGuardiasPendiente> getListaGuardiasPendientes() {
		return ListaGuardiasPendiente;
	}

	public void setEdicion(boolean edicion) {
		this.edicion = edicion;
	}

	public boolean isEdiciontipo() {
		return ediciontipo;
	}

	public void setEdiciontipo(boolean ediciontipo) {
		this.ediciontipo = ediciontipo;
	}
	
	public Date getPendiente_fecha() {
		return pendiente_fecha;
	}
	
	public void setPendiente_fecha(Date pendiente_fecha) {
		this.pendiente_fecha = pendiente_fecha;
	}
	
	public Integer getPendienteId() {
		return pendienteId;
	}
	
	public void setPendienteId(Integer pendienteId) {
		this.pendienteId = pendienteId;
	}
	
	public HgGuardia getGuardia() {
		return guardia;
	}
	
	public String getApellidoguardia1() {
		return apellidoguardia1;
	}
	
	public void setApellidoguardia1(String apellidoguardia1) {
		this.apellidoguardia1 = apellidoguardia1;
	}
	
	public String getNombreguardia1() {
		return nombreguardia1;
	}
	
	public void setNombreguardia1(String nombreguardia1) {
		this.nombreguardia1 = nombreguardia1;
	}
	
	public String getHcab_id() {
		return hcab_id;
	}

	public String getHcab_lugar() {
		return hcab_lugar;
	}

	public String getHcab_turno() {
		return hcab_turno;
	}

	public List<HgHorarioDet> getHorarioDet() {
		return horarioDet;
	}

	public String getHcab_descripcion() {
		return hcab_descripcion;
	}

	/**
	 * accion para cargar los datos en el formulario
	 * 
	 * @param horcab
	 * @throws Exception
	 */
	public String cargarPendiente(HgGuardiasPendiente pendiente) {
		try {
			pendiente_fecha = pendiente.getGuapenFecha();
			pendienteId = pendiente.getGuapenId();
			guardia = pendiente.getHgGuardia();
			mostrara();
			edicion = true;
			ediciontipo = false;
			return "hg_npendiente?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * Mestodo q carga datos del guardia
	 * 
	 * @return guardia
	 */
	public void mostrara() {
		HgGuardia gua;
		try {
			gua = managergest.guardiaByID(guardia.getGuaCedula());
			nombreguardia1 = gua.getGuaNombre();
			apellidoguardia1 = gua.getGuaApellido();
			horarioDet = managerhorario.horarioDetByCedulaFecha(
					gua.getGuaCedula(), pendiente_fecha);
			for (HgHorarioDet hordet : horarioDet) {
				hcab_id = hordet.getHgHorarioCab().getHcabId().toString();
				hcab_descripcion = hordet.getHgHorarioCab().getHcabNombre();
				hcab_turno = hordet.getHgTurno().getTurDescripcion();
				hcab_lugar = hordet.getHgLugare().getLugNombre();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * limpia la informacion de ausencia
	 * 
	 * @return
	 * @throws Exception
	 */
	public String volverPendiente() throws Exception {
		pendienteId = null;
		pendiente_fecha = null;
		guardia = null;
		nombreguardia1 = null;
		apellidoguardia1 = null;
		horarioDet = null;
		hcab_id = null;
		hcab_descripcion = null;
		hcab_turno = null;
		hcab_lugar = null;
		getListaGuardiasPendientes().clear();
		getListaGuardiasPendientes().addAll(managergest.findAllGuardiasPendientes());
		return "hg_pendientes?faces-redirect=true";
	}
}
