package main;

import java.util.Scanner;
//import java.util.Arrays;
//import java.util.matchException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;
import java.nio.file.Files;
import java.util.stream.Stream;

import cliente.Client;
import cliente.PremiumClient;
import cliente.StandardClient;
import content.Content;
import content.PlayList;
import content.Podcast;
import content.Song;
import excepciones.*;
import filemanager.FileManager;
import producer.Producer;


public class Main
{

	public static void main(String[] args) throws IOException 
	{
		Scanner ingreso= new Scanner(System.in);
		Integer respuesta=0;
		
		List<Song> songDb = new ArrayList<>();
		List<PremiumClient> clientPDb = new ArrayList<>();
		List<StandardClient> clientSDb = new ArrayList<>();
		List<Podcast> podcastDb = new ArrayList<>();
		Map <Long, Producer> producerDb = new HashMap<Long, Producer>();

		
		File fileClientsP=new File ("clientesP.dat");
		File fileClientsS=new File ("clientesS.dat");
		File fileSongs=new File ("canciones.dat");
		File filePodcast=new File ("podcast.dat");
		File fileProducerTXT=new File ("src/archivos/producers.txt");
		File fileProducer=new File ("producer.dat");

        Boolean mal=false;
        Boolean entroAArchivo=false;
        Boolean entroACrearPlayList=false;
       
        //guardar lo de text file
       producerDb=FileManager.addProducerByDefaultFile(fileProducerTXT);
        
        
		do
		{
			 do
		     {
	        	try
	        	{
	        		System.out.println("MENU");
	        		System.out.println("OPCION 1. Adicionar un nuevo productor");//FUNCIONA
	    			System.out.println("OPCION 2. Adicionar nuevas canciones a la BD");//FUNCIONA
	    			System.out.println("OPCION 3. Adicionar nuevos podcast a la BD");//FUNCIONA
	    			System.out.println("OPCION 4. Registrar nuevos usuarios a la BD");//FUNCIONA
	    			System.out.println("OPCION 5. Listar canciones de un álbum");//FUNCIONA
	    			System.out.println("OPCION 6. Listar artistas para un género dado");//FUNCIONA
	    			System.out.println("OPCION 7. Para un usuario registrado, descargar una canción o podcast.");//FUNCIONA
	    			System.out.println("OPCION 8. Buscar canciones por palabras clave.");//FUNCIONA
	    			System.out.println("OPCION 9. Eliminar una cancion o podcast de su lista de ids.");
	    			System.out.println("OPCION 10. Guardar el estado actual de la base de datos de canciones, podcasts y usuarios.");//FUNCIONA
	    			System.out.println("OPCION 11. Cargar un estado previo.");//FUNCIONA
	    			System.out.println("OPCION 12. Mostrar los creditos ");//FUNCIONA
	    			System.out.println("OPCION 13. Anadir contenido a la playlist");
	    			System.out.println("OPCION 14. CREAR playlist");//FUNCIONA
	    			System.out.println("OPCION 15. Mostrar los productores");//FUNCIONA
	    			System.out.println("OPCION 16. Escuchar una cancion o podcast");
	    			System.out.println("OPCION 0. SALIR DEL PROGRAMA");//FUNCIONA1
	    			
	    			System.out.println("Ingresar opcion que desea: ");//FUNCIONA
	    			respuesta=Integer. parseInt(ingreso.nextLine());
	        		mal=true;
	        	}
	        	catch(NumberFormatException e)
	        	{
	        		System.err.println("el valor no es un numero");
	        		mal=false;
	        	}

		     }while(!mal);

			switch(respuesta)
			{
				case 1:// OPCION 1. Adicionar nnuevo productor a la BD
					if (addNewProducerToDB(ingreso, producerDb))
					{
					  //no es necesario esta opcion para utilizar las demas
					  System.out.println("Productor anadido a la base de datos");
	                  System.out.println("SE GUARDO CORRECTAMENTE");
	                  //EL MAP SE LLENA CON UN ARCHIVO
	                }
					else
					{
						System.out.println("Error en anadir el nuevo productor a la BD");
					}
					break;
				case 2:// OPCION 2. Adicionar nuevas canciones a la BD
					if (addNewSongToDB(songDb, ingreso,producerDb))
					{
					  System.out.println("Cancion anadida a la base de datos");
                      System.out.println("SE GUARDO CORRECTAMENTE");
                    }
					else
					{
						System.out.println("Error en anadir la nueva cancion a la BD");
					}
					break;
				case 3://OPCION 3. Adicionar nuevos podcast a la BD
					if (addNewPodcastToDB(podcastDb,ingreso,producerDb))
					{
					   //no es necesario anadir una condicion para ingresar aca
                       System.out.println("Podcast anadido a la base de datos");
                    }
					else
					{
						System.out.println("Error en anadir el usuario a la BD. El usuario ya existe");
					}
					break;
				case 4://opcion 4 Registrar nuevos usuarios a la BD
					if (addNewUserToDB(songDb, podcastDb, ingreso, clientPDb, clientSDb) && !(songDb.isEmpty()) && !(podcastDb.isEmpty()))
					{
                      System.out.println("Usuario anadido a la base de datos");
                    }
					else
					{
							System.out.println("Error en anadir el usuario a la BD. El usuario ya existe");
					}
					break;
				case 5://OPCION 5. Listar canciones de un álbum
						if(!(songDb.isEmpty()))
						{
							listSongsByAlbum(songDb, ingreso);
						}

					break;
				case 6://OPCION 6. Listar artistas para un género dado
						if(!(songDb.isEmpty()))
						{
							listSongsByArtist(songDb, ingreso);
						}

					break;
				case 7://OPCION 7. Para un usuario registrado, descargar una canción.
					if (addSongToList(songDb, podcastDb, clientSDb, clientPDb, ingreso) && !(songDb.isEmpty()) && !(clientPDb.isEmpty())  && !(clientSDb.isEmpty()) && !(podcastDb.isEmpty()))
					{
                      System.out.println("Cancion anadida a la base de datos");
                      System.out.println("SE GUARDO CORRECTAMENTE");
                    }
					else
					{
						System.out.println("Error en anadir la nueva cancion a la BD");
					}
					break;
				case 8://OPCION 8. Buscar canciones por palabras clave
					if (!(songDb.isEmpty()))
					{
						lookForSongs(songDb, ingreso);
                    }
					else
					{
						System.out.println("Error, no hay canciones en la BD");
					}
					break;
				case 9://OPCION 9. Eliminar una cancion o podcast de su lista de ids.
					if (!(songDb.isEmpty()) && !(clientPDb.isEmpty()) && !(clientSDb.isEmpty()))
					{
						eliminarIds(clientPDb, clientSDb, ingreso);
                    }
					else
					{
						System.out.println("Error, no hay canciones en la BD o usuarios");
					}
					break;
				case 10: //OPCION 10. Guardar el estado actual de la base de datos de canciones, podcasts y usuarios.
					if (!(songDb.isEmpty()) && !(clientPDb.isEmpty()) && !(clientSDb.isEmpty()) && !(podcastDb.isEmpty()))
					{
						FileManager.writeClientPToFile(fileClientsP,clientPDb);
						FileManager.writeClientSToFile(fileClientsS,clientSDb);
						FileManager.writeSongToFile(fileSongs,songDb);
						FileManager.writePodcastToFile(filePodcast,podcastDb);
						FileManager.writeProducerToFile(fileProducer,producerDb);
						entroAArchivo=true;
                    }
					else
					{
						System.out.println("Error, no hay canciones o podcasts en la BD o usuarios");
					}
					break; 
				case 11://OPCION 11.Cargar un estado previo.
					if (!(songDb.isEmpty()) && !(clientPDb.isEmpty())&& !(clientSDb.isEmpty())  && !(podcastDb.isEmpty()) &&  entroAArchivo)
					{
						clientPDb .clear();
						clientPDb=FileManager.writeClientPFileToList(fileClientsP, clientPDb);
						clientSDb .clear();
						clientSDb=FileManager.writeClientSFileToList(fileClientsS, clientSDb);
						songDb.clear();
						songDb=FileManager.writeSongFileToList(fileSongs, songDb);
						podcastDb.clear();
						podcastDb=FileManager.writePodcastFileToList(filePodcast, podcastDb);
						producerDb.clear();
						producerDb=FileManager.writeProducerFileToList(fileProducer, producerDb);
						reestablishId(clientPDb,clientSDb,songDb,podcastDb, producerDb);
						//FUNCION DE MANEJO DE IDS
						//SE MIRA EL TAMANO DE CADA LISTA
						//SE CAMBIA EL VALOR DE ID ESTATICO A UNO MENOR QUE EL TAMANO DE LA LISTA Y SHA
						
                    }
					else
					{
						System.out.println("Error, no hay canciones o podcasts en la BD o usuarios o no se ha creado el archivo");
					}
					
					break;
				case 12://OPCION 12. Mostrar los creditos 
					if (!(songDb.isEmpty()) && !(podcastDb.isEmpty()))
					{
						showCredits(ingreso,songDb,podcastDb);
                    }
					else
					{
						System.out.println("Error, no hay canciones o podcasts en la BD o usuarios o no se ha creado el archivo");
					}
					
					break;
				case 13://OPCION 13. Anadir contenido a la playlist
					if (!(songDb.isEmpty()) && !(clientPDb.isEmpty())&& !(clientSDb.isEmpty())  && !(podcastDb.isEmpty()) && entroACrearPlayList)
					{
						addContentToPlayList(clientPDb, clientSDb, ingreso,songDb,podcastDb);
                    }
					else
					{
						System.out.println("Error, no existen playlists");
					}
					break;
				case 14://OPCION 14. CREAR playlist
					if (!(songDb.isEmpty()) && !(clientPDb.isEmpty())&& !(clientSDb.isEmpty())  && !(podcastDb.isEmpty()))
					{
						optionCreaterPlayList(ingreso, clientSDb,songDb, clientPDb, podcastDb);
						entroACrearPlayList=true;
                    }
					else
					{
						System.out.println("Error, no hay canciones o podcasts en la BD o usuarios o no se ha creado el archivo");
					}
					break;
				case 15://OPCION 15. MOSTRAR LOS PRODUCTORES
					if (!(producerDb.isEmpty()))//PONER AQUI SI EL MAP ESTA VACIO
					{
						printProducersMap(producerDb);
                    }
					else
					{
						System.out.println("Error, no hay canciones o podcasts en la BD o usuarios o no se ha creado el archivo");
					}
					break;
				case 16://OPCION 15. MOSTRAR LOS PRODUCTORES
					if (!(songDb.isEmpty()) && !(podcastDb.isEmpty()))
					{
						playContent(songDb,podcastDb,ingreso);
                    }
					else
					{
						System.out.println("Error, no hay canciones o podcasts en la BD");
					}
					break;
				case 0:
					System.out.println("TERMINO");
					ingreso.close();
					break;
				default:
					System.out.println("ERROR");
					break;
			}

	 }while(respuesta!=0);
	}
	
	private static Boolean addNewProducerToDB(Scanner respuesta, Map producerDb)
	{
		String name;
		String nickName;
		
		System.out.println("nombre del nuevo productor: ");
	    name = respuesta.nextLine();
	    
	    System.out.println("Apodo del productor: ");
	    nickName = respuesta.nextLine();
	    
	    //se crea primero para tener el id y asi anadirlo al map con base al id
	    Producer producerReserved=new Producer(name,nickName);
	    //se anade al mapa llave el ID, contenido es el objeto tipo producer
	    producerDb.put(producerReserved.getId(),producerReserved);
	   
		return true;
	}

	private static Boolean addNewSongToDB(List<Song> songDb, Scanner respuesta, Map producerDb)
	{
		String name;
		String artist;
		String genre;
		Integer duration=0;
		String album;
		
		boolean mal;
		
		String seguir=" ";
	    Long producerId=0L;


	  System.out.println("Por favor ingrese la informacion de la nueva cancion: " );
      System.out.println("nombre de la nueva cancion: ");
      name = respuesta.nextLine();
      System.out.println("nombre del artista de la cancion: ");
      artist = respuesta.nextLine();
      System.out.println("genero de la cancion: ");
      genre =  respuesta.nextLine();
      System.out.println("album al que pertenece la cancion: ");
      album = respuesta.nextLine();
    	
      do
    	{
        	try
        	{
        		System.out.println("duracion de la cancion (en segundos): ");
        		duration=Integer.parseInt(respuesta.nextLine());
        		mal=true;
        	}//FormatException
        	catch(NumberFormatException e)
        	{
        		System.err.println("el valor no es un numero");
        		mal=false;
        	}

    	}while(!mal);
      
      
      //aqui se guarda la basico de todo para que se cree el arreglo de productores//
      songDb.add(new Song(artist, genre, album, name, duration));
      
      do
      {
    	  do
      	  {
          	try
          	{
          		
          		System.out.println("Ingrese Id del productor: "); 
          	    producerId=Long.parseLong(respuesta.nextLine());
          		mal=true;
          		
          	}//FormatException
          	catch(NumberFormatException e)
          	{
          		System.err.println("el valor no es un numero");
          		mal=false;
          	}
          	
       	 }while(!mal);
        
    	//mira si el id existe en el map y si existe se guarda en una variable tipo producer
    	  if (producerDb.containsKey(producerId)) 
    	  {
    		  Producer guardar=(Producer) producerDb.get(producerId);
    		  //se anade el producer a la lista de la cancion creada se envia a una funcion de alla
    		  songDb.get(songDb.size()-1).addProducer(guardar);

          } 
    	  else 
          {
              System.out.println("Productor no encontrado");
          }
    	  
    	  do{
				try
	    		{
					System.out.println("Desea ingresar otro productor?: (S/N)");  
			    	seguir=respuesta.nextLine();
	    			mal=true;
		    		evaluacion(seguir, "R");
	    		}
	    		catch(ExcepcionRespuestaPreguntas e)
	    		{
	    			System.err.println(e.getResp());
	    			mal=false;
	    		}
			}while(!mal);
    	  
      }while(seguir.equals("S")  || seguir.equals("s"));

      return true;
	}
	
	private static Boolean addNewPodcastToDB(List<Podcast> podcastDb, Scanner respuesta, Map producerDb)
	{

		String NombreDelContenido;
		String author;
		String category;
		Integer duration=0;
		boolean mal;
		Long producerId=0L;
		String seguir=" ";
         
	  System.out.println("Por favor ingrese la informacion del nuevo podcast: " );
	  
      System.out.println("nombre del nuevo podcast: ");
      NombreDelContenido = respuesta.nextLine();
      System.out.println("nombre del autor: ");
      author = respuesta.nextLine();
      System.out.println("categoria del podcast: ");
      category=  respuesta.nextLine();
      
      podcastDb.add(new Podcast(author, category, NombreDelContenido, duration));
      
      //se hace basicamente lo mismo que en cancion
      do{
    	  do
      	  {
          	try
          	{
          		
          		System.out.println("Ingrese Id del productor: "); 
          		producerId=Long.parseLong(respuesta.nextLine());
          		mal=true;
          		
          	}//FormatException
          	catch(NumberFormatException e)
          	{
          		System.err.println("el valor no es un numero");
          		mal=false;
          	}
          	
       	 }while(!mal);
        
    	//mira si el id existe en el map y si existe se guarda en una variable tipo producer
    	  if (producerDb.containsKey(producerId)) 
    	  {
    		  Producer guardar=(Producer) producerDb.get(producerId);
    		  //se anade el producer a la lista de la cancion creada se envia a una funcion de alla
    		  podcastDb.get(podcastDb.size()-1).addProducer(guardar);

          } 
    	  else 
          {
              System.out.println("Productor no encontrado");
          }
    	  
    	  do{
				try
	    		{
					System.out.println("Desea ingresar otro productor?: (S/N)");  
			    	seguir=respuesta.nextLine();
	    			mal=true;
		    		evaluacion(seguir, "R");
	    		}
	    		catch(ExcepcionRespuestaPreguntas e)
	    		{
	    			System.err.println(e.getResp());
	    			mal=false;
	    		}
			}while(!mal);
    	  
    	  
      }while(seguir.equals("S")  || seguir.equals("s"));

      return true;
	}

	private static Boolean addNewUserToDB(List<Song> songDb, List<Podcast> podcastDb, Scanner respuesta, List<PremiumClient> clientPDb, List<StandardClient> clientSDb)
	{

	    String userName;
	    String password;
	    String membresia=" ";
	    String name;
	    String lastName;
	    String patron;
	    Integer age=0;
	    Boolean libre;
	    Boolean correcto;
	    Boolean mal;

		do
		{
			correcto=true;
			
			try
  		    {
				System.out.println("Por favor ingrese si el usuario es premium o estandar: (P/S) " );
    	    	membresia=respuesta.nextLine();
        		mal=true;
        		//U ES PARA MIRAR LO DE LA RES;UESTA DE TIPO DE USUARIO
	    		evaluacion(membresia, "U");

  		    }
  		    catch(ExcepcionRespuestaPreguntas e)
  		    {
	  			System.err.println(e.getResp());
	  			mal=false;
  		    }	
		}while(!mal);
		
	    do
	    {
	    	libre=false;
	    	correcto=false;
	    	System.out.println("Por favor ingrese la informacion del usuario: " );
	    	
	    	
	  
		    System.out.println("Por favor ingrese el nombre de usuario (de 8 a 30 caracteres solo accepta '-' y '_': " );
		    userName=respuesta.nextLine();

		    patron="^([a-zA-Z]+)([a-zA-Z0-9_-]{7,30})$";

		    if(userName.matches(patron))
		    {
		    	correcto=true;
		    	libre=true;
		    }

		    if(correcto)
		    {
		    	//SE EVALUA PARA CADA TIPO DE CLIENTE
		    	for(StandardClient client : clientSDb)
			    {
		    		//evaluar si el usuario ya existe
			    	if(userName.equals(client.getUserName()))
			    	{
			    		libre=false;
			    		System.out.println("El nombre de usuario ya existe, ingrese otro" );
			    		break;
			    	}
			    }
		    	
		    	for(PremiumClient client : clientPDb)
			    {
			    	if(userName.equals(client.getUserName()))
			    	{
			    		libre=false;
			    		System.out.println("El nombre de usuario ya existe, ingrese otro" );
			    		break;
			    	}
			    }
		    }

		    if(libre)
		    {
		    	System.out.println("Por favor ingrese el nombre: " );
	        	name=respuesta.nextLine();
	        	System.out.println("Por favor ingrese el apellido: " );
	        	lastName=respuesta.nextLine();
		    	System.out.println("Por favor ingrese la contrasena: " );
	        	password=respuesta.nextLine();
	        	
	        	do
	        	{
		        	try
		        	{
		        		System.out.println("Por favor ingrese la edad: " );
		        		age=Integer.parseInt(respuesta.nextLine());//convertir
		        		mal=true;
		        	}//FormatException
		        	catch(NumberFormatException e)
		        	{
		        		System.err.println("el valor no es un numero");
		        		mal=false;
		        	}

	        	}while(!mal);

	        	
	  	    	if(membresia.equals("P") || membresia.equals("p") )
	  	    	{
	  	    		//SI LA MEMPRESIA SE ESPECIFICO QUE ERA PREMIUM
	  	    		clientPDb.add(new PremiumClient (userName, password, name, lastName, age));
	  	    		//SE CREAN LLS ARREGLOS DE LAS CANCIONES Y PODCAST Y SE PIDEN POR SEPARADO
	  	    		doWhilePedirPOD(podcastDb, respuesta, clientPDb);
	  	    		doWhilePedirPSG(songDb, respuesta, clientPDb);
	  	    	}
	  	    	else
	  	    	{
	  	    		clientSDb.add(new StandardClient (userName, password, name, lastName, age));
	  	    	    doWhilePedirSSG(songDb, respuesta, clientSDb);
	  	    	}

		    }

	    }while(!libre);


	    return true;
	}
	//PEDIR LAS CANCIONES PARA EL CLIENTE ESTANDAR
	private static void doWhilePedirSSG( List<Song> songDb, Scanner respuesta, List<StandardClient> clientSDb)
	{
		 String seguir;
		 Boolean existe;
		 Long evaluar=0L;
		 Boolean mal;
		 
		do
	    {
    		existe=false;
    		seguir="S";
	    	do
	        {
	        	try
	        	{
	        		System.out.println("Por favor ingrese el id de la cancion: " );
	        		evaluar=Long.parseLong(respuesta.nextLine());
	        		mal=true;
	        	}
	        	catch(NumberFormatException e)
	        	{
	        		System.err.println("el valor no es un numero");
	        		mal=false;
	        	}
	        }while(!mal);

	    	for(int i=0; i<songDb.size(); i++)
	    	{
	    		//MIRA POR TODA LA LISTA
	    		if(evaluar.equals(songDb.get(i).getId()))
	    		{
	    			//si existe el id se  guarda en la lista del estandar
    				clientSDb.get(clientSDb.size()-1).addContentToListSong(evaluar);
    				existe=true;
    				
    				//se anade una descarga a esa cancion
    				songDb.get(i).addDownload();

    				boolean mirar;

    				do
    				{
    					mirar=true;

	    				try
			    		{
	    					System.out.println("Quiere ingresar una nueva cancion?(S/N)" );
			    			seguir=respuesta.nextLine();
    			    		evaluacion(seguir, "R");
			    		}
			    		catch(ExcepcionRespuestaPreguntas e)
			    		{
			    			System.err.println(e.getResp());
			    			mirar=false;
			    		}
	    				
    				}while(!mirar);
	    		}
	    	}

	    		if(!existe)
	    		{
	    			System.err.println("el codigo no existe" );
	    		}

	    	}while(seguir.equals("S")  || seguir.equals("s"));	
	}
	
	//funcion de pedir canciones para cliente premium
	//basicamente es lo mismo que lo de arribs pero con el db de premium
	private static void doWhilePedirPSG( List<Song> songDb, Scanner respuesta, List<PremiumClient> clientSDb)
	{
		 String seguir;
		 Boolean existe;
		 Long evaluar=0L;
		 Boolean mal;

		do
	    {
    		existe=false;
    		seguir="S";

	    	do
	        {
	        	try
	        	{
	        		System.out.println("Por favor ingrese el id de la cancion: " );
	        		evaluar=Long.parseLong(respuesta.nextLine());
	        		mal=true;
	        	}
	        	catch(NumberFormatException e)
	        	{
	        		System.err.println("el valor no es un numero");
	        		mal=false;
	        	}
	        }while(!mal);

    		for(int i=0; i<songDb.size(); i++)
    		{

    			if(evaluar.equals(songDb.get(i).getId()))
    			{
    				clientSDb.get(clientSDb.size()-1).addContentToListSong(evaluar);
    				existe=true;
    				
    				songDb.get(i).addDownload();

    				boolean mirar;

    				do
    				{
    					mirar=true;

	    				try
			    		{
	    					System.out.println("Quiere ingresar una nueva cancion?(S/N)" );
			    			seguir=respuesta.nextLine();
    			    		evaluacion(seguir, "R");
			    		}
			    		catch(ExcepcionRespuestaPreguntas e)
			    		{
			    			System.err.println(e.getResp());
			    			mirar=false;
			    		}
    				}while(!mirar);
    			}
    		}

    		if(!existe)
    		{
    			System.err.println("el codigo no existe" );
    		}

    	}while(seguir.equals("S")  || seguir.equals("s"));	
	}
	
	private static void doWhilePedirPOD(List<Podcast> songDb, Scanner respuesta, List<PremiumClient> clientPDb)
	{
		 String seguir;
		 Boolean existe;
		 Long evaluar=0L;
		 Boolean mal;
		 //se llama songpd, pero es la lista de podcasts
		 //esto solo es para premium
		
		do
	    {
    		existe=false;
    		seguir="S";

	    	do
	        {
	        	try
	        	{
	        		System.out.println("Por favor ingrese el id del podcast: " );
	        		evaluar=Long.parseLong(respuesta.nextLine());
	        		mal=true;
	        	}
	        	catch(NumberFormatException e)
	        	{
	        		System.err.println("el valor no es un numero");
	        		mal=false;
	        	}
	        }while(!mal);

    		for(int i=0; i<songDb.size(); i++)
    		{
    			if(evaluar.equals(songDb.get(i).getId()))
    			{
    				clientPDb.get(clientPDb.size()-1).addContentToListPodcast(evaluar);
    				
    				existe=true;
    				songDb.get(i).addDownload();

    				boolean mirar;

    				do
    				{
	    				try
			    		{
	    					System.out.println("Quiere ingresar un nuevo podcast?(S/N)" );
			    			seguir=respuesta.nextLine();
    			    		evaluacion(seguir, "R");
    			    		mirar=true;
			    		}
			    		catch(ExcepcionRespuestaPreguntas e)
			    		{
			    			System.err.println(e.getResp());
			    			mirar=false;
			    		}
    				}while(!mirar);
    			}
    		}

    		if(!existe)
    		{
    			System.err.println("el codigo no existe" );
    		}
    	}while(seguir.equals("S")  || seguir.equals("s"));	
	}
	
	private static void optionCreaterPlayList(Scanner respuesta, List<StandardClient> clientSDb, List<Song> songDb, List<PremiumClient> clientPDb, List<Podcast> podcastDb)
	{
		String response; 
		boolean existe=false;
		boolean mirar=false;
		
		do 
		{
			//pide el nombre de ususario, ya que es mas facil que saberse el id
			System.out.println("Igrese su nombre de usuario" );
			response=respuesta.nextLine();
			
			//primero mira si existe en standard
			for(int i=0; i<clientSDb.size(); i++)
			{
				if(clientSDb.get(i).getUserName().equals(response))
				{
					askCreaterOnlyPlayList(respuesta, clientSDb, songDb, i);
					existe=true;
				}
				
				
			}
			
			//si no existe mira en este de premium
			for(int i=0; i<clientPDb.size(); i++)
			{
				if(clientPDb.get(i).getUserName().equals(response))
				{
					askCreatePlayLists(podcastDb,respuesta,clientPDb,songDb,i);
					existe=true;
				}
				
			}
			
			if(!existe)
			{
				do
				{
    				try
		    		{
    					System.out.println("no existe ese nombre de usuario. Desea tratar de nuevo?" );
    					response=respuesta.nextLine();
			    		evaluacion(response, "R");
			    		mirar=true;
		    		}
		    		catch(ExcepcionRespuestaPreguntas e)
		    		{
		    			System.err.println(e.getResp());
		    			mirar=false;
		    		}
				}while(!mirar);
				
			}
			
		}while(response.equals("S")  || response.equals("s"));
	}		
		
	private static void askCreaterOnlyPlayList(Scanner respuesta, List<StandardClient> clientSDb, List<Song> songDb, Integer i)
	{
		String response; 
		Long id=0L;
		boolean existe=false;
		boolean mirar=false;
		boolean mal=false;
		
		//se crea la play list solo con poner el nombre
		System.out.println("Ingrese nombre del la playlist" );
		response=respuesta.nextLine();
		//se llama esta funcion de la clase standard
		//se usa i para saber de cual cliente se habla
		clientSDb.get(i).createOnlyPL(response);
		
			do
			{
				
				
			      do
			    	{
			        	try
			        	{
			        		
			        		System.out.println("Ingrese id de las canciones que quiere meter (por ser cliente standard solo puede meter canciones)" );
			        		id = Long.parseLong(respuesta.nextLine());
			        		mal=true;
			        	}//FormatException
			        	catch(NumberFormatException e)
			        	{
			        		System.err.println("el valor no es un numero");
			        		mal=false;
			        	}

			    	}while(!mal);
				
				existe=false;
				
				for(Song song : songDb)
				{
					if(song.getId().equals(id))
					{
						clientSDb.get(i).addContentToPL(song);
						existe=true;	
					}
				}
	
				if(!existe)
				{
					System.out.println("no existe la cancion" );
					do
					{
							try
			    		{
	    					System.out.println("Quiere ingresar una nueva cancion?(S/N)" );
			    			response=respuesta.nextLine();
				    		evaluacion(response, "R");
				    		mirar=true;
			    		}
			    		catch(ExcepcionRespuestaPreguntas e)
			    		{
			    			System.err.println(e.getResp());
			    			mirar=false;
			    		}	
					}while(!mirar);
				}
				else
				{
					do
					{
							try
			    		{
	    					System.out.println("Quiere ingresar una nueva cancion?(S/N)" );
			    			response=respuesta.nextLine();
				    		evaluacion(response, "R");
				    		mirar=true;
			    		}
			    		catch(ExcepcionRespuestaPreguntas e)
			    		{
			    			System.err.println(e.getResp());
			    			mirar=false;
			    		}	
					}while(!mirar);
					
				}
				
			}while(response.equals("S")  || response.equals("s"));
	}
	
	private static void askCreatePlayLists(List<Podcast> podcastDb, Scanner respuesta, List<PremiumClient> clientPDb, List<Song> songDb, Integer i)
	{
		String response="S";
		Long id=0L;
		boolean existe;
		boolean mirar=false;
		boolean mal=false;
		
		do
		{
			
			if(response.equals("S")  || response.equals("s"))
			{
				System.out.println("Ingrese nombre del la playlist" );
				response=respuesta.nextLine();
				//se crea la play list sin saber su tipo
				clientPDb.get(i).createPL(response);
				
				do
				{
					try
		    		{
						System.out.println("La playlist es de canciones o de podcasts? (C/P)");
						response=respuesta.nextLine();
						//opcion par saber si es un podcast
			    		evaluacion(response, "C");
			    		mirar=true;
		    		}
		    		catch(ExcepcionRespuestaPreguntas e)
		    		{
		    			System.err.println(e.getResp());
		    			mirar=false;
		    		}	
				}while(!mirar);
				
				if(response.equals("c")  || response.equals("C"))
				{
					do
					{
						response="n";
						existe=false;
						do
				        {
				        	try
				        	{
				        		System.out.println("Ingrese id de la cancion que quiere meter" );
				        		id = Long.parseLong(respuesta.nextLine());
				        		mal=true;
				        	}
				        	catch(NumberFormatException e)
				        	{
				        		System.err.println("el valor no es un numero");
				        		mal=false;
				        	}
				        }while(!mal);
						
						
						for(Song song : songDb)
						{
							if(song.getId().equals(id))
							{
								clientPDb.get(i).addContentToPL(song);
								existe=true;
							}
						}
						
						if(!existe)
						{
							System.out.println("no existe la cancion." );
						}
						
						do
						{
							try
				    		{
								System.out.println("Desea ingresar otra cancion?" );
								response=respuesta.nextLine();
					    		evaluacion(response, "R");
					    		mirar=true;
				    		}
				    		catch(ExcepcionRespuestaPreguntas e)
				    		{
				    			System.err.println(e.getResp());
				    			mirar=false;
				    		}	
						}while(!mirar);
						
					}while(response.equals("S")  || response.equals("s"));		
				}
				else
				{
					do
					{
						response="n";
						existe=false;
						do
				        {
				        	try
				        	{
				        		System.out.println("Ingrese id del podcast que quiere meter" );
				        		id = Long.parseLong(respuesta.nextLine());
				        		mal=true;
				        	}
				        	catch(NumberFormatException e)
				        	{
				        		System.err.println("el valor no es un numero");
				        		mal=false;
				        	}
				        }while(!mal);
						
						
						
						for(Podcast podcast : podcastDb)
						{
							if(podcast.getId().equals(id))
							{
								clientPDb.get(i).addContentToPL(podcast);
								existe=true;
							}
						}
						
						if(!existe)
						{
							System.out.println("no existe el podcast." );
						}
						
						do
						{
							try
				    		{
								System.out.println("Desea ingresar otro podcast?" );
								response=respuesta.nextLine();
					    		evaluacion(response, "R");
					    		mirar=true;
				    		}
				    		catch(ExcepcionRespuestaPreguntas e)
				    		{
				    			System.err.println(e.getResp());
				    			mirar=false;
				    		}	
						}while(!mirar);
						
					}while(response.equals("S")  || response.equals("s"));	
					
				}
			}
			
			do
			{
				try
	    		{
					System.out.println("Quiere crear otra playlist? (S/N)" );
					response=respuesta.nextLine();
		    		evaluacion(response, "R");
		    		mirar=true;
	    		}
	    		catch(ExcepcionRespuestaPreguntas e)
	    		{
	    			System.err.println(e.getResp());
	    			mirar=false;
	    		}	
			}while(!mirar);
			
		}while(response.equals("S")  || response.equals("s"));
		
	}

	private static void evaluacion (String respuesta, String modalidad) throws ExcepcionRespuestaPreguntas
	{
		if(modalidad.equals("R"))
		{
			if(!(respuesta.equals("S") || respuesta.equals("N") || respuesta.equals("s") || respuesta.equals("n")))
			{
				throw new ExcepcionRespuestaPreguntas ("La respuesta no fue S o N ");
			}
		}
		else if (modalidad.equals("U"))
		{
			if(!(respuesta.equals("P") || respuesta.equals("S") || respuesta.equals("s") || respuesta.equals("p")))
			{
				throw new ExcepcionRespuestaPreguntas ("La respuesta no fue P o S");
			}
		}
		else
		{
			if(!(respuesta.equals("C") || respuesta.equals("P") || respuesta.equals("c") || respuesta.equals("p")))
			{
				throw new ExcepcionRespuestaPreguntas ("La respuesta no fue P o S");
			}
		}
	
	}
	
	private static void listSongsByAlbum(List<Song> songDb, Scanner respuesta)
	{
		ArrayList<Long> listaAparecidos=new ArrayList<>();
		//Long[] listaAparecidos=new Long[1];
		String albumBuscar;
		Boolean encontrado;


		System.out.println("Listado de canciones (si no aparecen es porque el album no existe): " );
		System.out.println("Por favor ingrese el album: " );
		albumBuscar=respuesta.nextLine();

		for(Song song: songDb)
		{
			encontrado=false;

			if(albumBuscar.equals(song.getAlbum()))
			{
				if(listaAparecidos.size()==0)
				{
					System.out.println(song.getName());
					listaAparecidos.add(song.getId());
				}
				else
				{
					for(Long lista: listaAparecidos)
					{
						if(lista==song.getId())
						{
							encontrado=true;
						}
					}

					if(!encontrado)
					{
						System.out.println(song.getName());
						listaAparecidos.add(song.getId());
					}
				}

			}
		}
	}

	public static void listSongsByArtist(List<Song> songDb, Scanner respuesta)
	{
		ArrayList<String> listaAparecidos=new ArrayList<>();
		//String[] listaAparecidos=new String[1];
		String genreBuscar;
		Boolean encontrado;


		System.out.println("Por favor ingrese el genero: " );
		genreBuscar=respuesta.nextLine();
		//que ingrese en la excepcion un nombre sin numeros

		System.out.println("Listado de artistas (si no aparecen es porque el genero no existe): " );

		for(Song song: songDb)
		{
			encontrado=false;

			if(genreBuscar.equals(song.getGenre()))
			{
				if(listaAparecidos.size()==0)
				{
					System.out.println(song.getArtist());
					listaAparecidos.add(song.getArtist());
				}
				else
				{
					for(String lista: listaAparecidos)
					{
						if(lista.equals(song.getArtist()))
						{
							encontrado=true;
						}
					}

					if(!encontrado)	
					{
						System.out.println(song.getArtist());
						listaAparecidos.add(song.getArtist());
					}

				}

			}
		}


	}

	
	public static Boolean addSongToList(List<Song> songDb,List<Podcast> podcastDb, List<StandardClient> clientSDb, List<PremiumClient> clientPDb, Scanner respuesta)
	{
		Boolean retornar;
		Long idBuscar=0L;
		boolean mal=false;
		
		do
		{
			do
	        {
	        	try
	        	{
	        		System.out.println("Por favor ingrese el id del usuario: " );
	        		idBuscar=Long.parseLong(respuesta.nextLine());
	        		mal=true;
	        	}
	        	catch(NumberFormatException e)
	        	{
	        		System.err.println("el valor no es un numero");
	        		mal=false;
	        	}
	        }while(!mal);
			
			
			retornar=addIdStandard(songDb,clientSDb,respuesta,idBuscar);
			
			retornar=addIdPremium(songDb,podcastDb, clientPDb,respuesta,idBuscar);
			
		}while(!retornar);
		
		return true;

	}
	
	public static Boolean addIdPremium(List<Song> songDb,List<Podcast> podcastDb, List<PremiumClient> clientPDb, Scanner respuesta, Long idBuscar)
	{
		Boolean existeCancion=true;
		Boolean existeUsuario=false;
		Boolean mal=false;
			
			for(PremiumClient client : clientPDb)
			{
	
				if(idBuscar.equals(client.getId()))
				{
					existeUsuario=true;
					existeCancion=false;
					do
			        {
			        	try
			        	{
			        		System.out.println("Por favor ingrese el id de la cancion o podcast a descargar: " );
			        		idBuscar=Long.parseLong(respuesta.nextLine());
			        		mal=true;
			        	}
			        	catch(NumberFormatException e)
			        	{
			        		System.err.println("el valor no es un numero");
			        		mal=false;
			        	}
			        }while(!mal);
					
	
					for(Song song: songDb)
					{
						if(idBuscar==song.getId())
						{
							existeCancion=true;
							song.addDownload();
							client.addContentToListSong(idBuscar);
							System.out.println("se descargo correctamente la cancion" );
							return true;
						}
					}
					
					for(Podcast podcast: podcastDb)
					{
						if(idBuscar.equals(podcast.getId()))
						{
							existeCancion=true;
							podcast.addDownload();
	
							client.addContentToListPodcast(idBuscar);
							
							System.out.println("se descargo correctamente la cancion" );
							return true;
						}
					}
				}
	
			}
	
			if(!existeCancion)
			{
				System.err.println("La cancion a descargar no existe" );
				return false;
			}
		
		return false;
	}
	
	
	public static Boolean addIdStandard(List<Song> songDb, List<StandardClient> clientSDb,Scanner respuesta, Long idBuscar)
	{
		Boolean existeCancion=true;
		Boolean existeUsuario=false;
		Boolean mal=false;
		
			for(StandardClient client : clientSDb)
			{
				if(idBuscar==client.getId())
				{
					existeUsuario=true;
					existeCancion=false;
					do
			        {
			        	try
			        	{
			        		System.out.println("Por favor ingrese el id de la cancion a descargar: " );
			        		idBuscar=Long.parseLong(respuesta.nextLine());
			        		mal=true;
			        	}
			        	catch(NumberFormatException e)
			        	{
			        		System.err.println("el valor no es un numero");
			        		mal=false;
			        	}
			        }while(!mal);
					
	
	
					for(Song song: songDb)
					{
						if(idBuscar==song.getId())
						{
							existeCancion=true;
							song.addDownload();
	
							client.addContentToListSong(idBuscar);
							
							System.out.println("se descargo correctamente la cancion" );
							return true;
						}
					}
				}
	
			}
			
			if(!existeCancion)
			{
				System.err.println("La cancion a descargar no existe" );
				return false;
			}
		
		return false; 
	}

	public static void lookForSongs(List<Song> songDb,Scanner respuesta)
	{
		//REVISAR EL PROGRAMA, EL PORQUE SOLO SE IMPRIME UNA VEZ UNA CANCION Y NO LAS DOS
		String clave=" ";
		String response="A";
		ArrayList<Integer> resp=new ArrayList<>();
		//Integer[] resp= new Integer[1];
		ArrayList<Long> posicion=new ArrayList<>();
		//Long[] posicion= new Long[1];
		boolean correcto;

		do
		{
			correcto=true;
			try
	  		{
					System.out.println("Ingrese la palabra clave:" );
					clave=respuesta.nextLine();
		    		evaluacion2(clave);
	  		}
	  		catch(EspacioPalabraClave a)
	  		{
	  			System.err.println(a.getResp());
	  			correcto=false;
	  		}
		}while(!correcto);

		//excepcion de si ingresa mas de una palabras
		//si ingresa un punto coma espacio guion etc
		do
		{
			correcto=true;
			try
			{
				System.out.println("¿Desea buscar con base al album?: (s/n)" );
				response=respuesta.nextLine();
				evaluacion(response, "R");

			}
			catch(ExcepcionRespuestaPreguntas e)
			{
  			System.err.println(e.getResp());
  			correcto=false;
			}
		}while(!correcto);

		if(response.equals("s") || response.equals("S"))
		{
			resp.add(1);
		}

		do
		{
			correcto=true;
			try
  		{
				System.out.println("¿Desea buscar con base al nombre del artista?: (s/n)" );
				response=respuesta.nextLine();
				evaluacion(response, "R");

  		}
  		catch(ExcepcionRespuestaPreguntas e)
  		{
  			System.err.println(e.getResp());
  			correcto=false;
  		}

		}while(!correcto);

		if(response.equals("s") || response.equals("S"))
		{
			resp.add(2);
		}

		do
		{
			correcto=true;
			try
  		{
				System.out.println("¿Desea buscar con base al nombre de la cancion?: (s/n)" );
				response=respuesta.nextLine();
				evaluacion(response, "R");

  		}
  		catch(ExcepcionRespuestaPreguntas e)
  		{
  			System.err.println(e.getResp());
  			correcto=false;
  		}
		}while(!correcto);


		if(response.equals("s") || response.equals("S"))
		{
			resp.add(3);
		}

		do
		{
			correcto=true;
			try
  		{
				System.out.println("¿Desea buscar con base al genero?: (s/n)" );
				response=respuesta.nextLine();
	    		evaluacion(response, "R");

  		}
  		catch(ExcepcionRespuestaPreguntas e)
  		{
  			System.err.println(e.getResp());
  			correcto=false;
  		}
		}while(!correcto);

		if(response.equals("s") || response.equals("S"))
		{
			resp.add(4);
		}

		boolean encontrado;
		System.out.println("Lista de canciones que se vinculan con la palabra clave" );

		for(Integer respu :resp)
		{
			switch(respu)
			{
				case 1: //nombre del album
					for(Song song: songDb)
					{
						encontrado=false;

						if(clave.equals(song.getAlbum()))
						{
						   for(Long posi: posicion)
						   {
							 if(song.getId()==posi)
							 {
								 encontrado=true;
							 }
						   }

						   if(!encontrado)
						   {
							   System.out.println(song.getName());
							   posicion.add(song.getId());
						   }
						}
					}
					break;
				case 2: //nombre del artista
					for(Song song: songDb)
					{
						encontrado=false;

						if(clave.equals(song.getArtist()))
						{
							for(Long posi: posicion)
						   {
								encontrado=false;

								 if(song.getId()==posi)
								 {
									 encontrado=true;

								 }
							}

							if(!encontrado)
							   {
								   System.out.println(song.getName());

								   posicion.add(song.getId());
							   }

						 }

					}
					break;
				case 3: //nombre de la cancion
					for(Song song: songDb)
					{
						encontrado=false;

						if(clave.equals(song.getName()))
						{
							for(Long posi: posicion)
							{
								encontrado=false;

								 if(song.getId()==posi)
								 {
									 encontrado=true;

								 }

							}

							if(!encontrado)
						   {
							   System.out.println(song.getName());

							   posicion.add(song.getId());
						   }

						}
					}
					break;
				case 4: //genero
					for(Song song: songDb)
					{
						encontrado=false;
						if(clave.equals(song.getGenre()))
						{
							for(Long posi: posicion)
						   {
								encontrado=false;

								 if(song.getId()==posi)
								 {
									 encontrado=true;

								 }

						   }

							if(!encontrado)
							{
							   System.out.println(song.getName());

							   posicion.add(song.getId());
							}
						}
					}
					break;
			}
		}
	}


	private static void evaluacion2 (String respuesta) throws EspacioPalabraClave
	{
		if(respuesta.indexOf(' ')>-1)
		{
		throw new EspacioPalabraClave ("hay mas de una palabra en la palabra clave \""+respuesta+"\"");
		}
	}
	
	private static void eliminarIds(List<PremiumClient> clientPDb, List<StandardClient> clientSDb, Scanner respuesta)
	{
		boolean mal=false;
		Long idBuscar=0L;
		
		do
        {
        	try
        	{
        		System.out.println("Ingrese su id de usuario" );
        		idBuscar=Long.parseLong(respuesta.nextLine());
        		mal=true;
        	}
        	catch(NumberFormatException e)
        	{
        		System.err.println("el valor no es un numero");
        		mal=false;
        	}
        }while(!mal);
		
		
		Long idBorrar=0L;
		boolean encontrado =false; 
		
		do
		{
			do
	        {
	        	try
	        	{
	        		System.out.println("Ingrese el id a borrar" );
	        		idBorrar=Long.parseLong(respuesta.nextLine());	
	        		mal=true;
	        	}
	        	catch(NumberFormatException e)
	        	{
	        		System.err.println("el valor no es un numero");
	        		mal=false;
	        	}
	        }while(!mal);
			
			
			for(StandardClient buscado: clientSDb)
			{
				if(buscado.getId().equals(idBuscar))
				{
					if(buscado.borrarIdsSong(idBorrar))
					{
						encontrado =true; 
					}
					break;
				}
			}
			
			for(PremiumClient buscado: clientPDb)
			{
				if(buscado.getId().equals(idBuscar))
				{
					
					if(buscado.borrarIdsPodcast(idBorrar))
					{
						encontrado =true; 
					}
					
					if(buscado.borrarIdsSong(idBorrar))
					{
						encontrado =true; 
					}
					break;
				}
			}
			
			if(!encontrado)
			{
				System.out.println("Id no existe en su contenido" );
			}
		}while(!encontrado);

	}
	
	private static void addContentToPlayList(List<PremiumClient> clientPDb, List<StandardClient> clientSDb, Scanner respuesta,List<Song> songDb,List<Podcast> podcastDb)
	{
		String responseUsuario="n"; 
		String responseNombre="n"; 
		String responseId="n"; 
		boolean existeNombreUsuario=false;
		boolean existeNombrePL=false;
		boolean existeId=false;
		boolean relacionCoherente=false;
		Long id=0L;
		boolean mirar=false;
		boolean coherente=false;
		boolean mal=false;
		
		do 
		{
			System.out.println("Igrese su nombre de usuario" );
			responseUsuario=respuesta.nextLine();
			
			for(StandardClient client : clientSDb)
			{
				if(client.getUserName().equals(responseUsuario))
				{
					do
					{
						do
				        {
				        	try
				        	{
				        		System.out.println("Ingrese id de la cancion" );
				        		id= Long.parseLong(respuesta.nextLine());
				        		mal=true;
				        	}
				        	catch(NumberFormatException e)
				        	{
				        		System.err.println("el valor no es un numero");
				        		mal=false;
				        	}
				        }while(!mal);
						
						existeId=false;
					
						for(Song song : songDb)
						{
							if(song.getId().equals(id))
							{
								//esto se manda a la funcion de anadir contenido a la pl pero para standard
								client.addContentToPL(song);
								existeId=true;
							}
						}
						
						if(!existeId)
						{
							do
							{
								try
					    		{
									System.out.println("no existe ese id. Desea tratar de nuevo?" );
									responseUsuario=respuesta.nextLine();
						    		evaluacion(responseUsuario, "R");
						    		mirar=true;
					    		}
					    		catch(ExcepcionRespuestaPreguntas e)
					    		{
					    			System.err.println(e.getResp());
					    			mirar=false;
					    		}	
							}while(!mirar);
						}
						
					}while(responseId.equals("S")  || responseId.equals("s"));	
					
					existeNombreUsuario=true;
				}	
			}
			
			for(PremiumClient client : clientPDb)
			{
				if(client.getUserName().equals(responseUsuario))
				{
					
					do
					{
						System.out.println("Ingrese nombre del la playlist" );
						responseNombre=respuesta.nextLine();	
						existeId=false;
						
							for(PlayList pl: client.getPlayLists())
							{
								if(pl.getName().equals(responseNombre))
								{
									do
							        {
							        	try
							        	{
							        		System.out.println("Ingrese id del podcast o cancion" );
							        		id= Long.parseLong(respuesta.nextLine());
							        		mal=true;
							        	}
							        	catch(NumberFormatException e)
							        	{
							        		System.err.println("el valor no es un numero");
							        		mal=false;
							        	}
							        }while(!mal);
									
									
									for(Podcast podcast : podcastDb)
									{
										if(podcast.getId().equals(id))
										{
											//esto se manda a la funcion de anadir contenido a la pl pero para standard
											coherente=client.addContentToPLWhen(podcast,responseNombre);
											existeId=true;
										}
									}
									
		
									for(Song song : songDb)
									{
										if(song.getId().equals(id))
										{
											//esto se manda a la funcion de anadir contenido a la pl pero para standard
											coherente=client.addContentToPLWhen(song,responseNombre);
											existeId=true;
										}
									}
									
									
									if(!existeId)
									{
										do
										{
											try
								    		{
												System.out.println("no existe ese id. Desea tratar de nuevo?" );
												responseUsuario=respuesta.nextLine();
									    		evaluacion(responseUsuario, "R");
									    		mirar=true;
								    		}
								    		catch(ExcepcionRespuestaPreguntas e)
								    		{
								    			System.err.println(e.getResp());
								    			mirar=false;
								    		}	
										}while(!mirar);
									}
									

									if(!coherente)
									{
										do
										{
											try
								    		{
												System.out.println("este ingreso es inconsistente. no pertenece al tipo de clase. Desea tratar de nuevo?" );
												responseUsuario=respuesta.nextLine();
									    		evaluacion(responseUsuario, "R");
									    		mirar=true;
								    		}
								    		catch(ExcepcionRespuestaPreguntas e)
								    		{
								    			System.err.println(e.getResp());
								    			mirar=false;
								    		}	
										}while(!mirar);
									}
									
									existeNombrePL=true;
								}
							}
							
							if(!existeNombrePL)
							{
								do
								{
									try
						    		{
										System.out.println("no existe ese nombre de pl. Desea tratar de nuevo?" );
										responseUsuario=respuesta.nextLine();
							    		evaluacion(responseUsuario, "R");
							    		mirar=true;
						    		}
						    		catch(ExcepcionRespuestaPreguntas e)
						    		{
						    			System.err.println(e.getResp());
						    			mirar=false;
						    		}	
								}while(!mirar);
							}
							
					}while(responseNombre.equals("S")  || responseNombre.equals("s"));	
					
					
					
					
					existeNombreUsuario=true;
				}	
			}	
		
			if(!existeNombreUsuario)
			{
				do
				{
					try
		    		{
						System.out.println("no existe ese nombre de usuario. Desea tratar de nuevo?" );
						responseUsuario=respuesta.nextLine();
			    		evaluacion(responseUsuario, "R");
			    		mirar=true;
		    		}
		    		catch(ExcepcionRespuestaPreguntas e)
		    		{
		    			System.err.println(e.getResp());
		    			mirar=false;
		    		}	
				}while(!mirar);
			}
			
		}while(responseUsuario.equals("S")||responseUsuario.equals("s"));
	}
	
	private static void showCredits(Scanner respuesta,List<Song> songDb,List<Podcast> podcastDb)
	{
		String responseId="n";
		Long id=0L;
		Boolean existeId=false;
		Boolean mirar=false;
		Boolean mal=false;
		
		do
		{
			do
	        {
	        	try
	        	{
	        		System.out.println("Ingrese id de la cancion o podcast" );
	        		id= Long.parseLong(respuesta.nextLine());
	        		mal=true;
	        	}
	        	catch(NumberFormatException e)
	        	{
	        		System.err.println("el valor no es un numero");
	        		mal=false;
	        	}
	        }while(!mal);
			
			
			
			for(Podcast podcast : podcastDb)
			{
				if(podcast.getId().equals(id))
				{
					//esto se manda a la funcion de anadir contenido a la pl pero para standard
				   System.out.println(podcast.showCredits());
					existeId=true;
				}
			}
			

			for(Song song : songDb)
			{
				if(song.getId().equals(id))
				{
					//esto se manda a la funcion de anadir contenido a la pl pero para standard
					System.out.println(song.showCredits());
					existeId=true;
				}
			}
			
			if(!existeId)
			{
				do
				{
					try
		    		{
						System.out.println("no existe ese id. Desea tratar de nuevo?" );
						responseId=respuesta.nextLine();
			    		evaluacion(responseId, "R");
			    		mirar=true;
		    		}
		    		catch(ExcepcionRespuestaPreguntas e)
		    		{
		    			System.err.println(e.getResp());
		    			mirar=false;
		    		}	
				}while(!mirar);
			}
			
		}while(responseId.equals("S")|| responseId.equals("s"));
		
	}
	
	private static void printProducersMap(Map<Long,Producer> producerDb)
	{
		Producer reserva;
		for (Map.Entry<Long,Producer> entry : producerDb.entrySet())
		{
			//se guarda el valor en una variable tipo producer para despues invocar el nombre de dicho objeto (solo se imprimira eso)
			reserva=entry.getValue();
			
		    System.out.println("key: " + entry.getKey() + "; value: " + reserva.getName());
		   
		    for(Content prod: reserva.getContentList())
		    {
		    	System.out.println("Nombre de la cancion o podcast: " + prod.getName());	
		    }
		}
		
	}
	
	private static void reestablishId (List<PremiumClient> clientPDb, List<StandardClient> clientSDb,List<Song> songDb,List<Podcast> podcastDb, Map<Long,Producer> producerDb)
	{
		Content.setNextId((long)(songDb.size()+podcastDb.size()));
		
		Client.setNextId((long)(clientPDb.size()+clientSDb.size()));
		
		Producer.setNextId((long)(producerDb.size()));
		
	}
	
	private static void playContent(List<Song> songDb,List<Podcast> podcastDb,Scanner respuesta)
	{
		String resp=" ";
		Boolean existeNombre=false;
		Boolean mirar=false;
		
		
		do
		{
			System.out.println("Ingrese el nombre de la cancion o podcast que desea escuchar: ");
			resp=respuesta.nextLine();
			
			for(Podcast podcast : podcastDb)
			{
				if(podcast.getName().equals(resp))
				{
					//esto se manda a la funcion de anadir contenido a la pl pero para standard
				   System.out.println(podcast.play());
				   existeNombre=true;
				}
			}
			

			for(Song song : songDb)
			{
				if(song.getName().equals(resp))
				{
					//esto se manda a la funcion de anadir contenido a la pl pero para standard
					System.out.println(song.play());
					existeNombre=true;
				}
			}
			
			if(!existeNombre)
			{
				do
				{
					try
		    		{
						System.out.println("no existe ese nombre. Desea tratar de nuevo?" );
						resp=respuesta.nextLine();
			    		evaluacion(resp, "R");
			    		mirar=true;
		    		}
		    		catch(ExcepcionRespuestaPreguntas e)
		    		{
		    			System.err.println(e.getResp());
		    			mirar=false;
		    		}	
				}while(!mirar);
			}
			
			
		}while(resp.equals("S")|| resp.equals("s"));	
		
	}
	
	
}		
