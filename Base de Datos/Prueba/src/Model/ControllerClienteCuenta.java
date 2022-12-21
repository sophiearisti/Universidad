package Model;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControllerClienteCuenta 
{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public static int idCliente;
	
	public static void idCliente(Integer id)
	{
		idCliente=id;
	}
	
	public void Solicitudesaceptadas(ActionEvent event) throws IOException 
	{
		Aceptado.idDueno(idCliente);
		root = FXMLLoader.load(getClass().getResource("/Interfaces/Aceptado.fxml"));
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void BuscarPropiedades(ActionEvent event) throws IOException 
	{
		Buscador.idCliente(idCliente);
		root = FXMLLoader.load(getClass().getResource("/Interfaces/Buscador.fxml"));
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void Intereses(ActionEvent event) throws IOException 
	{
		InteresesController.idcliente(idCliente);
		root = FXMLLoader.load(getClass().getResource("/Interfaces/Intereses.fxml"));
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void Ajustes(ActionEvent event) throws IOException 
	{
		Ajustes.idCuenta(1);
		Ajustes.idUsuario(idCliente);
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
