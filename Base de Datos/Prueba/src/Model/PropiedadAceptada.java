package Model;

public class PropiedadAceptada 
{
	
	private int idprop;
	
	private String pais;
	
	private String municipio;
	
	private String departamento;
	
	private String tipo; 
	
	
	public PropiedadAceptada(int idprop, String pais, String municipio, String departamento, String tipo) {
		super();
		this.idprop = idprop;
		this.pais = pais;
		this.municipio = municipio;
		this.departamento = departamento;
		this.tipo = tipo;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getIdprop() {
		return idprop;
	}

	public void setIdprop(int idprop) {
		this.idprop = idprop;
	}
	

}
