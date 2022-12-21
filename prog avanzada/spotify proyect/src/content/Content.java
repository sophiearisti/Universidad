package content;
import producer.*;
import playable.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

//esta es la  super-clase 

public abstract class Content implements Serializable, Playable
{
	private static final long serialVersionUID = 6188065896756091380L;

	
	protected Long id;
	protected String NombreDelContenido;
	protected Integer duracion;
	protected Integer numberOfDownloads;
	protected static Long nextId=0L; 
	protected Set<Producer> producers;
	
	public 	Content( 
			String NombreDelContenido, 
            Integer duration) 
    {
    		this.id = getNextId();
    		this.NombreDelContenido = NombreDelContenido;
    		this.duracion=duration;
    		this.numberOfDownloads=0;	
    		this.producers = new HashSet<Producer>();
    		
    }
	
	public void addDownload()
	{
		this.numberOfDownloads++;
	}
	
	public String getName() {
		return NombreDelContenido;
	}
	
	public Long getId() {
		return id;
	}
	
	public Integer getNumberOfDownloads() {
		return numberOfDownloads;
	}
	
	private Long getNextId() 
	{
		return nextId++;
	}
	
	public void addProducer(Producer producer) 
	{
		//se anade a la lista de productores
        producers.add(producer);
        
        //a cambio se anade el obejto nuevo en la lista del productor
        producer.addContentList(this);
    }

	public String showProductores() 
	
	{
		String sumatoria=" ";
		
		for(Producer producer: producers)
		{
			sumatoria=sumatoria+"\n "+producer.getName();
		}
		
		return "Content [producers=" + sumatoria+ "]";
	}

	public static void setNextId(Long nextId) {
		Content.nextId = nextId;
	}
	
	public abstract String showCredits();

	public String getNombreDelContenido() {
		return NombreDelContenido;
	}
	
}
