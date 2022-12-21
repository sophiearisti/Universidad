package cliente;

import java.util.ArrayList;
import java.util.List;

import content.*;

public class StandardClient extends Client{
	
	private List<Long> songs;
	private PlayList onlyPL;

	public StandardClient(
			String userName, 
    		String password,
            String name,
            String lastName,
            Integer age) 
    {
    		
    		super(userName, password,name,lastName, age);
    		this.songs=new ArrayList<>();	
    }
	

	public void addContentToListSong(Long id)
	{
		this.songs.add(id);
	}
	
	public  List<Long> getSongs() {
        return songs;
    }
	
	public  boolean borrarIdsSong(Long id) {
    	
		//index of mira si esa cosa existe en la lista
		// si es menor a cero es porque no existe
    	if(songs.indexOf(id)<0)
    	{
    		return false;
    	}
    	else
    	{
    		songs.remove(songs.indexOf(id));
    		return true;
    	}
    }
	
	public boolean createOnlyPL(String name) 
	{
		this.onlyPL=new PlayList(name);
		return true;
	}
	
	public PlayList getOnlyPL() 
	{
		return onlyPL;
	}
    
	public void addContentToPL(Content addThisToPL)
	{
		//se manda esto a la funcion de pl
			this.onlyPL.addContent(addThisToPL);
	}
}
