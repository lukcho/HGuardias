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
import hguardias.model.dao.entities.HgGuardia;
import hguardias.model.dao.entities.HgGuardiasDiasNoTrabajo;
import hguardias.model.dao.entities.HgTurno;
import hguardias.model.dao.entities.Persona;
import hguardias.model.generic.Mensaje;
import hguardias.model.manager.ManagerBuscar;
import hguardias.model.manager.ManagerGestion;

@SessionScoped
@ManagedBean
public class GuardiaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerGestion managergest;

	@EJB
	private ManagerBuscar managerBuscar;

	private static String Activo = "A";
	private static String Masculino = "M";
	// guardias
	private String guardia_id;
	private String guardia_nombre;
	private String guardia_apellido;
	private Date guardia_fechanac;
	private String guardia_ciudad;
	private String guardia_sexo;
	private String guardia_telefono;
	private String guardia_celular;
	private String guardia_correo;
	private String guardia_direccion;
	private String guardia_estadoCivil;
	private String guardia_tipoSangre;
	private String guardia_estado;
	private boolean guardia_CCTV;
	private boolean guardia_CentroEmprendimiento;
	private boolean guardia_motorizado;
	private String guardia_licencia_motorizado;
	private boolean guardia_chofer;
	private String guardia_licencia_chofer;
	private boolean guardia_controlaccesos;
	private Integer guardia_casoturno;
	private boolean guardia_casoestudio;
	private boolean guardia_casonocturno;
	private boolean guardia_chofer_si;

	private HgGuardia guardia;

	// Buscarxcedula
	private String dniBuscar;

	// mmostrar
	private boolean mostrarguardia_id;
	private boolean edicion;
	private boolean edicionbuscar;
	private boolean ediciontipo;

	private List<HgGuardia> listaguardias;
	
	//notrabaja
	private boolean lug_lunes;
	private boolean lug_martes;
	private boolean lug_miercoles;
	private boolean lug_jueves;
	private boolean lug_viernes;
	private boolean lug_sabado;
	private boolean lug_domingo;
	private HgGuardiasDiasNoTrabajo guardiaNoTrabajaElsita;
	private List<HgGuardiasDiasNoTrabajo> listaGuardiaNoTrabaja;

	private String usuario;

	@Inject
	SesionBean ms;

	public GuardiaBean() {
	}

	@PostConstruct
	public void ini() {
		lug_lunes = false;
		lug_martes = false;
		lug_miercoles = false;
		lug_jueves = false;
		lug_viernes = false;
		lug_sabado = false;
		lug_domingo = false;
		guardia_estado = "A";
		listaGuardiaNoTrabaja = new ArrayList<HgGuardiasDiasNoTrabajo>();
		listaguardias = managergest.findAllGuardias();
		usuario = ms.validarSesion("hg_guardias.xhtml");
	}
	
	public String getDniBuscar() {
		return dniBuscar;
	}

	public void setDniBuscar(String dniBuscar) {
		this.dniBuscar = dniBuscar;
	}

	public String getUsuario() {
		return usuario;
	}

	public boolean isMostrarguardia_id() {
		return mostrarguardia_id;
	}

	public void setMostrarguardia_id(boolean mostrarguardia_id) {
		this.mostrarguardia_id = mostrarguardia_id;
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

	public String getGuardia_id() {
		return guardia_id;
	}

	public void setGuardia_id(String guardia_id) {
		this.guardia_id = guardia_id;
	}

	public String getGuardia_nombre() {
		return guardia_nombre;
	}

	public void setGuardia_nombre(String guardia_nombre) {
		this.guardia_nombre = guardia_nombre;
	}

	public String getGuardia_apellido() {
		return guardia_apellido;
	}

	public void setGuardia_apellido(String guardia_apellido) {
		this.guardia_apellido = guardia_apellido;
	}

	public Date getGuardia_fechanac() {
		return guardia_fechanac;
	}

	public void setGuardia_fechanac(Date guardia_fechanac) {
		this.guardia_fechanac = guardia_fechanac;
	}

	public String getGuardia_ciudad() {
		return guardia_ciudad;
	}

	public void setGuardia_ciudad(String guardia_ciudad) {
		this.guardia_ciudad = guardia_ciudad;
	}

	public String getGuardia_sexo() {
		return guardia_sexo;
	}

	public void setGuardia_sexo(String guardia_sexo) {
		this.guardia_sexo = guardia_sexo;
	}

	public String getGuardia_telefono() {
		return guardia_telefono;
	}

	public void setGuardia_telefono(String guardia_telefono) {
		this.guardia_telefono = guardia_telefono;
	}

	public String getGuardia_celular() {
		return guardia_celular;
	}

	public void setGuardia_celular(String guardia_celular) {
		this.guardia_celular = guardia_celular;
	}

	public String getGuardia_correo() {
		return guardia_correo;
	}

	public void setGuardia_correo(String guardia_correo) {
		this.guardia_correo = guardia_correo;
	}

	public String getGuardia_direccion() {
		return guardia_direccion;
	}

	public void setGuardia_direccion(String guardia_direccion) {
		this.guardia_direccion = guardia_direccion;
	}

	public String getGuardia_estado() {
		return guardia_estado;
	}

	public void setGuardia_estado(String guardia_estado) {
		this.guardia_estado = guardia_estado;
	}

	public boolean isGuardia_CCTV() {
		return guardia_CCTV;
	}

	public void setGuardia_CCTV(boolean guardia_CCTV) {
		this.guardia_CCTV = guardia_CCTV;
	}

	public boolean isGuardia_motorizado() {
		return guardia_motorizado;
	}

	public void setGuardia_motorizado(boolean guardia_motorizado) {
		this.guardia_motorizado = guardia_motorizado;
	}

	public boolean isGuardia_chofer() {
		return guardia_chofer;
	}

	public void setGuardia_chofer(boolean guardia_chofer) {
		this.guardia_chofer = guardia_chofer;
	}

	public boolean isGuardia_controlaccesos() {
		return guardia_controlaccesos;
	}

	public void setGuardia_controlaccesos(boolean guardia_controlaccesos) {
		this.guardia_controlaccesos = guardia_controlaccesos;
	}

	public boolean isGuardia_casoestudio() {
		return guardia_casoestudio;
	}

	public void setGuardia_casoestudio(boolean guardia_casoestudio) {
		this.guardia_casoestudio = guardia_casoestudio;
	}

	public Integer getGuardia_casoturno() {
		return guardia_casoturno;
	}

	public void setGuardia_casoturno(Integer guardia_casoturno) {
		this.guardia_casoturno = guardia_casoturno;
	}

	public boolean isGuardia_casonocturno() {
		return guardia_casonocturno;
	}

	public void setGuardia_casonocturno(boolean guardia_casonocturno) {
		this.guardia_casonocturno = guardia_casonocturno;
	}

	public HgGuardia getGuardia() {
		return guardia;
	}

	public void setGuardia(HgGuardia guardia) {
		this.guardia = guardia;
	}

	public String getGuardia_tipoSangre() {
		return guardia_tipoSangre;
	}

	public String getGuardia_estadoCivil() {
		return guardia_estadoCivil;
	}

	public void setGuardia_estadoCivil(String guardia_estadoCivil) {
		this.guardia_estadoCivil = guardia_estadoCivil;
	}

	public void setGuardia_tipoSangre(String guardia_tipoSangre) {
		this.guardia_tipoSangre = guardia_tipoSangre;
	}

	public List<HgGuardia> getListaguardias() {
		return listaguardias;
	}

	public void setListaguardias(List<HgGuardia> listaguardias) {
		this.listaguardias = listaguardias;
	}

	public boolean isEdicionbuscar() {
		return edicionbuscar;
	}

	public void setEdicionbuscar(boolean edicionbuscar) {
		this.edicionbuscar = edicionbuscar;
	}
	
	public String getGuardia_licencia_chofer() {
		return guardia_licencia_chofer;
	}
	
	public void setGuardia_licencia_chofer(String guardia_licencia_chofer) {
		this.guardia_licencia_chofer = guardia_licencia_chofer;
	}
	public boolean isGuardia_chofer_si() {
		return guardia_chofer_si;
	}
	
	public void setGuardia_chofer_si(boolean guardia_chofer_si) {
		this.guardia_chofer_si = guardia_chofer_si;
	}
	
	public String getGuardia_licencia_motorizado() {
		return guardia_licencia_motorizado;
	}
	
	public void setGuardia_licencia_motorizado(
			String guardia_licencia_motorizado) {
		this.guardia_licencia_motorizado = guardia_licencia_motorizado;
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
	
	public List<HgGuardiasDiasNoTrabajo> getListaGuardiaNoTrabaja() {
		return listaGuardiaNoTrabaja;
	}
	
	public void setListaGuardiaNoTrabaja(
			List<HgGuardiasDiasNoTrabajo> listaGuardiaNoTrabaja) {
		this.listaGuardiaNoTrabaja = listaGuardiaNoTrabaja;
	}
	
	public HgGuardiasDiasNoTrabajo getGuardiaNoTrabajaElsita() {
		return guardiaNoTrabajaElsita;
	}
	
	public void setGuardiaNoTrabajaElsita(
			HgGuardiasDiasNoTrabajo guardiaNoTrabajaElsita) {
		this.guardiaNoTrabajaElsita = guardiaNoTrabajaElsita;
	}
	
	public boolean isGuardia_CentroEmprendimiento() {
		return guardia_CentroEmprendimiento;
	}
	
	public void setGuardia_CentroEmprendimiento(
			boolean guardia_CentroEmprendimiento) {
		this.guardia_CentroEmprendimiento = guardia_CentroEmprendimiento;
	}

	// guardias
	/**
	 * accion para invocar el manager y crear guardia o editar el guardia
	 * 
	 * @param guardia_id
	 * @param guardia_descripcion
	 * @param lug_ciudad
	 * @param guardia_estado
	 * @throws Exception
	 */
	public String crearGuardia() {
		String r = "";
		try {
			java.util.Date fechai = guardia_fechanac;
			SimpleDateFormat dateFormati = new SimpleDateFormat("yyyy-MM-dd");
			final String stringDatei = dateFormati.format(fechai);
			final java.sql.Date sqlfechai = java.sql.Date.valueOf(stringDatei);
			if (edicion) {
				managergest.editarGuardia(guardia_id.trim(),
						guardia_nombre.trim(), guardia_apellido.trim(),
						sqlfechai, guardia_ciudad.trim(), guardia_sexo.trim(),
						guardia_telefono.trim(), guardia_celular.trim(),
						guardia_correo.trim(), guardia_direccion.trim(),
						guardia_CCTV,guardia_CentroEmprendimiento, guardia_motorizado, guardia_chofer,guardia_licencia_chofer,
						guardia_controlaccesos,
						guardia_casoestudio, guardia_casonocturno,
						guardia_estadoCivil, guardia_tipoSangre,
						guardia_estado.trim(),guardia_casoturno);
				getListaguardias().clear();
				getListaguardias().addAll(managergest.findAllGuardias());
				Mensaje.crearMensajeINFO("Actualizado - Modificado");
				limpiarCampos();
				r = "hg_guardias?faces-redirect=true";
			} else {
				if (this.ccorreo(guardia_correo)) {
					Mensaje.crearMensajeWARN("Correo Repetido..!!! El correo ya esta siendo utilizado");
				} else if (this.ccedula(guardia_id)) {
					Mensaje.crearMensajeWARN("Cédula Repetida..!!! La c�dula ya esta siendo utilizada");
				} else {
					managergest.insertarGuardia(guardia_id.trim(),
							guardia_nombre.trim(), guardia_apellido.trim(),
							sqlfechai, guardia_ciudad.trim(),
							guardia_sexo.trim(), guardia_telefono.trim(),
							guardia_celular.trim(), guardia_correo.trim(),
							guardia_direccion.trim(), guardia_CCTV,guardia_CentroEmprendimiento,
							guardia_motorizado, guardia_chofer,guardia_licencia_chofer,
							guardia_controlaccesos,
							guardia_casoestudio, guardia_casonocturno,
							guardia_estadoCivil, guardia_tipoSangre, guardia_casoturno);
					Mensaje.crearMensajeINFO("Registrado - Creado");
					getListaguardias().clear();
					getListaguardias().addAll(managergest.findAllGuardias());
					limpiarCampos();
					r = "hg_guardias?faces-redirect=true";
				}
			}
			return r;
		} catch (Exception e) {
			Mensaje.crearMensajeERROR("Error al crear guardia");
			e.printStackTrace();
			return "";
		}
	}
	
	public void limpiarCampos(){
		guardia_id = null;
		dniBuscar = null;
		guardia_estado = "A";
		guardia_nombre = null;
		guardia_apellido = null;
		guardia_fechanac = null;
		guardia_ciudad = null;
		guardia_sexo = null;
		guardia_telefono = null;
		guardia_celular = null;
		guardia_correo = null;
		guardia_estadoCivil = null;
		guardia_tipoSangre = null;
		guardia_direccion = null;
		guardia_CCTV = false;
		guardia_CentroEmprendimiento = false;
		guardia_motorizado = false;
		guardia_chofer = false;
		guardia_licencia_chofer=null;
		guardia_controlaccesos = false;
		guardia_casoturno = null;
		guardia_casoestudio = false;
		guardia_casonocturno = false;
		lug_lunes = false;
		lug_martes = false;
		lug_miercoles = false;
		lug_jueves = false;
		lug_viernes = false;
		lug_sabado = false;
		lug_domingo = false;
		getListaGuardiaNoTrabaja().clear();
		edicion = true;
	}

	/**
	 * accion para abrir el dialogo
	 * 
	 */
	public void abrirDialog() {
		if (valida(guardia_id) == true) {
			RequestContext.getCurrentInstance().execute("PF('gu').show();");
		} else {
			Mensaje.crearMensajeWARN("C�dula Incorrecta");
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
	public String cargarGuardia(HgGuardia guardia) {
		try {
			guardia_id = guardia.getGuaCedula();
			dniBuscar = guardia_id;
			guardia_estado = guardia.getGuaEstado();
			guardia_nombre = guardia.getGuaNombre();
			guardia_apellido = guardia.getGuaApellido();
			guardia_fechanac = (Date) guardia.getGuaFechanac();
			guardia_ciudad = guardia.getGuaCiudad();
			guardia_sexo = guardia.getGuaSexo();
			guardia_telefono = guardia.getGuaTelefono();
			guardia_celular = guardia.getGuaCelular();
			guardia_correo = guardia.getGuaCorreo();
			guardia_direccion = guardia.getGuaDireccion();
			guardia_estadoCivil = guardia.getGuaEstadoCivil();
			guardia_tipoSangre = guardia.getGuaTipoSangre();
			guardia_CCTV = guardia.getGuaCctv();
			guardia_CentroEmprendimiento = guardia.getGuaCentroEmprendimiento();
			guardia_motorizado = guardia.getGuaMotorizado();
			guardia_licencia_motorizado = guardia.getGuaTipoLicenciaMotorizado();
			guardia_chofer = guardia.getGuaChofer();
			guardia_licencia_chofer = guardia.getGuaTipoLicenciaChofer();
			guardia_controlaccesos = guardia.getGuaControlAccesos();
			guardia_casoturno = guardia.getGuaCasoTurno();
			guardia_casoestudio = guardia.getGuaCasoEstudio();
			guardia_casonocturno = guardia.getGuaCasoNocturno();
			listaGuardiaNoTrabaja=managergest.findGuardiaByIdGuardiaNT(guardia_id);
			edicion = true;
			managergest.asignarTurno(guardia.getGuaCasoTurno());
			ediciontipo = false;
			edicionbuscar = false;
			if(guardia_chofer==true){
				guardia_chofer_si=false;
			}else{
				guardia_chofer_si=true;	
			}
			return "hg_nguardia?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * activar y desactivar estadoguardia
	 * 
	 * @throws Exception
	 */
	public String cambiarEstadoGuardia() {
		try {
			Mensaje.crearMensajeINFO(managergest
					.cambioEstadoGuardia(getGuardia().getGuaCedula()));
			getListaguardias().clear();
			getListaguardias().addAll(managergest.findAllGuardias());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * cambiar el estado del guardias
	 * 
	 * @param cond
	 * @throws Exception
	 */
	public void cambiarEstadoGuardiaa(HgGuardia cond) {
		setGuardia(cond);
		RequestContext.getCurrentInstance().execute("PF('ce').show();");
	}

	/**
	 * metodo para conocer el guardia_id si esta usado
	 * 
	 * * @param guardia_id
	 */
	public boolean averiguarGuardiaId(Integer guardia_id) {
		Integer t = 0;
		boolean r = false;
		List<HgGuardia> lug = managergest.findAllGuardias();
		for (HgGuardia y : lug) {
			if (y.getGuaCedula().equals(guardia_id)) {
				t = 1;
				r = true;
				Mensaje.crearMensajeWARN("El c�digo del lugar existe");
			}
		}
		if (t == 0) {
			r = false;
		}
		return r;
	}

	/**
	 * metodo para mostrar los turnos en solicitud
	 * 
	 */
	public List<SelectItem> getListaTurno() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		for (HgTurno t : managergest.findAllTurnos()) {
			if (!t.getTurEstado().equals("I")) {
				listadoSI.add(new SelectItem(t.getTurId(), t
						.getTurDescripcion()));
			}
		}
		return listadoSI;
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
	 * Lista de estados
	 * 
	 * @return lista de items de estados
	 */
	public List<SelectItem> getlistEstadosSexo() {
		List<SelectItem> lista = new ArrayList<SelectItem>();
		lista.add(new SelectItem(Funciones.valorEstadoMasculino,
				Funciones.estadoMasculino + " : "
						+ Funciones.valorEstadoMasculino));
		lista.add(new SelectItem(Funciones.valorEstadoFemenino,
				Funciones.estadoFemenino + " : "
						+ Funciones.valorEstadoFemenino));
		return lista;
	}
	
	/**
	 * Lista de LICENCIAS
	 * 
	 * @return lista de items de estados
	 */
	public List<SelectItem> getlistLicencias() {
		List<SelectItem> lista = new ArrayList<SelectItem>();
		lista.add(new SelectItem(Funciones.valorEstadoA,Funciones.valorEstadoA));
		lista.add(new SelectItem(Funciones.valorEstadoA1,Funciones.valorEstadoA1));
		lista.add(new SelectItem(Funciones.valorEstadoB,Funciones.valorEstadoB ));
		lista.add(new SelectItem(Funciones.valorEstadoC,Funciones.valorEstadoC ));
		lista.add(new SelectItem(Funciones.valorEstadoC1,Funciones.valorEstadoC1 ));
		lista.add(new SelectItem(Funciones.valorEstadoD,Funciones.valorEstadoD ));
		lista.add(new SelectItem(Funciones.valorEstadoD1,Funciones.valorEstadoD1 ));
		lista.add(new SelectItem(Funciones.valorEstadoE,Funciones.valorEstadoE ));
		lista.add(new SelectItem(Funciones.valorEstadoE1,Funciones.valorEstadoE1 ));
		lista.add(new SelectItem(Funciones.valorEstadoF,Funciones.valorEstadoF ));
		lista.add(new SelectItem(Funciones.valorEstadoG,Funciones.valorEstadoG ));
		return lista;
	}

	/**
	 * Redirecciona a la pagina de creacion de guardias
	 * 
	 * @return
	 */
	public String nuevoGuardia() {
		guardia_id = null;
		guardia_estado = "A";
		guardia_nombre = null;
		guardia_apellido = null;
		guardia_fechanac = null;
		guardia_ciudad = null;
		guardia_sexo = null;
		guardia_telefono = null;
		guardia_celular = null;
		guardia_correo = null;
		guardia_direccion = null;
		guardia_estadoCivil = null;
		guardia_tipoSangre = null;
		guardia_CCTV = false;
		guardia_CentroEmprendimiento = false;
		guardia_motorizado = false;
		guardia_chofer = false;
		guardia_licencia_chofer="";
		guardia_controlaccesos = false;
		guardia_casoturno = null;
		guardia_casoestudio = false;
		guardia_casonocturno = false;
		mostrarguardia_id = false;
		edicion = false;
		lug_lunes = false;
		lug_martes = false;
		lug_miercoles = false;
		lug_jueves = false;
		lug_viernes = false;
		lug_sabado = false;
		lug_domingo = false;
		guardia_chofer_si=true;
		edicionbuscar = true;
		getListaGuardiaNoTrabaja().clear();
		return "hg_nguardia?faces-redirect=true";
	}

	/**
	 * limpia la informacion de guardia
	 * 
	 * @return
	 * @throws Exception
	 */
	public String volverGuardia() throws Exception {
		dniBuscar = null;
		guardia_id = null;
		guardia_estado = "A";
		guardia_nombre = null;
		guardia_apellido = null;
		guardia_fechanac = null;
		guardia_ciudad = null;
		guardia_sexo = null;
		guardia_telefono = null;
		guardia_celular = null;
		guardia_correo = null;
		guardia_direccion = null;
		guardia_estadoCivil = null;
		guardia_tipoSangre = null;
		guardia_CCTV = false;
		guardia_CentroEmprendimiento = false;
		guardia_motorizado = false;
		guardia_chofer = false;
		guardia_licencia_chofer="";
		guardia_controlaccesos = false;
		guardia_casoturno = null;
		guardia_casoestudio = false;
		guardia_casonocturno = false;
		mostrarguardia_id = false;
		lug_lunes = false;
		lug_martes = false;
		lug_miercoles = false;
		lug_jueves = false;
		lug_viernes = false;
		lug_sabado = false;
		lug_domingo = false;
		edicion = false;
		edicionbuscar = true;
		guardia_chofer_si=true;
		getListaGuardiaNoTrabaja().clear();
		getListaguardias().clear();
		getListaguardias().addAll(managergest.findAllGuardias());
		return "hg_guardias?faces-redirect=true";
	}

	/**
	 * M�todo para comprobar la cedula
	 * 
	 * @param cedula
	 * @return boolean
	 */
	public boolean ccedula(String cedula) {
		List<HgGuardia> u = managergest.findAllGuardias();
		for (HgGuardia us : u) {
			if (cedula.equals(us.getGuaCedula())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * metodo para comprobar el correo
	 * 
	 * @param correo
	 * @return boolean
	 */
	public boolean ccorreo(String correo) {
		List<HgGuardia> u = managergest.findAllGuardias();
		for (HgGuardia us : u) {
			if (correo.equals(us.getGuaCorreo())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * M�todo para validar la cedula
	 * 
	 * @param x
	 */
	public static boolean valida(String x) {
		int suma = 0;
		if (x.length() == 9) {
			Mensaje.crearMensajeWARN("Ingrese su cedula de 10 digitos");
			return false;
		} else {
			int a[] = new int[x.length() / 2];
			int b[] = new int[(x.length() / 2)];
			int c = 0;
			int d = 1;
			for (int i = 0; i < x.length() / 2; i++) {
				a[i] = Integer.parseInt(String.valueOf(x.charAt(c)));
				c = c + 2;
				if (i < (x.length() / 2) - 1) {
					b[i] = Integer.parseInt(String.valueOf(x.charAt(d)));
					d = d + 2;
				}
			}

			for (int i = 0; i < a.length; i++) {
				a[i] = a[i] * 2;
				if (a[i] > 9) {
					a[i] = a[i] - 9;
				}
				suma = suma + a[i] + b[i];
			}
			int aux = suma / 10;
			int dec = (aux + 1) * 10;
			if ((dec - suma) == Integer.parseInt(String.valueOf(x.charAt(x
					.length() - 1))))
				return true;
			else if (suma % 10 == 0 && x.charAt(x.length() - 1) == '0') {
				return true;
			} else {
				return false;
			}
		}
	}

	public void cargarPersona() {
		try {
			if (getDniBuscar() != null) {
				if (this.ccedula(dniBuscar)) {
					Mensaje.crearMensajeWARN("C�dula Repetida..!!! La c�dula ya esta siendo utilizada");
				} else if (Funciones.validacionCedula(getDniBuscar().trim())) {
					Persona per = managerBuscar
							.buscarPersonaWSReg(getDniBuscar().trim());
					mostrarCamposPersona(per);
				} else
					Mensaje.crearMensajeWARN("La c�dula es incorrecta.");
			}
		} catch (Exception e) {
			Mensaje.crearMensajeINFO("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void mostrarCamposPersona(Persona per) {
		if (!per.equals(null)) {
			setGuardia_id(per.getPerDNI());
			setGuardia_nombre(per.getPerNombres());
			setGuardia_apellido(per.getPerApellidos());
			setGuardia_correo(per.getPerCorreo());
			setGuardia_telefono(per.getPerTelefono());
			setGuardia_celular(per.getPerCelular());
			setGuardia_sexo(per.getPerGenero());
			setGuardia_fechanac(per.getPerFechaNacimiento());
			setGuardia_estadoCivil(per.getPerEstadoCivil());
			setGuardia_tipoSangre(per.getPerGrupoSangineo());
		} else {
			Mensaje.crearMensajeWARN("No se encuentra dicha persona.");
		}
	}
	
	public void mostrarLicencia(){
		if(guardia_chofer==true){
			guardia_chofer_si=false;
		}else{
			guardia_chofer_si=true;
			guardia_licencia_chofer = "";
		}
	}
	
	public String cambiarNombre(String param){
		if(param.equals(Activo)){
			return "Activo";
		}else{
			return "Inactivo";
		}
	}
	
	public String cambiarSexo(String param){
		if(param.equals(Masculino)){
			return "Masculino";
		}else{
			return "Femenino";
		}
	}
	
	public void abrirDialog1() {
		if (guardia_id != null) 
		RequestContext.getCurrentInstance().execute("PF('gu1').show();");
		else
		Mensaje.crearMensajeWARN("Cree el guardia primero");
	}
	
	// nolugartrabaja

	public void crearGuardiaNoTrabaja() {
			try {
				if (managergest.guardiaNoTrabajoById(guardia_id) == 0) {
					managergest.asignarGuardiaNoTrabajo(guardia_id);
					managergest.insertarGuardiaNoTrabaja(lug_lunes, lug_martes, lug_miercoles, lug_jueves, lug_viernes, lug_sabado, lug_domingo);
					getListaGuardiaNoTrabaja().clear();
					getListaGuardiaNoTrabaja()
							.addAll(managergest.findGuardiaByIdGuardiaNT(guardia_id));
					Mensaje.crearMensajeINFO("Almacenado correctamente");
				} else {
					Mensaje.crearMensajeWARN("Yá se encuentra creado");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	public void eliminarguardiaNoTrabaja(HgGuardiasDiasNoTrabajo item) {
		setGuardiaNoTrabajaElsita(item);
		RequestContext.getCurrentInstance().execute("PF('ef').show();");
	}
	
	public String eliminarGNT() {
		try {
			managergest.eliminarGuardiaNoTrabajo(getGuardiaNoTrabajaElsita().getGuaDiaTrabajoId());
			getListaGuardiaNoTrabaja().clear();
			getListaGuardiaNoTrabaja().addAll(managergest.findGuardiaByIdGuardiaNT(guardia_id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * metodo para asignar el lugarorigen a solicitud
	 * 
	 */
	public String asignarTurno() {
		managergest.asignarTurno(guardia_casoturno);
		return "";
	}
	
}
