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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
import hguardias.model.dao.entities.HgLugare;
import hguardias.model.dao.entities.HgTurno;
import hguardias.model.dao.entidades.DiasMG;
import hguardias.model.dao.entidades.Guardias;
import hguardias.model.dao.entidades.HorarioDet;
import hguardias.model.dao.entidades.Lugares;
import hguardias.model.dao.entidades.Turnos;
import hguardias.model.dao.entidades.Dias;
import hguardias.model.dao.entidades.Semanas;
import hguardias.model.dao.entities.Persona;
import hguardias.model.generic.Mensaje;
import hguardias.model.manager.ManagerGestion;

@SessionScoped
@ManagedBean
public class horarioCDBeanRespaldo implements Serializable {

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
	
	private Integer numeroRegistrosTotal;
	private Integer numeroLugaresVacios;
	private Integer numeroRegistrosCreados;
	
	@Inject
	SesionBean ms;

	public horarioCDBeanRespaldo() {
	}

	@PostConstruct
	public void ini() {
		usuario = ms.validarSesion("hg_horarios.xhtml");
		System.out.println(usuario);
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
		listaSinGuardias = new ArrayList<HorarioDet>();
		numeroLugaresVacios =0;
		numeroRegistrosCreados=0;
		numeroRegistrosTotal=0;
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

	public String crearhorario() {
		List<HgGuardia> lisHgGuardia = managergest.findAllGuardias();
		List<HgTurno> lisHgTurno = managergest.findAllTurnos();
		List<HgLugare> lisHgLugar = managergest.findAllLugares();

		List<Guardias> ligua = new ArrayList<Guardias>();
		List<Turnos> liturn = new ArrayList<Turnos>();
		List<Lugares> liluga = new ArrayList<Lugares>();
		List<Dias> liDias = new ArrayList<Dias>();

//		for (HgGuardia g : lisHgGuardia) {
//			Guardias guardias = new Guardias(g.getGuaCedula(),
//					g.getGuaApellido(), g.getGuaCasoEstudio(),
//					g.getGuaCasoNocturno(), g.getGuaCasoTurno(),
//					g.getGuaCctv(), g.getGuaCelular(), g.getGuaChofer(),
//					g.getGuaCiudad(), g.getGuaControlAccesos(),
//					g.getGuaCorreo(), g.getGuaDireccion(), g.getGuaEstado(),
//					g.getGuaEstadoCivil(), g.getGuaFechanac(),
//					g.getGuaMotorizado(), g.getGuaNombre(), g.getGuaSexo(),
//					g.getGuaTelefono(), g.getGuaTipoSangre());
//			ligua.add(guardias);
//		}

		for (HgTurno t : lisHgTurno) {
			Turnos turnos = new Turnos(t.getTurId(), t.getTurDescripcion(),
					t.getTurEstado(), t.getTurHoraFin(), t.getTurHoraInicio(),
					ligua);
			liturn.add(turnos);
		}

		for (HgLugare l : lisHgLugar) {
			Lugares lugares = new Lugares(l.getLugId(), l.getLugCctv(),
					l.getLugCiudad(), l.getLugControlAccesos(),
					l.getLugEstado(), l.getLugNombre(), l.getLugNroGuardias(),
					liturn);
			liluga.add(lugares);
		}

		int diaId = 0;
		String diaNombre = "";
		int semanaId = 0;
		String semanaNombre = "";

		Dias dia = new Dias(diaId, diaNombre, liluga);
		dia.setDiaId(1);
		dia.setDiaNombre("Lunes");
		dia.setDiaId(2);
		dia.setDiaNombre("Martes");
		dia.setDiaId(3);
		dia.setDiaNombre("Miercoles");
		dia.setDiaId(4);
		dia.setDiaNombre("Jueves");
		dia.setDiaId(5);
		dia.setDiaNombre("Viernes");
		dia.setDiaId(6);
		dia.setDiaNombre("Sabado");
		dia.setDiaId(7);
		dia.setDiaNombre("Domingo");
		dia.setLugares(liluga);

		Semanas semana = new Semanas(semanaId, semanaNombre, liDias);
		semana.setSemanaId(1);
		semana.setSemanaNombre("Primera");
		semana.setSemanaId(2);
		semana.setSemanaNombre("Segunda");
		semana.setSemanaId(3);
		semana.setSemanaNombre("Tercera");
		semana.setSemanaId(4);
		semana.setSemanaNombre("Cuarta");
		semana.setSemanaId(5);
		semana.setSemanaNombre("Quinta");
		semana.setDias(liDias);

		return "";
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
//			horcab_usuarioreg = "abc";// 
			horcab_usuarioreg  = usuariologeado.trim();
//			cab_id=1;
			managerhorario.insertarHorarioCab(cab_id, sqlfechai, sqlfechaf,horcab_nombre.trim(), horcab_usuarioreg,horcab_fecha_creacion);
			if (!managergest.findAllGuardias().isEmpty()) {
				metodasotonito_fecha_lugar_turno_guardia(cab_id);
				Mensaje.crearMensajeINFO("Se creó satisfactoriamente");
			} else {
				Mensaje.crearMensajeWARN("Error detalle vacio revisar guardias");
			}
			// mainAg();
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al crear horario");
			e.printStackTrace();
		}
	}
	
	/**
	 * accion cerrar horario
	 * 
	 */
	public String cerrarHorarioCab() {
		numeroLugaresVacios=0;
		numeroRegistrosCreados=0;
		horcab_id = null;
		horcab_fechainicio = null;
		horcab_fechafin = null;
		horcab_nombre = null;
		horcab_usuarioreg = null;
		horcab_fecha_creacion = null;
		cab_id = null;
		date = null;
		getListaSinGuardias().clear();
		getlistaHorarioCab().clear();
		getlistaHorarioCab().addAll(managerhorario.findAllHorariosCab());
		return "hg_horarios?faces-redirect=true";
	}

	@SuppressWarnings("deprecation")
	public void metodaso(Integer cab_id) {
		List<HgLugare> lugares = managergest.findAllLugares();
		List<HgTurno> turnos = managergest.findAllTurnos();
		List<HgGuardia> guardiaescogidos = managergest.findAllGuardias();

		Date fechainicial = sqlfechai;
		Date fechafinal = addDays(sqlfechaf); // aumento un dia para que pueda//
												// realizar el control de si
												// es// menor a la fecha
		Date fechanterior = fechainicial;

		Integer diasLibres = 5;// numero de dias al que debe trabajar el usuario
		Integer contadorlistas = 1;
		try {
			Integer dias = diasXFi_Ff();// nro de dias entre fechas
			for (Integer a = 0; a <= dias; a++) {
				fechainicial = java.sql.Date.valueOf(new SimpleDateFormat(
						"yyyy-MM-dd").format(fechainicial));// obtengo la
															// fecha// en
															// formato//
															// (yyyy-mm-dd)
				System.out.println("--------------------->fechas: "
						+ fechainicial + " " + fechafinal);
				if (!fechainicial.after(fechafinal)) {// si la fecha es menor//
														// que la fecha final
					System.out.println("--------------------->dias " + dias
							+ " cuantos faltan:" + a);
					for (HgLugare lugar : lugares) {// lugares
						Integer contador = 1;
						System.out.println("--------------------->lugar: "
								+ lugar.getLugNombre());
						for (HgTurno turno : turnos) {// turnos
							System.out.println("--------------------->turno: "
									+ turno.getTurDescripcion());
							romper: for (HgGuardia guardia : guardiaescogidos) {
								Integer vecestrabajo = 0;
								// vecestrabajo =
								// managerhorario.findNumDiasxGuardia(guardia,
								// cab_id);// numero
								// de
								// veces
								// que
								// trabaja
								// contador
								// devuelve
								// valor
								System.out
										.println("------------>numero de veces que ha trabajado: "
												+ vecestrabajo);
								if (vecestrabajo < diasLibres) {// control que
																// no realiza si
																// trabaja 5
																// dias
									if ((managerhorario.existeGuardia(cab_id,
											fechainicial,
											guardia.getGuaCedula()) == 0)) {
										// si trabaja el guardia en ese dia
										if (contador <= lugar
												.getLugNroGuardias()) {// contador
																		// del
																		// numero
																		// se
																		// guardias
																		// quese
																		// encuentra
																		// en el
																		// lugar
											System.out
													.println("--------------------->nro. guardias cont: "
															+ contador
															+ " nro. guardias en lugar: "
															+ lugar.getLugNroGuardias());
											if (guardia.getGuaEstado().equals(
													"A")) {// si el guardia se//
															// encuentra en//
															// activo o//
															// desactivado//
															// casos en los que
															// el guardia
												// debe cumplir dependiendo
												// del// lugar y de su capacidad
												if (guardia.getGuaCasoEstudio() == true
														&& (fechainicial
																.getDay() != 0 || fechainicial
																.getDay() != 1)) {
													// agregar caso si es//
													// estudio libres sabados
													// y// domingos
													if (turno.getTurId() == 3) { // si//
																					// es//
																					// turno//
																					// nocturno//
																					// agrega//
																					// un//
																					// dia//
																					// mas
														fechainicial = addDays(fechainicial);
														almacenarDetalles(
																guardia, lugar,
																turno,
																fechainicial,
																fechainicial,
																cab_id);
														fechainicial = restDays(fechainicial);
														contador++;
														guardiaescogidos = managergest
																.AllGuardiasMenosAgregado(guardia);
														break romper;
													} else if (turno.getTurId() == 1) {
														if (managerhorario
																.existeGuardiaXturnoMNoc(
																		cab_id,
																		restDays(fechainicial),
																		guardia.getGuaCedula()) == 0) {
															// si existe el//
															// usuario en el//
															// turno nocturno//
															// del dia
															// anterior// no
															// puede trabajar//
															// diurno
															almacenarDetalles(
																	guardia,
																	lugar,
																	turno,
																	fechainicial,
																	fechainicial,
																	cab_id);
															contador++;
															guardiaescogidos = managergest
																	.AllGuardiasMenosAgregado(guardia);
															break romper;
														}
													} else {
														almacenarDetalles(
																guardia, lugar,
																turno,
																fechainicial,
																fechainicial,
																cab_id);
														contador++;
														guardiaescogidos = managergest
																.AllGuardiasMenosAgregado(guardia);
														break romper;
													}
												} else if (guardia
														.getGuaCasoNocturno() == true
														&& lugar.getLugCctv() == false
														&& guardia.getGuaCctv() == false
														&& !turno.getTurId()
																.equals(1)) {
													// agregar caso si
													// trabajan// solo turnos
													// vespertino o// nocturno
													if (turno.getTurId() == 3) {
														fechainicial = addDays(fechainicial);
														almacenarDetalles(
																guardia, lugar,
																turno,
																fechainicial,
																fechainicial,
																cab_id);
														fechainicial = restDays(fechainicial);
														contador++;
														guardiaescogidos = managergest
																.AllGuardiasMenosAgregado(guardia);
														break romper;
													} else {
														almacenarDetalles(
																guardia, lugar,
																turno,
																fechainicial,
																fechainicial,
																cab_id);
														contador++;
														guardiaescogidos = managergest
																.AllGuardiasMenosAgregado(guardia);
														break romper;
													}
												} else if (guardia.getGuaCctv() == true
														&& guardia
																.getGuaChofer() == true
														&& lugar.getLugCctv() == true) {
													// agregar si es CCTV y//
													// chofer
													if (turno.getTurId() == 3) {
														fechainicial = addDays(fechainicial);
														almacenarDetalles(
																guardia, lugar,
																turno,
																fechainicial,
																fechainicial,
																cab_id);
														fechainicial = restDays(fechainicial);
														contador++;
														guardiaescogidos = managergest
																.AllGuardiasMenosAgregado(guardia);
														break romper;
													} else if (turno.getTurId() == 1) {
														if (managerhorario
																.existeGuardiaXturnoMNoc(
																		cab_id,
																		restDays(fechainicial),
																		guardia.getGuaCedula()) == 0) {
															almacenarDetalles(
																	guardia,
																	lugar,
																	turno,
																	fechainicial,
																	fechainicial,
																	cab_id);
															contador++;
															guardiaescogidos = managergest
																	.AllGuardiasMenosAgregado(guardia);
															break romper;
														}
													} else {
														almacenarDetalles(
																guardia, lugar,
																turno,
																fechainicial,
																fechainicial,
																cab_id);
														contador++;
														guardiaescogidos = managergest
																.AllGuardiasMenosAgregado(guardia);
														break romper;
													}
												} else if (guardia
														.getGuaControlAccesos() == true
														&& lugar.getLugControlAccesos() == true) {
													// agregar si es control
													// de// accesos y
													// enrolamiento
													if (turno.getTurId() == 3) {
														fechainicial = addDays(fechainicial);
														almacenarDetalles(
																guardia, lugar,
																turno,
																fechainicial,
																fechainicial,
																cab_id);
														fechainicial = restDays(fechainicial);
														contador++;
														guardiaescogidos = managergest
																.AllGuardiasMenosAgregado(guardia);
														break romper;
													} else if (turno.getTurId() == 1) {
														if (managerhorario
																.existeGuardiaXturnoMNoc(
																		cab_id,
																		restDays(fechainicial),
																		guardia.getGuaCedula()) == 0) {
															almacenarDetalles(
																	guardia,
																	lugar,
																	turno,
																	fechainicial,
																	fechainicial,
																	cab_id);
															contador++;
															guardiaescogidos = managergest
																	.AllGuardiasMenosAgregado(guardia);
															break romper;
														}
													} else {
														almacenarDetalles(
																guardia, lugar,
																turno,
																fechainicial,
																fechainicial,
																cab_id);
														contador++;
														guardiaescogidos = managergest
																.AllGuardiasMenosAgregado(guardia);
														break romper;
													}
												} else if (guardia
														.getGuaMotorizado() == true
														&& lugar.getLugCctv() == false
														&& guardia.getGuaCctv() == false
														&& lugar.getLugControlAccesos() == false
														&& lugar.getLugNombre() != "Instituto"
														&& lugar.getLugNombre() != "Centro de emprendimiento"
														&& lugar.getLugNombre() != "CCTV"
														&& lugar.getLugNombre() != "San Eloy"
														&& lugar.getLugNombre() != "Tanques de Agua"
														&& lugar.getLugNombre() != "Control 1") {
													// agregar si es
													// motorizado// en lugares
													// que no sean// los que se
													// describen
													if (turno.getTurId() == 3) {
														fechainicial = addDays(fechainicial);
														almacenarDetalles(
																guardia, lugar,
																turno,
																fechainicial,
																fechainicial,
																cab_id);
														fechainicial = restDays(fechainicial);
														contador++;
														guardiaescogidos = managergest
																.AllGuardiasMenosAgregado(guardia);
														break romper;
													} else if (turno.getTurId() == 1) {
														if (managerhorario
																.existeGuardiaXturnoMNoc(
																		cab_id,
																		restDays(fechainicial),
																		guardia.getGuaCedula()) == 0) {
															almacenarDetalles(
																	guardia,
																	lugar,
																	turno,
																	fechainicial,
																	fechainicial,
																	cab_id);
															contador++;
															guardiaescogidos = managergest
																	.AllGuardiasMenosAgregado(guardia);
															break romper;
														}
													} else {
														almacenarDetalles(
																guardia, lugar,
																turno,
																fechainicial,
																fechainicial,
																cab_id);
														contador++;
														guardiaescogidos = managergest
																.AllGuardiasMenosAgregado(guardia);
														break romper;
													}
												} else if (lugar
														.getLugControlAccesos() == false
														&& guardia
																.getGuaControlAccesos() == false
														&& guardia
																.getGuaChofer() == false
														&& guardia.getGuaCctv() == false
														&& lugar.getLugCctv() == false
														&& guardia
																.getGuaMotorizado() == false
														&& guardia
																.getGuaCasoEstudio() == false) {
													// agregar si no tiene//
													// referencia de ser
													// chofer// o control de
													// accesos o// cctv
													if (turno.getTurId() == 3) {
														fechainicial = addDays(fechainicial);
														almacenarDetalles(
																guardia, lugar,
																turno,
																fechainicial,
																fechainicial,
																cab_id);
														fechainicial = restDays(fechainicial);
														contador++;
														guardiaescogidos = managergest
																.AllGuardiasMenosAgregado(guardia);
														break romper;
													} else if (turno.getTurId() == 1) {
														System.out
																.println(fechanterior);
														if (managerhorario
																.existeGuardiaXturnoMNoc(
																		cab_id,
																		restDays(fechainicial),
																		guardia.getGuaCedula()) == 0) {
															almacenarDetalles(
																	guardia,
																	lugar,
																	turno,
																	fechainicial,
																	fechainicial,
																	cab_id);
															contador++;
															guardiaescogidos = managergest
																	.AllGuardiasMenosAgregado(guardia);
															break romper;
														}
													} else {
														almacenarDetalles(
																guardia, lugar,
																turno,
																fechainicial,
																fechainicial,
																cab_id);
														contador++;
														guardiaescogidos = managergest
																.AllGuardiasMenosAgregado(guardia);
														break romper;
													}
												}
											}
										}
									}
								}
								// libres
								// if(t.getTurId()== 4){
								// List<HgHorarioDet> hgHdet =
								// managerhorario.findAllGuardiasxFecha(fechainicial,addDays(fechainicial),cab_id);
								// for(HgHorarioDet ghdet: hgHdet){
								// if(managerhorario.existeGuardia(cab_id,fechainicial,fechanterior,g.getGuaCedula(),t.getTurId()).size()
								// == 0
								// &&
								// g.getGuaCedula().equals(ghdet.getHgGuardia().getGuaCedula()))
								// //si el dia anterior tambien tubo vacacion
								// almacenarDetallesLibre(g,fechainicial,fechainicial,cab_id);
								// }
								// }
							}
						}
					}
					// registro de guardias libres que no trabajan ese dia
					List<HgGuardia> guardiaslibres = managergest
							.findAllGuardias();
					List<HgHorarioDet> hgHdet = managerhorario
							.findAllGuardiasxFecha(fechainicial,
									addDays(fechainicial), cab_id);
					for (HgGuardia g1 : guardiaslibres) {
						for (HgHorarioDet ghdet : hgHdet) {
							if (!g1.getGuaCedula().equals(
									ghdet.getHgGuardia().getGuaCedula())) {
								if (managerhorario.existeGuardia(cab_id,
										fechainicial, g1.getGuaCedula()) == 0) {
									almacenarDetallesLibre(g1, fechainicial,
											fechainicial, cab_id);
									break;
								}
							}
						}
					}
					// la lista sea ordenada por dia ascendente como
					// descendente// distribuya
					if (numeropar(contadorlistas)) {
						guardiaescogidos = managergest.findAllGuardias();
					} else {
						guardiaescogidos = managergest.findAllGuardiasDesc();
					}
					contadorlistas++;
					fechainicial = addDays(fechainicial);
				}
			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error en la creación");
		}
		Mensaje.crearMensajeINFO("Se crearon"
				+ managerhorario.findAllHorariosDet().size() + " registros");
		System.out.println("---------------> numero de registros almacenados: "
				+ managerhorario.findAllHorariosDet().size());
	}

	public void metodasolkc(Integer cab_id) {
		List<HgLugare> lugares = managergest.findAllLugares();
		List<HgTurno> turnos = managergest.findAllTurnos();
		List<HgGuardia> guardias = managergest.findAllGuardias();
		try {
			for (HgGuardia guardia : guardias) {}
				Date fechainicial = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(sqlfechai));
				Date fechafinal = addDays(sqlfechaf);
				Integer dias = diasXFi_Ff();
				for (Integer numeroDia = 0; numeroDia <= dias; numeroDia++) {
					for (HgTurno turno : turnos) {
						for (HgLugare lugar : lugares) {
							for(Integer numeroGuardias=1; numeroGuardias<=lugar.getLugNroGuardias(); numeroGuardias++){
								HgGuardia guardiaAlmacenar = new HgGuardia();
								guardiaAlmacenar = obtenerGuardia(lugar,fechainicial, turno,numeroDia);
								almacenarDetalles(guardiaAlmacenar, lugar,turno, fechainicial,fechainicial, cab_id);
							}	
						}
					}
					//libres a ls q no trabaojr
					fechainicial = addDays(fechainicial);
					fechainicial = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(fechainicial));
				}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error en la creación");
		}
		Mensaje.crearMensajeINFO("Se crearon"
				+ managerhorario.findAllHorariosDet().size() + " registros");
		System.out.println("---------------> numero de registros almacenados: "
				+ managerhorario.findAllHorariosDet().size());
	}

	public void metodasotonito_fecha_lugar_turno_guardia(Integer cab_id) {
		List<HgLugare> lugares = managergest.findAllLugares();
		List<HgTurno> turnos = managergest.findAllTurnos();
		try {
			Date fechainicial = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(sqlfechai));
			Date fechafinal =  java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(sqlfechaf));
			Integer dias = diasXFi_Ff();
			for (Integer numeroDia = 0; numeroDia <= dias; numeroDia++) {
				for (HgLugare lugar : lugares) {
					if(averiguarDia(lugar, fechainicial)==true){
					for (HgTurno turno : turnos) {
						for(Integer numeroGuardias=1; numeroGuardias<=lugar.getLugNroGuardias(); numeroGuardias++){
							HgGuardia guardiaAlmacenar = new HgGuardia();
							boolean guardia = true;
							guardiaAlmacenar = obtenerGuardia(lugar,fechainicial, turno,numeroDia);
							if(guardiaAlmacenar.getGuaCedula() == null){
								guardia = false;
								HorarioDet hdet = new HorarioDet(turno, lugar, fechainicial);
								listaSinGuardias.add(hdet);
							}
							if(guardia){
								if(numeroDia!=26){
									if(managerhorario.guardiaPendienteByID(guardiaAlmacenar.getGuaCedula())!=null){
										almacenarDetalles(guardiaAlmacenar, lugar,turno, fechainicial,fechainicial, cab_id);
										managerhorario.EliminarGuardiaPendienteLibre(guardiaAlmacenar);
									}else{
										almacenarDetalles(guardiaAlmacenar, lugar,turno, fechainicial,fechainicial, cab_id);
									}
								}else{
									almacenarDetalles(guardiaAlmacenar, lugar,turno, fechainicial,fechainicial, cab_id);
									managerhorario.insertarGuardiaPendienteLibre(guardiaAlmacenar);
								}
							}	
					}
				}
					}
				}
				fechainicial = addDays(fechainicial);
				fechainicial = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(fechainicial));
			}
			numeroRegistrosTotal = ((managergest.findAllLugares().size() * 3 ) * (dias+1)) - 24;
			numeroRegistrosCreados= managerhorario.findAllHorariosDetXIdCab(cab_id).size();
			numeroLugaresVacios = listaSinGuardias.size();
			System.out.println("---------------> numero de registros almacenados: "+ numeroRegistrosCreados);
			System.out.println("---------------> posiciones donde no se registraron: "+numeroLugaresVacios+" ");
			for(Integer i =0; i<listaSinGuardias.size();i++){
						System.out.println(listaSinGuardias.get(i).getLugares().getLugNombre().toString());
						System.out.println(listaSinGuardias.get(i).getTurnos().getTurDescripcion());
						System.out.println(listaSinGuardias.get(i).getHdetFechaInicio());
						System.out.println("--------------------------------------");
			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error en la creación");
		}
	}
	/**
	 * Metodo para saber que dias se puede trabajar dependiendo del boolean y el dia
	 * @param lugar
	 * @param fechainicial
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public boolean averiguarDia(HgLugare lugar, Date fechainicial){
		boolean resultado= true;
		if(lugar.getLugLunes() == false && fechainicial.getDay()==1){
			resultado=false;
		}if(lugar.getLugMartes() == false && fechainicial.getDay()==2){
			resultado=false;
		}if(lugar.getLugMiercoles() == false && fechainicial.getDay()==3){
			resultado=false;
		}if(lugar.getLugJueves() == false && fechainicial.getDay()==4){
			resultado=false;
		}if(lugar.getLugViernes() == false && fechainicial.getDay()==5){
			resultado=false;
		}if(lugar.getLugSabado() == false && fechainicial.getDay()==6){
			resultado=false;
		}if(lugar.getLugDomingo() == false && fechainicial.getDay()==0){
			resultado=false;
		}		
		return resultado;
	}
	
	public void metodasotonito_respaldo(Integer cab_id) {
		List<HgLugare> lugares = managergest.findAllLugares();
		List<HgTurno> turnos = managergest.findAllTurnos();
		try {
				Integer dias = diasXFi_Ff();
				for (Integer numeroDias = 0; numeroDias <= dias; numeroDias++) {
					Date fechainicial = sqlfechai;
					Date fechafinal = addDays(sqlfechaf);
					fechainicial = java.sql.Date.valueOf(new SimpleDateFormat(
							"yyyy-MM-dd").format(fechainicial));
					if (!fechainicial.after(fechafinal)) {
						Integer contador = 1;
						for (HgTurno turno : turnos) {// turnos
							for (HgLugare lugar : lugares) {
							if (contador <= lugar.getLugNroGuardias()) {
								boolean bandera = false;
								HgGuardia guardiaAlmacenar = new HgGuardia();
								if (turno.getTurId() == 3) {
									while (bandera == false) {
										guardiaAlmacenar = obtenerGuardia(lugar,fechainicial, turno,numeroDias);
										almacenarDetalles(guardiaAlmacenar, lugar,turno, fechainicial,fechainicial, cab_id);
										bandera = true;
										contador++;
									}
								} else if (turno.getTurId() == 1) {
									while (bandera == false) {
										guardiaAlmacenar = obtenerGuardia(lugar, fechainicial, turno,numeroDias );
											almacenarDetalles(guardiaAlmacenar,lugar, turno, fechainicial,fechainicial, cab_id);
											bandera = true;
											contador++;
									}
								} else {
									while (bandera == false) {
										guardiaAlmacenar = obtenerGuardia(lugar,fechainicial, turno,numeroDias);
										almacenarDetalles(guardiaAlmacenar, lugar,turno, fechainicial, fechainicial,cab_id);
										bandera = true;
										contador++;
									}
								}
							}
						}
					}
					// // registro de guardias libres que no trabajan ese dia
					// List<HgGuardia> guardiaslibres =
					// managergest.findAllGuardias();
					// List<HgHorarioDet> hgHdet =
					// managerhorario.findAllGuardiasxFecha(fechainicial,addDays(fechainicial),
					// cab_id);
					// for (HgGuardia g1 : guardiaslibres) {
					// for (HgHorarioDet ghdet : hgHdet) {
					// if (!g1.getGuaCedula().equals(
					// ghdet.getHgGuardia().getGuaCedula())) {
					// if
					// (managerhorario.existeGuardia(cab_id,fechainicial,g1.getGuaCedula()).size()
					// == 0) {
					// almacenarDetallesLibre(g1, fechainicial,fechainicial,
					// cab_id);
					// break;
					// }
					// }
					// }
					// }
					fechainicial = addDays(fechainicial);
				}
			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error en la creación");
		}
		Mensaje.crearMensajeINFO("Se crearon"
				+ managerhorario.findAllHorariosDet().size() + " registros");
		System.out.println("---------------> numero de registros almacenados: "
				+ managerhorario.findAllHorariosDet().size());
	}

	private HgGuardia obtenerGuardia(HgLugare lugar, Date fecha,HgTurno turno,Integer numeroDias) {
		HgGuardia guardiaelegido = new HgGuardia();
		HgGuardiasPendiente guardiaelegidoPendiente = new HgGuardiasPendiente();
		List<HgGuardia> guardiasDisponibles = new ArrayList<HgGuardia>();
		List<HgGuardiasPendiente> guardiasDisponiblesPendientes = new ArrayList<HgGuardiasPendiente>();
		if (numeroDias%3!=0){
			guardiasDisponibles = managergest.findGuardiasDisponibles(fecha);
			guardiaelegido = obtenerGuardiaCompatible(lugar, fecha, turno, guardiasDisponibles,numeroDias);
			}
		if (guardiaelegido.getGuaCedula() == null){
			guardiasDisponibles = managergest.findGuardiasDisponiblesSinLibres(fecha);
			guardiaelegido=obtenerGuardiaCompatible(lugar, fecha, turno, guardiasDisponibles,numeroDias);
		}
		if (guardiaelegido.getGuaCedula() == null){
			guardiasDisponibles = managergest.findGuardiasUltimosDosDiasLibres(fecha);
			guardiaelegido = obtenerGuardiaCompatible(lugar, fecha, turno, guardiasDisponibles,numeroDias);
		}
		if (guardiaelegido.getGuaCedula() == null){
			guardiasDisponiblesPendientes = managerhorario.findAllGuardiasPendientes();
			//setear
			guardiaelegidoPendiente = obtenerGuardiaCompatiblePendiente(lugar, fecha, turno, guardiasDisponiblesPendientes,numeroDias);
			guardiaelegido = setearHgGuardia(guardiaelegidoPendiente);
			}
		return guardiaelegido;
	}

	private HgGuardia obtenerGuardiaCompatible(HgLugare lugar, Date fechainicial, HgTurno turno, List<HgGuardia> guardiasDisponibles,Integer numeroDias) {
		HgGuardia guardiaelegido = new HgGuardia();
		for (HgGuardia guardia : guardiasDisponibles) {
			Boolean guardiaAplica=true;
			Integer vecestrabajo = 0;
			Integer diasTrabajados = 5;
//			if(managerhorario.trabajoLugTurnDiaAnterior(guardia,turno,restDays(fechainicial))== 0){
			if ((managerhorario.existeGuardia(cab_id, fechainicial, guardia.getGuaCedula()) != 1)) {
			if (managerhorario.trabajoDiaAnterior(guardia,restDays(fechainicial)) == 1) {
				vecestrabajo = managerhorario.findNumDiasxGuardia(guardia,restDays(fechainicial), rest5Days(restDays(fechainicial)));
			}else if(managerhorario.trabajoDiaAnterior(guardia,restDays(restDays(fechainicial))) == 1){
				vecestrabajo = managerhorario.findNumDiasxGuardia(guardia,restDays(restDays(fechainicial)),rest5Days(restDays(restDays(fechainicial))));
			}
			if (vecestrabajo < diasTrabajados) {
				if(managerhorario.existeGuardiaXturnoMNoc(cab_id,restDays(fechainicial),guardia.getGuaCedula()) == 1 && turno.getTurId()==1)
					guardiaAplica=false;
				if (lugar.getLugCctv() == true && guardia.getGuaCctv() != true) 
					guardiaAplica=false;
				if(lugar.getLugCctv() == true && sinCCTV(guardiasDisponibles)==false)
					guardiaAplica=true;
				if (lugar.getLugControlAccesos() == true && guardia.getGuaControlAccesos() != true) 
					guardiaAplica=false;
				if(lugar.getLugControlAccesos() == true && sinControlAccesos(guardiasDisponibles)==false)
					guardiaAplica=true;
				if (guardia.getGuaCasoEstudio() == true && (fechainicial.getDay() == 0 || fechainicial.getDay() == 6)) 
					guardiaAplica=false;
				if (guardia.getGuaCasoNocturno() == true && turno.getTurId()==3) 
					guardiaAplica=false;
				if ( (lugar.getLugNombre() == "Instituto" || lugar.getLugNombre() == "Centro de emprendimiento"
						|| lugar.getLugNombre() == "San Eloy" || lugar.getLugNombre() == "CCTV" 
						|| lugar.getLugNombre() == "Tanques de Agua" || lugar.getLugNombre() == "Control 1")
						&& guardia.getGuaMotorizado() == true )
					guardiaAplica=false;
				if(guardiaAplica){
					guardiaelegido = guardia;
					break;
				}
			}
		}
		}
		return guardiaelegido;
	}
	
	private HgGuardiasPendiente obtenerGuardiaCompatiblePendiente(HgLugare lugar, Date fechainicial, HgTurno turno, List<HgGuardiasPendiente> guardiasDisponiblesPendientes,Integer numeroDias) {
		HgGuardiasPendiente guardiaelegido = new HgGuardiasPendiente();
		for (HgGuardiasPendiente guardia : guardiasDisponiblesPendientes) {
			Boolean guardiaAplica=true;
			Integer vecestrabajo = 0;
			Integer diasTrabajados = 6;
//			if(managerhorario.trabajoLugTurnDiaAnterior(guardia,turno,restDays(fechainicial))== 0){
			if ((managerhorario.existeGuardia(cab_id, fechainicial, guardia.getGuaCedula()) != 1)) {
			if (managerhorario.trabajoDiaAnteriorPendiente(guardia,restDays(fechainicial)) == 1) {
				vecestrabajo = managerhorario.findNumDiasxGuardiaPendiente(guardia,restDays(fechainicial), rest5Days(restDays(fechainicial)));
			}else if(managerhorario.trabajoDiaAnteriorPendiente(guardia,restDays(restDays(fechainicial))) == 1){
				vecestrabajo = managerhorario.findNumDiasxGuardiaPendiente(guardia,restDays(restDays(fechainicial)),rest5Days(restDays(restDays(fechainicial))));
			}
			if (vecestrabajo < diasTrabajados) {
				if(managerhorario.existeGuardiaXturnoMNoc(cab_id,restDays(fechainicial),guardia.getGuaCedula()) == 1 && turno.getTurId()==1)
					guardiaAplica=false;
				if (lugar.getLugCctv() == true && guardia.getGuaCctv() != true) 
					guardiaAplica=false;
				if(lugar.getLugCctv() == true && sinCCTVPendiente(guardiasDisponiblesPendientes)==false)
					guardiaAplica=true;
				if (lugar.getLugControlAccesos() == true && guardia.getGuaControlAccesos() != true) 
					guardiaAplica=false;
				if(lugar.getLugControlAccesos() == true && sinControlAccesosPendiente(guardiasDisponiblesPendientes)==false)
					guardiaAplica=true;
				if (guardia.getGuaCasoEstudio() == true && (fechainicial.getDay() == 0 || fechainicial.getDay() == 6)) 
					guardiaAplica=false;
				if (guardia.getGuaCasoNocturno() == true && turno.getTurId()==3) 
					guardiaAplica=false;
				if ( (lugar.getLugNombre() == "Instituto" || lugar.getLugNombre() == "Centro de emprendimiento"
						|| lugar.getLugNombre() == "San Eloy" || lugar.getLugNombre() == "CCTV" 
						|| lugar.getLugNombre() == "Tanques de Agua" || lugar.getLugNombre() == "Control 1")
						&& guardia.getGuaMotorizado() == true )
					guardiaAplica=false;
				if(guardiaAplica){
					guardiaelegido = guardia;
					break;
				}
			}
		}
		}
		return guardiaelegido;
	}
	
	private HgGuardia setearHgGuardia(HgGuardiasPendiente guardiapendiente){
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
	
	private boolean sinCCTV(List<HgGuardia> hgGuardia){
		boolean resultado=true;
		for(HgGuardia guardia : hgGuardia){
			if(guardia.getGuaCctv()==false)
				resultado = false;
		}
		return resultado;
	}
	
	private boolean sinCCTVPendiente(List<HgGuardiasPendiente> hgGuardiasPendientes){
		boolean resultado=true;
		for(HgGuardiasPendiente guardia : hgGuardiasPendientes){
			if(guardia.getGuaCctv()==false)
				resultado = false;
		}
		return resultado;
	}
	
	private boolean sinControlAccesos(List<HgGuardia> hgGuardia){
		boolean resultado=true;
		for(HgGuardia guardia : hgGuardia){
			if(guardia.getGuaControlAccesos()==false)
				resultado = false;
		}
		return resultado;
	}
	
	private boolean sinControlAccesosPendiente(List<HgGuardiasPendiente> hgGuardia){
		boolean resultado=true;
		for(HgGuardiasPendiente guardia : hgGuardia){
			if(guardia.getGuaControlAccesos()==false)
				resultado = false;
		}
		return resultado;
	}

	private HgGuardia obtenerGuardia_respaldo(HgLugare lugar, Date fechainicial,HgTurno turno) {
		List<HgGuardia> guardiaescogidos = managergest.findAllGuardias();
		HgGuardia guardiaelegido = new HgGuardia();
		for (HgGuardia guardia : guardiaescogidos) {
			System.out.println("Fecha de selección----------------->"+fechainicial);
//			if(managerhorario.trabajoLugTurnDiaAnterior(guardia,turno,restDays(fechainicial))== 0){
			Integer valor = 0;
			if ((managerhorario.existeGuardia(cab_id, fechainicial, guardia.getGuaCedula()) == 1)) {
				Integer vecestrabajo = 0;
				Integer diasTrabajados = 5;// número de días al que debe trabajar// el//// usuario
				if (managerhorario.trabajoDiaAnterior(guardia,restDays(fechainicial)) == 1) {
					vecestrabajo = managerhorario.findNumDiasxGuardia(guardia,restDays(fechainicial), rest5Days(restDays(fechainicial)));
					if (vecestrabajo < diasTrabajados){
						valor = 1;
					}
			}else if(managerhorario.trabajoDiaAnterior(guardia,restDays(restDays(fechainicial))) == 1){
					vecestrabajo = managerhorario.findNumDiasxGuardia(guardia,restDays(restDays(fechainicial)),rest5Days(restDays(restDays(fechainicial))));
					if (vecestrabajo < diasTrabajados){
						valor = 1;
					}
			}else{
				valor = 1;
				}
			if (valor == 1) {
				// numero// de// veces// que// trabaja// contador// devuelve//// valor
					// si trabaja el guardia en ese dia
					if (guardia.getGuaEstado().equals("A")) {
						if (guardia.getGuaCasoEstudio() == true&& (fechainicial.getDay() != 0 || fechainicial.getDay() != 1)) {
							// agregar caso si es// estudio libres sabados y////// domingos
							if (turno.getTurId() != 1)  {
							guardiaelegido = guardia;
							break;
							}else if(managerhorario.existeGuardiaXturnoMNoc(cab_id,restDays(fechainicial),guardia.getGuaCedula()) == 0){
								guardiaelegido = guardia;
								break;
							}
						} else if (guardia.getGuaCasoNocturno() == true&& lugar.getLugCctv() == false
								&& guardia.getGuaCctv() == false&& !turno.getTurId().equals(1)) {
							// agregar caso si trabajan// solo turnos vespertino// o// nocturno
							if (turno.getTurId() != 1)  {
								guardiaelegido = guardia;
								break;
								}else if(managerhorario.existeGuardiaXturnoMNoc(cab_id,restDays(fechainicial),guardia.getGuaCedula()) == 0){
									guardiaelegido = guardia;
									break;
								}
						} else if (guardia.getGuaCctv() == true && guardia.getGuaChofer() == true&& lugar.getLugCctv() == true) {
							// agregar si es CCTV y// chofer
							if (turno.getTurId() != 1)  {
								guardiaelegido = guardia;
								break;
								}else if(managerhorario.existeGuardiaXturnoMNoc(cab_id,restDays(fechainicial),guardia.getGuaCedula()) == 0){
									guardiaelegido = guardia;
									break;
								}
						} else if (guardia.getGuaControlAccesos() == true&& lugar.getLugControlAccesos() == true) {
							// agregar si es control de// accesos y enrolamiento
							if (turno.getTurId() != 1)  {
								guardiaelegido = guardia;
								break;
								}else if(managerhorario.existeGuardiaXturnoMNoc(cab_id,restDays(fechainicial),guardia.getGuaCedula()) == 0){
									guardiaelegido = guardia;
									break;
								}
						} else if (guardia.getGuaMotorizado() == true&& lugar.getLugCctv() == false
								&& guardia.getGuaCctv() == false&& lugar.getLugControlAccesos() == false
								&& lugar.getLugNombre() != "Instituto"&& lugar.getLugNombre() != "Centro de emprendimiento"
								&& lugar.getLugNombre() != "CCTV"&& lugar.getLugNombre() != "San Eloy"
								&& lugar.getLugNombre() != "Tanques de Agua"&& lugar.getLugNombre() != "Control 1") {
							// agregar si es motorizado// en lugares que no// sean// los que se describen
							if (turno.getTurId() != 1)  {
								guardiaelegido = guardia;
								break;
								}else if(managerhorario.existeGuardiaXturnoMNoc(cab_id,restDays(fechainicial),guardia.getGuaCedula()) == 0){
									guardiaelegido = guardia;
									break;
								}
						} else if (lugar.getLugControlAccesos() == false&& guardia.getGuaControlAccesos() == false
								&& guardia.getGuaChofer() == false&& guardia.getGuaCctv() == false
								&& lugar.getLugCctv() == false&& guardia.getGuaMotorizado() == false
								&& guardia.getGuaCasoEstudio() == false) {
							// agregar si no tiene// referencia de ser chofer//// o control de accesos o// cctv
							if (turno.getTurId() != 1)  {
								guardiaelegido = guardia;
								break;
								}else if(managerhorario.existeGuardiaXturnoMNoc(cab_id,restDays(fechainicial),guardia.getGuaCedula()) == 0){
									guardiaelegido = guardia;
									break;
								}
						}
					}
				}
			}
		}
//		}
		return guardiaelegido;
	}
	
	public boolean numeropar(Integer contadorpar) {
		boolean r = false;
		if (contadorpar % 2 == 0) {
			r = true;
		} else {
			r = false;
		}
		return r;
	}

	// restriccion1
	public Integer restriccion1(Integer[][][][] decicion) {

		Integer R1 = 0;
		Integer nroGuardias = managergest.findAllGuardias().size();
		Integer nroPuestos = managergest.findAllLugares().size();
		Integer nroTurnos = managergest.findAllTurnos().size();

		List<HgLugare> lisHgLugar = managergest.findAllLugares();
		List<DiasMG> liDias = new ArrayList<DiasMG>();
		List<HgTurno> lisHgTurno = managergest.findAllTurnos();
		List<HgGuardia> lisHgGuardia = managergest.findAllGuardias();

		int diaId = 0;
		String diaNombre = "";
		int semanaId = 0;
		String semanaNombre = "";

		DiasMG dia = new DiasMG(diaId, diaNombre);
		dia.setDiaId(1);
		dia.setDiaNombre("Lunes");
		liDias.add(dia);
		diaId++;
		DiasMG dia2 = new DiasMG(diaId, diaNombre);
		dia2.setDiaId(2);
		dia2.setDiaNombre("Martes");
		liDias.add(dia2);
		diaId++;
		DiasMG dia3 = new DiasMG(diaId, diaNombre);
		dia3.setDiaId(3);
		dia3.setDiaNombre("Miercoles");
		liDias.add(dia3);
		diaId++;
		DiasMG dia4 = new DiasMG(diaId, diaNombre);
		dia4.setDiaId(4);
		dia4.setDiaNombre("Jueves");
		liDias.add(dia4);
		diaId++;
		DiasMG dia5 = new DiasMG(diaId, diaNombre);
		dia5.setDiaId(5);
		dia5.setDiaNombre("Viernes");
		liDias.add(dia5);
		diaId++;
		DiasMG dia6 = new DiasMG(diaId, diaNombre);
		dia6.setDiaId(6);
		dia6.setDiaNombre("Sabado");
		liDias.add(dia6);
		diaId++;
		DiasMG dia7 = new DiasMG(diaId, diaNombre);
		dia7.setDiaId(7);
		dia7.setDiaNombre("Domingo");
		liDias.add(dia7);

		Integer nroDias = liDias.size();

		for (DiasMG diaMG : liDias) {
			for (HgLugare lugares : lisHgLugar) {
				for (HgTurno turno : lisHgTurno) {
					for (HgGuardia guardias : lisHgGuardia) {
						R1 += Math
								.abs(decicion[nroGuardias][nroPuestos][nroTurnos][nroDias]
										- lugares.getLugNroGuardias());
					}
				}
			}
		}
		return R1;
	}

	public void metodaso1(Integer cab_id) {
//		List<HgGuardia> lisHgGuardia = managergest.findAllGuardias();
//		List<HgGuardia> lisHgGuardiaDesc = managergest.findAllGuardiasDesc();
//		List<HgTurno> lisHgTurno = managergest.findAllTurnos();
//		List<HgLugare> lisHgLugar = managergest.findAllLugares();
//		List<Guardias> liguaEscogidos = new ArrayList<Guardias>();
//
//		List<Guardias> ligua = new ArrayList<Guardias>();
//		List<Turnos> liturn = new ArrayList<Turnos>();
//		List<Lugares> liluga = new ArrayList<Lugares>();
//		List<Dias> liDias = new ArrayList<Dias>();
//		List<Semanas> liSemanas = new ArrayList<Semanas>();
//		List<HorarioDet> liHdet = new ArrayList<HorarioDet>();
//
////		for (HgGuardia g : lisHgGuardia) {
////			Guardias guardias = new Guardias(g.getGuaCedula(),
////					g.getGuaApellido(), g.getGuaCasoEstudio(),
////					g.getGuaCasoNocturno(), g.getGuaCasoTurno(),
////					g.getGuaCctv(), g.getGuaCelular(), g.getGuaChofer(),
////					g.getGuaCiudad(), g.getGuaControlAccesos(),
////					g.getGuaCorreo(), g.getGuaDireccion(), g.getGuaEstado(),
////					g.getGuaEstadoCivil(), g.getGuaFechanac(),
////					g.getGuaMotorizado(), g.getGuaNombre(), g.getGuaSexo(),
////					g.getGuaTelefono(), g.getGuaTipoSangre());
////			ligua.add(guardias);
////
////		}
//		System.out.println("-------------->Tamaño de la lista guardias: "
//				+ ligua.size());
//
//		for (HgTurno t : lisHgTurno) {
//			Turnos turnos = new Turnos(t.getTurId(), t.getTurDescripcion(),
//					t.getTurEstado(), t.getTurHoraFin(), t.getTurHoraInicio(),
//					ligua);
//			liturn.add(turnos);
//
//		}
//		System.out.println("-------------->Tamaño de la lista turnos: "
//				+ liturn.size());
//
//		for (HgLugare l : lisHgLugar) {
//			Lugares lugares = new Lugares(l.getLugId(), l.getLugCctv(),
//					l.getLugCiudad(), l.getLugControlAccesos(),
//					l.getLugEstado(), l.getLugNombre(), l.getLugNroGuardias(),
//					liturn);
//			liluga.add(lugares);
//		}
//		System.out.println("-------------->Tamaño de la lista lugares: "
//				+ liluga.size());
//
//		int diaId = 0;
//		String diaNombre = "";
//		int semanaId = 0;
//		String semanaNombre = "";
//
//		Dias dia = new Dias(diaId, diaNombre, liluga);
//		dia.setDiaId(1);
//		dia.setDiaNombre("Lunes");
//		dia.setLugares(liluga);
//		liDias.add(dia);
//		diaId++;
//		Dias dia2 = new Dias(diaId, diaNombre, liluga);
//		dia2.setDiaId(2);
//		dia2.setDiaNombre("Martes");
//		dia2.setLugares(liluga);
//		liDias.add(dia2);
//		diaId++;
//		// Dias dia3 = new Dias(diaId, diaNombre, liluga);
//		// dia3.setDiaId(3);
//		// dia3.setDiaNombre("Miercoles");
//		// dia3.setLugares(liluga);
//		// liDias.add(dia3);
//		// diaId++;
//		// Dias dia4 = new Dias(diaId, diaNombre, liluga);
//		// dia4.setDiaId(4);
//		// dia4.setDiaNombre("Jueves");
//		// dia4.setLugares(liluga);
//		// liDias.add(dia4);
//		// diaId++;
//		// Dias dia5 = new Dias(diaId, diaNombre, liluga);
//		// dia5.setDiaId(5);
//		// dia5.setDiaNombre("Viernes");
//		// dia5.setLugares(liluga);
//		// liDias.add(dia5);
//		// diaId++;
//		// Dias dia6 = new Dias(diaId, diaNombre, liluga);
//		// dia6.setDiaId(6);
//		// dia6.setDiaNombre("Sabado");
//		// dia6.setLugares(liluga);
//		// liDias.add(dia6);
//		// diaId++;
//		// Dias dia7 = new Dias(diaId, diaNombre, liluga);
//		// dia7.setDiaId(7);
//		// dia7.setDiaNombre("Domingo");
//		// dia7.setLugares(liluga);
//		// liDias.add(dia7);
//
//		System.out.println("-------------->Tamaño de la lista dias: "
//				+ liDias.size());
//
//		Semanas semana = new Semanas(semanaId, semanaNombre, liDias);
//		semana.setSemanaId(1);
//		semana.setSemanaNombre("Primera");
//		semana.setDias(liDias);
//		liSemanas.add(semana);
//		semanaId++;
//		// Semanas semana1 = new Semanas(semanaId, semanaNombre, liDias);
//		// semana1.setSemanaId(2);
//		// semana1.setSemanaNombre("Segunda");
//		// semana1.setDias(liDias);
//		// liSemanas.add(semana1);
//		// semana.setSemanaId(3);
//		// semana.setSemanaNombre("Tercera");
//		// semana.setDias(liDias);
//		// semana.setSemanaId(4);
//		// semana.setSemanaNombre("Cuarta");
//		// semana.setDias(liDias);
//		// semana.setSemanaId(5);
//		// semana.setSemanaNombre("Quinta");
//		// semana.setDias(liDias);
//
//		System.out.println(semana.getSemanaNombre());
//		System.out.println("-------------->Tamaño de la lista semanas: "
//				+ liSemanas.size());
//		for (Semanas s : liSemanas) {
//			System.out.println(s.getSemanaNombre());
//			for (Dias d : liDias) {
//				System.out.println(d.getDiaNombre());
//				for (Lugares l : liluga) {
//					Integer contador = 1;
//					System.out.println(l.getLugNombre());
//					for (Turnos t : liturn) {
//						System.out.println(t.getTurDescripcion());
//						romper: for (Guardias g : ligua) {
//							if (revisarGuaXsem(g, t, l, d, s, ligua, liturn,
//									liluga, liDias, liSemanas, liHdet) == false) {
//								System.out.println("Contador: " + contador
//										+ "  Nro. Guardias por lugar: "
//										+ l.getLugNroGuardias());
//								if (contador <= l.getLugNroGuardias()) {
//									if (g.getGuaEstado().equals("A")) {
//										if (g.getGuaCasoEstudio() == true
//												&& l.getLugCctv() == false
//												&& g.getGuaCctv() == false
//												&& (s.getSemanaNombre() == "Sabado" || s
//														.getSemanaNombre() == "Domingo")) {
//											// agregar caso si es estudio libres
//											// sabados y domingos
//											System.out
//													.println("----------------> datos:  "
//															+ s.getSemanaNombre()
//															+ " "
//															+ d.getDiaNombre()
//															+ " "
//															+ l.getLugNombre()
//															+ " "
//															+ t.getTurDescripcion()
//															+ " "
//															+ g.getGuaNombre());
//											HorarioDet hdet = new HorarioDet(g,
//													t, l, d, s);
//											liHdet.add(hdet);
//											liguaEscogidos.add(g);
//											contador++;
//											break romper;
//										} else if (g.getGuaCasoNocturno() == true
//												&& l.getLugCctv() == false
//												&& g.getGuaCctv() == false
//												&& !t.getTurId().equals(1)) {
//											// agregar caso si trabajan solo
//											// turnos vespertino o nocturno
//											System.out
//													.println("----------------> datos:  "
//															+ s.getSemanaNombre()
//															+ " "
//															+ d.getDiaNombre()
//															+ " "
//															+ l.getLugNombre()
//															+ " "
//															+ t.getTurDescripcion()
//															+ " "
//															+ g.getGuaNombre());
//											HorarioDet hdet = new HorarioDet(g,
//													t, l, d, s);
//											liHdet.add(hdet);
//											liguaEscogidos.add(g);
//											contador++;
//											break romper;
//										} else if (g.getGuaCctv() == true
//												&& g.getGuaChofer() == true
//												&& l.getLugCctv() == true) {
//											// agregar si es CCTV
//											System.out
//													.println("----------------> datos:  "
//															+ s.getSemanaNombre()
//															+ " "
//															+ d.getDiaNombre()
//															+ " "
//															+ l.getLugNombre()
//															+ " "
//															+ t.getTurDescripcion()
//															+ " "
//															+ g.getGuaNombre());
//											HorarioDet hdet = new HorarioDet(g,
//													t, l, d, s);
//											liHdet.add(hdet);
//											liguaEscogidos.add(g);
//											contador++;
//											break romper;
//										} else if (g.getGuaControlAccesos() == true
//												&& l.getLugControlAccesos() == true
//												&& l.getLugId() == 14
//												&& l.getLugCctv() == false
//												&& g.getGuaCctv() == false) {
//											// agregar si es control de accesos
//											// y enrolamiento
//											System.out
//													.println("----------------> datos:  "
//															+ s.getSemanaNombre()
//															+ " "
//															+ d.getDiaNombre()
//															+ " "
//															+ l.getLugNombre()
//															+ " "
//															+ t.getTurDescripcion()
//															+ " "
//															+ g.getGuaNombre());
//											HorarioDet hdet = new HorarioDet(g,
//													t, l, d, s);
//											liHdet.add(hdet);
//											liguaEscogidos.add(g);
//											contador++;
//											break romper;
//										} else if (g.getGuaMotorizado() == true
//												&& l.getLugCctv() == false
//												&& g.getGuaCctv() == false
//												&& l.getLugControlAccesos() == false
//												&& l.getLugNombre() != "Instituto"
//												&& l.getLugNombre() != "Centro de emprendimiento"
//												&& l.getLugNombre() != "CCTV"
//												&& l.getLugNombre() != "San Eloy"
//												&& l.getLugNombre() != "Tanques de Agua"
//												&& l.getLugNombre() != "Control 1") {
//											// agregar si es motorizado y solo 3
//											// y en cualquier sitio
//											System.out
//													.println("----------------> datos:  "
//															+ s.getSemanaNombre()
//															+ " "
//															+ d.getDiaNombre()
//															+ " "
//															+ l.getLugNombre()
//															+ " "
//															+ t.getTurDescripcion()
//															+ " "
//															+ g.getGuaNombre());
//											HorarioDet hdet = new HorarioDet(g,
//													t, l, d, s);
//											liHdet.add(hdet);
//											liguaEscogidos.add(g);
//											contador++;
//											break romper;
//										} else if (
//										// l.getLugControlAccesos() == false
//										l.getLugCctv() == false
//												// && g.getGuaControlAccesos()
//												// == false
//												&& g.getGuaChofer() == false
//												&& g.getGuaCctv() == false
//												&& g.getGuaCasoNocturno() == false
//												&& g.getGuaMotorizado() == false
//												&& g.getGuaCasoEstudio() == false) {
//											// agregar si no tiene referencia
//											System.out
//													.println("----------------> datos:  "
//															+ s.getSemanaNombre()
//															+ " "
//															+ d.getDiaNombre()
//															+ " "
//															+ l.getLugNombre()
//															+ " "
//															+ t.getTurDescripcion()
//															+ " "
//															+ g.getGuaNombre());
//											HorarioDet hdet = new HorarioDet(g,
//													t, l, d, s);
//											liHdet.add(hdet);
//											liguaEscogidos.add(g);
//											contador++;
//											break romper;
//										}
//									}
//								}
//							}
//						}
//						t.setGuardias(liguaEscogidos);
//						System.out
//								.println("---------------->Nro de guardias almacenados "
//										+ t.getGuardias().size());
//					}
//				}
//				ligua.clear();
////				for (HgGuardia g : lisHgGuardiaDesc) {
////					Guardias guardias = new Guardias(g.getGuaCedula(),
////							g.getGuaApellido(), g.getGuaCasoEstudio(),
////							g.getGuaCasoNocturno(), g.getGuaCasoTurno(),
////							g.getGuaCctv(), g.getGuaCelular(),
////							g.getGuaChofer(), g.getGuaCiudad(),
////							g.getGuaControlAccesos(), g.getGuaCorreo(),
////							g.getGuaDireccion(), g.getGuaEstado(),
////							g.getGuaEstadoCivil(), g.getGuaFechanac(),
////							g.getGuaMotorizado(), g.getGuaNombre(),
////							g.getGuaSexo(), g.getGuaTelefono(),
////							g.getGuaTipoSangre());
////					ligua.add(guardias);
////				}
//			}
//		}
	}

	public boolean revisarGuaXsem(Guardias guard, Turnos tu, Lugares lu,
			Dias di, Semanas se, List<Guardias> gua, List<Turnos> liturn,
			List<Lugares> liluga, List<Dias> liDias, List<Semanas> liSemanas,
			List<HorarioDet> liHdet) {
		boolean r = false;
		for (Semanas s : liSemanas) {
			if (se.getSemanaId().equals(s.getSemanaId())) {
				for (Dias d : liDias) {
					if (d.getDiaId().equals(di.getDiaId())) {
						for (Lugares l : liluga) {
							if (l.getLugId().equals(lu.getLugId())) {
								for (Turnos t : liturn) {
									if (t.getTurId().equals(tu.getTurId()))
										for (HorarioDet hdet : liHdet) {
											if (hdet.getGuardias()
													.getGuaCedula()
													.equals(guard
															.getGuaCedula())
													&& (hdet.getDia()
															.getDiaId()
															.equals(d
																	.getDiaId()))) {
												r = true;
												break;
											}
										}
								}
							}
						}
					}
				}
			}
		}
		return r;
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
			System.out
					.println("------------------> Error almacenarDetalles HorarioCDBean:");
			e.printStackTrace();
			Mensaje.crearMensajeERROR("Error al almacenar detalle: "
					+ e.getMessage());
		}
		return hghorariodetalle;
	}

	/**
	 * metodo para almacenar los guardias libres
	 * 
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
			System.out
					.println("------------------> Error almacenarDetalles HorarioCDBean:");
			e.printStackTrace();
			Mensaje.crearMensajeERROR("Error al almacenar detalle: "
					+ e.getMessage());
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
	 * Metodo para reducir en un dia cada fecha
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
	 * Metodo para aumentar en un dia cada fecha
	 * 
	 * @param date
	 * @return
	 */
	public static Date addDays2(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 2); // minus number would decrement the days
		return cal.getTime();
	}

	/**
	 * accion para abrir el dialogo
	 * 
	 */
	public void abrirDialog() {
		if (horcab_fechainicio.after(horcab_fechafin)) {
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
			usuariologeado = horcab_usuarioreg;
			edicion = true;
			ediciontipo = false;
			getListaHorarioDet().clear();
			getListaHorarioDet().addAll(
					managerhorario.findAllHorariosDetXIdCab(cab_id));
			return "hg_nhorario?faces-redirect=true";
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mainAg() throws Exception {
		System.out.println("ESTA GEENRANDOSE");
		String child = "";
		for (int i = 0; i <= 200; i++) {
			System.out.println("entra");
			// ag.poblacionInicial();
			ag.displayTable(ag.poblacionInicial());
			System.out.println("pasa por aqui1");
			ag.funcionObjetivo(ag.poblacionInicial());
			System.out.println("pasa por aqui2");
			ag.seleccion();
			System.out.println("pasa por aqui3");
			ag.cruce();
			System.out.println("pasa por aqui4");
			child = ag.mutar();
		}
		System.out.println(child);
	}
}
