package hguardias.controller.gestion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import hguardias.model.dao.entities.HgGuardia;
import hguardias.model.dao.entities.HgLugare;
import hguardias.model.dao.entities.HgTurno;
import hguardias.model.dao.entidades.DiasMG;
//import hguardias.model.dao.entidades.HorarioDet;
import hguardias.model.manager.ManagerGestion;

@Stateless
public class algoritmoGenetico implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerGestion managergest;
	Integer nroGuardias = 1;
	Integer nroPuestos = 1;
	Integer nroTurnos = 1;
	List<DiasMG> liDias = new ArrayList<DiasMG>();
	int diaId = 1;
	String diaNombre = "";
	Integer nroDias = 7;
	String cadena="";
	ArrayList<String>[][] resultado;
	Integer bestpapa;
	String bestcadenapapa="";
	Integer bestmama;
	String bestcadenamama="";
	String child = "";

	@PostConstruct
	public void ini() {
	}

	private Integer[][][][] tempBin = new Integer[nroGuardias][nroPuestos][nroTurnos][nroDias];// aqui
	Integer maxSizeP = nroDias * nroGuardias * nroPuestos * nroTurnos;
	Integer[][] poblacion = new Integer[maxSizeP][4];// almacenamos
	String[] binariop = new String[maxSizeP];

	//generacion aleatoria de 1 y 0 en una matriz de 4x4 
	public Integer[][][][] poblacionInicial() {
		Integer nroGuardias = managergest.findAllGuardias().size();
		Integer nroPuestos = managergest.findAllLugares().size();
		Integer nroTurnos = managergest.findAllTurnos().size();
		List<DiasMG> liDias = diaslistar();
		
		nroDias = liDias.size();
		
		Integer[][][][] decision = new Integer[nroGuardias][nroTurnos][nroPuestos][nroDias];

		for (int gua = 0; gua < nroGuardias; gua++) {
			for (int turn = 0; turn < nroTurnos; turn++) {
				for (int lug = 0; lug < nroPuestos; lug++) {
					for (int diasl = 0; diasl < nroDias; diasl++) {
						if (diasl == 1 || diasl == 3 || diasl == 5 || diasl == 7) {
							decision[gua][turn][lug][diasl] = 0;
						} else {
							decision[gua][turn][lug][diasl] = 1;
						}
					}
				}
			}
		}
		return decision;
	}

	public List<DiasMG> diaslistar() {
		DiasMG dia = new DiasMG(diaId, diaNombre);
		dia.setDiaId(1);
		dia.setDiaNombre("Lunes");
		liDias.add(dia);
		diaId++;
		DiasMG dia2 = new DiasMG(diaId, diaNombre);
		dia2.setDiaId(2);
		dia2.setDiaNombre("Martes");
		liDias.add(dia2);
		diaId++;
		DiasMG dia3 = new DiasMG(diaId, diaNombre);
		dia3.setDiaId(3);
		dia3.setDiaNombre("Miercoles");
		liDias.add(dia3);
		diaId++;
		DiasMG dia4 = new DiasMG(diaId, diaNombre);
		dia4.setDiaId(4);
		dia4.setDiaNombre("Jueves");
		liDias.add(dia4);
		diaId++;
		DiasMG dia5 = new DiasMG(diaId, diaNombre);
		dia5.setDiaId(5);
		dia5.setDiaNombre("Viernes");
		liDias.add(dia5);
		diaId++;
		DiasMG dia6 = new DiasMG(diaId, diaNombre);
		dia6.setDiaId(6);
		dia6.setDiaNombre("Sabado");
		liDias.add(dia6);
		diaId++;
		DiasMG dia7 = new DiasMG(diaId, diaNombre);
		dia7.setDiaId(7);
		dia7.setDiaNombre("Domingo");
		liDias.add(dia7);
		
		return liDias;
	}

	// restriccion1 garantizar cumplimiento de la demanda del lugar en el turno t
	public Integer restriccion1(Integer[][][][] decision) throws Exception {

		Integer R1 = 0;
		Integer nroGuardias = managergest.findAllGuardias().size();
		Integer nroPuestos = managergest.findAllLugares().size();
		Integer nroTurnos = managergest.findAllTurnos().size();

		List<HgLugare> lisHgLugar = managergest.findAllLugares();
		List<DiasMG> liDias = new ArrayList<DiasMG>();
		List<HgTurno> lisHgTurno = managergest.findAllTurnos();
		List<HgGuardia> lisHgGuardia = managergest.findAllGuardias();

		int diaId = 0;
		String diaNombre = "";

		DiasMG dia = new DiasMG(diaId, diaNombre);
		dia.setDiaId(1);
		dia.setDiaNombre("Lunes");
		liDias.add(dia);
		diaId++;
		DiasMG dia2 = new DiasMG(diaId, diaNombre);
		dia2.setDiaId(2);
		dia2.setDiaNombre("Martes");
		liDias.add(dia2);
		diaId++;
		DiasMG dia3 = new DiasMG(diaId, diaNombre);
		dia3.setDiaId(3);
		dia3.setDiaNombre("Miercoles");
		liDias.add(dia3);
		diaId++;
		DiasMG dia4 = new DiasMG(diaId, diaNombre);
		dia4.setDiaId(4);
		dia4.setDiaNombre("Jueves");
		liDias.add(dia4);
		diaId++;
		DiasMG dia5 = new DiasMG(diaId, diaNombre);
		dia5.setDiaId(5);
		dia5.setDiaNombre("Viernes");
		liDias.add(dia5);
		diaId++;
		DiasMG dia6 = new DiasMG(diaId, diaNombre);
		dia6.setDiaId(6);
		dia6.setDiaNombre("Sabado");
		liDias.add(dia6);
		diaId++;
		DiasMG dia7 = new DiasMG(diaId, diaNombre);
		dia7.setDiaId(7);
		dia7.setDiaNombre("Domingo");
		liDias.add(dia7);

		for (int diaMG = 0; diaMG < liDias.size(); diaMG++) {
			for (int lugares = 1; lugares <= lisHgLugar.size(); lugares++) {
				for (int turno = 0; turno < lisHgTurno.size(); turno++) {
					for (int guardias = 0; guardias < lisHgGuardia.size(); guardias++) {
						System.out.println(Integer.parseInt(decision[guardias][turno][lugares][diaMG].toString()));
						System.out.println(managergest.LugarByID(lugares).getLugNroGuardias());
						R1 += Math.abs(decision[guardias][turno][lugares][diaMG] - managergest.LugarByID(lugares).getLugNroGuardias());
						System.out.println(R1);
					}
				}
			}
		}
		return R1;
	}
	
	// Restriccion2 Garantizar que un guardia maximo un turno en el dia
	public Integer restriccion2(Integer[][][][] decision) {
		Integer R2 = 0;
		Integer nroGuardias = managergest.findAllGuardias().size();
		Integer nroPuestos = managergest.findAllLugares().size();
		Integer nroTurnos = managergest.findAllTurnos().size();

		List<HgLugare> lisHgLugar = managergest.findAllLugares();
		List<DiasMG> liDias = new ArrayList<DiasMG>();
		List<HgTurno> lisHgTurno = managergest.findAllTurnos();
		List<HgGuardia> lisHgGuardia = managergest.findAllGuardias();

		int diaId = 0;
		String diaNombre = "";

		DiasMG dia = new DiasMG(diaId, diaNombre);
		dia.setDiaId(1);
		dia.setDiaNombre("Lunes");
		liDias.add(dia);
		diaId++;
		DiasMG dia2 = new DiasMG(diaId, diaNombre);
		dia2.setDiaId(2);
		dia2.setDiaNombre("Martes");
		liDias.add(dia2);
		diaId++;
		DiasMG dia3 = new DiasMG(diaId, diaNombre);
		dia3.setDiaId(3);
		dia3.setDiaNombre("Miercoles");
		liDias.add(dia3);
		diaId++;
		DiasMG dia4 = new DiasMG(diaId, diaNombre);
		dia4.setDiaId(4);
		dia4.setDiaNombre("Jueves");
		liDias.add(dia4);
		diaId++;
		DiasMG dia5 = new DiasMG(diaId, diaNombre);
		dia5.setDiaId(5);
		dia5.setDiaNombre("Viernes");
		liDias.add(dia5);
		diaId++;
		DiasMG dia6 = new DiasMG(diaId, diaNombre);
		dia6.setDiaId(6);
		dia6.setDiaNombre("Sabado");
		liDias.add(dia6);
		diaId++;
		DiasMG dia7 = new DiasMG(diaId, diaNombre);
		dia7.setDiaId(7);
		dia7.setDiaNombre("Domingo");
		liDias.add(dia7);

		Integer nroDias = liDias.size();

		for (DiasMG diaMG : liDias) {
			for (HgGuardia guardias : lisHgGuardia) {
				for (HgTurno turno : lisHgTurno) {
					for (HgLugare lugares : lisHgLugar) {
						R2 += Math
								.abs(decision[nroGuardias][nroTurnos][nroPuestos][nroDias] - 1);
					}
				}
			}
		}
		return R2;
	}

	// Restriccion3 asegurar que un guardia no pueda doblar turno
	public Integer restriccion3(Integer[][][][] decision) {
		Integer R3 = 0;
		Integer nroGuardias = managergest.findAllGuardias().size();
		Integer nroPuestos = managergest.findAllLugares().size();
		Integer nroTurnos = managergest.findAllTurnos().size();

		List<HgLugare> lisHgLugar = managergest.findAllLugares();
		List<DiasMG> liDias = new ArrayList<DiasMG>();
		List<HgTurno> lisHgTurno = managergest.findAllTurnos();
		List<HgGuardia> lisHgGuardia = managergest.findAllGuardias();

		int diaId = 0;
		String diaNombre = "";

		DiasMG dia = new DiasMG(diaId, diaNombre);
		dia.setDiaId(1);
		dia.setDiaNombre("Lunes");
		liDias.add(dia);
		diaId++;
		DiasMG dia2 = new DiasMG(diaId, diaNombre);
		dia2.setDiaId(2);
		dia2.setDiaNombre("Martes");
		liDias.add(dia2);
		diaId++;
		DiasMG dia3 = new DiasMG(diaId, diaNombre);
		dia3.setDiaId(3);
		dia3.setDiaNombre("Miercoles");
		liDias.add(dia3);
		diaId++;
		DiasMG dia4 = new DiasMG(diaId, diaNombre);
		dia4.setDiaId(4);
		dia4.setDiaNombre("Jueves");
		liDias.add(dia4);
		diaId++;
		DiasMG dia5 = new DiasMG(diaId, diaNombre);
		dia5.setDiaId(5);
		dia5.setDiaNombre("Viernes");
		liDias.add(dia5);
		diaId++;
		DiasMG dia6 = new DiasMG(diaId, diaNombre);
		dia6.setDiaId(6);
		dia6.setDiaNombre("Sabado");
		liDias.add(dia6);
		diaId++;
		DiasMG dia7 = new DiasMG(diaId, diaNombre);
		dia7.setDiaId(7);
		dia7.setDiaNombre("Domingo");
		liDias.add(dia7);

		Integer nroDias = liDias.size();

		for (HgGuardia guardias : lisHgGuardia) {
			for (DiasMG diaMG : liDias) {
				for (HgLugare lugares : lisHgLugar) {
					R3 += Math
							.abs(Math
									.abs(decision[nroGuardias][3][nroPuestos][nroDias]
											- decision[nroGuardias][1][nroPuestos][nroDias + 1]) - 1);
				}
			}
		}
		return R3;
	}

	// Restriccion4 guardias studiantes deben tener horario fijo para tener
	// sabado y domingo
	public Integer restriccion4(Integer[][][][] decision) {
		Integer R4 = 0;
		Integer nroGuardias = managergest.findAllGuardias().size();
		Integer nroPuestos = managergest.findAllLugares().size();
		Integer nroTurnos = managergest.findAllTurnos().size();

		List<HgLugare> lisHgLugar = managergest.findAllLugares();
		List<DiasMG> liDias = new ArrayList<DiasMG>();
		List<HgTurno> lisHgTurno = managergest.findAllTurnos();
		List<HgGuardia> lisHgGuardia = managergest.findAllGuardias();

		int diaId = 0;
		String diaNombre = "";

		DiasMG dia = new DiasMG(diaId, diaNombre);
		dia.setDiaId(1);
		dia.setDiaNombre("Lunes");
		liDias.add(dia);
		diaId++;
		DiasMG dia2 = new DiasMG(diaId, diaNombre);
		dia2.setDiaId(2);
		dia2.setDiaNombre("Martes");
		liDias.add(dia2);
		diaId++;
		DiasMG dia3 = new DiasMG(diaId, diaNombre);
		dia3.setDiaId(3);
		dia3.setDiaNombre("Miercoles");
		liDias.add(dia3);
		diaId++;
		DiasMG dia4 = new DiasMG(diaId, diaNombre);
		dia4.setDiaId(4);
		dia4.setDiaNombre("Jueves");
		liDias.add(dia4);
		diaId++;
		DiasMG dia5 = new DiasMG(diaId, diaNombre);
		dia5.setDiaId(5);
		dia5.setDiaNombre("Viernes");
		liDias.add(dia5);
		diaId++;
		DiasMG dia6 = new DiasMG(diaId, diaNombre);
		dia6.setDiaId(6);
		dia6.setDiaNombre("Sabado");
		liDias.add(dia6);
		diaId++;
		DiasMG dia7 = new DiasMG(diaId, diaNombre);
		dia7.setDiaId(7);
		dia7.setDiaNombre("Domingo");
		liDias.add(dia7);

		Integer nroDias = liDias.size();

		for (HgGuardia guardias : lisHgGuardia) {
			for (DiasMG diaMG : liDias) {
				if (guardias.getGuaCasoEstudio() == true
						&& (diaMG.getDiaId() == 6 || diaMG.getDiaId() == 7))
					for (HgLugare lugares : lisHgLugar) {
						R4 += decision[nroGuardias][nroTurnos][nroPuestos][nroDias];
					}
			}
		}
		return R4;
	}

	// Restriccion5 guardias perteneciente a argis y chofer rotan CCTV
	public Integer restriccion5(Integer[][][][] decision) {
		Integer R5 = 0;
		Integer nroGuardias = managergest.findAllGuardias().size();
		Integer nroPuestos = managergest.findAllLugares().size();
		Integer nroTurnos = managergest.findAllTurnos().size();

		List<HgLugare> lisHgLugar = managergest.findAllLugares();
		List<DiasMG> liDias = new ArrayList<DiasMG>();
		List<HgTurno> lisHgTurno = managergest.findAllTurnos();
		List<HgGuardia> lisHgGuardia = managergest.findAllGuardias();

		int diaId = 0;
		String diaNombre = "";

		DiasMG dia = new DiasMG(diaId, diaNombre);
		dia.setDiaId(1);
		dia.setDiaNombre("Lunes");
		liDias.add(dia);
		diaId++;
		DiasMG dia2 = new DiasMG(diaId, diaNombre);
		dia2.setDiaId(2);
		dia2.setDiaNombre("Martes");
		liDias.add(dia2);
		diaId++;
		DiasMG dia3 = new DiasMG(diaId, diaNombre);
		dia3.setDiaId(3);
		dia3.setDiaNombre("Miercoles");
		liDias.add(dia3);
		diaId++;
		DiasMG dia4 = new DiasMG(diaId, diaNombre);
		dia4.setDiaId(4);
		dia4.setDiaNombre("Jueves");
		liDias.add(dia4);
		diaId++;
		DiasMG dia5 = new DiasMG(diaId, diaNombre);
		dia5.setDiaId(5);
		dia5.setDiaNombre("Viernes");
		liDias.add(dia5);
		diaId++;
		DiasMG dia6 = new DiasMG(diaId, diaNombre);
		dia6.setDiaId(6);
		dia6.setDiaNombre("Sabado");
		liDias.add(dia6);
		diaId++;
		DiasMG dia7 = new DiasMG(diaId, diaNombre);
		dia7.setDiaId(7);
		dia7.setDiaNombre("Domingo");
		liDias.add(dia7);

		Integer nroDias = liDias.size();

		for (HgGuardia guardias : lisHgGuardia) {
			if (guardias.getGuaCctv() == true
					|| guardias.getGuaChofer() == true)
				for (HgLugare lugares : lisHgLugar) {
					if (lugares.getLugCctv() == false)
						for (DiasMG diaMG : liDias) {
							for (HgTurno turno : lisHgTurno) {
								R5 += decision[nroGuardias][nroTurnos][nroPuestos][nroDias];
							}
						}
				}
		}
		return R5;
	}

	// Restriccion6 guardias pertenecen al grupo de enrolamienrto
	public Integer restriccion6(Integer[][][][] decision) {
		Integer R6 = 0;
		Integer nroGuardias = managergest.findAllGuardias().size();
		Integer nroPuestos = managergest.findAllLugares().size();
		Integer nroTurnos = managergest.findAllTurnos().size();

		List<HgLugare> lisHgLugar = managergest.findAllLugares();
		List<DiasMG> liDias = new ArrayList<DiasMG>();
		List<HgTurno> lisHgTurno = managergest.findAllTurnos();
		List<HgGuardia> lisHgGuardia = managergest.findAllGuardias();

		int diaId = 0;
		String diaNombre = "";

		DiasMG dia = new DiasMG(diaId, diaNombre);
		dia.setDiaId(1);
		dia.setDiaNombre("Lunes");
		liDias.add(dia);
		diaId++;
		DiasMG dia2 = new DiasMG(diaId, diaNombre);
		dia2.setDiaId(2);
		dia2.setDiaNombre("Martes");
		liDias.add(dia2);
		diaId++;
		DiasMG dia3 = new DiasMG(diaId, diaNombre);
		dia3.setDiaId(3);
		dia3.setDiaNombre("Miercoles");
		liDias.add(dia3);
		diaId++;
		DiasMG dia4 = new DiasMG(diaId, diaNombre);
		dia4.setDiaId(4);
		dia4.setDiaNombre("Jueves");
		liDias.add(dia4);
		diaId++;
		DiasMG dia5 = new DiasMG(diaId, diaNombre);
		dia5.setDiaId(5);
		dia5.setDiaNombre("Viernes");
		liDias.add(dia5);
		diaId++;
		DiasMG dia6 = new DiasMG(diaId, diaNombre);
		dia6.setDiaId(6);
		dia6.setDiaNombre("Sabado");
		liDias.add(dia6);
		diaId++;
		DiasMG dia7 = new DiasMG(diaId, diaNombre);
		dia7.setDiaId(7);
		dia7.setDiaNombre("Domingo");
		liDias.add(dia7);

		Integer nroDias = liDias.size();

		for (HgGuardia guardias : lisHgGuardia) {
			if (guardias.getGuaControlAccesos() == true)
				for (HgLugare lugares : lisHgLugar) {
					if (lugares.getLugControlAccesos() == false)
						for (DiasMG diaMG : liDias) {
							for (HgTurno turno : lisHgTurno) {
								R6 += decision[nroGuardias][nroTurnos][nroPuestos][nroDias];
							}
						}
				}
		}
		return R6;
	}

	// Restriccion7 requieren guardias motorizado por turno
	public Integer restriccion7(Integer[][][][] decision) {
		Integer R7 = 0;
		Integer nroGuardias = managergest.findAllGuardias().size();
		Integer nroPuestos = managergest.findAllLugares().size();
		Integer nroTurnos = managergest.findAllTurnos().size();

		List<HgLugare> lisHgLugar = managergest.findAllLugares();
		List<DiasMG> liDias = new ArrayList<DiasMG>();
		List<HgTurno> lisHgTurno = managergest.findAllTurnos();
		List<HgGuardia> lisHgGuardia = managergest.findAllGuardias();

		int diaId = 0;
		String diaNombre = "";

		DiasMG dia = new DiasMG(diaId, diaNombre);
		dia.setDiaId(1);
		dia.setDiaNombre("Lunes");
		liDias.add(dia);
		diaId++;
		DiasMG dia2 = new DiasMG(diaId, diaNombre);
		dia2.setDiaId(2);
		dia2.setDiaNombre("Martes");
		liDias.add(dia2);
		diaId++;
		DiasMG dia3 = new DiasMG(diaId, diaNombre);
		dia3.setDiaId(3);
		dia3.setDiaNombre("Miercoles");
		liDias.add(dia3);
		diaId++;
		DiasMG dia4 = new DiasMG(diaId, diaNombre);
		dia4.setDiaId(4);
		dia4.setDiaNombre("Jueves");
		liDias.add(dia4);
		diaId++;
		DiasMG dia5 = new DiasMG(diaId, diaNombre);
		dia5.setDiaId(5);
		dia5.setDiaNombre("Viernes");
		liDias.add(dia5);
		diaId++;
		DiasMG dia6 = new DiasMG(diaId, diaNombre);
		dia6.setDiaId(6);
		dia6.setDiaNombre("Sabado");
		liDias.add(dia6);
		diaId++;
		DiasMG dia7 = new DiasMG(diaId, diaNombre);
		dia7.setDiaId(7);
		dia7.setDiaNombre("Domingo");
		liDias.add(dia7);

		Integer nroDias = liDias.size();

		for (DiasMG diaMG : liDias) {
			for (HgGuardia guardias : lisHgGuardia) {
				if (guardias.getGuaMotorizado() == true)
					for (HgTurno turno : lisHgTurno)
						for (HgLugare lugares : lisHgLugar) {
							R7 += Math
									.abs(decision[nroGuardias][nroTurnos][nroPuestos][nroDias] - 3);
						}
			}
		}
		return R7;
	}

	//generacion a través de las restricciones requeridas
	public Integer funcionObjetivo(Integer[][][][] decision) throws Exception {
		Integer R1 = restriccion1(decision);
		System.out.println("-------------> R1 respuesta:"+R1);
		Integer R2 = restriccion2(decision);
		Integer R3 = restriccion3(decision);
		Integer R4 = restriccion4(decision);
		Integer R5 = restriccion5(decision);
		Integer R6 = restriccion6(decision);
		Integer R7 = restriccion7(decision);
		Integer f = R1 + R2 + R3 + R4 + R5 + R6 + R7;
	
		for (int gua = 0; gua <= nroGuardias; gua++) {
			for (int turn = 0; turn <= nroTurnos; turn++) {
				for (int lug = 0; lug <= nroPuestos; lug++) {
					for (int diasl = 1; diasl <= nroDias; diasl++) {
						cadena += cadena+ decision[gua][turn][lug][diasl];
						}
					}
				}
			}
	
		for(int i=0; i<resultado.length;i++){
				if(resultado[i]!= null){
					resultado[i][0].add(cadena);
					resultado[i][1].add(f.toString());
			}
		}
		return f;
	}

	//metodo de mutar
	public String mutar(){
		
		double pmi = 0.05;
		double pmf = 0.1;
		for(int i = 0; i < child.length(); i++){
			String cad = "";
			double q = Math.random();
			char mander = child.charAt(i);
			if(q > pmi && q < pmf){
				if(mander == '0') cad += "1";
				else cad += "0";
			}
			else
				cad += mander;
			
			child = cad;
		}
		return child;
	}

	//metodo de cruce o reproduccion
	public void  cruce() {
		
		int crossPoint = (int) (Math.random() * bestcadenapapa.length());// make
		for (int i = 0; i < bestcadenapapa.length(); ++i) {
						if (i < crossPoint)
							child = bestcadenapapa;
						else
							child = bestcadenamama;
		}
	}

	//muestra 
	public void displayTable(Integer[][][][] pi) {
		for (int gua = 0; gua <= nroGuardias; gua++) {
			for (int turn = 0; turn <= nroTurnos; turn++) {
				for (int lug = 0; lug <= nroPuestos; lug++) {
					for (int diasl = 0; diasl <= nroDias; diasl++) {
						System.out.println(pi[gua][turn][lug][diasl] + "  ");
					}
					System.out.println("/n");
				}
			}
		}
		
	}
	
	//metodo de seleccion
	public 	void seleccion(){
		 Integer pos;
		 ArrayList<String> tmp;
	
		 for(int i=0; i<resultado.length-1; i++)
			{
			   pos = i;
			   for (int j = i + 1; j < resultado.length; j++){ 
                   if (Integer.parseInt(resultado[j][1].toString()) < Integer.parseInt(resultado[pos][1].toString())){
                       pos = j;
                   }
			   }
			   if (pos != i){ 
				   tmp = resultado[i][1];
				   resultado[i][1] = resultado[pos][1];
				   resultado[pos][1] = tmp;
			   }
			}
		 
				bestpapa = Integer.parseInt(resultado[0][1].toString());
				bestcadenapapa = resultado[0][0].toString();
				
				bestmama= Integer.parseInt(resultado[1][1].toString());
				bestcadenamama = resultado[1][0].toString();
	}
}
