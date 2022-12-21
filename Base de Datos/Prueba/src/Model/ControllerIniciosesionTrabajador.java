package Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControllerIniciosesionTrabajador {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	@FXML
	private TextField Usuario;
	
	@FXML
	private PasswordField contrasena;
	
    @FXML
    private Text erroneo;
	
	

	public void evaluarinicioTrabajador(ActionEvent event) throws IOException 
	{
       erroneo.setVisible(false);
		
		String username=Usuario.getText();
		System.out.print(username);
		String password= contrasena.getText();
		System.out.print(password);
		
		
		String ConsultaIngreso= "select ID_TRABAJADOR "
				+ "from trabajadores "
				+ "where DOCUMENTO=? and CONTRASENA=?";
		
		
		//se hace la consulta
		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
			PreparedStatement ps=conex.prepareStatement(ConsultaIngreso))
		{
			//TOCA HACER QUE SOLO INGRESE NUMEROS EN ESTA PARTE IMPORTANTE
			//hacer el try catch
			
			Integer user=0;
			
			Boolean esUnNumero=true;
			
			try 
			{
			    user= Integer.parseInt(username);
			    
			} catch (Exception e) {
				erroneo.setText("El usuario no es un numero");
				erroneo.setVisible(true);
				esUnNumero=false;
			}
			
			if(esUnNumero)
			{
				ps.setInt(1, user);
				ps.setString(2, password);
 			
				ResultSet rs= ps.executeQuery();
			
				Boolean ver=rs.next();
			
				System.out.print(ver); 
				
				if(ver)
				{
					ControlerResgis.idtrabajador(rs.getInt("ID_TRABAJADOR"));
					root = FXMLLoader.load(getClass().getResource("/Interfaces/Registro.fxml"));
				  	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				  	scene = new Scene(root);
				  	stage.setScene(scene);
				  	stage.show();
				  
				}
				else
				{
					erroneo.setText("Ingreso de sesion erroneo");
					erroneo.setVisible(true);
			  }
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void devolveriniciosesion(ActionEvent event) throws IOException 
	{
		root = FXMLLoader.load(getClass().getResource("/Interfaces/InicioSesion.fxml"));
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
