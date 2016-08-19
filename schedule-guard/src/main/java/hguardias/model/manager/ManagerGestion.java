package hguardias.model.manager;

import hguardias.model.dao.entities.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ManagerGestion {

	@EJB
	private ManagerDAO mDAO;
	
	private static HgGuardia hg_gua;
	
	private static HgTurno hg_turno;
	String h = "";

	public ManagerGestion() {
	}

	// Turno

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
		return mDAO.findAll(HgTurno.class);
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
	public void insertarTurno(String tur_descripcion, Time tur_hora_inicio, Time tur_hora_fin) throws Exception {
		HgTurno turno = new HgTurno();
		turno.setTurDescripcion(tur_descripcion);
		turno.setTurHoraInicio(tur_hora_inicio);
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
	public void editarTurno(Integer tur_id, String tur_descripcion, Time tur_hora_inicio, Time tur_hora_fin, String tur_estado)
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
		return mDAO.findAll(HgGuardia.class," o.guaCedula asc ");
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
		return mDAO.findWhere(HgGuardia.class, " o.guaControlAccesos = true ", null);
	}
	
	/**
	 * buscar guardiasxmotorizados
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgGuardia> guardiaByMorotizados() {
		return mDAO.findWhere(HgGuardia.class, " o.guaMotorizado = true ", null);
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
		return mDAO.findWhere(HgGuardia.class, " o.guaCasoTurno = "+turno+"", null);
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
			String gua_apellido, Date gua_fechanac, String gue_ciudad, String gua_sexo, 
			String gua_telefono,String gua_celular, String gua_correo, String gua_direccion,
			boolean gua_cctv, boolean gua_motorizado, boolean gua_chofer, boolean gua_controlaccs,
			Integer gua_casoturno,boolean gua_casoestudio,boolean gua_casonocturno, String gua_EstadoCivil, String gua_TipoSangre) throws Exception {
		
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
			String gua_apellido, Date gua_fechanac, String gue_ciudad, String gua_sexo, 
			String gua_telefono,String gua_celular, String gua_correo, String gua_direccion,
			boolean gua_cctv, boolean gua_motorizado, boolean gua_chofer, boolean gua_controlaccs,
			Integer gua_casoturno,boolean gua_casoestudio,boolean gua_casonocturno,String gua_EstadoCivil, String gua_TipoSangre, String gua_estado) throws Exception {
		
		HgGuardia gua = this.guardiaByID(gua_cedid);
		// con.setCondCedula(con_cedid);
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
	 * listar todos los Lugares
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgLugare> findAllLugares() {
		return mDAO.findAll(HgLugare.class);
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
	public void insertarLugar(String lug_nombre, String lug_ciudad, Integer lug_nroguardias, boolean lug_CCTV, boolean lug_controlaccs, String lug_estado)
			throws Exception {
		HgLugare lug = new HgLugare();
		lug.setLugNombre(lug_nombre);
		lug.setLugCiudad(lug_ciudad);
		lug.setLugNroGuardias(lug_nroguardias);
		lug.setLugCctv(lug_CCTV);
		lug.setLugControlAccesos(lug_controlaccs);
		lug.setLugEstado("A");
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
	public void editarLugar(Integer lug_id, String lug_nombre, String lug_ciudad, Integer lug_nroguardias, 
			boolean lug_CCTV, boolean lug_controlaccs,String lug_estado) throws Exception {
		HgLugare lug = this.LugarByID(lug_id);
		lug.setLugNombre(lug_nombre);
		lug.setLugCiudad(lug_ciudad);
		lug.setLugNroGuardias(lug_nroguardias);
		lug.setLugCctv(lug_CCTV);
		lug.setLugControlAccesos(lug_controlaccs);
		lug.setLugEstado(lug_estado);
		mDAO.actualizar(lug);
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
		 * listar todos las Ausencias
		 * 
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public List<HgAusencia> findAllAusencias() {
			return mDAO.findAll(HgAusencia.class);
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
		public void insertarAusencia(Date aus_fecha_inicio,Date aus_fecha_fin,String aus_descripcion) throws Exception {
			HgAusencia aus = new HgAusencia();
			aus.setHgGuardia(hg_gua);
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
		public void editarAusencia(Integer aus_id,Date aus_fecha_inicio,Date aus_fecha_fin,String aus_descripcion) throws Exception {
			
			HgAusencia aus = this.ausenciaByID(aus_id);
			// con.setCondCedula(con_cedid);
			aus.setHgGuardia(hg_gua);
			aus.setAusFechaInicio(aus_fecha_inicio);
			aus.setAusFechaFin(aus_fecha_fin);
			aus.setAusDescripcion(aus_descripcion);

			mDAO.actualizar(aus);
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
				// TODO Auto-generated prodch block
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
				// TODO Auto-generated prodch block
				e.printStackTrace();
			}
			return hg_turno;
		}
}
