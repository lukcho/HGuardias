package hguardias.controller.gestion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;

import hguardias.controller.access.SesionBean;
import hguardias.model.generic.Funciones;
import hguardias.model.dao.entities.HgLugare;
import hguardias.model.generic.Mensaje;
import hguardias.model.manager.ManagerGestion;

@SessionScoped
@ManagedBean
public class lugarBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerGestion managergest;

	// Lugares
	private Integer lug_id;
	private String lug_nombre;
	private String lug_nroguardias;
	private String lug_ciudad;
	private String lug_estado;
	private boolean lug_CCTV;
	private boolean lug_controlaccs;

	private HgLugare lug;

	// mmostrar
	private boolean mostrarlug_id;
	private boolean edicion;
	private boolean ediciontipo;

	private List<HgLugare> listaLugares;

	private String usuario;

	@Inject
	SesionBean ms;

	public lugarBean() {
	}

	@PostConstruct
	public void ini() {
		lug_id = null;
		lug_nombre = "";
		lug_ciudad = "";
		lug_nroguardias=null;
		lug_CCTV=false;
		lug_controlaccs=false;
		lug_estado = "A";
		edicion = false;
		ediciontipo = false;
		mostrarlug_id = false;
		listaLugares = managergest.findAllLugares();
		usuario = ms.validarSesion("hg_lugares.xhtml");
	}

	public String getUsuario() {
		return usuario;
	}

	public Integer getLug_id() {
		return lug_id;
	}

	public void setLug_id(Integer lug_id) {
		this.lug_id = lug_id;
	}

	public String getLug_nombre() {
		return lug_nombre;
	}

	public void setLug_nombre(String lug_nombre) {
		this.lug_nombre = lug_nombre;
	}

	public String getLug_ciudad() {
		return lug_ciudad;
	}

	public void setLug_ciudad(String lug_ciudad) {
		this.lug_ciudad = lug_ciudad;
	}

	public String getLug_estado() {
		return lug_estado;
	}

	public void setLug_estado(String lug_estado) {
		this.lug_estado = lug_estado;
	}

	public boolean isMostrarlug_id() {
		return mostrarlug_id;
	}

	public void setMostrarlug_id(boolean mostrarlug_id) {
		this.mostrarlug_id = mostrarlug_id;
	}

	public boolean isEdicion() {
		return edicion;
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

	public String getLug_nroguardias() {
		return lug_nroguardias;
	}
	
	public void setLug_nroguardias(String lug_nroguardias) {
		this.lug_nroguardias = lug_nroguardias;
	}

	public boolean isLug_CCTV() {
		return lug_CCTV;
	}

	public void setLug_CCTV(boolean lug_CCTV) {
		this.lug_CCTV = lug_CCTV;
	}

	public boolean isLug_controlaccs() {
		return lug_controlaccs;
	}

	public void setLug_controlaccs(boolean lug_controlaccs) {
		this.lug_controlaccs = lug_controlaccs;
	}

	public HgLugare getLug() {
		return lug;
	}

	public void setLug(HgLugare lug) {
		this.lug = lug;
	}

	public List<HgLugare> getListaLugares() {
		return listaLugares;
	}

	public void setListaLugares(List<HgLugare> listaLugares) {
		this.listaLugares = listaLugares;
	}

	// Lugares
	/**
	 * accion para invocar el manager y crear lugar o editar el lugar
	 * 
	 * @param lug_id
	 * @param lug_nombre
	 * @param lug_ciudad
	 * @param lug_estado
	 * @throws Exception
	 */
	public String crearLugar() {
		try {
			Integer nroguradias;
			nroguradias = Integer.parseInt(lug_nroguardias);
			
			if (edicion) {
				managergest.editarLugar(lug_id, lug_nombre.trim(), lug_ciudad.trim(), nroguradias, lug_CCTV, lug_controlaccs, lug_estado.trim());
				getListaLugares().clear();
				getListaLugares().addAll(managergest.findAllLugares());
				Mensaje.crearMensajeINFO("Actualizado - Modificado");
				lug_id = null;
				lug_nombre = null;
				lug_ciudad = null;
				lug_nroguardias=null;
				lug_estado = "A";
				lug_CCTV=false;
				lug_controlaccs=false;
				
			} else {
				managergest.insertarLugar(lug_nombre.trim(), lug_ciudad.trim(), nroguradias, lug_CCTV, lug_controlaccs, lug_estado.trim());
				Mensaje.crearMensajeINFO("Registrado - Creado");
				getListaLugares().clear();
				getListaLugares().addAll(managergest.findAllLugares());
				lug_id = null;
				lug_nombre = null;
				lug_ciudad = null;
				lug_nroguardias=null;
				lug_estado = "A";
				lug_CCTV=false;
				lug_controlaccs=false;
			}
			return "hg_lugares?faces-redirect=true";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error al crear lugar", null));
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), null));
			return "";
		}
	}

	/**
	 * accion para abrir el dialogo
	 * 
	 */
	public void abrirDialog() {
		RequestContext.getCurrentInstance().execute("PF('gu').show();");
	}

	/**
	 * accion para cargar los datos en el formulario
	 * 
	 * @param lug_id
	 * @param lug_nombre
	 * @param lug_ciudad
	 * @param lug_estado
	 * @throws Exception
	 */
	public String cargarLugar(HgLugare lug) {
		try {
			lug_id = lug.getLugId();
			lug_nombre = lug.getLugNombre();
			lug_ciudad = lug.getLugCiudad();
			lug_nroguardias = lug.getLugNroGuardias().toString();
			lug_estado = lug.getLugEstado();
			lug_CCTV=lug.getLugCctv();
			lug_controlaccs=lug.getLugControlAccesos();
			edicion = true;
			ediciontipo = false;
			return "hg_nlugar?faces-redirect=true";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * activar y desactivar estadoLugar
	 * 
	 * @throws Exception
	 */
	public String cambiarEstadoLugar() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("INFORMACIÓN",
					managergest.cambioEstadoLugar(getLug().getLugId())));
			getListaLugares().clear();
			getListaLugares().addAll(managergest.findAllLugares());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "";
	}

	/**
	 * cambiar el estado del lugares
	 * 
	 * @param cond
	 * @throws Exception
	 */
	public void cambiarEstadoLugara(HgLugare cond) {
		setLug(cond);
		RequestContext.getCurrentInstance().execute("PF('ce').show();");
		System.out.println("holi");
	}

	/**
	 * metodo para conocer el lug_id si esta usado
	 * 
	 *  * @param lug_id
	 */
	public boolean averiguarLugarId(Integer lug_id) {
		Integer t = 0;
		boolean r = false;
		List<HgLugare> lug = managergest.findAllLugares();
		for (HgLugare y : lug) {
			if (y.getLugId().equals(lug_id)) {
				System.out.println("si entra1");
				t = 1;
				r = true;
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"El código del lugar existe.", null));
			}
		}
		if (t == 0) {
			r = false;
		}
		return r;
	}

	/**
	 * Lista de estados
	 * 
	 * @return lista de items de estados
	 */
	public List<SelectItem> getlistEstados() {
		List<SelectItem> lista = new ArrayList<SelectItem>();
		lista.add(new SelectItem(Funciones.estadoActivo, Funciones.estadoActivo
				+ " : " + Funciones.valorEstadoActivo));
		lista.add(new SelectItem(Funciones.estadoInactivo,
				Funciones.estadoInactivo + " : "
						+ Funciones.valorEstadoInactivo));
		return lista;
	}

	/**
	 * Redirecciona a la pagina de creacion de lugares
	 * 
	 * @return
	 */
	public String nuevoLugar() {
		
		lug_id = null;
		lug_nombre = null;
		lug_ciudad = null;
		lug_nroguardias=null;
		lug_estado = "A";
		lug_CCTV=false;
		lug_controlaccs=false;
		mostrarlug_id = false;
		edicion = false;
		return "hg_nlugar?faces-redirect=true";
	}

	/**
	 * limpia la informacion de lugar
	 * 
	 * @return
	 * @throws Exception
	 */
	public String volverLugar() throws Exception {
		lug_id = null;
		lug_nombre = null;
		lug_ciudad = null;
		lug_nroguardias=null;
		lug_estado = "A";
		lug_CCTV=false;
		lug_controlaccs=false;
		mostrarlug_id = false;
		edicion = false;
		getListaLugares().clear();
		getListaLugares().addAll(managergest.findAllLugares());
		return "hg_lugares?faces-redirect=true";
	}
}
