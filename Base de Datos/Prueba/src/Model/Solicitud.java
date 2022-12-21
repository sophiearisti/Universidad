package Model;

public class Solicitud 
{
	private int idCliente;
	
	private int idPropiedad;
	
	private String fecha;
	
	private String pais;
	
	private String municipio;
	
	private String departamento;
	
	private String usuario; 
	
	

	

	public Solicitud(int idCliente, int idPropiedad, String fecha, String pais, String municipio, String departamento,
			String usuario) {
		super();
		this.idCliente = idCliente;
		this.idPropiedad = idPropiedad;
		this.fecha = fecha;
		this.pais = pais;
		this.municipio = municipio;
		this.departamento = departamento;
		this.usuario = usuario;
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getIdPropiedad() {
		return idPropiedad;
	}

	public void setIdPropiedad(int idPropiedad) {
		this.idPropiedad = idPropiedad;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}
