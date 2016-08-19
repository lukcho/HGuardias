package hguardias.model.manager;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import hguardias.model.dao.entities.HgParametro;
import hguardias.model.dao.entities.Persona;
import hguardias.model.generic.ConsumeREST;
import hguardias.model.generic.Funciones;

@Stateless
public class ManagerBuscar {

	@EJB
	private ManagerDAO mDAO;

	public ManagerBuscar() {
		mDAO = new ManagerDAO();
	}

	public Persona buscarPersonaWSReg(String dni) throws Exception {
		HgParametro param = parametroByID("find_personas");
		JSONObject respuesta = ConsumeREST.consumeGetRestEasyObject(param
				.getParValor() + dni);
		if (respuesta.isEmpty())
			return null;
		else {
			System.out.println("Respuesta FindPersonas ---> " + respuesta);
			Persona p = new Persona();
			p.setPerDNI(Funciones.evaluarDatoWS(respuesta.get("perDni")));
			p.setPerNombres(Funciones.evaluarDatoWS(respuesta.get("perNombres")));
			p.setPerApellidos(Funciones.evaluarDatoWS(respuesta.get("perApellidos")));
			p.setPerFechaNacimiento(Funciones.evaluarDatoWSDate(respuesta.get("perFechaNacimiento")));
			p.setPerGenero(Funciones.evaluarDatoWS(respuesta.get("perGenero")));
			p.setPerTelefono(Funciones.evaluarDatoWS(respuesta.get("perTelefono")));
			p.setPerCelular(Funciones.evaluarDatoWS(respuesta.get("perCelular")));
			p.setPerCorreo(Funciones.evaluarDatoWS(respuesta.get("perCorreo")));	
			p.setPerEstadoCivil(Funciones.evaluarDatoWS(respuesta.get("perEstadoCivil")));
			if(!((JSONArray)respuesta.get("genSalud")).isEmpty()){
				
			    p.setPerGrupoSangineo(Funciones.evaluarDatoWS(((JSONObject)((JSONArray) respuesta.get("genSalud")).get(0)).get("sldGrupoSanguineo")));
			   }else{
			    p.setPerGrupoSangineo("");
			   }
			return p;
		}
	}

	/**
 	 * Metodo para el envio de mensajes usando WS
	 * 
	 * @param para
	 * @param asunto
	 * @param body
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void envioMailWS(String para, String asunto, String body)
			throws Exception {
		HgParametro param = parametroByID("envio_mail");
		HgParametro idWS = parametroByID("id_ws_mail");
		JSONObject objSalida = new JSONObject();
		objSalida.put("id", idWS.getParValor());
		objSalida.put("para", para);
		objSalida.put("asunto", asunto);
		objSalida.put("body", body);
		System.out.println("Envio Mail ---> " + objSalida);
		String url = param.getParValor();
		JSONObject respuesta = ConsumeREST.postClientRestEasy(url, objSalida);
		if (!respuesta.get("respuesta").equals("OK"))
			throw new Exception("Error al enviar el correo. (WS)");
	}
	
	/**
	 * Metodo para el envio de mensajes al administrador usando WS
	 * 
	 * @param asunto
	 * @param body
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void envioMailAdminWS(String asunto, String body)
			throws Exception {
		HgParametro param = parametroByID("envio_mail");
		HgParametro idWS = parametroByID("id_ws_mail");
		HgParametro adminWS = parametroByID("admin_correo");
		JSONObject objSalida = new JSONObject();
		objSalida.put("id", idWS.getParValor());
		objSalida.put("para", adminWS.getParValor());
		objSalida.put("asunto", asunto);
		objSalida.put("body", body);
		System.out.println("Envio Mail ---> " + objSalida);
		String url = param.getParValor();
		JSONObject respuesta = ConsumeREST.postClientRestEasy(url, objSalida);
		if (!respuesta.get("respuesta").equals("OK"))
			throw new Exception("Error al enviar el correo. (WS)");
	}

	/**
	 * buscar los vehuculos por ID
	 * 
	 * @param vehi_id
	 * @throws Exception
	 */
	public HgParametro parametroByID(String parametro) throws Exception {
		return (HgParametro) mDAO.findById(HgParametro.class, parametro);
	}
}
