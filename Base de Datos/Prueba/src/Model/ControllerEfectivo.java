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
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControllerEfectivo implements Initializable
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
	
	@FXML
	private Text abonado;
	
	private Float montoAbonado=0f;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		
		totPagar.setText(Float.toString(RentaTot));	
		
		abonado.setText(Float.toString(montoTotalAbonados));	
		
		montoAbonado=montoTotalAbonados;
		
	}
	

    @FXML
    private TextField valor;
	
	
	public void AnadirEfectivo(ActionEvent event)  throws IOException
	{
		montoAbonado=montoTotalAbonados;
		

		ERROR.setVisible(false);
		
		Boolean error=false;
		
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
			
			montoAbonado+=Float.parseFloat(valor.getText());
			
			abonado.setText(Float.toString(montoAbonado));
		}
		
	}
	
	  private Stage stage;
	  private Scene scene;
	  private Parent root;
	  

	public void SiguienteEfectivo(ActionEvent event)  throws IOException
	{
		Boolean todoCorrecto=true;
		
		ERROR.setVisible(false);
		
		try 
		{
		  Float.parseFloat(valor.getText());
		    
		} 
		catch (Exception e)
		{
			ERROR.setText("El valor no es un numero");
			ERROR.setVisible(true);
			 todoCorrecto=false;
		}
		
		
		if(todoCorrecto)
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
		
	public void atrasEfectivos(ActionEvent event) throws IOException
	{
		if(ordenPago.size()==3)
		{
			root= FXMLLoader.load(getClass().getResource("/Interfaces/Tarjeta.fxml"));
			stage= (Stage)((Node)event.getSource()).getScene().getWindow();
			scene= new Scene (root);
			stage.setScene(scene);
			stage.show();	
		}
		else
		{
			switch(ordenPago.get(0))
			{
				case 2:
					root= FXMLLoader.load(getClass().getResource("/Interfaces/Tarjeta.fxml"));
					stage= (Stage)((Node)event.getSource()).getScene().getWindow();
					scene= new Scene (root);
					stage.setScene(scene);
					stage.show();
				break;
				
				case 1:
					root= FXMLLoader.load(getClass().getResource("/Interfaces/Bonos.fxml"));
					stage= (Stage)((Node)event.getSource()).getScene().getWindow();
					scene= new Scene (root);
					stage.setScene(scene);
					stage.show();
				break;
				case 3:
					root= FXMLLoader.load(getClass().getResource("/Interfaces/MetodosDePago.fxml"));
					stage= (Stage)((Node)event.getSource()).getScene().getWindow();
					scene= new Scene (root);
					stage.setScene(scene);
					stage.show();
				break;
			}
		}
				
	} 
		
		

}
