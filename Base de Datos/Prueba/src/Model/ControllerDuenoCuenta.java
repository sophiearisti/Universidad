package Model;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControllerDuenoCuenta 
{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	public static int idDueno;
	
	public static void idDueno(Integer id)
	{
		idDueno=id;
	}
	
	public void solicitudes(ActionEvent event) throws IOException 
	{
		Solicitudes.idDueno(idDueno);
		root = FXMLLoader.load(getClass().getResource("/Interfaces/Solicitudes.fxml"));
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void Historial(ActionEvent event) throws IOException 
	{
	    ControllerHistorial.idCliente(idDueno);
		root = FXMLLoader.load(getClass().getResource("/Interfaces/historial.fxml"));
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void CrearPropiedad(ActionEvent event) throws IOException 
	{
		CrearPropiedad.idcliente(idDueno);
		root = FXMLLoader.load(getClass().getResource("/Interfaces/CrearPropiedad.fxml"));
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void VerPropiedades(ActionEvent event) throws IOException 
	{
		root = FXMLLoader.load(getClass().getResource("/Interfaces/VerPropiedades.fxml"));
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void Ajustes(ActionEvent event) throws IOException 
	{
		Ajustes.idCuenta(2);
		Ajustes.idUsuario(idDueno);
		root = FXMLLoader.load(getClass().getResource("/Interfaces/Ajustes.fxml"));
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void CerrarSesion(ActionEvent event) throws IOException 
	{
		
		root = FXMLLoader.load(getClass().getResource("/Interfaces/CerrarSesion.fxml"));
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
