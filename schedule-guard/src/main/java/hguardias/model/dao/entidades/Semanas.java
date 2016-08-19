package hguardias.model.dao.entidades;

import java.util.List;


public class Semanas {

	private Integer semanaId;
	private String semanaNombre;
	private List<Dias> dias;
	
	public Semanas(Integer semanaId, String semanaNombre, List<Dias> dias) {
		
		this.semanaId = semanaId;
		this.semanaNombre = semanaNombre;
		this.dias= dias; 
				
	}

	public Integer getSemanaId() {
		return semanaId;
	}
	
	public void setSemanaId(Integer semanaId) {
		this.semanaId = semanaId;
	}
	
	public String getSemanaNombre() {
		return semanaNombre;
	}
	
	public void setSemanaNombre(String semanaNombre) {
		this.semanaNombre = semanaNombre;
	}
	
	public List<Dias> getDias() {
		return dias;
	}
	
	public void setDias(List<Dias> dias) {
		this.dias = dias;
	}
}