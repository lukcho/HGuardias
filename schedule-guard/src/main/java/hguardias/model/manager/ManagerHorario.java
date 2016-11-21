package hguardias.model.manager;

import hguardias.model.dao.entities.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
		return mDAO.findAll(HgHorarioDet.class," o.hdetFechaInicio desc ");
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
	 * Verifica si el guardia exite actual
	 * 
	 * @param u
	 *            guardia a analizar
	 * @return true o false
	 */
	@SuppressWarnings("unchecked")
	public List<HgHorarioDet> existeGuardiaFechaAct(Date fechai, Date fechaant,
			String cedula) {
		Date finicial = java.sql.Date
				.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(fechai));
		return mDAO.findWhere(HgHorarioDet.class,
				" o.hgGuardia.guaCedula = '" + cedula + "' "
						+ "and o.hdetFechaInicio = '" + finicial + "' ", null);
	}

	/**
	 * listar todos las horariosdet ordenadas x cedula de guardia
	 * 
	 * @param gua_cid
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgHorarioDet> findAllGuardiasxFecha(Date fechaIn,
			Date fechasigui, Integer cab_id) {
		Date finicial = java.sql.Date
				.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(fechaIn));
		return mDAO.findWhere(HgHorarioDet.class, " o.hdetFechaInicio = '"
				+ finicial + "' and o.hgTurno.turId not in (3)  "
				// + "or (o.hdetFechaInicio = '"+ fechadesp +
				// "' and o.hgTurno.turId = 3 )"
				+ " ", " o.hdetFechaInicio ");
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
	 * listar todos las horariosdet ordenadas x cedula de guardia
	 * 
	 * @param gua_cid
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgHorarioDet> findAllHorariosDetXIdCab(Integer cab_id) {
		return mDAO.findWhere(HgHorarioDet.class, " o.hgHorarioCab.hcabId = '"
				+ cab_id + "' ",
				" o.hgLugare.lugId , o.hdetFechaInicio, o.hgTurno.turId ");
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
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgHorarioDet> findAllHorarioDetXFecha(Date fechai, Date fechaf) {
		Date finicial = java.sql.Date
				.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(fechai));
		Date ffinal = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd")
				.format(fechaf));
		return mDAO.findWhere(HgHorarioDet.class, "o.hdetFechaInicio between '"
				+ finicial + "' and  '" + ffinal + "'",
				" o.hdetFechaInicio desc , o.hdetHoraInicio desc");
	}

	/**
	 * listar x guardia los horariosdet con la fecha contar los dias que trabaja
	 * 
	 * @param fechai
	 * @param fechaf
	 * 
	 * @throws Exception
	 */
	public Integer findNumDiasxGuardia(HgGuardia g, Date fecha_inicial,
			Date fecha5dias) {
		Date finicial = java.sql.Date
				.valueOf(new SimpleDateFormat("yyyy-MM-dd")
						.format(fecha_inicial));
		Date finicial5dias = java.sql.Date.valueOf(new SimpleDateFormat(
				"yyyy-MM-dd").format(fecha5dias));
		return mDAO.findWhere(
				HgHorarioDet.class,
				" o.hgGuardia.guaCedula = '" + g.getGuaCedula()
						+ "'  and o.hdetFechaInicio between '" + finicial5dias
						+ "' and  '" + finicial + "'  ", null).size();
	}

	/**
	 * listar x guardia los horariosdet con la fecha contar los dias que trabaja
	 * 
	 * @param fechai
	 * @param fechaf
	 * 
	 * @throws Exception
	 */
	public Integer trabajoDiaAnterior(HgGuardia g, Date fecha_inicial) {
		Date finicial = java.sql.Date
				.valueOf(new SimpleDateFormat("yyyy-MM-dd")
						.format(fecha_inicial));
		return mDAO.findWhere(
				HgHorarioDet.class,
				" o.hgGuardia.guaCedula = '" + g.getGuaCedula()
						+ "'  and o.hdetFechaInicio = '" + finicial + "' ",
				null).size();
	}

	/**
	 * listar x guardia los horariosdet con la fecha contar los dias que trabaja
	 * 
	 * @param fechai
	 * @param fechaf
	 * 
	 * @throws Exception
	 */
	public Integer findNumDiasxGuardiaPendiente(HgGuardiasPendiente g,
			Date fecha_inicial, Date fecha5dias) {
		Date finicial = java.sql.Date
				.valueOf(new SimpleDateFormat("yyyy-MM-dd")
						.format(fecha_inicial));
		Date finicial5dias = java.sql.Date.valueOf(new SimpleDateFormat(
				"yyyy-MM-dd").format(fecha5dias));
		return mDAO.findWhere(
				HgHorarioDet.class,
				" o.hgGuardia.guaCedula = '" + g.getHgGuardia().getGuaCedula()
						+ "'  and o.hdetFechaInicio between '" + finicial5dias
						+ "' and  '" + finicial + "'  ", null).size();
	}

	/**
	 * listar x guardia los horariosdet con la fecha contar los dias que trabaja
	 * 
	 * @param fechai
	 * @param fechaf
	 * 
	 * @throws Exception
	 */
	public Integer trabajoDiaAnteriorPendiente(HgGuardiasPendiente g,
			Date fecha_inicial) {
		Date finicial = java.sql.Date
				.valueOf(new SimpleDateFormat("yyyy-MM-dd")
						.format(fecha_inicial));
		return mDAO.findWhere(
				HgHorarioDet.class,
				" o.hgGuardia.guaCedula = '" + g.getHgGuardia().getGuaCedula()
						+ "'  and o.hdetFechaInicio = '" + finicial + "' ",
				null).size();
	}

	/**
	 * listar x guardia los horariosdet con la fecha contar los dias que trabaja
	 * 
	 * @param fechai
	 * @param fechaf
	 * 
	 * @throws Exception
	 */
	public Integer trabajoLugDiaAnterior(HgGuardia g, HgLugare lugar, Date fecha_inicial) {
		Date finicial = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(restDays(fecha_inicial)));
		return mDAO.findWhere(HgHorarioDet.class," o.hgGuardia.guaCedula = '" + g.getGuaCedula()+ "' and o.hdetFechaInicio = '" + finicial
						+ "' and o.hgLugare.lugId = " + lugar.getLugId() + " ",null).size();
	}

	/**
	 * listar todos las horariosdet ordenadas x cedula de guardia
	 * 
	 * @param gua_cid
	 * @throws Exception
	 */
	public Integer FindDiasLibresxGuardia(Date fechaInicial, Date fechaFinal,
			HgGuardia g, Integer cab_id) {
		Date finicial = java.sql.Date
				.valueOf(new SimpleDateFormat("yyyy-MM-dd")
						.format(fechaInicial));
		Date fantes = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd")
				.format(fechaFinal));
		return mDAO.findWhere(
				HgHorarioDet.class,
				" o.hgGuardia.guaCedula = '" + g.getGuaCedula() + "' "
						+ "and (o.hdetFechaInicio = '" + finicial
						+ "' or o.hdetFechaInicio ='" + fantes + "' )  ",
				" o.hdetFechaInicio ").size();
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
	public void insertarHorarioDet(Date det_fechaini, Date det_fechafin,
			HgGuardia g, HgLugare l, HgTurno t, Time det_horaini,
			Time det_horafin, HgHorarioCab hor_cab) throws Exception {

		HgHorarioDet hor_det = new HgHorarioDet();
		hor_det.setHdetFechaInicio(det_fechaini);
		hor_det.setHdetFechaFin(det_fechafin);
		hor_det.setHdetHoraInicio(det_horaini);
		hor_det.setHdetHoraFin(det_horafin);
		hor_det.setHdetEstado("A");
		hor_det.setHgGuardia(g);
		hor_det.setHgLugare(l);
		hor_det.setHgTurno(t);
		hor_det.setHgHorarioCab(hor_cab);
		hor_det.setHdetEstado("A");

		mDAO.insertar(hor_det);
	}

	/**
	 * Agrega horariosdet y horariocab libre
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
	public void insertarHorarioDetLibre(Date det_fechaini, Date det_fechafin,
			HgGuardia g, HgTurno t, Time det_horaini, Time det_horafin,
			HgHorarioCab hor_cab) throws Exception {

		HgHorarioDet hor_det = new HgHorarioDet();
		hor_det.setHdetFechaInicio(det_fechaini);
		hor_det.setHdetFechaFin(det_fechafin);
		hor_det.setHdetHoraInicio(det_horaini);
		hor_det.setHdetHoraFin(det_horafin);
		hor_det.setHdetEstado("A");
		hor_det.setHgGuardia(g);
		hor_det.setHgTurno(t);
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

	/**
	 * Verifica si el guardia exite
	 * 
	 * @param u
	 *            guardia a analizar
	 * @return true o false
	 */
	public Integer existeGuardia(Integer cab_id, Date fechai, String cedula) {
		Date finicial = java.sql.Date
				.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(fechai));
		return mDAO.findWhere(
				HgHorarioDet.class,
				" o.hgGuardia.guaCedula = '" + cedula + "' "
						+ "and o.hdetFechaInicio = '" + finicial + "' ", null)
				.size();
	}

	@SuppressWarnings("unchecked")
	public List<HgHorarioDet> existeGuardiaLibre(Integer cab_id, Date fechaant,
			String cedula) {
		Date fantes = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd")
				.format(fechaant));
		return mDAO.findWhere(HgHorarioDet.class, " o.hdetFechaInicio = '"
				+ fantes + "' " + " and o.hgGuardia.guaCedula = '" + cedula
				+ "' and o.hgTurno.turId= 4 ", null);
	}

	public Integer existeGuardiaXturnoMNoc(Integer cab_id, Date fechaant,
			String cedula) {
		Date fantes = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd")
				.format(fechaant));
		return mDAO.findWhere(
				HgHorarioDet.class,
				" o.hdetFechaInicio = '" + fantes + "' "
						+ " and o.hgGuardia.guaCedula = '" + cedula
						+ "' and o.hgTurno.turId= 3 ", null).size();
	}

	public Integer trabajoSemanaAnteriorLugar(Date fecha7dias, String cedula,
			Integer id_lugar) {
		Date f7dias = rest5Days(restDays(restDays(fecha7dias)));
		Date f1dia = restDays(fecha7dias);
		Date f7diasa = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(f7dias));
		Date f1diasb = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(f1dia));
		return mDAO.findWhere(HgHorarioDet.class," o.hdetFechaInicio <= '" + f1diasb+ "' and o.hdetFechaInicio >= '" + f7diasa + "'"
						+ "and o.hgGuardia.guaCedula = '" + cedula+ "' and o.hgLugare.lugId = " + id_lugar + " ", null).size();
	}

	// Horariocabecera

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
		return mDAO.findWhere(HgHorarioCab.class, " 1=1 ",
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
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgHorarioCab> findAllHorarioCabXFecha(Date fechai, Date fechaf) {
		Date finicial = java.sql.Date
				.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(fechai));
		Date ffinal = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd")
				.format(fechaf));
		return mDAO.findWhere(HgHorarioCab.class, "o.hcabFechaInicio between '"
				+ finicial + "' and  '" + ffinal + "'",
				" o.hcabFechaInicio desc");
	}

	/**
	 * Agrega horariocab
	 * 
	 * @param cab_fechaini
	 * @param cab_fechafin
	 * @param hor_cab
	 * @throws Exception
	 */
	public void insertarHorarioCab(Integer cab_id, Date cab_fechaini,
			Date cab_fechafin, String cab_nombre, String cab_usuario,
			Timestamp hor_cab_fechacreacion) throws Exception {
		System.out.println(cab_id + " " + cab_nombre + " " + cab_usuario + " "
				+ cab_fechaini + " " + cab_fechafin + " "
				+ hor_cab_fechacreacion);
		HgHorarioCab hor_cab = new HgHorarioCab();
		hor_cab.setHcabId(cab_id);
		hor_cab.setHcabNombre(cab_nombre);
		hor_cab.setHcabUsuario(cab_usuario);
		hor_cab.setHcabFechaInicio(cab_fechaini);
		hor_cab.setHcabFechaFin(cab_fechafin);
		hor_cab.setHcabFechaRegistro(hor_cab_fechacreacion);

		mDAO.insertar(hor_cab);
	}

	/**
	 * Cambiar datos de cabecera
	 * 
	 * @param turno_id
	 * @param tur_descripcion
	 * @param tur_hora_inicio
	 * @param tur_hora_fin
	 * @param estado
	 * @throws Exception
	 */
	public void editarCabecera(Integer cab_id,
			Integer hcabnumeroregistrosTotal,
			Integer hcabNumeroRegistrosCreados, Integer hcabNumeroLugaresVacios)
			throws Exception {
		HgHorarioCab cabecera = this.horarioCabByID(cab_id);
		cabecera.setHcabNumeroRegistrosTotal(hcabnumeroregistrosTotal);
		cabecera.setHcabNumeroRegistrosCreados(hcabNumeroRegistrosCreados);
		cabecera.setHcabNumeroLugaresVacios(hcabNumeroLugaresVacios);
		mDAO.actualizar(cabecera);
	}

	public Integer ultimoOrdenCabecera() {
		Integer orden = mDAO
				.tomarValorIntJPQL("select max(o.hcabId) from HgHorarioCab o");
		if (orden == null) {
			orden = 1;
			System.out.println(orden);
		} else {
			orden += 1;
			System.out.println(orden);
		}
		return orden;
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
			e.printStackTrace();
		}
		return hg_hcab;
	}

	// guardia pendiente libre

	/**
	 * Agrega guardias
	 * 
	 * @param gua_cedid
	 * @param con_nombre
	 * @param con_apellido
	 * @param con_telefono
	 * @param con_correo
	 * @throws Exception
	 */
	public void insertarGuardiaPendienteLibre(HgGuardia guardia,Date fecha)
			throws Exception {
		HgGuardiasPendiente gua = new HgGuardiasPendiente();
		Integer pendienteid = ultimoOrdenPendiente();
		gua.setGuapenId(pendienteid);
		gua.setHgGuardia(guardia);
		gua.setGuapenFecha(fecha);
		mDAO.insertar(gua);
	}

	public Integer ultimoOrdenPendiente() {
		Integer orden = mDAO
				.tomarValorIntJPQL("select max(o.guapenId) from HgGuardiasPendiente o");
		if (orden == null) {
			orden = 1;
			System.out.println(orden);
		} else {
			orden += 1;
			System.out.println(orden);
		}
		return orden;
	}
	
	/**
	 * Elimina guardias
	 * 
	 * @param gua_cedid
	 * @param con_nombre
	 * @param con_apellido
	 * @param con_telefono
	 * @param con_correo
	 * @throws Exception
	 */
	public void eliminarGuardiaPendienteLibre(Integer cab_id,String guardia,Date fechainicial)
			throws Exception {
		Date finicial = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(fechainicial));
		mDAO.ejectJPQL("delete from HgGuardiasPendiente where hgGuardia.guaCedula = '"+guardia+"' and guapenFecha = '"+finicial+"' ");
	}

	/**
	 * listar todos los guardias pendientes
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgGuardiasPendiente> findAllGuardiasPendientes(Date fechainicial) {
		return mDAO.findWhere(HgGuardiasPendiente.class, " o.guapenFecha = '"+fechainicial+"' ", " o.hgGuardia.guaCedula asc ");
	}

	//guardias
	/**
	 * listar todos los guardias
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgGuardia> findAllGuardiasDesc() {
		return mDAO.findAll(HgGuardia.class, " o.guaCedula desc ");
	}

	/**
	 * buscar guardias por ID
	 * 
	 * @param con_id
	 * @throws Exception
	 */
	public HgGuardia guardiaByID(String con_id) throws Exception {
		return (HgGuardia) mDAO.findById(HgGuardia.class, con_id);
	}

	/**
	 * buscar guardiaspendiente por ID
	 * 
	 * @param con_id
	 * @throws Exception
	 */
	public Integer guardiaPendienteByID(String ced_id,Date fechainicial)throws Exception {
		Date finicial = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(fechainicial));
		Long abc = mDAO.tomarValorIntJPQLlong("select count(o) from HgGuardiasPendiente o where o.hgGuardia.guaCedula = '"+ced_id+"' and o.guapenFecha = '"+finicial+"' "); 
		Integer r =  (int) (long) abc;
		return  r;
	}

	// lugarturno vacio

	public Integer ultimoOrdenLugarTurnoVacio() {
		Integer orden = mDAO
				.tomarValorIntJPQL("select max(o.hglugturId) from HgLugaresTurnosVacio o");
		if (orden == null) {
			orden = 1;
			System.out.println(orden);
		} else {
			orden += 1;
			System.out.println(orden);
		}
		return orden;
	}
	
	public void insertarLugarTurnoVacio(Integer hor_cab, HgTurno t, HgLugare l,
			Date det_fechaini) throws Exception {
		Integer lugturn = this.ultimoOrdenLugarTurnoVacio();
		HgLugaresTurnosVacio lugturvacio = new HgLugaresTurnosVacio();
		lugturvacio.setHglugturId(lugturn);
		lugturvacio.setHgHorarioCab(this.horarioCabByID(hor_cab));
		lugturvacio.setHgTurno(t);
		lugturvacio.setHgLugare(l);
		lugturvacio.setHglugturFechaInicio(det_fechaini);
		mDAO.insertar(lugturvacio);
	}

	/**
	 * listar todos las horariosdet ordenadas x cedula de guardia
	 * 
	 * @param gua_cid
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgLugaresTurnosVacio> lugarTurnoVacio(Integer cab_id) {
		return mDAO.findWhere(HgLugaresTurnosVacio.class,
				" o.hgHorarioCab.hcabId = '" + cab_id + "' ", null);
	}

	// ausensias
	/**
	 * Verifica si el guardia exite
	 * 
	 * @param u
	 *            guardia a analizar
	 * @return true o false
	 */
	public Integer existeAusencia(String cedula, Date fechai) {
		Date finicial = java.sql.Date
				.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(fechai));
		return mDAO.findWhere(
				HgAusencia.class," o.hgGuardia.guaCedula = '" + cedula + "' "+ "and o.ausFechaInicio <= '" + finicial+ "' and o.ausFechaFin >= '" + finicial + "' ", null)
				.size();
	}

	// cambio de turnos

	/**
	 * buscar los horariosdet por ID
	 * 
	 * @param hdet_id
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgHorarioDet> horarioDetByCedulaFecha(String cedula, Date fecha)
			throws Exception {
		Date finicial = java.sql.Date
				.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(fecha));
		return mDAO.findWhere(HgHorarioDet.class, " o.hdetFechaInicio = '"
				+ finicial + "' and o.hgGuardia.guaCedula = '" + cedula + "' ",
				null);
	}
	
	/**
	 * buscar horairodetalle por ID
	 * 
	 * @param con_id
	 * @throws Exception
	 */
	public HgHorarioDet dertalleByID(Integer det_id) throws Exception {
		return (HgHorarioDet) mDAO.findById(HgHorarioDet.class, det_id);
	}

	/**
	 * Cambiar datos de cabecera
	 * 
	 * @param turno_id
	 * @param tur_descripcion
	 * @param tur_hora_inicio
	 * @param tur_hora_fin
	 * @param estado
	 * @throws Exception
	 */
	public void editarGuardiasDetalle(HgHorarioDet hdet) throws Exception {
		HgHorarioDet detalle = this.horarioDetByID(hdet.getHdetId());
		detalle.setHgGuardia(hdet.getHgGuardia());
		mDAO.actualizar(detalle);
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
	 * M�todo que permite asegurar obtener guardias que estaban libres los 2
	 * dias
	 * 
	 * @param fechainicial
	 * @return
	 */ 
	public void eliminarHorarioCab(Integer hcab_id, Date fechaFinal,Date fechaInicial) {
		Date ffinal = java.sql.Date
				.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(fechaFinal));
		Date finicial = java.sql.Date
				.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(fechaInicial));
		mDAO.ejectJPQL("delete from HgHorarioDet where hgHorarioCab.hcabId = "+hcab_id+"  ");
		mDAO.ejectJPQL("delete from HgGuardiasPendiente where guapenFecha <= '"+ffinal+"' and guapenFecha >= '"+finicial+"' ");
		mDAO.ejectJPQL("delete from HgHistorialMovimiento where hgHorarioCab.hcabId = "+hcab_id+"  ");
		mDAO.ejectJPQL("delete from HgLugaresTurnosVacio where hgHorarioCab.hcabId = "+hcab_id+"  ");
		mDAO.ejectJPQL("delete from HgHorarioCab where hcabId  = "+hcab_id+" ");
	}
}
