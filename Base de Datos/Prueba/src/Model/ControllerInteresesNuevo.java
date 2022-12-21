package Model;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControllerInteresesNuevo implements Initializable
{
	private Stage stage;
	private Scene scene;
	private Parent root;
		
      @FXML
       private Button sig;
	   @FXML
	    private TextField canthab;
	   
	   @FXML
	    private Text ERROR;


	    @FXML
	    private TextField rentamaxima;

	    @FXML
	    private ComboBox<String> tipopropiedad;
	    ObservableList<String> tipoPropiedadList= FXCollections.observableArrayList("casa","apartamento");

	    @FXML
	    private ComboBox<String> ubicacion;
	    ObservableList<String> PaisList= FXCollections.observableArrayList();
	    
	    private  static int idCliente;
	    
	    public static void idcliente(Integer id)
		{
	    	idCliente=id;
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) 
		{
			tipopropiedad.setItems(tipoPropiedadList);
			
			String Paises= "select pais "
					+ "from ubicacion";
			
			try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
					PreparedStatement ps=conex.prepareStatement(Paises))
			{
				ResultSet rs= ps.executeQuery();
				
				while(rs.next())
				{
					PaisList.add(rs.getString("pais"));
				}
				
				
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
			sig.setDisable(true);
			ubicacion.setItems(PaisList);
		}
		
		
		public void IrACliente(ActionEvent event) throws IOException 
		{
			ERROR.setVisible(false);
			
			if(sig.isDisabled())
			{
				ERROR.setVisible(true);
				ERROR.setText("no ha guardado los datos");
			}
			else
			{
				ControllerClienteCuenta.idCliente(idCliente);
				root = FXMLLoader.load(getClass().getResource("/Interfaces/Cliente.fxml"));
			    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}
			
		}
		

		public void guardar(ActionEvent event) throws IOException 
		{
			Boolean mal=false;
			
			ERROR.setVisible(false);
			
			if(canthab.getText().length()!=0 && rentamaxima.getText().length()!=0 && tipopropiedad.getValue()!=null && ubicacion.getValue()!=null )
			{
				try 
				{
					Integer.parseInt(canthab.getText());
				    
				} 
				catch (Exception e) 
				{
					ERROR.setText("Lo que ingreso no es un numero");
					ERROR.setVisible(true);	
					mal=true;
				}
				
				try 
				{
					Float.parseFloat(rentamaxima.getText());
				    
				} 
				catch (Exception e) 
				{
					ERROR.setText("Lo que ingreso no es un numero");
					ERROR.setVisible(true);	
					mal=true;
				}
				
				if(!mal)
				{

					int idPais=0;
					//para guardar el id del pais
					String Paises= "select ID_UBICACION "
							+ "from ubicacion "
							+ "where pais=?";
					
					try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
		    				PreparedStatement ps=conex.prepareStatement(Paises))
		    		{
						ps.setString(1, ubicacion.getValue());
						
		    			ResultSet rs= ps.executeQuery();
		    			
		    			rs.next();
		    			
						idPais=rs.getInt("ID_UBICACION");
						
		    		} catch (SQLException e) {
		    			// TODO Auto-generated catch block
		    			e.printStackTrace();
		    		}

					String InsertarIntereses="insert into INTERES_CLIENTE (ID_INTERESES,ID_UBICACION,MAX_RENTA,HABITACIONES,TIPO,ID_CUENTA) values (null,?,?,?,?,?) ";
					
					try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
		    				PreparedStatement ps=conex.prepareStatement(InsertarIntereses))
		    		{
						ps.setInt(1, idPais);
						ps.setInt(2, Integer.parseInt(rentamaxima.getText()));
						ps.setInt(3, Integer.parseInt(canthab.getText()));
						ps.setString(4, tipopropiedad.getValue());
						ps.setInt(5, idCliente);
						
		    			ps.executeQuery();

		    			sig.setDisable(false);
		    			
		    			ERROR.setVisible(true);
		    			ERROR.setText("Datos guardados");
						
		    		} catch (SQLException e) {
		    			// TODO Auto-generated catch block
		    			e.printStackTrace();
		    		}
					
				}
			}
			else
			{
				ERROR.setVisible(true);
				ERROR.setText("debe llenar todos los campos");
			}
		}
		

}