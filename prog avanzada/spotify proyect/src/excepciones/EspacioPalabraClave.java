package excepciones;

public class EspacioPalabraClave extends Exception{
	
private String resp;
	
	public EspacioPalabraClave(String respuesta)
	{
		this.resp=respuesta;
	}

	public String getResp() 
	{
		return resp;
	}

}
