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

import hguardias.model.manager.ManagerBuscar;
import hguardias.model.manager.ManagerHorario;
import hguardias.model.dao.entities.Persona;
import hguardias.model.manager.ManagerCarga;
import hguardias.controller.access.SesionBean;
import hguardias.model.generic.Funciones;
import hguardias.model.dao.entities.HgGuardia;
import hguardias.model.dao.entities.HgHorarioCab;
import hguardias.model.generic.Mensaje;
import hguardias.model.manager.ManagerGestion;

@SessionScoped
@ManagedBean
public class horarioCDBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerGestion managergest;

	@EJB
	private ManagerHorario managerhorario;

	@EJB
	private ManagerBuscar mb;

	// horariocab
	private Integer horcab_id;
	private String horcab_nombre;
	private String horcab_usuarioreg;
	private Date horcab_fechainicio;
	private Date horcab_fechafin;

	// usuario logeado conecta
	private String usuario;
	private String cedula;
	private Persona per;

	// usuario
	private String usuariologeado;

	// mmostrar
	private boolean edicion;
	private boolean ediciontipo;

	private Date date;

	private List<HgHorarioCab> listaHorarioCab;

	@Inject
	SesionBean ms;

	public horarioCDBean() {
	}

	@PostConstruct
	public void ini() {
		usuario = ms.validarSesion("hg_ausencia.xhtml");
		System.out.println(usuario);
		BuscarPersona();
		horcab_id = null;
		horcab_fechainicio = null;
		horcab_fechafin = null;
		horcab_nombre = null;
		horcab_usuarioreg=null;
		edicion = false;
		ediciontipo = false;
		usuariologeado = "";
		listaHorarioCab = managerhorario.findAllHorariosCab();
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	
	public String getHorcab_usuarioreg() {
		return horcab_usuarioreg;
	}
	
	public void setHorcab_usuarioreg(String horcab_usuarioreg) {
		this.horcab_usuarioreg = horcab_usuarioreg;
	}

	public Date getDate() {
		date = new Date();
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUsuario() {
		return usuario;
	}

	public Integer gethorcab_id() {
		return horcab_id;
	}

	public void sethorcab_id(Integer horcab_id) {
		this.horcab_id = horcab_id;
	}

	public Date gethorcab_fechainicio() {
		return horcab_fechainicio;
	}

	public void sethorcab_fechainicio(Date horcab_fechainicio) {
		this.horcab_fechainicio = horcab_fechainicio;
	}

	public Date gethorcab_fechafin() {
		return horcab_fechafin;
	}

	public void sethorcab_fechafin(Date horcab_fechafin) {
		this.horcab_fechafin = horcab_fechafin;
	}

	public String gethorcab_nombre() {
		return horcab_nombre;
	}

	public void sethorcab_nombre(String horcab_nombre) {
		this.horcab_nombre = horcab_nombre;
	}

	public List<HgHorarioCab> getlistaHorarioCab() {
		return listaHorarioCab;
	}

	public void setlistaHorarioCab(List<HgHorarioCab> listaHorarioCab) {
		this.listaHorarioCab = listaHorarioCab;
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

	public String getusuariologeado() {
		return usuariologeado;
	}

	public void setusuariologeado(String usuariologeado) {
		this.usuariologeado = usuariologeado;
	}

	// horairocab
	/**
	 * accion para invocar el manager y crear ausencia o editar el ausencia
	 * 
	 * @param horcab_id
	 * @param sqlfechai
	 * @param sqlfechaf
	 * @param horcab_nombre
	 * @throws Exception
	 */
	public String crearHorarioCab() {
		try {

			java.util.Date fechai = horcab_fechainicio;
			SimpleDateFormat dateFormati = new SimpleDateFormat("yyyy-MM-dd");
			final String stringDatei = dateFormati.format(fechai);
			final java.sql.Date sqlfechai = java.sql.Date.valueOf(stringDatei);

			java.util.Date fechaf = horcab_fechafin;
			SimpleDateFormat dateFormatf = new SimpleDateFormat("yyyy-MM-dd");
			final String stringDatef = dateFormatf.format(fechaf);
			final java.sql.Date sqlfechaf = java.sql.Date.valueOf(stringDatef);

			horcab_usuarioreg = usuariologeado.trim();
			managerhorario.insertarHorarioCab(sqlfechai, sqlfechaf, horcab_nombre.trim(),horcab_usuarioreg);
			Mensaje.crearMensajeINFO("Registrado - Creado");
			getlistaHorarioCab().clear();
			getlistaHorarioCab().addAll(managerhorario.findAllHorariosCab());
			horcab_id = null;
			horcab_fechainicio = null;
			horcab_fechafin = null;
			horcab_nombre = null;
			horcab_usuarioreg=null;

			return "hg_hcabeceras?faces-redirect=true";
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
	 * @param horcab
	 * @throws Exception
	 */
	public String cargarHorarioCab(HgHorarioCab horcab) {
		try {
			horcab_id = horcab.getHcabId();
			horcab_fechainicio = (Date) horcab.getHcabFechaInicio();
			horcab_fechafin = (Date) horcab.getHcabFechaFin();
			horcab_nombre = horcab.getHcabNombre();
			horcab_usuarioreg = horcab.getHcabUsuario();
			edicion = true;
			ediciontipo = false;
			return "hg_nhcabcera?faces-redirect=true";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "";
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
	 * Redirecciona a la pagina de creacion de hroairocab
	 * 
	 * @return
	 */
	public String nuevoHorarioCab() {
		BuscarPersona();
		horcab_id = null;
		horcab_fechainicio = null;
		horcab_fechafin = null;
		horcab_nombre = null;
		ediciontipo = false;
		edicion = false;
		return "lug_nhcabecera?faces-redirect=true";
	}

	/**
	 * limpia la informacion de ausencia
	 * 
	 * @return
	 * @throws Exception
	 */
	public String volverHorarioCab() throws Exception {
		horcab_id = null;
		horcab_fechainicio = null;
		horcab_fechafin = null;
		horcab_nombre = null;
		edicion = false;
		getlistaHorarioCab().clear();
		getlistaHorarioCab().addAll(managerhorario.findAllHorariosCab());
		return "hg_hcabeceras?faces-redirect=true";
	}

	/**
	 * metodo para mostrar los guardias
	 * 
	 */
	public List<SelectItem> getListaGuardias() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		for (HgGuardia t : managergest.findAllGuardias()) {
			if (!t.getGuaEstado().equals("I")) {
				listadoSI.add(new SelectItem(t.getGuaCedula(), t.getGuaNombre()
						+ " " + t.getGuaApellido()));
			}
		}
		return listadoSI;
	}

	/**
	 * Metodo para buscar persona logeada
	 * 
	 * @throws Exception
	 */
	public void BuscarPersona() {
		try {
			cedula = ManagerCarga.consultaSQL(usuario);
			per = mb.buscarPersonaWSReg(cedula);

			if (per != null) {
				usuariologeado = per.getPerNombres() + " "
						+ per.getPerApellidos();
			} else {
				throw new Exception("PERSONA NULA");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
