package hguardias.model.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ejb.EJB;

import hguardias.general.connection.SingletonJDBC;
import hguardias.model.dao.entities.Persona;

/**
 * Contiene la lógica de negocio para realizar el proceso de reserva de sitios
 * 
 * @author
 * 
 */

public class ManagerCarga {

	private SingletonJDBC conDao;

	@EJB
	private ManagerDAO mDAO;

	public ManagerCarga() {
		conDao = SingletonJDBC.getInstance();
	}

	public static String consultaSQL(String usr) throws Exception {
		String cc = "jdbc:postgresql://10.1.0.158:5432/app_permisos?user=adm_svcyachay&password=_50STg-FGh2h";
		Connection conexion = null;
		Statement comando = null;
		ResultSet resultado = null;
		System.out.println(usr);
		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection(cc);
			comando = conexion.createStatement();
			resultado = comando
					.executeQuery("SELECT per_id FROM app_usuario WHERE usu_login = '"
							+ usr + "';");
			if (resultado.next()) {
				return resultado.getString("per_id");
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			if (resultado != null)
				resultado.close();
			if (comando != null)
				comando.close();
			if (conexion != null)
				conexion.close();
		}
	}

	/**
	 * Devuelve un funcionario por dni
	 * 
	 * @param dni
	 * @return Funcionario
	 * @throws Exception
	 */
	public Persona funcionarioByDNI(String usr_login) throws Exception {
		Persona f = null;
		ResultSet consulta = conDao
				.consultaSQL("SELECT p.per_id as dni , p.per_nombres as nombres, "
						+ " p.per_apellidos as apellidos, p.per_correo as correo, "
						+ " f.fun_cargo as cargo, f.fun_jefe_inmediato as jefe, f.fun_gerencia as gerencia, f.fun_direccion as direccion, "
						+ " (select pe.per_nombres||' '||pe.per_apellidos "
						+ " from gen_persona pe where pe.per_id=f.fun_jefe_inmediato) as nombreJefe "
						+ " FROM gen_persona p INNER JOIN gen_usuario u ON p.per_id = u.per_id "
						+ " INNER JOIN gen_funcionario f  on f.per_id = u.per_id "
						+ " WHERE u.usu_login = '" + usr_login + "'");
		if (consulta != null) {
			consulta.next();
			f = new Persona();
			f.setPerDNI(consulta.getString("dni"));
			f.setPerNombres(consulta.getString("nombres"));
			f.setPerApellidos(consulta.getString("apellidos"));
			f.setPerCorreo(consulta.getString("correo"));
		}
		return f;
	}

	/**
	 * Devuelve un persona solicitud por dni
	 * 
	 * @param dni
	 * @return Funcionario
	 * @throws Exception
	 */
	public Persona personasolicitudByDNI(String per_id) throws Exception {
		Persona f = null;
		ResultSet consulta = conDao
				.consultaSQL("SELECT f.per_dni as dni ,(select pe.per_nombres from gen_persona pe where pe.per_dni =f.fun_jefe_inmediato) as nombres,"
						+ "	(select pe.per_apellidos from gen_persona pe where pe.per_dni =f.fun_jefe_inmediato) as apellidos,"
						+ "	(select pe.per_apellidos from gen_persona pe where pe.per_dni =f.fun_jefe_inmediato) as correo"
						+ " from gen_persona p, gen_funcionarios_institucion f "
						+ "	 where p.per_dni= f.per_dni "
						+ "	 and p.per_dni='" + per_id + "'");
		
		if (consulta != null) {
			consulta.next();
			f = new Persona();
			f.setPerDNI(consulta.getString("dni"));
			f.setPerNombres(consulta.getString("nombres"));
			f.setPerApellidos(consulta.getString("apellidos"));
			f.setPerCorreo(consulta.getString("correo"));
		}
		return f;
	}
}
