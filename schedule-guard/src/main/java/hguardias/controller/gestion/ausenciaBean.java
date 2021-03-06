package hguardias.controller.gestion;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;

import hguardias.controller.access.SesionBean;
import hguardias.model.generic.Funciones;
import hguardias.model.dao.entidades.Guardias;
import hguardias.model.dao.entities.HgAusencia;
import hguardias.model.dao.entities.HgGuardia;
import hguardias.model.dao.entities.HgTipoAusencia;
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
	private Guardias guardia;
	private String nombreguardia;
	private String apellidoguardia;
	private Integer tipoause_id;
	// mmostrar
	private boolean mostrarlug_id;
	private boolean edicion;
	private boolean ediciontipo;

	private Date date;

	private List<HgAusencia> listaAusencias;

	private String usuario;

	private HgAusencia ausenciaElsita;
	
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
		nombreguardia = "";
		apellidoguardia = "";
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

	public Guardias getGuardia() {
		return guardia;
	}

	public void setGuardia(Guardias guardia) {
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
	
	public HgAusencia getAusenciaElsita() {
		return ausenciaElsita;
	}
	
	public void setAusenciaElsita(HgAusencia ausenciaElsita) {
		this.ausenciaElsita = ausenciaElsita;
	}
	
	public Integer getTipoause_id() {
		return tipoause_id;
	}
	
	public void setTipoause_id(Integer tipoause_id) {
		this.tipoause_id = tipoause_id;
	}

	// ausencias
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
			final String stringDatei = dateFormati.format(fechai);
			final java.sql.Date sqlfechai = java.sql.Date.valueOf(stringDatei);

			java.util.Date fechaf = aus_fechafin;
			SimpleDateFormat dateFormatf = new SimpleDateFormat("yyyy-MM-dd");
			final String stringDatef = dateFormatf.format(fechaf);
			final java.sql.Date sqlfechaf = java.sql.Date.valueOf(stringDatef);

			if (edicion) {
				managergest.editarAusencia(aus_id, sqlfechai, sqlfechaf,
						aus_descripcion.trim());
				getListaAusencias().clear();
				getListaAusencias().addAll(managergest.findAllAusencias());
				Mensaje.crearMensajeINFO("Actualizado - Modificado");
				aus_id = null;
				aus_fechainicio = null;
				aus_fechafin = null;
				aus_descripcion = null;
				gua_id = null;
				nombreguardia = "";
				apellidoguardia = "";
				tipoause_id = null;
				Mensaje.crearMensajeINFO("Ausecia Editada");
			} else {
				managergest.insertarAusencia(aus_id,sqlfechai, sqlfechaf,
						aus_descripcion.trim());
				Mensaje.crearMensajeINFO("Registrado - Creado");
				getListaAusencias().clear();
				getListaAusencias().addAll(managergest.findAllAusencias());
				aus_id = null;
				aus_fechainicio = null;
				aus_fechafin = null;
				aus_descripcion = null;
				tipoause_id = null;
				gua_id = null;
				nombreguardia = "";
				apellidoguardia = "";
				Mensaje.crearMensajeINFO("Ausencia creada");
			}
			return "hg_ausencias?faces-redirect=true";
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al crear ausencia");
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * accion para abrir el dialogo
	 * 
	 */
	public void abrirDialog() {
		Date fechainicial = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(aus_fechainicio));
		Date fechafinal = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(aus_fechafin));
		if (fechainicial.after(fechafinal))
			Mensaje.crearMensajeWARN("Fecha inicio debe ser menor que la Fecha Fin");
		else if(managergest.ausenciaXFecha(fechainicial,fechainicial,gua_id) == 0){
			RequestContext.getCurrentInstance().execute("PF('gu').show();");
		}else
			Mensaje.crearMensajeWARN("El guardia tiene ausencia en esas fechas");
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
			tipoause_id = aus.getHgTipoAusencia().getTipAusId();
			asignarGuardia();
			mostrara();
			edicion = true;
			ediciontipo = false;
			return "hg_nausencia?faces-redirect=true";
		} catch (Exception e) {
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
				t = 1;
				r = true;
				Mensaje.crearMensajeWARN("El c�digo del ausencia existe");
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
		aus_id = managergest.ultimoOrdenCabeceraAusencia();
		aus_fechainicio = null;
		aus_fechafin = null;aus_descripcion = null;
		gua_id = null;nombreguardia = "";tipoause_id = null;
		apellidoguardia = "";ediciontipo = false;
		mostrarlug_id = false;edicion = false;
		return "hg_nausencia?faces-redirect=true";
	}

	/**
	 * limpia la informacion de ausencia
	 * 
	 * @return
	 * @throws Exception
	 */
	public String volverAusencia() throws Exception {
		aus_id = null;aus_fechainicio = null;
		aus_fechafin = null;aus_descripcion = null;
		gua_id = null;nombreguardia = "";
		apellidoguardia = "";mostrarlug_id = false;
		tipoause_id = null;
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
	 * metodo para asignar el guardia
	 * 
	 */
	public String asignarTipoAusencia() {
		managergest.asignarTipoAusencia(tipoause_id);
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
				listadoSI.add(new SelectItem(t.getGuaCedula(), t.getGuaCedula()
						+ " " + t.getGuaApellido() +" "+ t.getGuaNombre() ));
			}
		}
		return listadoSI;
	}
	
	/**
	 * metodo para mostrar los guardias
	 * 
	 */
	public List<SelectItem> getListaTipoAusencia() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		for (HgTipoAusencia t : managergest.findAllTipoAusencias()) {
				listadoSI.add(new SelectItem(t.getTipAusId(), t.getTipAusNombre()));
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
			nombreguardia = "";
			apellidoguardia = "";
			gua = managergest.guardiaByID(gua_id);
			nombreguardia = gua.getGuaNombre();
			apellidoguardia = gua.getGuaApellido();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * eliminar ausencia abriendo el dialogo
	 * 
	 * @param pro_id
	 * @throws Exception
	 */
	public void abrirDialogAusenciaEliminar(HgAusencia item) {
		setAusenciaElsita(item);
		RequestContext.getCurrentInstance().execute("PF('ef').show();");
	}
	
	/**
	 * eliminar un fotoproducto
	 * 
	 * @param pro_id
	 * @throws Exception
	 */
	public String eliminarAusencia() {
		try {
			managergest.eliminarAusencia(ausenciaElsita.getAusId());
			Mensaje.crearMensajeINFO("Ausencia eliminada");
			getListaAusencias().clear();
			getListaAusencias().addAll(managergest.findAllAusencias());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
