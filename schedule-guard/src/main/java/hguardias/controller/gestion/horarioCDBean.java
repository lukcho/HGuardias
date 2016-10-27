package hguardias.controller.gestion;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;

import hguardias.model.manager.ManagerBuscar;
import hguardias.model.manager.ManagerHorario;
import hguardias.model.manager.ManagerCarga;
import hguardias.controller.access.SesionBean;
import hguardias.model.generic.Funciones;
import hguardias.model.dao.entities.HgGuardia;
import hguardias.model.dao.entities.HgGuardiasPendiente;
import hguardias.model.dao.entities.HgHorarioCab;
import hguardias.model.dao.entities.HgHorarioDet;
import hguardias.model.dao.entities.HgLugarTurno;
import hguardias.model.dao.entities.HgLugare;
import hguardias.model.dao.entities.HgLugaresTurnosVacio;
import hguardias.model.dao.entities.HgTurno;
import hguardias.model.dao.entidades.HorarioDet;
import hguardias.model.dao.entities.Persona;
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

	@EJB
	private algoritmoGenetico ag;

	// horariocab
	private Integer horcab_id;
	private String horcab_nombre;
	private String horcab_usuarioreg;
	private Date horcab_fechainicio;
	private Date horcab_fechafin;
	private Timestamp horcab_fecha_creacion;
	private Integer cab_id;

	private Date fecha;

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

	// fechas tipo sql
	private java.sql.Date sqlfechai;
	private java.sql.Date sqlfechaf;

	private List<HgHorarioCab> listaHorarioCab;
	private List<HgHorarioDet> listaHorarioDet;
	private List<HorarioDet> listaSinGuardias;
	private List<HgLugaresTurnosVacio> listaLugarTurnoVacio;

	private Integer numeroRegistrosTotal;
	private Integer numeroLugaresVacios;
	private Integer numeroRegistrosCreados;

	@Inject
	SesionBean ms;

	public horarioCDBean() {
	}

	@PostConstruct
	public void ini() {
		usuario = ms.validarSesion("hg_horarios.xhtml");
		BuscarPersona();
		horcab_id = null;
		horcab_fechainicio = null;
		horcab_fechafin = null;
		sqlfechai = null;
		sqlfechaf = null;
		cab_id = null;
		horcab_fecha_creacion = null;
		horcab_nombre = null;
		horcab_usuarioreg = null;
		edicion = false;
		ediciontipo = false;
		usuariologeado = "";
		listaHorarioCab = managerhorario.findAllHorariosCab();
		listaHorarioDet = managerhorario.findAllHorariosDet();
		listaLugarTurnoVacio = new ArrayList<HgLugaresTurnosVacio>();
		listaSinGuardias = new ArrayList<HorarioDet>();
		numeroLugaresVacios = 0;
		numeroRegistrosCreados = 0;
		numeroRegistrosTotal = 0;
	}

	public java.sql.Date getSqlfechai() {
		return sqlfechai;
	}

	public void setSqlfechai(java.sql.Date sqlfechai) {
		this.sqlfechai = sqlfechai;
	}

	public java.sql.Date getSqlfechaf() {
		return sqlfechaf;
	}

	public Integer getCab_id() {
		return cab_id;
	}

	public void setCab_id(Integer cab_id) {
		this.cab_id = cab_id;
	}

	public void setSqlfechaf(java.sql.Date sqlfechaf) {
		this.sqlfechaf = sqlfechaf;
	}

	public String getCedula() {
		return cedula;
	}

	public Timestamp getHorcab_fecha_creacion() {
		return horcab_fecha_creacion;
	}

	public void setHorcab_fecha_creacion(Timestamp horcab_fecha_creacion) {
		this.horcab_fecha_creacion = horcab_fecha_creacion;
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

	public List<HgHorarioDet> getListaHorarioDet() {
		return listaHorarioDet;
	}

	public void setListaHorarioDet(List<HgHorarioDet> listaHorarioDet) {
		this.listaHorarioDet = listaHorarioDet;
	}

	public List<HgLugaresTurnosVacio> getListaLugarTurnoVacio() {
		return listaLugarTurnoVacio;
	}

	public void setListaLugarTurnoVacio(
			List<HgLugaresTurnosVacio> listaLugarTurnoVacio) {
		this.listaLugarTurnoVacio = listaLugarTurnoVacio;
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<HorarioDet> getListaSinGuardias() {
		return listaSinGuardias;
	}

	public void setListaSinGuardias(List<HorarioDet> listaSinGuardias) {
		this.listaSinGuardias = listaSinGuardias;
	}

	public Integer getNumeroLugaresVacios() {
		return numeroLugaresVacios;
	}

	public void setNumeroLugaresVacios(Integer numeroLugaresVacios) {
		this.numeroLugaresVacios = numeroLugaresVacios;
	}

	public Integer getNumeroRegistrosCreadoss() {
		return numeroRegistrosCreados;
	}

	public void setNumeroRegistrosCreadoss(Integer numeroRegistrosCreadoss) {
		this.numeroRegistrosCreados = numeroRegistrosCreadoss;
	}

	public Integer getNumeroRegistrosTotal() {
		return numeroRegistrosTotal;
	}

	public void setNumeroRegistrosTotal(Integer numeroRegistrosTotal) {
		this.numeroRegistrosTotal = numeroRegistrosTotal;
	}

	// horariocab
	/**
	 * accion para invocar el manager y crear horario
	 * 
	 * @param horcab_id
	 * @param sqlfechai
	 * @param sqlfechaf
	 * @param horcab_nombre
	 * @throws Exception
	 */
	public void crearHorarioCab() {
		try {
			fecha = new Date();
			sqlfechai = java.sql.Date
					.valueOf(new SimpleDateFormat("yyyy-MM-dd")
							.format(horcab_fechainicio));
			sqlfechaf = java.sql.Date
					.valueOf(new SimpleDateFormat("yyyy-MM-dd")
							.format(horcab_fechafin));
			horcab_fecha_creacion = new Timestamp(fecha.getTime());
			horcab_usuarioreg = usuariologeado.trim();
			managerhorario.insertarHorarioCab(cab_id, sqlfechai, sqlfechaf,
					horcab_nombre.trim(), horcab_usuarioreg,
					horcab_fecha_creacion);
			if (!managergest.findAllGuardias().isEmpty()) {
				generacionFechaLugarTurnoGuardia(cab_id);
				Mensaje.crearMensajeINFO("Se creó satisfactoriamente");
			} else {
				Mensaje.crearMensajeWARN("Error detalle vacio revisar guardias");
			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al crear horario");
			e.printStackTrace();
		}
	}

	public void generacionFechaLugarTurnoGuardia(Integer cab_id) {
		List<HgLugare> lugares = managergest.findAllLugares();
		try {
			Date fechainicial = java.sql.Date.valueOf(new SimpleDateFormat(
					"yyyy-MM-dd").format(sqlfechai));
			Date fechafinal = java.sql.Date.valueOf(new SimpleDateFormat(
					"yyyy-MM-dd").format(sqlfechaf));
			Integer dias = diasXFi_Ff();
			for (Integer numeroDia = 0; numeroDia <= dias; numeroDia++) {
				for (HgLugare lugar : lugares) {
					if (averiguarDia(lugar, fechainicial) == true) {
						List<HgLugarTurno> lugarTurnos = managergest.findLugarByIdLugar(lugar.getLugId());
						for (HgLugarTurno lugarTurno : lugarTurnos) {
							for (Integer numeroGuardias = 1; numeroGuardias <= lugarTurno.getLugTurNumeroGuardias(); numeroGuardias++) {
								HgGuardia guardiaAlmacenar = new HgGuardia();
								boolean guardia = true;
								guardiaAlmacenar = obtenerGuardia(lugar,fechainicial, lugarTurno.getHgTurno(),numeroDia);
								if (guardiaAlmacenar.getGuaCedula() == null) {
									guardia = false;
									managerhorario.insertarLugarTurnoVacio(cab_id, lugarTurno.getHgTurno(),lugar, fechainicial);
								}
								if (guardia) {
									if (numeroDia != 26) {
										if (managerhorario
												.guardiaPendienteByID(guardiaAlmacenar.getGuaCedula()) != null) {
											almacenarDetalles(guardiaAlmacenar,lugar,
													lugarTurno.getHgTurno(),fechainicial, fechainicial,cab_id);
											managerhorario
													.EliminarGuardiaPendienteLibre(guardiaAlmacenar);
										} else {
											almacenarDetalles(guardiaAlmacenar,
													lugar,
													lugarTurno.getHgTurno(),
													fechainicial, fechainicial,
													cab_id);
										}
									} else {
										almacenarDetalles(guardiaAlmacenar,
												lugar, lugarTurno.getHgTurno(),
												fechainicial, fechainicial,
												cab_id);
										managerhorario
												.insertarGuardiaPendienteLibre(guardiaAlmacenar);
									}
								}
							}
						}
					}
				}
				fechainicial = addDays(fechainicial);
				fechainicial = java.sql.Date.valueOf(new SimpleDateFormat(
						"yyyy-MM-dd").format(fechainicial));
			}
			numeroRegistrosCreados = managerhorario.findAllHorariosDetXIdCab(
					cab_id).size();
			numeroLugaresVacios = managerhorario.lugarTurnoVacio(cab_id).size();
			numeroRegistrosTotal = numeroRegistrosCreados + numeroLugaresVacios;
			managerhorario.editarCabecera(cab_id, numeroRegistrosTotal,
					numeroRegistrosCreados, numeroLugaresVacios);
			listaLugarTurnoVacio = managergest.allLugarTurnoByID(cab_id);
			horcab_fechainicio = java.sql.Date.valueOf(new SimpleDateFormat(
					"yyyy-MM-dd").format(sqlfechai));
			horcab_fechafin = java.sql.Date.valueOf(new SimpleDateFormat(
					"yyyy-MM-dd").format(sqlfechaf));
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error en la creación");
		}
	}

	/**
	 * Metodo para saber que dias se puede trabajar dependiendo del boolean y el
	 * dia
	 * 
	 * @param lugar
	 * @param fechainicial
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public boolean averiguarDia(HgLugare lugar, Date fechainicial) {
		boolean resultado = true;
		if (lugar.getLugLunes() == false && fechainicial.getDay() == 1) {
			resultado = false;
		}
		if (lugar.getLugMartes() == false && fechainicial.getDay() == 2) {
			resultado = false;
		}
		if (lugar.getLugMiercoles() == false && fechainicial.getDay() == 3) {
			resultado = false;
		}
		if (lugar.getLugJueves() == false && fechainicial.getDay() == 4) {
			resultado = false;
		}
		if (lugar.getLugViernes() == false && fechainicial.getDay() == 5) {
			resultado = false;
		}
		if (lugar.getLugSabado() == false && fechainicial.getDay() == 6) {
			resultado = false;
		}
		if (lugar.getLugDomingo() == false && fechainicial.getDay() == 0) {
			resultado = false;
		}
		return resultado;
	}

	private HgGuardia obtenerGuardia(HgLugare lugar, Date fecha, HgTurno turno,
			Integer numeroDias) {
		HgGuardia guardiaelegido = new HgGuardia();
		HgGuardiasPendiente guardiaelegidoPendiente = new HgGuardiasPendiente();
		List<HgGuardia> guardiasDisponibles = new ArrayList<HgGuardia>();
		List<HgGuardiasPendiente> guardiasDisponiblesPendientes = new ArrayList<HgGuardiasPendiente>();
		if (numeroDias % 3 != 0) {
			guardiasDisponibles = managergest.findGuardiasDisponibles(fecha);
			guardiaelegido = obtenerGuardiaCompatible(lugar, fecha, turno,
					guardiasDisponibles, numeroDias);
		}
		if (guardiaelegido.getGuaCedula() == null) {
			guardiasDisponibles = managergest
					.findGuardiasDisponiblesSinLibres(fecha);
			guardiaelegido = obtenerGuardiaCompatible(lugar, fecha, turno,
					guardiasDisponibles, numeroDias);
		}
		if (guardiaelegido.getGuaCedula() == null) {
			guardiasDisponibles = managergest
					.findGuardiasUltimosDosDiasLibres(fecha);
			guardiaelegido = obtenerGuardiaCompatible(lugar, fecha, turno,
					guardiasDisponibles, numeroDias);
		}
		if (guardiaelegido.getGuaCedula() == null) {
			guardiasDisponiblesPendientes = managerhorario
					.findAllGuardiasPendientes();
			// setear
			guardiaelegidoPendiente = obtenerGuardiaCompatiblePendiente(lugar,
					fecha, turno, guardiasDisponiblesPendientes, numeroDias);
			guardiaelegido = setearHgGuardia(guardiaelegidoPendiente);
		}
		return guardiaelegido;
	}

	@SuppressWarnings("deprecation")
	private HgGuardia obtenerGuardiaCompatible(HgLugare lugar,
			Date fechainicial, HgTurno turno,
			List<HgGuardia> guardiasDisponibles, Integer numeroDias) {
		HgGuardia guardiaelegido = new HgGuardia();
		for (HgGuardia guardia : guardiasDisponibles) {
			Boolean guardiaAplica = true;
			Integer vecestrabajo = 0;
			Integer diasTrabajados = 5;
			if (managerhorario.existeAusencia(guardia.getGuaCedula(),
					fechainicial) == 0) {
				// if(managerhorario.trabajoLugTurnDiaAnterior(guardia,turno,restDays(fechainicial))==
				// 0){
				if ((managerhorario.existeGuardia(cab_id, fechainicial,
						guardia.getGuaCedula()) != 1)) {
					if (managerhorario.trabajoDiaAnterior(guardia,
							restDays(fechainicial)) == 1) {
						vecestrabajo = managerhorario.findNumDiasxGuardia(
								guardia, restDays(fechainicial),
								rest5Days(restDays(fechainicial)));
					} else if (managerhorario.trabajoDiaAnterior(guardia,
							restDays(restDays(fechainicial))) == 1) {
						vecestrabajo = managerhorario.findNumDiasxGuardia(
								guardia, restDays(restDays(fechainicial)),
								rest5Days(restDays(restDays(fechainicial))));
					}
					if (vecestrabajo < diasTrabajados) {
						if (managerhorario.existeGuardiaXturnoMNoc(cab_id,
								restDays(fechainicial), guardia.getGuaCedula()) == 1
								&& turno.getTurId() == 1)
							guardiaAplica = false;
						if (lugar.getLugCctv() == true
								&& guardia.getGuaCctv() != true)
							guardiaAplica = false;
						if (lugar.getLugControlAccesos() == true
								&& guardia.getGuaControlAccesos() != true)
							guardiaAplica = false;
						if (guardia.getGuaCasoEstudio() == true
								&& (fechainicial.getDay() == 0 || fechainicial
										.getDay() == 6))
							guardiaAplica = false;
						if (guardia.getGuaCasoNocturno() == true
								&& turno.getTurId() == 3)
							guardiaAplica = false;
						if ((lugar.getLugNombre() == "Instituto"
								|| lugar.getLugNombre() == "Centro de emprendimiento"
								|| lugar.getLugNombre() == "San Eloy"
								|| lugar.getLugNombre() == "CCTV"
								|| lugar.getLugNombre() == "Tanques de Agua" || lugar
								.getLugNombre() == "Control 1")
								&& guardia.getGuaMotorizado() == true)
							guardiaAplica = false;
						if (guardiaAplica) {
							guardiaelegido = guardia;
							break;
						}
					}
				}
			}
		}
		return guardiaelegido;
	}

	@SuppressWarnings("deprecation")
	private HgGuardiasPendiente obtenerGuardiaCompatiblePendiente(
			HgLugare lugar, Date fechainicial, HgTurno turno,
			List<HgGuardiasPendiente> guardiasDisponiblesPendientes,
			Integer numeroDias) {
		HgGuardiasPendiente guardiaelegido = new HgGuardiasPendiente();
		for (HgGuardiasPendiente guardia : guardiasDisponiblesPendientes) {
			Boolean guardiaAplica = true;
			Integer vecestrabajo = 0;
			Integer diasTrabajados = 6;
			// if(managerhorario.trabajoLugTurnDiaAnterior(guardia,turno,restDays(fechainicial))==
			// 0){
			if ((managerhorario.existeGuardia(cab_id, fechainicial,
					guardia.getGuaCedula()) != 1)) {
				if (managerhorario.trabajoDiaAnteriorPendiente(guardia,
						restDays(fechainicial)) == 1) {
					vecestrabajo = managerhorario.findNumDiasxGuardiaPendiente(
							guardia, restDays(fechainicial),
							rest5Days(restDays(fechainicial)));
				} else if (managerhorario.trabajoDiaAnteriorPendiente(guardia,
						restDays(restDays(fechainicial))) == 1) {
					vecestrabajo = managerhorario.findNumDiasxGuardiaPendiente(
							guardia, restDays(restDays(fechainicial)),
							rest5Days(restDays(restDays(fechainicial))));
				}
				if (vecestrabajo < diasTrabajados) {
					if (managerhorario.existeGuardiaXturnoMNoc(cab_id,
							restDays(fechainicial), guardia.getGuaCedula()) == 1
							&& turno.getTurId() == 1)
						guardiaAplica = false;
					if (lugar.getLugCctv() == true
							&& guardia.getGuaCctv() != true)
						guardiaAplica = false;
					if (lugar.getLugControlAccesos() == true
							&& guardia.getGuaControlAccesos() != true)
						guardiaAplica = false;
					if (guardia.getGuaCasoEstudio() == true
							&& (fechainicial.getDay() == 0 || fechainicial
									.getDay() == 6))
						guardiaAplica = false;
					if (guardia.getGuaCasoNocturno() == true
							&& turno.getTurId() == 3)
						guardiaAplica = false;
					if ((lugar.getLugNombre() == "Instituto"
							|| lugar.getLugNombre() == "Centro de emprendimiento"
							|| lugar.getLugNombre() == "San Eloy"
							|| lugar.getLugNombre() == "CCTV"
							|| lugar.getLugNombre() == "Tanques de Agua" || lugar
							.getLugNombre() == "Control 1")
							&& guardia.getGuaMotorizado() == true)
						guardiaAplica = false;
					if (guardiaAplica) {
						guardiaelegido = guardia;
						break;
					}
				}
			}
		}
		return guardiaelegido;
	}

	/**
	 * accion cerrar horario
	 * 
	 */
	public String cerrarHorarioCab() {
		numeroLugaresVacios = 0;
		numeroRegistrosCreados = 0;
		horcab_id = null;
		horcab_fechainicio = null;
		horcab_fechafin = null;
		horcab_nombre = null;
		numeroRegistrosTotal = 0;
		numeroRegistrosCreados = 0;
		numeroLugaresVacios = 0;
		horcab_usuarioreg = null;
		horcab_fecha_creacion = null;
		cab_id = null;
		date = null;
		getListaSinGuardias().clear();
		getlistaHorarioCab().clear();
		getlistaHorarioCab().addAll(managerhorario.findAllHorariosCab());
		return "hg_horarios?faces-redirect=true";
	}

	private HgGuardia setearHgGuardia(HgGuardiasPendiente guardiapendiente) {
		HgGuardia guardia = new HgGuardia();
		guardia.setGuaCedula(guardiapendiente.getGuaCedula());
		guardia.setGuaNombre(guardiapendiente.getGuaNombre());
		guardia.setGuaApellido(guardiapendiente.getGuaApellido());
		guardia.setGuaFechanac(guardiapendiente.getGuaFechanac());
		guardia.setGuaCiudad(guardiapendiente.getGuaCiudad());
		guardia.setGuaSexo(guardiapendiente.getGuaSexo());
		guardia.setGuaTelefono(guardiapendiente.getGuaTelefono());
		guardia.setGuaCelular(guardiapendiente.getGuaCelular());
		guardia.setGuaCorreo(guardiapendiente.getGuaCorreo());
		guardia.setGuaDireccion(guardiapendiente.getGuaDireccion());
		guardia.setGuaCctv(guardiapendiente.getGuaCctv());
		guardia.setGuaMotorizado(guardiapendiente.getGuaMotorizado());
		guardia.setGuaChofer(guardiapendiente.getGuaChofer());
		guardia.setGuaControlAccesos(guardiapendiente.getGuaControlAccesos());
		guardia.setGuaCasoTurno(guardiapendiente.getGuaCasoTurno());
		guardia.setGuaCasoEstudio(guardiapendiente.getGuaCasoEstudio());
		guardia.setGuaCasoNocturno(guardiapendiente.getGuaCasoNocturno());
		guardia.setGuaEstadoCivil(guardiapendiente.getGuaEstadoCivil());
		guardia.setGuaTipoSangre(guardiapendiente.getGuaEstadoCivil());
		guardia.setGuaEstado(guardiapendiente.getGuaEstado());
		return guardia;
	}

	private boolean sinCCTV(List<HgGuardia> hgGuardia) {
		boolean resultado = true;
		for (HgGuardia guardia : hgGuardia) {
			if (guardia.getGuaCctv() == false)
				resultado = false;
		}
		return resultado;
	}

	private boolean sinCCTVPendiente(
			List<HgGuardiasPendiente> hgGuardiasPendientes) {
		boolean resultado = true;
		for (HgGuardiasPendiente guardia : hgGuardiasPendientes) {
			if (guardia.getGuaCctv() == false)
				resultado = false;
		}
		return resultado;
	}

	private boolean sinControlAccesos(List<HgGuardia> hgGuardia) {
		boolean resultado = true;
		for (HgGuardia guardia : hgGuardia) {
			if (guardia.getGuaControlAccesos() == false)
				resultado = false;
		}
		return resultado;
	}

	private boolean sinControlAccesosPendiente(
			List<HgGuardiasPendiente> hgGuardia) {
		boolean resultado = true;
		for (HgGuardiasPendiente guardia : hgGuardia) {
			if (guardia.getGuaControlAccesos() == false)
				resultado = false;
		}
		return resultado;
	}

	public Integer diasXFi_Ff() {
		Long diff = sqlfechaf.getTime() - sqlfechai.getTime();
		return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}

	/**
	 * metodo para almacenar detalle
	 * 
	 */
	public HgHorarioDet almacenarDetalles(HgGuardia g, HgLugare l, HgTurno t,
			Date fechainicial1, Date fechaFinal, Integer cab_id) {
		HgHorarioDet hghorariodetalle = new HgHorarioDet();
		try {
			if (g.getGuaCasoTurno() != null) {
				managerhorario.insertarHorarioDet(fechainicial1, fechaFinal, g,
						l, t, managergest.turnoByID(g.getGuaCasoTurno())
								.getTurHoraInicio(),
						managergest.turnoByID(g.getGuaCasoTurno())
								.getTurHoraFin(), managerhorario
								.horarioCabByID(cab_id));
			} else {
				managerhorario.insertarHorarioDet(fechainicial1, fechaFinal, g,
						l, t, t.getTurHoraInicio(), t.getTurHoraFin(),
						managerhorario.horarioCabByID(cab_id));
			}
		} catch (Exception e) {
			e.printStackTrace();
			Mensaje.crearMensajeERROR("Error al almacenar detalle");
		}
		return hghorariodetalle;
	}

	/**
	 * Metodo para almacenar los guardias
	 * 
	 * @param g
	 * @param fechainicial1
	 * @param fechaFinal
	 * @param cab_id
	 * @return
	 */
	public HgHorarioDet almacenarDetallesLibre(HgGuardia g, Date fechainicial1,
			Date fechaFinal, Integer cab_id) {
		HgHorarioDet hghorariodetalle = new HgHorarioDet();
		try {
			HgTurno t = managergest.turnoByID(4);
			managerhorario.insertarHorarioDetLibre(fechainicial1, fechaFinal,
					g, t, t.getTurHoraInicio(), t.getTurHoraFin(),
					managerhorario.horarioCabByID(cab_id));
		} catch (Exception e) {
			e.printStackTrace();
			Mensaje.crearMensajeERROR("Error al almacenar detalle");
		}
		return hghorariodetalle;
	}

	/**
	 * Metodo para aumentar en un dia cada fecha
	 * 
	 * @param date
	 * @return
	 */
	public static Date addDays(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1); // minus number would decrement the days
		return cal.getTime();
	}

	/**
	 * Metodo para reducir en un dia cada fecha
	 * 
	 * @param date
	 * @return
	 */
	public static Date restDays(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1); // minus number would decrement the days
		return cal.getTime();
	}

	/**
	 * Metodo para reducir en 5 dias cada fecha
	 * 
	 * @param date
	 * @return
	 */
	public static Date rest5Days(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -5); // minus number would decrement the days
		return cal.getTime();
	}

	/**
	 * accion para abrir el dialogo
	 * 
	 */
	public void abrirDialog() {
		if (horcab_fechainicio.after(horcab_fechafin)) {
			Mensaje.crearMensajeWARN("Fecha inicio debe ser menor que la Fecha Fin");
		} else
			RequestContext.getCurrentInstance().execute("PF('gu').show();");
	}

	/**
	 * accion para abrir el dialogo
	 * 
	 */
	public void abrirDialog1() {
		getListaLugarTurnoVacio().clear();
		getListaLugarTurnoVacio().addAll(managergest.allLugarTurnoByID(cab_id));
		RequestContext.getCurrentInstance().execute("PF('info').show();");
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
			cab_id = horcab_id;
			horcab_fechainicio = (Date) horcab.getHcabFechaInicio();
			horcab_fechafin = (Date) horcab.getHcabFechaFin();
			horcab_nombre = horcab.getHcabNombre();
			horcab_usuarioreg = horcab.getHcabUsuario();
			numeroRegistrosTotal = horcab.getHcabNumeroRegistrosTotal();
			numeroRegistrosCreados = horcab.getHcabNumeroRegistrosCreados();
			numeroLugaresVacios = horcab.getHcabNumeroLugaresVacios();
			usuariologeado = horcab_usuarioreg;
			edicion = true;
			ediciontipo = false;
			getListaHorarioDet().clear();
			getListaHorarioDet().addAll(
					managerhorario.findAllHorariosDetXIdCab(cab_id));
			getListaLugarTurnoVacio().clear();
			getListaLugarTurnoVacio().addAll(
					managergest.allLugarTurnoByID(cab_id));
			return "hg_nhorario?faces-redirect=true";
		} catch (Exception e) {
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
		numeroRegistrosTotal = 0;
		numeroRegistrosCreados = 0;
		numeroLugaresVacios = 0;
		cab_id = managerhorario.ultimoOrdenCabecera();
		return "hg_nhorario?faces-redirect=true";
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
		horcab_usuarioreg = null;
		usuariologeado = null;
		numeroRegistrosTotal = 0;
		numeroRegistrosCreados = 0;
		numeroLugaresVacios = 0;
		cab_id = null;
		getlistaHorarioCab().clear();
		getlistaHorarioCab().addAll(managerhorario.findAllHorariosCab());
		return "hg_horarios?faces-redirect=true";
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
			e.printStackTrace();
		}
	}
}
