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
import hguardias.model.dao.entities.HgLugarTurno;
import hguardias.model.dao.entities.HgLugare;
import hguardias.model.dao.entities.HgTurno;
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
	private boolean lug_lunes;
	private boolean lug_martes;
	private boolean lug_miercoles;
	private boolean lug_jueves;
	private boolean lug_viernes;
	private boolean lug_sabado;
	private boolean lug_domingo;

	private Integer lugturn_id;
	private Integer lugturn_turno;
	private Integer lugturn_lugar;
	private Integer lugturn_nroGuardias;

	private HgLugare lug;
	private HgLugarTurno lug_turno;

	private Integer lug_idTurno;
	private String numeroGuardias;
	private HgLugarTurno lugarTurnoElsita;
	private List<HgLugarTurno> listaLugarTurno;

	// mmostrar
	private boolean mostrarlug_id;
	private boolean edicion;
	private boolean ediciontipo;
	private boolean guardado;

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
		lug_nroguardias = null;
		lug_CCTV = false;
		lug_controlaccs = false;
		lug_lunes = false;
		lug_martes = false;
		lug_miercoles = false;
		lug_jueves = false;
		lug_viernes = false;
		lug_sabado = false;
		lug_domingo = false;
		lug_estado = "A";
		edicion = false;
		ediciontipo = false;
		mostrarlug_id = false;
		numeroGuardias = "0";
		guardado = false;
		lugturn_id = 0;
		lugturn_turno = 0;
		lugturn_lugar = 0;
		lugturn_nroGuardias = 0;
		listaLugares = managergest.findAllLugares();
		listaLugarTurno = new ArrayList<HgLugarTurno>();
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

	public boolean isLug_lunes() {
		return lug_lunes;
	}

	public void setLug_lunes(boolean lug_lunes) {
		this.lug_lunes = lug_lunes;
	}

	public boolean isLug_martes() {
		return lug_martes;
	}

	public void setLug_martes(boolean lug_martes) {
		this.lug_martes = lug_martes;
	}

	public boolean isLug_miercoles() {
		return lug_miercoles;
	}

	public void setLug_miercoles(boolean lug_miercoles) {
		this.lug_miercoles = lug_miercoles;
	}

	public boolean isLug_jueves() {
		return lug_jueves;
	}

	public void setLug_jueves(boolean lug_jueves) {
		this.lug_jueves = lug_jueves;
	}

	public boolean isLug_viernes() {
		return lug_viernes;
	}

	public void setLug_viernes(boolean lug_viernes) {
		this.lug_viernes = lug_viernes;
	}

	public boolean isLug_sabado() {
		return lug_sabado;
	}

	public void setLug_sabado(boolean lug_sabado) {
		this.lug_sabado = lug_sabado;
	}

	public boolean isLug_domingo() {
		return lug_domingo;
	}

	public void setLug_domingo(boolean lug_domingo) {
		this.lug_domingo = lug_domingo;
	}

	public Integer getLug_idTurno() {
		return lug_idTurno;
	}

	public void setLug_idTurno(Integer lug_idTurno) {
		this.lug_idTurno = lug_idTurno;
	}

	public String getNumeroGuardias() {
		return numeroGuardias;
	}

	public void setNumeroGuardias(String numeroGuardias) {
		this.numeroGuardias = numeroGuardias;
	}

	public HgLugarTurno getLugarTurnoElsita() {
		return lugarTurnoElsita;
	}

	public void setLugarTurnoElsita(HgLugarTurno lugarTurnoElsita) {
		this.lugarTurnoElsita = lugarTurnoElsita;
	}

	public List<HgLugarTurno> getListaLugarTurno() {
		return listaLugarTurno;
	}

	public void setListaLugarTurno(List<HgLugarTurno> listaLugarTurno) {
		this.listaLugarTurno = listaLugarTurno;
	}

	public boolean isGuardado() {
		return guardado;
	}

	public void setGuardado(boolean guardado) {
		this.guardado = guardado;
	}

	public Integer getLugturn_id() {
		return lugturn_id;
	}

	public void setLugturn_id(Integer lugturn_id) {
		this.lugturn_id = lugturn_id;
	}

	public Integer getLugturn_lugar() {
		return lugturn_lugar;
	}

	public void setLugturn_lugar(Integer lugturn_lugar) {
		this.lugturn_lugar = lugturn_lugar;
	}

	public Integer getLugturn_turno() {
		return lugturn_turno;
	}

	public void setLugturn_turno(Integer lugturn_turno) {
		this.lugturn_turno = lugturn_turno;
	}

	public Integer getLugturn_nroGuardias() {
		return lugturn_nroGuardias;
	}

	public void setLugturn_nroGuardias(Integer lugturn_nroGuardias) {
		this.lugturn_nroGuardias = lugturn_nroGuardias;
	}

	public HgLugarTurno getLug_turno() {
		return lug_turno;
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
			Integer numguardia = 1;
			if (edicion) {
				managergest.editarLugar(lug_id, lug_nombre.trim(),
						lug_ciudad.trim(), numguardia, lug_CCTV,
						lug_controlaccs, lug_lunes, lug_martes, lug_miercoles,
						lug_jueves, lug_viernes, lug_sabado, lug_domingo,
						lug_estado.trim());
				getListaLugares().clear();
				getListaLugares().addAll(managergest.findAllLugares());
				guardado = false;
				Mensaje.crearMensajeINFO("Actualizado - Modificado");
			} else {
				managergest.insertarLugar(lug_nombre.trim(), lug_ciudad.trim(),
						numguardia, lug_CCTV, lug_controlaccs, lug_lunes,
						lug_martes, lug_miercoles, lug_jueves, lug_viernes,
						lug_sabado, lug_domingo, lug_estado.trim());
				for (HgLugare lug : managergest.findLugarByNombre(lug_nombre)) {
					lug_id = lug.getLugId();
				}
				getListaLugares().clear();
				getListaLugares().addAll(managergest.findAllLugares());
				guardado = true;
				Mensaje.crearMensajeINFO("Creado correctamente el lugar");
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			Mensaje.crearMensajeWARN("Error al crear lugar");
			return "";
		}
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
	public void cargarLugarTurno(HgLugarTurno lugarturno) {
		try {
			lugturn_id = lugarturno.getLugTur();
			lugturn_turno = lugarturno.getHgTurno().getTurId();
			lugturn_lugar = lugarturno.getHgLugare().getLugId();
			lugturn_nroGuardias = lugarturno.getLugTurNumeroGuardias();
			asignarTurnoactu();
			RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * accion para invocar el manager y crear lugar o editar el lugar
	 * 
	 * @param lug_id
	 * @param lug_nombre
	 * @param lug_ciudad
	 * @param lug_estado
	 * @throws Exception
	 */
	public void actualizarTurno() {
		try {
			managergest.editarLugarTurno(lugturn_id, lugturn_turno,
					lugturn_lugar, lugturn_nroGuardias);
			getListaLugarTurno().clear();
			getListaLugarTurno().addAll(
					managergest.LugarTurnoById1((lugturn_lugar)));
			Mensaje.crearMensajeINFO("Actualizado - Modificado");
			Mensaje.crearMensajeINFO("Creado correctamente el lugar");
		} catch (Exception e) {
			e.printStackTrace();
			Mensaje.crearMensajeWARN("Error al actualizar el turno");
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
	 * accion para abrir el dialogo
	 * 
	 */
	public void abrirDialog1() {
		if (lug_id != null) {
			if (lug_idTurno != -1) {
				if (!numeroGuardias.equals("") && !numeroGuardias.equals("0")) {
					RequestContext.getCurrentInstance().execute(
							"PF('gu1').show();");
				} else {
					Mensaje.crearMensajeWARN("Debe agregar un valor a número de guardias");
				}
			} else {
				Mensaje.crearMensajeWARN("Debe seleccionar un turno");
			}
		} else {
			Mensaje.crearMensajeWARN("Debe crear primero el lugar");
		}
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
			lug_CCTV = lug.getLugCctv();
			lug_controlaccs = lug.getLugControlAccesos();
			lug_lunes = lug.getLugLunes();
			lug_martes = lug.getLugMartes();
			lug_miercoles = lug.getLugMiercoles();
			lug_jueves = lug.getLugJueves();
			lug_viernes = lug.getLugViernes();
			lug_sabado = lug.getLugSabado();
			lug_domingo = lug.getLugDomingo();
			edicion = true;
			ediciontipo = false;
			guardado = false;
			getListaLugarTurno().clear();
			getListaLugarTurno().addAll(managergest.LugarTurnoById1(lug_id));
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
			e.printStackTrace();
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
	}

	/**
	 * metodo para conocer el lug_id si esta usado
	 * 
	 * * @param lug_id
	 */
	public boolean averiguarLugarId(Integer lug_id) {
		Integer t = 0;
		boolean r = false;
		List<HgLugare> lug = managergest.findAllLugares();
		for (HgLugare y : lug) {
			if (y.getLugId().equals(lug_id)) {
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
	 * Redirecciona a la pagina de creacion de lugares
	 * 
	 * @return
	 */
	public String nuevoLugar() {
		lug_id = null;lug_nombre = null;
		lug_ciudad = null;lug_nroguardias = null;
		lug_estado = "A";lug_CCTV = false;
		lug_controlaccs = false;lug_lunes = false;
		lug_martes = false;lug_miercoles = false;
		lug_jueves = false;lug_viernes = false;
		lug_sabado = false;lug_domingo = false;
		mostrarlug_id = false;edicion = false;
		guardado = false;lug_idTurno = -1;
		numeroGuardias = "0";
		getListaLugarTurno().clear();
		return "hg_nlugar?faces-redirect=true";
	}

	/**
	 * limpia la informacion de lugar
	 * 
	 * @return
	 * @throws Exception
	 */
	public String volverLugar() throws Exception {
		lug_id = null;lug_nombre = null;
		lug_ciudad = null;lug_nroguardias = null;
		lug_estado = "A";lug_CCTV = false;
		lug_controlaccs = false;lug_lunes = false;
		lug_martes = false;lug_miercoles = false;
		lug_jueves = false;lug_viernes = false;
		lug_sabado = false;lug_domingo = false;
		mostrarlug_id = false;edicion = false;
		guardado = false;lug_idTurno = -1;
		numeroGuardias = "0";
		getListaLugares().clear();
		getListaLugarTurno().clear();
		getListaLugares().addAll(managergest.findAllLugares());
		return "hg_lugares?faces-redirect=true";
	}

	// turnolugar
	/**
	 * guardar una imagen fotoproducto
	 * 
	 * @param pro_id
	 * @throws Exception
	 */
	public void crearLugarTurno() {
		try {
			if (managergest.lugar_TurnoByID(lug_id) == 0) {
				managergest.insertarTurnoLugar(numeroGuardias);
				lug_idTurno = -1;
				numeroGuardias = "0";
				getListaLugarTurno().clear();
				getListaLugarTurno()
						.addAll(managergest.LugarTurnoById1(lug_id));
				Mensaje.crearMensajeINFO("Turno creado");
			} else {
				Mensaje.crearMensajeWARN("Yá se encuentra el turno creado");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * metodo para mostrar los lugaresTurno
	 * 
	 */
	public List<SelectItem> getlistaTurno() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		for (HgTurno t : managergest.findAllTurnos()) {
			if (!t.getTurEstado().equals("I")) {
				listadoSI.add(new SelectItem(t.getTurId(), t
						.getTurDescripcion()
						+ " "
						+ t.getTurHoraInicio()
						+ " - " + t.getTurHoraFin() + ""));
			}
		}
		return listadoSI;
	}

	/**
	 * metodo para asignar el lugarorigen a solicitud
	 * 
	 */
	public String asignarTurno() {
		managergest.asignarLugar(lug_id);
		managergest.asignarTurno(lug_idTurno);
		return "";
	}

	/**
	 * metodo para asignar el lugarorigen a solicitud
	 * 
	 */
	public String asignarTurnoactu() {
		managergest.asignarLugar(lugturn_lugar);
		managergest.asignarTurno(lugturn_turno);
		return "";
	}

	/**
	 * eliminar lugturno abriendo el dialogo
	 * 
	 * @param pro_id
	 * @throws Exception
	 */
	public void eliminarLugarTurno(HgLugarTurno item) {
		setLugarTurnoElsita(item);
		RequestContext.getCurrentInstance().execute("PF('ef').show();");
	}

	/**
	 * eliminar un fotoproducto
	 * 
	 * @param pro_id
	 * @throws Exception
	 */
	public String eliminarTurnoLugar() {
		try {
			managergest.eliminarTurnoLugar(getLugarTurnoElsita().getLugTur());
			getListaLugarTurno().clear();
			getListaLugarTurno().addAll(managergest.LugarTurnoById1(lug_id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
