package hguardias.model.dao.entidades;

import java.util.List;


public class Dias {

	private Integer diaId;
	private String diaNombre;
	private List<Lugares> Lugares;
	
	public Dias(Integer diaId, String diaNombre, List<Lugares> Lugares) {
		
		this.diaId = diaId;
		this.diaNombre = diaNombre;
		this.Lugares= Lugares; 
				
	}

	public Integer getDiaId() {
		return diaId;
	}
	
	public void setDiaId(Integer diaId) {
		this.diaId = diaId;
	}
	
	public String getDiaNombre() {
		return diaNombre;
	}
	
	public void setDiaNombre(String diaNombre) {
		this.diaNombre = diaNombre;
	}
	
	public List<Lugares> getLugares() {
		return Lugares;
	}
	
	public void setLugares(List<Lugares> lugares) {
		Lugares = lugares;
	}
}