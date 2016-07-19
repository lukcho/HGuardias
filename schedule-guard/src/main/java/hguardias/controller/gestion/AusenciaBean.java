package hguardias.controller.gestion;

import java.io.Serializable;
import java.sql.Date;
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
import hguardias.model.dao.entities.HgAusencia;
import hguardias.model.dao.entities.HgGuardia;
import hguardias.model.generic.Mensaje;
import hguardias.model.manager.ManagerGestion;

@SessionScoped
@ManagedBean
public class AusenciaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerGestion managergest;

	// ausenciaes
	private Integer aus_id;
	private Date aus_fechainicio;
	private Date aus_fechafin;
	private String aus_descripcion;
	
	private String gua_id;
	private HgGuardia guardia;

	// mmostrar
	private boolean mostrarlug_id;
	private boolean edicion;
	private boolean ediciontipo;

	private List<HgAusencia> listaAusencia;

	private String usuario;

	@Inject
	SesionBean ms;

	public AusenciaBean() {
	}

	@PostConstruct
	public void ini() {
		aus_id=null;
		aus_fechainicio=null;
		aus_fechafin=null;
		aus_descripcion=null;
		edicion = false;
		ediciontipo = false;
		mostrarlug_id = false;
		listaAusencia= managergest.findAllAusencias();
		usuario = ms.validarSesion("hg_ausencia.xhtml");
	}
	
	public String getGua_id() {
		return gua_id;
	}
	
	public void setGua_id(String gua_id) {
		this.gua_id = gua_id;
	}
	
	public HgGuardia getGuardia() {
		return guardia;
	}
	
	public void setGuardia(HgGuardia guardia) {
		this.guardia = guardia;
	}

	public String getUsuario() {
		return usuario;
	}

	public Integer getAus_id() {
		return aus_id;
	}

	public void setAus_id(Integer aus_id) {
		this.aus_id = aus_id;
	}

	public Date getAus_fechainicio() {
		return aus_fechainicio;
	}

	public void setAus_fechainicio(Date aus_fechainicio) {
		this.aus_fechainicio = aus_fechainicio;
	}

	public Date getAus_fechafin() {
		return aus_fechafin;
	}

	public void setAus_fechafin(Date aus_fechafin) {
		this.aus_fechafin = aus_fechafin;
	}

	public String getAus_descripcion() {
		return aus_descripcion;
	}

	public void setAus_descripcion(String aus_descripcion) {
		this.aus_descripcion = aus_descripcion;
	}

	public List<HgAusencia> getListaAusencia() {
		return listaAusencia;
	}
	
	public void setListaAusencia(List<HgAusencia> listaAusencia) {
		this.listaAusencia = listaAusencia;
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

	

	// ausenciaes
	/**
	 * accion para invocar el manager y crear ausencia o editar el ausencia
	 * 
	 * @param lug_id
	 * @param lug_nombre
	 * @param lug_ciudad
	 * @param lug_estado
	 * @throws Exception
	 */
	public String crearAusencia() {
		try {
			if (edicion) {
				managergest.editarAusencia(aus_id, aus_fechainicio, aus_fechafin, aus_descripcion.trim());
				getListaAusencia().clear();
				getListaAusencia().addAll(managergest.findAllAusencias());
				Mensaje.crearMensajeINFO("Actualizado - Modificado");
				aus_id=null;
				aus_fechainicio=null;
				aus_fechafin=null;
				aus_descripcion=null;
				gua_id=null;
				
			} else {
				managergest.insertarAusencia(aus_fechainicio, aus_fechafin, aus_descripcion.trim());
				Mensaje.crearMensajeINFO("Registrado - Creado");
				getListaAusencia().clear();
				getListaAusencia().addAll(managergest.findAllAusencias());
				aus_id=null;
				aus_fechainicio=null;
				aus_fechafin=null;
				aus_descripcion=null;
				gua_id=null;
			}
			return "hg_ausenciaes?faces-redirect=true";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error al crear ausencia", null));
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
	public String cargarAusencia(HgAusencia aus) {
		try {
			aus_id=aus.getAusId();
			aus_fechainicio=(Date) aus.getAusFechaInicio();
			aus_fechafin=(Date) aus.getAusFechaFin();
			aus_descripcion=aus.getAusDescripcion();
			gua_id = aus.getHgGuardia().getGuaCedula();
			edicion = true;
			ediciontipo = false;
			return "hg_nausencia?faces-redirect=true";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * metodo para conocer el ausencia si esta usado
	 * 
	 *  * @param lug_id
	 */
	public boolean averiguarausenciaId(Integer aus_id) {
		Integer t = 0;
		boolean r = false;
		List<HgAusencia> lug = managergest.findAllAusencias();
		for (HgAusencia y : lug) {
			if (y.getAusId().equals(aus_id)) {
				System.out.println("si entra1");
				t = 1;
				r = true;
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"El código del ausencia existe.", null));
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
	 * Redirecciona a la pagina de creacion de ausenciaes
	 * 
	 * @return
	 */
	public String nuevoausencia() {
		
		
		mostrarlug_id = false;
		edicion = false;
		return "lug_nausencia?faces-redirect=true";
	}

	/**
	 * limpia la informacion de ausencia
	 * 
	 * @return
	 * @throws Exception
	 */
	public String volverausencia() throws Exception {
		
		
		mostrarlug_id = false;
		edicion = false;
		getListaAusencia().clear();
		getListaAusencia().addAll(managergest.findAllAusencias());
		return "hg_ausenciaes?faces-redirect=true";
	}
	
	/**
	 * metodo para asignar el guardia
	 * 
	 */
	public String asignarLugarDestino() {
		managergest.asignarguardia(gua_id);
		return "";
	}

	
	/**
	 * metodo para mostrar los guardias
	 * 
	 */
	public List<SelectItem> getListaGuardias() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		for (HgGuardia t : managergest.findAllGuardias()) {
			if (!t.getGuaEstado().equals("I")) {
				listadoSI.add(new SelectItem(t.getGuaCedula(), t.getGuaNombre()+" "+t.getGuaApellido()));
				}
		}
		return listadoSI;
	}
}
