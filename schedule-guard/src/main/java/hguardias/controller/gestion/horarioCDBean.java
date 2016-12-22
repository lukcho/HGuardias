package hguardias.controller.gestion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import hguardias.model.manager.ManagerBuscar;
import hguardias.model.manager.ManagerHistorial;
import hguardias.model.manager.ManagerHorario;
import hguardias.model.manager.ManagerCarga;
import hguardias.controller.access.SesionBean;
import hguardias.model.generic.Funciones;
import hguardias.model.dao.entities.HgGuardia;
import hguardias.model.dao.entities.HgGuardiasDiasNoTrabajo;
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
	private ManagerHistorial managerhisto;

	@EJB
	private ManagerHorario managerhorario;

	@EJB
	private ManagerBuscar mb;

	private ManagerCarga manager;

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

	private ScheduleModel eventModel;
	private ScheduleEvent event = new DefaultScheduleEvent();
	private List<HgHorarioDet> listaHorarioDetByCabId;

	private String guardiaBuscar;
	private Integer lugarBuscar;

	// cambiolugarturno
	private Integer lugarTurnoVacioId;
	private String cedulaguardiaLugTurnVacio;
	private HgTurno turnoVacio;
	private String nombreLugarTurno;
	private HgLugare lugarVacio;
	private String turnoLugarTurno;
	private Date fechaLugarTurno;
	private String nombreguardiaLugTurnVacio;
	private String apellidoguardiaLugTurnVacio;
	private String guardiaLugTurnVacio;
	private HgGuardia guardiaLugarTurno;
	private List<HgHorarioDet> horarioDet;
	private Integer hcab_id;
	private String hcab_descripcion;
	private HgTurno hcab_turnoId;
	private String hcab_turno;
	private HgLugare hcab_lugarId;
	private String hcab_lugar;
	
	//atributo de direccion de url
	private String url_doc;

	@Inject
	SesionBean ms;

	public horarioCDBean() {
	}

	@PostConstruct
	public void ini() {
		usuario = ms.validarSesion("hg_horarios.xhtml");
		BuscarPersona();
		eventModel = new DefaultScheduleModel();
		listaHorarioCab = managerhorario.findAllHorariosCab();
		listaHorarioDet = managerhorario.findAllHorariosDet();
		listaLugarTurnoVacio = new ArrayList<HgLugaresTurnosVacio>();
		listaSinGuardias = new ArrayList<HorarioDet>();
		eventModel = new DefaultScheduleModel();
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
		nombreLugarTurno = null;
		turnoLugarTurno = null;
		fechaLugarTurno = null;
		nombreguardiaLugTurnVacio = null;
		apellidoguardiaLugTurnVacio = null;
		guardiaLugTurnVacio = null;
		hcab_turnoId = null;
		hcab_lugarId = null;
		lugarTurnoVacioId = null;
		lstGuardias = new ArrayList<SelectItem>();
		try {
			url_doc = manager.ParametroByID("direccion_doc");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getGuardiaBuscar() {
		return guardiaBuscar;
	}

	public Integer getLugarBuscar() {
		return lugarBuscar;
	}

	public java.sql.Date getSqlfechai() {
		return sqlfechai;
	}

	public java.sql.Date getSqlfechaf() {
		return sqlfechaf;
	}

	public Integer getCab_id() {
		return cab_id;
	}

	public String getCedula() {
		return cedula;
	}

	public Timestamp getHorcab_fecha_creacion() {
		return horcab_fecha_creacion;
	}

	public String getHorcab_usuarioreg() {
		return horcab_usuarioreg;
	}

	public Date getDate() {
		date = new Date();
		return date;
	}

	public String getUsuario() {
		return usuario;
	}

	public Date gethorcab_fechainicio() {
		return horcab_fechainicio;
	}

	public Date gethorcab_fechafin() {
		return horcab_fechafin;
	}

	public String gethorcab_nombre() {
		return horcab_nombre;
	}

	public List<HgHorarioCab> getlistaHorarioCab() {
		return listaHorarioCab;
	}

	public List<HgHorarioDet> getListaHorarioDet() {
		return listaHorarioDet;
	}

	public List<HgHorarioDet> getListaHorarioDetByCabId() {
		return listaHorarioDetByCabId;
	}

	public List<HgLugaresTurnosVacio> getListaLugarTurnoVacio() {
		return listaLugarTurnoVacio;
	}

	public boolean isEdicion() {
		return edicion;
	}

	public boolean isEdiciontipo() {
		return ediciontipo;
	}

	public String getusuariologeado() {
		return usuariologeado;
	}

	public Date getFecha() {
		return fecha;
	}

	public List<HorarioDet> getListaSinGuardias() {
		return listaSinGuardias;
	}

	public Integer getNumeroLugaresVacios() {
		return numeroLugaresVacios;
	}

	public Integer getNumeroRegistrosCreadoss() {
		return numeroRegistrosCreados;
	}

	public Integer getNumeroRegistrosTotal() {
		return numeroRegistrosTotal;
	}

	public Date getCambio_fecha() {
		return cambio_fecha;
	}

	public List<HgGuardia> getListaguardiasXFecha() {
		return listaguardiasXFecha;
	}

	public String getGuardiaId1() {
		return guardiaId1;
	}

	public String getGuardiaId2() {
		return guardiaId2;
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

	public List<SelectItem> getLstGuardias() {
		return lstGuardias;
	}

	public HgHorarioCab getHcabElsita() {
		return hcabElsita;
	}

	public void setHcabElsita(HgHorarioCab hcabElsita) {
		this.hcabElsita = hcabElsita;
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public String getNombreLugarTurno() {
		return nombreLugarTurno;
	}

	public String getTurnoLugarTurno() {
		return turnoLugarTurno;
	}

	public Date getFechaLugarTurno() {
		return fechaLugarTurno;
	}

	public void setCambio_fecha(Date cambio_fecha) {
		this.cambio_fecha = cambio_fecha;
	}

	public void setGuardia1(HgGuardia guardia1) {
		this.guardia1 = guardia1;
	}

	public void setGuardia2(HgGuardia guardia2) {
		this.guardia2 = guardia2;
	}

	public void setApellidoguardia1(String apellidoguardia1) {
		this.apellidoguardia1 = apellidoguardia1;
	}

	public void setApellidoguardia2(String apellidoguardia2) {
		this.apellidoguardia2 = apellidoguardia2;
	}

	public void setGuardiaId1(String guardiaId1) {
		this.guardiaId1 = guardiaId1;
	}

	public void setGuardiaId2(String guardiaId2) {
		this.guardiaId2 = guardiaId2;
	}

	public void setGuardiaBuscar(String guardiaBuscar) {
		this.guardiaBuscar = guardiaBuscar;
	}

	public void setCab_id(Integer cab_id) {
		this.cab_id = cab_id;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setFechaLugarTurno(Date fechaLugarTurno) {
		this.fechaLugarTurno = fechaLugarTurno;
	}

	public void setHorcab_fecha_creacion(Timestamp horcab_fecha_creacion) {
		this.horcab_fecha_creacion = horcab_fecha_creacion;
	}

	public void setHorcab_fechafin(Date horcab_fechafin) {
		this.horcab_fechafin = horcab_fechafin;
	}

	public void setHorcab_fechainicio(Date horcab_fechainicio) {
		this.horcab_fechainicio = horcab_fechainicio;
	}

	public void setHorcab_id(Integer horcab_id) {
		this.horcab_id = horcab_id;
	}

	public void setLugarBuscar(Integer lugarBuscar) {
		this.lugarBuscar = lugarBuscar;
	}

	public void setHorcab_nombre(String horcab_nombre) {
		this.horcab_nombre = horcab_nombre;
	}

	public void setHorcab_usuarioreg(String horcab_usuarioreg) {
		this.horcab_usuarioreg = horcab_usuarioreg;
	}

	public void setNombreguardia1(String nombreguardia1) {
		this.nombreguardia1 = nombreguardia1;
	}

	public void setNombreguardia2(String nombreguardia2) {
		this.nombreguardia2 = nombreguardia2;
	}

	public void setNombreLugarTurno(String nombreLugarTurno) {
		this.nombreLugarTurno = nombreLugarTurno;
	}

	public void setNumeroLugaresVacios(Integer numeroLugaresVacios) {
		this.numeroLugaresVacios = numeroLugaresVacios;
	}

	public void setNumeroRegistrosCreados(Integer numeroRegistrosCreados) {
		this.numeroRegistrosCreados = numeroRegistrosCreados;
	}

	public void setNumeroRegistrosTotal(Integer numeroRegistrosTotal) {
		this.numeroRegistrosTotal = numeroRegistrosTotal;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public void setUsuariologeado(String usuariologeado) {
		this.usuariologeado = usuariologeado;
	}

	public void setTurnoLugarTurno(String turnoLugarTurno) {
		this.turnoLugarTurno = turnoLugarTurno;
	}

	public void setSqlfechaf(java.sql.Date sqlfechaf) {
		this.sqlfechaf = sqlfechaf;
	}

	public void setSqlfechai(java.sql.Date sqlfechai) {
		this.sqlfechai = sqlfechai;
	}

	public String getNombreguardiaLugTurnVacio() {
		return nombreguardiaLugTurnVacio;
	}

	public String getApellidoguardiaLugTurnVacio() {
		return apellidoguardiaLugTurnVacio;
	}

	public String getGuardiaLugTurnVacio() {
		return guardiaLugTurnVacio;
	}

	public void setNombreguardiaLugTurnVacio(String nombreguardiaLugTurnVacio) {
		this.nombreguardiaLugTurnVacio = nombreguardiaLugTurnVacio;
	}

	public void setGuardiaLugTurnVacio(String guardiaLugTurnVacio) {
		this.guardiaLugTurnVacio = guardiaLugTurnVacio;
	}

	public String getCedulaguardiaLugTurnVacio() {
		return cedulaguardiaLugTurnVacio;
	}

	public void setCedulaguardiaLugTurnVacio(String cedulaguardiaLugTurnVacio) {
		this.cedulaguardiaLugTurnVacio = cedulaguardiaLugTurnVacio;
	}

	public Integer getHcab_id() {
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

	// horario cabecera
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
			horcab_usuarioreg = "";// usuariologeado.trim()
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
		List<HgLugare> lugares = managergest.findAllLugaresActivos();
		try {
			Date fechainicial = java.sql.Date.valueOf(new SimpleDateFormat(
					"yyyy-MM-dd").format(sqlfechai));
			Integer dias = diasXFi_Ff(sqlfechaf, sqlfechai);
			for (Integer numeroDia = 0; numeroDia <= dias; numeroDia++) {
				for (HgLugare lugar : lugares) {
					if (averiguarDia(lugar, fechainicial) == true) {
						List<HgLugarTurno> lugarTurnos = managergest
								.findLugarByIdLugar(lugar.getLugId());
						for (HgLugarTurno lugarTurno : lugarTurnos) {
							for (Integer numeroGuardias = 1; numeroGuardias <= lugarTurno
									.getLugTurNumeroGuardias(); numeroGuardias++) {
								HgGuardia guardiaAlmacenar = new HgGuardia();
								boolean guardia = true;
								boolean sinGuardia = true;
								guardiaAlmacenar = obtenerGuardia(lugar,
										fechainicial, lugarTurno.getHgTurno(),
										numeroDia, sinGuardia);
								if (guardiaAlmacenar.getGuaCedula() == null) {
									sinGuardia = false;
									guardiaAlmacenar = obtenerGuardia(lugar,
											fechainicial,
											lugarTurno.getHgTurno(), numeroDia,
											sinGuardia);
								}
								if (guardiaAlmacenar.getGuaCedula() == null) {
									guardia = false;
									managerhorario.insertarLugarTurnoVacio(
											cab_id, lugarTurno.getHgTurno(),
											lugar, fechainicial);
								}
								if (guardia) {
									// if (numeroDia + 1 == 30) {
									// if (managerhorario
									// .findNumDiasxGuardia(guardiaAlmacenar,restDays(fechainicial),rest5Days(restDays((fechainicial))))
									// == 5) {
									// managerhorario.insertarGuardiaPendienteLibre(guardiaAlmacenar,fechainicial);
									// almacenarDetalles(guardiaAlmacenar,lugar,lugarTurno.getHgTurno(),fechainicial,
									// null, cab_id);
									// } else {
									// almacenarDetalles(guardiaAlmacenar,lugar,lugarTurno.getHgTurno(),fechainicial,
									// null, cab_id);
									// }
									// } else {
									almacenarDetalles(guardiaAlmacenar, lugar,
											lugarTurno.getHgTurno(),
											fechainicial, null, cab_id);
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

	private HgGuardia obtenerGuardia(HgLugare lugar, Date fecha, HgTurno turno,
			Integer numeroDias, boolean sinGuardia) {
		HgGuardia guardiaelegido = new HgGuardia();
		List<HgGuardia> guardiasDisponibles = new ArrayList<HgGuardia>();
		// if (guardiaelegido.getGuaCedula() == null) {
		// guardiasDisponibles = managergest.findGuardias7Dias(fecha);
		// guardiaelegido = obtenerGuardiaCompatible(lugar, fecha,
		// turno,guardiasDisponibles, numeroDias);
		// }
		if (numeroDias % 3 != 0) {
			guardiasDisponibles = managergest.findGuardiasDisponibles(fecha);
			guardiaelegido = obtenerGuardiaCompatible(lugar, fecha, turno,
					guardiasDisponibles, numeroDias, sinGuardia);
		}
		if (guardiaelegido.getGuaCedula() == null) {
			guardiasDisponibles = managergest
					.findGuardiasDisponiblesSinLibres(fecha);
			guardiaelegido = obtenerGuardiaCompatible(lugar, fecha, turno,
					guardiasDisponibles, numeroDias, sinGuardia);
		}
		if (guardiaelegido.getGuaCedula() == null) {
			guardiasDisponibles = managergest
					.findGuardiasUltimosDosDiasLibres(fecha);
			guardiaelegido = obtenerGuardiaCompatible(lugar, fecha, turno,
					guardiasDisponibles, numeroDias, sinGuardia);
		}
		// if (guardiaelegido.getGuaCedula() == null
		// && (numeroDias + 1 == 30)) {
		// guardiasDisponibles = managergest
		// .findGuardiasDisponiblesDiaAnterior(fecha);
		// guardiaelegido = obtenerGuardiaCompatibleSexto(lugar, fecha, turno,
		// guardiasDisponibles, numeroDias, sinGuardia);
		// }
		return guardiaelegido;
	}

	private HgGuardia obtenerGuardiaCompatible(HgLugare lugar,
			Date fechainicial, HgTurno turno,
			List<HgGuardia> guardiasDisponibles, Integer numeroDias,
			boolean sinGuardia) {
		HgGuardia guardiaelegido = new HgGuardia();
		try {
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
			java.util.Date date = fechainicial;
			df.applyPattern("EEE");
			for (HgGuardia guardia : guardiasDisponibles) {
				Boolean guardiaAplica = true;
				Integer vecestrabajo = 0;
				Integer diasTrabajados = 5;

				if (averiguarGuardiaNoTrabajo(guardia, fechainicial, turno) == true) {

					if (managerhorario.existeAusencia(guardia.getGuaCedula(),
							fechainicial) == 0) {
						if ((managerhorario.existeGuardia(cab_id, fechainicial,
								guardia.getGuaCedula()) != 1)) {
							if (managerhorario.trabajoDiaAnterior(guardia,
									restDays(fechainicial)) == 1) {
								vecestrabajo = managerhorario
										.findNumDiasxGuardia(
												guardia,
												restDays(fechainicial),
												rest5Days(restDays(fechainicial)));
							} else if (managerhorario.trabajoDiaAnterior(
									guardia, restDays(restDays(fechainicial))) == 1) {
								vecestrabajo = managerhorario
										.findNumDiasxGuardia(
												guardia,
												restDays(restDays(fechainicial)),
												rest5Days(restDays(restDays(fechainicial))));
							}
							if (vecestrabajo < diasTrabajados) {
								// if(managerhorario.trabajoSemanaAnteriorLugar(fechainicial,
								// guardia.getGuaCedula(), lugar.getLugId()) ==
								// 0 )
								// guardiaAplica = false;
								// if(managerhorario.trabajoLugDiaAnterior(guardia,lugar,fechainicial)==1)
								// guardiaAplica = false;
								// if(managerhorario.trabajoLugDiaAnterior(guardia,lugar,restDays(fechainicial))==1)
								// guardiaAplica = false;
								// if(managerhorario.trabajoLugDiaAnterior(guardia,lugar,restDays(restDays(fechainicial)))==1)
								// guardiaAplica = false;
								// if(managerhorario.trabajoLugDiaAnterior(guardia,lugar,restDays(restDays(restDays(fechainicial))))==1)
								// guardiaAplica = false;
								if (sinGuardia == true) {
									if (managerhorario.existeGuardiaXturnoMNoc(
											cab_id, restDays(fechainicial),
											guardia.getGuaCedula()) == 1
											&& turno.getTurId() == 1)
										guardiaAplica = false;
									if (lugar.getLugCctv() == true
											&& guardia.getGuaCctv() != true)
										guardiaAplica = false;
									if (lugar.getLugCentroEmprendimiento() == true
											&& guardia
													.getGuaCentroEmprendimiento() != true)
										guardiaAplica = false;
									if (lugar.getLugControlAccesos() == true
											&& guardia.getGuaControlAccesos() != true)
										guardiaAplica = false;
									if (guardia.getGuaCasoEstudio() == true
											&& (df.format(date).equals("dom") || df
													.format(date).equals("sáb")))
										guardiaAplica = false;
									if (guardia.getGuaCasoNocturno() == true
											&& turno.getTurId() == 3)
										guardiaAplica = false;
									if ((lugar.getLugNombre().equals(
											"Instituto")
											|| lugar.getLugNombre().equals(
													"Centro de emprendimiento")
											|| lugar.getLugNombre().equals(
													"San Eloy")
											|| lugar.getLugNombre().equals(
													"CCTV")
											|| lugar.getLugNombre().equals(
													"Tanques de Agua") || lugar
											.getLugNombre().equals("Control 1"))
											&& guardia.getGuaMotorizado() == true)
										guardiaAplica = false;
								} else {
									if (lugar.getLugCctv() == false
											|| lugar.getLugControlAccesos() == false
											|| lugar.getLugCentroEmprendimiento() == false) {
										if (managerhorario
												.existeGuardiaXturnoMNoc(
														cab_id,
														restDays(fechainicial),
														guardia.getGuaCedula()) == 1
												&& turno.getTurId() == 1)
											guardiaAplica = false;
										if (lugar.getLugCctv() == true
												&& guardia.getGuaCctv() != true)
											guardiaAplica = false;
										if (lugar.getLugCentroEmprendimiento() == true
												&& guardia
														.getGuaCentroEmprendimiento() != true)
											guardiaAplica = false;
										if (lugar.getLugControlAccesos() == true
												&& guardia
														.getGuaControlAccesos() != true)
											guardiaAplica = false;
										if (guardia.getGuaCasoEstudio() == true
												&& (df.format(date).equals(
														"dom") || df.format(
														date).equals("sáb")))
											guardiaAplica = false;
										if (guardia.getGuaCasoNocturno() == true
												&& turno.getTurId() == 3)
											guardiaAplica = false;
									} else if (lugar.getLugNombre().equals(
											"Bloques")
											|| lugar.getLugNombre()
													.equals("Centro de emprendimiento 1")) {
										if (managerhorario
												.existeGuardiaXturnoMNoc(
														cab_id,
														restDays(fechainicial),
														guardia.getGuaCedula()) == 1
												&& turno.getTurId() == 1)
											guardiaAplica = false;
									}
								}
								if (guardiaAplica) {
									guardiaelegido = guardia;
									break;
								}
							}
						}
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return guardiaelegido;
	}

	// private HgGuardia obtenerGuardiaCompatibleSexto(HgLugare lugar,
	// Date fechainicial, HgTurno turno,
	// List<HgGuardia> guardiasDisponibles, Integer numeroDias,
	// boolean sinGuardia) {
	// HgGuardia guardiaelegido = new HgGuardia();
	// try {
	// SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
	// java.util.Date date = fechainicial;
	// df.applyPattern("EEE");
	// for (HgGuardia guardia : guardiasDisponibles) {
	// Boolean guardiaAplica = true;
	// Integer vecestrabajo = 0;
	// Integer diasTrabajados = 6;
	// if(averiguarGuardiaNoTrabajo(guardia,fechainicial)==true){
	// if (managerhorario.existeAusencia(guardia.getGuaCedula(),
	// fechainicial) == 0) {
	// if ((managerhorario.existeGuardia(cab_id, fechainicial,
	// guardia.getGuaCedula()) != 1)) {
	// if (managerhorario.trabajoDiaAnterior(guardia,
	// restDays(fechainicial)) == 1) {
	// vecestrabajo = managerhorario.findNumDiasxGuardia(
	// guardia, restDays(fechainicial),
	// rest5Days(restDays(fechainicial)));
	// } else if (managerhorario.trabajoDiaAnterior(guardia,
	// restDays(restDays(fechainicial))) == 1) {
	// vecestrabajo = managerhorario.findNumDiasxGuardia(
	// guardia, restDays(restDays(fechainicial)),
	// rest5Days(restDays(restDays(fechainicial))));
	// }
	// if (vecestrabajo < diasTrabajados) {
	// // if(managerhorario.trabajoSemanaAnteriorLugar(fechainicial,
	// // guardia.getGuaCedula(), lugar.getLugId()) == 0 )
	// // guardiaAplica = false;
	// //
	// if(managerhorario.trabajoLugDiaAnterior(guardia,lugar,fechainicial)==1)
	// // guardiaAplica = false;
	// //
	// if(managerhorario.trabajoLugDiaAnterior(guardia,lugar,restDays(fechainicial))==1)
	// // guardiaAplica = false;
	// //
	// if(managerhorario.trabajoLugDiaAnterior(guardia,lugar,restDays(restDays(fechainicial)))==1)
	// // guardiaAplica = false;
	// //
	// if(managerhorario.trabajoLugDiaAnterior(guardia,lugar,restDays(restDays(restDays(fechainicial))))==1)
	// // guardiaAplica = false;
	// if (sinGuardia == true) {
	// if
	// (managerhorario.existeGuardiaXturnoMNoc(cab_id,restDays(fechainicial),guardia.getGuaCedula())
	// == 1
	// && turno.getTurId() == 1)
	// guardiaAplica = false;
	// if (lugar.getLugCctv() == true && guardia.getGuaCctv() != true)
	// guardiaAplica = false;
	// if (lugar.getLugCentroEmprendimiento() == true &&
	// guardia.getGuaCentroEmprendimiento() != true)
	// guardiaAplica = false;
	// if (lugar.getLugControlAccesos() == true &&
	// guardia.getGuaControlAccesos() != true)
	// guardiaAplica = false;
	// if (guardia.getGuaCasoEstudio() == true && (df.format(date).equals("dom")
	// || df.format(date).equals("sáb")))
	// guardiaAplica = false;
	// if (guardia.getGuaCasoNocturno() == true
	// && turno.getTurId() == 3)
	// guardiaAplica = false;
	// if ((lugar.getLugNombre().equals("Instituto")
	// || lugar.getLugNombre().equals("Centro de emprendimiento")
	// || lugar.getLugNombre().equals("San Eloy")
	// || lugar.getLugNombre().equals("CCTV")
	// || lugar.getLugNombre().equals("Tanques de Agua") || lugar
	// .getLugNombre().equals("Control 1"))
	// && guardia.getGuaMotorizado() == true)
	// guardiaAplica = false;
	// } else {
	// if(lugar.getLugCctv()==false ||
	// lugar.getLugControlAccesos()==false||lugar.getLugCentroEmprendimiento()==false){
	// if (managerhorario.existeGuardiaXturnoMNoc(cab_id,
	// restDays(fechainicial),
	// guardia.getGuaCedula()) == 1
	// && turno.getTurId() == 1)
	// guardiaAplica = false;
	// if (lugar.getLugCctv() == true
	// && guardia.getGuaCctv() != true)
	// guardiaAplica = false;
	// if (lugar.getLugCentroEmprendimiento() == true &&
	// guardia.getGuaCentroEmprendimiento() != true)
	// guardiaAplica = false;
	// if (lugar.getLugControlAccesos() == true
	// && guardia.getGuaControlAccesos() != true)
	// guardiaAplica = false;
	// if (guardia.getGuaCasoEstudio() == true
	// && (df.format(date).equals("dom") || df.format(
	// date).equals("sáb")))
	// guardiaAplica = false;
	// if (guardia.getGuaCasoNocturno() == true
	// && turno.getTurId() == 3)
	// guardiaAplica = false;
	// }else if(lugar.getLugNombre().equals("Bloques") ||
	// lugar.getLugNombre().equals("Centro de emprendimiento 1")){
	// if (managerhorario.existeGuardiaXturnoMNoc(cab_id,
	// restDays(fechainicial),
	// guardia.getGuaCedula()) == 1
	// && turno.getTurId() == 1)
	// guardiaAplica = false;
	// }
	// }
	// if (guardiaAplica) {
	// guardiaelegido = guardia;
	// break;
	// }
	// }
	// }
	// }
	// }
	// }
	// }catch (ParseException e) {
	// e.printStackTrace();
	// }
	// return guardiaelegido;
	// }

	public boolean averiguarDia(HgLugare lugar, Date fechainicial)
			throws ParseException {
		boolean resultado = true;
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
		java.util.Date date = fechainicial;
		df.applyPattern("EEE");

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

	public boolean averiguarGuardiaNoTrabajo(HgGuardia guardia,
			Date fechainicial, HgTurno turno) throws ParseException {
		boolean resultado = true;
		try {
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
			java.util.Date date = fechainicial;
			df.applyPattern("EEE");
			for (HgGuardiasDiasNoTrabajo guardiaNoTrabajo : managergest
					.findGuardiaByIdGuardiaNT(guardia.getGuaCedula())) {

				if (guardiaNoTrabajo.getGuaDiaTrabajoDomingo() == true
						&& df.format(date).equals("dom")) {
					if (turno.getTurId() == (guardiaNoTrabajo.getHgGuardia()
							.getGuaCasoTurno())) {
						resultado = false;
					} else {
						resultado = true;
						break;
					}
					resultado = false;
				}
				if (guardiaNoTrabajo.getGuaDiaTrabajoLunes() == true
						&& df.format(date).equals("lun")) {
					if (turno.getTurId() == (guardiaNoTrabajo.getHgGuardia()
							.getGuaCasoTurno())) {
						resultado = false;
					} else {
						resultado = true;
						break;
					}
					resultado = false;
				}
				if (guardiaNoTrabajo.getGuaDiaTrabajoMartes() == true
						&& df.format(date).equals("mar")) {
					if (turno.getTurId() == (guardiaNoTrabajo.getHgGuardia()
							.getGuaCasoTurno())) {
						resultado = false;
					} else {
						resultado = true;
						break;
					}
					resultado = false;
				}
				if (guardiaNoTrabajo.getGuaDiaTrabajoMiercoles() == true
						&& df.format(date).equals("mié")) {
					if (turno.getTurId() == (guardiaNoTrabajo.getHgGuardia()
							.getGuaCasoTurno())) {
						resultado = false;
					} else {
						resultado = true;
						break;
					}
					resultado = false;
				}
				if (guardiaNoTrabajo.getGuaDiaTrabajoJueves() == true
						&& df.format(date).equals("jue")) {
					if (turno.getTurId() == (guardiaNoTrabajo.getHgGuardia()
							.getGuaCasoTurno())) {
						resultado = false;
					} else {
						resultado = true;
						break;
					}
					resultado = false;
				}
				if (guardiaNoTrabajo.getGuaDiaTrabajoViernes() == true
						&& df.format(date).equals("vie")) {
					if (turno.getTurId() == (guardiaNoTrabajo.getHgGuardia()
							.getGuaCasoTurno())) {
						resultado = false;
					} else {
						resultado = true;
						break;
					}
					resultado = false;
				}
				if (guardiaNoTrabajo.getGuaDiaTrabajoSabado() == true
						&& df.format(date).equals("sáb")) {
					if (turno.getTurId() == (guardiaNoTrabajo.getHgGuardia()
							.getGuaCasoTurno())) {
						resultado = false;
					} else {
						resultado = true;
						break;
					}
					resultado = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public Integer diasXFi_Ff(Date fechaf, Date fechai) {
		Long diff = fechaf.getTime() - fechai.getTime();
		long dias = diff / (1000 * 60 * 60 * 24);
		return (int) dias;
	}

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
		nombreLugarTurno = null;
		turnoLugarTurno = null;
		fechaLugarTurno = null;
		getListaSinGuardias().clear();
		getlistaHorarioCab().clear();
		getlistaHorarioCab().addAll(managerhorario.findAllHorariosCab());
		getListaHorarioDet().clear();
		return "hg_horarios?faces-redirect=true";
	}

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

	public static Date addDays(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1); // minus number would decrement the days
		return cal.getTime();
	}

	public static Date restDays(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1); // minus number would decrement the days
		return cal.getTime();
	}

	public static Date rest5Days(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -5); // minus number would decrement the days
		return cal.getTime();
	}

	public void abrirDialog() {
		if (horcab_fechainicio.after(horcab_fechafin)) {
			Mensaje.crearMensajeWARN("Fecha inicio debe ser menor que la Fecha Fin");
		} else
			RequestContext.getCurrentInstance().execute("PF('gu').show();");
	}

	public void abrirDialog1() {
		getListaLugarTurnoVacio().clear();
		getListaLugarTurnoVacio().addAll(managergest.allLugarTurnoByID(cab_id));
		RequestContext.getCurrentInstance().execute("PF('info').show();");
	}

	public void abrirDialog2() {
		RequestContext.getCurrentInstance()
				.execute("PF('cambioturno').show();");
	}

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
			eventModel = new DefaultScheduleModel();
			for (HgHorarioDet e : managerhorario
					.findAllHorariosDetXIdCab(cab_id)) {
				String lugarguardia = e.getHgLugare().getLugNombre() + " - "
						+ e.getHgGuardia().getGuaApellido() + " "
						+ e.getHgGuardia().getGuaNombre();
				event = new DefaultScheduleEvent(lugarguardia,
						e.getHdetFechaInicio(), e.getHdetFechaInicio(), e);
				((DefaultScheduleEvent) event).setStyleClass(e.getHgTurno()
						.getTurCodigoColor());
				eventModel.addEvent(event);
			}
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

	public void abrirDialogHorarioEliminar(HgHorarioCab item) {
		setHcabElsita(item);
		RequestContext.getCurrentInstance().execute("PF('ef').show();");
	}

	public void eliminarHorarioCab() {
		try {
			managerhorario.eliminarHorarioCab(hcabElsita.getHcabId(),
					hcabElsita.getHcabFechaFin(),
					hcabElsita.getHcabFechaInicio());
			getlistaHorarioCab().clear();
			getlistaHorarioCab().addAll(managerhorario.findAllHorariosCab());
			Mensaje.crearMensajeINFO("Horario eliminado");
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("No se eliminó el horario");
			e.printStackTrace();
		}
	}

	public List<SelectItem> getlistEstados() {
		List<SelectItem> lista = new ArrayList<SelectItem>();
		lista.add(new SelectItem(Funciones.estadoActivo, Funciones.estadoActivo
				+ " : " + Funciones.valorEstadoActivo));
		lista.add(new SelectItem(Funciones.estadoInactivo,
				Funciones.estadoInactivo + " : "
						+ Funciones.valorEstadoInactivo));
		return lista;
	}

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

	public String verCalendario() {
		return "hg_calendario?faces-redirect=true";
	}

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
		nombreLugarTurno = null;
		turnoLugarTurno = null;
		fechaLugarTurno = null;
		getlistaHorarioCab().clear();
		getlistaHorarioCab().addAll(managerhorario.findAllHorariosCab());
		return "hg_horarios?faces-redirect=true";
	}

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

	public void mostrarGuardia1() {
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

	public void mostrarGuardia2() {
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

	public void asignarGuardia1() {
		guardia1 = managergest.asignarGuardia(guardiaId1);
	}

	public void asignarGuardia2() {
		guardia2 = managergest.asignarGuardia(guardiaId2);
	}

	public void abrirDialogConfirm() {
		RequestContext.getCurrentInstance().execute("PF('cambio').show();");
	}

	public String volverCambio() throws Exception {
		cambio_fecha = null;
		guardiaId1 = null;
		guardiaId2 = null;
		nombreguardia1 = "";
		apellidoguardia1 = "";
		nombreguardia2 = "";
		apellidoguardia2 = "";
		nombreLugarTurno = null;
		turnoLugarTurno = null;
		fechaLugarTurno = null;
		listaguardiasXFecha.clear();
		lstGuardias.clear();
		return "hg_nhorario?faces-redirect=true";
	}

	public void cambioGuardias() {
		try {
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
							moverGuardia(hordet2, hordet1);
							guardarHistorial(hordet2, hordet1);
							Mensaje.crearMensajeINFO("El cambio se realizó correctamente");
							RequestContext.getCurrentInstance().execute(
									"PF('cambio').hide();");
							lstGuardias.clear();
						}
					} else {
						moverGuardia(hordet2, hordet1);
						guardarHistorial(hordet2, hordet1);
						Mensaje.crearMensajeINFO("El cambio se realizó correctamente");
						RequestContext.getCurrentInstance().execute(
								"PF('cambio').hide();");
						lstGuardias.clear();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		getListaHorarioDet().clear();
		getListaHorarioDet().addAll(
				managerhorario.findAllHorariosDetXIdCab(cab_id));
	}

	public void moverGuardia(HgHorarioDet hdet2, HgHorarioDet hdet1) {
		try {
			hdet2.setHgGuardia(guardia1);
			hdet1.setHgGuardia(guardia2);
			managerhorario.editarGuardiasDetalle(hdet1);
			managerhorario.editarGuardiasDetalle(hdet2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void guardarHistorial(HgHorarioDet hdet2, HgHorarioDet hdet1) {
		try {
			Integer historialid = managerhisto.ultimoOrdenCabeceraHistorial();
			Date fecha = new Date();
			HgHorarioCab cab = managerhorario.horarioCabByID(cab_id);
			Timestamp horaCreacionHistorial = new Timestamp(fecha.getTime());
			managerhisto.insertarHistorial(historialid, cab, guardia1,
					guardia2, hdet2.getHgLugare().getLugId(), hdet1
							.getHgLugare().getLugId(), horaCreacionHistorial,
					hdet1.getHdetFechaInicio());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void imprimirRptDocumento(HgHorarioCab horcab) {
		try {
			ServletContext servletContext = (ServletContext) FacesContext
					.getCurrentInstance().getExternalContext().getContext();
			String carpetaReportes = (String) servletContext
					.getRealPath(File.separatorChar + "reports");
			String rutaReporte = carpetaReportes + File.separatorChar
					+ "ReportIngSalGuardias.jasper";
			// Connection conexion =
			// DriverManager.getConnection("jdbc:postgresql://10.1.0.158:5432/horario_guardias?user=adm_horario_guardias&password={Jt26qGTf#T");
			Connection conexion = DriverManager
					.getConnection("jdbc:postgresql://localhost:5432/horario_guardias?user=postgres&password=root");
			sqlfechai = java.sql.Date
					.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(horcab
							.getHcabFechaInicio()));
			sqlfechaf = java.sql.Date
					.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(horcab
							.getHcabFechaFin()));
			Map<String, Object> parametros = new HashMap<String, Object>();
			System.out.println(carpetaReportes + File.separatorChar
					+ "yachay-logo1.png");
			System.out.println(carpetaReportes + File.separatorChar
					+ "yachay-logo2.png");
			System.out.println(horcab.getHcabId());
			parametros.put("pIdhcab", horcab.getHcabId());
			parametros.put("pImagen", carpetaReportes + File.separatorChar
					+ "yachay-logo1.png");
			parametros.put("pImagen2", carpetaReportes + File.separatorChar
					+ "yachay-logo2.png");
			parametros.put("pFechaInicial", sqlfechai);
			parametros.put("pFechaFinal", sqlfechaf);
			JasperPrint informe = JasperFillManager.fillReport(rutaReporte,
					parametros, conexion);
			HttpServletResponse response = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			response.addHeader("Content-disposition",
					"attachment; filename=jsfReporte.pdf");
			ServletOutputStream stream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(informe, stream);
			stream.flush();
			stream.close();
			FacesContext.getCurrentInstance().responseComplete();
			Mensaje.crearMensajeINFO("Se imprimió correctamente");
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al imprimir");
			e.printStackTrace();
		}
	}

	// calendario

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
	}

	public List<SelectItem> cargarGuardiasTodos() {
		List<SelectItem> listadoGuardias = new ArrayList<SelectItem>();
		listadoGuardias.add(new SelectItem("vacio",
				" --Seleccione el guardia-- "));
		for (HgGuardia i : managergest.findAllGuardias()) {
			listadoGuardias.add(new SelectItem(i.getGuaCedula(), i
					.getGuaCedula()
					+ " | "
					+ i.getGuaApellido()
					+ " "
					+ i.getGuaNombre()));
		}
		return listadoGuardias;
	}

	public List<SelectItem> cargarLugaresTodos() {
		List<SelectItem> listadoLugares = new ArrayList<SelectItem>();
		listadoLugares.add(new SelectItem(0, " --Seleccione el lugar-- "));
		for (HgLugare i : managergest.findAllLugaresActivos()) {
			listadoLugares.add(new SelectItem(i.getLugId(), i.getLugNombre()));
		}
		return listadoLugares;
	}

	public void asignarGuardiaBuscar() {
		eventModel = new DefaultScheduleModel();
		for (HgHorarioDet e : managerhorario.findAllHorariosDetXIdCab(cab_id)) {
			if (e.getHgGuardia().getGuaCedula().equals(guardiaBuscar)) {
				String lugarguardia = e.getHgLugare().getLugNombre() + " - "
						+ e.getHgGuardia().getGuaApellido() + " "
						+ e.getHgGuardia().getGuaNombre();
				event = new DefaultScheduleEvent(lugarguardia,
						e.getHdetFechaInicio(), e.getHdetFechaInicio(), e);
				((DefaultScheduleEvent) event).setStyleClass(e.getHgTurno()
						.getTurCodigoColor());
				eventModel.addEvent(event);
			} else if (guardiaBuscar.equals("vacio")) {
				refresh();
			}
		}
		Mensaje.crearMensajeINFO("Calendario Actualizado");
	}

	public void asignarLugarBuscar() {
		eventModel = new DefaultScheduleModel();
		for (HgHorarioDet e : managerhorario.findAllHorariosDetXIdCab(cab_id)) {
			if (e.getHgLugare().getLugId() == lugarBuscar) {
				String lugarguardia = e.getHgLugare().getLugNombre() + " - "
						+ e.getHgGuardia().getGuaApellido() + " "
						+ e.getHgGuardia().getGuaNombre();
				event = new DefaultScheduleEvent(lugarguardia,
						e.getHdetFechaInicio(), e.getHdetFechaInicio(), e);
				((DefaultScheduleEvent) event).setStyleClass(e.getHgTurno()
						.getTurCodigoColor());
				eventModel.addEvent(event);
			} else if (lugarBuscar == 0) {
				refresh();
			}
		}
		Mensaje.crearMensajeINFO("Calendario Actualizado");
	}

	public String refresh() {
		eventModel = new DefaultScheduleModel();
		for (HgHorarioDet e : managerhorario.findAllHorariosDetXIdCab(cab_id)) {
			String lugarguardia = e.getHgLugare().getLugNombre() + " - "
					+ e.getHgGuardia().getGuaApellido() + " "
					+ e.getHgGuardia().getGuaNombre();
			event = new DefaultScheduleEvent(lugarguardia,
					e.getHdetFechaInicio(), e.getHdetFechaInicio(), e);
			((DefaultScheduleEvent) event).setStyleClass(e.getHgTurno()
					.getTurCodigoColor());
			eventModel.addEvent(event);
		}
		return "hg_calendario?faces-redirect=true";
	}

	// mover guardias
	public void cargarLugarTurnoVacio(HgLugaresTurnosVacio lugturnvacio) {
		lugarTurnoVacioId = lugturnvacio.getHglugturId();
		nombreLugarTurno = lugturnvacio.getHgLugare().getLugNombre();
		turnoVacio = lugturnvacio.getHgTurno();
		lugarVacio = lugturnvacio.getHgLugare();
		turnoLugarTurno = lugturnvacio.getHgTurno().getTurDescripcion();
		fechaLugarTurno = lugturnvacio.getHglugturFechaInicio();
		lstGuardias.clear();
		obtenerGuardiaXFechaLugarTurno();
		RequestContext.getCurrentInstance().execute("PF('lugarturno').show();");
	}

	public void mostrarGuardiaLugarTurnoVacio() {
		try {
			nombreguardiaLugTurnVacio = "";
			apellidoguardiaLugTurnVacio = "";
			guardiaLugarTurno = managergest.guardiaByID(guardiaLugTurnVacio);
			nombreguardiaLugTurnVacio = guardiaLugarTurno.getGuaNombre();
			apellidoguardiaLugTurnVacio = guardiaLugarTurno.getGuaApellido();
			horarioDet = managerhorario.horarioDetByCedulaFecha(
					guardiaLugarTurno.getGuaCedula(), fechaLugarTurno);
			for (HgHorarioDet hordet : horarioDet) {
				hcab_id = hordet.getHgHorarioCab().getHcabId();
				hcab_descripcion = hordet.getHgHorarioCab().getHcabNombre();
				hcab_turnoId = hordet.getHgTurno();
				hcab_turno = hordet.getHgTurno().getTurDescripcion();
				hcab_lugarId = hordet.getHgLugare();
				hcab_lugar = hordet.getHgLugare().getLugNombre();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void obtenerGuardiaXFechaLugarTurno() {
		Date finicial = java.sql.Date
				.valueOf(new SimpleDateFormat("yyyy-MM-dd")
						.format(fechaLugarTurno));
		cargarGuardias(finicial);
	}

	public void asignarGuardiaLugarTurno() {
		guardiaLugarTurno = managergest.asignarGuardia(guardiaLugTurnVacio);
	}

	public String volverCambioLugarTurno() throws Exception {
		guardiaLugTurnVacio = null;
		horarioDet = null;
		hcab_id = null;
		hcab_descripcion = null;
		hcab_turno = null;
		hcab_lugar = null;
		nombreguardiaLugTurnVacio = "";
		apellidoguardiaLugTurnVacio = "";
		lstGuardias.clear();
		listaguardiasXFecha.clear();
		return "";
	}

	public void cambioLugarTurno() {
		try {
			if (guardiaLugTurnVacio != null) {
				if (hcab_turnoId.getTurId() == 1) {
					if ((managerhorario.existeGuardiaXturnoMNoc(hcab_id,
							restDays(fechaLugarTurno),
							guardiaLugarTurno.getGuaCedula()) == 1)) {
						Mensaje.crearMensajeWARN("El día anterior el guardia trabaja en turno nocturno, no se puede realizar esta acción");

					} else {
						almacenarDetalles(guardiaLugarTurno, lugarVacio,
								turnoVacio, fechaLugarTurno, null, hcab_id);
						managerhorario.insertarLugarTurnoVacio(hcab_id,
								hcab_turnoId, hcab_lugarId, fechaLugarTurno);
						managerhorario.eliminarLugarVacio(lugarTurnoVacioId);
						managerhorario.eliminarHorarioDetalle(hcab_id,
								hcab_lugarId, hcab_turnoId, fechaLugarTurno,
								guardiaLugarTurno);
						getListaLugarTurnoVacio().clear();
						getListaLugarTurnoVacio().addAll(
								managergest.allLugarTurnoByID(cab_id));
						lstGuardias.clear();
						listaguardiasXFecha.clear();
						limpiarCampos();
						Mensaje.crearMensajeINFO("El cambio se realizó correctamente");
					}
				} else {
					almacenarDetalles(guardiaLugarTurno, lugarVacio,
							turnoVacio, fechaLugarTurno, null, hcab_id);
					managerhorario.insertarLugarTurnoVacio(hcab_id,
							hcab_turnoId, hcab_lugarId, fechaLugarTurno);
					managerhorario.eliminarLugarVacio(lugarTurnoVacioId);
					managerhorario.eliminarHorarioDetalle(hcab_id,
							hcab_lugarId, hcab_turnoId, fechaLugarTurno,
							guardiaLugarTurno);
					lstGuardias.clear();
					listaguardiasXFecha.clear();
					getListaLugarTurnoVacio().clear();
					getListaLugarTurnoVacio().addAll(
							managergest.allLugarTurnoByID(cab_id));
					limpiarCampos();
					Mensaje.crearMensajeINFO("El cambio se realizó correctamente");
				}
			} else {
				Mensaje.crearMensajeWARN("Escoga un guardia");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void abrirDialogConfirmLugTurn() {
		RequestContext.getCurrentInstance().execute(
				"PF('cambiolugturn').show();");
	}

	public void limpiarCampos() {
		guardiaLugTurnVacio = null;
		horarioDet = null;
		hcab_id = null;
		hcab_descripcion = null;
		hcab_turno = null;
		hcab_lugar = null;
		nombreguardiaLugTurnVacio = "";
		apellidoguardiaLugTurnVacio = "";
		nombreguardiaLugTurnVacio = "";
		apellidoguardiaLugTurnVacio = "";
		guardiaLugarTurno = null;
		nombreguardiaLugTurnVacio = null;
		apellidoguardiaLugTurnVacio = null;
		horarioDet = null;
		hcab_id = null;
		hcab_descripcion = null;
		hcab_turnoId = null;
		hcab_turno = null;
		hcab_lugarId = null;
		hcab_lugar = null;
		lugarTurnoVacioId = null;
		nombreLugarTurno = null;
		turnoVacio = null;
		lugarVacio = null;
		turnoLugarTurno = null;
		fechaLugarTurno = null;
	}

	/**
	 * M�todo para crear un excel y guardarlo en una direcci�n espec�fica
	 * 
	 * @param est
	 */
	public void crearExcel(List<HgHorarioDet> ext) {
		String url = "C:/Users/lcorrea/Desktop/";
//		String url=url_doc+"/descarga/";
		try {
			// ServletContext servletContext = (ServletContext) FacesContext
			// .getCurrentInstance().getExternalContext().getContext();
			// String contextPath = servletContext.getRealPath(File.separator
			// + "resources/doc/descarga/");
			HSSFWorkbook libro = new HSSFWorkbook();
			HSSFSheet hoja = libro.createSheet("Datos");
			llenarFila(ext, ext.get(0), hoja);
			OutputStream out = new FileOutputStream(url
					+ "DatosExcel_Horario.xls");
			libro.write(out);
			libro.close();
			Mensaje.crearMensajeINFO("Horario descargado, revise su carpeta de descargas");
		} catch (IOException e) {
			Mensaje.crearMensajeWARN("No se creó el horario");
			e.printStackTrace();
		}
	}

	public void GenerarExcel(HgHorarioCab hcab) {
		getListaHorarioDet().clear();
		getListaHorarioDet().addAll(
				managerhorario.findAllHorariosDetXIdCab(hcab.getHcabId()));
		this.crearExcel(getListaHorarioDet());
	}

	/**
	 * M�todo para llenar una fila de un archivo excel
	 * 
	 * @param est
	 * @param row
	 */
	public void llenarFila(List<HgHorarioDet> listext, HgHorarioDet ext,
			HSSFSheet hoja) {
		try {
			HSSFRow row = hoja.createRow(0);
			Integer filas = 1;
			Integer columasguardias = 2;
			Integer filasGuardiaFecha = 1;
			Date fechainicial = java.sql.Date.valueOf(new SimpleDateFormat(
					"yyyy-MM-dd").format(ext.getHdetFechaInicio()));
			System.out.println(fechainicial);
			List<HgGuardia> listGuardias = managergest.findAllGuardias();
			HSSFCell celda0 = row.createCell(0);
			celda0.setCellValue("NOMBRE GUARDIA");
			HSSFCell celda1 = row.createCell(1);
			celda1.setCellValue("FECHAS");
			row = hoja.createRow(1);
			for (Integer fecha = 0; fecha <= this.diasXFi_Ff(
					managerhorario.horarioCabByID(
							ext.getHgHorarioCab().getHcabId())
							.getHcabFechaFin(),
					managerhorario.horarioCabByID(
							ext.getHgHorarioCab().getHcabId())
							.getHcabFechaInicio()); fecha++) {
				celda0 = row.createCell(0);
				celda0.setCellValue("");
				if (filas <= this.diasXFi_Ff(
						managerhorario.horarioCabByID(
								ext.getHgHorarioCab().getHcabId())
								.getHcabFechaFin(),
						managerhorario.horarioCabByID(
								ext.getHgHorarioCab().getHcabId())
								.getHcabFechaInicio()) + 1) {
					row.createCell(filas).setCellValue(
							Funciones.dateToString(fechainicial));
					fechainicial = addDays(fechainicial);
					fechainicial = java.sql.Date.valueOf(new SimpleDateFormat(
							"yyyy-MM-dd").format(fechainicial));
					filas++;
				}
			}
			Integer loop = 0;
			for (HgGuardia guardiaMostrar : listGuardias) {
				loop++;
				fechainicial = java.sql.Date.valueOf(new SimpleDateFormat(
						"yyyy-MM-dd").format(ext.getHdetFechaInicio()));
				Date fechainicialfilasfecha = fechainicial;
				row = hoja.createRow(columasguardias);
				celda0 = row.createCell(0);
				celda0.setCellValue(guardiaMostrar.getGuaNombre() + " "
						+ guardiaMostrar.getGuaApellido());
				filasGuardiaFecha = 1;
				for (Integer fecha = 1; fecha <= this.diasXFi_Ff(managerhorario.horarioCabByID(ext.getHgHorarioCab().getHcabId()).getHcabFechaFin(),
						managerhorario.horarioCabByID(ext.getHgHorarioCab().getHcabId()).getHcabFechaInicio())+1; fecha++) {
					List<HgHorarioDet> listdetalleguardia = managerhorario
							.horarioDetByCedulaFecha(
									guardiaMostrar.getGuaCedula(),
									fechainicialfilasfecha);
					row.createCell(filasGuardiaFecha).setCellValue("Libre");
					for (HgHorarioDet detalleGuardiaMostrar : listdetalleguardia) {
						if (!detalleGuardiaMostrar.getHdetId().equals(null)) {
							row.createCell(filasGuardiaFecha).setCellValue(
									detalleGuardiaMostrar.getHgLugare()
											.getLugNombre()
											+ " - "
											+ detalleGuardiaMostrar
													.getHgTurno()
													.getTurDescripcion());
						}
					}
					fechainicialfilasfecha = addDays(fechainicialfilasfecha);
					fechainicialfilasfecha = java.sql.Date
							.valueOf(new SimpleDateFormat("yyyy-MM-dd")
									.format(fechainicialfilasfecha));
					filasGuardiaFecha++;
				}
				columasguardias++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * M�todo para descargar un archivo excel
	 */
	public void descargarArchivo() {
			if (listaHorarioDet==null || listaHorarioDet.size()==0){
				Mensaje.crearMensajeWARN("La tabla está vacía como para generar un reporte, primero debe dar clic en ");
			}else{
//			ServletContext servletContext = (ServletContext) FacesContext
//					.getCurrentInstance().getExternalContext().getContext();
//			String contextPath = servletContext.getRealPath(File.separator
//					+ "resources/doc/descargaDatosExcel_Externos.xls");
			Funciones.descargarExcel("C:\\Users\\lcorrea\\Desktop\\DatosExcel_Horario.xls");	
//			Funciones.descargarExcel(url_doc+"/descarga/DatosExcel_Horario.xls");
			Mensaje.crearMensajeINFO("Descargado, revise su carpeta de descargas");
			}
	}
	
}
