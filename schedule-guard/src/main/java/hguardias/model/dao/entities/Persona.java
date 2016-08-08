package hguardias.model.dao.entities;

import java.util.Date;

public class Persona {

	private String perDNI;
	private String perNombres;
	private String perApellidos;
	private Date perFechaNacimiento;
	private String perGenero;
	private String perTelefono;
	private String perCelular;
	private String perCorreo;
	private String perGrupoSangineo;
	private String perEstadoCivil;

	public Persona() {
	}

	public Persona(String perDNI) {
		this.perDNI = perDNI;
	}

	public Persona(String perDNI, String perNombres, String perApellidos,
			String perTelefono, String perCelular, String perCorreo,
			Date perFechaNacimiento, String perGenero,String perGrupoSangineo, String perEstadoCivil) {
		this.perDNI = perDNI;
		this.perNombres = perNombres;
		this.perApellidos = perApellidos;
		this.perTelefono = perTelefono;
		this.perCelular = perCelular;
		this.perCorreo = perCorreo;
		this.perFechaNacimiento = perFechaNacimiento;
		this.perGenero = perGenero;
		this.perGrupoSangineo = perGrupoSangineo;
		this.perEstadoCivil = perEstadoCivil;
	}

	/**
	 * @return the perDNI
	 */
	public String getPerDNI() {
		return perDNI;
	}

	/**
	 * @param perDNI
	 *            the perDNI to set
	 */
	public void setPerDNI(String perDNI) {
		this.perDNI = perDNI;
	}

	/**
	 * @return the perNombres
	 */
	public String getPerNombres() {
		return perNombres;
	}

	/**
	 * @param perNombres
	 *            the perNombres to set
	 */
	public void setPerNombres(String perNombres) {
		this.perNombres = perNombres;
	}

	/**
	 * @return the perApellidos
	 */
	public String getPerApellidos() {
		return perApellidos;
	}

	/**
	 * @param perApellidos
	 *            the perApellidos to set
	 */
	public void setPerApellidos(String perApellidos) {
		this.perApellidos = perApellidos;
	}

	/**
	 * @return the perTelefono
	 */
	public String getPerTelefono() {
		return perTelefono;
	}

	/**
	 * @param perTelefono
	 *            the perTelefono to set
	 */
	public void setPerTelefono(String perTelefono) {
		this.perTelefono = perTelefono;
	}

	/**
	 * @return the perCelular
	 */
	public String getPerCelular() {
		return perCelular;
	}

	/**
	 * @param perCelular
	 *            the perCelular to set
	 */
	public void setPerCelular(String perCelular) {
		this.perCelular = perCelular;
	}

	/**
	 * @return the perCorreo
	 */
	public String getPerCorreo() {
		return perCorreo;
	}

	/**
	 * @param perCorreo
	 *            the perCorreo to set
	 */
	public void setPerCorreo(String perCorreo) {
		this.perCorreo = perCorreo;
	}

	/**
	 * @return the perFechaNacimiento
	 */
	public Date getPerFechaNacimiento() {
		return perFechaNacimiento;
	}

	/**
	 * @param perFechaNacimiento
	 *            the perFechaNacimiento to set
	 */
	public void setPerFechaNacimiento(Date perFechaNacimiento) {
		this.perFechaNacimiento = perFechaNacimiento;
	}

	/**
	 * @return the perGenero
	 */
	public String getPerGenero() {
		return perGenero;
	}

	/**
	 * @param perGenero
	 *            the perGenero to set
	 */
	public void setPerGenero(String perGenero) {
		this.perGenero = perGenero;
	}
	
	public String getPerGrupoSangineo() {
		return perGrupoSangineo;
	}
	
	public void setPerGrupoSangineo(String perGrupoSangineo) {
		this.perGrupoSangineo = perGrupoSangineo;
	}
	
	public String getPerEstadoCivil() {
		return perEstadoCivil;
	}
	
	public void setPerEstadoCivil(String perEstadoCivil) {
		this.perEstadoCivil = perEstadoCivil;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		System.out.println(perDNI + " " + perNombres + " " + perApellidos + " "
				+ perFechaNacimiento + " " + perGenero + " " + perTelefono
				+ " " + perCelular + " " + perCorreo);
		return super.toString();
	}
}
