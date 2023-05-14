package controlador;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Circulo;
import modelo.Cuadrado;
import modelo.Rectangulo;
import modelo.Triangulo;
import javafx.fxml.Initializable;


public class ControlArea implements Initializable
{

    @FXML
    private Button BCalcularArea;

    @FXML
    private Button atras;
    
    @FXML
    private TextField CuadroTexto1;

    @FXML
    private TextField CuadroTexto2;
    
    private static String FiguraEscogida;
    
    @FXML
    private Label resultado;

	
	public static void SetFiguraEscogida(String figuraEscogida)
	{
		FiguraEscogida=figuraEscogida;
		
	}
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
    	if(FiguraEscogida.equals("triangulo"))
		{
			CuadroTexto2.setVisible(true);
			CuadroTexto1.setPromptText("base");
			CuadroTexto2.setPromptText("altura");
		}
    	else if(FiguraEscogida.equals("cuadrado"))
		{
			CuadroTexto1.setPromptText("lado");
		}
	}
    	
    
    @FXML
    void Calcular(ActionEvent event) 
    {
    	Double area=0d;
    	
    	if(FiguraEscogida.equals("triangulo"))
    	{
    		Double altura=Double.parseDouble (CuadroTexto2.getText());
    		Double base=Double.parseDouble (CuadroTexto1.getText());
    		
    		Triangulo triangulo=new Triangulo("0", 5d,4d, altura,base);
    		
    		area=triangulo.calcular_area();
    		
    	}
    	else if(FiguraEscogida.equals("cuadrado"))
    	{
    		Double lado=Double.parseDouble (CuadroTexto1.getText());
    		
    		Cuadrado cuadrado=new Cuadrado("0", 5d,4d, lado);
    		
    		area=cuadrado.calcular_area();
    		
    	}
    	
    	
    	
    	resultado.setText(Double.toString(area));
    }
    
    @FXML
    void MostrarPantallaMenu(ActionEvent event) throws IOException 
    {
	     Stage stage;
	   	 Scene scene;
	   	 Parent root;
	   	 
   	 
   	 	root = FXMLLoader.load(getClass().getResource("/vista/MenuFigura.fxml"));
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

    }


    
    
    
}

