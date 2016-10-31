package hguardias.controller.gestion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import hguardias.controller.access.SesionBean;
import hguardias.model.dao.entities.HgGuardia;
import hguardias.model.manager.ManagerBuscar;
import hguardias.model.manager.ManagerGestion;

@SessionScoped
@ManagedBean
public class reporteGuardiaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerGestion managergest;

	@EJB
	private ManagerBuscar managerBuscar;

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

	private String usuario;

	@Inject
	SesionBean ms;

	public reporteGuardiaBean() {
	}

	@PostConstruct
	public void ini() {
		guardia_estado = "A";
		listaguardias = managergest.findAllGuardias();
		usuario = ms.validarSesion("hg_reporteguardias.xhtml");
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

	// guardias
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
		guardia_motorizado = false;
		guardia_chofer = false;
		guardia_licencia_chofer=null;
		guardia_controlaccesos = false;
		guardia_casoturno = null;
		guardia_casoestudio = false;
		guardia_casonocturno = false;
		edicion = true;
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
			guardia_motorizado = guardia.getGuaMotorizado();
			guardia_licencia_motorizado = guardia.getGuaTipoLicenciaMotorizado();
			guardia_chofer = guardia.getGuaChofer();
			guardia_licencia_chofer = guardia.getGuaTipoLicenciaChofer();
			guardia_controlaccesos = guardia.getGuaControlAccesos();
			guardia_casoturno = guardia.getGuaCasoTurno();
			guardia_casoestudio = guardia.getGuaCasoEstudio();
			guardia_casonocturno = guardia.getGuaCasoNocturno();
			edicion = true;
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
		guardia_motorizado = false;
		guardia_chofer = false;
		guardia_licencia_chofer="";
		guardia_controlaccesos = false;
		guardia_casoturno = null;
		guardia_casoestudio = false;
		guardia_casonocturno = false;
		mostrarguardia_id = false;
		edicion = false;
		edicionbuscar = true;
		guardia_chofer_si=true;
		getListaguardias().clear();
		getListaguardias().addAll(managergest.findAllGuardias());
		return "hg_reporteguardias?faces-redirect=true";
	}
	
	public String cambiarNombre(Integer param){
		String r="";
		if(param == null){
			r= "";
		}else if(param == 1){
			r= "Diurno";
		}else if(param == 2){
			r= "Vespertino";
		}else if(param == 3){
			r= "Nocturno";
		}	
		return r;
	}
}