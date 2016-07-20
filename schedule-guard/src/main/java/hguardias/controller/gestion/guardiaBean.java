package hguardias.controller.gestion;

import java.io.Serializable;
import java.sql.Date;
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
import hguardias.model.dao.entities.HgGuardia;
import hguardias.model.generic.Mensaje;
import hguardias.model.manager.ManagerGestion;

@SessionScoped
@ManagedBean
public class guardiaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerGestion managergest;

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
	private String guardia_estado;
	private boolean guardia_CCTV;
	private boolean guardia_motorizado;
	private boolean guardia_chofer;
	private boolean guardia_controlaccesos;
	private String guardia_casoturno;
	private boolean guardia_casoestudio;
	private boolean guardia_casonocturno;

	private HgGuardia guardia;

	// mmostrar
	private boolean mostrarguardia_id;
	private boolean edicion;
	private boolean ediciontipo;

	private List<HgGuardia> listaguardias;

	private String usuario;

	@Inject
	SesionBean ms;

	public guardiaBean() {
	}

	@PostConstruct
	public void ini() {
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
		guardia_CCTV = false;
		guardia_motorizado = false;
		guardia_chofer = false;
		guardia_controlaccesos = false;
		guardia_casoturno = null;
		guardia_casoestudio = false;
		guardia_casonocturno = false;
		edicion = false;
		ediciontipo = false;
		mostrarguardia_id = false;
		listaguardias = managergest.findAllGuardias();
		usuario = ms.validarSesion("hg_guardias.xhtml");
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

	public String getGuardia_casoturno() {
		return guardia_casoturno;
	}

	public void setGuardia_casoturno(String guardia_casoturno) {
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

	public List<HgGuardia> getListaguardias() {
		return listaguardias;
	}

	public void setListaguardias(List<HgGuardia> listaguardias) {
		this.listaguardias = listaguardias;
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
		try {
			if (edicion) {
				managergest.editarGuardia(guardia_id.trim(),
						guardia_nombre.trim(), guardia_apellido.trim(),
						guardia_fechanac, guardia_ciudad.trim(),
						guardia_sexo.trim(), guardia_telefono.trim(),
						guardia_celular.trim(), guardia_correo.trim(),
						guardia_direccion.trim(), guardia_CCTV,
						guardia_motorizado, guardia_chofer,
						guardia_controlaccesos, guardia_casoturno.trim(),
						guardia_casoestudio, guardia_casonocturno,
						guardia_estado.trim());
				getListaguardias().clear();
				getListaguardias().addAll(managergest.findAllGuardias());
				Mensaje.crearMensajeINFO("Actualizado - Modificado");
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
				guardia_CCTV = false;
				guardia_motorizado = false;
				guardia_chofer = false;
				guardia_controlaccesos = false;
				guardia_casoturno = null;
				guardia_casoestudio = false;
				guardia_casonocturno = false;
				edicion = true;

			} else {
				// controla cedula repite
				managergest.insertarGuardia(guardia_id.trim(),
						guardia_nombre.trim(), guardia_apellido.trim(),
						guardia_fechanac, guardia_ciudad.trim(),
						guardia_sexo.trim(), guardia_telefono.trim(),
						guardia_celular.trim(), guardia_correo.trim(),
						guardia_direccion.trim(), guardia_CCTV,
						guardia_motorizado, guardia_chofer,
						guardia_controlaccesos, guardia_casoturno.trim(),
						guardia_casoestudio, guardia_casonocturno);
				Mensaje.crearMensajeINFO("Registrado - Creado");
				getListaguardias().clear();
				getListaguardias().addAll(managergest.findAllGuardias());
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
				guardia_CCTV = false;
				guardia_motorizado = false;
				guardia_chofer = false;
				guardia_controlaccesos = false;
				guardia_casoturno = null;
				guardia_casoestudio = false;
				guardia_casonocturno = false;
				edicion = true;
			}
			return "hg_guardias?faces-redirect=true";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error al crear guardia", null));
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), null));
			return "";
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
			guardia_CCTV = guardia.getGuaCctv();
			guardia_motorizado = guardia.getGuaMotorizado();
			guardia_chofer = guardia.getGuaChofer();
			guardia_controlaccesos = guardia.getGuaControlAccesos();
			guardia_casoturno = guardia.getGuaCasoTurno();
			guardia_casoestudio = guardia.getGuaCasoEstudio();
			guardia_casonocturno = guardia.getGuaCasoNocturno();
			edicion = true;
			ediciontipo = false;
			return "hg_nguardia?faces-redirect=true";
		} catch (Exception e) {
			// TODO: handle exception
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
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage("INFORMACIÓN", managergest
							.cambioEstadoGuardia(getGuardia().getGuaCedula())));
			getListaguardias().clear();
			getListaguardias().addAll(managergest.findAllGuardias());
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
		System.out.println("holi");
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
				System.out.println("si entra1");
				t = 1;
				r = true;
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"El código del lugar existe.", null));
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
	 * Lista de estados
	 * 
	 * @return lista de items de estados
	 */
	public List<SelectItem> getlistEstadosSexo() {
		List<SelectItem> lista = new ArrayList<SelectItem>();
		lista.add(new SelectItem(Funciones.estadoMasculino, Funciones.estadoMasculino
				+ " : " + Funciones.valorEstadoMasculino));
		lista.add(new SelectItem(Funciones.estadoFemenino,
				Funciones.estadoFemenino + " : "
						+ Funciones.valorEstadoFemenino));
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
		guardia_CCTV = false;
		guardia_motorizado = false;
		guardia_chofer = false;
		guardia_controlaccesos = false;
		guardia_casoturno = null;
		guardia_casoestudio = false;
		guardia_casonocturno = false;
		mostrarguardia_id = false;
		edicion = false;
		return "hg_nguardia?faces-redirect=true";
	}

	/**
	 * limpia la informacion de guardia
	 * 
	 * @return
	 * @throws Exception
	 */
	public String volverGuardia() throws Exception {
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
		guardia_CCTV = false;
		guardia_motorizado = false;
		guardia_chofer = false;
		guardia_controlaccesos = false;
		guardia_casoturno = null;
		guardia_casoestudio = false;
		guardia_casonocturno = false;
		mostrarguardia_id = false;
		edicion = false;
		getListaguardias().clear();
		getListaguardias().addAll(managergest.findAllGuardias());
		return "hg_guardias?faces-redirect=true";
	}
}
