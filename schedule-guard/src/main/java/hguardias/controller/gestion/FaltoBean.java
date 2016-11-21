package hguardias.controller.gestion;

import java.io.Serializable;
import java.sql.Timestamp;
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

import hguardias.model.manager.ManagerBuscar;
import hguardias.model.manager.ManagerFaltos;
import hguardias.model.manager.ManagerHorario;
import hguardias.controller.access.SesionBean;
import hguardias.model.dao.entities.HgFalto;
import hguardias.model.dao.entities.HgGuardia;
import hguardias.model.dao.entities.HgHorarioDet;
import hguardias.model.generic.Mensaje;
import hguardias.model.manager.ManagerGestion;

@SessionScoped
@ManagedBean
public class FaltoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerGestion managergest;

	@EJB
	private ManagerHorario managerhorario;

	@EJB
	private ManagerFaltos managerfalto;

	@EJB
	private ManagerBuscar mb;

	@EJB
	private algoritmoGenetico ag;

	// horariocab
	private Timestamp falto_fecha_creacion_timestamp;
	private Date faltoFecha;

	// usuario logeado conecta
	private String usuario;

	// mmostrar
	private boolean edicion;
	private boolean ediciontipo;

	// falto
	private Integer faltoId;
	private Date falto_fecha_creacion;
	private String falto_descripcion;
	private List<HgGuardia> listaguardiasXFecha;
	private HgGuardia guardia1;
	private String guardiaId1;
	private String nombreguardia1;
	private String apellidoguardia1;
	private HgHorarioDet detalle;
	private List<SelectItem> lstGuardias;
	private List<HgFalto> listaFaltos;
	private HgFalto hfaltoElsita;

	// detalle
	private List<HgHorarioDet> horarioDet;
	private String hcab_id;
	private String hcab_descripcion;
	private String hcab_turno;
	private String hcab_lugar;

	@Inject
	SesionBean ms;

	public FaltoBean() {
	}

	@PostConstruct
	public void ini() {
		usuario = ms.validarSesion("hg_horarios.xhtml");
		falto_fecha_creacion_timestamp = null;
		edicion = false;
		ediciontipo = false;
		listaguardiasXFecha = new ArrayList<HgGuardia>();
		listaFaltos = managerfalto.findAllFaltos();
		guardia1 = new HgGuardia();
		faltoFecha = null;
		guardiaId1 = null;
		nombreguardia1 = "";
		apellidoguardia1 = "";
		lstGuardias = new ArrayList<SelectItem>();
	}

	public Integer getFaltoId() {
		return faltoId;
	}

	public void setFaltoId(Integer faltoId) {
		this.faltoId = faltoId;
	}

	public Timestamp getFalto_fecha_creacion_timestamp() {
		return falto_fecha_creacion_timestamp;
	}

	public void setFalto_fecha_creacion_timestamp(
			Timestamp falto_fecha_creacion_timestamp) {
		this.falto_fecha_creacion_timestamp = falto_fecha_creacion_timestamp;
	}

	public String getUsuario() {
		return usuario;
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

	public Date getFaltoFecha() {
		return faltoFecha;
	}

	public void setFaltoFecha(Date faltoFecha) {
		this.faltoFecha = faltoFecha;
	}

	public String getFalto_descripcion() {
		return falto_descripcion;
	}

	public void setFalto_descripcion(String falto_descripcion) {
		this.falto_descripcion = falto_descripcion;
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

	public void setGuardiaId1(String guardiaId1) {
		this.guardiaId1 = guardiaId1;
	}

	public String getNombreguardia1() {
		return nombreguardia1;
	}

	public String getApellidoguardia1() {
		return apellidoguardia1;
	}

	public void setNombreguardia1(String nombreguardia1) {
		this.nombreguardia1 = nombreguardia1;
	}

	public void setApellidoguardia1(String apellidoguardia1) {
		this.apellidoguardia1 = apellidoguardia1;
	}

	public List<SelectItem> getLstGuardias() {
		return lstGuardias;
	}

	public void setLstGuardias(List<SelectItem> lstGuardias) {
		this.lstGuardias = lstGuardias;
	}

	public HgFalto getHfaltoElsita() {
		return hfaltoElsita;
	}

	public void setHfaltoElsita(HgFalto hfaltoElsita) {
		this.hfaltoElsita = hfaltoElsita;
	}

	public List<HgFalto> getListaFaltos() {
		return listaFaltos;
	}

	public void setListaFaltos(List<HgFalto> listaFaltos) {
		this.listaFaltos = listaFaltos;
	}

	public String getHcab_id() {
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
	public String crearFalta() {
		String r = "";
		try {
			falto_fecha_creacion = new Date();
			falto_fecha_creacion_timestamp = new Timestamp(
					falto_fecha_creacion.getTime());
			if (!edicion) {
				for (HgHorarioDet hordet : horarioDet) {
					managerfalto.insertarFalto(faltoId,falto_fecha_creacion_timestamp, hordet.getHdetId(),falto_descripcion.trim());
					getListaFaltos().clear();
					getListaFaltos().addAll(managerfalto.findAllFaltos());
					Mensaje.crearMensajeINFO("Registrado - Creado");
					r = "hg_faltos?faces-redirect=true";
				}
			}
		} catch (Exception e) {
			Mensaje.crearMensajeWARN("Error al crear la falta");
			e.printStackTrace();
		}
		return r;
	}

	/**
	 * accion para cargar los datos en el formulario
	 * 
	 * @param horcab
	 * @throws Exception
	 */
	public String cargarFaltos(HgFalto faltos) {
		try {
			faltoFecha = faltos.getHgHorarioDet().getHdetFechaInicio();
			guardiaId1 = faltos.getHgGuardia().getGuaCedula();
			faltoId = faltos.getFaltoId();
			falto_descripcion = faltos.getFaltoDescripcion();
			mostrara();
			edicion = true;
			ediciontipo = false;
			return "hg_nfalto?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * eliminar un fotoproducto
	 * 
	 * @param pro_id
	 * @throws Exception
	 */
	public String eliminarFalto() {
		try {
			managerfalto.eliminarFalto(hfaltoElsita.getFaltoId());
			Mensaje.crearMensajeINFO("Ausencia eliminada");
			getListaFaltos().clear();
			getListaFaltos().addAll(managerfalto.findAllFaltos());
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
	public void abrirDialogFaltoEliminar(HgFalto item) {
		setHfaltoElsita(item);
		RequestContext.getCurrentInstance().execute("PF('ef').show();");
	}

	/**
	 * Redirecciona a la pagina de creacion de hroairocab
	 * 
	 * @return
	 */
	public String nuevoFalto() {
		faltoId = null;
		detalle = null;
		guardiaId1 = null;
		nombreguardia1 = null;
		apellidoguardia1 =null;
		falto_descripcion = null;
		faltoFecha = null;
		hcab_id = null;
		hcab_descripcion = null;
		hcab_turno = null;
		hcab_lugar = null;
		ediciontipo = false;
		edicion = false;
		faltoId = managerfalto.ultimoOrdenFalto();
		return "hg_nfalto?faces-redirect=true";
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

	// faltos
	public void obtenerGuardiaXFecha() {
		Date finicial = java.sql.Date
				.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(faltoFecha));
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
			gua = managergest.guardiaByID(guardiaId1);
			nombreguardia1 = gua.getGuaNombre();
			apellidoguardia1 = gua.getGuaApellido();
			managerfalto.asignarGuardia(gua.getGuaCedula());
			horarioDet = managerhorario.horarioDetByCedulaFecha(
					gua.getGuaCedula(), faltoFecha);
			for (HgHorarioDet hordet : horarioDet) {
				managerfalto.asignarDetalle(hordet.getHdetId());
				hcab_id = hordet.getHgHorarioCab().getHcabId().toString();
				hcab_descripcion = hordet.getHgHorarioCab().getHcabNombre();
				hcab_turno = hordet.getHgTurno().getTurDescripcion();
				hcab_lugar = hordet.getHgLugare().getLugNombre();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * accion para abrir el dialogo
	 * 
	 */
	public void abrirDialogConfirm() {
		RequestContext.getCurrentInstance().execute("PF('falto').show();");
	}

	/**
	 * limpia la informacion de ausencia
	 * 
	 * @return
	 * @throws Exception
	 */
	public String volverFalto() throws Exception {
		faltoId = null;
		faltoFecha = null;
		guardiaId1 = null;
		apellidoguardia1 = "";
		listaguardiasXFecha.clear();
		getListaFaltos().clear();
		getListaFaltos().addAll(managerfalto.findAllFaltos());
		return "hg_faltos?faces-redirect=true";
	}

	public void faltoGuardia(HgHorarioDet hdet1) {
		try {
			managerhorario.editarGuardiasDetalle(hdet1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
