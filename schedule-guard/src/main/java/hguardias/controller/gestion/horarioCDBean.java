package hguardias.controller.gestion;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
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

	// Cambio turno
	private Date cambio_fecha;
	private List<HgGuardia> listaguardiasXFecha;
	private HgGuardia guardia1;
	private HgGuardia guardia2;
	private String guardiaId1;
	private String guardiaId2;
	private String nombreguardia1;
	private String apellidoguardia1;
	private String nombreguardia2;
	private String apellidoguardia2;

	private List<SelectItem> lstGuardias;
	
	private HgHorarioCab hcabElsita;

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
		listaguardiasXFecha = new ArrayList<HgGuardia>();
		guardia1 = new HgGuardia();
		guardia2 = new HgGuardia();
		cambio_fecha = null;
		guardiaId1 = null;
		guardiaId2 = null;
		nombreguardia1 = "";
		apellidoguardia1 = "";
		nombreguardia2 = "";
		apellidoguardia2 = "";
		lstGuardias = new ArrayList<SelectItem>();
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

	public Date getCambio_fecha() {
		return cambio_fecha;
	}

	public void setCambio_fecha(Date cambio_fecha) {
		this.cambio_fecha = cambio_fecha;
	}

	public List<HgGuardia> getListaguardiasXFecha() {
		return listaguardiasXFecha;
	}

	public void setListaguardiasXFecha(List<HgGuardia> listaguardiasXFecha) {
		this.listaguardiasXFecha = listaguardiasXFecha;
	}

	public String getGuardiaId1() {
		return guardiaId1;
	}

	public String getGuardiaId2() {
		return guardiaId2;
	}

	public void setGuardiaId1(String guardiaId1) {
		this.guardiaId1 = guardiaId1;
	}

	public void setGuardiaId2(String guardiaId2) {
		this.guardiaId2 = guardiaId2;
	}

	public String getNombreguardia1() {
		return nombreguardia1;
	}

	public String getApellidoguardia1() {
		return apellidoguardia1;
	}

	public String getApellidoguardia2() {
		return apellidoguardia2;
	}

	public String getNombreguardia2() {
		return nombreguardia2;
	}

	public void setNombreguardia1(String nombreguardia1) {
		this.nombreguardia1 = nombreguardia1;
	}

	public void setNombreguardia2(String nombreguardia2) {
		this.nombreguardia2 = nombreguardia2;
	}

	public void setApellidoguardia1(String apellidoguardia1) {
		this.apellidoguardia1 = apellidoguardia1;
	}

	public void setApellidoguardia2(String apellidoguardia2) {
		this.apellidoguardia2 = apellidoguardia2;
	}

	public List<SelectItem> getLstGuardias() {
		return lstGuardias;
	}

	public void setLstGuardias(List<SelectItem> lstGuardias) {
		this.lstGuardias = lstGuardias;
	}
	
	public HgHorarioCab getHcabElsita() {
		return hcabElsita;
	}
	
	public void setHcabElsita(HgHorarioCab hcabElsita) {
		this.hcabElsita = hcabElsita;
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
			sqlfechai = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(horcab_fechainicio));
			sqlfechaf = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(horcab_fechafin));
			horcab_fecha_creacion = new Timestamp(fecha.getTime());
			horcab_usuarioreg = usuariologeado.trim();
			managerhorario.insertarHorarioCab(cab_id, sqlfechai, sqlfechaf,horcab_nombre.trim(), horcab_usuarioreg,horcab_fecha_creacion);
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
		List<HgLugare> lugares = managergest.findAllLugaresActivos();
		try {
			Date fechainicial = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(sqlfechai));
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
								if (guardiaAlmacenar.getGuaCedula() == null && (lugar.getLugCctv()== true || lugar.getLugControlAccesos()== true)) {
									guardiaAlmacenar = obtenerGuardiaSG(lugar,fechainicial, lugarTurno.getHgTurno(),numeroDia);
								}
								if (guardiaAlmacenar.getGuaCedula() == null) {
									guardia = false;
									managerhorario.insertarLugarTurnoVacio(cab_id, lugarTurno.getHgTurno(),lugar, fechainicial);
								}
								if (guardia) {
									if (numeroDia != 26) {
										if (managerhorario.guardiaPendienteByID(guardiaAlmacenar.getGuaCedula()) != null) {
											almacenarDetalles(guardiaAlmacenar,lugar,lugarTurno.getHgTurno(),fechainicial, null, cab_id);
											managerhorario.EliminarGuardiaPendienteLibre(guardiaAlmacenar);
										} else {
											almacenarDetalles(guardiaAlmacenar,lugar,lugarTurno.getHgTurno(),fechainicial, null, cab_id);
										}
									} else {
										if(managerhorario.guardiaByIDPendiente(guardiaAlmacenar.getGuaCedula()) == null){
											managerhorario.insertarGuardiaPendienteLibre(guardiaAlmacenar);
											almacenarDetalles(guardiaAlmacenar,lugar, lugarTurno.getHgTurno(),fechainicial, null, cab_id);
										}
									}
								}
							}
						}
					}
				}
				fechainicial = addDays(fechainicial);
				fechainicial = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(fechainicial));
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

	private HgGuardia obtenerGuardia(HgLugare lugar, Date fecha, HgTurno turno,
			Integer numeroDias) {
		HgGuardia guardiaelegido = new HgGuardia();
		HgGuardiasPendiente guardiaelegidoPendiente = new HgGuardiasPendiente();
		List<HgGuardia> guardiasDisponibles = new ArrayList<HgGuardia>();
		List<HgGuardiasPendiente> guardiasDisponiblesPendientes = new ArrayList<HgGuardiasPendiente>();
		
//		if (guardiaelegido.getGuaCedula() == null) {
//			guardiasDisponibles = managergest.findGuardias7Dias(fecha);
//			guardiaelegido = obtenerGuardiaCompatible(lugar, fecha, turno,guardiasDisponibles, numeroDias);
//		}
		if (numeroDias % 3 != 0) {
			guardiasDisponibles = managergest.findGuardiasDisponibles(fecha);
			guardiaelegido = obtenerGuardiaCompatible(lugar, fecha, turno,guardiasDisponibles, numeroDias);
		}
		if (guardiaelegido.getGuaCedula() == null) {
			guardiasDisponibles = managergest.findGuardiasDisponiblesSinLibres(fecha);
			guardiaelegido = obtenerGuardiaCompatible(lugar, fecha, turno,guardiasDisponibles, numeroDias);
		}
		if (guardiaelegido.getGuaCedula() == null) {
			guardiasDisponibles = managergest.findGuardiasUltimosDosDiasLibres(fecha);
			guardiaelegido = obtenerGuardiaCompatible(lugar, fecha, turno,guardiasDisponibles, numeroDias);
		}
		if (guardiaelegido.getGuaCedula() == null) {
			guardiasDisponiblesPendientes = managerhorario.findAllGuardiasPendientes();
			// setear
			guardiaelegidoPendiente = obtenerGuardiaCompatiblePendiente(lugar,fecha, turno, guardiasDisponiblesPendientes, numeroDias);
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
//						if(managerhorario.trabajoSemanaAnteriorLugar(fechainicial, guardia.getGuaCedula(), lugar.getLugId()) == 0 )
//							guardiaAplica = false;
//						if(managerhorario.trabajoLugDiaAnterior(guardia,lugar,fechainicial)==1)
//							guardiaAplica = false;
//						if(managerhorario.trabajoLugDiaAnterior(guardia,lugar,restDays(fechainicial))==1)
//							guardiaAplica = false;
//						if(managerhorario.trabajoLugDiaAnterior(guardia,lugar,restDays(restDays(fechainicial)))==1)
//							guardiaAplica = false;
//						if(managerhorario.trabajoLugDiaAnterior(guardia,lugar,restDays(restDays(restDays(fechainicial))))==1)
//							guardiaAplica = false;
						if (managerhorario.existeGuardiaXturnoMNoc(cab_id,restDays(fechainicial), guardia.getGuaCedula()) == 1&& turno.getTurId() == 1)
							guardiaAplica = false;
						if (lugar.getLugCctv() == true && guardia.getGuaCctv() != true)
							guardiaAplica = false;
						if (lugar.getLugControlAccesos() == true && guardia.getGuaControlAccesos() != true)
							guardiaAplica = false;
						if (guardia.getGuaCasoEstudio() == true && (fechainicial.getDay() == 0 || fechainicial.getDay() == 6))
							guardiaAplica = false;
						if (guardia.getGuaCasoNocturno() == true&& turno.getTurId() == 3)
							guardiaAplica = false;
						if ((lugar.getLugNombre() == "Instituto"|| lugar.getLugNombre() == "Centro de emprendimiento"
								|| lugar.getLugNombre() == "San Eloy"|| lugar.getLugNombre() == "CCTV"
								|| lugar.getLugNombre() == "Tanques de Agua" || lugar.getLugNombre() == "Control 1")
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
	
	private HgGuardia obtenerGuardiaSG(HgLugare lugar, Date fecha, HgTurno turno,
			Integer numeroDias) {
		HgGuardia guardiaelegido = new HgGuardia();
		HgGuardiasPendiente guardiaelegidoPendiente = new HgGuardiasPendiente();
		List<HgGuardia> guardiasDisponibles = new ArrayList<HgGuardia>();
		List<HgGuardiasPendiente> guardiasDisponiblesPendientes = new ArrayList<HgGuardiasPendiente>();
		
		if (numeroDias % 3 != 0) {
			guardiasDisponibles = managergest.findGuardiasDisponibles(fecha);
			guardiaelegido = obtenerGuardiaCompatibleSG(lugar, fecha, turno,guardiasDisponibles, numeroDias);
		}
		if (guardiaelegido.getGuaCedula() == null) {
			guardiasDisponibles = managergest.findGuardiasDisponiblesSinLibres(fecha);
			guardiaelegido = obtenerGuardiaCompatibleSG(lugar, fecha, turno,guardiasDisponibles, numeroDias);
		}
		if (guardiaelegido.getGuaCedula() == null) {
			guardiasDisponibles = managergest.findGuardiasUltimosDosDiasLibres(fecha);
			guardiaelegido = obtenerGuardiaCompatibleSG(lugar, fecha, turno,guardiasDisponibles, numeroDias);
		}
		if (guardiaelegido.getGuaCedula() == null) {
			guardiasDisponiblesPendientes = managerhorario.findAllGuardiasPendientes();
			// setear
			guardiaelegidoPendiente = obtenerGuardiaCompatiblePendiente(lugar,fecha, turno, guardiasDisponiblesPendientes, numeroDias);
			guardiaelegido = setearHgGuardia(guardiaelegidoPendiente);
		}
		return guardiaelegido;
	}
	
	@SuppressWarnings("deprecation")
	private HgGuardia obtenerGuardiaCompatibleSG(HgLugare lugar,
			Date fechainicial, HgTurno turno,
			List<HgGuardia> guardiasDisponibles, Integer numeroDias) {
		HgGuardia guardiaelegido = new HgGuardia();
		for (HgGuardia guardia : guardiasDisponibles) {
			Boolean guardiaAplica = true;
			Integer vecestrabajo = 0;
			Integer diasTrabajados = 5;
			if (managerhorario.existeAusencia(guardia.getGuaCedula(),
					fechainicial) == 0) {
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
						if (managerhorario.existeGuardiaXturnoMNoc(cab_id,restDays(fechainicial), guardia.getGuaCedula()) == 1&& turno.getTurId() == 1)
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
	 * Metodo para saber que dias se puede trabajar dependiendo del boolean y el
	 * dia
	 * 
	 * @param lugar
	 * @param fechainicial
	 * @return
	 * @throws ParseException 
	 */
	@SuppressWarnings("deprecation")
	public boolean averiguarDia(HgLugare lugar, Date fechainicial) throws ParseException {
		boolean resultado = true;
		SimpleDateFormat df = new SimpleDateFormat( "dd/MM/yy" );
		//parse in the date
		java.util.Date date = fechainicial;
		//change the pattern to output the day of week
		df.applyPattern( "EEE" );
		//print the formatted date out
		System.out.println( "Day of Week = " + df.format( date ) );
		
		if (lugar.getLugDomingo() == false && df.format(date).equals("dom")) {
			resultado = false;
		}
		if (lugar.getLugLunes() == false && df.format(date).equals("lun")) {
			resultado = false;
		}
		if (lugar.getLugMartes() == false && df.format(date).equals("mar")) {
			resultado = false;
		}
		if (lugar.getLugMiercoles() == false && df.format(date).equals("mié")) {
			resultado = false;
		}
		if (lugar.getLugJueves() == false && df.format(date).equals("jue")) {
			resultado = false;
		}
		if (lugar.getLugViernes() == false && df.format(date).equals("vie")) {
			resultado = false;
		}
		if (lugar.getLugSabado() == false && df.format(date).equals("sáb")) {
			resultado = false;
		}
		return resultado;
	}
	
	public Integer diasXFi_Ff() {
		Long diff = sqlfechaf.getTime() - sqlfechai.getTime();
		long dias = diff / (1000 * 60 * 60 * 24);
		return (int) dias;
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
	 * accion para abrir el dialogo
	 * 
	 */
	public void abrirDialog2() {
		RequestContext.getCurrentInstance()
				.execute("PF('cambioturno').show();");
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
	 * eliminar lugturno abriendo el dialogo
	 * 
	 * @param pro_id
	 * @throws Exception
	 */
	public void abrirDialogHorarioEliminar(HgHorarioCab item) {
		setHcabElsita(item);
		RequestContext.getCurrentInstance().execute("PF('ef').show();");
	}
	
	/**
	 * eliminar un fotoproducto
	 * 
	 * @param pro_id
	 * @throws Exception
	 */
	public String eliminarHorarioCab() {
		try {
			managerhorario.eliminarHorarioCab(hcabElsita.getHcabId());
			getlistaHorarioCab().clear();
			getlistaHorarioCab().addAll(managerhorario.findAllHorariosCab());
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

	// cambio de turno
	public void obtenerGuardiaXFecha() {
		Date finicial = java.sql.Date
				.valueOf(new SimpleDateFormat("yyyy-MM-dd")
						.format(cambio_fecha));
		cargarGuardias(finicial);
	}

	public void cargarGuardias(Date finicial) {
		getLstGuardias().clear();
		getLstGuardias().add(new SelectItem("", " --Seleccione el guardia-- "));
		listaguardiasXFecha = managergest.findGuardiasXFecha(finicial);
		if (!listaguardiasXFecha.isEmpty()) {
			for (HgGuardia i : listaguardiasXFecha) {
				getLstGuardias().add(
						new SelectItem(i.getGuaCedula(), i.getGuaCedula()
								+ " | " + i.getGuaApellido() + " "
								+ i.getGuaNombre()));
			}
		} else {
			Mensaje.crearMensajeWARN("Escoja otra fecha, en esta no encuentra registros");
		}
	}

	/**
	 * Mestodo q carga datos del guardia
	 * 
	 * @return guardia
	 */
	public void mostrara() {
		HgGuardia gua;
		try {
			nombreguardia1 = "";
			apellidoguardia1 = "";
			gua = managergest.guardiaByID(guardiaId1);
			nombreguardia1 = gua.getGuaNombre();
			apellidoguardia1 = gua.getGuaApellido();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Mestodo q carga datos del guardia
	 * 
	 * @return guardia
	 */
	public void mostrarb() {
		HgGuardia gua;
		try {
			nombreguardia2 = "";
			apellidoguardia2 = "";
			gua = managergest.guardiaByID(guardiaId2);
			nombreguardia2 = gua.getGuaNombre();
			apellidoguardia2 = gua.getGuaApellido();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * metodo para asignar el guardia
	 * 
	 */
	public String asignarGuardia1() {
		guardia1 = managergest.asignarGuardia(guardiaId1);
		return "";
	}

	/**
	 * metodo para asignar el guardia
	 * 
	 */
	public String asignarGuardia2() {
		guardia2 = managergest.asignarGuardia(guardiaId2);
		return "";
	}

	/**
	 * accion para abrir el dialogo
	 * 
	 */
	public void abrirDialogConfirm() {
		RequestContext.getCurrentInstance().execute("PF('cambio').show();");
	}

	/**
	 * limpia la informacion de ausencia
	 * 
	 * @return
	 * @throws Exception
	 */
	public String volverCambio() throws Exception {
		cambio_fecha = null;
		guardiaId1 = null;
		guardiaId2 = null;
		nombreguardia1 = "";
		apellidoguardia1 = "";
		nombreguardia2 = "";
		apellidoguardia2 = "";
		listaguardiasXFecha.clear();
		return "hg_nhorario?faces-redirect=true";
	}

	public String cambioGuardias() {
		try {
			HgGuardia guardiaID1 = null;
			HgGuardia guardiaID2 = null;
			List<HgHorarioDet> horarioDetalleGuardia1 = managerhorario
					.horarioDetByCedulaFecha(guardiaId1, cambio_fecha);
			List<HgHorarioDet> horarioDetalleGuardia2 = managerhorario
					.horarioDetByCedulaFecha(guardiaId2, cambio_fecha);

			for (HgHorarioDet hordet1 : horarioDetalleGuardia1) {
				for (HgHorarioDet hordet2 : horarioDetalleGuardia2) {
					if (hordet1.getHgTurno().getTurId() == 1
							|| hordet2.getHgTurno().getTurId() == 1) {
						if ((managerhorario.existeGuardiaXturnoMNoc(hordet2
								.getHgHorarioCab().getHcabId(),
								restDays(cambio_fecha), guardiaId1) == 1)
								|| managerhorario.existeGuardiaXturnoMNoc(
										hordet1.getHgHorarioCab().getHcabId(),
										restDays(cambio_fecha), guardiaId2) == 1) {
							Mensaje.crearMensajeWARN("El día anterior el guardia trabaja en turno nocturno, no se puede realizar esta acción");
						} else {
							guardiaID1 = guardia1;
							guardiaID2 = guardia2;
							hordet2.setHgGuardia(guardiaID1);
							hordet1.setHgGuardia(guardiaID2);
							managerhorario.editarGuardiasDetalle(hordet1);
							managerhorario.editarGuardiasDetalle(hordet2);
							Mensaje.crearMensajeINFO("El cambio se realizó correctamente");
						}
					} else {
						guardiaID1 = guardia1;
						guardiaID2 = guardia2;
						hordet2.setHgGuardia(guardiaID1);
						hordet1.setHgGuardia(guardiaID2);
						managerhorario.editarGuardiasDetalle(hordet1);
						managerhorario.editarGuardiasDetalle(hordet2);
						Mensaje.crearMensajeINFO("El cambio se realizó correctamente");
						RequestContext.getCurrentInstance().execute(
								"PF('cambio').hide();");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		getListaHorarioDet().clear();
		getListaHorarioDet().addAll(
				managerhorario.findAllHorariosDetXIdCab(cab_id));
		return "";
	}
}
