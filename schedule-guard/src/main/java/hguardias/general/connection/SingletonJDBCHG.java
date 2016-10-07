package hguardias.general.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Luis Alberto Cisneros Gómez
 * @author lcisneros
 *
 */
public class SingletonJDBCHG {

	private static String URL = "jdbc:postgresql://loalhost:5432/horarioGuardias";
	private static String USER = "postgres";
	private static String PASS = "root";
	private static String CONTROLER = "org.postgresql.Driver"; 

	private static SingletonJDBCHG jdbc;

	private SingletonJDBCHG() {
	}

	public static SingletonJDBCHG getInstance() {
		if (jdbc == null) {
			jdbc = new SingletonJDBCHG();
		}
		return jdbc;
	}
	
	private static Connection getConnection() throws ClassNotFoundException,
			SQLException, InstantiationException, IllegalAccessException {
		Connection conn = null;
		Class.forName(CONTROLER).newInstance();
		conn = DriverManager.getConnection(URL, USER, PASS);
		return conn;
	}

	/**
	 * Ejecuta las sencias sql insert,delete y update
	 * @param sql
	 * @return true o false
	 * @throws SQLException
	 */
	@SuppressWarnings("static-access")
	public boolean ejecutarSQL(String sql) throws SQLException {
		Connection conn = null;
		Statement st = null;
		try {
			conn = this.getConnection();
			st = conn.createStatement();
			st.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			System.out.println("Error en la ejecucion: " + e.getMessage());
			return false;
		} finally {
			if (st != null)
				st.close();
			if (conn != null)
				conn.close();
		}
	}

	/**
	 * Permire ejecutar la sentenciua sql select
	 * @param sql
	 * @return ResultSet
	 */
	@SuppressWarnings("static-access")
	public ResultSet consultaSQL(String sql) throws SQLException {
		Connection conn = null;
		try {
			conn = this.getConnection();
			return conn.createStatement().executeQuery(sql);
		} catch (Exception e) {
			System.out.println("Error en la consulta: " + e.getMessage());
			return null;
		} finally {
			if (conn != null)
				conn.close();
		}
	}
	
	}

