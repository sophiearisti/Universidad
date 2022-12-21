package Model;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class InfoCuentaCliente implements Initializable {
	@FXML
	private TextField usuario;
	@FXML
	private TextField nom;
	@FXML
	private TextField ape;
	@FXML
	private TextField cor;
	@FXML
	private TextField tipoC;
	@FXML
	private ImageView back;
	@FXML
	private ImageView editar;
	
	
	private  static int idCliente;
    
	public static void idcliente(Integer id)
	{
	    idCliente=id;
	}
	
	public void botonRegresar(MouseEvent event)throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("/Interfaces/Ajustes.fxml"));
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
	}
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//para guardar la info que tiene el cliente en su cuenta
		String infoCuenta= "select NOMBRE_USUARIO, NOMBRE, APELLIDO, CORREO, ID_TIPOCUENTA "
				+ "from CUENTA "
				+ "where IDCUENTA=? ";
		
		String user;
		String nombre;
		String apellido;
		String correo;
		Integer tipoID;
		
		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
				PreparedStatement ps=conex.prepareStatement(infoCuenta))
		{
			ps.setInt(1, idCliente);
			
			ResultSet rs= ps.executeQuery();
			
			rs.next();
			
			user = rs.getNString("NOMBRE_USUARIO");
			nombre = rs.getNString("NOMBRE");
			apellido = rs.getNString("APELLIDO");
			correo = rs.getNString("CORREO");
			tipoID = rs.getInt("ID_TIPOCUENTA");
			
			usuario.setText(user);
			nom.setText(nombre);
			ape.setText(apellido);
			cor.setText(correo);
		
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}