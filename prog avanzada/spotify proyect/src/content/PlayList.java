package content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayList  implements Serializable{
	private static final long serialVersionUID = 6555065896756091380L;
	private Long id;
	private String name;
	private static Long nextId=0L;
	private List<Content> ContentPlayList;
	
	
	
	public PlayList(String name) 
	{
		super();
		this.id = getNextId() ;
		this.name = name;
		ContentPlayList= new ArrayList<>();
	}

	public void addContent(Content addThisToPL)
	{
		this.ContentPlayList.add(addThisToPL);
	}
	
	private Long getNextId() 
	{
		return nextId++;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Content getPlayListPos0() 
	{
		return ContentPlayList.get(0);
	}

	public List<Content> getContentPlayList() 
	{
		return ContentPlayList;
	}
	
	

}
