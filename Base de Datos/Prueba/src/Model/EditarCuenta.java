package Model;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditarCuenta implements Initializable{
	
	@FXML
	private PasswordField contra;
	@FXML
	private TextField nombre;
	@FXML
	private TextField apellido;
	@FXML
	private TextField correo;
	@FXML
	private Button guardar;
	@FXML
	private ImageView back1;
	@FXML
	private Text ERROR;
	
	
 	private  static int idCliente;
//    
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
	
	public void obtenerInfo(ActionEvent event) {
		//Permito que el usuario ingrese nuevos datos
		
		String contrasena, nom, ape, cor;
		
		if(((contra.getText().length()>0 && contra.getText()!=null) && (nombre.getText().length() > 0 && nombre.getText()!=null) && (apellido.getText().length()>0 && apellido.getText()!=null) && (correo.getText().length() > 0 && correo.getText()!=null )))
		{
			contrasena = contra.getText();
			nom = nombre.getText();
			ape = apellido.getText();
			cor = correo.getText();
			
			if(cor.contains("@"))
			{
				String InsertarIntereses="UPDATE CUENTA SET CONTRASENA= ?, NOMBRE= ?,APELLIDO= ?,CORREO=? WHERE IDCUENTA=?";
				
				try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
	    				PreparedStatement ps=conex.prepareStatement(InsertarIntereses))
	    		{
					ps.setString(1, contrasena);
					ps.setString(2, nom);
					ps.setString(3, ape);
					ps.setString(4, cor);
					ps.setInt(5, idCliente);
					
	    			ps.executeQuery();

	    			
	    			ERROR.setVisible(true);
	    			ERROR.setText("Datos guardados");
					
	    		} catch (SQLException e) {
	    			e.printStackTrace();
	    		}
			}
			else {
				ERROR.setVisible(true);
				ERROR.setText("El correo debe contener @");
			}
		}
		else {
			ERROR.setVisible(true);
			ERROR.setText("Debe completar todos los campos");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ERROR.setVisible(false);
	}
}