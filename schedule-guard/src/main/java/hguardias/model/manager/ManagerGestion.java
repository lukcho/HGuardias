package hguardias.model.manager;

import hguardias.model.dao.entities.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.sql.Time;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ManagerGestion {

	@EJB
	private ManagerDAO mDAO;

	private static HgGuardia hg_gua;
	private static HgTipoAusencia hg_tipo_ausencia;

	private static HgTurno hg_turno;
	private static HgLugare hg_lugar;
	String h = "";

	public ManagerGestion() {
	}

	// Turno
	/**
	 * Retorna el ultimo valor de id
	 * @return
	 */
	public Integer ultimoOrdenCabeceraTurno() {
		Integer orden = mDAO
				.tomarValorIntJPQL("select max(o.turId) from HgTurno o");
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
	 * buscar todos turnos
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgTurno> findturno() {
		return mDAO.findWhere(HgTurno.class, "1=1", null);
	}

	/**
	 * listar todos los turnos
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgTurno> findAllTurnos() {
		return mDAO.findAll(HgTurno.class, "tur_id asc");
	}

	/**
	 * buscar turnos por ID
	 * 
	 * @param turno_id
	 * @throws Exception
	 */
	public HgTurno turnoByID(Integer turno_id) throws Exception {
		return (HgTurno) mDAO.findById(HgTurno.class, turno_id);
	}

	/**
	 * Agrega turnos
	 * 
	 * @param turno_id
	 * @param tur_descripcion
	 * @param tur_hora_inicio
	 * @param tur_hora_fin
	 * @throws Exception
	 */
	public void insertarTurno(Integer tur_id,String tur_descripcion, Time tur_hora_inicio,
			Time tur_hora_fin) throws Exception {
		HgTurno turno = new HgTurno();
		turno.setTurId(tur_id);
		turno.setTurDescripcion(tur_descripcion);
		turno.setTurHoraInicio(tur_hora_inicio);
		turno.setTurCodigoColor("008000");
		turno.setTurHoraFin(tur_hora_fin);
		turno.setTurEstado("A");

		mDAO.insertar(turno);
	}

	/**
	 * Cambiar datos de turno
	 * 
	 * @param turno_id
	 * @param tur_descripcion
	 * @param tur_hora_inicio
	 * @param tur_hora_fin
	 * @param estado
	 * @throws Exception
	 */
	public void editarTurno(Integer tur_id, String tur_descripcion,
			Time tur_hora_inicio, Time tur_hora_fin, String tur_estado)
			throws Exception {
		HgTurno turno = this.turnoByID(tur_id);
		// vehi.setVehiIdplaca(vehi_id);
		turno.setTurDescripcion(tur_descripcion);
		turno.setTurHoraInicio(tur_hora_inicio);
		turno.setTurHoraFin(tur_hora_fin);
		turno.setTurEstado(tur_estado);

		mDAO.actualizar(turno);
	}

	/**
	 * Cambiar estado turno
	 * 
	 * @param tur_id
	 * @throws Exception
	 */
	public String cambioEstadoTurno(Integer tur_id) throws Exception {
		String h = "";
		HgTurno turno = turnoByID(tur_id);

		if (turno.getTurEstado().equals("A")) {
			turno.setTurEstado("I");
			h = "Estado Modificado";
		} else if (turno.getTurEstado().equals("I")) {
			turno.setTurEstado("A");
			h = "Estado Modificado";
		}
		mDAO.actualizar(turno);
		return h;
	}

	/**
	 * Verifica si el turno esta activado
	 * 
	 * @param u
	 *            turno a analizar
	 * @return true o false
	 */
	public boolean esTurnoActivo(HgTurno u) {
		boolean resp = false;
		if (u.getTurEstado().equals("A")) {
			resp = true;
		}
		return resp;
	}

	// Guardias
	/**
	 * listar todos los guardias
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgGuardia> findAllGuardias() {
		return mDAO.findAll(HgGuardia.class, " o.guaApellido asc ");
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
	 * buscar guardias menos el guardia agregado
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgGuardia> AllGuardiasMenosAgregado(HgGuardia g) {
		return mDAO.findWhere(HgGuardia.class,
				" o.guaCedula not like '" + g.getGuaCedula() + "' ", null);
	}

	/**
	 * buscar guardiasxcctv
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgGuardia> guardiaByCctv() {
		return mDAO.findWhere(HgGuardia.class, " o.guaCctv = true ", null);
	}

	/**
	 * buscar guardiasxenrolamiento
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgGuardia> guardiaByEnrolamiento() {
		return mDAO.findWhere(HgGuardia.class, " o.guaControlAccesos = true ",
				null);
	}

	/**
	 * buscar guardiasxmotorizados
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgGuardia> guardiaByMorotizados() {
		return mDAO
				.findWhere(HgGuardia.class, " o.guaMotorizado = true ", null);
	}

	/**
	 * buscar guardiasxmotorizados
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgGuardia> guardiaByChofer() {
		return mDAO.findWhere(HgGuardia.class, " o.guaChofer = true ", null);
	}

	/**
	 * buscar guardiasxmotorizados
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgGuardia> guardiaByEspecial(Integer turno) {
		return mDAO.findWhere(HgGuardia.class, " o.guaCasoTurno = " + turno
				+ "", null);
	}

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
	public void insertarGuardia(String gua_cedid, String gua_nombre,
			String gua_apellido, Date gua_fechanac, String gue_ciudad,
			String gua_sexo, String gua_telefono, String gua_celular,
			String gua_correo, String gua_direccion, boolean gua_cctv,
			boolean gua_motorizado, boolean gua_chofer,String gua_licencia_chofer,
			boolean gua_controlaccs, Integer gua_casoturno,
			boolean gua_casoestudio, boolean gua_casonocturno,
			String gua_EstadoCivil, String gua_TipoSangre) throws Exception {

		HgGuardia gua = new HgGuardia();
		gua.setGuaCedula(gua_cedid);
		gua.setGuaNombre(gua_nombre);
		gua.setGuaApellido(gua_apellido);
		gua.setGuaFechanac(gua_fechanac);
		gua.setGuaCiudad(gue_ciudad);
		gua.setGuaSexo(gua_sexo);
		gua.setGuaTelefono(gua_telefono);
		gua.setGuaCelular(gua_celular);
		gua.setGuaCorreo(gua_correo);
		gua.setGuaDireccion(gua_direccion);
		gua.setGuaCctv(gua_cctv);
		gua.setGuaMotorizado(gua_motorizado);
		gua.setGuaChofer(gua_chofer);
		if(gua_motorizado==true){
			gua.setGuaTipoLicenciaMotorizado("A");
		}else{
			gua.setGuaTipoLicenciaMotorizado(null);
		}
		if(gua_chofer== true){
			gua.setGuaTipoLicenciaChofer(gua_licencia_chofer);
		}else{
			gua.setGuaTipoLicenciaChofer(null);
		}
		gua.setGuaControlAccesos(gua_controlaccs);
		gua.setGuaCasoTurno(gua_casoturno);
		gua.setGuaCasoEstudio(gua_casoestudio);
		gua.setGuaCasoNocturno(gua_casonocturno);
		gua.setGuaEstadoCivil(gua_EstadoCivil);
		gua.setGuaTipoSangre(gua_TipoSangre);
		gua.setGuaEstado("A");
		mDAO.insertar(gua);
	}

	/**
	 * Cambiar datos de guardias
	 * 
	 * @param con_cedid
	 * @param con_nombre
	 * @param con_apellido
	 * @param con_telefono
	 * @param con_correo
	 * @throws Exception
	 */
	public void editarGuardia(String gua_cedid, String gua_nombre,
			String gua_apellido, Date gua_fechanac, String gue_ciudad,
			String gua_sexo, String gua_telefono, String gua_celular,
			String gua_correo, String gua_direccion, boolean gua_cctv,
			boolean gua_motorizado, boolean gua_chofer,String gua_licencia_chofer,
			boolean gua_controlaccs, Integer gua_casoturno,
			boolean gua_casoestudio, boolean gua_casonocturno,
			String gua_EstadoCivil, String gua_TipoSangre, String gua_estado)
			throws Exception {

		HgGuardia gua = this.guardiaByID(gua_cedid);
		gua.setGuaNombre(gua_nombre);
		gua.setGuaApellido(gua_apellido);
		gua.setGuaFechanac(gua_fechanac);
		gua.setGuaCiudad(gue_ciudad);
		gua.setGuaSexo(gua_sexo);
		gua.setGuaTelefono(gua_telefono);
		gua.setGuaCelular(gua_celular);
		gua.setGuaCorreo(gua_correo);
		gua.setGuaDireccion(gua_direccion);
		gua.setGuaCctv(gua_cctv);
		gua.setGuaMotorizado(gua_motorizado);
		gua.setGuaChofer(gua_chofer);
		if(gua_motorizado==true){
			gua.setGuaTipoLicenciaMotorizado("A");
		}else{
			gua.setGuaTipoLicenciaMotorizado(null);
		}
		if(gua_chofer== true){
			gua.setGuaTipoLicenciaChofer(gua_licencia_chofer);
		}else{
			gua.setGuaTipoLicenciaChofer(null);
		}
		gua.setGuaControlAccesos(gua_controlaccs);
		gua.setGuaCasoTurno(gua_casoturno);
		gua.setGuaCasoEstudio(gua_casoestudio);
		gua.setGuaCasoNocturno(gua_casonocturno);
		gua.setGuaEstadoCivil(gua_EstadoCivil);
		gua.setGuaTipoSangre(gua_TipoSangre);
		gua.setGuaEstado(gua_estado);
		mDAO.actualizar(gua);
	}

	/**
	 * Cambiar estado guardias
	 * 
	 * @param id_prod
	 * @throws Exception
	 */
	public String cambioEstadoGuardia(String gua_id) throws Exception {
		String h = "";
		HgGuardia con = guardiaByID(gua_id);

		if (con.getGuaEstado().equals("A")) {
			con.setGuaEstado("I");
			h = "Estado Modificado";
		} else if (con.getGuaEstado().equals("I")) {
			con.setGuaEstado("A");
			h = "Estado Modificado";
		}
		mDAO.actualizar(con);
		return h;
	}

	/**
	 * Verifica si el guardias esta activado
	 * 
	 * @param u
	 *            guardias a analizar
	 * @return true o false
	 */
	public boolean esGuaActivo(HgGuardia u) {
		boolean resp = false;
		if (u.getGuaEstado().equals("A")) {
			resp = true;
		}
		return resp;
	}

	// LUGARES

	/**
	 * Retorna el ultimo valor de id
	 * @return
	 */
	public Integer ultimoOrdenCabeceraLugar() {
		Integer orden = mDAO
				.tomarValorIntJPQL("select max(o.lugId) from HgLugare o");
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
	 * listar todos los Lugares
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgLugare> findAllLugares() {
		return mDAO.findAll(HgLugare.class, " o.lugPrioridad asc ");
	}
	
	/**
	 * listar todos los Lugares
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgLugare> findAllLugaresActivos() {
		return mDAO.findWhere(HgLugare.class, " o.lugEstado = 'A' ", "o.lugPrioridad asc");
	}

	/**
	 * listar todos los Lugares
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgLugare> findAllLugaresDesc() {
		return mDAO.findAll(HgLugare.class, " o.lugId desc");
	}

	/**
	 * buscar conductores por ID
	 * 
	 * @param lug_id
	 * @throws Exception
	 */
	public HgLugare LugarByID(Integer lug_id) throws Exception {
		return (HgLugare) mDAO.findById(HgLugare.class, lug_id);
	}

	/**
	 * Agrega lugar
	 * 
	 * @param lug_id
	 * @param lug_nombre
	 * @param lug_ciudad
	 * @param lug_estado
	 * @param lug_nroguardias
	 * @param lug_CCTV
	 * @param lug_controlaccs
	 * @throws Exception
	 */
	public void insertarLugar(Integer lug_id,String lug_nombre, String lug_ciudad,Integer numeroguardia,
			boolean lug_CCTV, boolean lug_controlaccs,
			boolean lunes, boolean martes, boolean miercoles, boolean jueves,
			boolean viernes, boolean sabado, boolean domingo, String lug_estado,Integer prioridad)
			throws Exception {
		HgLugare lug = new HgLugare();
		lug.setLugId(lug_id);
		lug.setLugNombre(lug_nombre);
		lug.setLugCiudad(lug_ciudad);
		lug.setLugNroGuardias(numeroguardia);
		lug.setLugCctv(lug_CCTV);
		lug.setLugControlAccesos(lug_controlaccs);
		lug.setLugLunes(lunes);
		lug.setLugMartes(martes);
		lug.setLugMiercoles(miercoles);
		lug.setLugJueves(jueves);
		lug.setLugViernes(viernes);
		lug.setLugSabado(sabado);
		lug.setLugDomingo(domingo);
		lug.setLugEstado("A");
		lug.setLugPrioridad(prioridad);
		mDAO.insertar(lug);
	}

	/**
	 * Cambiar datos de lugar
	 * 
	 * @param lug_id
	 * @param lug_nombre
	 * @param lug_ciudad
	 * @param lug_estado
	 * @param lug_nroguardias
	 * @param lug_CCTV
	 * @param lug_controlaccs
	 * @throws Exception
	 */
	public void editarLugar(Integer lug_id, String lug_nombre,
			String lug_ciudad,Integer numeroguardia, boolean lug_CCTV,
			boolean lug_controlaccs, boolean lunes, boolean martes,
			boolean miercoles, boolean jueves, boolean viernes, boolean sabado,
			boolean domingo, String lug_estado) throws Exception {
		HgLugare lug = this.LugarByID(lug_id);
		lug.setLugNombre(lug_nombre);
		lug.setLugCiudad(lug_ciudad);
		lug.setLugNroGuardias(numeroguardia);
		lug.setLugCctv(lug_CCTV);
		lug.setLugControlAccesos(lug_controlaccs);
		lug.setLugLunes(lunes);
		lug.setLugMartes(martes);
		lug.setLugMiercoles(miercoles);
		lug.setLugJueves(jueves);
		lug.setLugViernes(viernes);
		lug.setLugSabado(sabado);
		lug.setLugDomingo(domingo);
		lug.setLugEstado(lug_estado);
		mDAO.actualizar(lug);
	}
	
	/**
	 * Cambiar datos de lugar
	 * 
	 * @param lug_id
	 * @param lug_nombre
	 * @param lug_ciudad
	 * @param lug_estado
	 * @param lug_nroguardias
	 * @param lug_CCTV
	 * @param lug_controlaccs
	 * @throws Exception
	 */
	public void actualizarPrioridad(HgLugare lugarPrioridad) throws Exception {
		mDAO.actualizar(lugarPrioridad);
	}
	

	/**
	 * Cambiar estado lugar
	 * 
	 * @param lug_id
	 * @throws Exception
	 */
	public String cambioEstadoLugar(Integer lug_id) throws Exception {
		String h = "";
		HgLugare lug = LugarByID(lug_id);

		if (lug.getLugEstado().equals("A")) {
			lug.setLugEstado("I");
			h = "Estado Modificado";
		} else if (lug.getLugEstado().equals("I")) {
			lug.setLugEstado("A");
			h = "Estado Modificado";
		}
		mDAO.actualizar(lug);
		return h;
	}

	/**
	 * Verifica si el lugar esta activado
	 * 
	 * @param u
	 *            lugar a analizar
	 * @return true o false
	 */
	public boolean esLugActivo(HgLugare u) {
		boolean resp = false;
		if (u.getLugEstado().equals("A")) {
			resp = true;
		}
		return resp;
	}

	// Guardias Ausencia
	/**
	 * Retorna el ultimo valor de id
	 * @return
	 */
	public Integer ultimoOrdenCabeceraAusencia() {
		Integer orden = mDAO
				.tomarValorIntJPQL("select max(o.ausId) from HgAusencia o");
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
	 * listar todos las Ausencias
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgAusencia> findAllAusencias() {
		return mDAO.findAll(HgAusencia.class, " o.ausFechaInicio desc ");
	}

	/**
	 * buscar Ausencia por ID
	 * 
	 * @param con_id
	 * @throws Exception
	 */
	public HgAusencia ausenciaByID(Integer aus_id) throws Exception {
		return (HgAusencia) mDAO.findById(HgAusencia.class, aus_id);
	}

	/**
	 * Agrega Ausencia
	 * 
	 * @param hg_gua
	 * @param aus_fecha_inicio
	 * @param aus_fecha_fin
	 * @param aus_descripcion
	 * @throws Exception
	 */
	public void insertarAusencia(Integer aus_id,Date aus_fecha_inicio, Date aus_fecha_fin,
			String aus_descripcion) throws Exception {
		HgAusencia aus = new HgAusencia();
		aus.setAusId(aus_id);
		aus.setHgGuardia(hg_gua);
		aus.setHgTipoAusencia(hg_tipo_ausencia);
		aus.setAusFechaInicio(aus_fecha_inicio);
		aus.setAusFechaFin(aus_fecha_fin);
		aus.setAusDescripcion(aus_descripcion);

		mDAO.insertar(aus);
	}
	
	/**
	 * Cambiar datos de Ausencia
	 * 
	 * @param hg_gua
	 * @param aus_fecha_inicio
	 * @param aus_fecha_fin
	 * @param aus_descripcion
	 * @throws Exception
	 */
	public void editarAusencia(Integer aus_id, Date aus_fecha_inicio,
			Date aus_fecha_fin, String aus_descripcion) throws Exception {

		HgAusencia aus = this.ausenciaByID(aus_id);
		// con.setCondCedula(con_cedid);
		aus.setHgGuardia(hg_gua);
		aus.setAusFechaInicio(aus_fecha_inicio);
		aus.setAusFechaFin(aus_fecha_fin);
		aus.setAusDescripcion(aus_descripcion);

		mDAO.actualizar(aus);
	}
	
	//tipoausencias
	
	/**
	 * listar todos las Ausencias
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgTipoAusencia> findAllTipoAusencias() {
		return mDAO.findAll(HgTipoAusencia.class);
	}
	
	/**
	 * buscar Ausencia por ID
	 * 
	 * @param con_id
	 * @throws Exception
	 */
	public HgTipoAusencia tipoAusenciaByID(Integer aus_id) throws Exception {
		return (HgTipoAusencia) mDAO.findById(HgTipoAusencia.class, aus_id);
	}
	
	/**
	 * metodo para asignar el guardia
	 * 
	 * @param u
	 *            guardia a analizar
	 * @return true o false
	 */
	public HgTipoAusencia asignarTipoAusencia(Integer tipoaus) {
		try {
			hg_tipo_ausencia = this.tipoAusenciaByID(tipoaus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hg_tipo_ausencia;
	}
	
	/**
	 * Agrega Ausencia
	 * 
	 * @param hg_gua
	 * @param aus_fecha_inicio
	 * @param aus_fecha_fin
	 * @param aus_descripcion
	 * @throws Exception
	 */
	public void insertarTipoAusencia(String tipoaus_nombre, String tipoaus_descripcion) throws Exception {
		HgTipoAusencia tipoaus = new HgTipoAusencia();
		tipoaus.setTipAusNombre(tipoaus_nombre);
		tipoaus.setTipAusDescripcion(tipoaus_descripcion);

		mDAO.insertar(tipoaus);
	}
	
	/**
	 * Cambiar datos de Ausencia
	 * 
	 * @param hg_gua
	 * @param aus_fecha_inicio
	 * @param aus_fecha_fin
	 * @param aus_descripcion
	 * @throws Exception
	 */
	public void editarTipoAusencia(Integer tipoaus_id, String tipoaus_nombre ,String tipoaus_descripcion) throws Exception {

		HgTipoAusencia tipoaus = this.tipoAusenciaByID(tipoaus_id);
		tipoaus.setTipAusNombre(tipoaus_nombre);
		tipoaus.setTipAusDescripcion(tipoaus_descripcion);

		mDAO.actualizar(tipoaus);
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
			hg_gua = this.guardiaByID(gua_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hg_gua;
	}

	/**
	 * metodo para asignar el turno
	 * 
	 * @param u
	 *            guardia a analizar
	 * @return true o false
	 */
	public HgTurno asignarTurno(Integer turno_id) {
		try {
			hg_turno = this.turnoByID(turno_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hg_turno;
	}

	/**
	 * M�todo que permite asegurar obtener guardias que estaban libres los 2
	 * dias
	 * 
	 * @param fechainicial
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<HgGuardia> findGuardiasDisponibles(Date fechainicial) {
		List<HgGuardia> l = new ArrayList<HgGuardia>();
		Date finicial = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(restDays(fechainicial)));
		List<Object> lista = mDAO
				.ejectNativeSQL3("select o.gua_cedula, o.gua_nombre, o.gua_apellido, o.gua_estado, "
						+ "o.gua_cctv, o.gua_motorizado, o.gua_chofer, o.gua_control_accesos, "
						+ "o.gua_caso_estudio, o.gua_caso_nocturno from hg_guardias o where "
						+ "o.gua_estado='A' and o.gua_cedula not in "
						+ "( select p.gua_cedula from hg_horario_det p where p.hdet_fecha_inicio = '"
						+finicial+ "') order by o.gua_apellido");
		l = ObjectToClass1(lista);
		return l;
	}

	private List<HgGuardia> ObjectToClass1(List<Object> lista) {
		List<HgGuardia> li = new ArrayList<HgGuardia>();
		Iterator it = lista.iterator();
		while (it.hasNext()) {
			HgGuardia s = new HgGuardia();
			Object[] obj = (Object[]) it.next();
			s.setGuaCedula(String.valueOf(obj[0]));
			s.setGuaNombre(String.valueOf(obj[1]));
			s.setGuaApellido(String.valueOf(obj[2]));
			s.setGuaEstado(String.valueOf(obj[3]));
			s.setGuaCctv(Boolean.valueOf(String.valueOf(obj[4])));
			s.setGuaMotorizado(Boolean.valueOf(String.valueOf(obj[5])));
			s.setGuaChofer(Boolean.valueOf(String.valueOf(obj[6])));
			s.setGuaControlAccesos(Boolean.valueOf(String.valueOf(obj[7])));
			s.setGuaCasoEstudio(Boolean.valueOf(String.valueOf(obj[8])));
			s.setGuaCasoNocturno(Boolean.valueOf(String.valueOf(obj[9])));
			li.add(s);
		}
		return li;
	}

	/**
	 * M�todo que permite obtener guardias 2 dias libres
	 * 
	 * @param fechainicial
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<HgGuardia> findGuardiasUltimosDosDiasLibres(Date fechainicial) {
		Date finicial = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(restDays(fechainicial)));
		Date finicial1 = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(restDays(restDays(fechainicial))));
		List<HgGuardia> l = new ArrayList<HgGuardia>();
		List<Object> lista = mDAO
				.ejectNativeSQL3("select o.gua_cedula, o.gua_nombre, o.gua_apellido, o.gua_estado, o.gua_cctv, "
						+ ""
						+ " o.gua_motorizado, o.gua_chofer, o.gua_control_accesos, o.gua_caso_estudio,"
						+ "  o.gua_caso_nocturno from hg_guardias o"
						+ " where o.gua_cedula not in (select p.gua_cedula "
						+ "from hg_horario_det p where (p.hdet_fecha_inicio between '"
						+ finicial1
						+ "'"
						+ " and '"
						+ finicial
						+ "'  and p.hdet_fecha_inicio = '"
						+ finicial
						+ "' "
						+ " ) group by p.gua_cedula )");
		l = ObjectToClass1(lista);
		return l;
	}
	
	/**
	 * M�todo que permite obtener guardias 2 dias libres
	 * 
	 * @param fechainicial
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<HgGuardia> findGuardiasDisponiblesDiaAnterior(Date fechainicial) {
		Date finicial = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(restDays(fechainicial)));
		List<HgGuardia> l = new ArrayList<HgGuardia>();
		List<Object> lista = mDAO
				.ejectNativeSQL3("select o.gua_cedula, o.gua_nombre, o.gua_apellido, o.gua_estado, o.gua_cctv, "
						+ ""
						+ " o.gua_motorizado, o.gua_chofer, o.gua_control_accesos, o.gua_caso_estudio,"
						+ "  o.gua_caso_nocturno from hg_guardias o"
						+ " where o.gua_cedula in (select p.gua_cedula "
						+ "from hg_horario_det p where (p.hdet_fecha_inicio = '"+finicial+ "' "
						+ " ) group by p.gua_cedula )");
		l = ObjectToClass1(lista);
		return l;
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
	 * M�todo que permite obtener guardias que se encuentran libres el dia anterior
	 * 
	 * @param fechainicial
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<HgGuardia> findGuardiasDisponiblesSinLibres(Date fechainicial) {
		List<HgGuardia> l = new ArrayList<HgGuardia>();
		Date finicial = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(restDays(fechainicial)));
		List<Object> lista = mDAO
				.ejectNativeSQL3("select o.gua_cedula, o.gua_nombre, o.gua_apellido, o.gua_estado, "
						+ "o.gua_cctv, o.gua_motorizado, o.gua_chofer, o.gua_control_accesos, "
						+ "o.gua_caso_estudio, o.gua_caso_nocturno from hg_guardias o where "
						+ "o.gua_estado='A' and o.gua_cedula in "
						+ "( select p.gua_cedula from hg_horario_det p where p.hdet_fecha_inicio = '"
						+finicial+ "') order by o.gua_apellido ");
		l = ObjectToClass1(lista);
		return l;
	}
	/**
	 * M�todo que permite asegurar obtener guardias que estaban libres los 2
	 * dias
	 * 
	 * @param fechainicial
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<HgGuardia> findGuardias7Dias(Date fecha7dias) {
		Date f7dias = rest5Days(restDays(restDays(fecha7dias)));
		List<HgGuardia> l = new ArrayList<HgGuardia>();
		Date finicial = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(f7dias));
		List<Object> lista = mDAO
				.ejectNativeSQL3("select o.gua_cedula, o.gua_nombre, o.gua_apellido, o.gua_estado, "
						+ "o.gua_cctv, o.gua_motorizado, o.gua_chofer, o.gua_control_accesos, "
						+ "o.gua_caso_estudio, o.gua_caso_nocturno from hg_guardias o where "
						+ "o.gua_estado='A' and o.gua_cedula in "
						+ "( select p.gua_cedula from hg_horario_det p where p.hdet_fecha_inicio = '"
						+finicial+ "') order by o.gua_apellido");
		l = ObjectToClass1(lista);
		return l;
	}

	// lugarturno
	/**
	 * Retorna el ultimo valor de id
	 * @return
	 */
	public Integer ultimoOrdenCabeceraLugarTurno() {
		Integer orden = mDAO
				.tomarValorIntJPQL("select max(o.lugTur) from HgLugarTurno o");
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
	 * buscar conductores por ID
	 * 
	 * @param lug_id
	 * @throws Exception
	 */
	public HgLugarTurno lugarByIdLugar(Integer lug_id) throws Exception {
		return (HgLugarTurno) mDAO.findById(HgLugarTurno.class, lug_id);
	}

	/**
	 * Metodo para buscar el id del lugar por el nombre
	 * 
	 * @param nombreLugar
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<HgLugarTurno> findLugarByIdLugar(Integer lugarId) {
		List<HgLugarTurno> l = new ArrayList<HgLugarTurno>();
		List<Object> lista = mDAO.ejectNativeSQL3("select o.lug_tur, o.lug_id, o.tur_id,o.lug_tur_numero_guardias "
						+ " from hg_lugar_turno o where o.lug_id = "+lugarId+ ";");

		l = ObjectToClassLugarTurno(lista);
		return l;
	}

	private List<HgLugarTurno> ObjectToClassLugarTurno(List<Object> lista) {
		List<HgLugarTurno> li = new ArrayList<HgLugarTurno>();
		try {
			Iterator it = lista.iterator();
			while (it.hasNext()) {
				HgLugarTurno s = new HgLugarTurno();
				Object[] obj = (Object[]) it.next();
				s.setLugTur(Integer.parseInt(String.valueOf(obj[0])));
				s.setHgLugare(this.LugarByID(Integer.parseInt(String.valueOf(obj[1]))));
				s.setHgTurno(this.turnoByID(Integer.parseInt(String.valueOf(obj[2]))));
				s.setLugTurNumeroGuardias(Integer.parseInt(String.valueOf(obj[3])));
				li.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return li;
	}

	/**
	 * Metodo para buscar el id del lugar por el nombre
	 * 
	 * @param nombreLugar
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<HgLugare> findLugarByNombre(String nombreLugar) {
		List<HgLugare> l = new ArrayList<HgLugare>();
		List<Object> lista = mDAO
				.ejectNativeSQL3("select o.lug_id, o.lug_nombre from hg_lugares o where o.lug_nombre = '"
						+ nombreLugar + "';");
		l = ObjectToClassLugar(lista);
		return l;
	}

	private List<HgLugare> ObjectToClassLugar(List<Object> lista) {
		List<HgLugare> li = new ArrayList<HgLugare>();
		Iterator it = lista.iterator();
		while (it.hasNext()) {
			HgLugare s = new HgLugare();
			Object[] obj = (Object[]) it.next();
			s.setLugId(Integer.parseInt(String.valueOf(obj[0])));
			s.setLugNombre(String.valueOf(obj[1]));
			li.add(s);
		}
		return li;
	}

	/**
	 * metodo para asignar el turno
	 * 
	 * @param u
	 *            guardia a analizar
	 * @return true o false
	 */
	public HgLugare asignarLugar(Integer lugar_id) {
		try {
			hg_lugar = this.LugarByID(lugar_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hg_lugar;
	}

	/**
	 * Agrega itemfoto
	 * 
	 * @param prod_id
	 * @param nombre
	 * @param valor
	 * @throws Exception
	 */
	public void insertarTurnoLugar(Integer turnlug_id,String direccion) throws Exception {
		HgLugarTurno lugarTurno = new HgLugarTurno();
		lugarTurno.setLugTur(turnlug_id);
		lugarTurno.setHgLugare(hg_lugar);
		lugarTurno.setHgTurno(hg_turno);
		lugarTurno.setLugTurNumeroGuardias(Integer.parseInt(direccion));
		mDAO.insertar(lugarTurno);
	}

	/**
	 * Elimina lugarturno
	 * 
	 * @param prod_id
	 * @throws Exception
	 */
	public void eliminarTurnoLugar(Integer itemf_id) throws Exception {
		mDAO.eliminar(HgLugarTurno.class, itemf_id);
	}
	
	/**
	 * Elimina lugarturno
	 * 
	 * @param prod_id
	 * @throws Exception
	 */
	public void eliminarAusencia(Integer itemf_id) throws Exception {
		mDAO.eliminar(HgAusencia.class, itemf_id);
	}

	/**
	 * M�todo para buscar la imagen por Id del item
	 * 
	 * @param item_id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgLugarTurno> LugarTurnoById1(Integer lugturn_id)
			throws Exception {
		return mDAO.findWhere(HgLugarTurno.class, " o.hgLugare.lugId = "
				+ lugturn_id + " ", null);
	}

	/**
	 * listar x guardia los horariosdet con la fecha contar los dias que trabaja
	 * 
	 * @param fechai
	 * @param fechaf
	 * 
	 * @throws Exception
	 */
	public Integer lugar_TurnoByID(Integer lug_id) {
		return mDAO.findWhere(
				HgLugarTurno.class,
				" o.hgLugare.lugId = " + lug_id + " and o.hgTurno.turId = "
						+ hg_turno.getTurId() + " ", null).size();
	}
	
	/**
	 * listar x guardia los horariosdet con la fecha contar los dias que trabaja
	 * 
	 * @param fechai
	 * @param fechaf
	 * 
	 * @throws Exception
	 */
	public HgLugarTurno lugar_TurnoByIDid(Integer lugturn_id) throws Exception {
		return (HgLugarTurno)  mDAO.findById(HgLugarTurno.class," o.lugTur = " + lugturn_id +" ");
	}
	
	/**
	 * listar todos los turnos
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgLugaresTurnosVacio> allLugarTurnoByID(Integer cabId) {
		return mDAO.findWhere(HgLugaresTurnosVacio.class," o.hgHorarioCab.hcabId = "+cabId+" ",null);
	}
	
	/**
	 * Cambiar datos de lugar
	 * 
	 * @param lug_id
	 * @param lug_nombre
	 * @param lug_ciudad
	 * @param lug_estado
	 * @param lug_nroguardias
	 * @param lug_CCTV
	 * @param lug_controlaccs
	 * @throws Exception
	 */
	public void editarLugarTurno(Integer lugturn_id,Integer lugturn_turno,Integer lugturn_lugar,Integer lugturn_nroGuardias) throws Exception {
		HgLugarTurno lugarturno = this.lugar_TurnoByIDid(lugturn_id);
		asignarTurno(lugturn_turno);
		asignarLugar(lugturn_lugar);
		lugarturno.setHgTurno(hg_turno);
		lugarturno.setHgLugare(hg_lugar);
		lugarturno.setLugTurNumeroGuardias(lugturn_nroGuardias);
		mDAO.actualizar(lugarturno);
	}
	
	
	public Integer ultimoOrdenLugar() {
		Integer orden = mDAO.tomarValorIntJPQL("select max(o.lugId) from HgLugare o");
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
	 * M�todo que permite asegurar obtener guardias que estaban libres los 2
	 * dias
	 * 
	 * @param fechainicial
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<HgGuardia> findGuardiasXFecha(Date fechainicial) {
		List<HgGuardia> l = new ArrayList<HgGuardia>();
		Date finicial = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(fechainicial));
		List<Object> lista = mDAO
				.ejectNativeSQL3("select o.gua_cedula, o.gua_nombre, o.gua_apellido, o.gua_estado, "
						+ "o.gua_cctv, o.gua_motorizado, o.gua_chofer, o.gua_control_accesos, "
						+ "o.gua_caso_estudio, o.gua_caso_nocturno from hg_guardias o where "
						+ "o.gua_estado='A' and o.gua_cedula in "
						+ "( select p.gua_cedula from hg_horario_det p where p.hdet_fecha_inicio = '"
						+finicial+ "') order by o.gua_apellido ");
		l = ObjectToClass1(lista);
		return l;
	}
	
	/**
	 * Verifica si el guardia exite
	 * 
	 * @param u
	 *            guardia a analizar
	 * @return true o false
	 */
	public Integer ausenciaXFecha(Date fechainicial,Date fechafinal,String gua_id){
		Date finicial = java.sql.Date
				.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(fechainicial));
		return mDAO.findWhere(HgAusencia.class," o.hgGuardia.guaCedula = '" + gua_id + "' "+ " and (o.ausFechaInicio <= '" + fechainicial
						+ "' and o.ausFechaFin >= '" + finicial + "' or o.ausFechaInicio <= '" + fechafinal+"' "
						+ " and o.ausFechaFin >= '" + fechafinal+"' ) ", null).size();
	}
	
	// Guardias Pendientes
		/**
		 * listar todos los guardiasp
		 * 
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public List<HgGuardiasPendiente> findAllGuardiasPendientes() {
			return mDAO.findAll(HgGuardiasPendiente.class, " o.guapenFecha asc ");
		}

		/**
		 * buscar guardias por ID
		 * 
		 * @param con_id
		 * @throws Exception
		 */
		public HgGuardiasPendiente guardiaPendienteByID(String guapen_id) throws Exception {
			return (HgGuardiasPendiente) mDAO.findById(HgGuardiasPendiente.class, guapen_id);
		}
}
