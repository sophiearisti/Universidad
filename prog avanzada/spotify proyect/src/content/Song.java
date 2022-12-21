package content;
import java.io.Serializable;

public class Song extends Content implements Serializable
{
	private static final long serialVersionUID = 6128016096756071380L;
	private String artist; 
	private String genre; 
	private String album; 
	
	public Song( 
    		String artist,
            String genre,
            String album, 
            String NombreDelContenido, 
            Integer duracion) 
    {
		    super(NombreDelContenido,duracion);
    		this.artist = artist;
    		this.genre = genre;
    		this.album = album;
    }
	
	public String getArtist() 
	{
		return artist;
	}
	
	public String getGenre() 
	{
		return genre;
	}
	
	public String getAlbum() {
		return album;
	}
	
	public String showCredits() 
	{
		String repuesto=super.showProductores();
		return repuesto+"\n"+ "Podcast [Author=" + artist + "]";
	}
	
	public String play() 
	{
		return "Se esta reproduciendo la cancion: "+ NombreDelContenido;
	}

}
