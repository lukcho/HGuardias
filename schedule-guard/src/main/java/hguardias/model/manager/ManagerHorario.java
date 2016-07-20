package hguardias.model.manager;

import hguardias.model.dao.entities.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ManagerHorario {

	@EJB
	private ManagerDAO mDAO;

	@EJB
	private ManagerGestion mGes;
	
	private static HgGuardia hg_gua;
	private static HgLugare hg_lug;
	private static HgTurno hg_turno;
	private static HgHorarioCab hg_hcab;

	private Timestamp fecha_creacion;

	String h = "";

	public ManagerHorario() {
	}

	// HorarioDet

	/**
	 * buscar todas los horariosdet
	 * 
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	public List<HgHorarioDet> findHorariodet() {
		return mDAO.findWhere(HgHorarioDet.class, "1=1", null);
	}

	/**
	 * listar todas las horariosdet
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgHorarioDet> findAllHorariosDet() {
		return mDAO.findAll(HgHorarioDet.class);
	}

	/**
	 * listar todos las horariosdet ordenados por fecha y estado
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgHorarioDet> findAllHorariosDetOrdenadosaaprorecha() {
		return mDAO.findWhere(HgHorarioDet.class,
				" o.hdetEstado not like 'A' ",
				" o.hdetFechaInicio desc, o.hdetHoraInicio desc ");
	}

	/**
	 * listar todos las horariosdet ordenadas x cedula de guardia
	 * 
	 * @param gua_cid
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgHorarioDet> findAllHorariosDetOrdenadosXGua(String gua_cid) {
		return mDAO.findWhere(HgHorarioDet.class, " o.HgGuardia.guaCedula = '"
				+ gua_cid + "' ",
				" o.hdetFechaInicio desc , o.hdetHoraInicio desc ");
	}

	/**
	 * buscar los horariosdet por ID
	 * 
	 * @param hdet_id
	 * @throws Exception
	 */
	public HgHorarioDet horarioDetByID(Integer hdet_id) throws Exception {
		return (HgHorarioDet) mDAO.findById(HgHorarioDet.class, hdet_id);
	}

	/**
	 * listar todos los horariosdet con la fecha
	 * 
	 * @param fechai
	 * @param fechaf

	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgHorarioDet> findAllHorarioDetXFecha(Date fechai, Date fechaf) {
		return mDAO.findWhere(HgHorarioDet.class,
				"o.hdetFechaInicio between '" + fechai + "' and  '"
						+ fechaf + "'",
				" o.hdetFechaInicio desc , o.hdetHoraInicio desc");
	}
	
	/**
	 * Agrega horariosdet y horariocab
	 * 
	 * @param cab_fechaini
	 * @param cab_fechafin
	 * @param det_nombre
	 * @param det_usuario
	 * @param hg_gua
	 * @param hg_lug
	 * @param hg_turno
	 * @param hor_cab
	 * @throws Exception
	 */
	public void insertarHorarioDetCab(Date cab_fechaini, Date cab_fechafin,Time cab_horaini,Time cab_horafin, String det_nombre,String det_usuario) throws Exception {
		
		HgHorarioCab hor_cab = new HgHorarioCab();
		cargafecha();
		hor_cab.setHcabNombre(det_nombre);
		hor_cab.setHcabUsuario(det_usuario);
		hor_cab.setHcabFechaInicio(cab_fechaini);
		hor_cab.setHcabFechaFin(cab_fechafin);
		hor_cab.setHcabFechaRegistro(fecha_creacion);

		mDAO.insertar(hor_cab);
		
		HgHorarioDet hor_det = new HgHorarioDet();
		hor_det.setHdetFechaInicio(cab_fechaini);
		hor_det.setHdetFechaFin(cab_fechafin);
		hor_det.setHdetHoraInicio(cab_horaini);
		hor_det.setHdetHoraFin(cab_horafin);
		hor_det.setHdetEstado("A");
		hor_det.setHgGuardia(hg_gua);
		hor_det.setHgLugare(hg_lug);
		hor_det.setHgTurno(hg_turno);
		hor_det.setHgHorarioCab(hor_cab);
		hor_det.setHdetEstado("A");		

		mDAO.insertar(hor_det);
	}

	/**
	 * Cambiar estado horariodet
	 * 
	 * @param sol_id
	 * @throws Exception
	 */
	public String cambioEstadoHorarioDet(Integer sol_id) throws Exception {
		String h = "";
		HgHorarioDet hor_det = horarioDetByID(sol_id);
		if (hor_det.getHdetEstado().equals("A")) {
			hor_det.setHdetEstado("I");
			h = "Estado Modificado";
		} else if (hor_det.getHdetEstado().equals("I")) {
			hor_det.setHdetEstado("A");
			h = "Estado Modificado";
		}
		mDAO.actualizar(hor_det);
		return h;
	}

	/**
	 * Verifica si el solicitud esta activado
	 * 
	 * @param u
	 *            solicitud a analizar
	 * @return true o false
	 */
	public boolean esHorairoDetActivo(HgHorarioDet u) {
		boolean resp = false;
		if (u.getHdetEstado().equals("A")) {
			resp = true;
		}
		return resp;
	}
	
	//Horariocabecera
	
	/**
	 * buscar todas los horarioscab
	 * 
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	public List<HgHorarioCab> findHorariocab() {
		return mDAO.findWhere(HgHorarioCab.class, "1=1", null);
	}

	/**
	 * listar todas las horarioscab
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgHorarioCab> findAllHorariosCab() {
		return mDAO.findAll(HgHorarioCab.class);
	}

	/**
	 * listar todos las horarioscab ordenados por fecha y estado
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgHorarioCab> findAllHorariosCabOrdenadosaaprorecha() {
		return mDAO.findWhere(HgHorarioCab.class," 1=1 ",
				" o.hcabFechaRegistro desc, o.hcabFechaInicio desc ");
	}

	/**
	 * buscar los horarioscab por ID
	 * 
	 * @param hdet_id
	 * @throws Exception
	 */
	public HgHorarioCab horarioCabByID(Integer hcab_id) throws Exception {
		return (HgHorarioCab) mDAO.findById(HgHorarioCab.class, hcab_id);
	}

	/**
	 * listar todos los horarioscab con la fecha
	 * 
	 * @param fechai
	 * @param fechaf

	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgHorarioCab> findAllHorarioCabXFecha(Date fechai, Date fechaf) {
		return mDAO.findWhere(HgHorarioCab.class,
				"o.hcabFechaInicio between '" + fechai + "' and  '"	+ fechaf + "'",	" o.hcabFechaInicio desc");
	}	
	
	/**
	 * Agrega horariocab
	 * 
	 * @param cab_fechaini
	 * @param cab_fechafin
	 * @param hor_cab
	 * @throws Exception
	 */
	public void insertarHorarioCab(Date cab_fechaini, Date cab_fechafin, String det_nombre,String det_usuario) throws Exception {
		
		HgHorarioCab hor_cab = new HgHorarioCab();
		cargafecha();
		hor_cab.setHcabNombre(det_nombre);
		hor_cab.setHcabUsuario(det_usuario);
		hor_cab.setHcabFechaInicio(cab_fechaini);
		hor_cab.setHcabFechaFin(cab_fechafin);
		hor_cab.setHcabFechaRegistro(fecha_creacion);

		mDAO.insertar(hor_cab);
	}

	// asignaciones

	/**
	 * metodo para asignar el guardia
	 * 
	 * @param u
	 *            guardia a analizar
	 * @return true o false
	 */
	public HgGuardia asignarGuardia(String gua_id) {
		try {
			hg_gua = mGes.guardiaByID(gua_id);
		} catch (Exception e) {
			// TODO Auto-generated prodch block
			e.printStackTrace();
		}
		return hg_gua;
	}

	/**
	 * metodo para asignar el lugar
	 * 
	 * @param u
	 *            vehiculo a analizar
	 * @return true o false
	 */
	public HgLugare asignarLugar(Integer lug_id) {
		try {
			hg_lug = mGes.LugarByID(lug_id);
		} catch (Exception e) {
			// TODO Auto-generated prodch block
			e.printStackTrace();
		}
		return hg_lug;
	}

	/**
	 * metodo para asignar el turno
	 * 
	 * @param u
	 *            conductor a analizar
	 * @return true o false
	 */
	public HgTurno asignarTurno(Integer turno_id) {
		try {
			hg_turno = mGes.turnoByID(turno_id);
		} catch (Exception e) {
			// TODO Auto-generated prodch block
			e.printStackTrace();
		}
		return hg_turno;
	}
	
	/**
	 * metodo para asignar el lugar
	 * 
	 * @param u
	 *            lugar a analizar
	 * @return true o false
	 */
	public HgHorarioCab asignarHorarioCab(Integer horcab_id) {
		try {
			hg_hcab = this.horarioCabByID(horcab_id);
		} catch (Exception e) {
			// TODO Auto-generated prodch block
			e.printStackTrace();
		}  
		return hg_hcab;
	}
	
	/**
	 * Cargar datos fecha
	 * 
	 * @throws Exception
	 */
	public void cargafecha() {
		Calendar calendar = Calendar.getInstance();
		java.sql.Timestamp ourJavaTimestampObject = new java.sql.Timestamp(
				calendar.getTime().getTime());
		fecha_creacion = ourJavaTimestampObject;
	}
}
