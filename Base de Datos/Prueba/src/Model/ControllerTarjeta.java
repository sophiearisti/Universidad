package Model;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControllerTarjeta implements Initializable
{
	private static Float RentaTot;
	
	public static void RentaTot(Float tot)
	{
		RentaTot=tot;
	}
	
	private static ArrayList<Integer> ordenPago= new ArrayList<>();
	
	public static void ListPagos(ArrayList<Integer> ordenDePago)
	{
		 ordenPago=ordenDePago;
	}
	
	private static Float montoTotalAbonados=0f;
	
	public static void Montos(Float montoTotalAbonado)
	{
		montoTotalAbonados=montoTotalAbonado;
	}
	
	@FXML
	private Text ERROR;
    
	@FXML
	private Text totPagar;
	
	private static Tarjeta tarjeta;
	
	private static Integer numeroTarjeta=0;
	private static String nombrePoseedor=" ";
	private static String fechaVencimiento;
	private static Float ValorAPagar=0f;
	
	private Float montoAbonado=0f;
	
	@FXML
    private TextField nombre;

    @FXML
    private TextField numero;

    @FXML
    private TextField valor;
	
    @FXML
    private DatePicker fecha;
    
    @FXML
	private Text abonado;
    
	private String[] tipotarjeta = {"visa", "mastercard"};
	 
	@FXML
	 private ComboBox<String> tipoTarjeta;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		tipoTarjeta.getItems().addAll(tipotarjeta);
		
		totPagar.setText(Float.toString(RentaTot));
		nombre.setText(nombrePoseedor);
		numero.setText(Integer.toString(numeroTarjeta));
		valor.setText(Float.toString(ValorAPagar));
		
		montoAbonado=montoTotalAbonados;
		
		abonado.setText(Float.toString(montoAbonado));	
	
		if(!nombre.getText().equals(" "))
		{
			
			ERROR.setText("Usted ya Habia ingresado toda la informacion de la tarjeta, para ir al siguiente paso primero debe ingresar nuevamente la fecha de vencimiento");
			ERROR.setVisible(true);
			
		}
	
	}
	
    public void Evaluar(ActionEvent event)  throws IOException
	{
    	evaluarTarjeta();
	}
    
    private Boolean evaluarTarjeta()
    {
    	montoAbonado=montoTotalAbonados;
    	
    	Boolean error=false;
    	
		 if(nombre.getText().length()!=0 && numero.getText().length()!=0 && valor.getText().length()!=0 && tipoTarjeta.getValue()!=null && fecha.getValue().toString().length()!=0)
		 {
			 
			 	try 
				{
				  Integer.parseInt(numero.getText());
				    
				} 
				catch (Exception e)
				{
					ERROR.setText("El numero de la tarjeta no es un numero");
					ERROR.setVisible(true);
					error=true;
				}
			 	
			 	try 
				{
				    Float.parseFloat(valor.getText());
				    
				} 
				catch (Exception e)
				{
					ERROR.setText("El valor no es un numero");
					ERROR.setVisible(true);
					error=true;
				}
			 	
			 	if(!error)
			 	{
			 		
			 		String evalFecha=fecha.getValue().toString();
			 	
				 	String[] arregloFecha=evalFecha.split("-");
				 	
				 	if(Float.parseFloat(arregloFecha[0])>2022)
				 	{
				 		tarjeta=new Tarjeta(tipoTarjeta.getValue().toString(),Integer.parseInt(numero.getText()),nombre.getText(),Float.parseFloat(valor.getText()),evalFecha);
				 		
				 		numeroTarjeta=Integer.parseInt(numero.getText());
				 		nombrePoseedor=nombre.getText();
				 		fechaVencimiento=evalFecha;
				 		ValorAPagar=Float.parseFloat(valor.getText());
				 		
				 		montoAbonado+=Float.parseFloat(valor.getText());
						
						abonado.setText(Float.toString(montoAbonado));
				 		
				 		return true;
				 	}
				 	else 
				 	{
				 		ERROR.setText("La fecha de vencimiento debe ser en un año mayor a la del 2022");
						ERROR.setVisible(true);	
				 	}
			 
			 	}
		 }
		 else
		 {
			 ERROR.setText("No lleno todos los campos");
			 ERROR.setVisible(true);	 
		 }
		 
		 return false;
    	
    }
	
	  private Stage stage;
	  private Scene scene;
	  private Parent root;
	  
	  public void SiguienteTarjeta(ActionEvent event)  throws IOException
	  {
			
			ERROR.setVisible(false);
			
			if(evaluarTarjeta())
			{
				
				ControllerFinalizarPago.todoTarjeta(tarjeta);
				
			    if((ordenPago.size()==2 && ordenPago.get(2)==3) || ordenPago.size()==3)
				{
			    	ControllerEfectivo.ListPagos(ordenPago);
					ControllerEfectivo.RentaTot(RentaTot);
					ControllerEfectivo.Montos(montoAbonado);
					root= FXMLLoader.load(getClass().getResource("/Interfaces/Efectivo.fxml"));
					stage= (Stage)((Node)event.getSource()).getScene().getWindow();
					scene= new Scene (root);
					stage.setScene(scene);
					stage.show();
					
				}
				else if(montoAbonado.equals(RentaTot))
				{
					
					root= FXMLLoader.load(getClass().getResource("/Interfaces/FinalizarPago.fxml"));
					stage= (Stage)((Node)event.getSource()).getScene().getWindow();
					scene= new Scene (root);
					stage.setScene(scene);
					stage.show();	
				}
				else
				{
					ERROR.setText("El monto abonado no es igual al monto que debe pagar");
					ERROR.setVisible(true);	
					
				}
				
			}
			else
			{
				ERROR.setText("No lleno todos los campos");
				ERROR.setVisible(true);	
			}
			
	 }
		
		public void atrasTarjeta(ActionEvent event) throws IOException
		{
			if(ordenPago.get(0)==1)
			{
				root= FXMLLoader.load(getClass().getResource("/Interfaces/Bonos.fxml"));
				stage= (Stage)((Node)event.getSource()).getScene().getWindow();
				scene= new Scene (root);
				stage.setScene(scene);
				stage.show();	
				
			}
			else
			{
				root= FXMLLoader.load(getClass().getResource("/Interfaces/MetodosDePago.fxml"));
				stage= (Stage)((Node)event.getSource()).getScene().getWindow();
				scene= new Scene (root);
				stage.setScene(scene);
				stage.show();	
			}
					
		}  
		

}
