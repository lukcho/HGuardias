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
import hguardias.model.dao.entities.Persona;
import hguardias.model.manager.ManagerCarga;
import hguardias.controller.access.SesionBean;
import hguardias.model.generic.Funciones;
import hguardias.model.dao.entities.HgGuardia;
import hguardias.model.dao.entities.HgHorarioCab;
import hguardias.model.dao.entities.HgHorarioDet;
import hguardias.model.dao.entities.HgLugare;
import hguardias.model.dao.entities.HgTurno;
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
	private Timestamp horcab_fecha_creacion;
	
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
	
	@Inject
	SesionBean ms;

	public horarioCDBean() {
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
		horcab_fecha_creacion = null;
		horcab_nombre = null;
		horcab_usuarioreg = null;
		edicion = false;
		ediciontipo = false;
		usuariologeado = "";
		listaHorarioCab = managerhorario.findAllHorariosCab();
		listaHorarioDet = managerhorario.findAllHorariosDet();
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
		String r="";
		try {
			fecha = new Date();
			sqlfechai = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(horcab_fechainicio));
			sqlfechaf = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(horcab_fechafin));
			horcab_fecha_creacion = new Timestamp(fecha.getTime());
			horcab_usuarioreg = usuariologeado.trim();
			Integer cab_id = managerhorario.ultimoOrdenCabecera();

			managerhorario.insertarHorarioCab(cab_id,sqlfechai, sqlfechaf,
					horcab_nombre.trim(), horcab_usuarioreg,horcab_fecha_creacion);

			metodaso(cab_id).isEmpty();
			Mensaje.crearMensajeINFO("Se creo satisfactoriamente");
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Registrado - Horario creado", null));
			horcab_id = null;
			horcab_fechainicio = null;
			horcab_fechafin = null;
			horcab_nombre = null;
			horcab_usuarioreg = null;
			horcab_fecha_creacion=null;
			date = null;
			getlistaHorarioCab().clear();
			getlistaHorarioCab().addAll(managerhorario.findAllHorariosCab());
			
			r= "hg_horarios?faces-redirect=true";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error al crear horario", null));
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), null));
		}
		return r;
	}

	@SuppressWarnings("deprecation")
	public String metodaso(Integer cab_id) {
		List<HgLugare> lugar = managergest.findAllLugares();
		List<HgTurno> turno = managergest.findAllTurnos();
		List<HgGuardia> guardia = managergest.findAllGuardias();
		List<HgGuardia> guardiaescogidos = managergest.findAllGuardias();

		Date fechainicial = sqlfechai;
		Date fechainicial1 = null;
		Integer chofer = 1;
		Integer motorizados = 1;
		Integer contador = 1;
		Integer cctv = 1;
		try {
			Integer dias= diasXFi_Ff();
			for(Integer a=0; a<=dias; a++){
				System.out.println("--------------------->dias "+dias+" cuantos faltan:"+a);
				System.out.println("--------------------->fechas: "+fechainicial+" "+getSqlfechaf());
				for(HgTurno t : turno){//turnos
					System.out.println("--------------------->turno: "+t.getTurDescripcion());
				for (HgLugare l : lugar){//lugares
					contador=1;
					System.out.println("--------------------->lugar: "+l.getLugNombre());
					if (contador<= l.getLugNroGuardias()){//controlar el nro de guardias que hay en el lugar
						System.out.println("--------------------->nro. guardias cont: "+contador+" nro. guardias en lugar: "+l.getLugNroGuardias());
						for (HgGuardia g : guardiaescogidos) {// eliminar a los guardias que ya han sido escogidos en esa semana
								if (g.getGuaEstado().equals("A")) {
									if (g.getGuaCasoEstudio() == true && (fechainicial.getDay() != 0 || fechainicial.getDay() != 1)) {
										// agregar caso si es estudio libres sabados y domingos
										   almacenarDetalles(g,l,t, fechainicial, fechainicial,cab_id);
										
									} else if (g.getGuaMotorizado() == true && motorizados <= 3) {
										// agregar si es motorizado y solo 3 y en cualquier sitio
										almacenarDetalles(g,l,t, fechainicial, fechainicial,cab_id);
										motorizados++;
										
									} else if (g.getGuaCasoNocturno() == true && !t.getTurId().equals(1)) {
										// agregar caso si trabajan solo turnos vespertino o nocturno
										almacenarDetalles(g,l,t, fechainicial, fechainicial,cab_id);
										
									} else if (g.getGuaCctv() == true && l.getLugCctv() == true && cctv<=3) {
										// agregar si es
										almacenarDetalles(g,l,t, fechainicial, fechainicial,cab_id);
										cctv++;
										
									} else if (g.getGuaControlAccesos() == true && l.getLugControlAccesos()  == true) {
										// agregar si es control de accesos y enrolamiento
										almacenarDetalles(g,l,t, fechainicial, fechainicial,cab_id);
										
									} else if (g.getGuaChofer()  == true && chofer <= 1) {
										// agregar si es guadia chofer
										almacenarDetalles(g,l,t, fechainicial, fechainicial,cab_id);
										chofer++;
									}
								}
							}
					}
					contador++;
				}
				}
				fechainicial1 = addDays(fechainicial);
				fechainicial = fechainicial1;
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e
							.getMessage()));
		}
		return "";
	}
	
	public Integer diasXFi_Ff()
	{
		    Long diff = sqlfechaf.getTime() - sqlfechai.getTime();
		    return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * metodo para mostrar los guardias
	 * 
	 */
	public HgHorarioDet almacenarDetalles(HgGuardia g, HgLugare l,HgTurno t, Date fechainicial1, Date fechaFinal, Integer cab_id) {
		HgHorarioDet hghorariodetalle = new HgHorarioDet();
		try {
			if(g.getGuaCasoTurno()!=null)
			{
				System.out.println("entra cuando si tiene caso turno");	
				managerhorario.insertarHorarioDet(fechainicial1, fechaFinal, g, l, t,
						managergest.turnoByID(g.getGuaCasoTurno()).getTurHoraInicio(), 
						managergest.turnoByID(g.getGuaCasoTurno()).getTurHoraFin(), 
						managerhorario.horarioCabByID(cab_id));
			}else{
				System.out.println("entra cuando no tiene caso turno");		
				managerhorario.insertarHorarioDet(fechainicial1, fechaFinal, g, l,t, 
					t.getTurHoraInicio(),t.getTurHoraFin(), 
					managerhorario.horarioCabByID(cab_id));
			}
		
		} catch (Exception e) {
			System.out.println("------------------> Error almacenarDetalles HorarioCDBean:");
			e.printStackTrace();
			Mensaje.crearMensajeERROR("Error al almacenar detalle: "+e.getMessage());
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
			horcab_fechainicio = (Date) horcab.getHcabFechaInicio();
			horcab_fechafin = (Date) horcab.getHcabFechaFin();
			horcab_nombre = horcab.getHcabNombre();
			horcab_usuarioreg = horcab.getHcabUsuario();
			usuariologeado=horcab_usuarioreg;
			edicion = true;
			ediciontipo = false;
			getListaHorarioDet().clear();
			getListaHorarioDet().addAll(managerhorario.findAllHorariosDetXIdCab(horcab_id));
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
		horcab_usuarioreg=null;
		usuariologeado=null;
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
}
