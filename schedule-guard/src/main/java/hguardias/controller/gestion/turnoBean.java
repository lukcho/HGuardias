package hguardias.controller.gestion;

import java.io.Serializable;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import hguardias.model.dao.entities.HgTurno;
import hguardias.model.generic.Mensaje;
import hguardias.model.manager.ManagerGestion;

@SessionScoped
@ManagedBean
public class turnoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerGestion managergest;

	private static String Activo = "A";
	// Turnos
	private Integer turno_id;
	private String turno_descripcion;
	private String turno_hora_inicio;
	private String turno_hora_fin;
	private String turno_estado;

	// fechas
	private Time horainiciotiemp;
	private Time horafintiemp;

	private HgTurno turno;

	// mmostrar
	private boolean mostrarturno_id;
	private boolean edicion;
	private boolean ediciontipo;

	private List<HgTurno> listaTurnos;

	private String usuario;

	@Inject
	SesionBean ms;

	public turnoBean() {
	}

	@PostConstruct
	public void ini() {
		turno_id = null;
		turno_descripcion = "";
		turno_estado = "A";
		edicion = false;
		turno_hora_inicio = null;
		turno_hora_fin = null;
		ediciontipo = false;
		mostrarturno_id = false;
		listaTurnos = managergest.findAllTurnos();
		usuario = ms.validarSesion("hg_turnos.xhtml");
	}

	public HgTurno getTurno() {
		return turno;
	}

	public void setTurno(HgTurno turno) {
		this.turno = turno;
	}

	public String getUsuario() {
		return usuario;
	}

	public Integer getturno_id() {
		return turno_id;
	}

	public void setturno_id(Integer turno_id) {
		this.turno_id = turno_id;
	}

	public String getturno_descripcion() {
		return turno_descripcion;
	}

	public void setturno_descripcion(String turno_descripcion) {
		this.turno_descripcion = turno_descripcion;
	}

	public String getturno_estado() {
		return turno_estado;
	}

	public void setturno_estado(String turno_estado) {
		this.turno_estado = turno_estado;
	}

	public boolean isMostrarturno_id() {
		return mostrarturno_id;
	}

	public void setMostrarturno_id(boolean mostrarturno_id) {
		this.mostrarturno_id = mostrarturno_id;
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

	public List<HgTurno> getListaTurnos() {
		return listaTurnos;
	}

	public void setListaTurnos(List<HgTurno> listaTurnos) {
		this.listaTurnos = listaTurnos;
	}

	public String getTurno_hora_inicio() {
		return turno_hora_inicio;
	}

	public void setTurno_hora_inicio(String turno_hora_inicio) {
		this.turno_hora_inicio = turno_hora_inicio;
	}

	public String getTurno_hora_fin() {
		return turno_hora_fin;
	}

	public void setTurno_hora_fin(String turno_hora_fin) {
		this.turno_hora_fin = turno_hora_fin;
	}

	// turnos
	/**
	 * accion para invocar el manager y crear Turno o editar el Turno
	 * 
	 * @param turno_id
	 * @param turno_descripcion
	 * @param lug_ciudad
	 * @param turno_estado
	 * @throws Exception
	 */
	public String crearTurno() {
		try {
			DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
			horainiciotiemp = new java.sql.Time(formatter.parse(
					turno_hora_inicio).getTime());
			horafintiemp = new java.sql.Time(formatter.parse(turno_hora_fin)
					.getTime());
			if (edicion) {
				managergest.editarTurno(turno_id, turno_descripcion.trim(),
						horainiciotiemp, horafintiemp, turno_estado.trim());
				Mensaje.crearMensajeINFO("Actualizado - Modificado");
				limpiarCampos();
			} else {
				managergest.insertarTurno(turno_id,turno_descripcion.trim(),
						horainiciotiemp, horafintiemp);
				Mensaje.crearMensajeINFO("Registrado - Creado");
				limpiarCampos();
			}
			getListaTurnos().clear();
			getListaTurnos().addAll(managergest.findAllTurnos());
			return "hg_turnos?faces-redirect=true";
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al crear turno");
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Limpiar campos
	 */
	public void limpiarCampos() {
		turno_id = null;
		turno_descripcion = null;
		turno_estado = "A";
		turno_hora_inicio = null;
		turno_hora_fin = null;
		horainiciotiemp = null;
		horafintiemp = null;
		edicion = true;
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
	 * @param turno_id
	 * @param turno_descripcion
	 * @param lug_ciudad
	 * @param turno_estado
	 * @throws Exception
	 */
	public String cargarTurno(HgTurno turno) {
		try {
			turno_id = turno.getTurId();
			turno_descripcion = turno.getTurDescripcion();
			turno_estado = turno.getTurEstado();
			turno_hora_inicio = turno.getTurHoraInicio().toString();
			turno_hora_fin = turno.getTurHoraFin().toString();
			edicion = true;
			ediciontipo = false;
			return "hg_nturno?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * activar y desactivar estadoTurno
	 * 
	 * @throws Exception
	 */
	public String cambiarEstadoTurno() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("INFORMACIÓN",
					managergest.cambioEstadoTurno(getTurno().getTurId())));
			getListaTurnos().clear();
			getListaTurnos().addAll(managergest.findAllTurnos());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * cambiar el estado del turnos
	 * 
	 * @param cond
	 * @throws Exception
	 */
	public void cambiarEstadoTurnoa(HgTurno cond) {
		setTurno(cond);
		RequestContext.getCurrentInstance().execute("PF('ce').show();");
	}

	/**
	 * metodo para conocer el turno_id si esta usado
	 * 
	 * * @param turno_id
	 */
	public boolean averiguarTurnoId(Integer turno_id) {
		Integer t = 0;
		boolean r = false;
		List<HgTurno> lug = managergest.findAllTurnos();
		for (HgTurno y : lug) {
			if (y.getTurId().equals(turno_id)) {
				t = 1;
				r = true;
				Mensaje.crearMensajeWARN("El código del lugar existe");
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
	 * Redirecciona a la pagina de creacion de turnos
	 * 
	 * @return
	 */
	public String nuevoTurno() {
		turno_id = managergest.ultimoOrdenCabeceraTurno();
		turno_descripcion = null;
		turno_estado = "A";
		turno_hora_inicio = null;
		turno_hora_fin = null;
		horainiciotiemp = null;
		horafintiemp = null;
		mostrarturno_id = false;
		edicion = false;
		return "hg_nturno?faces-redirect=true";
	}

	/**
	 * limpia la informacion de turno
	 * 
	 * @return
	 * @throws Exception
	 */
	public String volverTurno() throws Exception {
		turno_id = null;
		turno_descripcion = null;
		turno_estado = "A";
		turno_hora_inicio = null;
		turno_hora_fin = null;
		horainiciotiemp = null;
		horafintiemp = null;
		mostrarturno_id = false;
		edicion = false;
		getListaTurnos().clear();
		getListaTurnos().addAll(managergest.findAllTurnos());
		return "hg_turnos?faces-redirect=true";
	}

	/**
	 * Lista de horas
	 * 
	 * @return lista de items de horas
	 */
	public List<SelectItem> getlistHoras() {
		List<SelectItem> lista = new ArrayList<SelectItem>();
		lista.add(new SelectItem(Funciones.hora_1, Funciones.hora_1));
		lista.add(new SelectItem(Funciones.hora_2, Funciones.hora_2));
		lista.add(new SelectItem(Funciones.hora_3, Funciones.hora_3));
		lista.add(new SelectItem(Funciones.hora_4, Funciones.hora_4));
		lista.add(new SelectItem(Funciones.hora_5, Funciones.hora_5));
		lista.add(new SelectItem(Funciones.hora_6, Funciones.hora_6));
		lista.add(new SelectItem(Funciones.hora_7, Funciones.hora_7));
		lista.add(new SelectItem(Funciones.hora_8, Funciones.hora_8));
		lista.add(new SelectItem(Funciones.hora_9, Funciones.hora_9));
		lista.add(new SelectItem(Funciones.hora_10, Funciones.hora_10));
		lista.add(new SelectItem(Funciones.hora_11, Funciones.hora_11));
		lista.add(new SelectItem(Funciones.hora_12, Funciones.hora_12));
		lista.add(new SelectItem(Funciones.hora_13, Funciones.hora_13));
		lista.add(new SelectItem(Funciones.hora_14, Funciones.hora_14));
		lista.add(new SelectItem(Funciones.hora_15, Funciones.hora_15));
		lista.add(new SelectItem(Funciones.hora_16, Funciones.hora_16));
		lista.add(new SelectItem(Funciones.hora_17, Funciones.hora_17));
		lista.add(new SelectItem(Funciones.hora_18, Funciones.hora_18));
		lista.add(new SelectItem(Funciones.hora_19, Funciones.hora_19));
		lista.add(new SelectItem(Funciones.hora_20, Funciones.hora_20));
		lista.add(new SelectItem(Funciones.hora_21, Funciones.hora_21));
		lista.add(new SelectItem(Funciones.hora_22, Funciones.hora_22));
		lista.add(new SelectItem(Funciones.hora_23, Funciones.hora_23));
		lista.add(new SelectItem(Funciones.hora_24, Funciones.hora_24));
		return lista;
	}
	
	public String cambiarNombre(String param){
		if(param.equals(Activo)){
			return "Activo";
		}else{
			return "Inactivo";
		}
	}
}
