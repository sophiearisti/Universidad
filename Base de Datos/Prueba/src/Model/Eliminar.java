package Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Eliminar 
{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	@FXML
    private Text pregunta;
	
	@FXML
    private Text statement;
	
	@FXML
    private Button aceptar;
	
	@FXML
    private Button si;
	
	@FXML
    private Button no;
	
    private static Integer idCuenta;
	
	public static void idCuenta(Integer id)
	{
		idCuenta=id;
	}
	
	public void Si(ActionEvent event) throws IOException 
	{
		String EliminarCuenta="delete from cuenta "
				+ "where IDCUENTA=?";
		
		
		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
				PreparedStatement ps=conex.prepareStatement(EliminarCuenta))
		{
			ps.setInt(1,idCuenta);
			
			ps.executeQuery();
		}
		catch (SQLException e)
		{
		// TODO Auto-generated catch block
			e.printStackTrace();
	    }
		si.setVisible(false);
		no.setVisible(false);
		pregunta.setVisible(false);
		
		statement.setVisible(true);
		aceptar.setVisible(true);
		
		
	}
	
	public void Aceptar(ActionEvent event) throws IOException 
	{
		
		root = FXMLLoader.load(getClass().getResource("/Interfaces/InicioSesion.fxml"));
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void No(ActionEvent event) throws IOException 
	{
		root = FXMLLoader.load(getClass().getResource("/Interfaces/Ajustes.fxml"));
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
