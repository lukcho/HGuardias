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
public class ManagerFaltos {

	@EJB
	private ManagerDAO mDAO;

	@EJB
	private ManagerGestion mGes;
	
	@EJB
	private ManagerHorario mHor;

	private static HgGuardia hg_gua;
	private static HgTurno hg_turno;
	private static HgHorarioCab hg_hcab;
	private static HgHorarioDet hg_hdet;

	String h = "";

	public ManagerFaltos() {
	}

	// faltos

	public Integer ultimoOrdenFalto() {
		Integer orden = mDAO.tomarValorIntJPQL("select max(o.faltoId) from HgFalto o");
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
	 * buscar todas los horariosdet
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgFalto> findAllFaltos() {
		return mDAO.findWhere(HgFalto.class, "1=1", null);
	}

	/**
	 * listar todas las horariosdet
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgFalto> findAllHorariosDet() {
		return mDAO.findAll(HgFalto.class," o.faltoFechaFalta asc ");
	}

	/**
	 * listar todos las horariosdet ordenadas x cedula de guardia
	 * 
	 * @param gua_cid
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgFalto> findAllFaltosxFecha(Date fechaIn,Integer cab_id) {
		Date finicial = java.sql.Date
				.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(fechaIn));
		return mDAO.findWhere(HgFalto.class, " o.faltoFechaFalta = '"
				+ finicial + "' "
				// + "or (o.hdetFechaInicio = '"+ fechadesp +
				// "' and o.hgTurno.turId = 3 )"
				+ " ", " o.faltoFechaFalta ");
	}

	/**
	 * buscar los faltosByID por ID
	 * 
	 * @param hdet_id
	 * @throws Exception
	 */
	public HgFalto faltoByID(Integer falto_id) throws Exception {
		return (HgFalto) mDAO.findById(HgFalto.class, falto_id);
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
	 * metodo para asignar el guardia
	 * 
	 * @param u
	 *            guardia a analizar
	 * @return true o false
	 */
	public HgHorarioDet asignarDetalle(Integer det_id) {
		try {
			hg_hdet = mHor.dertalleByID(det_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hg_hdet;
	}

	/**
	 * metodo para asignar el turno
	 * 
	 * @param u
	 *            conductor a analizar
	 * @return true o false
	 */
	public HgTurno obtenerTurno(Integer turno_id) {
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
	public HgHorarioCab obtenerHorarioCab(Integer horcab_id) {
		try {
			hg_hcab = mHor.horarioCabByID(horcab_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hg_hcab;
	}
	
	public void insertarFalto(Integer hor_cab, HgTurno t, HgLugare l,
			Date det_fechaini) throws Exception {
		Integer faltoid = this.ultimoOrdenFalto();
		HgFalto falto = new HgFalto();
		mDAO.insertar(falto);
	}
	
	/**
	 * Elimina lugarturno
	 * 
	 * @param prod_id
	 * @throws Exception
	 */
	public void eliminarFalto(Integer falto_id) throws Exception {
		mDAO.eliminar(HgFalto.class, falto_id);
	}
	
	/**
	 * 
	 * 
	 * @param aus_id
	 * @param aus_fecha_inicio
	 * @param aus_fecha_fin
	 * @param aus_descripcion
	 * @throws Exception
	 */
	public void insertarFalto(Integer faltoid,Timestamp falto_fecha_creacion_timestamp,Integer hdet,
			String falto_descripcion) throws Exception {
		HgFalto falto = new HgFalto();
		falto.setFaltoId(faltoid);
		falto.setFaltoDescripcion(falto_descripcion);
		falto.setFaltoFechaFalta(falto_fecha_creacion_timestamp);
		falto.setHgGuardia(hg_gua);
		falto.setHgHorarioDet(hg_hdet);

		mDAO.insertar(falto);
	}
}