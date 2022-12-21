package Model;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Ajustes 
{
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private static Integer idDevolverse;
	
	public static void idCuenta(Integer id)
	{
		idDevolverse=id;
	}
	
    private static Integer idCuenta;
	
	public static void idUsuario(Integer id)
	{
		idCuenta=id;
	}
	
	public void InfoCuenta(ActionEvent event) throws IOException 
	{
		//mandar id cuenta
		InfoCuentaCliente.idcliente(idCuenta);
		root = FXMLLoader.load(getClass().getResource("/Interfaces/InfoCuenta.fxml"));
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void ActualizarCuenta(ActionEvent event) throws IOException 
	{
		//mandar id cuenta
		EditarCuenta.idcliente(idDevolverse); 
		root = FXMLLoader.load(getClass().getResource("/Interfaces/EditarCuenta.fxml"));
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void DevolverCuenta(ActionEvent event) throws IOException 
	{
		if(idDevolverse==1)
		{
			ControllerClienteCuenta.idCliente(idCuenta);
			root = FXMLLoader.load(getClass().getResource("/Interfaces/Cliente.fxml"));
		    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		else
		{
			ControllerDuenoCuenta.idDueno(idCuenta);
			root = FXMLLoader.load(getClass().getResource("/Interfaces/Dueno.fxml"));
		    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		
	}
	
	public void EliminarCuenta(ActionEvent event) throws IOException 
	{
		Eliminar.idCuenta(idCuenta);
		root = FXMLLoader.load(getClass().getResource("/Interfaces/EliminarCuenta.fxml"));
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
