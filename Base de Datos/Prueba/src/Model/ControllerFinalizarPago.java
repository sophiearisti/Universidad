//VER LO DE LOS COSTOS


package Model;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControllerFinalizarPago 
{
	private static int idClientes;
	
	private static int idPropiedades;
	
	private static Map <Integer,Float> Bonos=null;
	
	private static Tarjeta tarjeta=null;
	
	private static String correo;
	
	private static Map <Integer,String> inquilinosList;
	
	private static ArrayList<Integer> CantidadXservicio;
	
	public static void todoIDS(Integer idCliente, Integer idPropiedad)
	{
		idClientes=idCliente;
		idPropiedades=idPropiedad;
	}
	
	public static void todoBonos(Map <Integer,Float> Bono)
	{
		Bonos=Bono;
	}
	
	public static void todoTarjeta(Tarjeta t)
	{
		tarjeta=t;
	}
	
	public static void todoCorreo(String email)
	{
		correo=email;
	}
	
	public static void todoInquilinos(Map <Integer,String>inquilinos)
	{
		inquilinosList=inquilinos;
	}
	
	public static void todoServicios( ArrayList<Integer> servicios)
	{
		CantidadXservicio=servicios;
	}
	
	private static ArrayList<Integer> ordenPago= new ArrayList<>();
	
	public static void ListPagos(ArrayList<Integer> ordenDePago)
	{
		 ordenPago=ordenDePago;
	}
	
	private static Float RentaTot;
	
	public static void RentaTot(Float tot)
	{
		RentaTot=tot;
	}
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	

    @FXML
    private Button atras;

    @FXML
    private Button menu;
    
    @FXML
    private Button contrato;
    
    @FXML
    private Button pagar;
    
    @FXML
    private Text ERROR;

	public void GuardarTodoEnLaBDD(ActionEvent event)  throws IOException
	{
		
		if(tarjeta!=null)
		{
			Boolean existe=false;
			
			String ConsultaExistencia= "select * "
					+ "from  tarjeta "
					+"where Numero_Tarjeta=?";
			
			try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
					PreparedStatement ps=conex.prepareStatement(ConsultaExistencia))
			{
				
				ps.setInt(1, tarjeta.getNumeroTarjeta());
				
				ResultSet rs=ps.executeQuery();
				
				existe=rs.next();
				
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			
			if(!existe)
			{
				String ConsultaIngresoT = "INSERT INTO tarjeta (NUMERO_TARJETA,TIPO_TARJETA,NOMBRE_POSEEDOR,AÑO_VENCIMIENTO) "
					+"VALUES (?,?,?,?) ";
			
				try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
						PreparedStatement ps=conex.prepareStatement(ConsultaIngresoT)){
					
					ps.setInt(1, tarjeta.getNumeroTarjeta());
					
					ps.setString(2,tarjeta.getTipoDeTarjeta());
					
					ps.setString(3, tarjeta.getNombrePoseedor());
					
					String[] arregloFecha=tarjeta.getVencimiento().split("-");
					
					ps.setInt(4, Integer.parseInt(arregloFecha[0]));
					
					ps.executeQuery();
					
					
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				} 
			}
			
			
			
			String Pago="INSERT INTO pago (ID_PAGO,CORREO,TOTAL,ID_CUENTA,NUMERO_TARJETA) VALUES (null,?,?,?,?)";
//			
			try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
					PreparedStatement ps=conex.prepareStatement(Pago))
			{
				System.out.println(correo);
				System.out.println(RentaTot);
				System.out.println(idClientes);
				System.out.println(tarjeta.getNumeroTarjeta());
				
				ps.setString(1, correo);
				ps.setFloat(2, RentaTot);
				ps.setInt(3, idClientes);
				ps.setInt(4, tarjeta.getNumeroTarjeta());
				ps.executeQuery();
				
			}
			catch (SQLException e)
			{
					e.printStackTrace();
			}
			
		}
		else
		{
			
			String Pago="INSERT INTO pago (ID_PAGO,CORREO,TOTAL,ID_CUENTA,NUMERO_TARJETA) VALUES (null,?,?,?,null)";
//			
			try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
					PreparedStatement ps=conex.prepareStatement(Pago))
			{
				
				ps.setString(1, correo);
				ps.setFloat(2, RentaTot);
				ps.setInt(3, idClientes);
				
				ps.executeQuery();
				
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			
		}
		
		int IDsPago=0;
		
		String idpago="select MAX(ID_PAGO) as pago "
				+ "from pago ";
		
		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
				PreparedStatement ps=conex.prepareStatement(idpago))
		{
			
			ResultSet rs= ps.executeQuery();
			
			rs.next();
			
			IDsPago=rs.getInt("pago");
		}
		catch (SQLException e)
		{
				e.printStackTrace();
		}
		
		if(Bonos!=null)
		{
		
			for (Map.Entry<Integer, Float> bonosList : Bonos.entrySet())
			{
				 String ConsultaIngresoB = " INSERT INTO bono  (NUMERO_BONO, ID_PAGO, cantidad) "+
							"VALUES (?,?,?) ";
				 
					try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
							PreparedStatement ps=conex.prepareStatement(ConsultaIngresoB))
					{
					
						ps.setInt(1, bonosList.getKey());
						ps.setInt(2, IDsPago);
						ps.setFloat(3, bonosList.getValue());
						
						ps.executeQuery();
						
					
					}
					catch (SQLException e)
					{
							e.printStackTrace();
					}
	
			}
		}
		
		
			
			String servicios="select ID_TipoServicio as id "
					+ "from Tipo_Servicio";
			
			try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
					PreparedStatement ps=conex.prepareStatement(servicios))
			{
				ResultSet rs= ps.executeQuery();
				
				int contador=0;
				
				String  ServicioCliente= "INSERT INTO servicio  (ID_Servicio, Cant_inquilinos, id_TipoServicio, ID_PROPIEDAD) "+
							"VALUES (null,?,?,?) ";
				
				while(rs.next())
				{
					
					try(Connection conex2 = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
							PreparedStatement ps2=conex2.prepareStatement(ServicioCliente))
					{
					
						ps2.setInt(1, CantidadXservicio.get(contador));
						ps2.setInt(2, rs.getInt("id"));
						ps2.setInt(3,idPropiedades );
						
						ps2.executeQuery();
						
						contador++;
						
					
					}	
					catch (SQLException e)
					{
							e.printStackTrace();
					}
				}
					
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			} 
			
//			
			String comisiones="select ID_Comision as id "
					+ "from comisiones";
			
			try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
					PreparedStatement ps=conex.prepareStatement(comisiones))
			{
				ResultSet rs= ps.executeQuery();
				
				String  comisionesXPago= "INSERT INTO pagoxComisiones (ID_Comision, ID_Pago) "+
						"VALUES (?,?) ";
						
				while(rs.next())
				{
					try(Connection conex2 = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
							PreparedStatement ps2=conex2.prepareStatement(comisionesXPago))
					{
						ps2.setInt(1, rs.getInt("id"));
						ps2.setInt(2, IDsPago);
						
						ps2.executeQuery();
						
					}
					catch (SQLException e)
					{
						e.printStackTrace();
					} 
					
				}
				
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
//			
//			
			String impuestos="select ID_Impuesto as id "
					+ "from Impuestos";
			
			try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
					PreparedStatement ps=conex.prepareStatement(impuestos))
			{
				ResultSet rs= ps.executeQuery();
				
				String  impuestosXPago= "INSERT INTO PagoXImpuestos (ID_Impuesto, ID_Pago) "+
						"VALUES (?,?) ";
				
				while(rs.next())
				{
					
					try(Connection conex2 = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
							PreparedStatement ps2=conex2.prepareStatement(impuestosXPago))
					{
						ps2.setInt(1, rs.getInt("id"));
						ps2.setInt(2, IDsPago);
						
						ps2.executeQuery();
					}
					catch (SQLException e)
					{
						e.printStackTrace();
					} 
					
				}
				
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			} 
//			
//			
			String tipoPago="select ID_Tipo as id "
					+ "from Tipo_Pago";
			
			try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
					PreparedStatement ps=conex.prepareStatement(tipoPago))
			{
				ResultSet rs= ps.executeQuery();
				
				String  tipoPagoXPago= "INSERT INTO PagoXtipo_pago (ID_tipo, ID_Pago) "+
						"VALUES (?,?) ";
				
				while(rs.next())
				{
					try(Connection conex2 = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
							PreparedStatement ps2=conex2.prepareStatement(tipoPagoXPago))
					{
						
						ps2.setInt(1, rs.getInt("id"));
						ps2.setInt(2, IDsPago);
						
						ps2.executeQuery();
						
					}
					catch (SQLException e)
					{
						e.printStackTrace();
					} 
					
				}
				
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			} 

			String unUpdateCONTRIGGER="UPDATE PROPIEDADES "
					+ "SET ESTADO=2 "
						+ "WHERE ID_PROPIEDADES=?";
			
			try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,
					Constantes.USERNAME,Constantes.PASSWORD);
					PreparedStatement ps=conex.prepareStatement(unUpdateCONTRIGGER))
			{
				ps.setInt(1,idPropiedades);
				ps.executeQuery();
				
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			
			String InsertInquilinos="insert into INQUILINOS (ID_INQUILINOS,NOMBRE,ID_PROPIEDADES) values (?,?,?);";	
			
			for (Map.Entry<Integer, String> inquilinos : inquilinosList.entrySet())
			{
				try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,
						Constantes.USERNAME,Constantes.PASSWORD);
						PreparedStatement ps=conex.prepareStatement(InsertInquilinos))
				{
					
						ps.setInt(1,inquilinos.getKey());
						ps.setString(2,inquilinos.getValue());
						ps.setInt(3,idPropiedades);
						ps.executeQuery();
					
				}
				catch (SQLException e) 
				{
					e.printStackTrace();
				}	
			}
			
			
		atras.setDisable(true);
		
		menu.setDisable(false);
		
		contrato.setDisable(false);
		
		pagar.setDisable(true);
	}
	
	public void Contrato(ActionEvent event)  throws IOException, URISyntaxException
	{
		
		//mostrar el pdf
		Desktop.getDesktop().browse(new URI("https://drive.google.com/file/d/1ljiaFK7-F1tUELULyy_HhAfhyr73Tecv/view?usp=share_link"));
		
	}
	
	public void Atras(ActionEvent event)  throws IOException
	{
		int devolver=0;
		
		for(int i=0; i<ordenPago.size(); i++)
		{
			devolver=ordenPago.get(i);
		}
		
		switch(devolver)
		{
			case 1:
				root= FXMLLoader.load(getClass().getResource("/Interfaces/Bonos.fxml"));
				stage= (Stage)((Node)event.getSource()).getScene().getWindow();
				scene= new Scene (root);
				stage.setScene(scene);
				stage.show();
			break;
			
			case 2:
				root= FXMLLoader.load(getClass().getResource("/Interfaces/Tarjeta.fxml"));
				stage= (Stage)((Node)event.getSource()).getScene().getWindow();
				scene= new Scene (root);
				stage.setScene(scene);
				stage.show();
			break;
			
			case 3:
				root= FXMLLoader.load(getClass().getResource("/Interfaces/Efectivo.fxml"));
				stage= (Stage)((Node)event.getSource()).getScene().getWindow();
				scene= new Scene (root);
				stage.setScene(scene);
				stage.show();
			break;
		}
		
	}
	
	public void Menu(ActionEvent event)  throws IOException
	{
		
		root= FXMLLoader.load(getClass().getResource("/Interfaces/Cliente.fxml"));
		stage= (Stage)((Node)event.getSource()).getScene().getWindow();
		scene= new Scene (root);
		stage.setScene(scene);
		stage.show();
		
	}

}
