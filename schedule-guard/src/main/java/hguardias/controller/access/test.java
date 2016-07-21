package hguardias.controller.access;

import org.json.simple.JSONObject;

import hguardias.model.manager.ManagerCarga;
import hguardias.model.dao.entities.HgParametro;
import hguardias.model.dao.entities.Persona;
import hguardias.model.generic.ConsumeREST;
import hguardias.model.generic.Funciones;

public class test {

	public static void main(String[] args) {
		try {
			System.out.println(buscarPersonaWSReg("1003443296").getPerNombres());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Persona buscarPersonaWSReg(String dni) throws Exception {
		JSONObject respuesta = ConsumeREST.consumeGetRestEasyObject("http://yachay-ws.yachay.gob.ec/data/WSPersonaEntity/dni=" + dni);
		if (respuesta.isEmpty())
			return null;
		else {
			System.out.println("Respuesta FindPersonas ---> " + respuesta);
			Persona p = new Persona();
			p.setPerDNI(Funciones.evaluarDatoWS(respuesta.get("perDni")));
			p.setPerNombres(Funciones.evaluarDatoWS(respuesta.get("perNombres")));
			p.setPerApellidos(Funciones.evaluarDatoWS(respuesta
					.get("perApellidos")));
			p.setPerCorreo(Funciones.evaluarDatoWS(respuesta.get("perCorreo")));
			p.setPerCelular(Funciones.evaluarDatoWS(respuesta.get("perCelular")));
			p.setPerTelefono(Funciones.evaluarDatoWS(respuesta
					.get("perTelefono")));
			return p;
		}
	}

}
