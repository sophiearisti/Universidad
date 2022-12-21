package content;

import java.io.Serializable;
import playable.*;

public class Podcast extends Content  implements Serializable
{
	
	private static final long serialVersionUID = 6128016896756091380L;

	private String Author; 
	private String cathegory;
	
	public Podcast(String author, 
			String cathegory, 
			String NombreDelContenido, 
            Integer duracion) 
	{	
		super(NombreDelContenido,duracion);
		this.Author = author;
		this.cathegory = cathegory;
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}

	public String getCathegory() {
		return cathegory;
	}

	public void setCathegory(String cathegory) {
		this.cathegory = cathegory;
	}

	public String showCredits() {
		
		String repuesto=super.showProductores();
		return repuesto+"\n"+ "Podcast [Author=" + Author + "]";
	}
	
	public String play() 
	{
		return "Se esta reproduciendo el podcast: "+ NombreDelContenido;
	}
	
	
}
