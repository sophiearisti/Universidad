package Model;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControllerServicios implements Initializable
{
	  @FXML
	    private Button addAdicionales;

	    @FXML
	    private Button addPiscina;

	    @FXML
	    private Button addSeg;

	    @FXML
	    private TextField cantidadAdicionales;

	    @FXML
	    private TextField cantidadPiscina;

	    @FXML
	    private TextField cantidadSeguridad;

	    @FXML
	    private Text totPagar;

	    @FXML
	    private Text totServicios;
	    
	    @FXML
	    private Text CostoPiscina;

	    @FXML
	    private Text CostoSeg;

	    @FXML
	    private Text CostosAdicionales;
	    
	    @FXML
	    private Button siguiente;
	    
	    private static Integer cantInquilinos;
	    
		public static int idPropiedad;
		
		public static void idPropiedad(Integer id)
		{
			idPropiedad=id;
		}
		
		public static void getCantInquilinos(Integer cant) 
		{
				cantInquilinos=cant;
		}
		
		
	private Float costosSeguridad=0f;
	private static int cantSeguridad;
	
	private Float costosPiscina=0f;
	private  static int cantPiscina;
	
	private Float costosAdd=0f;
	private  static int cantAdd;
	
	private static Float costosRentaPropiedad=0f;
	
	private static Float totServiciosGuardar=0f;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		
		String CostosSERVICIOS="select Servicio as serv, Costo_Servicio as costo "
				+ "from Tipo_Servicio";
		
		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
				PreparedStatement ps=conex.prepareStatement(CostosSERVICIOS))
		{
			ResultSet rs= ps.executeQuery();
			
			while(rs.next())
			{
				if(rs.getString("serv").equals("accidentes"))
				{
					
					CostoSeg.setText(Float.toString(rs.getFloat("costo")));
					costosSeguridad=rs.getFloat("costo");
					
				}
				else if(rs.getString("serv").equals("piscina"))
				{
					
					CostoPiscina.setText(Float.toString(rs.getFloat("costo")));
					costosPiscina=rs.getFloat("costo");
					
				}
				else
				{
					
					CostosAdicionales.setText(Float.toString(rs.getFloat("costo")));
					costosAdd=rs.getFloat("costo");
					
				}
					
			}
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		String Renta="select c.RENTA as rent "
				+ "from CARACTERISTICAS c join propiedades p on p.ID_CARACTERISTICAS=c.IDCARACTERISTICAS "
				+ "where p.ID_PROPIEDADES=?";
		
		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
				PreparedStatement ps=conex.prepareStatement(Renta))
		{
			ps.setInt(1, idPropiedad);
			
			ResultSet rs= ps.executeQuery();
			
			rs.next();
			
			costosRentaPropiedad=rs.getFloat("rent");
			
				
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String impuestos="select PORCENTAJE "
				+ "from IMPUESTOS";
		
		
		float sumatoriaimpuestos=0f;
////		
		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
				PreparedStatement ps=conex.prepareStatement(impuestos))
		{
			ResultSet rs= ps.executeQuery();
			
			while(rs.next())
			{
				sumatoriaimpuestos=sumatoriaimpuestos+rs.getFloat("PORCENTAJE");
			}
				
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
////		
		String comisiones="select PORCENTAJE "
				+ "from comisiones";
		
		float sumatoriacomisiones=0f;
		
		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
				PreparedStatement ps=conex.prepareStatement(comisiones))
		{
			ResultSet rs= ps.executeQuery();
			
			while(rs.next())
			{
				sumatoriacomisiones=sumatoriaimpuestos+rs.getFloat("PORCENTAJE");
			}
			
				
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		costosRentaPropiedad=costosRentaPropiedad+(costosRentaPropiedad*sumatoriacomisiones)+(costosRentaPropiedad*sumatoriaimpuestos);
		
		cantidadSeguridad.setText(Integer.toString(cantSeguridad));
		
		cantidadPiscina.setText(Integer.toString(cantPiscina));
		
		cantidadAdicionales.setText(Integer.toString(cantAdd));
		
		totServicios.setText(Float.toString(totServiciosGuardar));
		
		totPagar.setText(Float.toString(totServiciosGuardar+costosRentaPropiedad));
		
	}
	
	@FXML
	private Text ERROR;
	
	
	public void AdicionarSeguridad (ActionEvent event)
	{
		ERROR.setVisible(false);
		
		
		AdicionarSeguridad2 ();	
	}
	
	private Boolean AdicionarSeguridad2 ()
	{
		Boolean EstaEnINT=true;
		
		if(cantidadSeguridad.getText().length()!=0)
		{
			try 
			{
				cantSeguridad= Integer.parseInt(cantidadSeguridad.getText());
			    
			} 
			catch (Exception e) 
			{
				ERROR.setText("La cantidad de inquilinos no es un entero");
				ERROR.setVisible(true);	
				EstaEnINT=false;
				return false;
			}
			
		}
		
		if(cantSeguridad<=cantInquilinos)
		{
			totPagar.setText(Float.toString(costosRentaPropiedad+cantAdd*costosAdd+cantPiscina*costosPiscina+cantSeguridad*costosSeguridad));
			totServicios.setText(Float.toString(cantAdd*costosAdd+cantPiscina*costosPiscina+cantSeguridad*costosSeguridad));
		}
		else
		{
			ERROR.setText("La cantidad de inquilinos es mayor a la cantidad de inquilinos que habia ingresado");
			ERROR.setVisible(true);	
			return false;
			
		}
		
		return true;
		
	}
	
	public void AdicionarPiscina (ActionEvent event)
	{
		ERROR.setVisible(false);
		
		AdicionarPiscina2();
		
	}
	
	private Boolean AdicionarPiscina2 ()
	{
		Boolean EstaEnINT=true;
		
		if(cantidadPiscina.getText().length()!=0)
		{
			try 
			{
				cantPiscina= Integer.parseInt(cantidadPiscina.getText());
			    
			} 
			catch (Exception e) 
			{
				ERROR.setText("La cantidad de inquilinos no es un entero");
				ERROR.setVisible(true);	
				EstaEnINT=false;
				return false;
			}
			
		}
		
		if(cantPiscina<=cantInquilinos)
		{
			totPagar.setText(Float.toString(costosRentaPropiedad+cantAdd*costosAdd+cantPiscina*costosPiscina+cantSeguridad*costosSeguridad));
			totServicios.setText(Float.toString(cantAdd*costosAdd+cantPiscina*costosPiscina+cantSeguridad*costosSeguridad));
		}
		else
		{
			ERROR.setText("La cantidad de inquilinos es mayor a la cantidad de inquilinos que habia ingresado");
			ERROR.setVisible(true);	
			return false;
			
		}
		
		return true;
		
		
	}
	
	
	public void AdicionarAdicionales (ActionEvent event)
	{
		ERROR.setVisible(false);
		
		AdicionarAdicionales2 ();
		
	}
	
	private Boolean AdicionarAdicionales2 ()
	{
		Boolean EstaEnINT=true;
		
		if(cantidadAdicionales.getText().length()!=0)
		{
			try 
			{
				cantAdd= Integer.parseInt(cantidadAdicionales.getText());
			    
			} 
			catch (Exception e) 
			{
				ERROR.setText("La cantidad de inquilinos no es un entero");
				ERROR.setVisible(true);	
				EstaEnINT=false;
				return false;
			}
			
		}
		
		if(cantAdd<=cantInquilinos)
		{
			totPagar.setText(Float.toString(costosRentaPropiedad+cantAdd*costosAdd+cantPiscina*costosPiscina+cantSeguridad*costosSeguridad));
			totServicios.setText(Float.toString(cantAdd*costosAdd+cantPiscina*costosPiscina+cantSeguridad*costosSeguridad));
			
		}
		else
		{
			ERROR.setText("La cantidad de inquilinos es mayor a la cantidad de inquilinos que habia ingresado");
			ERROR.setVisible(true);	
			return false;
			
		}
		
		return true;
		
	}
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
		
	public void devolver(ActionEvent event) throws IOException
	{
		root= FXMLLoader.load(getClass().getResource("/Interfaces/cantinquilinos.fxml"));
		stage= (Stage)((Node)event.getSource()).getScene().getWindow();
		scene= new Scene (root);
		stage.setScene(scene);
		stage.show();			
	}  
	
	ArrayList<Integer> CantidadXservicio=new ArrayList<>();
	
	public void SiguienteServicios (ActionEvent event)  throws IOException
	{
		
		ERROR.setVisible(false);

		
		if(AdicionarAdicionales2() && AdicionarPiscina2() && AdicionarSeguridad2())
		{
			ControllerMetodos.RentaTot(Float.parseFloat(totPagar.getText()));
			
			CantidadXservicio.add(cantSeguridad);
			CantidadXservicio.add(cantPiscina);
			CantidadXservicio.add(cantAdd);
			
			totServiciosGuardar= Float.parseFloat(totServicios.getText());
		
			ControllerFinalizarPago.todoServicios(CantidadXservicio);
			ControllerFinalizarPago.RentaTot(costosRentaPropiedad+cantAdd*costosAdd+cantPiscina*costosPiscina+cantSeguridad*costosSeguridad);
			
			root= FXMLLoader.load(getClass().getResource("/Interfaces/MetodosDePago.fxml"));
			stage= (Stage)((Node)event.getSource()).getScene().getWindow();
			scene= new Scene (root);
			stage.setScene(scene);
			stage.show();	
			
		}
		
	}

}
