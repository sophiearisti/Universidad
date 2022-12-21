package Model;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControllerBonos implements Initializable
{
	private static Float RentaTot;
	
	public static void RentaTot(Float tot)
	{
		RentaTot=tot;
	}
	
	private static Float montoTotalAbonado=0f;
	
	
	private static ArrayList<Integer> ordenPago= new ArrayList<>();
	
	public static void ListPagos(ArrayList<Integer> ordenDePago)
	{
		 ordenPago=ordenDePago;
	}
	
	@FXML
	private Text ERROR;
	    
	@FXML
	private Text totPagar;
	
	@FXML
	private Text abonado;
	
	private Float montoAbonado=0f;
	
	private static Map <Integer, Float> Bonos=new HashMap <Integer, Float>();  
	
	private static int cant=0;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		
		totPagar.setText(Float.toString(RentaTot));	
		
		abonado.setText(Float.toString(montoTotalAbonado));	
		
		montoAbonado=montoTotalAbonado;
		
		if(!Bonos.isEmpty())
		{
			numeroBonos.setText(Integer.toString(cant));
			
			if(Bonos.size()==cant)
			{
				ERROR.setText("Usted ya habia ingresado todos los bonos, si quiere volver a empezar incicie digitando la cantidad de bonos que desea ingresar");
				ERROR.setVisible(true);
			}
			else
			{
				ERROR.setText("Usted no habia terminado de ingresar todos los bonos. Si desea volver a empezar, empiece ingresando la cantidad de bonos ");
				ERROR.setVisible(true);
				valor.setDisable(false);
				codigo.setDisable(false);
				aceptarBono.setDisable(false);	
			}
		}
		
		
	}
	
	@FXML
    private TextField codigo;

    @FXML
    private TextField numeroBonos;
    
    @FXML
    private TextField valor;
    
    @FXML
    private Button aceptarBono;
    
	public void AceptarCantidad(ActionEvent event)  throws IOException
	{
		ERROR.setVisible(false);
		
		Boolean error=false;
		
		Bonos.clear();
		
		montoAbonado=montoTotalAbonado;
		
		try 
		{
		  Integer.parseInt(numeroBonos.getText());  
		} 
		catch (Exception e)
		{
			ERROR.setText("El valor no es un numero");
			ERROR.setVisible(true);
			error=true;
		}
		
		if(!error)
		{	
			
			valor.setDisable(false);
			codigo.setDisable(false);
			aceptarBono.setDisable(false);	
			cant=Integer.parseInt(numeroBonos.getText());
			
		}
		
	}
	
	@FXML
    void Reset(MouseEvent event) 
	{
		valor.setDisable(true);
		valor.clear();;
		codigo.setDisable(true);
		codigo.clear();
		aceptarBono.setDisable(true);	
		Bonos.clear();
		
		ERROR.setText("Cada vez que cambie la cantidad de bonos, debera volver a digitarlos todos");
		ERROR.setVisible(true);
		montoTotalAbonado=0f;
    }
	
	private static int contador=0;
	
	@FXML
	private Text numero;

	
	public void AnadirBono(ActionEvent event)  throws IOException
	{
		ERROR.setVisible(false);
		
		Boolean error=false;
		
		if(contador<cant)
		{
			try 
			{
			  Integer.parseInt(codigo.getText());
			    
			} 
			catch (Exception e)
			{
				ERROR.setText("El valor del codigo no es un numero");
				ERROR.setVisible(true);
				error=true;
			}
			
			try 
			{
			  Integer.parseInt(valor.getText());
			    
			} 
			catch (Exception e)
			{
				ERROR.setText("El valor del monto no es un numero");
				ERROR.setVisible(true);
				error=true;
			}
			
			if(!error)
			{
				
				
				//ver si ya lo ingreso
				if(Bonos.containsKey(Integer.parseInt(codigo.getText())))
				{
					ERROR.setText("El codigo del bono ya fue ingresado");
					ERROR.setVisible(true);
				}
				else
				{
					//ver si el bono ya existe en la base de datos
					
					String ConsultaRegistro= "select numero_bono as codigo "
							+ "from bono "
							+ "where numero_bono=? ";
					
					try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
							PreparedStatement ps=conex.prepareStatement(ConsultaRegistro))
					{
						ps.setInt(1, Integer.parseInt(codigo.getText()));
						
						ResultSet rs= ps.executeQuery();
						
						Boolean existe=rs.next();
						
						if(!existe)
						{
							
							Bonos.put(Integer.parseInt(codigo.getText()),Float.parseFloat(valor.getText()));
							
							montoAbonado+=Float.parseFloat(valor.getText());
							
							
							
							abonado.setText(Float.toString(montoAbonado));
							
							montoTotalAbonado=montoAbonado;
					
							contador++;
							numero.setText(Integer.toString(contador+1));
							
							codigo.clear();
							valor.clear();	
							
						}
						else
						{
							ERROR.setText("Ese bono ya fue usado anteriormente en otra compra");
							ERROR.setVisible(true);
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
			ERROR.setText("ya ingreso todos los bonos");
			ERROR.setVisible(true);
			valor.setDisable(true);
			codigo.setDisable(true);
			aceptarBono.setDisable(true);
			codigo.clear();
			valor.clear();	
			
			
		}
		
    }
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private Button atras;
	 
	    
	public void SiguienteBonos(ActionEvent event)  throws IOException
	{
		
		ERROR.setVisible(false);
		
		
		
		if(codigo.getText().length()==0 && valor.getText().length()==0)
		{
			ControllerFinalizarPago.todoBonos(Bonos);
			
			if(ordenPago.size()>1)
			{
				
				if(ordenPago.get(1)==2)
				{
					ControllerTarjeta.ListPagos(ordenPago);
					ControllerTarjeta.RentaTot(RentaTot);
					ControllerTarjeta.Montos(montoTotalAbonado);
					root= FXMLLoader.load(getClass().getResource("/Interfaces/Tarjeta.fxml"));
					stage= (Stage)((Node)event.getSource()).getScene().getWindow();
					scene= new Scene (root);
					stage.setScene(scene);
					stage.show();	
				}
				else
				{
					ControllerEfectivo.ListPagos(ordenPago);
					ControllerEfectivo.RentaTot(RentaTot);
					ControllerEfectivo.Montos(montoTotalAbonado);
					root= FXMLLoader.load(getClass().getResource("/Interfaces/Efectivo.fxml"));
					stage= (Stage)((Node)event.getSource()).getScene().getWindow();
					scene= new Scene (root);
					stage.setScene(scene);
					stage.show();	
				}
				
			}
			else
			{
				if(montoAbonado.equals(RentaTot))
				{
					
					root= FXMLLoader.load(getClass().getResource("/Interfaces/FinalizarPago.fxml"));
					stage= (Stage)((Node)event.getSource()).getScene().getWindow();
					scene= new Scene (root);
					stage.setScene(scene);
					stage.show();	
				}
				else
				{
					ERROR.setText("El valor abonado no equivale al valor total que debe pagar.");
					ERROR.setVisible(true);
				}
				
			}
			
		}
		else
		{
			ERROR.setText("Debe hacer click en el boton aceptar antes de ir al siguiente paso. Si ese bono no lo quiere ingresar, borrelo.");
			ERROR.setVisible(true);
		}
		
	}
	
	public void atrasBonos(ActionEvent event) throws IOException
	{
		root= FXMLLoader.load(getClass().getResource("/Interfaces/MetodosDePago.fxml"));
		stage= (Stage)((Node)event.getSource()).getScene().getWindow();
		scene= new Scene (root);
		stage.setScene(scene);
		stage.show();			
	} 

}
