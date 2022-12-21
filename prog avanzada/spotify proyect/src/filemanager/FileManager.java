package filemanager;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.stream.Stream;

import cliente.PremiumClient;
import cliente.StandardClient;
import content.Podcast;
import content.Song;
import producer.Producer;

public class FileManager {
	
	//write songs to file 
	private static final String nombreP="clientesP.dat";
	private static final String nombreS="clientesS.dat";
	private static final String nombreSG="canciones.dat";
	private static final String nombrePOD="podcast.dat";
	private static final String nombrePROD="producer.dat";
	
	public static void writeSongToFile(File file, List<Song> songs) throws IOException
	{
		
		
			try (FileOutputStream fos = new FileOutputStream(nombreSG,false);
                    ObjectOutputStream oos = new ObjectOutputStream(fos)) 
			 {
	            for(Song song : songs) 
	            {
	                oos.writeObject(song);
	            }
	            oos.close();
			    fos.close();
			    System.out.println("archivo canciones guardado correctamente");
			    
			}
			catch (IOException e) 
			{
	            System.out.println("Error en sobreescribir en archivo de canciones");
	            e.printStackTrace();
	        }
	}
	
	public static void writePodcastToFile(File file, List<Podcast> podcasts) throws IOException
	{
		
		
			try (FileOutputStream fos = new FileOutputStream(nombrePOD,false);
                    ObjectOutputStream oos = new ObjectOutputStream(fos)) 
			 {
	            for(Podcast pod : podcasts) 
	            {
	                oos.writeObject(pod);
	            }
	            oos.close();
			    fos.close();
			    System.out.println("archivo canciones guardado correctamente");
			    
			}
			catch (IOException e) 
			{
	            System.out.println("Error en sobreescribir en archivo de canciones");
	            e.printStackTrace();
	        }
	}
	
	
	//write clients to file
	public static void writeClientSToFile(File file, List<StandardClient> clients) throws IOException
	{
		
			 try (FileOutputStream fos = new FileOutputStream(nombreS,false);
                     ObjectOutputStream oos = new ObjectOutputStream(fos)) 
			 {
	            for(StandardClient cliente : clients) 
	            {
	                oos.writeObject(cliente);
	            }
	            oos.close();
		           fos.close();
		        System.out.println("archivo clientes guardado correctamente");
			 }
	         catch(IOException ex)
	         {
	        	 System.out.println("Error en sobreescribir en archivo de clientes");
	        	 ex.printStackTrace();	
		     }
		
	}
	
	public static void writeClientPToFile(File file, List<PremiumClient> clients) throws IOException
	{
		
			 try (FileOutputStream fos = new FileOutputStream(nombreP,false);
                     ObjectOutputStream oos = new ObjectOutputStream(fos)) 
			 {
	            for(PremiumClient cliente : clients) 
	            {
	                oos.writeObject(cliente);
	            }
	            oos.close();
		           fos.close();
		        System.out.println("archivo clientes guardado correctamente");
			 }
	         catch(IOException ex)
	         {
	        	 System.out.println("Error en sobreescribir en archivo de clientes");
	        	 ex.printStackTrace();	
		     }
		
	}
	
	public static List<PremiumClient> writeClientPFileToList(File file, List<PremiumClient> clients) throws IOException
	{
		
		try (FileInputStream fis = new FileInputStream(nombreP);
	             ObjectInputStream ois = new ObjectInputStream(fis)) {

	            while(true) {
	            	PremiumClient client = (PremiumClient) ois.readObject();
	                clients.add(client);
	                System.out.println(client.getUserName());
	            }
	        }catch (EOFException | ClassNotFoundException e) {
	            return clients;
	        }
		
	}
	
	public static List<StandardClient> writeClientSFileToList(File file, List<StandardClient> clients) throws IOException
	{
		
		try (FileInputStream fis = new FileInputStream(nombreS);
	             ObjectInputStream ois = new ObjectInputStream(fis)) {

	            while(true) 
	            {
	            	StandardClient client = (StandardClient) ois.readObject();
	                clients.add(client);
	                System.out.println(client.getUserName());
	            }
	        }catch (EOFException | ClassNotFoundException e) {
	            return clients;
	        }
		
	}
	
	public static List<Song> writeSongFileToList(File file, List<Song> songs) throws IOException
	{
		 try (FileInputStream fis = new FileInputStream(nombreSG);
	             ObjectInputStream ois = new ObjectInputStream(fis)) {

	            while(true) 
	            {
	                Song song = (Song) ois.readObject();
	                songs.add(song);
	                System.out.println(song.getAlbum());
	            }
	        }catch (EOFException | ClassNotFoundException e)
		 	{
	            return songs;
	        }

	}
	
	public static List<Podcast> writePodcastFileToList(File file, List<Podcast> podcasts) throws IOException
	{
		 try (FileInputStream fis = new FileInputStream(nombrePOD);
	             ObjectInputStream ois = new ObjectInputStream(fis)) {

	            while(true) {
	                Podcast POD = (Podcast) ois.readObject();
	                podcasts.add(POD);
	                System.out.println(POD.getAuthor());
	            }
	        }catch (EOFException | ClassNotFoundException e) {
	            return podcasts;
	        }

	}
	
	
//	addProducerByDefaultFile
	
	public static void writeProducerToFile(File file, Map<Long,Producer> producers) throws IOException
	{
		try (FileOutputStream fos = new FileOutputStream(nombrePROD,false);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) 
		 {
           for(Producer producer: producers.values()) 
           {
               oos.writeObject(producer);
           }
           oos.close();
	           fos.close();
	        System.out.println("archivo productores guardado correctamente");
		 }
        catch(IOException ex)
        {
        	System.out.println("Error en sobreescribir en archivo de productores");
       	 
        	ex.printStackTrace();	
	     }
	}
	
	public static Map<Long,Producer> writeProducerFileToList(File file, Map<Long,Producer> producers) throws IOException
	{
		 try (FileInputStream fis = new FileInputStream(nombrePROD);
	             ObjectInputStream ois = new ObjectInputStream(fis)) {

	            while(true) {
	                Producer PROD = (Producer) ois.readObject();
	                producers.put(PROD.getId(),PROD);
	            }
	        }catch (EOFException | ClassNotFoundException e) {
	            return producers;
	        }

	}
	
	public static Map<Long,Producer> addProducerByDefaultFile(File fileProducerTXT) throws IOException
	{
		Map<Long,Producer> llenar= new HashMap<>();
		
		List<String> lines = Files.readAllLines(fileProducerTXT.toPath());
		
        Producer auxProducer;
        
        for(String line : lines) 
        {       	
            String[] producerData = line.split(";");
            String name = producerData[0];
            String nickName = producerData[1];

            auxProducer = new Producer(name,nickName);
            llenar.put(auxProducer.getId(),auxProducer);
        }
		return llenar;		
	}
	
}
	
