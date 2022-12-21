package Model;

public class Tarjeta 
{
	private String tipoDeTarjeta;
	
	private Integer numeroTarjeta=0;
	
	private String nombrePoseedor=" ";
	
	private Float valor=0f;
	
	private String vencimiento;

	
	
	public String getTipoDeTarjeta() {
		return tipoDeTarjeta;
	}



	public void setTipoDeTarjeta(String tipoDeTarjeta) {
		this.tipoDeTarjeta = tipoDeTarjeta;
	}



	public Integer getNumeroTarjeta() {
		return numeroTarjeta;
	}



	public void setNumeroTarjeta(Integer numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}



	public String getNombrePoseedor() {
		return nombrePoseedor;
	}



	public void setNombrePoseedor(String nombrePoseedor) {
		this.nombrePoseedor = nombrePoseedor;
	}



	public Float getValor() {
		return valor;
	}



	public void setValor(Float valor) {
		this.valor = valor;
	}




	public String getVencimiento() {
		return vencimiento;
	}



	public void setVencimiento(String vencimiento) {
		this.vencimiento = vencimiento;
	}



	public Tarjeta(String tipoDeTarjeta, Integer numeroTarjeta, String nombrePoseedor, Float valor,
			String vencimiento)
	{
		super();
		this.tipoDeTarjeta = tipoDeTarjeta;
		this.numeroTarjeta = numeroTarjeta;
		this.nombrePoseedor = nombrePoseedor;
		this.valor = valor;
		this.vencimiento = vencimiento;
	}
	
	
	

}
