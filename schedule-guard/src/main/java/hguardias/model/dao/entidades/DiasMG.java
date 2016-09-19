package hguardias.model.dao.entidades;

public class DiasMG {

	private Integer diaId;
	private String diaNombre;
	
	public DiasMG(Integer diaId, String diaNombre) {
		
		this.diaId = diaId;
		this.diaNombre = diaNombre;
				
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
}