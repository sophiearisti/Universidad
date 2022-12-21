package cliente;

import java.util.ArrayList;

import content.*;

import java.util.List;

public class PremiumClient extends Client{
	
    private List<Long> songs;
    private List<Long> podcast;
    private List<PlayList> playLists;
    
	public PremiumClient(
			String userName, 
    		String password,
            String name,
            String lastName,
            Integer age) 
    {
    		
    		super(userName, password,name,lastName, age);
    		this.songs=new ArrayList<>();
    		this.podcast=new ArrayList<>();
    		this.playLists=new ArrayList<>();
    }
	
	
	public void addContentToListPodcast(Long id)
	{
		this.podcast.add(id);
	}
    

	public void addContentToListSong(Long id)
	{
		this.songs.add(id);
	}
    
   public  List<Long> getSongs() 
   {
        return songs;
    }
    
   public  List<Long> getPodcast() 
   {
       return podcast;
   }
   
    public  boolean borrarIdsPodcast(Long id) 
    {
    	
    	if(podcast.indexOf(id)<0)
    	{
    		return false;
    	}
    	else
    	{
    		podcast.remove(podcast.indexOf(id));
    		return true;
    	}
    }
    
    public  boolean borrarIdsSong(Long id) 
	{
    	
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
    
    public boolean createPL(String name) 
	{
		this.playLists.add(new PlayList(name));
		return true;
	}
    
    public Boolean addContentToPLWhen(Content addThisToPL, String PLName)
	{
		for(PlayList playlist : playLists)
		{
			if(PLName.equals(playlist.getName()))
			{
				if(playlist.getContentPlayList().size()==0)
				{
					playlist.addContent(addThisToPL);
				}
				else if(addThisToPL.getClass()==playlist.getPlayListPos0().getClass())
				{
					playlist.addContent(addThisToPL);
				}
				else
				{
					return false;
				}
			}
			
		}
		return true; 
	}
	
    public void addContentToPL(Content addThisToPL)
	{
		
    	playLists.get(playLists.size()-1).addContent(addThisToPL);
	}


	public List<PlayList> getPlayLists() 
	{
		return playLists;
	}
    
}
