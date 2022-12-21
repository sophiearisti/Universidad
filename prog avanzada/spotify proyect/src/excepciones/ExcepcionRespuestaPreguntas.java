package excepciones;

public class ExcepcionRespuestaPreguntas extends Exception
{
	
	private String resp;
	
	public ExcepcionRespuestaPreguntas(String respuesta)
	{
		this.resp=respuesta;
	}

	public String getResp() {
		return resp;
	}
	
}
