package hguardias.model.manager;

import hguardias.model.dao.entidades.Guardias;
import hguardias.model.dao.entities.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
	 * Verifica si el guardia exite actual
	 * 
	 * @param u
	 *            guardia a analizar
	 * @return true o false
	 */
	@SuppressWarnings("unchecked")
	public List<HgHorarioDet> existeGuardiaFechaAct(Date fechai, Date fechaant,
			String cedula) {
		java.sql.Date fechainicio = new java.sql.Date(fechai.getTime());
		java.sql.Date fechaante = new java.sql.Date(fechaant.getTime());
		return mDAO.findWhere(HgHorarioDet.class, " o.hgGuardia.guaCedula = '"
				+ cedula + "' " + "and o.hdetFechaInicio = '" + fechainicio
				+ "' ", null);
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
		java.sql.Date fechainicio = new java.sql.Date(fechaIn.getTime());
		java.sql.Date fechadesp = new java.sql.Date(fechasigui.getTime());
		return mDAO.findWhere(HgHorarioDet.class, " o.hdetFechaInicio = '"
				+ fechainicio + "' and o.hgTurno.turId not in (3)  "
				// + "or (o.hdetFechaInicio = '"+ fechadesp +
				// "' and o.hgTurno.turId = 3 )"
				+ " ",
				" o.hdetFechaInicio ");
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
				" o.hdetFechaInicio asc , o.hdetHoraInicio asc");
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
		return mDAO.findWhere(HgHorarioDet.class, "o.hdetFechaInicio between '"
				+ fechai + "' and  '" + fechaf + "'",
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
	public Integer findNumDiasxGuardia(HgGuardia g, Date fecha_inicial, Date fecha5dias) {
		return mDAO.findWhere(HgHorarioDet.class," o.hgGuardia.guaCedula = '" + g.getGuaCedula()
								+ "'  and o.hdetFechaInicio between '"+fecha5dias+"' and  '"+fecha_inicial+"'  ", null).size();
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
		return mDAO.findWhere(HgHorarioDet.class," o.hgGuardia.guaCedula = '" + g.getGuaCedula()
								+ "'  and o.hdetFechaInicio = '"+fecha_inicial+"' ", null).size();
	}
	
	/**
	 * listar x guardia los horariosdet con la fecha contar los dias que trabaja
	 * 
	 * @param fechai
	 * @param fechaf
	 * 
	 * @throws Exception
	 */
	public Integer findNumDiasxGuardiaPendiente(HgGuardiasPendiente g, Date fecha_inicial, Date fecha5dias) {
		return mDAO.findWhere(HgHorarioDet.class," o.hgGuardia.guaCedula = '" + g.getGuaCedula()
								+ "'  and o.hdetFechaInicio between '"+fecha5dias+"' and  '"+fecha_inicial+"'  ", null).size();
	}
	
	/**
	 * listar x guardia los horariosdet con la fecha contar los dias que trabaja
	 * 
	 * @param fechai
	 * @param fechaf
	 * 
	 * @throws Exception
	 */
	public Integer trabajoDiaAnteriorPendiente(HgGuardiasPendiente g, Date fecha_inicial) {
		return mDAO.findWhere(HgHorarioDet.class," o.hgGuardia.guaCedula = '" + g.getGuaCedula()
								+ "'  and o.hdetFechaInicio = '"+fecha_inicial+"' ", null).size();
	}
	
	/**
	 * listar x guardia los horariosdet con la fecha contar los dias que trabaja
	 * 
	 * @param fechai
	 * @param fechaf
	 * 
	 * @throws Exception
	 */
	public Integer trabajoLugTurnDiaAnterior(HgGuardia g,HgTurno t, Date fecha_inicial) {
		return mDAO.findWhere(HgHorarioDet.class," o.hgGuardia.guaCedula = '"+ g.getGuaCedula()
								+ "' and o.hdetFechaFin = '"+fecha_inicial+"' and o.hgTurno.turId = "+t.getTurId()+" ", null).size();
	}
	

	/**
	 * listar todos las horariosdet ordenadas x cedula de guardia
	 * 
	 * @param gua_cid
	 * @throws Exception
	 */
	public Integer FindDiasLibresxGuardia(Date fechaInicial,
			Date fechaFinal, HgGuardia g, Integer cab_id) {
		java.sql.Date fechainicio = new java.sql.Date(fechaInicial.getTime());
		java.sql.Date fechaantes= new java.sql.Date(fechaFinal.getTime());
		return mDAO.findWhere(HgHorarioDet.class, " o.hgGuardia.guaCedula = '"+g.getGuaCedula()+"' "
				 + "and (o.hdetFechaInicio = '"+fechainicio+"' or o.hdetFechaInicio ='" +fechaantes +"' )  ",
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
	@SuppressWarnings("unchecked")
	public Integer existeGuardia(Integer cab_id, Date fechai, String cedula) {
		java.sql.Date fechainicio = new java.sql.Date(fechai.getTime());
		return mDAO.findWhere(HgHorarioDet.class, " o.hgGuardia.guaCedula = '" + cedula + "' "
				+ "and o.hdetFechaInicio = '" + fechainicio + "' ", null).size();
	}

	@SuppressWarnings("unchecked")
	public List<HgHorarioDet> existeGuardiaLibre(Integer cab_id, Date fechaant,
			String cedula) {
		java.sql.Date fechaante = new java.sql.Date(fechaant.getTime());
		System.out.println(fechaante);
		return mDAO.findWhere(HgHorarioDet.class, " o.hdetFechaInicio = '"
				+ fechaante + "' " + " and o.hgGuardia.guaCedula = '" + cedula
				+ "' and o.hgTurno.turId= 4 ", null);
	}

	public Integer existeGuardiaXturnoMNoc(Integer cab_id,
			Date fechaant, String cedula) {
		return mDAO.findWhere(HgHorarioDet.class, " o.hdetFechaInicio = '"
				+ fechaant + "' " + " and o.hgGuardia.guaCedula = '" + cedula
				+ "' and o.hgTurno.turId= 3 ", null).size();
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
		return mDAO
				.findWhere(HgHorarioCab.class, "o.hcabFechaInicio between '"
						+ fechai + "' and  '" + fechaf + "'",
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
	
	//guardia pendiente libre
	
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
	public void insertarGuardiaPendienteLibre(HgGuardia guardia) throws Exception {
		HgGuardiasPendiente gua = new HgGuardiasPendiente();
		gua.setGuaCedula(guardia.getGuaCedula());
		gua.setGuaNombre(guardia.getGuaNombre());
		gua.setGuaApellido(guardia.getGuaApellido());
		gua.setGuaFechanac(guardia.getGuaFechanac());
		gua.setGuaCiudad(guardia.getGuaCiudad());
		gua.setGuaSexo(guardia.getGuaSexo());
		gua.setGuaTelefono(guardia.getGuaTelefono());
		gua.setGuaCelular(guardia.getGuaCelular());
		gua.setGuaCorreo(guardia.getGuaCorreo());
		gua.setGuaDireccion(guardia.getGuaDireccion());
		gua.setGuaCctv(guardia.getGuaCctv());
		gua.setGuaMotorizado(guardia.getGuaMotorizado());
		gua.setGuaChofer(guardia.getGuaChofer());
		gua.setGuaControlAccesos(guardia.getGuaControlAccesos());
		gua.setGuaCasoTurno(guardia.getGuaCasoTurno());
		gua.setGuaCasoEstudio(guardia.getGuaCasoEstudio());
		gua.setGuaCasoNocturno(guardia.getGuaCasoNocturno());
		gua.setGuaEstadoCivil(guardia.getGuaEstadoCivil());
		gua.setGuaTipoSangre(guardia.getGuaTipoSangre());
		gua.setGuaEstado(guardia.getGuaEstado());
		mDAO.insertar(gua);
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
		public void EliminarGuardiaPendienteLibre(HgGuardia guardia) throws Exception {
			mDAO.eliminar(HgGuardiasPendiente.class, guardia.getGuaCedula());
		}
	
		/**
		 * listar todos los guardias
		 * 
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public List<HgGuardiasPendiente> findAllGuardiasPendientes() {
			return mDAO.findAll(HgGuardiasPendiente.class," o.guaCedula asc ");
		}
		
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
		 * buscar guardias por ID
		 * 
		 * @param con_id
		 * @throws Exception
		 */
		public HgGuardiasPendiente guardiaPendienteByID(String con_id) throws Exception {
			return (HgGuardiasPendiente) mDAO.findById(HgGuardiasPendiente.class, con_id);
		}
}
