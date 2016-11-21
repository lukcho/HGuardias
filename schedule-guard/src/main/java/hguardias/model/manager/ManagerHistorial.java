package hguardias.model.manager;

import hguardias.model.dao.entities.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ManagerHistorial {

	@EJB
	private ManagerDAO mDAO;
	
	String h = "";

	public ManagerHistorial() {
	}

	// Historial
	/**
	 * Retorna el ultimo valor de id
	 * @return
	 */
	public Integer ultimoOrdenCabeceraHistorial() {
		Integer orden = mDAO
				.tomarValorIntJPQL("select max(o.histoId) from HgHistorialMovimiento o");
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
	public List<HgHistorialMovimiento> findturno() {
		return mDAO.findWhere(HgHistorialMovimiento.class, "1=1", null);
	}

	/**
	 * listar todos los historial
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HgHistorialMovimiento> findAllMovientos() {
		return mDAO.findAll(HgHistorialMovimiento.class, " o.hdetFecha asc");
	}

	/**
	 * buscar historial por ID
	 * 
	 * @param turno_id
	 * @throws Exception
	 */
	public HgHistorialMovimiento historialByID(Integer historial_id) throws Exception {
		return (HgHistorialMovimiento) mDAO.findById(HgHistorialMovimiento.class, historial_id);
	}

	/**
	 * Agrega historial
	 * 
	 * @param turno_id
	 * @param tur_descripcion
	 * @param tur_hora_inicio
	 * @param tur_hora_fin
	 * @throws Exception
	 */
	public void insertarHistorial(Integer historial_id, HgHorarioCab cab,HgGuardia g1, HgGuardia g2, Integer lugar2, Integer lugar1,Timestamp fecha,Date fechacreacion) throws Exception {
		HgHistorialMovimiento historialMovimiento = new HgHistorialMovimiento();
		historialMovimiento.setHistoId(historial_id);
		historialMovimiento.setHgHorarioCab(cab);
		historialMovimiento.setGuaCedulaDesde(g1.getGuaCedula());
		historialMovimiento.setGuaCedulaHacia(g2.getGuaCedula());
		historialMovimiento.setHistoFechaMovimiento(fecha);
		historialMovimiento.setLugIdDesde(lugar1);
		historialMovimiento.setLugIdHacia(lugar2);
		historialMovimiento.setHdetFecha(fechacreacion);
		mDAO.insertar(historialMovimiento);
	}
}
