package Model;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControlerResgis implements Initializable{
	
public static int idtrab;
private Stage stage;
private Scene scene;
private Parent root;
	
	ObservableList<String> tipoDocList= FXCollections.observableArrayList("cc","ce","ti");
	
	ObservableList<String> tipoUsuarioList= FXCollections.observableArrayList("cliente","dueno");
	
	

		@FXML
		private Text notificacion;
	
		@FXML
	    private TextField Usuario;

	    @FXML
	    private PasswordField contrasena;

	    @FXML
	    private TextField correo;

	    @FXML
	    private TextField nombre;
	    
	    @FXML
	    private TextField apellido;
	    
	    @FXML
	    private TextField numerodoc;

	    @FXML
	    private ComboBox<String> tiposDocBox;

	    @FXML
	    private ComboBox<String> tiposUsuarioBox;
    
	
	public static void idtrabajador(Integer id)
	{
		idtrab=id;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		tiposDocBox.setItems(tipoDocList);
		tiposUsuarioBox.setItems(tipoUsuarioList);
		
	}
	
	public void devolveriniciosesion(ActionEvent event) throws IOException 
	{
		root = FXMLLoader.load(getClass().getResource("/Interfaces/InicioSesion.fxml"));
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
	public void evaluarRegistro(ActionEvent event) throws IOException 
	{
		String password= contrasena.getText();
        System.out.print(password);
 		String name= nombre.getText();
        System.out.print(name);
        String lastname= apellido.getText();
  		System.out.print(lastname);
		String email= correo.getText();
		System.out.print(email);
		String doc=numerodoc.getText();
		System.out.print(doc);
     	String docType= tiposDocBox.getValue();
     	System.out.print( docType);
		String userType= tiposUsuarioBox.getValue();
		System.out.print(userType);
		
		notificacion.setVisible(false);
		 
    	String username=Usuario.getText();
		System.out.print(username);
		
				String ConsultaRegistro= "select * "
				+ "from cuenta "
				+ "where NOMBRE_USUARIO=? ";
		
				
		if(docType!=null && userType!=null && username.length()!=0 && password.length()!=0 && name.length()!=0 && lastname.length()!=0 && email.length()!=0 && doc.length()!=0)
		{
     		System.out.print("BUENAS");
			if(correo.getText().contains("@"))
			{
				System.out.print("BIEN");
				if(Pattern.matches("[A-Za-z0-9_-]{1,15}",username))
				{
					
					try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
							PreparedStatement ps=conex.prepareStatement(ConsultaRegistro))
					{
						ps.setString(1, username);
						
						ResultSet rs= ps.executeQuery();
						
						Boolean existe=rs.next();
						
						if(!existe)
						{
							//cerrar conexion
							
							System.out.print("VAMOS DE MARAVILLA");
							
							evaluarDuplicados(username,password,name,lastname,email,doc,docType,userType);
							
						}
						else
						{
							notificacion.setText("Ese nombre de usuario ya existe");
							notificacion.setVisible(true);
						}
						
						
					}
					catch (SQLException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				else
				{
					notificacion.setText("Usuario no puede contener caracteres especiales");
					notificacion.setVisible(true);
					
				}
			}
			else
			{
				notificacion.setText("correo debe contener @");
				notificacion.setVisible(true);
			}
		}
		else
		{
			notificacion.setVisible(true);	
			notificacion.setText("Debe llenar todos los campos");	
		}
//		
		
		
	}
	
	private void evaluarDuplicados(String username, String password, String name,String lastname, String email, String doc, String docType, String userType) throws IOException 
	{
		//HACER FUNCION PARA ESTO
		
		String ConsultaCuentaRepetida;
		
		ConsultaCuentaRepetida= "insert into cuenta (IDCUENTA,NOMBRE_USUARIO,CONTRASENA,NOMBRE,APELLIDO,CORREO,DOCUMENTO,TIPO_DOCUMENTO,ID_TIPOCUENTA,ID_TRABAJADOR) "
				+ "values (NULL,?,?,?,?,?,?,?,?,?)";
		
		Boolean perfect=true;
		
		try(Connection conexx = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
				PreparedStatement pss=conexx.prepareStatement(ConsultaCuentaRepetida))
		{
			pss.setString(1, username);
			pss.setString(2, password);
			pss.setString(3, name);
			pss.setString(4, lastname);
			pss.setString(5, email);
			pss.setString(6, doc);
			pss.setString(7, docType);
			
			
			if(userType.equals("cliente"))
			{
				pss.setInt(8, 1);
			}
			else
			{
				pss.setInt(8, 2);
			}
			
			pss.setInt(9, idtrab);
			
			pss.executeQuery();
			
			//conexx.commit();
			
			//pss.close();
				
		}
		catch (SQLException e) 
		{
			notificacion.setText("Esta persona ya tiene una cuenta de este tipo");
			notificacion.setVisible(true);
			perfect=false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(perfect)
		{
			
			notificacion.setText("Usuario ingresado efectivamente en el sistema");
			notificacion.setVisible(true);
		}
		
		
		
		
	}

}
