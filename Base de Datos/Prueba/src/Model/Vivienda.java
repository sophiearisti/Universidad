package Model;

public class Vivienda 
{
	private String pais;
	
	private String descripcion;
	
	private String img;
	
	private String fecha;
	
	private int id;
	
	private int estado;
	
	private float arriendo;
	
	private int  idArriendo;
	
	
	
	public int getIdArriendo() {
		return idArriendo;
	}

	public void setIdArriendo(int idArriendo) {
		this.idArriendo = idArriendo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	private String tipo;
	

	public float getArriendo() {
		return arriendo;
	}

	public void setArriendo(float arriendo) {
		this.arriendo = arriendo;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPais() 
	{
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) 
	{
		this.descripcion = descripcion;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	
	

}
