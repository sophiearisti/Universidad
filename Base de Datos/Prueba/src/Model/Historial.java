package Model;

public class Historial 
{
	
	private Integer vistiasTot;
	private Integer mes;
	private String idPropiedad;
	
	
	public Historial(Integer vistiasTot, Integer mes, String idPropiedad) {
		super();
		this.vistiasTot = vistiasTot;
		this.mes = mes;
		this.idPropiedad = idPropiedad;
	}


	public Integer getVistiasTot() {
		return vistiasTot;
	}


	public void setVistiasTot(Integer vistiasTot) {
		this.vistiasTot = vistiasTot;
	}


	public Integer getMes() {
		return mes;
	}


	public void setMes(Integer mes) {
		this.mes = mes;
	}


	public String getIdPropiedad() {
		return idPropiedad;
	}


	public void setIdPropiedad(String idPropiedad) {
		this.idPropiedad = idPropiedad;
	}
	
	

}
