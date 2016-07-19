package hguardias.model.dao.entities;

import java.util.Date;

public class Persona {
	
	private String perDNI;
	private String perTipoDNI;
	private String perNombres;
	private String perApellidos;
	private String perTelefono;
	private String perCelular;
	private String perCorreo;
	private String perPais;
	private Date perFechaNacimiento;
	private String perGenero;
	private String perDireccion;
	private String perGerencia;
	private String perDiscapacidadTipo;
	private String perDiscapacidadGrado;
	private String perDiscapacidadCarnet;
	private String cargo;
	private String jefeInmediato;
	private String correoJefeInmediato;
	
	public Persona() {}
	
	public Persona(String perDNI){
		this.perDNI = perDNI;
	}
	
	public Persona(String perDNI, String perTipoDNI, String perNombres,
			String perApellidos, String perTelefono, String perCelular,
			String perCorreo, String perPais, Date perFechaNacimiento,
			String cargo, String jefeInmediato, String pergerencia,
			String perDireccion, String perGenero,String correoJefeInmediato,
			String perDiscapacidadTipo, String perDiscapacidadGrado,
			String perDiscapacidadCarnet) {
		this.perDNI = perDNI;
		this.perTipoDNI = perTipoDNI;
		this.perNombres = perNombres;
		this.perApellidos = perApellidos;
		this.perTelefono = perTelefono;
		this.perCelular = perCelular;
		this.perCorreo = perCorreo;
		this.perGerencia = pergerencia;
		this.perPais = perPais;
		this.perFechaNacimiento = perFechaNacimiento;
		this.perDireccion = perDireccion;
		this.perGenero = perGenero;
		this.perDiscapacidadTipo = perDiscapacidadTipo;
		this.perDiscapacidadGrado = perDiscapacidadGrado;
		this.perDiscapacidadCarnet = perDiscapacidadCarnet;
		this.correoJefeInmediato = correoJefeInmediato;
		
		this.cargo = cargo;
		this.jefeInmediato = jefeInmediato;
	}
	
	public String getCorreoJefeInmediato() {
		return correoJefeInmediato;
	}

	public void setCorreoJefeInmediato(String correoJefeInmediato) {
		this.correoJefeInmediato = correoJefeInmediato;
	}

	public String getPerGerencia() {
		return perGerencia;
	}

	public void setPerGerencia(String perGerencia) {
		this.perGerencia = perGerencia;
	}

	/**
	 * @return the perDNI
	 */
	public String getPerDNI() {
		return perDNI;
	}

	/**
	 * @param perDNI the perDNI to set
	 */
	public void setPerDNI(String perDNI) {
		this.perDNI = perDNI;
	}

	/**
	 * @return the perTipoDNI
	 */
	public String getPerTipoDNI() {
		return perTipoDNI;
	}

	/**
	 * @param perTipoDNI the perTipoDNI to set
	 */
	public void setPerTipoDNI(String perTipoDNI) {
		this.perTipoDNI = perTipoDNI;
	}

	/**
	 * @return the perNombres
	 */
	public String getPerNombres() {
		return perNombres;
	}

	/**
	 * @param perNombres the perNombres to set
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
	 * @param perApellidos the perApellidos to set
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
	 * @param perTelefono the perTelefono to set
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
	 * @param perCelular the perCelular to set
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
	 * @param perCorreo the perCorreo to set
	 */
	public void setPerCorreo(String perCorreo) {
		this.perCorreo = perCorreo;
	}

	/**
	 * @return the perPais
	 */
	public String getPerPais() {
		return perPais;
	}

	/**
	 * @param perPais the perPais to set
	 */
	public void setPerPais(String perPais) {
		this.perPais = perPais;
	}

	/**
	 * @return the perFechaNacimiento
	 */
	public Date getPerFechaNacimiento() {
		return perFechaNacimiento;
	}

	/**
	 * @param perFechaNacimiento the perFechaNacimiento to set
	 */
	public void setPerFechaNacimiento(Date perFechaNacimiento) {
		this.perFechaNacimiento = perFechaNacimiento;
	}
	
	/**
	 * @return the perDireccion
	 */
	public String getPerDireccion() {
		return perDireccion;
	}

	/**
	 * @param perDireccion the perDireccion to set
	 */
	public void setPerDireccion(String perDireccion) {
		this.perDireccion = perDireccion;
	}
	
	/**
	 * @return the perGenero
	 */
	public String getPerGenero() {
		return perGenero;
	}

	/**
	 * @param perGenero the perGenero to set
	 */
	public void setPerGenero(String perGenero) {
		this.perGenero = perGenero;
	}

	/**
	 * @return the perDiscapacidadTipo
	 */
	public String getPerDiscapacidadTipo() {
		return perDiscapacidadTipo;
	}

	/**
	 * @param perDiscapacidadTipo the perDiscapacidadTipo to set
	 */
	public void setPerDiscapacidadTipo(String perDiscapacidadTipo) {
		this.perDiscapacidadTipo = perDiscapacidadTipo;
	}

	/**
	 * @return the perDiscapacidadGrado
	 */
	public String getPerDiscapacidadGrado() {
		return perDiscapacidadGrado;
	}

	/**
	 * @param perDiscapacidadGrado the perDiscapacidadGrado to set
	 */
	public void setPerDiscapacidadGrado(String perDiscapacidadGrado) {
		this.perDiscapacidadGrado = perDiscapacidadGrado;
	}

	/**
	 * @return the perDiscapacidadCarnet
	 */
	public String getPerDiscapacidadCarnet() {
		return perDiscapacidadCarnet;
	}

	/**
	 * @param perDiscapacidadCarnet the perDiscapacidadCarnet to set
	 */
	public void setPerDiscapacidadCarnet(String perDiscapacidadCarnet) {
		this.perDiscapacidadCarnet = perDiscapacidadCarnet;
	}
	
	/**
	 * @return the cargo
	 */
	public String getCargo() {
		return cargo;
	}

	/**
	 * @param cargo the cargo to set
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	/**
	 * @return the jefeInmediato
	 */
	public String getJefeInmediato() {
		return jefeInmediato;
	}

	/**
	 * @param jefeInmediato the jefeInmediato to set
	 */
	public void setJefeInmediato(String jefeInmediato) {
		this.jefeInmediato = jefeInmediato;
	}
	
	
	
}
