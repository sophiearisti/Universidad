package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class ControlMenuFigura implements Initializable{

    @FXML
    private Button BSiguiente;

    @FXML
    private ComboBox<String> scrollFigura;
    

    @FXML
    void MostrarPantallaCalcularArea(ActionEvent event) throws IOException 
    {
    	 Stage stage;
    	 Scene scene;
    	 Parent root;
    	 
    	ControlArea.SetFiguraEscogida(scrollFigura.getValue());
    	 
    	root = FXMLLoader.load(getClass().getResource("/vista/pantallaCalcularArea.fxml"));
 	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
 		scene = new Scene(root);
 		stage.setScene(scene);
 		stage.show();

    
    }
    
    @Override
   	public void initialize(URL arg0, ResourceBundle arg1) 
   	{
        ObservableList<String> FigurasList= FXCollections.observableArrayList();
    	
    	FigurasList.add("triangulo");
    	FigurasList.add("cuadrado");
    	FigurasList.add("rectangulo");
    	FigurasList.add("circulo");
    	
    	scrollFigura.setItems(FigurasList);
	}
   	

}
