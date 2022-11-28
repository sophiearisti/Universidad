import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

//int QR = (flags & 0b10000000) >>> 7;
//int opCode = ( flags & 0b01111000) >>> 3;
//int AA = ( flags & 0b00000100) >>> 2;
//int TC = ( flags & 0b00000010) >>> 1;
//int RD = flags & 0b00000001;

public class Servidor {

private int puerto;
private DatagramSocket socketUDP;

private boolean salir;

//crea el servidor
Servidor(int p){
	this.puerto=p;
	this.salir=false;
	iniciarSocket();
}

public void iniciarSocket()
{
//crea el socket
	
    System.out.println("Iniciando el servidor UDP");
    try {
		this.socketUDP = new DatagramSocket(this.puerto);
	} catch (SocketException e) {
		e.printStackTrace();
	}
}
 
public void terminarSocket(){
	System.out.println("Terminando el servidor UDP");
    this.socketUDP.close();
}

public void escucharDNS(){	
 //el puerto siempre este escuchando   
	byte respuesta[]= new byte[1024];
	
    try 
    {
	    while (true && !this.salir) 
	    {
	    	//crea un paquete tipo datagrama llamado datagramaRecibido
	    	
        	DatagramPacket datagramaRecibido = new DatagramPacket(respuesta, respuesta.length);
           
	        this.obtenerMensajeDNS(datagramaRecibido);
	    }
    }
	 catch (IOException e) 
    {
		e.printStackTrace();
	}
}

public void obtenerMensajeDNS(DatagramPacket datagramaRecibido) throws IOException
{
	 short QTYPE;
	 ArrayList <String> QNAME; 
	 short QCLASS;
	 Short ID;
	 
	 
	do
	{	
		//solo se leen los datagramas con tipologia A
		this.socketUDP.receive(datagramaRecibido);
		
	    //se crea un flujo de datos para ir leyendo los datos
	    //se obtiene la parte de los dato del datagrama y se crea de este un arreglo de bytes
		DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(datagramaRecibido.getData()));
		
	    System.out.println("\n\nSe inicia la decodificacion");
	    
	    //Se lee el ID del query DNS
	    //cada parte es de tamaño short
	    ID= dataInputStream.readShort();
	    System.out.println("\n\n ID:"+ ID+ " "+Integer.toBinaryString(ID));
	    
	    //guarda la info de todos los elementos del flag
	    short flags = dataInputStream.readShort();
	    System.out.println("\n\n Flags (QR=0 1B (ES UN QUERY),\n OPCODE=0000 4B (ES UN STANDARD QUERY),\n AA=0 1B (SE USA EN LA RESPUESTA, ES CERO),\n TC=0 1B (0, MENSAJE NO TRUNCADO),\n RD=1 1B ( ES REQUERIDA),\n RA=0 1B (RECURSION NO DISPONIBLE),\n Z=000 3B (SIEMPRE CERO),\n RCODE=0000 (TIPO 0)):\n"+ flags);
	    
	    System.out.println("\n\nFLAGS:"+ flags+ " "+Integer.toBinaryString(flags));
	    
	    //guarda  QDCOUNT este debe ser
	    Short QDCOUNT = dataInputStream.readShort(); 
	    System.out.println("\n\nQDCOUNT (1):"+ QDCOUNT+ " "+Integer.toBinaryString(QDCOUNT));
	    
	    //guarda  ANCOUNT este debe ser 0, se enfatiza en la respuesta
	    Short ANCOUNT = dataInputStream.readShort();
	    System.out.println("\n\nANCOUNT (0):"+ ANCOUNT+ " "+Integer.toBinaryString(ANCOUNT));
	    
	    //guarda  NSCOUNT este debe ser 0, se enfatiza en la respuesta
	    Short NSCOUNT = dataInputStream.readShort();
	    System.out.println("\n\nNSCOUNT (0):"+ NSCOUNT+ " "+Integer.toBinaryString(NSCOUNT));
	    
	    //guarda  NSCOUNT este debe ser 0, se enfatiza en la respuesta
	    Short ARCOUNT = dataInputStream.readShort();
	    System.out.println("\n\nARCOUNT (0):"+ ARCOUNT+ " "+Integer.toBinaryString(ARCOUNT));
	     
	    
	    //6medium3com0
	    //Arreglo en el que se va a guardar el nombre del dominio
	    QNAME = new ArrayList<String>();
	    
	    //el nombre de dominio se guarda de esta manera por ejemplo 6medium3com0
	    int recLen;
	    //while termina cuando el tamaño especificado sea 0
	    
	    while ((recLen = dataInputStream.readByte()) > 0) 
	    	//termina en cero el nombre de dominio
	       {
	      System.out.println("\nrecLen: "+ recLen);
	   	//se crea un arreglo para guardar cada caracter que es 1B de tamaño
	         byte[] record = new byte[recLen];
	        
	        //se guarda cada B
	         for (int i = 0; i < recLen; i++) 
	         {
	            record[i] = dataInputStream.readByte();
	         }
	        
	        //Lo obtenido se convierte a string con base al tipo UTF8
	        //Los primeros 128 puntos de código Unicode se codifican como 1 byte en UTF-8. 
	        //PRECISAMENTE QNAME ES un list para guardar cada parte de la consulta
	         QNAME.add(new String(record, StandardCharsets.UTF_8));
	    }
	    
//	    if(QNAME.get(0).equals("Computadorcito")) {
//	    	System.out.println("Se ha recibido un mensaje para computadorcito");
//	    }
	  
	    System.out.println("\n\nimprimir nombre de dominio\n");
	    for (int i=0; i<QNAME.size(); i++) 
	    { 
	    	System.out.println(QNAME.get(i)+ "\n");
	    }
	    	 
	    QTYPE = dataInputStream.readShort();
	    System.out.println("\n\nQTYPE (1):"+ QTYPE);
	    
	    
	    QCLASS = dataInputStream.readShort();
	    System.out.println("\n\nCLASS (1):"+ QCLASS);
	 } while(QTYPE!=1);
    
	
    //funcion en la que se mande el arreglo del nombre de dominio que se busca para buscarlo en el archivo
     
    byte[] IPDefDNS = BuscarDominio(datagramaRecibido,QNAME);
    
    Integer puerto=datagramaRecibido.getPort();
    
    System.out.println("\nPUERTO: "+puerto);
    
    //SE MANDA LA SECCION DE PREGUNTAS Y LA DIRECCION IP de lo que se busca y la ip del cliente
    //la funcion para empaquetar todo y mandarlo
    if(IPDefDNS!=null)
    {
      RespuestaDNS(ID, QNAME, QTYPE, QCLASS, IPDefDNS, datagramaRecibido.getAddress(), puerto);
    } 
}

public byte[] BuscarDominio(DatagramPacket datagramaRecibido, ArrayList <String> QNAME) throws IOException
{
	Boolean encontrado=true;
	byte respuesta[]= new byte[1024];
	byte buffer[]= new byte[1024];
	int puertoDNSForaneo=53;
	byte IPDefDNS[]=null;
	Integer port=datagramaRecibido.getPort();
	
	System.out.println("\nEntro a buscar dominio");
	
	if(QNAME.size()==3)
	{
		IPDefDNS=LeerArchivo(QNAME, port);
		
		if(IPDefDNS==null)
		{
			encontrado=false;
		}
		else
		{
			return IPDefDNS;
		}
	}	
	else
	{
		encontrado=false;
	}
		
	if(!encontrado)
	{
		System.out.println("\nENTRO");
		
		//se declara y define la direccion ip del dns de la javeriana
		
		byte IPDefDNS1[] = {10,2,1,10};
		System.out.println("\n1");
        InetAddress ipDNS= InetAddress.getByAddress(IPDefDNS1);
        System.out.println("\n1");
        //se obtiene el contenido del mensaje de la trama que es el query del cliente original
		buffer= datagramaRecibido.getData();
		System.out.println("\n2");
		//mandar la informacion al otro DNS
		DatagramPacket peticion = new DatagramPacket(buffer, buffer.length, ipDNS, puertoDNSForaneo);
		this.socketUDP.send(peticion);
		System.out.println("\n3");
		
		//se obtiene la informacion del DNS externo
		DatagramPacket datagramaDNSExterno = new DatagramPacket(respuesta, respuesta.length);
		System.out.println("\n4");
		this.socketUDP.receive(datagramaDNSExterno);
        System.out.println("\n4");
        //se obtiene la respuesta del DNS EXTERNO
        buffer= datagramaDNSExterno.getData();
        System.out.println("\n5");
        
        //se obtiene la direccion ip y puerto del cliente 
        InetAddress ipCliente = datagramaRecibido.getAddress();
        Integer puerto=datagramaRecibido.getPort();
        System.out.println("\nPUERTO de la query: "+ puerto);
        System.out.println("\nIP CLIENTE PARA ENVIAR SOS: "+ puerto);
        
        //se manda la respuesta al cliente
        Responder(buffer, ipCliente, puerto); 
	}
	
	return IPDefDNS;
}

public byte[] LeerArchivo(ArrayList <String> QNAME, Integer port) throws IOException
{
	System.out.println("\nEntro a leer archivo");
	
	//se llama crea el archivo
	 File file = new File("C:\\Users\\Estudiante\\Documents\\MasterFile.txt"); 
	//Creo un objeto para leer el archivo
	FileReader fr = new FileReader(file);
	// Utilizo el bufferedReader para leer el archivo
	BufferedReader br = new BufferedReader(fr);
	
	
	// Se usa para leer linea por lineal el archivo
	String line = "";
	
	//delimitadores
	String espacio=" ";
	
	//Arreglo para que en cada posicion se guarde la particion de la linea
	String[] ArregloEspacios;
	
	byte IPDefDNS1[]= {0,0,0,0};
	
	// Ciclo para que se lea el archivo mientras haya mas lineas por leer
	while ((line = br.readLine()) != null) 
	{
		System.out.println("\n Linea leida"+line);
		
		// "split" -> Retorna el arreglo con line dividida por el delimiter 
		ArregloEspacios= line.split(espacio);

		//se compara la primera palabra para ver si es $ORIGIN 
		if(ArregloEspacios[0].equals("$ORIGIN"))
		{	
			System.out.println("\nArregloEspacios[1]: "+ArregloEspacios[1]);
			
			String direccion=ArregloEspacios[1];
			
			System.out.println("\n "+direccion);
			
			Pattern pattern = Pattern.compile("_");

	        String[] ArregloPuntos= pattern.split(direccion);
			
			System.out.println("\nArreglos _ (0): "+ArregloPuntos[0]+ "\n");
			System.out.println("\nQNAME (1)"+QNAME.get(1)+ "\n");
			System.out.println("\nArreglos _ (1): "+ArregloPuntos[1]+ "\n");
			System.out.println("\nQNAME (2)"+QNAME.get(2)+ "\n");
			
			//compara el nombre# y el com
			//nombre1_com_
			//pc1_nombre1_com_
			if(ArregloPuntos[0].equals(QNAME.get(1)) && ArregloPuntos[1].equals(QNAME.get(2)))
			{
				do
				{
				  //compara hasta terminal el soa
				  line = br.readLine();
				  System.out.println("\n linea despues de encontrar el origen compatible: "+line);
				  
				}while(!(line.equals(")")));
				
				do
				{
					//compara seccion de nombres de dominio
					  line = br.readLine();
					  
					  System.out.println("\n evaluacion nombre pcx:"+line);
					  
					  ArregloEspacios= line.split(espacio);
					  
					  System.out.println("\nArregloEspacios[0] nombre pcx: "+ArregloEspacios[0]);
					  System.out.println("\nQNAME.get(0) nombre pcx:"+QNAME.get(0));
					  
					     //pc1 A 172_16_0_10
						//pc1 nombre1 com_
					  if(ArregloEspacios[0].equals(QNAME.get(0)))
					  {
						  direccion=ArregloEspacios[2];
							
						  System.out.println("\n Impresion direccion IP: "+direccion);
					
					      ArregloPuntos= pattern.split(direccion);
						
					      
						  for(int i=0; i<4; i++)
						  {
							  int ARREGLOS=Integer.parseInt(ArregloPuntos[i]);
							  
							  //una IP son 4 B
							  IPDefDNS1[i]=(byte)ARREGLOS;
							  
							  System.out.println("\nIP "+(int)IPDefDNS1[i]);
							  
						  }
						  
						  br.close();
						  
						  System.out.println("retornar la ip");
						  
						  return IPDefDNS1;
					  }
					  
				} while(!(ArregloEspacios[0].equals("$ORIGIN")));
				br.close();
				return null;
			}
		}
	}
	br.close();
	return null;
}
public void RespuestaDNS(short IDr, ArrayList <String> QNAME, short QTYPE, short QCLASS,  byte[] ipBuscado, InetAddress ipCliente, Integer port) throws IOException
{
	System.out.println("Entro a respuesta");
	
	//prearacion de la cabecera
	
	//se crea un id aleatorio
	short ID = IDr;
	System.out.println("\nID "+ID);

	//se crea la parte de flags predeterminados 1000000110000000
	//QR=1 1B (es una respuesta)
	//OPCODE=0000 4B (ES UN STANDARD QUERY)
	//AA=0 1B (no es de tipo authoritative)
	//TC=0 1B (0, MENSAJE NO TRUNCADO)
	//RD=1 1B (ES REQUERIDA
	
	//RA=0 1B (RECURSION NO DISPONIBLE, el servidor no hace recursion) REVISAR
	
	//Z=000 3B (SIEMPRE CERO)
	//RCODE=0000 (TIPO 0)
	
	short requestFlags1 = (short) 33024;

	//short requestFlags = Short.parseShort("0000000100000000", 2);
	//ByteBuffer flagsByteBuffer = ByteBuffer.allocate(2).putShort(requestFlags);
	//byte[] flagsByteArray = flagsByteBuffer.array();
	
	
	//SE MANDO SOLO UNA PREGUNTA
	short QDCOUNT = 1;
	
	//solo se manda una respuesta
	short ANCOUNT = 1;
	
	short NSCOUNT = 0;
	short ARCOUNT = 0;

	//crear mensaje a enviar
	//se crea un flujo de datos para guardar de tipo BYTE
	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
	
	//se guarda la cabecera
	dataOutputStream.writeShort(ID);
	dataOutputStream.writeShort(requestFlags1);
	dataOutputStream.writeShort(QDCOUNT);
	dataOutputStream.writeShort(ANCOUNT);
	dataOutputStream.writeShort(NSCOUNT);
	dataOutputStream.writeShort(ARCOUNT);
	
	//3pc17nombre13com0
	//SE GUARDA EL QUERY=[pc1,nombre,com]
	//el tamaño y las letras
	System.out.println("\nQNAME "+QNAME.size());
	
	for (int i = 0; i < QNAME.size(); i++) 
	{
		System.out.println("\nQNAME "+QNAME.get(i));
	    byte[] QueryBytes = QNAME.get(i).getBytes(StandardCharsets.UTF_8);
	    
	    String tamano=QNAME.get(i);
	    
	    Integer tamanostring=tamano.length();
	    System.out.println("\ntamaño string "+tamanostring);
	    
	    dataOutputStream.writeByte(tamanostring);
	    dataOutputStream.write(QueryBytes);
	}
	
	dataOutputStream.writeByte(0);// no more parts
	
	dataOutputStream.writeShort(QTYPE);
	dataOutputStream.writeShort(QCLASS);
	
	Short TYPE=1; //ES TIPO A
	Short CLASS=1; //ES CLASE IN
	Short TTL=1035; //a 32 bit signed integer that specifies the time interval
    //that the resource record may be cached before the source
    //of the information should again be consulted. 
	Short RDLENGTH=4; //an unsigned 16 bit integer that specifies the length in
    // octets of the RDATA field.
	//ES 4 PORQUE ES DIRECCION IP
	
	//ES 
	short NAME = (short) 49164;
	//aqui se pone la respuesta
	//1100000000001100
	
	dataOutputStream.writeShort(NAME);
	dataOutputStream.writeShort(TYPE);
	dataOutputStream.writeShort(CLASS);
	dataOutputStream.writeShort(0);
	dataOutputStream.writeShort(TTL);
	dataOutputStream.writeShort(RDLENGTH);
	
	//la ip tiene 4 B
	//es un arreglo de 4 que es la ip encontrada
	//ir escribiendo la ip parte por parte
	
	for(int i=0; i<4; i++)
	{
		System.out.println("\nIP "+(int)ipBuscado[i]);
		dataOutputStream.writeByte(ipBuscado[i]);
	}
	
	byte[] dnsFrame = byteArrayOutputStream.toByteArray();

	for (int i = 0; i < dnsFrame.length; i++) 
	{
	    System.out.print(String.format("%s", dnsFrame[i] + " "));
	   // Integer.toBinaryString(
	}
	
	//se envia todo a la funcion responder
	Responder(dnsFrame, ipCliente,port);
}

public void Responder(byte buffer[], InetAddress ip, Integer port) throws IOException 
{
	DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length, ip, port);
	this.socketUDP.send(respuesta);

	
	//EL SOCKET NO NOS DEJA FUNCIONAR
	//terminarSocket();
}

}

