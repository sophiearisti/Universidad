package Model;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControllerCantInquilinos implements Initializable
{
	public static int idCliente;
    
    public static int idPropiedad;
    
    private static Map <Integer,String> InquilinosIDs=new HashMap<Integer,String>(); 
	
	public static void idClientePropiedad(Integer id, Integer propiedad)
	{
		idCliente=id;
		
		idPropiedad=propiedad;
		
		InquilinosIDs.clear();
	}
	
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
    private Button botonCant;
	@FXML
    private TextField Cantidad;
	
	@FXML
    private Text error;
	
	@FXML
    private Text pregunta;
	
	@FXML
    private Button botonpregunta;
	
	@FXML
    private Button botonpregunta2;

    @FXML
    private Rectangle cuadropregunta;
    
    @FXML
    private TextField ids;
    
    @FXML
    private Button botonids;
    
    @FXML
    private Text numero;

    @FXML
    private Button siguiente;
    
    @FXML
    private Text enunciadoIDs;
    
    @FXML
    private TextField nombres;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{	
    	if(!InquilinosIDs.isEmpty())
		{
    		error.setText("Usted ya habia ingresado todos los inquilinos, si quiere volver a empezar incicie digitando la cantidad de bonos que desea ingresar");
			error.setVisible(true);
			siguiente.setVisible(true);
		}
	}
	
    	
    
	public void devolverInfoPropiedad(ActionEvent event) throws IOException 
	{
		root = FXMLLoader.load(getClass().getResource("/Interfaces/InfoPropiedadArrendarpt1.fxml"));
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	private int cant;
	
	public void listoCantidad(ActionEvent event) throws IOException 
	{
		String cantidad=Cantidad.getText();
		
		Boolean mal=false;
		
		error.setVisible(false);
		botonpregunta.setVisible(false);
		cuadropregunta.setVisible(false);
		pregunta.setVisible(false);
		
		if(cantidad.length()==0)
		{
			error.setText("debe poner un numero");
		    error.setVisible(true);
		}
		else if(Integer.parseInt(cantidad)==0)
		{
			error.setText("No puede poner 0");
		    error.setVisible(true);
			
		}
		else
		{
			try 
			{
				cant= Integer.parseInt(cantidad);
			    
			} 
			catch (Exception e) 
			{
				error.setText("Lo que ingreso no es un numero");
				error.setVisible(true);	
				mal=true;
			}
			
			if(!mal)
			{
				botonpregunta.setVisible(true);
				cuadropregunta.setVisible(true);
				pregunta.setText("¿Está seguro que ese el numero de inquilinos?");
				pregunta.setVisible(true);
			}
		}
		
	}
	
	private int contador=0;
	
	public void SiPregunta1(ActionEvent event) throws IOException 
	{
		InquilinosIDs.clear();
		error.setVisible(false);
		Cantidad.setDisable(true);
		botonCant.setDisable(true);
		ids.setVisible(true);
		nombres.setVisible(true);
		enunciadoIDs.setVisible(true);
		botonids.setVisible(true);
		numero.setVisible(true);
		botonpregunta.setVisible(false);
		cuadropregunta.setVisible(false);
		pregunta.setVisible(false);
		numero.setText(Integer.toString(contador+1));
		
	}
	
	
	public void ListoIds(ActionEvent event) throws IOException 
	{
		cuadropregunta.setVisible(false);
		
		error.setVisible(false);
		
		
		if(contador<cant)
		{
			if(ids.getText().length()!=0 && nombres.getText().length()!=0)
			{
				Boolean correcto=true;
				
				try 
				{
				  if(Integer.parseInt(ids.getText())==0)
				  {
					  correcto=false;
					  error.setText("La cantidad de inquilinos debe ser minimo 1");
					  error.setVisible(true);
					  
				  }
				  
				    
				} 
				catch (Exception e)
				{
					error.setText("El valor del monto no es un numero");
					error.setVisible(true);
					correcto=false;
				}
				
				if(correcto)
				{
					Boolean existe2=false;
					
					System.out.println(InquilinosIDs.containsKey(Integer.parseInt(ids.getText())));
					
					if(InquilinosIDs.containsKey(Integer.parseInt(ids.getText())))
					{
						error.setVisible(true);
						error.setText("Ya ingreso a ese inquilino");
						existe2=true;
					}
					else
					{
						
						String ConsultaRegistro= "select ID_inquilinos as inquilinos "
								+ "from INQUILINOS "
								+ "where ID_inquilinos=? ";
						
						try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
								PreparedStatement ps=conex.prepareStatement(ConsultaRegistro))
						{
							ps.setInt(1, Integer.parseInt(ids.getText()));
							
							ResultSet rs= ps.executeQuery();
							
							Boolean existe=rs.next();
							
							if(!existe && !existe2)
							{
								
								InquilinosIDs.put(Integer.parseInt(ids.getText()),nombres.getText());
							
								
								botonpregunta2.setVisible(true);
								cuadropregunta.setVisible(true);
								pregunta.setVisible(true);
								pregunta.setText("¿Está seguro que ese el numero del id del inquilino?");
								
							}
							else
							{
								error.setText("El inquilino ya hace parte de otra propiedad, no puede ser anadido");
								error.setVisible(true);
							}
								
						}
						catch (SQLException e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
				}
				
			}
			else
			{
				error.setText("No puede dejar campos vacios");
				error.setVisible(true);
				
			}
			
		}
		else
		{
			ids.setDisable(true);
			nombres.setDisable(true);
			botonids.setDisable(true);
			error.setVisible(true);
			siguiente.setVisible(true);
			error.setText("No puede ingresar mas inquilinos");
			ids.clear();
			nombres.clear();
		}
	}
	
	public void SiPregunta2(ActionEvent event) throws IOException 
	{ 
		
		botonpregunta2.setVisible(false);
		cuadropregunta.setVisible(false);
		pregunta.setVisible(false);
		
		contador++;
		
		if(contador<cant)
		{
			numero.setText(Integer.toString(contador+1));
		}
		
		
		ids.clear();
		nombres.clear();
		
	}
	
	public void siguiente(ActionEvent event) throws IOException 
	{
		ControllerServicios.getCantInquilinos(cant);
		ControllerServicios.idPropiedad(idPropiedad);
		ControllerFinalizarPago.todoInquilinos(InquilinosIDs);
		ControllerFinalizarPago.todoIDS(idCliente, idPropiedad);
		root = FXMLLoader.load(getClass().getResource("/Interfaces/ServiciosAdicionales.fxml"));
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
