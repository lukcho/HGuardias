package hguardias.controller.gestion;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class ausenciaBean implements Serializable {

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
	private String nombreguardia;
	private String apellidoguardia;

	// mmostrar
	private boolean mostrarlug_id;
	private boolean edicion;
	private boolean ediciontipo;

	private Date date;

	private List<HgAusencia> listaAusencias;

	private String usuario;

	@Inject
	SesionBean ms;

	public ausenciaBean() {
	}

	@PostConstruct
	public void ini() {
		aus_id = null;
		aus_fechainicio = null;
		aus_fechafin = null;
		aus_descripcion = null;
		edicion = false;
		ediciontipo = false;
		mostrarlug_id = false;
		nombreguardia="";
		apellidoguardia="";
		listaAusencias = managergest.findAllAusencias();
		usuario = ms.validarSesion("hg_ausencias.xhtml");
	}

	public String getGua_id() {
		return gua_id;
	}

	public void setGua_id(String gua_id) {
		this.gua_id = gua_id;
	}

	public Date getDate() {
		date = new Date();
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public List<HgAusencia> getListaAusencias() {
		return listaAusencias;
	}

	public void setListaAusencias(List<HgAusencia> listaAusencias) {
		this.listaAusencias = listaAusencias;
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
	
	public String getNombreguardia() {
		return nombreguardia;
	}
	
	public void setNombreguardia(String nombreguardia) {
		this.nombreguardia = nombreguardia;
	}
	
	public String getApellidoguardia() {
		return apellidoguardia;
	}
	
	public void setApellidoguardia(String apellidoguardia) {
		this.apellidoguardia = apellidoguardia;
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
			
			java.util.Date fechai = aus_fechainicio;
			SimpleDateFormat dateFormati = new SimpleDateFormat("yyyy-MM-dd");
			final String stringDatei= dateFormati.format(fechai);
			final java.sql.Date sqlfechai=  java.sql.Date.valueOf(stringDatei);
			
			java.util.Date fechaf = aus_fechafin;
			SimpleDateFormat dateFormatf = new SimpleDateFormat("yyyy-MM-dd");
			final String stringDatef= dateFormatf.format(fechaf);
			final java.sql.Date sqlfechaf=  java.sql.Date.valueOf(stringDatef);
			
			if (edicion) {
				managergest.editarAusencia(aus_id, sqlfechai, sqlfechaf, aus_descripcion.trim());
				getListaAusencias().clear();
				getListaAusencias().addAll(managergest.findAllAusencias());
				Mensaje.crearMensajeINFO("Actualizado - Modificado");
				aus_id=null;
				aus_fechainicio=null;
				aus_fechafin=null;
				aus_descripcion=null;
				gua_id=null;
				nombreguardia="";
				apellidoguardia="";
				
			} else {
				managergest.insertarAusencia(sqlfechai, sqlfechaf, aus_descripcion.trim());
				Mensaje.crearMensajeINFO("Registrado - Creado");
				getListaAusencias().clear();
				getListaAusencias().addAll(managergest.findAllAusencias());
				aus_id=null;
				aus_fechainicio=null;
				aus_fechafin=null;
				aus_descripcion=null;
				gua_id=null;
				nombreguardia="";
				apellidoguardia="";
			}
			return "hg_ausencias?faces-redirect=true";
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
		if (aus_fechainicio.after(aus_fechafin)) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Fecha inicio debe ser menor que la Fecha Fin",
							null));
		} else
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
			aus_id = aus.getAusId();
			aus_fechainicio = (Date) aus.getAusFechaInicio();
			aus_fechafin = (Date) aus.getAusFechaFin();
			aus_descripcion = aus.getAusDescripcion();
			gua_id = aus.getHgGuardia().getGuaCedula();
			asignarGuardia();
			mostrara();
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
	 * * @param lug_id
	 */
	public boolean averiguarAusenciaId(Integer aus_id) {
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
	public String nuevoAusencia() {
		aus_id = null;
		aus_fechainicio = null;
		aus_fechafin = null;
		aus_descripcion = null;
		gua_id = null;
		nombreguardia="";
		apellidoguardia="";
		ediciontipo = false;
		mostrarlug_id = false;
		edicion = false;
		return "hg_nausencia?faces-redirect=true";
	}

	/**
	 * limpia la informacion de ausencia
	 * 
	 * @return
	 * @throws Exception
	 */
	public String volverAusencia() throws Exception {
		aus_id = null;
		aus_fechainicio = null;
		aus_fechafin = null;
		aus_descripcion = null;
		gua_id = null;
		nombreguardia="";
		apellidoguardia="";
		mostrarlug_id = false;
		edicion = false;
		getListaAusencias().clear();
		getListaAusencias().addAll(managergest.findAllAusencias());
		return "hg_ausencias?faces-redirect=true";
	}

	/**
	 * metodo para asignar el guardia
	 * 
	 */
	public String asignarGuardia() {
		managergest.asignarGuardia(gua_id);
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
				listadoSI.add(new SelectItem(t.getGuaCedula(),t.getGuaCedula()+" - "+ t.getGuaNombre()
						+ " " + t.getGuaApellido()));
			}
		}
		return listadoSI;
	}

	/**
	 * Mestodo q carga datos del guardia
	 * 
	 * @return guardia
	 */
	public void mostrara() {
		HgGuardia gua;
		try {
			nombreguardia="";
			apellidoguardia="";
			gua = managergest.guardiaByID(gua_id);
			// poner los datos del usuario
			nombreguardia= gua.getGuaNombre();
			apellidoguardia = gua.getGuaApellido();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
