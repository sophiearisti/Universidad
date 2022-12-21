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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControllerInicioSesion {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	@FXML
	private TextField Usuario;
	
	@FXML
	private PasswordField contrasena;
	
    @FXML
    private Text erroneo;
	
	public void InicioSesionTrabajador(MouseEvent event) throws IOException 
	{
		  root = FXMLLoader.load(getClass().getResource("/Interfaces/InicioSesionTRAB.fxml"));
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.show();
	}
	
	public void evaluarinicio(ActionEvent event) throws IOException 
	{
        erroneo.setVisible(false);
		
		String username=Usuario.getText();
		System.out.print(username);
		String password= contrasena.getText();
		System.out.print(password);
		
		
		String ConsultaIngreso= "select ID_TIPOCUENTA,IDCUENTA "
				+ "from cuenta "
				+ "where NOMBRE_USUARIO=? and CONTRASENA=?";
		
		
		//se hace la consulta
		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
			PreparedStatement ps=conex.prepareStatement(ConsultaIngreso))
		{
			
 			ps.setString(1, username);
 			ps.setString(2, password);
 			
			ResultSet rs= ps.executeQuery();
			
			Boolean ver=rs.next();

	
		
			if(ver)
			{
				int idcuenta=rs.getInt("IDCUENTA"); 
				
				if(rs.getInt("ID_TIPOCUENTA")==1)
				{
					String ConsultaIntereses= "select * "
							+ "from INTERES_CLIENTE "
							+ "where ID_CUENTA=?";
					
					System.out.print("OK"); 
					
					try(Connection conexPT2 = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
							PreparedStatement psPT2=conex.prepareStatement(ConsultaIntereses))
					{
						
						System.out.print("humm"); 
						
						psPT2.setInt(1, idcuenta);
						
						ResultSet rsPT2= psPT2.executeQuery();
						
						Boolean ver2=rsPT2.next();
						
						System.out.print(ver2); 
						
						if(!ver2)
						{
							
							//mandar el id del cliente para guardar los cambios
							ControllerInteresesNuevo.idcliente(idcuenta);
							root = FXMLLoader.load(getClass().getResource("/Interfaces/InteresesNuevo.fxml"));
							stage = (Stage)((Node)event.getSource()).getScene().getWindow();
							scene = new Scene(root);
							stage.setScene(scene);
							stage.show();
						}
						else
						{
							//esto de mandar el id me asegura entender 
							ControllerClienteCuenta.idCliente(idcuenta);
							root = FXMLLoader.load(getClass().getResource("/Interfaces/Cliente.fxml"));
							stage = (Stage)((Node)event.getSource()).getScene().getWindow();
							scene = new Scene(root);
							stage.setScene(scene);
							stage.show();
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
					ControllerDuenoCuenta.idDueno(idcuenta);
					root = FXMLLoader.load(getClass().getResource("/Interfaces/Dueno.fxml"));
				    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
					scene = new Scene(root);
					stage.setScene(scene);
					stage.show();
				}
				
			}
			else
			{
				erroneo.setVisible(true);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
