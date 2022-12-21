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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControllerMetodos implements Initializable
{
	 @FXML
	    private CheckBox TickBonos;

	    @FXML
	    private CheckBox TickEfectivo;

	    @FXML
	    private CheckBox TickTarjeta;

	    @FXML
	    private TextField correo;
	    
	    @FXML
		private Text ERROR;
	    
	    @FXML
	    private Text totPagar;
	    
	    
	    private static Float RentaTot;
		
		public static void RentaTot(Float tot)
		{
			RentaTot=tot;
		}
		
		private static Boolean bonosIsTicked=false;
		private static Boolean TarjetaIsTicked=false;
		private static Boolean EfectivoIsTicked=false;
		
		private static String email="";
		
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) 
		{
			
			totPagar.setText(Float.toString(RentaTot));
			correo.setText(email);
			TickTarjeta.setSelected(TarjetaIsTicked);
			TickEfectivo.setSelected(EfectivoIsTicked);
			TickBonos.setSelected(bonosIsTicked);
			
			bonosIsTicked=false;
			TarjetaIsTicked=false;
			EfectivoIsTicked=false;
			email="";
			
		}
	    
	    private Stage stage;
		private Scene scene;
		private Parent root;
		
		
		
		public void SiguienteMetodos (ActionEvent event)  throws IOException
		{
			Boolean todoCorrecto=true;
			
			ERROR.setVisible(false);
			
			ArrayList<Integer> ordenDePago= new ArrayList<>();
			
			
			if(correo.getText().length()!=0)
			{
				if(correo.getText().contains("@"))
				{
					email=correo.getText();
							
					if(!TickBonos.isSelected() && !TickEfectivo.isSelected() && !TickTarjeta.isSelected())
					{
						ERROR.setText("Debe tener minimo un metodo de pago");
						ERROR.setVisible(true);	
						todoCorrecto=false;
						
					}
					else
					{
						
						if(TickBonos.isSelected())
						{
							
							ordenDePago.add(1);
							bonosIsTicked=true;
							System.out.println("bien");
							
						}
						
						
						if(TickTarjeta.isSelected())
						{
						
							ordenDePago.add(2);
							TarjetaIsTicked=true;
							
							
						}
						
						if(TickEfectivo.isSelected())
						{
							
							ordenDePago.add(3);
							EfectivoIsTicked=true;
							
						}
					}
				
					
				}
				else
				{
					
					ERROR.setText("El correo no contiene un @");
					ERROR.setVisible(true);
					todoCorrecto=false;
				}
				
				
				
			}
			else
			{
				ERROR.setText("no escribio nada en el campo del correo");
				ERROR.setVisible(true);	
				todoCorrecto=false;
			}
			
			
			
			if(todoCorrecto)
			{
				ControllerFinalizarPago.todoCorreo(email);
				ControllerFinalizarPago.ListPagos(ordenDePago);
				
				if(ordenDePago.get(0).equals(1))
				{
					System.out.println("bien");
					ControllerBonos.ListPagos(ordenDePago);
					ControllerBonos.RentaTot(RentaTot);
					root= FXMLLoader.load(getClass().getResource("/Interfaces/Bonos.fxml"));
					stage= (Stage)((Node)event.getSource()).getScene().getWindow();
					scene= new Scene (root);
					stage.setScene(scene);
					stage.show();
//					
				}
				else if(ordenDePago.get(0).equals(2))
				{
					System.out.println("bien");
					ControllerTarjeta.ListPagos(ordenDePago);
					ControllerTarjeta.RentaTot(RentaTot);
					ControllerTarjeta.Montos(0f);
					ControllerFinalizarPago.todoBonos(null);
					root= FXMLLoader.load(getClass().getResource("/Interfaces/Tarjeta.fxml"));
					stage= (Stage)((Node)event.getSource()).getScene().getWindow();
					scene= new Scene (root);
					stage.setScene(scene);
					stage.show();
					
				}
				else
				{
					System.out.println("bien");
					ControllerEfectivo.ListPagos(ordenDePago);
					ControllerEfectivo.RentaTot(RentaTot);
					ControllerEfectivo.Montos(0f);
					ControllerFinalizarPago.todoBonos(null);
					root= FXMLLoader.load(getClass().getResource("/Interfaces/Efectivo.fxml"));
					stage= (Stage)((Node)event.getSource()).getScene().getWindow();
					scene= new Scene (root);
					stage.setScene(scene);
					stage.show();
//					
				}
				
			}
			
		}
		
		public void atrasMetodos(ActionEvent event) throws IOException
		{
			root= FXMLLoader.load(getClass().getResource("/Interfaces/ServiciosAdicionales.fxml"));
			stage= (Stage)((Node)event.getSource()).getScene().getWindow();
			scene= new Scene (root);
			stage.setScene(scene);
			stage.show();			
		}  
		
		

}
