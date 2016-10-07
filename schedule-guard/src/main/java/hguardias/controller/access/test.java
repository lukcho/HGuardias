package hguardias.controller.access;

import hguardias.model.dao.entidades.Dias;
import hguardias.model.dao.entidades.Guardias;
import hguardias.model.dao.entidades.Lugares;
import hguardias.model.dao.entidades.Semanas;
import hguardias.model.dao.entidades.Turnos;
import hguardias.model.dao.entities.HgGuardia;
import hguardias.model.dao.entities.HgLugare;
import hguardias.model.dao.entities.HgTurno;
import hguardias.model.manager.ManagerGestion;

import java.util.ArrayList;
import java.util.List;

public class test {

	
	
	public static void main(String[] args) {
		try {
			
			ManagerGestion managergest = new ManagerGestion();
			
			List<HgGuardia> lisHgGuardia = managergest.findAllGuardias();
			List<HgTurno> lisHgTurno = managergest.findAllTurnos();
			List<HgLugare> lisHgLugar = managergest.findAllLugares();

			List<Guardias> ligua = new ArrayList<Guardias>();
			List<Turnos> liturn = new ArrayList<Turnos>();
			List<Lugares> liluga = new ArrayList<Lugares>();
			List<Dias> liDias = new ArrayList<Dias>();

//			for (HgGuardia g : lisHgGuardia) {
//				Guardias guardias = new Guardias(g.getGuaCedula(),
//						g.getGuaApellido(), g.getGuaCasoEstudio(),
//						g.getGuaCasoNocturno(), g.getGuaCasoTurno(),
//						g.getGuaCctv(), g.getGuaCelular(), g.getGuaChofer(),
//						g.getGuaCiudad(), g.getGuaControlAccesos(),
//						g.getGuaCorreo(), g.getGuaDireccion(),
//						g.getGuaEstado(), g.getGuaEstadoCivil(),
//						g.getGuaFechanac(), g.getGuaMotorizado(),
//						g.getGuaNombre(), g.getGuaSexo(), g.getGuaTelefono(),
//						g.getGuaTipoSangre());
//				ligua.add(guardias);
//				
//			}
			System.out.println("-------------->Tamaño de la lista guardias: "+ligua.size());

			for (HgTurno t : lisHgTurno) {
				Turnos turnos = new Turnos(t.getTurId(), t.getTurDescripcion(),
						t.getTurEstado(), t.getTurHoraFin(),
						t.getTurHoraInicio(), ligua);
				liturn.add(turnos);
				
			}
			System.out.println("-------------->Tamaño de la lista turnos: "+liturn.size());

			for (HgLugare l : lisHgLugar) {
				Lugares lugares = new Lugares(l.getLugId(), l.getLugCctv(),
						l.getLugCiudad(), l.getLugControlAccesos(),
						l.getLugEstado(), l.getLugNombre(),
						l.getLugNroGuardias(), liturn);
				liluga.add(lugares);
			}
			System.out.println("-------------->Tamaño de la lista lugares: "+liluga.size());

			int diaId = 0;
			String diaNombre = "";
			int semanaId = 0;
			String semanaNombre = "";

			Dias dia = new Dias(diaId, diaNombre, liluga);
			dia.setDiaId(1);
			dia.setDiaNombre("Lunes");
			dia.setLugares(liluga);
			dia.setDiaId(2);
			dia.setDiaNombre("Martes");
			dia.setLugares(liluga);
			dia.setDiaId(3);
			dia.setDiaNombre("Miercoles");
			dia.setLugares(liluga);
			dia.setDiaId(4);
			dia.setDiaNombre("Jueves");
			dia.setLugares(liluga);
			dia.setDiaId(5);
			dia.setDiaNombre("Viernes");
			dia.setLugares(liluga);
			dia.setDiaId(6);
			dia.setDiaNombre("Sabado");
			dia.setLugares(liluga);
			dia.setDiaId(7);
			dia.setDiaNombre("Domingo");
			dia.setLugares(liluga);
			
			
			Semanas semana = new Semanas(semanaId, semanaNombre, liDias);
			semana.setSemanaId(1);
			semana.setSemanaNombre("Primera");
			semana.setDias(liDias);
			semana.setSemanaId(2);
			semana.setSemanaNombre("Segunda");
			semana.setDias(liDias);
			semana.setSemanaId(3);
			semana.setSemanaNombre("Tercera");
			semana.setDias(liDias);
			semana.setSemanaId(4);
			semana.setSemanaNombre("Cuarta");
			semana.setDias(liDias);
			semana.setSemanaId(5);
			semana.setSemanaNombre("Quinta");
			semana.setDias(liDias);
			
			System.out.println("-------------->Tamaño de la lista dias: "+liDias.size());
			
			

		} catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}
}
