package producer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import content.Content;

public class Producer  implements Serializable
{
	private static final long serialVersionUID = 6167016896756091380L;
	
	protected Long id;
	protected String name;
	protected String nickName;
	private List<Content> ContentList;
	private static Long nextId=0L; 
	
	
	public Producer(String name, 
			        String nickName) 
	{
		this.id = getNextId();
		this.name = name;
		this.nickName = nickName;
		this.ContentList=new ArrayList<>();
	}
	
	public void addContentList(Content addThis)
	{
		this.ContentList.add(addThis);
	}
	
	private Long getNextId() 
	{
		return nextId++;
	}
	
	public Long getId() 
	{
		return id;
	}

	@Override
	public String toString() {
		
		return "Producer [id=" + id + ", name=" + name + ", nickName=" + nickName + ", ContentList=" + ContentList
				+ "]";
	}

	public String getName() {
		return name;
	}

	public static void setNextId(Long nextId) {
		Producer.nextId = nextId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Content> getContentList() {
		return ContentList;
	}
	
	
}
